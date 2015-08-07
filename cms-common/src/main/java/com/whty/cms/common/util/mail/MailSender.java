package com.whty.cms.common.util.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.whty.cms.common.dto.MailDto;

/**
 * 邮件发送器
 * 
 * @author Ocea
 *
 */
public class MailSender {

	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();

	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	/**
	 * 邮箱session
	 */
	private transient Session session;

	/**
	 * 初始化
	 * 
	 * @param username
	 * @param password
	 * @param smtpHostName
	 */
	private void init(String username, String password, String smtpHostName) {
		// 初始化props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.connectiontimeout", "5000");
		props.put("mail.smtp.timeout", "5000");
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 * @param username
	 * @param password
	 */
	public MailSender(final String smtpHostName, final String username,
			final String password) {
		init(username, password, smtpHostName);
	}

	/**
	 * 初始化邮件发送器
	 * 
	 * @param username
	 * @param password
	 */
	public MailSender(final String username, final String password) {
		// 通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
		final String smtpHostName = "mail.whty.com.cn";
		init(username, password, smtpHostName);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, String subject, Object content)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置邮件主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 * @param mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, MailDto mail) throws AddressException,
			MessagingException {
		send(recipient, mail.getSubject(), mail.getContent());
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置邮件主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 * @param mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, MailDto mail)
			throws AddressException, MessagingException {
		send(recipients, mail.getSubject(), mail.getContent());
	}

}
