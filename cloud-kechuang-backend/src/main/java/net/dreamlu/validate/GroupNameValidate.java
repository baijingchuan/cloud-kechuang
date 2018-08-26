package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: GroupNameValidate 
 * @Description: 群名称校验器
 * @author ycj
 * @date 2017年10月7日 下午7:23:32 
 *
 */
public class GroupNameValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("groupName", "msg", "groupName为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
