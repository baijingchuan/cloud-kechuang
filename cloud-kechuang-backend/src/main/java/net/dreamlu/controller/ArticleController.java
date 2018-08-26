package net.dreamlu.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.Article;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.utils.Utils;
import net.dreamlu.validate.AddArticleValidate;
import net.dreamlu.validate.ArticleIdValidate;
import net.dreamlu.validate.GambitIdValidate;
import net.dreamlu.validate.PublisherMemberIdValidate;
import net.dreamlu.validate.UpdateArticleValidate;
import net.dreamlu.validate.UserIdValidate;
import net.dreamlu.vo.AjaxResult;




/**
 * 
 * @ClassName: ArticleController 
 * @Description: 朋友圈文章控制器
 * @author ycj
 * @date 2017年10月17日 下午9:21:08 
 *
 */

public class ArticleController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 发布文章
	 */
	@Before(AddArticleValidate.class)
	public void addArticle() {
		String title = getPara("title");
		String details = getPara("details");
		long publisherMemberId = getParaToLong("publisherMemberId");
		String pic = getPara("pic");
		int gambitId = getParaToInt("gambitId", 0);  //所属话题id（可以为空）
		String ip = Utils.getClientIP(getRequest());//获取客户端IP
		Article article = new Article();
		
		article.set("title", title);
		article.set("details", details);
		article.set("publisher_member_id", publisherMemberId);
		article.set("pic", pic);
		article.set("gambit_id", gambitId);
		article.set("comment_num", 0);
		article.set("zan_num", 0);
		article.set("create_ip", ip);
		article.set("create_time", System.currentTimeMillis());
		article.set("last_time", null);
		article.set("is_del", "1");
		
		boolean temp = article.save();
		if (!temp) {
			ajaxResult.addError("文章发布失败，请稍后再试");
		}else{
			
			ajaxResult.success(ModeUtils.getArticleJson(article, publisherMemberId), "文章发布成功");
		}
		
		
		renderJson(ajaxResult);
	}
	
	/**
	 * 根据id查找文章信息     by ycj 20171017
	 */

	@Before(ArticleIdValidate.class)
	public void searchArticle() {
		long userId = getParaToLong("userId");
		String articleId = getPara("articleId");
		Article article = new Article();
		article = Article.dao.findById(articleId);
		if (article == null) {
			ajaxResult.addError(" find article by id fail");
		} else {
			ajaxResult.success(ModeUtils.getArticleJson(article, userId), "OK");
		}
		

		renderJson(ajaxResult);
	}
	
	

	
	
	/**
	 * 修改文章信息   by ycj 20171017
	 */
	@Before(UpdateArticleValidate.class)
	public void updateArticle() {
		String articleId = getPara("articleId");
		String title = getPara("title");
		String details = getPara("details");
		long userId = getParaToLong("userId");
		long publisherMemberId = getParaToLong("publisherMemberId", 0L);
		String pic = getPara("pic");
		Article article = new Article();
		
		article = Article.dao.findById(articleId);
		if(article == null){
			ajaxResult.addError("您修改的文章不存在或被删除");
			renderJson(ajaxResult);
			return;
		}
		
		
		article.set("title", title);
		article.set("details", details);
		article.set("publisher_member_id", publisherMemberId);
		if(StringUtils.isNotEmpty(pic)){
			article.set("pic", pic);
		}

		article.set("last_time", System.currentTimeMillis());
		if(userId != publisherMemberId){
			ajaxResult.addError("您只能修改自己的文章的文章");
			renderJson(ajaxResult);
			return;
		}
			
		boolean temp = article.update();
		if (!temp) {
			ajaxResult.addError("文章修改失败，请稍后再试");
		}else{
			
			ajaxResult.success(ModeUtils.getArticleJson(article, userId), "文章修改成功");
		}
			
		

		renderJson(ajaxResult);
	}

	/**
	 * 修改文章状态（同意或拒绝陌生人的加文章申请）   by ycj 20171017
	 */
	@Before(ArticleIdValidate.class)
	public void delArticle() {
		int articleId = getParaToInt("articleId");
		Article article = new Article();
		
		article = Article.dao.findById(articleId);
		
		if(article == null){
			ajaxResult.addError(ModeUtils.errorCode,"文章已被删除");
			renderJson(ajaxResult);
			return;
		}
		
	
			
		int delRows = article.delArticle(articleId);
		if (delRows == 0) {
			ajaxResult.addError(ModeUtils.errorCode, "删除失败");
		}else{
			
			ajaxResult.success(ModeUtils.successCode, null, "删除成功");
			
		}
			
		

		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取我的文章列表 create by ycj 2017.10.17
	 */
	@Before(UserIdValidate.class)
	public void myArticleList() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		long userId = getParaToLong("userId");
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);


		Page<Article> page = Article.dao.myArticleList(pageNumber, pageSize, userId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find my article list fail"));
			return;
		}
		
		Page<JSONObject > newpage = ModeUtils.getArticlePage(page, userId);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	/**
	 * 获取某话题下的文章列表 create by ycj 2017.10.17
	 */
	@Before(GambitIdValidate.class)
	public void gambitArticleList() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		int gambitId = getParaToInt("gambitId", 0);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);
		long userId = getParaToLong("userId");

		Page<Article> page = Article.dao.gambitArticleList(pageNumber, pageSize, gambitId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find gambit article list fail"));
			return;
		}
		Page<JSONObject > newpage = ModeUtils.getArticlePage(page,userId);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	

	/**
	 * 获取所有文章列表 create by ycj 2017.10.17
	 */
	@Before(UserIdValidate.class)
	public void allArticleList() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);
		long userId = getParaToLong("userId");

		Page<Article> page = Article.dao.allArticleList(pageNumber, pageSize);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("page", page);
		if(page == null){
			renderJson(ajaxResult.addError("find all article list fail"));
			return;
		}
		Page<JSONObject > newpage = ModeUtils.getArticlePage(page, userId);
		json.put("page", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
}
