package com.pic_e.ncafeverify2.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pic_e.ncafeverify2.dto.Entry;
import com.pic_e.ncafeverify2.dto.VerifyDTO;
import com.pic_e.ncafeverify2.utils.Config.Node;


public class Verify {
	public static HashMap<UUID, VerifyDTO> process = new HashMap<>();
	
	public static void start(Player p, String naverID) {
		String code = Util.createCode(5);
		process.put(p.getUniqueId(), new VerifyDTO(p.getName(), code, naverID));
		Util.sendList(Util.makeList(Config.getMessage(Node.Start), new Entry("\\{code\\}", code)), p);
	}
	
	public static boolean confirm(VerifyDTO dto) {
		Document d;
		String data;
		Elements esd;
		Element ed = null;
		String clubId;
		String naverId;
		try {
			d = Jsoup.connect(Config.getCafeURL()).get();
			data = d.select("script").toString();
			clubId= data.split("g_sClubId = \"")[1].split("\";")[0];
			d = Jsoup.connect("https://cafe.naver.com/ArticleList.nhn?search.clubid=" + clubId).get();
			esd = d.select("tr>td.td_article>div.board-list>div.inner_list>a");
			for(Element e : esd) {
				String t = e.text();
				if(t.contains(":")) {
					if(t.split(":")[1].equals(dto.getCode())) {
						ed = e;
						break;
					}
				}
			}
			if(ed == null) return false;
			data = ed.parent().parent().parent().parent().select("td.td_name>div.pers_nick_area>table>tbody>tr>td.p-nick>a").attr("onclick");
			naverId = data.split("ui\\(event, '")[1].split("',")[0];
			if(naverId.equals(dto.getNaverId())) return true;
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
