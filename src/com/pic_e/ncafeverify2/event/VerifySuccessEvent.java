package com.pic_e.ncafeverify2.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VerifySuccessEvent extends Event implements Cancellable{
	private Player player;
	private static final HandlerList HANDLERS = new HandlerList();
	private boolean isCancelled;
	
	public VerifySuccessEvent(Player player) {
		this.player = player;
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
