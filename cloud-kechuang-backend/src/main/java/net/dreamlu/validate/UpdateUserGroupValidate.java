package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: UpdateUserGroupValidate 
 * @Description: 修改群组校验器
 * @author ycj
 * @date 2017年10月7日 下午7:25:56 
 *
 */
public class UpdateUserGroupValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("groupId", "msg", "groupId为空");
		validateRequired("groupName", "msg", "请输入群组名称");
		validateRequired("groupDescribe", "msg", "请输入群描述");
		validateRequired("address", "msg", "请输入地址");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
