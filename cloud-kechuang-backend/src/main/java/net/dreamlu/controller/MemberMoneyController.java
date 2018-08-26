package net.dreamlu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import net.dreamlu.model.MemberMoney;
import net.dreamlu.model.TUser;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.validate.SetTransactionPasswordValidate;
import net.dreamlu.validate.UserIdValidate;
import net.dreamlu.vo.AjaxResult;






/**
 * 
 * @ClassName: MemberMoneyController 
 * @Description: 用户钱包控制器
 * @author ycj
 * @date 2017年11月7日 下午5:21:41 
 *
 */

public class MemberMoneyController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 查看我的钱包
	 */
	@Before(UserIdValidate.class)
	public void getMyWallet() {
		long userId = getParaToLong("userId");
		String transactionPassword = getPara("transactionPassword");//交易密码
		TUser user = TUser.dao.findById(userId);
		if(user == null){
			ajaxResult.addError( "userId = '"+userId+"' 对应的用户不存在");
			renderJson(ajaxResult);
			return;
		}
		MemberMoney memberMoney = MemberMoney.dao.findById(userId);
		if(memberMoney == null){
			memberMoney = new MemberMoney();
			memberMoney.set("user_id", userId);
			memberMoney.set("total_income", 0.00);
			memberMoney.set("withdraw", 0.00);
			memberMoney.set("transaction_password", "");
			
			boolean temp = memberMoney.save();
			if (!temp) {
				ajaxResult.addError("文章评论失败，请稍后再试");
			}else{
				logger.info("memberMoney={}",memberMoney);
				
				ajaxResult.success(ModeUtils.getMemberMoneyJson(memberMoney), "获取钱包成功");
				
			}
		}else if(StringUtils.isEmpty(memberMoney.getStr("transaction_password"))){
			ajaxResult.success(ModeUtils.getMemberMoneyJson(memberMoney), "获取钱包成功");
		}else if(StringUtils.isEmpty(transactionPassword)){
			ajaxResult.addError( "您已设置交易密码，请输入交易密码");
			renderJson(ajaxResult);
			return;
		}else if(!memberMoney.getStr("transaction_password").equals(transactionPassword)){
			ajaxResult.addError( "您输入的交易密码不正确，请重新输入");
			renderJson(ajaxResult);
			return;
		}else{
			ajaxResult.success(ModeUtils.getMemberMoneyJson(memberMoney), "获取钱包成功");
		}
		
		
		
		renderJson(ajaxResult);
	}
	
	

	/**
	 * 设置交易密码   by ycj 20171107
	 */
	@Before(SetTransactionPasswordValidate.class)
	public void setTransactionPassword() {
		long userId = getParaToLong("userId");
		String transactionPassword = getPara("transactionPassword");//交易密码
		TUser user = TUser.dao.findById(userId);
		if(user == null){
			ajaxResult.addError( "userId = '"+userId+"' 对应的用户不存在");
			renderJson(ajaxResult);
			return;
		}
		MemberMoney memberMoney = MemberMoney.dao.findById(userId);
		String errMsg = "钱包交易密码设置失败，请稍后再试";
		String successMsg = "钱包交易密码设置成功";
		if(memberMoney == null){
			memberMoney = new MemberMoney();
			memberMoney.set("user_id", userId);
			memberMoney.set("total_income", 0.00);
			memberMoney.set("withdraw", 0.00);
			memberMoney.set("transaction_password", transactionPassword);
			
			boolean temp = memberMoney.save();
			if (!temp) {
				ajaxResult.addError(errMsg);
			}else{
				logger.info("memberMoney={}",memberMoney);
				
				//ajaxResult.success(ModeUtils.getMemberMoneyJson(memberMoney), "钱包交易密码设置成功");
				ajaxResult.success(ModeUtils.successCode, new JSONObject(), successMsg);
				
			}
		}else{
			int delRows = memberMoney.setTransactionPassword(transactionPassword, userId);
			if (delRows == 0) {
				ajaxResult.addError(ModeUtils.errorCode, errMsg);
			}else{
				
				ajaxResult.success(ModeUtils.successCode, new JSONObject(), successMsg);
				
				
			}
		}
		
	
			
		
			
		

		renderJson(ajaxResult);
	}
	
	
	
}
