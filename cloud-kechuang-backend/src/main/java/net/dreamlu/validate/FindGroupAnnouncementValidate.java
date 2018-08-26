package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;

/**
 * 查看群组公告校验器
 * @author ycj
 * @date 2017年9月29日 下午9:29:12 
 */
public class FindGroupAnnouncementValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		validateRequired("userId", "msg", "userId为空");
		
		validateRequired("announcementId", "msg", "announcementId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
