package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;

/**
 * 发布公告校验器
 * @author ycj
 * @date 2017年9月29日 下午9:29:12 
 */
public class UpdateAnnouncementValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {

		validateRequired("id", "msg", "id为空");
		validateRequired("title", "msg", "请输入您的公告标题");
		validateRequired("createBy", "msg", "createBy参数为空");
		validateRequired("content", "msg", "请输入您的公告内容");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
