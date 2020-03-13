

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;

import com.analyze.util.HttpClientCommon;

public class TestCraw {
		public static void main(String[] args) {
			try {

				// 先登陆到系统中，拿到cookie
				HttpClient httpClient = new HttpClient();//先创建一个http对象，对象不变，相当于同一个浏览器
				
				//1、第一步先登录到系统
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("username", "13758127834");
				paramMap.put("password", "13758127834");
				HttpClientCommon.doPost(httpClient, paramMap, null, "http://yingyan.cy1788.cn:8088/analyze/user/userLogin.htm", 5000, 5000, "utf-8");
				System.out.println("登录成功！");
				
//				//2、爬取系统的任意数据
				String result = HttpClientCommon.doGet(httpClient, paramMap, null, "http://yingyan.cy1788.cn:8088/analyze/shop/findAllShopList.htm", 5000, 5000, "utf-8");
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
}
