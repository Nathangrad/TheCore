package com.above.core;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.above.utils.Messages;

/**
 * Core main class running the example Data Management system
 * 
 * @author NathanGrad
 *
 */
public class Core extends JavaPlugin {

	private Logger console = Bukkit.getServer().getLogger();

	@Override
	public void onEnable() {
		Messages.core = this;
		console.info("Core successfully enabled");
	}

}
