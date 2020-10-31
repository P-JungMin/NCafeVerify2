package com.pic_e.ncafeverify2;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.pic_e.ncafeverify2.listeners.CommandListener;
import com.pic_e.ncafeverify2.listeners.EventListener;
import com.pic_e.ncafeverify2.skript.SkriptManager;
import com.pic_e.ncafeverify2.utils.Config;

public class NCafeVerify2 extends JavaPlugin{
	private static Plugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
		try {
			Config.load();
			getServer().getPluginManager().registerEvents(new EventListener(), this);
			getCommand("카페인증").setExecutor(new CommandListener.User());
			getCommand("인증관리").setExecutor(new CommandListener.Admin());
			try {
				SkriptManager.register();
			} catch (NoClassDefFoundError ignore) {}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		Config.saveUserData();
	}
	
	public static Plugin getInstance() {
		return instance;
	}
	
}
