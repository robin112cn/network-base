package com.whty.cms.base.ucenter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UCenterClient {

	public static UCenterUser uc_check(String username, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON
				+ ";charset=UTF-8");
		HttpEntity<String> entity = new HttpEntity<String>("{\"username\":\""
				+ username + "\"}", headers);

		try {
			String json = RestHttpConnector.getInstace().postForObject(
					url + "/get/{username}", entity, String.class,username);
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseResult resp = objectMapper.readValue(json,
					ResponseResult.class);
			if(resp.isSuccess()){
				List list = (List)resp.getData();
				if(list.size()>0){
					UCenterUser user1=
					JacksonMapper.jsonToClass(JacksonMapper.toJsonString(list.get(0)), UCenterUser.class);
					return user1;
				}
			}else{
				return null;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static UCenterUser uc_login(String username, String password,
			String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON
				+ ";charset=UTF-8");
		HttpEntity<String> entity = new HttpEntity<String>("{\"username\":\""
				+ username + "\",\"password\":\"" + password + "\"}", headers);

		try {
			String json = RestHttpConnector.getInstace().postForObject(
					url + "/auth", entity, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseResult<UCenterUser> resp = objectMapper.readValue(json,
					ResponseResult.class);
			UCenterUser uc = (resp.getTData(UCenterUser.class));
			return uc;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResponseResult<String> uc_register(String username, String password,
			String email, String phone, String nickname, String usertype, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON
				+ ";charset=UTF-8");
		HttpEntity<String> entity = new HttpEntity<String>("{\"username\":\""
				+ username + "\",\"password\":\"" + password
				+ "\",\"email\":\"" + email + "\",\"phone\":\""
				+ phone + "\",\"nickname\":\"" + nickname + "\",\"usertype\":\"" + usertype + "\"}", headers);
		String json = RestHttpConnector.getInstace().postForObject(
				url + "/register", entity, String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseResult<String> resp = objectMapper.readValue(json,
					ResponseResult.class);
			return resp;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String uc_edit(String username, String password, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON
				+ ";charset=UTF-8");
		HttpEntity<String> entity = new HttpEntity<String>("{\"username\":\""
				+ username + "\",\"password\":\"" + password
				+ "\"}", headers);
		String json = RestHttpConnector.getInstace().postForObject(
				url + "/edit", entity, String.class);

//		try {
//			JacksonMapper.
//			String uc = (resp.getTData(String.class));
//			return uc;
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return json;
	}

	public static List<UCenterUser> uc_find(String uid, String username,
			String email, String phone, String realname, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON
				+ ";charset=UTF-8");
		HttpEntity<String> entity = new HttpEntity<String>("{\"username\":\""
				+ username + "\",\"uid\":\"" + uid
				+ "\",\"email\":\"" + email + "\",\"phone\":\""
				+ phone + "\",\"realname\":\"" + realname + "\"}", headers);
		String json = RestHttpConnector.getInstace().postForObject(
				url + "/find", entity, String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ResponseResult<String> resp = objectMapper.readValue(json,
					ResponseResult.class);
			String listJson = resp.getTData(String.class);
			JavaType javaType = JacksonMapper.getCollectionType(ArrayList.class, UCenterUser.class);
			List<UCenterUser> lst =  (List<UCenterUser>)objectMapper.readValue(listJson, javaType);
			return lst;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static UCenterUser uc_login_1(String username, String password,
			String url ,String connectionTimeOut,String soTimeOut) {
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParameters, "UTF-8");
			HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
//			HttpConnectionParams.setConnectionTimeout(httpParameters,
//					connectionTimeOut);
//			HttpConnectionParams.setSoTimeout(httpParameters, soTimeOut);
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
		        String aaa = EntityUtils.toString(response.getEntity(), "utf-8");
		        System.out.println(aaa);
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

	public static void main(String[] args) {
//		UCenterClient.uc_register("admin2", "123456", "email","","测试11", "http://10.8.40.148:8122/uc");
//		UCenterClient.uc_find("", "", "","","测试11", "http://10.8.40.148:8122/uc");
		UCenterClient.uc_check("admin","http://10.8.40.148:8122/uc");
	}
}
