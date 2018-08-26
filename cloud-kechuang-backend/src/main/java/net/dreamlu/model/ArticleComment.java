package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;





/**
 * 
 * @ClassName: ArticleComment 
 * @Description: 朋友圈文章评论model 
 * @author ycj
 * @date 2017年10月21日 下午8:27:04 
 *
 */
@SuppressWarnings("serial")
public class ArticleComment extends Model<ArticleComment> {

	public static final ArticleComment dao = new ArticleComment();
	
	/**
	 * 获取评论人信息
	 * @return ycj 2017.10.21
	 */
	public TUser getCommentator() {
		return TUser.dao.findById(get("comment_member_id"));
	}
	/**
	 * 获取被回复人id信息
	 * @return ycj 2017.10.21
	 */
	public TUser getreplier() {
		return TUser.dao.findById(get("replied_member_id"));
	}
	
	

	
	public static final String baseSql = " comment_id, article_id, comment_member_id, replied_member_id, content, create_time, "
			+ " create_ip, is_del ";

	// comment_id: 主键id，自增
	// article_id: 文章id
	// comment_member_id 评论者id
	// replied_member_id 被回复人id
	// content 评论内容
	// create_time 创建时间
	// create_ip 评论者IP
	// is_del 1:正常,2删除,3禁用

	
	

	/**
	 * 获取某文章评论评论总数
	 * @param commentId
	 */
	public long getArticleCommentTimes( int articleId) {
		String sql = "SELECT count(*) FROM yx_article_comment WHERE article_id= ? and is_del='1' ";
		return Db.queryLong(sql, articleId);
	}
	
	
	/**
	 * 删除文章评论
	 * @param commentId
	 */
	public int delArticleComment( int commentId) {
		String sql = "update  yx_article_comment set is_del='2'  WHERE comment_id= ? ";
		return Db.update(sql, commentId);
	}
	
	
	/**
	 * 获取某文章下的评论列表 create by ycj 2017.10.21
	 * @param pageNumber
	 * @param pageSize
	 * @param articleId
	 * @return
	 */
	public Page<ArticleComment> getOneArticleCommentList(int pageNumber, int pageSize, int articleId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from yx_article_comment where  article_id = ? and  is_del = '1'  order by create_time asc", articleId);
	}
	
	

}
