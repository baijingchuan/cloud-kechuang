package net.dreamlu.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;


/**
 * 
 * @ClassName: Gambit 
 * @Description: 话题model
 * @author ycj
 * @date 2017年10月2日 下午9:48:44 
 *
 */
@SuppressWarnings("serial")
public class Gambit extends Model<Gambit> {

	public static final Gambit dao = new Gambit();
	public static final String baseSql = " id, img, content, create_by, create_time, update_time ";

	// id: 主键id，自增
	// img: 话题图片路径（多张图片路径之间用英文“,”符合隔开）
	// content: 话题内容
	// create_by: 话题发布者
	// create_time: 话题创建时间（毫秒）
	// update_time: 话题修改时间（毫秒）

	
	

	
	
	
	
	
	/**
	 * 获取话题列表 create by ycj 2017.10.02
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Gambit> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select "+baseSql, "from gambit order by id asc");
	}

	

}
