/*
 * generated by Xtext
 */
package org.epclet.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;

import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the IDE.
 */
public class EPCletUiModule extends org.epclet.ui.AbstractEPCletUiModule {
	public EPCletUiModule(AbstractUIPlugin plugin) {
		super(plugin);
		 
	}
	public void configureEPCletXtextEditorCallback(com.google.inject.Binder binder) {
		binder.bind(IXtextEditorCallback.class).annotatedWith(Names.named("MyDSLXtextEditorCallback")).to(EPCletXtextEditorCallback.class);
	}
}
