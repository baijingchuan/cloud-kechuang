package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: ArticleIdValidate 
 * @Description: 文章id校验器
 * @author ycj
 * @date 2017年10月17日 下午10:17:57 
 *
 */
public class ArticleIdValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("articleId", "msg", "articleId为空");
		validateRequired("userId", "msg", "userId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
