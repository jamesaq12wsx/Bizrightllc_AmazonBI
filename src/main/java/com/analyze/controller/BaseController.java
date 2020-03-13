package com.analyze.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
/**
 * BaseController  灏佽璇锋眰鍙傛暟 
 * @author Wei
 *
 */
@Controller
public class BaseController extends MultiActionController {

	protected static final String STATUS = "status";

	protected static final String SUCCESS = "1";

	protected static final String FAIL = "0";

	protected static final String MSG = "msg";

	public HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}
	
	
	
	/**
	 * 获取远程请求Ip
	 * 
	 * @param request
	 * @return
	 */
	public String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	
	/**
	 * 椤甸潰璺宠浆鍏叡鏂规硶
	 * @param request
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/{url}", method = RequestMethod.GET)
	public String go(HttpServletRequest request,@PathVariable String url) {
		
		return "production/"+url;
	}


	protected Map<String, Object> getRequestParams(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> requestParams = new HashMap<String, Object>();
		Map<String, String[]> paramsMap = request.getParameterMap();
		Iterator<Entry<String, String[]>> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> e = it.next();
			String key = e.getKey();
			String[] vaA = e.getValue();
			String valueS = null;
			for (String p : vaA) {
				if (null == valueS) {
					valueS = p;
				} else {
					valueS += ("," + p);
				}
			}
			requestParams.put(key, valueS);
		}
		requestParams.put("isParent", request.getSession().getAttribute("is_parent"));
		requestParams.put("username", request.getSession().getAttribute("username"));
		requestParams.put("userId", request.getSession().getAttribute("userid"));
		return requestParams;
	}
	
	
	protected Map<String, String> getRequestParamsAliCallBack(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, String> requestParams = new HashMap<String, String>();
		Map<String, String[]> paramsMap = request.getParameterMap();
		Iterator<Entry<String, String[]>> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> e = it.next();
			String key = e.getKey();
			String[] vaA = e.getValue();
			String valueS = null;
			for (String p : vaA) {
				if (null == valueS) {
					valueS = p;
				} else {
					valueS += ("," + p);
				}
			}
			requestParams.put(key, valueS);
		}
		return requestParams;
	}

}
