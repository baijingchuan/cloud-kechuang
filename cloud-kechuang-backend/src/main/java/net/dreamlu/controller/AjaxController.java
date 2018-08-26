package net.dreamlu.controller;

import net.dreamlu.common.WebUtils;
import net.dreamlu.model.TUser;
import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.LoginValidate;
import net.dreamlu.validate.RegisterValidate;
import net.dreamlu.vo.AjaxResult;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

/**
 * ajax控制器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年7月25日 下午5:37:21
 */
public class AjaxController extends Controller {

	AjaxResult<TUser> ajaxResult = new AjaxResult<TUser>();
	AjaxResult<String> registerResult = new AjaxResult<String>();
	AjaxResult<JSONObject> jsonResult = new AjaxResult<JSONObject>();
	
	/**
	 * jsonp test
	 * 
	 * $.getJSON("http://localhost:8080/ajax/test?callback=?", function(data) {alert(data)})
	 * 
	 */
	public void test() {
		String callback = getPara("callback");
		
		String json = JsonKit.toJson(true);
		
		String text = callback + "(" + json + ")";
		renderText(text);
	}

	/**
	 * 注册表单提交
	 */
	@Before(RegisterValidate.class)
	public void register() {
//		String userId = getPara("userId");
//		String userName = getPara("userName");
		String userIcon = getPara("userIcon");
		String email = getPara("email");
		String phone = getPara("phone");
		String password = getPara("password");
		TUser user = new TUser();
		
//		 判断账号是否存在
//		user = TUser.dao.findByUserId(userId);
//		if (null != user) {
//			ajaxResult.addError("该账号已经注册过，请返回登录或者使用新账号");
//			renderJson(ajaxResult);
//			return;
//		}
				
		// 判断邮箱是否存在
		user = TUser.dao.findByEmail(email);
		if (null != user) {
			String emailActive = user.get("email_active");
			if(emailActive.equals("1")){
				registerResult.addError("1000","该邮箱已经注册过，请返回登录或者使用新邮箱地址");
				renderJson(registerResult);
				return;
			}
		}
		
		// 判断手机号是否存在
		user = TUser.dao.findByPhone(phone);
		
		if (null != user) {
			String phoneActive = user.get("phone_active");
			if(phoneActive.equals("1")){
				registerResult.addError("1001","该手机号已经注册过，请返回登录或者使用新手机号");
				renderJson(registerResult);
				return;
			}
		}
		
		user = new TUser();
		password = HashKit.md5(password)+"a11548be29b56c135f272e8b08333e49";
//		user.set("user_id", userId);
//		user.set("user_name", userName);
		user.set("user_icon", userIcon);
		user.set("password", password);
		user.set("email", email);
		user.set("phone", phone);
		user.set("create_time", System.currentTimeMillis());
		user.set("update_time", null);
		
		boolean temp = user.save();

		if (!temp) {
			registerResult.addError("1002","注册失败，请稍后再试");
		}
		registerResult.success("1003", "", "注册成功");
		renderJson(registerResult);
	}

	/**
	 * 登录表单提交
	 */
	@Before(LoginValidate.class)
	public void session() {
		String userId = getPara("userId");
		String password = getPara("password");
		String remember = getPara("remember", "0");
		TUser user = new TUser();
		
		if(RegexUtils.match(RegexUtils.EMAIL, userId)){
			user = TUser.dao.findByEmail(userId);
			if(user == null){
				user = TUser.dao.findByUserId(userId);
				if(user == null){
					renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
				}else{
					password = HashKit.md5(password);
					// 比较密码
					String oldPwd = user.get("password");
					oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
					if (!oldPwd.equals(password)) {
						renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
						return;
					}
					renderJson(ajaxResult.success("1005", user, "登陆成功"));
				}
			}else{
				password = HashKit.md5(password);
				// 比较密码
				String oldPwd = user.get("password");
				oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
				if (!oldPwd.equals(password)) {
					renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
					return;
				}
				renderJson(ajaxResult.success("1005", user, "登陆成功"));
			}
		}else if (RegexUtils.match(RegexUtils.PHONE, userId)) {
			user = TUser.dao.findByPhone(userId);
			if(user == null){
				user = TUser.dao.findByUserId(userId);
				if(user == null){
					renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
				}else{
					password = HashKit.md5(password);
					// 比较密码
					String oldPwd = user.get("password");
					oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
					if (!oldPwd.equals(password)) {
						renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
						return;
					}
					renderJson(ajaxResult.success("1005", user, "登陆成功"));
				}
			}else{
				password = HashKit.md5(password);
				// 比较密码
				String oldPwd = user.get("password");
				oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
				if (!oldPwd.equals(password)) {
					renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
					return;
				}
				renderJson(ajaxResult.success("1005", user, "登陆成功"));
			}
		}else {
			user = TUser.dao.findByUserId(userId);
			if(user == null){
				renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
			}else{
				password = HashKit.md5(password);
				// 比较密码
				String oldPwd = user.get("password");
				oldPwd = oldPwd.replace("a11548be29b56c135f272e8b08333e49", "");
				if (!oldPwd.equals(password)) {
					renderJson(ajaxResult.addError("1004", "您的用户名或者密码不正确"));
					return;
				}
				renderJson(ajaxResult.success("1005", user, "登陆成功"));
			}
		}
			
		if(user != null){
			setSessionAttr("user", user);
			WebUtils.loginUser(this, user, "1".equals(remember));
		}
		// 记住密码推后讲解
		renderJson(ajaxResult);
	}
	
	/**
	 * 获取用户列表（用作首页用户直播列表） create by ycj 2017.09.22
	 */
	public void userlist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<TUser> page = TUser.dao.paginate(pageNumber, pageSize);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(jsonResult.addError("find user list fail"));
			return;
		}
		jsonResult.success(json,"");
		renderJson(jsonResult);
	}
	
	/**
	 * 根据用户userId获取用户信息（用作查看个人信息） create by ycj 2017.09.22
	 */
	public void findUserByUserId() {

		String userId = getPara("userId");
		TUser user = TUser.dao.findByUserId(userId);
		if(user == null){
			renderJson(ajaxResult.addError("find user fail"));
			return;
		}
		renderJson(ajaxResult.success(user,""));
	}

}
