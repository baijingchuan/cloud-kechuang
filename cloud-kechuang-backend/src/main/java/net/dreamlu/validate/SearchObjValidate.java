package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;

/**
 * 根据id查找对象校验器
 * @author ycj
 * @date 2017年9月29日 下午9:29:12 
 */
public class SearchObjValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("id", "msg", "id为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
