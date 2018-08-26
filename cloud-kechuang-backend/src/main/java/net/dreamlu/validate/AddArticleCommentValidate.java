package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.utils.RegexUtils;
import net.dreamlu.validate.base.ShortCircuitValidate;




/**
 * 
 * @ClassName: AddArticleCommentValidate 
 * @Description: 添加文章评论校验器 
 * @author ycj
 * @date 2017年10月21日 下午8:23:23 
 *
 */
public class AddArticleCommentValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("articleId", "msg", "articleId为空");
		validateRequired("commentMemberId", "msg", "commentMemberId为空");
		validateRequired("content", "msg", "评论内容为空");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
