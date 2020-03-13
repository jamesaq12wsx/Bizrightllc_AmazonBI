package com.analyze.util;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
/**
 * 
 * @author zql
 * @time 2016-11-21 14:20:55
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	private static final OkHttpClient CLIENT = new OkHttpClient();

	public static String get(String url) throws Exception {
		String data = "";
		try {
			CLIENT.setProxy(null);
			Request request = new Request.Builder().url(url).addHeader("cookie", "v=0; uc3=sg2=VWogacW9yW4j1aEpyxkzgut9EP%2BeaGlTg4Cf2Z5UnJI%3D&nk2=r4BO%2BdhE&id2=UoH8WAdywRX0Gw%3D%3D&vt3=F8dBzLgrry80%2BjOlGTU%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; existShop=MTUxNTk5NzU0NQ%3D%3D; lgc=%5Cu9676%5Cu9633%5Cu5A01; tracknick=%5Cu9676%5Cu9633%5Cu5A01; cookie2=19c910bd11188686491143d7b4716928; skt=7057b23a266e0136; t=5bda9cfc41faed4906e0a12e363d9c35; _cc_=WqG3DMC9EA%3D%3D; tg=0; l=AgIC/KgIe607jG9XWCiEORln0gNkiwbt; mt=ci=5_1; enc=YgiMvpxsJo1zGoDA%2BXjdpV1ac1o5Nji1cV4HT35Ejy42kKEow5q84FVo3VVvgaFLcfmukIHy%2FREsxaMn2pIJvg%3D%3D; _tb_token_=55e03e7e4ea35; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; whl=-1%260%260%261516010384623; _m_h5_tk=f3363ad12c1da1717c1457cb280a3f4d_1516076370461; _m_h5_tk_enc=dda6aee99add68aa84776e24b8821988; cna=ITXjEv3AbAUCAWXNKYn6+dHw; uc1=cookie14=UoTdfYX4IH4wyg%3D%3D&cookie15=W5iHLLyFOGW7aA%3D%3D; swfstore=13455; JSESSIONID=4E5BD5579A9B24FD951EDC8FE60A3384; Hm_lvt_96d9d92b8a4aac83bc206b6c9fb2844a=1516095018; Hm_lpvt_96d9d92b8a4aac83bc206b6c9fb2844a=1516095337; Hm_lvt_cdca62f337ad44ec441f4f40b393c2c7=1516095018; Hm_lpvt_cdca62f337ad44ec441f4f40b393c2c7=1516095338; isg=AioqgRPJBLgX5Yt7jgf4cBkme5AMM7whZUkGOrTj1n0I58qhnCv-BXAVAyWA; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0").build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request).execute();
			data = response.body().string();
		} catch (Exception e) {
			throw e;
		}
		return data;
	}
	
	public static String get(String url,Map<String, Object> dataMap) throws Exception {
		StringBuilder builder=new StringBuilder(url);
		if (null!=dataMap&&!dataMap.isEmpty()) {
			Iterator<String> iterator=dataMap.keySet().iterator();
			int index=0;
			while (iterator.hasNext()) {
				String key=iterator.next();
				String value="";
				Object valueTemp=dataMap.get(key);
				if (null==valueTemp) {						//值为空
					continue;
				}else if (valueTemp instanceof Number) {	//值是数字(int，double等等)
					value+=valueTemp;
				}else if(valueTemp instanceof String) {		//值是字符串
					value=(String)valueTemp;
				}else {										//其他，忽略
					logger.error("cant convert object or its null");
					continue;
				}
				key=key.trim();
				value=value.trim();
				if (0==index) {
					builder.append("?").append(key).append("=").append(value);
				}else {
					builder.append("&").append(key).append("=").append(value);	
				}
				index++;
			}
		}
		logger.info("GET "+builder.toString());
		String data = "";
		try {
			Request request = new Request.Builder().url(builder.toString()).build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request).execute();
			data = response.body().string();
		} catch (Exception e) {
			logger.error("HTTP get error, download url:" + url
					+ ", error message:" + e.getMessage());
			throw e;
		}
		return data;
	}
	
	/**
	 * 下载图片，返回byte[]
	 */
	public static byte[] getPicutre(String url) throws Exception {
		logger.info("GET "+url);
		byte[] data;
		try {
			Request request = new Request.Builder().url(url).build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request).execute();
			data=response.body().bytes();
		} catch (Exception e) {
			logger.error("HTTP get error, download url:" + url
					+ ", error message:" + e.getMessage());
			throw e;
		}
		return data;
	}
	
	public static String post(String url,String jsonData) throws Exception {
		logger.info("POST "+url+" , with json: "+jsonData);
		
		RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);
		String data = "";
		try {
			Request request = new Request.Builder().url(url).post(body).build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request)
					.execute();
			data = response.body().string();
		} catch (Exception e) {
			logger.error("HTTP get error, download url:" + url
					+ ", error message:" + e.getMessage());
			throw e;
		}
		return data;
	}
	
	public static String post(String url,byte[] content) throws Exception {
		logger.info("POST "+url);
		
		RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data;"), content);
		String data = "";
		try {
			Request request = new Request.Builder().url(url).post(body).build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request)
					.execute();
			data = response.body().string();
		} catch (Exception e) {
			logger.error("HTTP get error, download url:" + url
					+ ", error message:" + e.getMessage());
			throw e;
		}
		return data;
	}
	
	public static String post(String url,Map<String, Object> dataMap) throws Exception {
		logger.info("POST "+url);
		FormEncodingBuilder builder=new FormEncodingBuilder();
		if (null!=dataMap&&!dataMap.isEmpty()) {
			Iterator<String> iterator=dataMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key=iterator.next();
				String value="";
				Object valueTemp=dataMap.get(key);
				if (null==valueTemp) {						//值为空
					continue;
				}else if (valueTemp instanceof Number) {	//值是数字(int，double等等)
					value+=valueTemp;
				}else if(valueTemp instanceof String) {		//值是字符串
					value=(String)valueTemp;
				}else {										//其他，忽略
					logger.warn("cant convert object or its null ");
					continue;
				}
				key=key.trim();
				value=value.trim();
				builder.add(key, value);
			}
		}
		RequestBody body=builder.build();
		String data = "";
		try {
			Request request = new Request.Builder().url(url).post(body).build();
			com.squareup.okhttp.Response response = CLIENT.newCall(request)
					.execute();
			data = response.body().string();
		} catch (Exception e) {
			logger.error("HTTP get error, download url:" + url
					+ ", error message:" + e.getMessage());
			throw e;
		}
		return data;
	}
}
