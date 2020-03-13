package com.analyze.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合处理工具
 * 
 * @author Wei
 *
 */
public class ListUtil {

	public static void main(String[] args) {

//		String exampleStr = "韩都衣舍;韩版;2016;韩版;女装;冬装;新款;修身;显瘦;纯色;毛呢;外套;LO;2533;瑒;韩都衣舍;2017;韩版;女装;春装;新款;磨白;显瘦;小脚;牛仔;裤;女;MQ;1040;汝";	
//		String result = heavyTheList(exampleStr);
//		System.out.println(result);
		String mm="  ";
		System.out.println(mm.replace(" ", "").equals(""));

	}

	/**
	 * 一个集合字符串 如：123,456,789,123
	 * 
	 * @param str
	 * @return
	 */
	public static String heavyTheList(String str) {
		List<String> tempList = new ArrayList<String>();
		if (str != null && !"".equals(str)) {
			// 进行拆分
			String[] arr = str.split(";");
			for (String value : arr) {
				// 不包含，则往集合里面添加进去
				if (!tempList.contains(value)) {
					if (!value.replace(" ", "").equals("")) {
						tempList.add(value);
					}
					
				}
			}
		}

		return listToString(tempList, ';');
	}

	public static String listToString(List list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}
