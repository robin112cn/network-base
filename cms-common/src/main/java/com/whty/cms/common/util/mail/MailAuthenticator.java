package com.whty.cms.common.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 服务器邮箱登陆验证
 * 
 * @author Ocea
 * 
 */
public class MailAuthenticator extends Authenticator {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 初始化用户名和密码
	 * 
	 * @param username
	 * @param password
	 */
	public MailAuthenticator(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
