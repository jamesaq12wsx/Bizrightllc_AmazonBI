package com.analyze.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.analyze.Authority.MemoryData;

/**
 * 定义一个拦截器
 * @author Administrator
 *
 */
public class SingleUserInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		//如果拦截到的是登录的页面的话放行
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath() + "/";
		HttpSession session = request.getSession(false);
		
		
		
		// 如果访问的是一个jsp页面，但是并不是登陆页面，那么进行拦截
		if (url.indexOf(".jsp") > 0 && url.indexOf("login.jsp") < 0 && url.indexOf("404.jsp") < 0
				&& url.indexOf("500.jsp") < 0 && url.indexOf("AllMonitoringDetailNew.jsp") < 0) {
			if (!checkLogin(session, request, response)) {
				response.sendRedirect(basePath+"login.htm");
				return false;
			}

		}


		// 所有后台请求都需要验证登陆权限(if语句内的排除在外)
		if (url.indexOf(".htm") > 0 &&url.indexOf("login.htm")<0&& url.indexOf("userLogin.htm") < 0 && url.indexOf("queryShopMonitoringDetail") < 0
				&& url.indexOf("queryComMonitoringDetail") < 0 && url.indexOf("queryFreMonitoringDetail") < 0
				&& url.indexOf("queryMonitoringGoodsDetailNew") < 0 && url.indexOf("queryFrequencyMonitoringDetail") < 0
				&& url.indexOf("getShopListByUserId") < 0 && url.indexOf("addnovip") < 0 && url.indexOf("checkIsre") < 0
				&& url.indexOf("queryComMonitoring") < 0 && url.indexOf("mysession") < 0 && url.indexOf("500") < 0
				&& url.indexOf("404") < 0 && url.indexOf("notify_ElectronicBusinessEye") < 0 && url.indexOf("checkDrag")<0 &&url.indexOf("addShopMonitoringMoreGoodsNum")<0) {

			if (!checkLogin(session, request, response)) {
				response.sendRedirect(basePath+"login.htm");
				return false;
			}
		}
		
		
		return true;
	}
	
	
	private boolean checkLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (session != null) {
			if (session.getAttribute("userid") != null && !"".equals(session.getAttribute("userid"))) {
				String sessionid = MemoryData.getSessionIDMap().get(request.getSession().getAttribute("username").toString());
				if (sessionid.equals(request.getSession().getId())) {
					return true;
				}else {
					return false;
				}
				
			} else {
				return false;
			}

		} else {
			 return false;//不自动登录哦
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
