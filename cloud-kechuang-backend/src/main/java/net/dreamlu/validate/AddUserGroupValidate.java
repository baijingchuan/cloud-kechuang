package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: AddUserGroupValidate 
 * @Description: 添加群组校验器 
 * @author ycj
 * @date 2017年10月7日 下午7:06:32 
 *
 */
public class AddUserGroupValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("groupName", "msg", "请输入群组名称");
		validateRequired("groupDescribe", "msg", "请输入群描述");
		validateRequired("address", "msg", "请输入地址");
		validateRequired("groupClassificationId", "msg", "请选择群分类");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
