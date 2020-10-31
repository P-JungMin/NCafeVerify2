package com.pic_e.ncafeverify2.api;

import org.bukkit.entity.Player;

import com.pic_e.ncafeverify2.utils.Config;

public class NCafeVerifyAPI {
	public static boolean isVerified(Player p) {
		return Config.complete.containsKey(p.getUniqueId());
	}
	
	public static String getNaverID(Player p) {
		if(isVerified(p)) {
			return Config.complete.get(p.getUniqueId()).getNaverId();
		}else return null;
	}
}
