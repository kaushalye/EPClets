
package org.epclet;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class EPCletStandaloneSetup extends EPCletStandaloneSetupGenerated{

	public static void doSetup() {
		new EPCletStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

