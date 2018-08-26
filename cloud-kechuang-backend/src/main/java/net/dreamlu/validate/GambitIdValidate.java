package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: GambitIdValidate 
 * @Description: 话题id校验器
 * @author ycj
 * @date 2017年10月17日 下午11:17:57 
 *
 */
public class GambitIdValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("gambitId", "msg", "gambitId为空");
		validateRequired("userId", "msg", "userId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
