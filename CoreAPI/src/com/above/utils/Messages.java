package com.above.utils;

import com.above.core.Core;
import com.above.interfaces.IDataProvider;

/**
 * Message API to get customised messages from config file
 * 
 * @author NathanGrad
 *
 */
public class Messages implements IDataProvider<String, String> {

	/**
	 * Read only variable - do not assign
	 */
	public static Core core;

	@Override
	public String get(String input) {
		try {
			return core.getConfig().get("messages." + input).toString();
		} catch (NullPointerException npe) {
			setup(input, "");
		}
		return "";
	}

	@Override
	public void set(String input, String setterVariable) {
		core.getConfig().set("messages." + input, setterVariable);
		core.saveConfig();
	}

	@Override
	public void update(String input, String updaterVariable) {
		core.getConfig().set("messages." + input, get(input) + updaterVariable);
		core.saveConfig();
	}

	@Override
	public void setup(String input, String setterVariable) {
		core.getConfig().set("messages." + input, setterVariable);
		core.saveConfig();
	}

}
