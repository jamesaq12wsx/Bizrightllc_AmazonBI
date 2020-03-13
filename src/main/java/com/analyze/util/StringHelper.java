package com.analyze.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

//import com.enterprise.support.utility.Validation;


public class StringHelper {

	public static String getResultByReg(String content, String reg) {
		List<String> list = new ArrayList<String>();
		Pattern pa = Pattern.compile(reg, Pattern.DOTALL);
		Matcher ma1 = pa.matcher(content);
		if (ma1.find()) {
			list.add(ma1.group(1));
			return list.get(0);
		} else {
			return null;
		}
	}

/*	public static List<String> getResultListByReg(String content, String reg) {
		List<String> list = new ArrayList<String>();
		Pattern pa = Pattern.compile(reg, Pattern.DOTALL);
		Matcher ma1 = pa.matcher(content);
		while (ma1.find()) {
			list.add(ma1.group(1));
		}
		if(Validation.isEmpty(list)){
			return null;
		}else{
			return list;
		}
	}*/
	
	//��������Ϊ����
	public static String convertNumToString(String s) {
		String result = "";
		String[] aa = s.replace("&nbsp;", "").split(";");
		for (int i = 0; i < aa.length; i++) {
			if (aa[i].indexOf("&#") >= 0) {
				String[] bb = aa[i].split("&#");
				for (int j = 0; j < bb.length; j++) {
					if (bb[j].matches("[0-9]+")&&aa[i].indexOf("&#"+bb[j])>=0&&aa[i].indexOf(bb[j]+"&#"+bb[j])<0) {
						result = result + ((char) Integer.valueOf(bb[j].replace(";", "")).intValue());
					} else {
						result = result + bb[j];
					}
				}
			} else {
				result = result + aa[i];
			}
		}
		return result;
	}
	
/*	public static String nullToString(Object  s){
		return (!Validation.isEmpty(s))? (String.valueOf(s)):("");
	}
	*//**数据库插入null值*//*
	public static String nullToString2(Object  s){
		return (!Validation.isEmpty(s))? (String.valueOf(s)):("null");
	}*/
	
	// ������ݷ��ؼ���ID
	public static String encryptByString(String content) {
		return Password.createPassword(content);
	}
	
	//��String���浽ָ����·��
	public static boolean stringToFile(String content, String savePath,String fileName) {
		InputStream input = null;
		FileOutputStream output = null;
		File path = new File(savePath);
		File finallyPath = new File(savePath+"//"+fileName);
		if (!path.getAbsoluteFile().exists()) {
			path.getAbsoluteFile().mkdirs();
		}else{
			FileHelper.delFile(savePath);
		}
		try {
			input = new   ByteArrayInputStream(content.getBytes());
			output = new FileOutputStream(finallyPath.getAbsolutePath());
			IOUtils.copy(input, output);
			output.flush();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[][] convertResult(ResultSet res){
		String[][] result = null;
		int column = 0;
		Vector vector = new Vector();
		try {
			while(res.next()){
				column = res.getMetaData().getColumnCount();
				String[] str = new String[column];
				for (int i = 1; i < column+1; i++) {
					Object ob = res.getObject(i);
					if(ob==null) str[i-1] = "";
					else str[i-1] = ob.toString();
				}
				vector.addElement(str.clone());
			}
			result = new String[vector.size()][column];
			vector.copyInto(result);
			vector.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		}
	
	public static void main(String[] args) {
		stringToFile("jiamali","F://eee","123.jpg");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getLastResultByReg(String content, String reg) {
		Pattern p = Pattern.compile(reg,Pattern.DOTALL);
		Matcher m = p.matcher(content);
		Stack s = new Stack();
		while(m.find()){
			s.push(m.group(1));
		}
		return s.isEmpty()?null:(String)s.pop();
	}
	
	/**去除字符串前后的全角空格   并替换掉字符串中的全角空格*/
	public static String trimCHN(String para) {
		while(para.startsWith(" ")){
			para = para.substring(1, para.length()).trim();
		}
		while(para.endsWith(" ")){
			para = para.substring(0,para.length() - 1).trim();
		}
		para = para.replaceAll(" "," ");
		return para;
	}
	
	/**将html语言中的unicode转换成中文*/
	public static String escapeHtml(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}
	
	
	
}