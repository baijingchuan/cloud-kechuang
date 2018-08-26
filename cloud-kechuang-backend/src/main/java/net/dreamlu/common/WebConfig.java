package net.dreamlu.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.upload.OreillyCos;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

import net.dreamlu.controller.AdminController;
import net.dreamlu.controller.AjaxController;
import net.dreamlu.controller.ArticleCommentController;
import net.dreamlu.controller.ArticleController;
import net.dreamlu.controller.ArticlePraiseController;
import net.dreamlu.controller.BeetlController;
import net.dreamlu.controller.EmailController;
import net.dreamlu.controller.GambitController;
import net.dreamlu.controller.GroupAnnouncementController;
import net.dreamlu.controller.GroupClassificationController;
import net.dreamlu.controller.ImageController;
import net.dreamlu.controller.IndexController;
import net.dreamlu.controller.MemberMoneyController;
import net.dreamlu.controller.MyFavoriteController;
import net.dreamlu.controller.MyFriendsController;
import net.dreamlu.controller.MyFriendsGroupController;
import net.dreamlu.controller.TestController;
import net.dreamlu.controller.UeditorApiController;
import net.dreamlu.controller.UeditorController;
import net.dreamlu.controller.UserGroupController;
import net.dreamlu.controller.weixin.WeixinApiController;
import net.dreamlu.controller.weixin.WeixinMsgController;
import net.dreamlu.ext.UpFileRenamePolicy;
import net.dreamlu.ext.plugin.log.Slf4jLogFactory;
import net.dreamlu.model.Article;
import net.dreamlu.model.ArticleComment;
import net.dreamlu.model.ArticlePraise;
import net.dreamlu.model.Blog;
import net.dreamlu.model.Gambit;
import net.dreamlu.model.GroupAnnouncement;
import net.dreamlu.model.GroupClassification;
import net.dreamlu.model.MemberMoney;
import net.dreamlu.model.MyFavorite;
import net.dreamlu.model.MyFriends;
import net.dreamlu.model.MyFriendsGroup;
import net.dreamlu.model.TUser;
import net.dreamlu.model.UserGroup;
import net.dreamlu.utils.CloudUtils;

public class WebConfig extends JFinalConfig {

	// 开发模式
	private boolean isdev = false;

	@Override
	public void configConstant(Constants me) {
		// 加载配置文件
		loadPropertyFile("config.txt");

		isdev = getPropertyToBoolean("isdev", false);
		me.setDevMode(isdev);

		// 修改文件上传的默认限制
		me.setMaxPostSize(1024 * 1024 * 200);
//		me.setUploadedFileSaveDirectory("test");
		
		// 设置Slf4日志
		me.setLogFactory(new Slf4jLogFactory());
		// beetl模版配置工厂
		BeetlRenderFactory beetlRenderFactory = new BeetlRenderFactory();
		me.setMainRenderFactory(beetlRenderFactory);

		// JFinal-weixin设置
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(true);
		
		// 401,403,404,500错误代码处理
		me.setError401View("/common/404.html");
		me.setError403View("/common/404.html");
		me.setError404View("/common/404.html");
		me.setError500View("/common/404.html");
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "front");
		me.add("/image", ImageController.class, "front/image");
		me.add("/ueditor", UeditorController.class, "front/ueditor");
		
		// ueditor api包：http://git.oschina.net/596392912/JFinal-ueditor
		me.add("/ueditor/api", UeditorApiController.class);

		me.add("/ajax", AjaxController.class);
		me.add("/announcement", GroupAnnouncementController.class);
		me.add("/gambit", GambitController.class);
		me.add("/groupClassification", GroupClassificationController.class);
		me.add("/userGroup", UserGroupController.class);
		me.add("/myFriendsGroup", MyFriendsGroupController.class);
		me.add("/myFriends", MyFriendsController.class);
		me.add("/article", ArticleController.class);
		me.add("/articleComment", ArticleCommentController.class);
		me.add("/articlePraise", ArticlePraiseController.class);
		me.add("/myFavorite", MyFavoriteController.class);
		me.add("/memberMoney", MemberMoneyController.class);
		me.add("/test", TestController.class, "front");

		// beetl测试
		me.add("/beetl", BeetlController.class, "front");
		me.add("/email", EmailController.class);

		// 管理后台控制器
		me.add("/admin", AdminController.class);
		
