package net.dreamlu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import net.dreamlu.model.Article;
import net.dreamlu.model.ArticlePraise;
import net.dreamlu.utils.ModeUtils;
import net.dreamlu.utils.Utils;
import net.dreamlu.validate.ArticlePraiseValidate;
import net.dreamlu.vo.AjaxResult;






/**
 * 
 * @ClassName: ArticlePraiseController 
 * @Description: 朋友圈文章点赞控制器
 * @author ycj
 * @date 2017年10月25日 下午9:27:53 
 *
 */

public class ArticlePraiseController extends Controller {

	private AjaxResult<JSON> ajaxResult = new AjaxResult<JSON>();
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 对文章点赞
	 */
	@Before(ArticlePraiseValidate.class)
	public void articleDianZan() {
		int articleId = getParaToInt("articleId");
		long userId = getParaToLong("userId");
		String ip = Utils.getClientIP(getRequest());//获取客户端IP
		
		ArticlePraise articlePraise = ArticlePraise.dao.selectArticlePraise(userId, articleId);
		
		if (articlePraise == null) {
			articlePraise = new ArticlePraise();
			articlePraise.set("article_id", articleId);
			articlePraise.set("user_id", userId);
			articlePraise.set("create_ip", ip);
			articlePraise.set("create_time", System.currentTimeMillis());
			articlePraise.set("is_del", "1");
			
			boolean temp = articlePraise.save();
			if (!temp) {
				ajaxResult.addError("文章点赞失败，请稍后再试");
			}else{
				long resultnum = ArticlePraise.dao.getArticleArticleTimes(articleId);
				logger.info("resultnum={}",resultnum);
				int resultrows = Article.dao.setArticlePraiseTimes(articleId, resultnum);
				logger.info("setArticleCommentedTimes.resultrows={}",resultrows);
				ajaxResult.success(ModeUtils.getArticlePraiseJson(articlePraise), "文章点赞成功");
				
			}
		}else if ("2".equals(articlePraise.get("is_del"))) {
			articlePraise.set("is_del", "1");
			
			boolean temp = articlePraise.update();
			if (!temp) {
				ajaxResult.addError("文章点赞失败，请稍后再试");
			}else{
				long resultnum = ArticlePraise.dao.getArticleArticleTimes(articleId);
				logger.info("resultnum={}",resultnum);
				int resultrows = Article.dao.setArticlePraiseTimes(articleId, resultnum);
				logger.info("setArticleCommentedTimes.resultrows={}",resultrows);
				ajaxResult.success(ModeUtils.getArticlePraiseJson(articlePraise), "文章点赞成功");
				
			}
		}else {
			articlePraise.set("is_del", "2");
			
			boolean temp = articlePraise.update();
			if (!temp) {
				ajaxResult.addError("文章点赞失败，请稍后再试");
			}else{
				long resultnum = ArticlePraise.dao.getArticleArticleTimes(articleId);
				logger.info("resultnum={}",resultnum);
				int resultrows = Article.dao.setArticlePraiseTimes(articleId, resultnum);
				logger.info("setArticleCommentedTimes.resultrows={}",resultrows);
				ajaxResult.success(ModeUtils.getArticlePraiseJson(articlePraise), "文章取消点赞成功");
				
			}
		}
		
		
		
		renderJson(ajaxResult);
	}
	
	
	
}
