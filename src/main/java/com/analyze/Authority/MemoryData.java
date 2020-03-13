package com.analyze.Authority;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放账号和sessionID的对应关系，账号只能在一处登陆的限制
 * 
 * @author Administrator
 *
 */
public class MemoryData {
	private static Map<String, String> sessionIDMap = new HashMap<String, String>();

	public static Map<String, String> getSessionIDMap() {
		return sessionIDMap;
	}

	public static void setSessionIDMap(Map<String, String> sessionIDMap) {
		MemoryData.sessionIDMap = sessionIDMap;
	}

}