		// JFinal-weixin控制器
		me.add("/weixin/msg", WeixinMsgController.class);
		me.add("/weixin/api", WeixinApiController.class, "/api");
	}

	@Override
	public void configHandler(Handlers me) {
		// 改用JFinal自带的UrlSkipHandler
//		me.add(new StaticHandler("/static"));
		me.add(new UrlSkipHandler("/static", false));
		me.add(new DruidStatViewHandler("/druid"));
//		me.add(new FakeStaticHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
//		  me.add(new POST());
	}

	@Override
	public void configPlugin(Plugins me) {
		boolean inMopaas = CloudUtils.isInMopaas();
		
		// 数据库信息
		String masterJdbcUrl, masterUser, masterPassword;
		if (inMopaas) {
			masterJdbcUrl  = "mysql://b110ae14-0f98:f58afc78-4a5c@127.0.0.1:3306/ddc2a754cf48a42c19c37953770757726?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			masterUser     = "b110ae14-0f98";
			masterPassword = "b110ae14-0f98";
		} else {
			masterJdbcUrl  = getProperty("db.jdbcUrl");
			masterUser     = getProperty("db.user");
			masterPassword = getProperty("db.password");
		}
		
		// 主配置Druid数据库连接池插件
		DruidPlugin masterDruidPlugin = new DruidPlugin(masterJdbcUrl, masterUser, masterPassword);
		masterDruidPlugin.addFilter(new StatFilter()).addFilter(new Slf4jLogFilter());
		WallFilter masterWall = new WallFilter();
		masterWall.setDbType("mysql");
		masterDruidPlugin.addFilter(masterWall);
		me.add(masterDruidPlugin);
		// 主配置ActiveRecord插件
		ActiveRecordPlugin masterArp = new ActiveRecordPlugin("master", masterDruidPlugin);
		masterArp.addMapping("t_user", "user_id", TUser.class);
		masterArp.addMapping("group_announcement", "id", GroupAnnouncement.class);
		masterArp.addMapping("gambit", "id", Gambit.class);
		masterArp.addMapping("group_classification", "id", GroupClassification.class);
		masterArp.addMapping("user_group", "group_id", UserGroup.class);
		masterArp.addMapping("my_friends_group", "id", MyFriendsGroup.class);
		masterArp.addMapping("my_friends", "id", MyFriends.class);
		masterArp.addMapping("article", "article_id", Article.class);
		masterArp.addMapping("yx_article_comment", "comment_id", ArticleComment.class);
		masterArp.addMapping("yx_article_praise", "praise_id", ArticlePraise.class);
		masterArp.addMapping("yx_my_favorite", "id", MyFavorite.class);
		masterArp.addMapping("yx_member_money", "user_id", MemberMoney.class);
		masterArp.addMapping("blog", Blog.class);
		masterArp.setShowSql(isdev);
		me.add(masterArp);
		
		SqlReporter.setLog(true);
		
//		// 从数据源
//		String slaveJdbcUrl  = getProperty("db.slave.jdbcUrl");
//		String slaveUser     = getProperty("db.slave.user");
//		String slavePassword = getProperty("db.slave.password");
//		DruidPlugin slaveDruidPlugin = new DruidPlugin(slaveJdbcUrl, slaveUser, slavePassword);
//		slaveDruidPlugin.addFilter(new StatFilter()).addFilter(new Slf4jLogFilter());
//		WallFilter slaveWall = new WallFilter();
//		slaveWall.setDbType("mysql");
//		slaveDruidPlugin.addFilter(slaveWall);
//		me.add(slaveDruidPlugin);
//		// 从ActiveRecord插件
//		ActiveRecordPlugin slaveArp = new ActiveRecordPlugin("slave", slaveDruidPlugin);
//		slaveArp.addMapping("t_user", "id", net.dreamlu.pojo.TUser.class);
//		slaveArp.addMapping("blog", net.dreamlu.pojo.Blog.class);
//		slaveArp.setShowSql(isdev);
//		me.add(slaveDruidPlugin);

		// ehcahce插件配置
		me.add(new EhCachePlugin());
	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		//设置文件上传重命名策略
		OreillyCos.setFileRenamePolicy(new UpFileRenamePolicy());

		// 在JFinal启动时，beetl变量中加入启动时间 ${startTime!, "yyyy-MM-dd HH:mm:ss"}
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		String startTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		sharedVars.put("startTime", startTime);

		BeetlRenderFactory.groupTemplate.setSharedVars(sharedVars);
	}

}