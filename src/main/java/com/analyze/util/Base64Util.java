package com.analyze.util;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a tool class to convert Base64 string and normal string to each
 * other. <br>
 * <br>
 * More information about base64, Please refer to:
 * http://zh.wikipedia.org/zh-cn/Base64
 * 
 * @author user
 */
public class Base64Util {
	private static final Map<Integer, Character> base64CharMap = new HashMap<Integer, Character>();
	private static final String base64CharString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	static {
		// initialize base64 map
		for (int i = 0; i < base64CharString.length(); i++) {
			char c = base64CharString.charAt(i);
			base64CharMap.put(new Integer(i), new Character(c));
		}
	}

	/**
	 * This method is used to encode a normal string to base64 string
	 * 
	 * @param origin
	 *            The String to be encoded
	 * @return The String after encoded.
	 */
	public static String encode(String origin) {
		if (origin == null) {
			return null;
		}
		if (origin.length() == 0) {
			return "";
		}
		int length = origin.length();
		String binaryString = "";
		// to binary String
		for (int i = 0; i < length; i++) {
			int ascii = origin.charAt(i);
			String binaryCharString = Integer.toBinaryString(ascii);
			while (binaryCharString.length() < 8) {
				binaryCharString = "0" + binaryCharString;
			}
			binaryString += binaryCharString;
		}

		// to base64 index
		int beginIndex = 0;
		int endIndex = beginIndex + 6;
		String base64BinaryString = "";
		String charString = "";
		while ((base64BinaryString = binaryString.substring(beginIndex, endIndex)).length() > 0) {
			// if length is less than 6, add "0".
			while (base64BinaryString.length() < 6) {
				base64BinaryString += "0";
			}
			int index = Integer.parseInt(base64BinaryString, 2);
			char base64Char = base64CharMap.get(index);
			charString = charString + base64Char;
			beginIndex += 6;
			endIndex += 6;
			if (endIndex >= binaryString.length()) {
				endIndex = binaryString.length();
			}
			if (endIndex < beginIndex) {
				break;
			}
		}
		if (length % 3 == 2) {
			charString += "=";
		}
		if (length % 3 == 1) {
			charString += "==";
		}
		return charString;
	}

	/**
	 * This method is used to decode from base64 string to a normal string.
	 * 
	 * @param encodedString
	 *            The string to be decoded.
	 * @return The string after decoded.
	 */
	public static String decode(String encodedString) {
		if (encodedString == null) {
			return null;
		}
		if (encodedString.length() == 0) {
			return "";
		}
		// get origin base64 String
		String origin = encodedString.substring(0, encodedString.indexOf("="));
		String equals = encodedString.substring(encodedString.indexOf("="));

		String binaryString = "";
		// convert base64 string to binary string
		for (int i = 0; i < origin.length(); i++) {
			char c = origin.charAt(i);
			int ascii = base64CharString.indexOf(c);
			String binaryCharString = Integer.toBinaryString(ascii);
			while (binaryCharString.length() < 6) {
				binaryCharString = "0" + binaryCharString;
			}
			binaryString += binaryCharString;
		}
		// the encoded string has 1 "=", means that the binary string has append
		// 2 "0"
		if (equals.length() == 1) {
			binaryString = binaryString.substring(0, binaryString.length() - 2);
		}
		// the encoded string has 2 "=", means that the binary string has append
		// 4 "0"
		if (equals.length() == 2) {
			binaryString = binaryString.substring(0, binaryString.length() - 4);
		}

		// convert to String
		String charString = "";
		String resultString = "";
		int beginIndex = 0;
		int endIndex = beginIndex + 8;
		while ((charString = binaryString.substring(beginIndex, endIndex)).length() == 8) {
			int ascii = Integer.parseInt(charString, 2);
			resultString += (char) ascii;
			beginIndex += 8;
			endIndex += 8;
			if (endIndex > binaryString.length()) {
				break;
			}
		}
		return resultString;
	}

	// Test methods here.
	public static void main(String[] args) {
		String originString = "a7c67b53555717842d7bdd43ac95e19c";//md5加密的密码
		System.out.println("===============加密=================");
		String result = encode(originString);	
		System.out.println(result);
		System.out.println("===============解密=================");	
		System.out.println(decode(result));
	}
}