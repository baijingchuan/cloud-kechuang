package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: UpdateMyFriendsGroupValidate 
 * @Description: 修改好友分组校验器
 * @author ycj
 * @date 2017年10月10日 下午11:03:42 
 *
 */
public class UpdateMyFriendsGroupValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		validateRequired("id", "msg", "id为空");
		validateRequired("name", "msg", "name参数为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
