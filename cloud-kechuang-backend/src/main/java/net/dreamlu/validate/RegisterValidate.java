package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;

/**
 * 注册校验器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年7月30日 下午8:24:31
 */
public class RegisterValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
//		validateRequired("userId", "msg", "请输入您的账号");
//		validateString("userId", 6, 24, "msg", "请输入6~24位的账号");
//		
//		validateRequired("userName", "msg", "请输入您的昵称");
//		validateString("userName", 1, 24, "msg", "请输入1~24位的昵称");
		
//		validateRequired("userIcon", "msg", "请上传您的头像");
//		validateUrl("userIcon","msg", "上传头像信息有误请核对");
		
		validateRequired("email", "msg", "请输入您的邮箱");
		validateEmail("email", "msg", "请检查您的邮箱");

		validateRequired("phone", "msg", "请输入您的手机号");
		validateRegex("phone", RegexUtils.PHONE, "phone", "请检查您的手机号");
	
		validateRequired("password", "msg", "请输入您的密码");
		validateString("password", 6, 16, "msg", "请输入6~16位的密码");
		
		

//		validateRequired("imgCode", "message", "请输入验证码");
//		validateCaptcha("imgCode", "message", "验证码错误");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
