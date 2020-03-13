package com.analyze.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;

import com.analyze.dao.UserMapper;
import com.analyze.model.User;

import com.analyze.service.UserService;

import com.analyze.util.MD5Util;
import com.analyze.util.TimeUtil;


/**
 * 2018-04-18
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	public static final String userNameKey = "o";
	public static final String passWordKey = "p";
	public int wrongCount = 0;

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;


	/**
	 * 电商鹰眼登陆
	 * 
	 * @param request
	 * @param response
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {

		try {
			// 后台校验用户的合法性
			User user = userService.login(username, MD5Util.getMD5(password));
			if (user != null) {
				// 设置不变值 final_userid
				userService.setSession(request, "final_userid", user.getUserid());
				userService.setSession(request, "final_username", user.getUsername());
				userService.setSession(request, "userid", user.getUserid());
				userService.setSession(request, "username", user.getUsername());
				userService.setSession(request, "allcount", user.getSubaccount());
				userService.setSession(request, "deadline", user.getDeadline());
				userService.setSession(request, "is_parent", user.getIsParent());
				userService.setSession(request, "is_edit", user.getIsEdit());
				userService.setSession(request, "accountType", user.getAccountType());
				userService.setSession(request, "user", user);
				userService.setSession(request, "userid", user.getUserid());
				// 账号限制只能在一处设备登陆
				userService.singleLogin(request, username);
				
				
				//查询sku标签列表
				List<Map<String, Object>> labelList  = userService.selectLabelList(username);
				userService.setSession(request, "labelList", labelList);
				System.out.println(labelList.toString());
				

				return "redirect:/b_dashboard.htm"; 

			} else {
				request.setAttribute("tip", "校验失败，请检查用户名或密码！");

				return "production/login";
			}

		} catch (Exception e) {
			Calendar cal = Calendar.getInstance();
			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			log.error(username + "   " + now + "  登录过程中出现了异常：" + e.getMessage());
			return "redirect:/500.htm";
		}

	}

	// 退出系统
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut(HttpServletRequest request) {
		request.getSession().invalidate();// 销毁
		return "redirect:/login.htm";
	}
	
	@RequestMapping(value = "/getUserAccount", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("isParent", request.getSession().getAttribute("userid"));
			List<Object> list = userMapper.getUserAccount(map);
			resultMap.put("data", list);
			resultMap.put(STATUS, SUCCESS);
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/addUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object addUserAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=getRequestParams(request);
			map.put("untreatedpassword", MD5Util.getMD5(map.get("password").toString()));
			map.put("isParent", request.getSession().getAttribute("userid"));
			map.put("parentName", request.getSession().getAttribute("username"));
			map.put("isEdit", "0");
			map.put("inserttime", new Date());
			int i=userMapper.addUserAccount(map);
			if(i>0){
				List<Object> list = userMapper.getUserAccount(map);
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
			}
			
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/editUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object editUserAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=getRequestParams(request);
			map.put("untreatedpassword", MD5Util.getMD5(map.get("password").toString()));
			int i=userMapper.editUserAccount(map);
			if(i>0){
				map.put("isParent", request.getSession().getAttribute("userid"));
				List<Object> list = userMapper.getUserAccount(map);
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
			}
			
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/delUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object delUserAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=getRequestParams(request);
			int i=userMapper.delUserAccount(map);
			if(i>0){
				map.put("isParent", request.getSession().getAttribute("userid"));
				List<Object> list = userMapper.getUserAccount(map);
				resultMap.put("data", list);
				resultMap.put(STATUS, SUCCESS);
			}else{
				resultMap.put(STATUS, FAIL);
			}
			
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping(value = "/getAccountByuser", method = RequestMethod.GET)
	@ResponseBody
	public Object getAccountByuser(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("isParent", request.getSession().getAttribute("userid"));
			List<Object> list = userMapper.getAccountByuser(map);
			return list;
		} catch (Exception e) {
			resultMap.put(STATUS, FAIL);
			resultMap.put(MSG, e.getMessage());
			return resultMap;
		}
	}
}
