package com.whty.cms.base.ucenter;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestHttpConnector {
	private static RestTemplate instance;

	/**
	 * private constructor.
	 */
	private RestHttpConnector() {

	}

	/**
	 * if instance is null creates one and returns it.
	 *
	 * @return RestTemplate instance.
	 */
	public static RestTemplate getInstace() {
		try{
			if (instance == null) {
				instance = new RestTemplate();
				initConfigs(instance);
			}
			return instance;
		}catch (Exception e){
			return null;
		}
	}

	private static void initConfigs(RestTemplate restTemplate) throws Exception{
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

	}
}
