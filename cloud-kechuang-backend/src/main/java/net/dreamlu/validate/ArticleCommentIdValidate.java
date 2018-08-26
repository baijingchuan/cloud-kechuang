package net.dreamlu.validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import net.dreamlu.validate.base.ShortCircuitValidate;



/**
 * 
 * @ClassName: ArticleCommentIdValidate 
 * @Description: 评论id校验器
 * @author ycj
 * @date 2017年10月21日 下午10:17:57 
 *
 */
public class ArticleCommentIdValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		
		validateRequired("commentId", "msg", "commentId为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.render(new JsonRender().forIE());
	}

}
