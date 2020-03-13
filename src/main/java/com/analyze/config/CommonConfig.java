package com.analyze.config;

import com.analyze.util.PropertiesUtils;

import java.util.Properties;

/**
 * 基本信息配置
 */
public class CommonConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 促销文件下载目录
	public static String PROMOTION_UPLOAD_FILE_PATH = "";
	/**
	 * 初始化一些变量
	 */
	static {
		try {
			Properties p = PropertiesUtils.getProperties("common.properties");
			PROMOTION_UPLOAD_FILE_PATH=p.getProperty("promotion.download.file.path");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

