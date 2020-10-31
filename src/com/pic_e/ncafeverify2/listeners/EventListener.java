package com.pic_e.ncafeverify2.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.pic_e.ncafeverify2.utils.Verify;

public class EventListener implements Listener{
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(Verify.process.containsKey(e.getPlayer().getUniqueId())) {
			Verify.process.remove(e.getPlayer().getUniqueId());
		}
	}
}
