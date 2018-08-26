package net.dreamlu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.MyFriends;
import net.dreamlu.model.MyFriendsGroup;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.validate.AddMyFriendsValidate;
import net.dreamlu.validate.FriendsIdValidate;
import net.dreamlu.validate.MyFriendsListValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UpdateMyFriendsStatusObjValidate;
import net.dreamlu.vo.AjaxResult;



/**
 * 
 * @ClassName: MyFriendsController 
 * @Description: 我的好友控制器
 * @author ycj
 * @date 2017年10月13日 下午8:05:37 
 *
 */

public class MyFriendsController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 添加好友
	 */
	@Before(AddMyFriendsValidate.class)
	public void addMyFriends() {
		int hostId = getParaToInt("hostId");
		int friendId = getParaToInt("friendId");
		String friendRemark = getPara("friendRemark");
		int friendsGroupId = getParaToInt("friendsGroupId", 1);  //好友分组默认为 1----“我的好友”分组
		String verificationInformation = getPara("verificationInformation");
		MyFriends myFriends = new MyFriends();
		myFriends = MyFriends.dao.findByHostIdAndFriendId(hostId, friendId);
		if (myFriends == null)  {
			myFriends = new MyFriends();
			myFriends.set("host_id", hostId);
			myFriends.set("friend_id", friendId);
			myFriends.set("friend_remark", friendRemark);
			myFriends.set("verification_information", verificationInformation);
			myFriends.set("friends_group_id", friendsGroupId);
			myFriends.set("create_time", System.currentTimeMillis());
			myFriends.set("update_time", null);
			myFriends.set("status", "0");
			
			boolean temp = myFriends.save();
			if (!temp) {
				ajaxResult.addError("好友分组添加失败，请稍后再试");
			}else{
				
				//ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(myFriends)), "好友添加完成，等待对方同意");
				ajaxResult.success(ModeUtils.getMyFriendsJson(myFriends), "好友添加完成，等待对方同意");
			}
		}
		else if("1".equals(myFriends.get("status"))){
			ajaxResult.addError("已经是你的好友，不能重复添加");
		}
		else{
			//myFriends.set("host_id", hostId);
			//myFriends.set("friend_id", friendId);
			myFriends.set("friend_remark", friendRemark);
			myFriends.set("verification_information", verificationInformation);
			myFriends.set("friends_group_id", friendsGroupId);
			myFriends.set("update_time", System.currentTimeMillis());
			myFriends.set("status", "0");
			
			boolean temp = myFriends.update();
			if (!temp) {
				ajaxResult.addError("好友添加失败，请稍后再试");
			}else{
				
				ajaxResult.success(ModeUtils.getMyFriendsJson(myFriends), "好友添加完成，等待对方同意");
			}
		}
		
		
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找好友信息     by ycj 20171013
	 */

	@Before(SearchObjValidate.class)
	public void searchMyFriends() {
		String id = getPara("id");
		MyFriends myFriends = new MyFriends();
		myFriends = MyFriends.dao.findById(id);
		if (myFriends == null) {
			ajaxResult.addError(" find MyFriends by id fail");
		} else {
			ajaxResult.success(ModeUtils.getMyFriendsJson(myFriends), "OK");
		}
		

		renderJson(ajaxResult);
	}
	
	

	
	
	/**
	 * 修改好友信息（如好友备注信息、修改分组）   by ycj 20171013
	 */
	@Before(SearchObjValidate.class)
	public void updateMyFriends() {
		String id = getPara("id");
		String friendRemark = getPara("friendRemark");
		int friendsGroupId = getParaToInt("friendsGroupId",0);
		MyFriends myFriends = new MyFriends();
		
		myFriends = MyFriends.dao.findById(id);
		if(myFriends == null){
			ajaxResult.addError("您修改的好友不存在或被删除");
			renderJson(ajaxResult);
			return;
		}
		if (friendRemark == null && friendsGroupId == 0 ) {
			ajaxResult.addError("未输入任何修改参数");
			renderJson(ajaxResult);
			return;
		} else if (friendRemark !=null) {

			myFriends.set("friend_remark", friendRemark);
		} 
		if(friendsGroupId != 0){
			MyFriendsGroup myFriendsGroup  = MyFriendsGroup.dao.findById(friendsGroupId);//验证好友分组是否存在
			if(myFriendsGroup == null){
				ajaxResult.addError("系统出错，您分配的好友分组不存在或被删除");
				renderJson(ajaxResult);
				return;
			}
			myFriends.set("friends_group_id", friendsGroupId);
		}
			myFriends.set("update_time", System.currentTimeMillis());
			
			
		boolean temp = myFriends.update();
		if (!temp) {
			ajaxResult.addError("好友信息修改失败，请稍后再试");
		}else{
			
			ajaxResult.success(ModeUtils.getMyFriendsJson(myFriends), "好友信息修改成功");
		}
			
		

		renderJson(ajaxResult);
	}

	/**
	 * 修改好友状态（同意或拒绝陌生人的加好友申请）   by ycj 20171013
	 */
	@Before(UpdateMyFriendsStatusObjValidate.class)
	public void updateMyFriendsStatus() {
		String id = getPara("id");
		String status = getPara("status");
		MyFriends myFriends = new MyFriends();
		
		myFriends = MyFriends.dao.findById(id);
		if(myFriends == null){
			ajaxResult.addError("您的好友不存在或被删除");
			renderJson(ajaxResult);
			return;
		}else if(!"0".equals(myFriends.get("status"))){
			ajaxResult.addError("此好友申请已处理，请勿重复操作");
			renderJson(ajaxResult);
			return;
		}
		String msg = "";
		
		if("1".equals(status)){//当被添加好友接收添加好友者的申请时，被添加好友也要将添加者保持到自己的好友列表
			
			int hostId = myFriends.get("host_id");
			int friendId = myFriends.get("friend_id");
			String friendRemark = getPara("friendRemark");
			int friendsGroupId = getParaToInt("friendsGroupId", 1);  //好友分组默认为 1----“我的好友”分组
			MyFriends newMyFriends = MyFriends.dao.findByHostIdAndFriendId(friendId, hostId);//查找被添加好友的好友列表中此好友是否存在
			if(newMyFriends != null){
				
				ajaxResult.addError("此好友申请已处理，请勿重复操作");
				renderJson(ajaxResult);
				return;
			}
			newMyFriends = new MyFriends();
			newMyFriends.set("host_id", friendId);
			newMyFriends.set("friend_id", hostId);
			if(friendRemark != null){
				newMyFriends.set("friend_remark", friendRemark);
			}
			newMyFriends.set("friends_group_id", friendsGroupId);
			newMyFriends.set("create_time", System.currentTimeMillis());
			newMyFriends.set("status", "1");
			
			boolean temp = newMyFriends.save();
			if (!temp) {
				ajaxResult.addError("同意好友申请出错，请稍后再试");
				renderJson(ajaxResult);
				return;
			}
			msg = "已同意";
		}else {
			msg = "已拒绝";
		}
		
		
		myFriends.set("status", status);
	
		myFriends.set("update_time", System.currentTimeMillis());
			
			
		boolean temp = myFriends.update();
		if (!temp) {
			ajaxResult.addError("好友信息修改失败，请稍后再试");
		}else{
			
			ajaxResult.success(ModeUtils.getMyFriendsJson(myFriends), msg);
			
		}
			
		

		renderJson(ajaxResult);
	}
	
	
	/**
	 * 删除好友  by ycj 20171013
	 */
	@Before(SearchObjValidate.class)
	public void delMyFriendsById() {
		String id = getPara("id");
		MyFriends myFriends = new MyFriends();
		
		myFriends = MyFriends.dao.findById(id);
		

		if (myFriends!=null){
			int hostId = myFriends.get("host_id");
			int friendId = myFriends.get("friend_id");

			MyFriends newMyFriends = MyFriends.dao.findByHostIdAndFriendId(friendId, hostId);//查找自己在对方的好友列表中的信息，并进行删除
			if (newMyFriends!=null){
				boolean temp = newMyFriends.deleteById(newMyFriends.get("id"));
				
				if (!temp) {
					ajaxResult.addError("从对方好友列表中删除失败");
				}else{
					ajaxResult.success(null, "从对方好友列表中删除成功");
				}
			}
			
			
		}
		myFriends = new MyFriends();
		boolean temp = myFriends.deleteById(id);
		
		if (!temp) {
			ajaxResult.addError("删除失败");
		}else{
			ajaxResult.success(null, "删除成功");
		}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取好友列表 create by ycj 2017.10.13
	 */
	@Before(MyFriendsListValidate.class)
	public void MyFriendslist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		int hostId = getParaToInt("hostId", 0);
		int friendsGroupId = getParaToInt("friendsGroupId", 0);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<MyFriends> page = MyFriends.dao.paginate(pageNumber, pageSize, hostId, friendsGroupId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find myFriends list fail"));
			return;
		}
		
		Page<JSONObject > newpage = ModeUtils.getMyFriendsPage(page);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	/**
	 * 获取添加好友申请列表 create by ycj 2017.10.13
	 */
	@Before(FriendsIdValidate.class)
	public void MyFriendsAddApplylist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		int friendId = getParaToInt("friendId", 0);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<MyFriends> page = MyFriends.dao.AddApplyPaginate(pageNumber, pageSize, friendId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find myFriends list fail"));
			return;
		}
		Page<JSONObject > newpage = ModeUtils.getMyFriendsPage(page);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	
    
}
