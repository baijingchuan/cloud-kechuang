package net.dreamlu.controller;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.Gambit;
import net.dreamlu.model.GroupClassification;
import net.dreamlu.validate.AddGroupClassificationValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UpdateGroupClassificationValidate;
import net.dreamlu.vo.AjaxResult;



/**
 * 
 * @ClassName: GroupClassificationController 
 * @Description: 群分类控制器
 * @author ycj
 * @date 2017年10月5日 下午10:18:56 
 *
 */

public class GroupClassificationController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();

	

	/**
	 * 添加群分类
	 */
	@Before(AddGroupClassificationValidate.class)
	public void addGroupClassification() {
		String name = getPara("name");
		String remark = getPara("remark");
		GroupClassification groupClassification = new GroupClassification();
		
		
		groupClassification.set("name", name);
		groupClassification.set("remark", remark);
		
		boolean temp = groupClassification.save();
		if (!temp) {
			ajaxResult.addError("群分类添加失败，请稍后再试");
		}else{
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(groupClassification)), "群分类添加成功");
		}
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找分类     by ycj 20171005
	 */

	@Before(SearchObjValidate.class)
	public void searchGroupClassification() {
		String id = getPara("id");
		GroupClassification groupClassification = new GroupClassification();
		groupClassification = GroupClassification.dao.findById(id);
		if (groupClassification == null) {
			ajaxResult.addError(" find groupClassification by id fail");
		} else {
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(groupClassification)), "OK");
		}
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 修改群分类   by ycj 20171005
	 */
	@Before(UpdateGroupClassificationValidate.class)
	public void updateGroupClassification() {
		String id = getPara("id");
		String name = getPara("name");
		String remark = getPara("remark");
		GroupClassification groupClassification = new GroupClassification();
		groupClassification = GroupClassification.dao.findById(id);
		if(groupClassification==null){
			ajaxResult.addError("您修改的群分类不存在或被删除");
		}else {
			if ((StringUtils.isNotEmpty(remark)&&remark.equals(groupClassification.get("remark"))&&name.equals(groupClassification.get("name")))
					||(StringUtils.isEmpty(remark)&&name.equals(groupClassification.get("name")))) {
				ajaxResult.addError("群分类内容未做改动，请重新编辑");
			} else {
				if (StringUtils.isNotEmpty(remark)) {
					groupClassification.set("remark", remark);
				}
				groupClassification.set("name", name);
				
				boolean temp = groupClassification.update();
				if (!temp) {
					ajaxResult.addError("群分类修改失败，请稍后再试");
				}else{
					
					ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(groupClassification)), "群分类修改成功");
				}
			}
		}
		
		
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 删除群分类  by ycj 20171005
	 */
	@Before(SearchObjValidate.class)
	public void delGroupClassificationById() {
		String id = getPara("id");
		GroupClassification groupClassification = new GroupClassification();
		
				boolean temp = groupClassification.deleteById(id);

				if (!temp) {
					ajaxResult.addError("删除失败");
				}else{
					ajaxResult.success(null, "删除成功");
				}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取群分类列表 create by ycj 2017.10.05
	 */
	public void groupClassificationlist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<GroupClassification> page = GroupClassification.dao.paginate(pageNumber, pageSize);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find gambit list fail"));
			return;
		}
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	

}
