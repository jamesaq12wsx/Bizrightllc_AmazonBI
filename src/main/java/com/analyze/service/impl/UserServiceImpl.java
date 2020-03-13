package com.analyze.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.analyze.Authority.MemoryData;
import com.analyze.dao.UserMapper;
import com.analyze.model.User;
import com.analyze.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * @author
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return 用户记录的数量，0表示用户不存在
	 */
	public User login(String userName, String password) {
		return userMapper.login(userName, password);
	}
	public int deleteByPrimaryKey(Integer userid){
		return userMapper.deleteByPrimaryKey(userid);
	}

	public int insert(User record){
		return userMapper.insert(record);
	}

	public int insertSelective(User record){
		return userMapper.insertSelective(record);
	}

	public User selectByPrimaryKey(Integer userid){
		return userMapper.selectByPrimaryKey(userid);
	}

	public int updateByPrimaryKeySelective(User record){
		return userMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(User record){
		return userMapper.updateByPrimaryKey(record);
	}
	@Override
	public void setSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	@Override
	public void singleLogin(HttpServletRequest request, String username) {
		String sessionID = request.getRequestedSessionId();
		if (!MemoryData.getSessionIDMap().containsKey(username)) {// 不存在，首次登陆，放入Map
			MemoryData.getSessionIDMap().put(username, sessionID);
		} else if (MemoryData.getSessionIDMap().containsKey(username)
				&& !StringUtils.equals(sessionID, MemoryData.getSessionIDMap().get(username))) {
			MemoryData.getSessionIDMap().remove(username);
			MemoryData.getSessionIDMap().put(username, sessionID);
		}

	}
	@Override
	public List<Map<String, Object>> selectLabelList(String userName) {
		
		return userMapper.selectLabelList(userName);
	}

}
