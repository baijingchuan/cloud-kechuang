package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: AddMyFriendsGroupValidate 
 * @Description: 添加好友分组校验器 
 * @author ycj
 * @date 2017年10月10日 下午10:55:20 
 *
 */
public class AddMyFriendsGroupValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("name", "msg", "name参数为空");
		validateRequired("userId", "msg", "userId参数为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
