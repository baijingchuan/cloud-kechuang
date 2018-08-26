package net.dreamlu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.Article;
import net.dreamlu.model.ArticleComment;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.utils.Utils;
import net.dreamlu.validate.AddArticleCommentValidate;
import net.dreamlu.validate.ArticleCommentIdValidate;
import net.dreamlu.validate.ArticleIdValidate;
import net.dreamlu.validate.GambitIdValidate;
import net.dreamlu.vo.AjaxResult;





/**
 * 
 * @ClassName: ArticleCommentController 
 * @Description: 朋友圈文章评论控制器
 * @author ycj
 * @date 2017年10月21日 下午8:10:17 
 *
 */

public class ArticleCommentController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 对文章添加评论
	 */
	@Before(AddArticleCommentValidate.class)
	public void addArticleComment() {
		int articleId = getParaToInt("articleId");
		long commentMemberId = getParaToLong("commentMemberId");
		String content = getPara("content");
		long repliedMemberId = getParaToLong("repliedMemberId", 0L);  //回复评论人id（可以为空）
		String ip = Utils.getClientIP(getRequest());//获取客户端IP
		ArticleComment articleComment = new ArticleComment();
		
		articleComment.set("article_id", articleId);
		articleComment.set("comment_member_id", commentMemberId);
		articleComment.set("content", content);
		articleComment.set("replied_member_id", repliedMemberId);
		articleComment.set("create_ip", ip);
		articleComment.set("create_time", System.currentTimeMillis());
		articleComment.set("is_del", "1");
		
		boolean temp = articleComment.save();
		if (!temp) {
			ajaxResult.addError("文章评论失败，请稍后再试");
		}else{
			long resultnum = ArticleComment.dao.getArticleCommentTimes(articleId);
			logger.info("resultnum={}",resultnum);
			int resultrows = Article.dao.setArticleCommentedTimes(articleId, resultnum);
			logger.info("setArticleCommentedTimes.resultrows={}",resultrows);
			ajaxResult.success(ModeUtils.getArticleCommentJson(articleComment), "文章评论成功");
			
		}
		
		
		renderJson(ajaxResult);
	}
	
	

	/**
	 * 删除文章评论   by ycj 20171021
	 */
	@Before(ArticleCommentIdValidate.class)
	public void delArticleComment() {
		int commentId = getParaToInt("commentId");
		ArticleComment articleComment = new ArticleComment();
		
		articleComment = ArticleComment.dao.findById(commentId);
		
		if(articleComment == null){
			ajaxResult.addError(ModeUtils.errorCode,"文章评论已被删除");
			renderJson(ajaxResult);
			return;
		}
		
	
			
		int delRows = articleComment.delArticleComment(commentId);
		if (delRows == 0) {
			ajaxResult.addError(ModeUtils.errorCode, "删除失败");
		}else{
			int articleId = (int)articleComment.get("article_id");
			long resultnum = ArticleComment.dao.getArticleCommentTimes(articleId);
			logger.info("resultnum={}",resultnum);
			int resultrows = Article.dao.setArticleCommentedTimes(articleId, resultnum);
			logger.info("setArticleCommentedTimes.resultrows={}",resultrows);
			ajaxResult.success(ModeUtils.successCode, null, "删除成功");
			
			
		}
			
		

		renderJson(ajaxResult);
	}
	
	
	
	/**
	 * 获取某文章下的评论列表 create by ycj 2017.10.21
	 */
	@Before(ArticleIdValidate.class)
	public void getOneArticleCommentList() {
		//分页参数
		int pageNumber = getParaToInt("pageNum", 1);
		int pageSize = getParaToInt("pageSize", 10);
		int articleId = getParaToInt("articleId", 0);
		setAttr("pageNumber", pageNumber);
		setAttr("pageSize", pageSize);

		Page<ArticleComment> page = ArticleComment.dao.getOneArticleCommentList(pageNumber, pageSize, articleId);
		JSONObject json = new JSONObject();
		json.put("pageNumber", pageNumber);
		json.put("pageSize", pageSize);
		json.put("commentListpage", page);
		if(page == null){
			renderJson(ajaxResult.addError("get one article comment list fail"));
			return;
		}
		
		Page<JSONObject > newpage = ModeUtils.getArticleCommentPage(page);
		json.put("commentListpage", newpage);
		ajaxResult.success(json,"");
		renderJson(ajaxResult);
	}
	
	
	
}
