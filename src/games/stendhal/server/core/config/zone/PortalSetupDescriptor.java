/*
 * @(#) src/games/stendhal/server/config/zone/PortalSetupDescriptor.java
 *
 * $Id$
 */

package games.stendhal.server.core.config.zone;

//
//

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.EntityFactoryHelper;
import games.stendhal.server.entity.mapstuff.portal.Portal;

import org.apache.log4j.Logger;

/**
 * A portal setup descriptor.
 */
public class PortalSetupDescriptor extends EntitySetupDescriptor {
	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(PortalSetupDescriptor.class);

	/**
	 * The named portal identifier.
	 */
	protected Object identifier;

	/**
	 * The destination zone name (if any).
	 */
	protected String destinationZone;

	/**
	 * The named destination portal (if any).
	 */
	protected Object destinationIdentifier;

	/**
	 * Whether replacing an existing portal at that location.
	 */
	protected boolean replacing;

	/**
	 * Create a portal setup descriptor.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 * @param identifier
	 *            The identifier,
	 */
	public PortalSetupDescriptor(final int x, final int y,
			final Object identifier) {
		super(x, y);

		this.identifier = identifier;

		destinationZone = null;
		destinationIdentifier = null;
		replacing = false;
	}

	//
	// PortalSetupDescriptor
	//

	/**
	 * Get the destination identifier.
	 * 
	 * @return An identifier.
	 */
	public Object getDestinationIdentifier() {
		return destinationIdentifier;
	}

	/**
	 * Get the destination zone.
	 * 
	 * @return A zone name.
	 */
	public String getDestinationZone() {
		return destinationZone;
	}

	/**
	 * Get the identifier.
	 * 
	 * @return An identifier.
	 */
	public Object getIdentifier() {
		return identifier;
	}

	/**
	 * Determine if existing portals are replaced.
	 * 
	 * @return <code>true</code> if replacing an existing portal at that
	 *         location.
	 */
	public boolean isReplacing() {
		return replacing;
	}

	/**
	 * Set the destination zone/identifier.
	 * 
	 * @param zone
	 *            The destination zone name.
	 * @param identifier
	 *            The named destination portal.
	 */
	public void setDestination(final String zone, final Object identifier) {
		this.destinationZone = zone;
		this.destinationIdentifier = identifier;
	}

	/**
	 * Set whether to replace any existing portal.
	 * 
	 * @param replacing
	 *            Whether replacing an existing portal at that location.
	 */
	public void setReplacing(final boolean replacing) {
		this.replacing = replacing;
	}

	//
	// SetupDescriptor
	//

	/**
	 * Do appropriate zone setup.
	 * 
	 * @param zone
	 *            The zone.
	 */
	@Override
	public void setup(final StendhalRPZone zone) {
		String className = getImplementation();

		if (className == null) {
			/*
			 * Default implementation
			 */
			className = Portal.class.getName();
		}

		try {
			Portal portal = (Portal) EntityFactoryHelper.create(className,
					getParameters(), getAttributes());
			if (portal == null) {
				logger.warn("Unable to create portal: " + className);

				return;
			}

			portal.setPosition(getX(), getY());
			portal.setIdentifier(getIdentifier());

			Object destIdentifier = getDestinationIdentifier();

			if (destIdentifier != null) {
				portal.setDestination(getDestinationZone(), destIdentifier);
			}

			if (isReplacing()) {
				Portal oportal = zone.getPortal(getX(), getY());

				if (oportal != null) {
					logger.debug("Replacing portal: " + oportal);

					zone.remove(oportal);
				}
			}

			// Fix PortalBugs; blocked portals
			if (zone.simpleCollides(portal, portal.getX(), portal.getY())) {
				logger.warn("Unblocking blocked portal: " + portal);
				zone.collisionMap.setCollide(portal.getArea(), false);
			}

			zone.add(portal);
		} catch (IllegalArgumentException ex) {
			logger.error("Error with portal factory", ex);
		}
	}
}
