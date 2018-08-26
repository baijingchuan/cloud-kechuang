package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: UserIdValidate 
 * @Description: 根据userId查找用户好友分组列表校验器
 * @author ycj
 * @date 2017年10月13日 下午7:23:32 
 *
 */
public class UserIdValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("userId", "msg", "userId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
