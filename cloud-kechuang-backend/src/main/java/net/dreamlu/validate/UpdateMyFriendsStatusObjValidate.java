package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;

/**
 *  UpdateMyFriendsStatusObjValidate校验器
 * @author ycj
 * @date 2017年10月14日 下午9:29:12 
 */
public class UpdateMyFriendsStatusObjValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("id", "msg", "id为空");
		//validateRequired("status", "msg", "status为空");
		validateInteger("status",1 , 2, "msg", "status的值应为1或2");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
