package com.analyze.util;

import java.io.UnsupportedEncodingException;

/**
 * 浏览器编码转中文
 * 
 * @author Administrator
 *
 */
public class CodeUtil {
	public static String tranCode(String str) {
		try {
			return java.net.URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
