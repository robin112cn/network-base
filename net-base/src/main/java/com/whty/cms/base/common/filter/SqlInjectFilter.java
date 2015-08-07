package com.whty.cms.base.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SqlInjectFilter implements Filter {

	public void destroy() {

	}

	/**
     * 
     */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (request.getRequestURI().matches(".*/find") || request.getRequestURI().matches(".*/tableAjax")) {
			// 获取request里面的参数集合
			Map<String, String> params = request.getParameterMap();
			Set<String> keys = params.keySet();
			Map<String, String> newMap = new HashMap<String, String>();
			// 过滤掉参数里面的危险字符
			for (String key : keys) {
				String value = request.getParameter(key);
				// 过滤掉参数里面的危险字符比如 单引号双引号
				String filterVlue = filterSign(value);
				if(key.equals("merchantName")){
					System.out.println(filterVlue);
				}
				newMap.put(key, filterVlue);
				// System.out.println(key+"-----"+filterVlue);
			}
			ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(request, newMap);
			fc.doFilter(wrapRequest, res);
		}else {
			fc.doFilter(req, res);
		}
	}

	public void init(FilterConfig conf) throws ServletException {

	}

	public static String filterSign(String value) {
		// return value.replaceAll("'", "").replaceAll("\"", "").replaceAll("<",
		// "").replaceAll(">", "");
		// 20150228 去掉引号会导致json数据传递到后台时无法解析
		return value.replaceAll("<", "").replaceAll(">", "").replace("%", "\\%");
	}
}
