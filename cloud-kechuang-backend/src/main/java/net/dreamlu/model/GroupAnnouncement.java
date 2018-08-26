package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @ClassName: GroupAnnouncement 
 * @Description: 群组公告model 
 * @author ycj
 * @date 2017年9月29日 下午9:33:14 
 *
 */
@SuppressWarnings("serial")
public class GroupAnnouncement extends Model<GroupAnnouncement> {

	public static final GroupAnnouncement dao = new GroupAnnouncement();
	public static final String baseSql = " id, title, content, read_count, readers, create_by, create_time, update_time ";

	// id: 主键id，自增
	// title: 公告标题
	// content: 公告内容
	// read_count: 已读人数
	// readers: 已读人id（id之间用“，”号隔开）
	// create_by: 公告发布者
	// create_time: 公告时间（毫秒）

	
	

	
	
	
	
	
	
	/**
	 * 获取公告列表 create by ycj 2017.09.29
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<GroupAnnouncement> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from group_announcement order by id asc");
	}

	

}
