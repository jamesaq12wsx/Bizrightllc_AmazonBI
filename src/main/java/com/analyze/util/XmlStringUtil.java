package com.analyze.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * 
 * @author zql
 * @time 2016-11-21 14:15:57
 * XMl解析工具类
 */
public class XmlStringUtil {
	public Map<Object, Object> parse(String protocolXML) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));

			Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				for (int i = 0; i < books.getLength(); i++) {
					Node book = books.item(i);
					map.put(book.getNodeName(), book.getFirstChild().getNodeValue());
					System.out.println("节点=" + book.getNodeName() + "\t value=" + book.getFirstChild().getNodeValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String args[]) {
		String xxx = "<xml>\n" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[帐号余额不足，请用户充值或更换支付卡后再支付.]]></return_msg>"
				+ "<result_code><![CDATA[FAIL]]></result_code>" + "<err_code><![CDATA[NOTENOUGH]]></err_code>"
				+ "<err_code_des><![CDATA[帐号余额不足，请用户充值或更换支付卡后再支付.]]></err_code_des>" + "</xml>";
		// String s = "<xml>\n<return_code><![CDATA[SUCCESS]]></return_code>\n</xml>";
		System.out.println("转换前：" + xxx);
		xxx = xxx.replaceAll("\r|\n", "");
		System.out.println("转换后：" + xxx);
		long begin = System.currentTimeMillis();
		XmlStringUtil c = new XmlStringUtil();
		Map<Object, Object> map = c.parse(xxx);
		System.out.println(map.get("openid"));
		long after = System.currentTimeMillis();
		System.out.println("DOM用时" + (after - begin) + "毫秒");
	}
}