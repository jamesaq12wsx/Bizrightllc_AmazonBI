//package com.roman.util;
package com.analyze.util;
import sun.misc.BASE64Decoder;

public class URLParamUtil {
	
	/**
	 * 解密username
	 * @param str
	 * @return
	 */
	public static String getRealUsername(String str) {
		String dec_str = getFromBase64(str.replace("%3D", "").replace("-", "+").replace("_", "/"));
		char[] key = {'K'};
		char[] p = dec_str.toCharArray();
		char[] rs = new char[p.length];
		for(int i = 0; i < p.length; i++){
			int ch = p[i] ^ key[0];
			rs[i] = (char)ch;
		}
		String real_str = new String(rs);
		if(real_str.length() > 11){
			real_str = real_str.substring(0, 11);
		}
		return real_str;
	}
	
	/**
	 * base64界面，
	 * @param s
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getFromBase64(String s) {
		s = s.replace("-", "+").replace("_", "/"); // 修改字符
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try 
			{
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 测试    username=enh7cnh7f3J6fHg%3D，解密后13093049173
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getRealUsername("enh7cnh7f3J6fHg%3D"));
	}
}
