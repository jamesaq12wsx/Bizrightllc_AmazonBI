package com.analyze.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PageEncodingFilter implements Filter {

	private String adg;

	public PageEncodingFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(this.adg);
		response.setCharacterEncoding(this.adg);
		System.out.println("经过了编码过滤器....  ...");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fcg) throws ServletException {
		this.adg = fcg.getInitParameter("encoding");
	}

}
