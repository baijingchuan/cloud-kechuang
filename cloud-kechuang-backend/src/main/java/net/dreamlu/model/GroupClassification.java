package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;



/**
 * 
 * @ClassName: GroupClassification 
 * @Description: 群分类model
 * @author ycj
 * @date 2017年10月5日 下午10:50:42 
 *
 */
@SuppressWarnings("serial")
public class GroupClassification extends Model<GroupClassification> {

	public static final GroupClassification dao = new GroupClassification();
	public static final String baseSql = " id, name, remark ";

	// id: 主键id，自增
	// name 群分类名称
	// remark: 备注

	
	
	
	
	/**
	 * 获取群分类列表 create by ycj 2017.10.05
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<GroupClassification> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from group_classification order by id asc");
	}

	

}
