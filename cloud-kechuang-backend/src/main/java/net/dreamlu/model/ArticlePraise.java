package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;






/**
 * 
 * @ClassName: ArticlePraise 
 * @Description: 朋友圈文章点赞model 
 * @author ycj
 * @date 2017年10月25日 下午9:39:05 
 *
 */
@SuppressWarnings("serial")
public class ArticlePraise extends Model<ArticlePraise> {

	public static final ArticlePraise dao = new ArticlePraise();
	
	
	
	
	

	
	public static final String baseSql = " praise_id, article_id, user_id, create_time, create_ip, is_del ";

	// praise_id: 主键id，自增
	// article_id: 文章id
	// user_id 评论者id
	// create_time 创建时间
	// create_ip 评论者IP
	// is_del 1:正常,2删除,3禁用

	/**
	 * 获取文章信息
	 * @return ycj 2017.10.25
	 */
	public Article getArticle() {
		return Article.dao.findById(get("article_id"));
	}
	
	

	/**
	 * 获取某文章点赞总数
	 * @param articleId
	 */
	public long getArticleArticleTimes( int articleId) {
		String sql = "SELECT count(*) FROM yx_article_praise WHERE article_id= ? and is_del='1' ";
		return Db.queryLong(sql, articleId);
	}
	
	/**
	 * 查找某文章点赞信息
	 * @param userId
	 * @param articleId
	 */
	public ArticlePraise selectArticlePraise( long userId, int articleId) {
		String sql = "SELECT "+baseSql+" FROM yx_article_praise WHERE user_id= ? and article_id= ?  LIMIT 1";
		return this.findFirst(sql, userId, articleId);
	}
	
	
	
	
	
	
	

}
