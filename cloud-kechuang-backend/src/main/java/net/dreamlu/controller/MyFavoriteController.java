package net.dreamlu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.MyFavorite;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.validate.AddMyFavoriteValidate;
import net.dreamlu.validate.SearchObjValidate;
import net.dreamlu.validate.UserIdValidate;
import net.dreamlu.vo.AjaxResult;



/**
 * 
 * @ClassName: MyFavoriteController 
 * @Description: 我的收藏控制器
 * @author ycj
 * @date 2017年10月29日 下午8:04:36 
 *
 */

public class MyFavoriteController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();

	

	/**
	 * 添加收藏
	 */
	@Before(AddMyFavoriteValidate.class)
	public void addMyFavorite() {
		long userId = getParaToLong("userId");
		long publisherMemberId = getParaToLong("publisherMemberId");
		String title = getPara("title");
		String pic = getPara("pic");
		String favoriteDescribe = getPara("favoriteDescribe");
		String favoriteLabel = getPara("favoriteLabel");
		String link = getPara("link");
		MyFavorite myFavorite = new MyFavorite();
		myFavorite = MyFavorite.dao.findByLinkAndUserId(link, userId);
		if (myFavorite != null)  {
			ajaxResult.addError( "此文章已收藏");
			renderJson(ajaxResult);
			return;
		}
		
		myFavorite = new MyFavorite();
		
		myFavorite.set("user_id", userId);
		myFavorite.set("publisher_member_id", publisherMemberId);
		myFavorite.set("title", title);
		myFavorite.set("pic", pic);
		myFavorite.set("favorite_describe", favoriteDescribe);
		myFavorite.set("favorite_label", favoriteLabel);
		myFavorite.set("link", link);
		myFavorite.set("create_time", System.currentTimeMillis());
		
		boolean temp = myFavorite.save();
		if (!temp) {
			ajaxResult.addError("收藏失败，请稍后再试");
		}else{
			ajaxResult.success(ModeUtils.getMyFavoriteJson(myFavorite), "收藏成功");
		}
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找收藏信息     by ycj 20171029
	 */

	@Before(SearchObjValidate.class)
	public void searchMyFavorite() {
		String id = getPara("id");
		MyFavorite myFavorite = new MyFavorite();
		myFavorite = MyFavorite.dao.findById(id);
		if (myFavorite == null) {
			ajaxResult.addError(" find myFavorite by id fail");
		} else {
			ajaxResult.success(ModeUtils.getMyFavoriteJson(myFavorite), "OK");
		}
		

		renderJson(ajaxResult);
	}

	
	
	
	
	/**
	 * 删除收藏  by ycj 20171029
	 */
	@Before(SearchObjValidate.class)
	public void delMyFavoriteById() {
		String id = getPara("id");
		MyFavorite myFavorite = new MyFavorite();
		
				boolean temp = myFavorite.deleteById(id);

				if (!temp) {
					ajaxResult.addError("删除失败");
				}else{
					ajaxResult.success(null, "删除成功");
				}
			
		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取收藏列表 create by ycj 2017.10.29
	 */
	@Before(UserIdValidate.class)
	public void myFavoriteList() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		long userId = getParaToLong("userId");

		Page<MyFavorite> page = MyFavorite.dao.paginate(pageNumber, pageSize, userId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find myFavorite list fail"));
			return;
		}
		//ajaxResult.success(json,"");
		//renderJson(ajaxResult);
		Page<JSONObject > newpage = ModeUtils.getMyFavoritePage(page);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	

}
