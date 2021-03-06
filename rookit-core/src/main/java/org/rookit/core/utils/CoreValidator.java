package org.rookit.core.utils;

import org.rookit.utils.log.Logs;

@SuppressWarnings("javadoc")
public class CoreValidator extends Validator {
	
	private static final CoreValidator SINGLETON = new CoreValidator();

	public static final CoreValidator getDefault() {
		return SINGLETON;
	}
	
	private CoreValidator() {
		super(Logs.CORE);
	}

}
