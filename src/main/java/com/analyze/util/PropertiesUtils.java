package com.analyze.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Properties配置文件帮助类
 * 
 * 
 */
public class PropertiesUtils {

	/**
	 * 根据文件名获得properties配置
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Properties getProperties(String fileName) {

		InputStream instream = null;
		Properties properties = null;
		try {
			instream = PropertiesUtils.class.getClassLoader()
					.getResourceAsStream(fileName);
			properties = new Properties();
			properties.load(new InputStreamReader(instream, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return properties;
	}

	/**
	 * 根据配置文件名和key获得value
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getProperty(String fileName, String key) {
		try {
			String value = getProperties(fileName).getProperty(key.trim());
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String switchPropertyValue(String value) throws Exception {

		if (value == null) {
			return value;
		}

		return new String(value.getBytes("ISO8859-1"), "UTF-8");
	}

	public static void main(String[] args) {
		
		  Properties p = getProperties("jdbc.properties");
		  System.out.println(p.get("jdbc.url").toString());
		  System.out.println(p.get("jdbc.username").toString());
		
		
	}
}
