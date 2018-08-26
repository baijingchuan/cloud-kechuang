package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: MyFriendsListValidate 
 * @Description: 根据hostId查找用户好友列表校验器
 * @author ycj
 * @date 2017年10月13日 下午11:23:32 
 *
 */
public class MyFriendsListValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("hostId", "msg", "hostId为空");
		validateRequired("friendsGroupId", "msg", "friendsGroupId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
