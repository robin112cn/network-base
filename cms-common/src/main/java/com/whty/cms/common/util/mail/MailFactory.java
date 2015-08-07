package com.whty.cms.common.util.mail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 邮箱工程类
 * 
 * @author Ocea
 *
 */
public class MailFactory {

	/**
	 * 组装账号激活邮件主题
	 * 
	 * @return
	 */
	public static String activeMailSubject() {
		StringBuilder sb = new StringBuilder("");
		sb.append("亲爱的果核用户，请激活您的账号，完成注册！");
		return sb.toString();
	}

	/**
	 * 组装找回密码邮件主题
	 * 
	 * @return
	 */
	public static String getPwdMailSubject() {
		StringBuilder sb = new StringBuilder("");
		sb.append("果核网密码找回");
		return sb.toString();
	}

	/**
	 * 组装账号激活邮件内容
	 * 
	 * @param url
	 * @return
	 */
	public static String activeMailContent(String url) {
		StringBuilder sb = new StringBuilder("");
		sb.append("<p style=\"text-align: left;\">亲爱的果核用户：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">感谢您注册果核网。</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">请点击下面的链接激活您的帐号，完成注册：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\"><a href=\"");
		sb.append(url);
		sb.append("\">");
		sb.append(url);
		sb.append("</a></p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">（如果上面的链接无法点击，您也可以复制链接，粘贴到您浏览器的地址栏内，然后按“回车”完成激活操作。）</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">激活链接24小时内有效，超过24小时请重新注册。</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">激活链接将在您激活一次后失效。</p>");
		sb.append("<p style=\"text-align: right;\">果核用户中心</p>");
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = df.format(new Date());
		sb.append("<p style=\"text-align: right;\">");
		sb.append(dateStr);
		sb.append("</p>");
		return sb.toString();
	}

	/**
	 * 组装找回密码邮件内容
	 * 
	 * @param userCode
	 * @return
	 */
	public static String getPwdMailContent(String userCode) {
		StringBuilder sb = new StringBuilder("");
		sb.append("<p style=\"text-align: left;\">亲爱的果核用户：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">欢迎使用果核网找回密码功能。</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">您的验证码为：</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">");
		sb.append(userCode);
		sb.append("</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">该验证码有效期为10分钟，逾期请重新获取；</p>");
		sb.append("<p style=\"text-align: left; text-indent: 2em;\">如果您并未发过此请求，则可能是因为其他用户在尝试重设密码时误输入了您的电子邮件地址而使您收到这封邮件，那么您可以放心的忽略此邮件，无需进一步采取任何操作。</p>");
		sb.append("<p style=\"text-align: right;\">果核用户中心</p>");
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = df.format(new Date());
		sb.append("<p style=\"text-align: right;\">");
		sb.append(dateStr);
		sb.append("</p>");
		return sb.toString();
	}

}
