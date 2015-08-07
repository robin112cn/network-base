package com.whty.cms.base.ucenter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class UcClientHelper {

	/**
	 * 向用户中心登录
	 * @param username
	 * @param password
	 * @param url
	 * @param connectionTimeOut
	 * @param soTimeOut
	 * @return 登录成功返回用户详细信息
	 */
	public static String uc_login(String username, String password,
			String url ,int connectionTimeOut,int soTimeOut) {
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParameters, "UTF-8");
			HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
			HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
			HttpConnectionParams.setSoTimeout(httpParameters, soTimeOut);
			HttpConnectionParams.setSocketBufferSize(httpParameters, 1024);
			HttpClientParams.setRedirecting(httpParameters, false);
			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpPost httpPost = new HttpPost(url + "/auth");
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			String json = "{\"username\":\""
					+ username + "\",\"password\":\"" + password + "\"}";
			StringEntity se = new StringEntity(json);
			 se.setContentType("text/json");
		        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
		        httpPost.setEntity(se);
		        HttpResponse response = httpClient.execute(httpPost);
		        String resp = EntityUtils.toString(response.getEntity(), "utf-8");
		        System.out.println(resp);
		        return resp;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 向用户中心注册
	 * @param username
	 * @param password
	 * @param email
	 * @param phone
	 * @param realname
	 * @param url
	 * @param connectionTimeOut
	 * @param soTimeOut
	 * @return 注册成功返回data为uid
	 */
	public static String uc_register(String username, String password,
			String email, String phone, String realname, String url ,int connectionTimeOut,int soTimeOut) {
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParameters, "UTF-8");
			HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
			HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
			HttpConnectionParams.setSoTimeout(httpParameters, soTimeOut);
			HttpConnectionParams.setSocketBufferSize(httpParameters, 1024);
			HttpClientParams.setRedirecting(httpParameters, false);
			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpPost httpPost = new HttpPost(url + "/register");
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			String json = "{\"username\":\""
					+ username + "\",\"password\":\"" + password
					+ "\",\"email\":\"" + email + "\",\"phone\":\""
					+ phone + "\",\"realname\":\"" + realname + "\"}";
			StringEntity se = new StringEntity(json);
			 se.setContentType("text/json");
		        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
		        httpPost.setEntity(se);
		        HttpResponse response = httpClient.execute(httpPost);
		        String resp = EntityUtils.toString(response.getEntity(), "utf-8");
		        System.out.println(resp);
		        return resp;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
