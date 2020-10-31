package com.pic_e.ncafeverify2.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.pic_e.ncafeverify2.dto.VerifyDTO;

public class Config {
	private static File file = new File("plugins/NCafeVerify2/config.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public static HashMap<UUID, VerifyDTO> complete = new HashMap<>(); 
	
	public static void load() throws Exception{
		loadConfig();
		loadUserData();
	}
	
	public static void loadConfig() throws Exception{
		if(!file.exists()) {
			for(Node node : Node.values()) {
				config.set("cafe-url", "url");
				config.set(node.getPath(), node.getFirstValue());
			}
			config.save(file);
		}else config.load(file);
	}
	
	private static void loadUserData() throws Exception{
		File[] files = new File("plugins/NCafeVerify2/data/").listFiles((dir, name)->name.endsWith(".yml"));
		if(files == null) files = new File[0];
		for(File userFile : files) {
			FileConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
			VerifyDTO dto = new VerifyDTO(userData.getString("nickname"), null, userData.getString("naverId"), userData.getString("time"));
			complete.put(UUID.fromString(userFile.getName().replace(".yml", "")), dto);
		}
	}
	
	public static void saveUserData() {
		complete.forEach((uuid, dto)->{
			File userFile = new File("plugins/NCafeVerify2/data/" + uuid + ".yml");
			FileConfiguration userData = YamlConfiguration.loadConfiguration(userFile);
			userData.set("nickname", dto.getNickname());
			userData.set("naverId", dto.getNaverId());
			userData.set("time", dto.getTime());
			try {
				userData.save(userFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static List<String> getMessage(Node node) {
		return config.getStringList(node.getPath());
	}
	
	public static String getCafeURL() {
		return config.getString("cafe-url");
	}
	
	public enum Node {
		PrivacyMessage("message.privacy-message", Arrays.asList(
				"&c이 시스템은 사용자의 카페 가입 여부를 확인 등 여러 서비스를 위해 개인정보(네이버 아이디)을 수집, 저장 및 이용합니다.",
				"&c/카페인증 <네이버 ID> 명령어로 인증을 시작할 수 있습니다. (진행 시 개인정보 활용에 동의한 것으로 간주합니다)")),
		AlreadyComplete("message.already-complete", Arrays.asList("이미 인증을 완료하셨습니다!")),
		AlreadyProcess("message.already-process", Arrays.asList("이미 인증이 진행중입니다. 게시글 제목 예시: verify:{code}")),
		Processing("message.processing", Arrays.asList("&a확인 중...")),
		Success("message.success", Arrays.asList("&a인증에 성공했습니다!")),
		Fail("message.fail", Arrays.asList("&c인증에 실패했습니다")),
		Help("message.help", Arrays.asList("&a/카페인증 <네이버 ID> &f명령어로 인증을 시작할 수 있습니다")),
		Permission("message.permission", Arrays.asList("&c권한이 없습니다!")),
		Start("message.start", Arrays.asList(
				"인증을 시작합니다.",
				"카페에 접속 후 인증 게시판에 verify:{code} 라는 제목으로 게시글을 작성한 뒤",
				"&a/카페인증 &f명령어를 사용해주세요"));
		
		
		private String path;
		private List<String> firstValue;
		
		private Node(String path, List<String> firstValue) {
			this.path = path;
			this.firstValue = firstValue;
		}
		
		public String getPath() {
			return this.path;
		}
		
		public List<String> getFirstValue() {
			return this.firstValue;
		}
	}
}
