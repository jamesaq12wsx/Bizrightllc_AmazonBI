package com.analyze.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class Token {
	
	// 系统properties文件名称
    //private final static String CONFIG = "MsgPushConfig";
    // 获取访问权限码URL
    private final static String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private static String CorpID="wx1809c5ed681f4cd9";
	private static String Secret="96bj3exEvWkJHIQLsJXTc0dm13ThPLTQvqthYdoahQb-enm4a4W66bQ_4bkaE8M2";
    
	/**
	 * @getAccessToken 获取调用微信接口的标识,接口访问权限码;
	 * @return
	 */

	public  static String getAccessToken() throws Exception {
		// 获取配置文件中的值
		/*String CORPID = Properties.getValue(CONFIG, "CorpID", "");
		String CORPSECRET = Properties.getValue(CONFIG, "Secret", "");*/
		//
		HttpClient client = new HttpClient();
		
		
		String result=ACCESS_TOKEN_URL+"?"+"corpid="+CorpID+"&"+"corpsecret="+Secret;
		PostMethod post = new PostMethod(result);
		post.releaseConnection();
		try {
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
			//System.out.println("这是post返回过后的结果"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将数据转换成json
		JSONObject jasonObject = JSONObject.fromObject(result);
		result = jasonObject.get("access_token").toString();

		post.releaseConnection();
		return result;

	}
	public static void main(String[] args) {
		try {
			getAccessToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
