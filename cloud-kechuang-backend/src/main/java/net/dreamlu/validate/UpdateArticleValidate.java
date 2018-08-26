package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;




/**
 * 
 * @ClassName: UpdateArticleValidate 
 * @Description: 修改文章校验器  
 * @author ycj
 * @date 2017年10月17日 下午10:31:27 
 *
 */
public class UpdateArticleValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("articleId", "msg", "articleId为空");
		validateRequired("title", "msg", "标题为空");
		validateRequired("details", "msg", "文章详情内容为空");
		validateRequired("publisherMemberId", "msg", "publisherMemberId为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
