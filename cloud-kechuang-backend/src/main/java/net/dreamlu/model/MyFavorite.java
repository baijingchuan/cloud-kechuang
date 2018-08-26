package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;



/**
 * 
 * @ClassName: MyFavorite 
 * @Description: 我的收藏model
 * @author ycj
 * @date 2017年10月29日 下午8:15:03 
 *
 */
@SuppressWarnings("serial")
public class MyFavorite extends Model<MyFavorite> {

	public static final MyFavorite dao = new MyFavorite();
	public static final String baseSql = " id, user_id, publisher_member_id, title, pic, favorite_describe, favorite_label, "
			+ "link, create_time ";

	// id: 主键id，自增
	// user_id: 用户id
	// publisher_member_id: 发布人ID
	// title: 标题
	// pic: 图片路径
	// favorite_describe: 描述
	// favorite_label: 标签
	// link: 收藏链接地址
	// create_time: 话题创建时间（毫秒）

	
	/**
	 * 获取收藏人信息
	 * @return ycj 2017.10.29
	 */
	public TUser getUser() {
		return TUser.dao.findById(get("user_id"));
	}
	/**
	 * 获取作者信息
	 * @return ycj 2017.10.29
	 */
	public TUser getPublisher() {
		return TUser.dao.findById(get("publisher_member_id"));
	}

	
	/**
	 * 根据收藏链接和用户id查找用户收藏
	 * @param link
	 * @param userId
	 */
	public MyFavorite findByLinkAndUserId(String link,long userId) {
		String sql = "SELECT "+baseSql+" FROM yx_my_favorite WHERE link = ? and user_id = ? LIMIT 1";
		return this.findFirst(sql, link, userId);
	}
	
	
	
	/**
	 * 获取收藏列表 create by ycj 2017.10.29
	 * @param pageNumber
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Page<MyFavorite> paginate(int pageNumber, int pageSize, long userId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from yx_my_favorite where user_id = ? order by id desc",userId);
	}

	

}
