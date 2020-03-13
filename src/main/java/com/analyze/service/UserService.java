package com.analyze.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import com.analyze.model.User;

public interface UserService {
	int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    User login(String userName, String password);
	/**
	 * 设置session Key Value
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public void setSession(HttpServletRequest request, String key, Object value);

	/**
	 * 账号限制只能一处登陆
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public void singleLogin(HttpServletRequest request, String username);
	
	public List<Map<String, Object>> selectLabelList(String userName);

}
