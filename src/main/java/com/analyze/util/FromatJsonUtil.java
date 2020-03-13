package com.analyze.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化json工具类
 * 格式化json串中特殊字符\t \n
 * @author zql
 * @time 2016-11-29 09:55:46
 *
 */
public class FromatJsonUtil {
	/**
	 * 转换json特殊字符 \t \n 这种特殊字符
	 * @param str json字符串
	 * @return
	 */
	public static String replaceBlank(String str) {		
        String dest = "";		
        if (str!=null) {		
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");		
            Matcher m = p.matcher(str);		
            dest = m.replaceAll("");
        }		
        return dest;		
	 }
	public static void main(String[] args) throws Exception {		
		/*Properties pro = ProperTiesUtil.apiurl();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "zql");
		map.put("password", "e10adc3949ba59abbe56e057f20f883e");
		String url = pro.get("path").toString() + pro.get("login");
		String json = HttpUtil.post(url, map);*/
		
		/*String json="{\n\t\'Msg\':{\n\t\t\'userlevel\':1,\n\t\t\'phone\':\'15939007534\',\n\t\t\'lname\':\'VIP1\',\n\t\t\'viplevel\':1,\n\t\t\'userid\':10000,\n\t\t\'user_status\':0,\n\t\t\'password\':\'e10adc3949ba59abbe56e057f20f883e\',\n\t\t\'vipendtime\':\'2099-11-28 16:50:19\',\n\t\t\'vipstarttime\':\'2016-11-28 16:50:14\',\n\t\t\'inserttime\':\'2016-11-21 17:06:04\',\n\t\t\'username\':\'zql\',\n\t\t\'address\':\'上海闵行区\',\n\t\t\'wechat\':\'z765939377\',\n\t\t\'qq\':\'765939377\'\n\t},\n\t\'code\':\'000000\'\n}";
		String js=replaceBlank(json);		
		System.out.println(replaceBlank(json));
		Map<String,Object> mm=	JSONObject.parseObject(js);	
		System.out.println(mm.get("Msg"));*/
		
		
		  	String sParam = "Wed Nov 30 00:00:00 CST 2016";  
	        SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);  
	        Date d = sdf.parse(sParam);  
	        System.out.println("1."+ d);  
	        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
	        System.out.println("2."+sdf1.format(d)); 
	        
	        String sParam1 = "Sat Jan 30 00:00:00 CST 2016";  
	        SimpleDateFormat sdf11 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);  
	        Date d1 = sdf11.parse(sParam1);  
	        System.out.println("1."+ d1);  
	        SimpleDateFormat sdf111 = new SimpleDateFormat("yyyy-MM-dd");  
	        System.out.println("2."+sdf111.format(d1)); 
		
	}
}
