/*
 * @(#) src/games/stendhal/server/config/zone/ConfiguratorXMLReader.java
 *
 * $Id$
 */

package games.stendhal.server.core.config.zone;

//
//

import org.w3c.dom.Element;

import org.apache.log4j.Logger;

/**
 * A generic entity setup xml reader.
 */
public class ConfiguratorXMLReader extends SetupXMLReader {
	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(ConfiguratorXMLReader.class);

	//
	// ConfiguratorXMLReader
	//

	/**
	 * Create a configurator descriptor.
	 * 
	 * @param element
	 *            The configurator XML element.
	 * @param className
	 *            The handler class name.
	 * 
	 * @return A configurator.
	 */
	protected ConfiguratorDescriptor read(final Element element,
			final String className) {
		return new ConfiguratorDescriptor(className);
	}

	//
	// SetupXMLReader
	//

	/**
	 * Create a setup descriptor from XML data.
	 * 
	 * @param element
	 *            The descriptor XML element.
	 * 
	 * @return A setup descriptor, or <code>null</code> if invalid.
	 */
	@Override
	public SetupDescriptor read(final Element element) {
		if (!element.hasAttribute("class-name")) {
			logger.error("Implmentation without class-name");
			return null;
		} else {
			String className = element.getAttribute("class-name");

			ConfiguratorDescriptor desc = read(element, className);

			readParameters(desc, element);

			return desc;
		}
	}
}
