package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: AddMyFavoriteValidate 
 * @Description: 添加收藏MyFavorite
 * @author ycj
 * @date 2017年10月29日 下午8:12:05 
 *
 */
public class AddMyFavoriteValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("userId", "msg", "userId参数为空");
		validateRequired("publisherMemberId", "msg", "publisherMemberId参数为空");
		validateRequired("title", "msg", "标题为空");
		validateRequired("link", "msg", "收藏链接为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
