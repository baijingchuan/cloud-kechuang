package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;


/**
 * 
 * @ClassName: UpdateGambitValidate 
 * @Description: 修改话题校验器
 * @author ycj
 * @date 2017年10月2日 下午9:43:06 
 *
 */
public class UpdateGambitValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		validateRequired("id", "msg", "id为空");
		validateRequired("createBy", "msg", "createBy参数为空");
		validateRequired("content", "msg", "请输入您想要说的话");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
