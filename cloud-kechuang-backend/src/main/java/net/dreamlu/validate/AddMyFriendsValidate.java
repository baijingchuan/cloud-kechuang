package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: AddMyFriendsValidate 
 * @Description: 添加好友校验器 
 * @author ycj
 * @date 2017年10月13日 下午9:43:41 
 *
 */
public class AddMyFriendsValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("hostId", "msg", "hostId参数为空");
		validateRequired("friendId", "msg", "name参数为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
