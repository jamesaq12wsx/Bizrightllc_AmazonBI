package com.analyze.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.druid.support.http.WebStatFilter;
/**
 * 阿里巴巴数据库连接池监控页过滤，只有我能访问
 * @author Administrator
 *
 */
public class DruidFilter extends WebStatFilter{

	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		response.setCharacterEncoding("utf-8");
		String url = request.getRequestURI();

		if (url.indexOf("druid")>0) {
			if (!checkLogin(session, request, response)) {
				return;
			}else {
				String userid = request.getSession().getAttribute("userid").toString();
				if (!"22940".equals(userid)) {
					return;
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
			 return false;

		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}
	
}
