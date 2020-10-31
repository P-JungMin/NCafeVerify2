package com.pic_e.ncafeverify2.utils;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.pic_e.ncafeverify2.dto.Entry;

public class Util {
	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static void colorMessage(String message, Player p) {
		p.sendMessage(color(message));
	}
	
	public static List<String> makeList(List<String> list, Entry... args) {
		for(int i=0; i<list.size(); i++) {
			String temp = list.get(i);
			for(Entry entry : args) {
				temp = temp.replaceAll(entry.getKey(), entry.getValue());
			}
			list.set(i, color(temp));
		}
		return list;
	}
	
	public static void sendList(List<String> list, Player p) {
		list.forEach(message->p.sendMessage(color(message)));
	}
	
	public static String createCode(int len) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < len; i++) {
			temp.append((rnd.nextInt(10)));
		}
		return temp.toString();
	}
}
