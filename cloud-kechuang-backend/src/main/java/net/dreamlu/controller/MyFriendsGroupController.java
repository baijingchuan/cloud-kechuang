package net.dreamlu.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.MyFriends;
import net.dreamlu.model.MyFriendsGroup;
import net.dreamlu.validate.AddMyFriendsGroupValidate;
import net.dreamlu.validate.MyFriendsGroupNameValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UpdateMyFriendsGroupValidate;
import net.dreamlu.validate.UserIdValidate;
import net.dreamlu.vo.AjaxResult;



/**
 * 
 * @ClassName: MyFriendsGroupController 
 * @Description: 我的好友分组控制器
 * @author ycj
 * @date 2017年10月10日 下午8:59:39 
 *
 */

public class MyFriendsGroupController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 添加好友分组
	 */
	@Before(AddMyFriendsGroupValidate.class)
	public void addMyFriendsGroup() {
		String name = getPara("name");
		String remark = getPara("remark");
		long userId = getParaToLong("userId");
		MyFriendsGroup myFriendsGroup = new MyFriendsGroup();
		myFriendsGroup = MyFriendsGroup.dao.findByNameAndUserId(name, userId);
		if (myFriendsGroup != null)  {
			ajaxResult.addError( "好友分组名称已被使用，请重新输入好友分组名称");
			renderJson(ajaxResult);
			return;
		}
		
		myFriendsGroup = new MyFriendsGroup();
		myFriendsGroup.set("name", name);
		myFriendsGroup.set("status", 0);
		myFriendsGroup.set("remark", remark);
		
		boolean temp = myFriendsGroup.save();
		if (!temp) {
			ajaxResult.addError("好友分组添加失败，请稍后再试");
		}else{
			
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(myFriendsGroup)), "好友分组添加成功");
		}
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找好友分组     by ycj 20171010
	 */

	@Before(SearchObjValidate.class)
	public void searchMyFriendsGroup() {
		String id = getPara("id");
		MyFriendsGroup myFriendsGroup = new MyFriendsGroup();
		myFriendsGroup = MyFriendsGroup.dao.findById(id);
		if (myFriendsGroup == null) {
			ajaxResult.addError(" find MyFriendsGroup by id fail");
		} else {
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(myFriendsGroup)), "OK");
		}
		

		renderJson(ajaxResult);
	}
	
	
	
	
	
	
	
	/**
	 * 检查name是否重复     by ycj 20171010
	 */
	@Before(MyFriendsGroupNameValidate.class)
	public void checkname() {
		String name = getPara("name");
		long userId = getParaToLong("userId");
		MyFriendsGroup myFriendsGroup = new MyFriendsGroup();
		myFriendsGroup = MyFriendsGroup.dao.findByNameAndUserId(name, userId);
		if (myFriendsGroup == null) {
			ajaxResult.success(null, "好友分组名称可以使用");
		} else {
			ajaxResult.addError( "好友分组名称已被使用，请重新输入好友分组名称");
		}
		
		
		renderJson(ajaxResult);
	}

	
	
	/**
	 * 修改好友分组   by ycj 20171010
	 */
	@Before(UpdateMyFriendsGroupValidate.class)
	public void updateMyFriendsGroup() {
		String id = getPara("id");
		String name = getPara("name");
		String remark = getPara("remark");
		MyFriendsGroup thisMyFriendsGroup = new MyFriendsGroup();
		MyFriendsGroup otherMyFriendsGroup = new MyFriendsGroup();
		
		thisMyFriendsGroup = MyFriendsGroup.dao.findById(id);
		if(thisMyFriendsGroup==null){
			ajaxResult.addError("您修改的好友分组不存在或被删除");
		}else {
			Long userId = thisMyFriendsGroup.get("userId");
			otherMyFriendsGroup = MyFriendsGroup.dao.findByNameAndUserId(name, userId);
			if (otherMyFriendsGroup != null && otherMyFriendsGroup.get("id") != thisMyFriendsGroup.get("id"))  {
				ajaxResult.addError( "好友分组名称已被使用，请重新输入好友分组名称");
				renderJson(ajaxResult);
				return;
			}
			
			if (StringUtils.isNotEmpty(remark)) {
				thisMyFriendsGroup.set("remark", remark);
			}
			thisMyFriendsGroup.set("name", name);
			
			
			boolean temp = thisMyFriendsGroup.update();
			if (!temp) {
				ajaxResult.addError("好友分组修改失败，请稍后再试");
			}else{
				
				ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(thisMyFriendsGroup)), "好友分组修改成功");
			}
			
		}
		
		
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 删除好友分组  by ycj 20171005
	 */
	@Before(SearchObjValidate.class)
	public void delMyFriendsGroupById() {
		int id = getParaToInt("id");
		MyFriendsGroup myFriendsGroup = new MyFriendsGroup();
		
		myFriendsGroup = MyFriendsGroup.dao.findById(id);

		if (myFriendsGroup!=null){
			logger.info("myFriendsGroup.status={}",myFriendsGroup.get("status"));
			
		}
		if (myFriendsGroup!=null && "1".equals(myFriendsGroup.get("status")) ) {
			ajaxResult.addError("该分组为系统分组，不能删除");
		} else {

			
			myFriendsGroup = new MyFriendsGroup();
			boolean temp = myFriendsGroup.deleteById(id);
			
			if (!temp) {
				ajaxResult.addError("删除失败");
			}else{
				//删除该分组后，要把改分组下的好友更新为“我的好友”默认分组  update by ycj 20171014
				int updateMyFriendsRows = MyFriends.dao.updateMyFriends(id);
				JSONObject json = new JSONObject();
				json.put("updateMyFriendsRows", updateMyFriendsRows);
				ajaxResult.success(json, "删除成功");
			}
		}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取好友分组列表 create by ycj 2017.10.07
	 */
	@Before(UserIdValidate.class)
	public void myFriendsGrouplist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);
		long userId = getParaToLong("userId", 0L);

		Page<MyFriendsGroup> page = MyFriendsGroup.dao.paginate(pageNumber, pageSize, userId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find myFriends list fail"));
			return;
		}
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	
    
}
