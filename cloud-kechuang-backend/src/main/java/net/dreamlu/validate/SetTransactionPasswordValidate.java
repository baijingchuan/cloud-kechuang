package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: SetTransactionPasswordValidate 
 * @Description: 设置交易密码校验器
 * @author ycj
 * @date 2017年11月7日 下午6:46:36 
 *
 */
public class SetTransactionPasswordValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("userId", "msg", "userId为空");
		validateRequired("transactionPassword", "msg", "transactionPassword为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
