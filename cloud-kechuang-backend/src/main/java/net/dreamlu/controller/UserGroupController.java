package net.dreamlu.controller;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.UserGroup;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.validate.AddUserGroupValidate;
import net.dreamlu.validate.GroupNameValidate;
import net.dreamlu.validate.SearchUserGroupValidate;
import net.dreamlu.validate.UpdateUserGroupValidate;
import net.dreamlu.vo.AjaxResult;




/**
 * 
 * @ClassName: UserGroupController 
 * @Description: 群组控制器
 * @author ycj
 * @date 2017年10月7日 下午7:02:22 
 *
 */

public class UserGroupController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();

	

	/**
	 * 添加群组
	 */
	@Before(AddUserGroupValidate.class)
	public void addUserGroup() {
		String groupName = getPara("groupName");
		String groupDescribe = getPara("groupDescribe");
		String groupIcon = getPara("groupIcon");
		String address = getPara("address");
		String groupClassificationId = getPara("groupClassificationId");
		String remark = getPara("remark");
		UserGroup userGroup = new UserGroup();
		userGroup = UserGroup.dao.findByGroupName(groupName);
		if (userGroup != null)  {
			ajaxResult.addError( "群名称已被使用，请重新输入群名称");
			renderJson(ajaxResult);
			return;
		}
		
		userGroup = new UserGroup();
		userGroup.set("group_name", groupName);
		userGroup.set("group_describe", groupDescribe);
		userGroup.set("group_icon", groupIcon);
		userGroup.set("address", address);
		userGroup.set("group_classification_id", groupClassificationId);
		userGroup.set("create_time", System.currentTimeMillis());
		userGroup.set("remark", remark);
		
		boolean temp = userGroup.save();
		if (!temp) {
			ajaxResult.addError("群组添加失败，请稍后再试");
		}else{
			
			ajaxResult.success(ModeUtils.getUserGroupJson(userGroup), "群组添加成功");
		}
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找群     by ycj 20171007
	 */

	@Before(SearchUserGroupValidate.class)
	public void searchUserGroup() {
		String groupId = getPara("groupId");
		UserGroup userGroup = new UserGroup();
		userGroup = UserGroup.dao.findById(groupId);
		if (userGroup == null) {
			ajaxResult.addError(" find userGroup by groupId fail");
		} else {
			ajaxResult.success(ModeUtils.getUserGroupJson(userGroup), "OK");
		}
		

		renderJson(ajaxResult);
	}
	
	/**
	 * 根据groupName模糊查找群组     by ycj 20171007
	 */

	@Before(GroupNameValidate.class)
	public void fuzzySearchByGroupName() {
		String groupName = getPara("groupName");
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<UserGroup> page = UserGroup.dao.fuzzySearchByGroupName(pageNumber, pageSize, groupName);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find gambit list fail"));
			return;
		}
		Page<JSONObject > newpage = ModeUtils.getUserGroupPage(page);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据groupName查找群组     by ycj 20171007
	 */
	@Before(GroupNameValidate.class)
	public void findUserGroupByGroupName() {
		String groupName = getPara("groupName");
		UserGroup userGroup = new UserGroup();
		userGroup = UserGroup.dao.findByGroupName(groupName);
		if (userGroup == null) {
			ajaxResult.addError(" find userGroup by groupName fail");
		} else {
			ajaxResult.success(ModeUtils.getUserGroupJson(userGroup), "OK");
		}
		
		
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 检查groupName是否重复     by ycj 20171007
	 */
	@Before(GroupNameValidate.class)
	public void checkGroupName() {
		String groupName = getPara("groupName");
		UserGroup userGroup = new UserGroup();
		userGroup = UserGroup.dao.findByGroupName(groupName);
		if (userGroup == null) {
			ajaxResult.success(null, "群名称可以使用");
		} else {
			ajaxResult.addError( "群名称已被使用，请重新输入群名称");
		}
		
		
		renderJson(ajaxResult);
	}

	
	
	/**
	 * 修改群组   by ycj 20171007
	 */
	@Before(UpdateUserGroupValidate.class)
	public void updateUserGroup() {
		String groupId = getPara("groupId");
		String groupName = getPara("groupName");
		String groupDescribe = getPara("groupDescribe");
		String groupIcon = getPara("groupIcon");
		String address = getPara("address");
		String remark = getPara("remark");
		UserGroup thisUserGroup = new UserGroup();
		UserGroup otherUserGroup = new UserGroup();
		
		
		

		
		thisUserGroup = UserGroup.dao.findById(groupId);
		if(thisUserGroup==null){
			ajaxResult.addError("您修改的群组不存在或被删除");
		}else {
			
			otherUserGroup = UserGroup.dao.findByGroupName(groupName);
			if (otherUserGroup != null && otherUserGroup.get("group_id") != thisUserGroup.get("group_id"))  {
				ajaxResult.addError( "群名称已被使用，请重新输入群名称");
				renderJson(ajaxResult);
				return;
			}
			
			if (StringUtils.isNotEmpty(remark)) {
				thisUserGroup.set("remark", remark);
			}
			thisUserGroup.set("group_name", groupName);
			thisUserGroup.set("group_describe", groupDescribe);
			if (StringUtils.isNotEmpty(groupIcon)) {
				thisUserGroup.set("group_icon", groupIcon);
			}
			thisUserGroup.set("address", address);
			thisUserGroup.set("update_time", System.currentTimeMillis());
			
			boolean temp = thisUserGroup.update();
			if (!temp) {
				ajaxResult.addError("群组修改失败，请稍后再试");
			}else{
				
				ajaxResult.success(ModeUtils.getUserGroupJson(thisUserGroup), "群组修改成功");
			}
			
		}
		
		
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 删除群组  by ycj 20171005
	 */
	@Before(SearchUserGroupValidate.class)
	public void delUserGroupById() {
		String groupId = getPara("groupId");
		UserGroup userGroup = new UserGroup();
		
				boolean temp = userGroup.deleteById(groupId);

				if (!temp) {
					ajaxResult.addError("删除失败");
				}else{
					ajaxResult.success(null, "删除成功");
				}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取群组列表 create by ycj 2017.10.07
	 */
	public void userGrouplist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<UserGroup> page = UserGroup.dao.paginate(pageNumber, pageSize);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find gambit list fail"));
			return;
		}
		
		Page<JSONObject > newpage = ModeUtils.getUserGroupPage(page);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	
    
}
