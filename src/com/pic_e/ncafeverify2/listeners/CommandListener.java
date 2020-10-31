package com.pic_e.ncafeverify2.listeners;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.pic_e.ncafeverify2.NCafeVerify2;
import com.pic_e.ncafeverify2.dto.Entry;
import com.pic_e.ncafeverify2.dto.VerifyDTO;
import com.pic_e.ncafeverify2.event.VerifySuccessEvent;
import com.pic_e.ncafeverify2.utils.Config;
import com.pic_e.ncafeverify2.utils.Util;
import com.pic_e.ncafeverify2.utils.Verify;
import com.pic_e.ncafeverify2.utils.Config.Node;

public class CommandListener{
	public static class User implements CommandExecutor{
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length > 0) {
					if(Config.complete.containsKey(p.getUniqueId())) {
						Util.sendList(Config.getMessage(Node.AlreadyComplete), p);
					}else if(Verify.process.containsKey(p.getUniqueId())){
						Util.sendList(Util.makeList(Config.getMessage(Node.AlreadyProcess), new Entry("\\{code\\}", Verify.process.get(p.getUniqueId()).getCode())), p);
					}else {
						if(p.hasMetadata("privacy")) {
							Verify.start(p, args[0]);
						}else {
							p.setMetadata("privacy", new FixedMetadataValue(NCafeVerify2.getInstance(), "ok"));
							Util.sendList(Config.getMessage(Node.PrivacyMessage), p);
						}
					}
				}else {
					if(Verify.process.containsKey(p.getUniqueId())) {
						Bukkit.getScheduler().runTaskAsynchronously(NCafeVerify2.getInstance(), ()->{
							VerifyDTO dto = Verify.process.get(p.getUniqueId());
							Util.sendList(Config.getMessage(Node.Processing), p);
							if(Verify.confirm(dto)) {
								Verify.process.remove(p.getUniqueId());
								dto.setTime(new Date());
								Config.complete.put(p.getUniqueId(), dto);
								VerifySuccessEvent cafeVerifyEvent = new VerifySuccessEvent(p);
								Bukkit.getPluginManager().callEvent(cafeVerifyEvent);
								if(cafeVerifyEvent.isCancelled()) {
									Config.complete.remove(p.getUniqueId());
								}else Util.sendList(Config.getMessage(Node.Success), p);
							}else {
								Util.sendList(Config.getMessage(Node.Fail), p);
							}
						});
					}else if(Config.complete.containsKey(p.getUniqueId())){
						Util.sendList(Config.getMessage(Node.AlreadyComplete), p);
					}else {
						Util.sendList(Config.getMessage(Node.Help), p);
					}
				}
			}
			return false;
		}
	}
	
	public static class Admin implements CommandExecutor {
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.isOp()) {
					if(args.length > 0) {
						if(args[0].equals("확인")) {
							if(args.length == 2) {
								Player target = Bukkit.getPlayer(args[1]);
								if(target == null) {
									Bukkit.getScheduler().runTaskAsynchronously(NCafeVerify2.getInstance(), ()->{
										for(Map.Entry<UUID, VerifyDTO> entry : Config.complete.entrySet()) {
											VerifyDTO dto = entry.getValue();
											if(dto.nameCompare(args[1])) {
												Util.sendList(Arrays.asList(
														"&c" + args[1] + "님의 인증 정보",
														"&c >> 인증 시각: " + dto.getTime(),
														"&c >> 네이버 아이디: " + dto.getNaverId()
														), p);
												return;
											}
										}
										p.sendMessage(Util.color("&c" + args[1] + "님은 인증을 완료하지 않았습니다."));
									});
								}else {
									if(Config.complete.containsKey(target.getUniqueId())) {
										VerifyDTO dto = Config.complete.get(p.getUniqueId());
											Util.sendList(Arrays.asList(
													"&c" + args[1] + "님의 인증 정보",
													"&c >> 인증 시각: " + dto.getTime(),
													"&c >> 네이버 아이디: " + dto.getNaverId()
												), p);
									}else {
										p.sendMessage(Util.color("&c" + args[1] + "님은 인증을 완료하지 않았습니다."));
									}
								}
							}else p.sendMessage(Util.color("&c/인증관리 확인 <닉네임> :: <닉네임> 에 해당하는 유저의 인증 기록을 확인합니다."));
						}
//						else if(args[0].equals("취소")) {
//							if(args.length == 2) {
//								Player target = Bukkit.getPlayer(args[1]);
//								if(target == null) {
//									Bukkit.getScheduler().runTaskAsynchronously(NCafeVerify2.getInstance(), ()->{
//										for(Map.Entry<UUID, VerifyDTO> entry : Config.complete.entrySet()) {
//											VerifyDTO dto = entry.getValue();
//											if(dto.nameCompare(args[1])) {
//												Config.complete.remove(entry.getKey());
//												p.sendMessage(Util.color("&c" + args[1] + "님의 인증 기록을 성공적으로 삭제했습니다."));
//												return;
//											}
//										}
//										p.sendMessage(Util.color("&c" + args[1] + "님은 인증을 완료하지 않았습니다."));
//									});
//								}else {
//									if(Config.complete.containsKey(target.getUniqueId())) {
//										Config.complete.remove(target.getUniqueId());
//										p.sendMessage(Util.color("&c" + args[1] + "님의 인증 기록을 성공적으로 삭제했습니다."));
//									}else {
//										p.sendMessage(Util.color("&c" + args[1] + "님은 인증을 완료하지 않았습니다."));
//									}
//								}
//							}else p.sendMessage(Util.color("&c/인증관리 취소 <닉네임> :: <닉네임> 에 해당하는 유저의 인증을 취소합니다."));
//						}
						else if(args[0].equals("리로드")){
							try{
								Config.loadConfig();
								p.sendMessage(Util.color("&c성공적으로 리로드를 완료했습니다."));
							} catch (Exception e) {
								e.printStackTrace();
								p.sendMessage(Util.color("&c리로드를 실패했습니다. 원인: " + e.getMessage()));
								p.sendMessage(Util.color("&c(자세한 내용은 콘솔을 확인해 주세요)"));
							}
						}else {
							Util.sendList(Arrays.asList(
									"&c/인증관리 확인 <닉네임> :: <닉네임> 에 해당하는 유저의 인증 기록을 확인합니다.",
									//"&c/인증관리 취소 <닉네임> :: <닉네임> 에 해당하는 유저의 인증을 취소합니다.",
									"&c/인증관리 리로드 :: 설정파일을 다시 로드합니다."
									), p);
						}
					}else {
						Util.sendList(Arrays.asList(
								"&c/인증관리 확인 <닉네임> :: <닉네임> 에 해당하는 유저의 인증 기록을 확인합니다.",
								//"&c/인증관리 취소 <닉네임> :: <닉네임> 에 해당하는 유저의 인증을 취소합니다.",
								"&c/인증관리 리로드 :: 설정파일을 다시 로드합니다."
								), p);
					}
				}else {
					Util.sendList(Config.getMessage(Node.Permission), p);
				}
			}
			return false;
		}
	}
}
