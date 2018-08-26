package net.dreamlu.controller;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.Gambit;
import net.dreamlu.validate.AddGambitValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UpdateGambitValidate;
import net.dreamlu.vo.AjaxResult;


/**
 * 
 * @ClassName: GambitController 
 * @Description: 话题控制器
 * @author ycj
 * @date 2017年10月2日 下午9:41:55 
 *
 */

public class GambitController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();

	

	/**
	 * 发布话题
	 */
	@Before(AddGambitValidate.class)
	public void addGambit() {
		String img = getPara("img");
		String content = getPara("content");
		String createBy = getPara("createBy");
		Gambit gambit = new Gambit();
		
		
		gambit.set("img", img);
		gambit.set("content", content);
		gambit.set("create_by", createBy);
		gambit.set("create_time", System.currentTimeMillis());
		
		boolean temp = gambit.save();
		if (!temp) {
			ajaxResult.addError("话题发布失败，请稍后再试");
		}else{
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(gambit)), "话题发布成功");
		}
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找话题     by ycj 20171002
	 */

	@Before(SearchObjValidate.class)
	public void searchGambit() {
		String id = getPara("id");
		Gambit gambit = new Gambit();
		gambit = Gambit.dao.findById(id);
		if (gambit == null) {
			ajaxResult.addError(" find gambit by id fail");
		} else {
			ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(gambit)), "OK");
		}
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 修改话题   by ycj 20171002
	 */
	@Before(UpdateGambitValidate.class)
	public void updateGambit() {
		String id = getPara("id");
		String img = getPara("img");
		String content = getPara("content");
		String createBy = getPara("createBy");
		Gambit gambit = new Gambit();
		gambit = Gambit.dao.findById(id);
		if(gambit==null){
			ajaxResult.addError("您修改的话题不存在或被删除");
		}else {
			if ((StringUtils.isNotEmpty(img)&&img.equals(gambit.get("img"))&&content.equals(gambit.get("content")))
					||(StringUtils.isEmpty(img)&&content.equals(gambit.get("content")))) {
				ajaxResult.addError("话题内容未做改动，请重新编辑");
			} else {
				if (StringUtils.isNotEmpty(img)) {
					gambit.set("img", img);
				}
				gambit.set("content", content);
				gambit.set("create_by", createBy);
				gambit.set("update_time", System.currentTimeMillis());
				
				boolean temp = gambit.update();
				if (!temp) {
					ajaxResult.addError("话题修改失败，请稍后再试");
				}else{
					
					ajaxResult.success(JSONObject.parseObject(JsonKit.toJson(gambit)), "话题修改成功");
				}
			}
		}
		
		
		

		renderJson(ajaxResult);
	}

	
	
	/**
	 * 删除话题  by ycj 20171002
	 */
	@Before(SearchObjValidate.class)
	public void delGambitById() {
		String id = getPara("id");
		Gambit gambit = new Gambit();
		
				boolean temp = gambit.deleteById(id);

				if (!temp) {
					ajaxResult.addError("删除失败");
				}else{
					ajaxResult.success(null, "删除成功");
				}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取话题列表 create by ycj 2017.10.02
	 */
	public void gambitlist() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<Gambit> page = Gambit.dao.paginate(pageNumber, pageSize);
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
