package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: MyFriendsGroupNameValidate 
 * @Description: 好友分组名称校验器
 * @author ycj
 * @date 2017年10月10日 下午10:23:32 
 *
 */
public class MyFriendsGroupNameValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("name", "msg", "name为空");
		validateRequired("userId", "msg", "userId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
