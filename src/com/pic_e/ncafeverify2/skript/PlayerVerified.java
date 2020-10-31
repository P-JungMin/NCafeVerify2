package com.pic_e.ncafeverify2.skript;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.pic_e.ncafeverify2.utils.Config;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class PlayerVerified extends SimpleExpression<Boolean>{

	private Expression<Player> player;
    
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
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
    protected Boolean[] get(final Event e2) {
        final Player eplayer = this.player.getSingle(e2);
        return new Boolean[] { Config.complete.containsKey(eplayer.getUniqueId()) };
    }

}
