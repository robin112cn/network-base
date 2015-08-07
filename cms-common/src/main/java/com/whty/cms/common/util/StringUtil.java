package com.whty.cms.common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil extends org.apache.commons.lang3.StringUtils{
    /**
	 * 将字符串左边填充指定字符到len长度
	 * 
	 * @param s
	 *            原始字符串
	 * @param paddingStr
	 *            填充字符串
	 * @param len
	 *            长度
	 * @return
	 */
	public static String paddingStrLeft(String s, String paddingStr, int len) {
		int strLen = s.length();
		StringBuffer zeros = new StringBuffer("");
		for (int loop = len - strLen; loop > 0; loop--) {
			zeros.append(paddingStr);
		}
		return zeros.append(s).toString();
	}
	
	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
}
