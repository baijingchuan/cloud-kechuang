package net.dreamlu.model;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import net.dreamlu.utils.ModeUtils;





/**
 * 
 * @ClassName: Article 
 * @Description: 朋友圈文章model 
 * @author ycj
 * @date 2017年10月17日 下午10:53:18 
 *
 */
@SuppressWarnings("serial")
public class Article extends Model<Article> {

	public static final Article dao = new Article();
	
	/**
	 * 获取发布人信息
	 * @return ycj 2017.10.17
	 */
	public TUser getPublisherMember() {
		return TUser.dao.findById(get("publisher_member_id"));
	}
	
	/**
	 * 获该文章下的评论信息
	 * @return ycj 2017.10.17
	 */
	public Page<JSONObject >  getOneArticleCommentList() {
		Page<ArticleComment> page = ArticleComment.dao.getOneArticleCommentList(1, 10, (int)get("article_id"));
		Page<JSONObject > commentListpage = ModeUtils.getArticleCommentPage(page);
		return commentListpage;
	}
	
	

	
	public static final String baseSql = " article_id, title, details, publisher_member_id, pic, "
			+ "comment_num, praise_num, create_time, create_ip, "
			+ "last_time, gambit_id, is_del ";

	// article_id: 主键id，自增
	// title 文章标题
	// details 文章详情
	// publisher_member_id 发布人ID
	// pic 文章图片路径
	// comment_num 评论次数
	// praise_num 点赞次数
	// create_time 创建时间
	// create_ip 创建IP
	// last_time 最后更新时间
	// gambit_id 所属话题id
	// is_del 1:正常,2删除,3禁用

	
	

	
	/**
	 * 设置某文章的总评论数 update by ycj 20171025
	 * @param articleId
	 * @param totalCommentedTTimes
	 */
	public int setArticleCommentedTimes( int articleId, long totalCommentedTTimes) {
		String sql = "update  article set comment_num= ?  WHERE article_id= ? ";
		return Db.update(sql, totalCommentedTTimes, articleId);
	}
	
	/**
	 * 设置某文章的总点赞数 update by ycj 20171025
	 * @param articleId
	 * @param totalPraiseTimes
	 */
	public int setArticlePraiseTimes( int articleId, long totalPraiseTimes) {
		String sql = "update  article set praise_num= ?  WHERE article_id= ? ";
		return Db.update(sql, totalPraiseTimes, articleId);
	}
	
	
	/**
	 * 删除文章
	 * @param articleId
	 */
	public int delArticle( int articleId) {
		String sql = "update  article set is_del='2'  WHERE article_id= ? ";
		return Db.update(sql, articleId);
	}
	
	
	/**
	 * 获取我的文章列表 create by ycj 2017.10.17
	 * @param pageNumber
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Page<Article> myArticleList(int pageNumber, int pageSize, long userId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from article where  publisher_member_id = ? and  is_del = '1'  order by create_time DESC", userId);
	}
	
	
	/**
	 * 获取某话题下的文章列表 create by ycj 2017.10.17
	 * @param pageNumber
	 * @param pageSize
	 * @param gambitId
	 * @return
	 */
	public Page<Article> gambitArticleList(int pageNumber, int pageSize, int gambitId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from article where  gambit_id = ? and  is_del = '1'  order by create_time DESC", gambitId);
	}
	
	
	/**
	 * 获取所有文章列表 create by ycj 2017.10.17
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Article> allArticleList(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from article where is_del = '1'  order by create_time DESC");
	}
	
	

	

}
