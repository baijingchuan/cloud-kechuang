package net.dreamlu.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.model.Article;
import net.dreamlu.model.ArticleComment;
import net.dreamlu.model.ArticlePraise;
import net.dreamlu.model.GroupClassification;
import net.dreamlu.model.MemberMoney;
import net.dreamlu.model.MyFavorite;
import net.dreamlu.model.MyFriends;
import net.dreamlu.model.TUser;
import net.dreamlu.model.UserGroup;

public class ModeUtils {

	
	public static final String successCode = "1000";
	public static final String errorCode = "1001";
	
	public static  Page<JSONObject >  getUserGroupPage( Page<UserGroup> page){
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		int totalPage = page.getTotalPage();
		int totalRow = page.getTotalRow();
		List<UserGroup> list = page.getList();
		List<JSONObject> array = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			UserGroup group = list.get(i);
			JSONObject jsonObject = getUserGroupJson(group);
			
			array.add( jsonObject);
			//list.set(i, group);
			
		}
		Page<JSONObject > newpage = new Page<JSONObject>(array, pageNumber, pageSize, totalPage, totalRow);
		return newpage;
	}
	
	public static  JSONObject  getUserGroupJson( UserGroup group){
		
			GroupClassification groupClassification = group.getGroupClassification();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("group", group);
			jsonObject.put("groupClassification", groupClassification);
			
			
		return jsonObject;
	}
	
	public static  JSONObject  getMyFriendsJson( MyFriends myFriends){
		
		TUser friendsTUser = myFriends.getFriendsTUser();
		TUser hostTUser = myFriends.getHostTUser();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("hostTUser", hostTUser);
		jsonObject.put("friendsTUser", friendsTUser);
		jsonObject.put("myFriends", myFriends);
		
		
		return jsonObject;
	}
	
	public static  JSONObject  getArticleJson( Article article, long userId){
		
		TUser publisherMember = article.getPublisherMember();
		Page<JSONObject > commentListpage = article.getOneArticleCommentList();
		int articleId = article.getInt("article_id");
		ArticlePraise articlePraise = ArticlePraise.dao.selectArticlePraise(userId, articleId);
		JSONObject jsonObject = new JSONObject();
		
		String isDianZan = "0";
		if (articlePraise != null && "1".equals(articlePraise.get("is_del"))) {
			isDianZan ="1";
		}
		jsonObject.put("publisherMember", publisherMember);
		jsonObject.put("commentListpage", commentListpage);
		jsonObject.put("article", article);
		jsonObject.put("isDianZan", isDianZan);
		
		
		return jsonObject;
	}
	
	
	public static  JSONObject  getArticleCommentJson( ArticleComment articleComment){
		
		TUser commentator = articleComment.getCommentator();
		TUser replier = articleComment.getreplier();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("commentator", commentator);
		jsonObject.put("replier", replier);
		jsonObject.put("articleComment", articleComment);
		
		
		return jsonObject;
	}
	
	public static  JSONObject  getArticlePraiseJson( ArticlePraise articlePraise){
		
		Article article = articlePraise.getArticle();
		TUser publisherMember = article.getPublisherMember();
		Page<JSONObject > commentListpage = article.getOneArticleCommentList();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("publisherMember", publisherMember);
		jsonObject.put("commentListpage", commentListpage);
		jsonObject.put("article", article);
		String isDel = articlePraise.get("is_del");
		jsonObject.put("isDianZan", "1".equals(isDel)?"1":"0");
		jsonObject.put("article", article);
		
		
		return jsonObject;
	}
	
	
	public static  JSONObject  getMyFavoriteJson( MyFavorite myFavorite){
		
		TUser user = myFavorite.getUser();
		TUser publisher = myFavorite.getPublisher();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user", user);
		jsonObject.put("publisher", publisher);
		jsonObject.put("myFavorite", myFavorite);
		
		
		return jsonObject;
	}
	

	public static  JSONObject  getMemberMoneyJson( MemberMoney memberMoney){
		
		BigDecimal totalIncome = memberMoney.get("total_income");
		BigDecimal withdraw = memberMoney.get("withdraw");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalIncome", totalIncome);
		jsonObject.put("withdraw", withdraw);
		jsonObject.put("userId", memberMoney.get("user_id"));
		
		
		return jsonObject;
	}
	
	
	public static  Page<JSONObject >  getMyFriendsPage( Page<MyFriends> page){
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		int totalPage = page.getTotalPage();
		int totalRow = page.getTotalRow();
		List<MyFriends> list = page.getList();
		List<JSONObject> array = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			MyFriends myFriends = list.get(i);
			JSONObject jsonObject = getMyFriendsJson(myFriends);
			
			array.add( jsonObject);
			//list.set(i, group);
			
		}
		Page<JSONObject > newpage = new Page<JSONObject>(array, pageNumber, pageSize, totalPage, totalRow);
		return newpage;
	}
	public static  Page<JSONObject >  getArticlePage( Page<Article> page, long userId){
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		int totalPage = page.getTotalPage();
		int totalRow = page.getTotalRow();
		List<Article> list = page.getList();
		List<JSONObject> array = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			Article article = list.get(i);
			JSONObject jsonObject = getArticleJson(article, userId);
			
			array.add( jsonObject);
			//list.set(i, group);
			
		}
		Page<JSONObject > newpage = new Page<JSONObject>(array, pageNumber, pageSize, totalPage, totalRow);
		return newpage;
	}
	
	public static  Page<JSONObject >  getArticleCommentPage( Page<ArticleComment> page){
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		int totalPage = page.getTotalPage();
		int totalRow = page.getTotalRow();
		List<ArticleComment> list = page.getList();
		List<JSONObject> array = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			ArticleComment articleComment = list.get(i);
			JSONObject jsonObject = getArticleCommentJson(articleComment);
			
			array.add( jsonObject);
			
		}
		Page<JSONObject > newpage = new Page<JSONObject>(array, pageNumber, pageSize, totalPage, totalRow);
		return newpage;
	}
	

	public static  Page<JSONObject >  getMyFavoritePage( Page<MyFavorite> page){
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		int totalPage = page.getTotalPage();
		int totalRow = page.getTotalRow();
		List<MyFavorite> list = page.getList();
		List<JSONObject> array = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			MyFavorite myFavorite = list.get(i);
			JSONObject jsonObject = getMyFavoriteJson(myFavorite);
			
			array.add( jsonObject);
			
		}
		Page<JSONObject > newpage = new Page<JSONObject>(array, pageNumber, pageSize, totalPage, totalRow);
		return newpage;
	}
	
	
}
