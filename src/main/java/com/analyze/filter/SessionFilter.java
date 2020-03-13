package com.analyze.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;


import com.analyze.service.UserService;


/**
 * 过滤器
 * 
 * @author wei
 *
 */
public class SessionFilter implements Filter {

	private static Logger logger = Logger.getLogger(SessionFilter.class);

	private UserService userService;

	/**
	 * 初始化
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext sc = filterConfig.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(sc);

		if (cxt != null && cxt.getBean("userService") != null && userService == null)
			userService = (UserService) cxt.getBean("userService");
	}

	/**
	 * 
	 * 逻辑：除了登陆页面，所有页面或者后台请求都需要验证用户是否已经登陆，未登陆则不能请求成功
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String url = request.getRequestURI();

		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";
		
		/*if(url.indexOf("a_operationAnalysis")>0){
			chain.doFilter(req, res);
			return;
		}*/
		// 如果访问的是一个jsp页面，但是并不是登陆页面，那么进行拦截
		if (url.indexOf(".jsp") > 0 && url.indexOf("login.jsp") < 0 && url.indexOf("404.jsp") < 0
				&& url.indexOf("500.jsp") < 0 && url.indexOf("AllMonitoringDetailNew.jsp") < 0) {
			if (!checkLogin(session, request, response)) {
				response.sendRedirect(basePath + "login.htm");
				return;
			}

		}

		if (url.indexOf("druid") > 0) {
			if (!checkLogin(session, request, response)) {
				response.sendRedirect(basePath);
				return;
			} else {
				String userid = request.getSession().getAttribute("userid").toString();
				if (!"1".equals(userid)) {
					return;
				}
			}

		}

		// 所有后台请求都需要验证登陆权限(if语句内的排除在外)
		if (url.indexOf(".htm") > 0 && url.indexOf("login.htm") < 0 && url.indexOf("userLogin.htm") < 0
				&& url.indexOf("goLogin.htm") < 0 && url.indexOf("queryShopMonitoringDetail") < 0
				&& url.indexOf("queryComMonitoringDetail") < 0 && url.indexOf("queryFreMonitoringDetail") < 0
				&& url.indexOf("queryMonitoringGoodsDetailNew") < 0 && url.indexOf("queryFrequencyMonitoringDetail") < 0
				&& url.indexOf("getShopListByUserId") < 0 && url.indexOf("addnovip") < 0 && url.indexOf("checkIsre") < 0
				&& url.indexOf("queryComMonitoring") < 0 && url.indexOf("mysession") < 0 && url.indexOf("500") < 0
				&& url.indexOf("404") < 0 && url.indexOf("notify_ElectronicBusinessEye") < 0
				&& url.indexOf("checkDrag") < 0 && url.indexOf("addShopMonitoringMoreGoodsNum") < 0) {

			if (!checkLogin(session, request, response)) {
				response.sendRedirect(basePath + "login.htm");
				return;
			} else {
				// 判断是否是free账户，如果是，那么要在这里控制下添加编辑等权限，必须拦截
				String is_edit = request.getSession().getAttribute("is_edit").toString();
				if ("0".equals(is_edit)) {
					//所有子账户都没有添加编辑删除权限
					if (url.indexOf("addProduct") > 0 || url.indexOf("removeProduct") > 0 || // 单品监控设置
							url.indexOf("addFrequent") > 0 || url.indexOf("editFrequent") > 0 || // 高频监控设置
							url.indexOf("addShopMonitoring") > 0 || url.indexOf("updateShopMonitoring") > 0 || // 店铺对手管理
							url.indexOf("insertUserCategory") > 0||url.indexOf("insertUserCategory_Tqg") > 0||url.indexOf("insertUserCategory_Tejia") > 0||// 活动分析大盘走势
							//拦截一些页面(个人中心..)
							url.indexOf("g_buyService")>0||url.indexOf("g_buyRecord")>0||url.indexOf("g_modifyPassword")>0||url.indexOf("g_platformBulletin")>0||url.indexOf("g_settingWeChat")>0||
							url.indexOf("g_subaccount")>0
							) 
					{
						return;
					}
				}
			}
		}

		// 除了注册和登陆页面，其他页面都要验证是否已经登陆
		chain.doFilter(req, res);
	}

	/**
	 * 该方法用户验证用户是否已经登录，如果没有的话从cookie中获取
	 * 
	 * @param session
	 * @return
	 */
	private boolean checkLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		if (session != null) {
			if (session.getAttribute("userid") != null && !"".equals(session.getAttribute("userid"))) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;// 不自动登录哦
		}
	}

	/**
	 * 销毁时
	 */
	public void destroy() {

	}

}
