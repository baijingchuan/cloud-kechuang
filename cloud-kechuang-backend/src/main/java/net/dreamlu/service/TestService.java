package net.dreamlu.service;

import com.jfinal.aop.Before;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sun.mail.handlers.message_rfc822;

import net.dreamlu.model.TUser;

public class TestService {

	// 仅作视频演示
	@Before(Tx.class)
	public TUser saveUser() {
		TUser u = new TUser();
		u.set("nickName", "JFinal-test");
		u.set("email", "test@dreamlu.net");
		u.set("password", HashKit.md5("123456"));
		u.set("role", 0);
		u.save();
		return u;
	}
	
	public static void main(String[] args) {
	}
}
