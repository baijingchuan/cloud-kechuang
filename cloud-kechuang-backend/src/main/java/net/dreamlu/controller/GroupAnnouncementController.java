package net.dreamlu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.GroupAnnouncement;
import net.dreamlu.validate.FindGroupAnnouncementValidate;
import net.dreamlu.validate.GroupAnnouncementValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UpdateAnnouncementValidate;
import net.dreamlu.vo.AjaxResult;

/**
 * 
* @ClassName: GroupAnnouncementController 
* @Description: 群公告控制器 
* @author ycj
* @date 2017年9月29日 下午9:29:12 
*
 */

public class GroupAnnouncementController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();

	

	/**
	 * 发布公告
	 */
	@Before(GroupAnnouncementValidate.class)
	public void addGroupAnnouncement() {
		String title = getPara("title");
		String content = getPara("content");
		String createBy = getPara("createBy");
		GroupAnnouncement announcement = new GroupAnnouncement();
		
		
		announcement.set("title", title);
		announcement.set("content", content);
		announcement.set("read_count", 0);
		announcement.set("create_by", createBy);
		announcement.set("create_time", System.currentTimeMillis());
		
		boolean temp = announcement.save();
		if (!temp) {
			ajaxResult.addError("公告发布失败，请稍后再试");
		}

		

		ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(announcement)), "公告发布成功");
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找公告（用作修改公告前获取要修改的公告原来内容） by ycj 20171001
	 */

	@Before(SearchObjValidate.class)
	public void searchGroupAnnouncement() {
		String id = getPara("id");
		GroupAnnouncement announcement = new GroupAnnouncement();
		announcement = GroupAnnouncement.dao.findById(id);
		if (announcement == null) {
			ajaxResult.addError(" find announcement by id fail");
		} else {
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(announcement)), "OK");
		}
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 修改公告
	 */
	@Before(UpdateAnnouncementValidate.class)
	public void updateGroupAnnouncement() {
		String id = getPara("id");
		String title = getPara("title");
		String content = getPara("content");
		String createBy = getPara("createBy");
		GroupAnnouncement announcement = new GroupAnnouncement();
		announcement = GroupAnnouncement.dao.findById(id);
		if(announcement==null){
			ajaxResult.addError("您查看的公告不存在或被删除");
		}else {
			if (title.equals(announcement.get("title"))&&content.equals(announcement.get("content"))) {
				ajaxResult.addError("公告标题和公告内容未做改动，请重新编辑");
			} else {
				announcement.set("title", title);
				announcement.set("content", content);
				announcement.set("read_count", 0);
				announcement.set("create_by", createBy);
				announcement.set("readers", null);
				announcement.set("update_time", System.currentTimeMillis());
				
				boolean temp = announcement.update();
				if (!temp) {
					ajaxResult.addError("公告修改失败，请稍后再试");
				}else{
					
					ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(announcement)), "公告修改成功");
				}
			}
		}
		
		
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 删除公告 by ycj 20171001
	 */
	@Before(SearchObjValidate.class)
	public void delGroupAnnouncementById() {
		String id = getPara("id");
		GroupAnnouncement announcement = new GroupAnnouncement();
		
				boolean temp = announcement.deleteById(id);

				if (!temp) {
					ajaxResult.addError("删除失败");
				}else{
					ajaxResult.success(null, "删除成功");
				}
			
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据公告id查看公告信息
	 */
	@Before(FindGroupAnnouncementValidate.class)
	public void findGroupAnnouncementById() {
		String userId = getPara("userId");
		String announcementId = getPara("announcementId");
		GroupAnnouncement announcement = new GroupAnnouncement();
		announcement = GroupAnnouncement.dao.findById(announcementId);
		if(announcement==null){
			ajaxResult.addError("您查看的公告不存在或被删除");
		}else {
			String readers = announcement.get("readers");
			if(readers==null||(readers.indexOf(userId)==-1)){
				if (null == readers || "".equals(readers)) {
					readers = userId;
				} else {
					readers = readers + "," + userId;
				}
				announcement.set("readers", readers);
				announcement.set("read_count", readers.split(",").length);
				announcement.set("update_time", System.currentTimeMillis());
				
				boolean temp = announcement.update();

				if (!temp) {
					ajaxResult.addError("公告更新失败，请稍后再试");
				}
			}
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(announcement)), "OK");
		}
			
		renderJson(ajaxResult);
	}
	
	/**
	 * 获取公告列表 create by ycj 2017.09.29
	 */
	public void announcementlist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<GroupAnnouncement> page = GroupAnnouncement.dao.paginate(pageNumber, pageSize);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find announcement list fail"));
			return;
		}
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	

}
