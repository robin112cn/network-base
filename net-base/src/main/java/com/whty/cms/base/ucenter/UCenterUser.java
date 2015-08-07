package com.whty.cms.base.ucenter;


/**
 * @ClassName: User
 * @Description: 用户实体类
 * @author Qing.Luo
 * @date 2015年5月27日 下午4:57:49
 * @version
 *
 */
public class UCenterUser {

	private String uid;

	private String username;

	private String password;

	private String phone;

	private String realname;

	private String email;

	private String nickname;

	private String usertype;

	private String status;

	public UCenterUser() {

	}

	public static UCenterUser newInstance(String uid, String username,
			String password, String email) {
		return new UCenterUser(uid, username, password, email);
	}

	public UCenterUser(String uid, String username, String password, String email,
			String phone, String realname) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.setPhone(phone);
		this.setRealname(realname);
	}

	public UCenterUser(String uid, String username, String password, String email) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;

	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
