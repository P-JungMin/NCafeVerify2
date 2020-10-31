package com.pic_e.ncafeverify2.skript;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pic_e.ncafeverify2.event.VerifySuccessEvent;
import com.pic_e.ncafeverify2.utils.Util;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

public class SkriptManager {
	public static void register() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
			Skript.registerExpression((Class) PlayerVerified.class, (Class) Boolean.class, ExpressionType.SIMPLE, new String[] { "%player% verified" });
			Skript.registerExpression((Class) GetPlayerNaverID.class, (Class) String.class, ExpressionType.SIMPLE, new String[] { "%player%'s naverid" });
			Skript.registerEvent("verify success", (Class) VerifyComplete.class, (Class) VerifySuccessEvent.class, new String[] { "verify success" });
			EventValues.registerEventValue((Class) VerifySuccessEvent.class, (Class) Player.class, (Getter) new Getter<Player, VerifySuccessEvent>() {
				public Player get(final VerifySuccessEvent e) {
					return e.getPlayer();
				}
			}, 0);
			System.out.println(Util.color("&a[NCafeVerify] Skript API를 로딩했습니다."));
		}
	}
}
