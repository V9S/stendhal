/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.gui;


import games.stendhal.client.listener.FeatureChangeListener;

import javax.swing.SwingUtilities;

/**
 * A key ring.
 */
public class KeyRing extends SlotWindow implements FeatureChangeListener {
	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 2182192545574139973L;

	/**
	 * Create a key ring.
	 */
	public KeyRing() {
		// Remember if you change these numbers change also a number in
		// src/games/stendhal/server/entity/RPEntity.java
		super("keyring", 2, 4);
		// A panel window; forbid closing
		setCloseable(false);
	}

	//
	// KeyRing
	//

	/**
	 * Disable the keyring.
	 */
	private void disableKeyring() {
		/*
		 * You can not really lose a keyring for now, but
		 * a disable message is received at every map change.
		 * Just ignore it. (And after keyrings are made to
		 * real items, this whole file will be obsolete anyway).
		 */
	}

	//
	// FeatureChangeListener
	//

	/**
	 * A feature was disabled.
	 * 
	 * @param name
	 *            The name of the feature.
	 */
	public void featureDisabled(final String name) {
		if (name.equals("keyring")) {
			disableKeyring();
		}
	}

	/**
	 * A feature was enabled.
	 * 
	 * @param name
	 *            The name of the feature.
	 * @param value
	 *            Optional feature specific data.
	 */
	public void featureEnabled(final String name, final String value) {
		if (name.equals("keyring")) {
			if(!isVisible()) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setVisible(true);
					}
				});
			}
		}
	}
}
