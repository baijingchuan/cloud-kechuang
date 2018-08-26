package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;






/**
 * 
 * @ClassName: ArticlePraiseValidate 
 * @Description: 文章点赞校验器
 * @author ycj
 * @date 2017年10月25日 下午9:57:05 
 *
 */
public class ArticlePraiseValidate extends ShortCircuitValidate {

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
