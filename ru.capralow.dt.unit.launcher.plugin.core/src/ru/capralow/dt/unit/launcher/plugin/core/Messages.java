package ru.capralow.dt.unit.launcher.plugin.core;

import org.eclipse.osgi.util.NLS;

final class Messages extends NLS {
	private static final String BUNDLE_NAME = "ru.capralow.dt.launching.core.messages"; //$NON-NLS-1$

	public static String Internal_class;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
