package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;




/**
 * 
 * @ClassName: MyFriendsGroup 
 * @Description: 我的好友分组model
 * @author ycj
 * @date 2017年10月10日 下午9:07:00 
 *
 */
@SuppressWarnings("serial")
public class MyFriendsGroup extends Model<MyFriendsGroup> {

	public static final MyFriendsGroup dao = new MyFriendsGroup();
	

	
	public static final String baseSql = " id, user_id, name, status, remark ";

	// id: 主键id，自增
	// name 好友分组名称
	// status 是否为系统分组0：否，1：是  （分组中只有“我的好友“是系统分组且不可删除）
	// remark 备注

	
	
	/**
	 * 根据分组名称查找好友分组
	 * @param name
	 * @param userId
	 */
	public MyFriendsGroup findByNameAndUserId(String name,long userId) {
		String sql = "SELECT "+baseSql+" FROM my_friends_group WHERE name = ? and user_id = ? LIMIT 1";
		return this.findFirst(sql, name, userId);
	}
	
	
	/**
	 * 获取好友分组列表 create by ycj 2017.10.10
	 * @param pageNumber
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Page<MyFriendsGroup> paginate(int pageNumber, int pageSize, long userId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from my_friends_group where user_id = ? or status = '1' order by id asc",userId);
	}

	

}
