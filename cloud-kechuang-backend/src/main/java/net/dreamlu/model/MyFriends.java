package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;





/**
 * 
 * @ClassName: MyFriends 
 * @Description: 我的好友model 
 * @author ycj
 * @date 2017年10月13日 下午9:53:18 
 *
 */
@SuppressWarnings("serial")
public class MyFriends extends Model<MyFriends> {

	public static final MyFriends dao = new MyFriends();
	
	/**
	 * 获取好友的用户信息
	 * @return ycj 2017.10.13
	 */
	public TUser getFriendsTUser() {
		return TUser.dao.findById(get("friend_id"));
	}
	/**
	 * 获取申请好友的用户信息
	 * @return ycj 2017.10.13
	 */
	public TUser getHostTUser() {
		return TUser.dao.findById(get("host_id"));
	}
	

	
	public static final String baseSql = " id, host_id, friend_id, friend_remark, verification_information, status, "
			+ "friends_group_id, create_time, update_time ";

	// id: 主键id，自增
	// host_id 发送者id
	// friend_id 好友id
	// friend_remark 好友备注
	// verification_information 验证信息
	// friends_group_id 好友分组id
	// status 好友状态 0：等待对方同意加为好友，1：已加为好友、2：对方拒绝加为好友、3：好友被删除
	// create_time 创建时间
	// update_time 修改时间

	
	
	/**
	 * 根据用户id和好友id查找好友信息
	 * @param hostId
	 * @param friendId
	 */
	public MyFriends findByHostIdAndFriendId(int hostId, int friendId) {
		String sql = "SELECT "+baseSql+" FROM my_friends WHERE host_id = ? and friend_id = ? LIMIT 1";
		return this.findFirst(sql, hostId, friendId);
	}
	
	
	/**
	 * 根据用户id和好友id查找好友信息
	 * @param hostId
	 * @param friendId
	 */
	public int updateMyFriends( int friendsGroupId) {
		String sql = "update  my_friends set friends_group_id= 1 WHERE friends_group_id= ? ";
		return Db.update(sql, friendsGroupId);
	}
	
	
	/**
	 * 获取好友列表 create by ycj 2017.10.10
	 * @param pageNumber
	 * @param pageSize
	 * @param hostId
	 * @return
	 */
	public Page<MyFriends> paginate(int pageNumber, int pageSize, int hostId, int friendsGroupId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from my_friends where  host_id = ? and friends_group_id= ? and status = '1'  order by id asc", hostId, friendsGroupId);
	}
	
	/**
	 * 获取好友列表 create by ycj 2017.10.13
	 * @param pageNumber
	 * @param pageSize
	 * @param friendId
	 * @return
	 */
	public Page<MyFriends> AddApplyPaginate(int pageNumber, int pageSize, int friendId) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from my_friends where  friend_id = ? and status = '0'  order by id asc", friendId);
	}

	

}
