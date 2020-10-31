package com.pic_e.ncafeverify2.api;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.pic_e.ncafeverify2.utils.Config;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class GetPlayerNaverID extends SimpleExpression<String>{

	private Expression<Player> player;
    
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] e, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        this.player = (Expression<Player>)e[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return null;
    }
    
    @Nullable
    protected String[] get(final Event e2) {
        final Player eplayer = this.player.getSingle(e2);
        return new String[] { Config.complete.get(eplayer.getUniqueId()).getNaverId() };
    }

}
