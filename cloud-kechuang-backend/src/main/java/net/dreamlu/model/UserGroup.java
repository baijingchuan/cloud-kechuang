package net.dreamlu.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;




/**
 * 
 * @ClassName: UserGroup 
 * @Description:  群组model 
 * @author ycj
 * @date 2017年10月7日 下午7:11:13 
 *
 */
@SuppressWarnings("serial")
public class UserGroup extends Model<UserGroup> {

	public static final UserGroup dao = new UserGroup();
	

	public GroupClassification getGroupClassification() {
		return GroupClassification.dao.findById(get("group_classification_id"));
		}
	public static final String baseSql = " group_id, group_name, group_describe, group_icon, "
			+ "address, group_classification_id, "
			+ "create_time, remark ";

	// group_id: 主键id，自增
	// group_name 群名称
	// group_describe 群描述
	// group_icon 群图片
	// address 地址
	// group_classification_id 群分类id
	// create_time: 创建时间
	// update_time: 修改时间
	// remark: 备注

	/**
	 * 根据群名称查找群组
	 * @param groupName
	 */
	public UserGroup findByGroupName(String groupName) {
		String sql = "SELECT "+baseSql+" FROM user_group WHERE group_name = ? LIMIT 1";
		return this.findFirst(sql, groupName);
	}
	
	/**
	 * 根据群名称模糊查找群组
	 * @param email
	 * @return TUser
	 */
	public Page<UserGroup>  fuzzySearchByGroupName(int pageNumber, int pageSize, String groupName) {
		return paginate(pageNumber, pageSize, "SELECT "+baseSql, " FROM user_group WHERE group_name like ? order by group_id asc","%"+groupName+"%");
	}
	
	
	
	/**
	 * 获取群分类列表 create by ycj 2017.10.07
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<UserGroup> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from user_group order by group_id asc");
	}

	

}
