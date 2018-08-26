package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: SearchUserGroupValidate 
 * @Description: 根据groupId查找对象校验器
 * @author ycj
 * @date 2017年10月7日 下午7:23:32 
 *
 */
public class SearchUserGroupValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("groupId", "msg", "groupId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
