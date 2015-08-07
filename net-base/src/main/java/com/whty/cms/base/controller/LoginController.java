package com.whty.cms.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.whty.cms.base.common.Constant;
import com.whty.cms.base.common.CookieTool;
import com.whty.cms.base.dto.CasUser;
import com.whty.cms.base.pojo.BaseModules;
import com.whty.cms.base.pojo.BaseUsers;
import com.whty.cms.base.service.BaseModulesService;
import com.whty.cms.base.service.BaseUsersService;
import com.whty.cms.common.base.BaseController;
import com.whty.cms.common.base.BaseResponseDto;
import com.whty.cms.common.base.Menu;
import com.whty.cms.common.util.CheckEmpty;
import com.whty.cms.common.util.MD5Util;

@Controller
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private BaseModulesService baseModulesService;

	@Autowired
	private BaseUsersService baseUsersService;

	@RequestMapping(value = "${ctxPath}", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.info("login....");
		String errorMsg = "";
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		UsernamePasswordToken userToken = new UsernamePasswordToken(userName,passWord);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(userToken);
		} catch (UnknownAccountException e) {
			errorMsg = "用户名/密码错误";
		} catch (IncorrectCredentialsException e) {
			errorMsg = "用户名/密码错误";
		}  catch (LockedAccountException e) {
			errorMsg = "账号已停用！";
		} catch (AuthenticationException e) {
			errorMsg = "未知错误！请联系维护人员。";
			logger.debug("未知错误", e);
		}
		if (subject.isAuthenticated()) {
			String userId = (String) subject.getSession()
					.getAttribute("userId");
			BaseUsers baseUsers=new BaseUsers();
			baseUsers.setUserId(userId);
			baseUsers.setUserLastLoginIp(this.getIpAddr(request));
			baseUsers.setUserLastLoginTime(new Date());
			baseUsersService.updateSelectiveByPrimaryKey(baseUsers);
			String menuJsonStr = buildMenuStr(getMenuTrees(userId), request);
			request.getSession().setAttribute("menustr", menuJsonStr);
			request.getSession().setAttribute("userName",userName);
			return "index/index";
		} else {
			request.getSession().setAttribute("errorMsg", errorMsg);
			return "login";
		}
	}

	/**
	 * 取得客户端真实ip
	 *
	 * @param request
	 * @return 客户端真实ip
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		logger.debug("1- X-Forwarded-For ip={}", ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("2- Proxy-Client-IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("3- WL-Proxy-Client-IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.debug("4- HTTP_CLIENT_IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.debug("5- HTTP_X_FORWARDED_FOR ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("6- getRemoteAddr ip={}", ip);
		}
		logger.debug("finally ip={}", ip);
		return ip;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		request.getSession().removeAttribute("userName");
		subject.logout();
		return "login";
	}

	@RequestMapping(value = "/clearSession")
	public String clearSession(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("userName");
		return "login";
	}

	@RequestMapping(value = "/getUserName")
	public void getUserName(HttpServletRequest request, HttpServletResponse response) {
		String userNanme = (String) request.getSession().getAttribute("userName");
		if (CheckEmpty.isEmpty(userNanme)) {
			writeJSONResult(new BaseResponseDto(false, ""), response);
		} else {
			writeJSONResult(new BaseResponseDto(true, ""), response);
		}
	}

	private static Menu covertModuleToMenu(BaseModules baseModules) {
		Menu menu = new Menu();
		menu.setId(baseModules.getModuleId());
		menu.setIcon(baseModules.getIconCss());
		menu.setOrder(baseModules.getDisplayIndex());
		menu.setParentId(baseModules.getParentId());
		menu.setUrl(baseModules.getModuleUrl());
		menu.setName(baseModules.getModuleName());
		return menu;
	}

	private List<Menu> getMenuTrees(String userId) {
		List<BaseModules> listMoudle = baseModulesService.selectMyModules(userId);
		List<Menu> list = new ArrayList<Menu>();
		for (int i = 0; i < listMoudle.size(); i++) {
			BaseModules bm = listMoudle.get(i);
			if ("0".equals(bm.getParentId())) {
				Menu menu = new Menu();
				menu = covertModuleToMenu(bm);
				List<Menu> chList = new ArrayList<Menu>();
				for (int j = 0; j < listMoudle.size(); j++) {
					if (listMoudle.get(j).getParentId().equals(bm.getModuleId())) {
						chList.add(covertModuleToMenu(listMoudle.get(j)));
					}
				}
				menu.setSubMenus(chList);
				list.add(menu);
			}
		}

		return list;
	}

	private String buildMenuStr(List<Menu> menus, HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		StringBuffer targetStrBuffer = new StringBuffer();
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			boolean hasMemo = "".equals(menu.getMemo());
			boolean hasUrl = "".equals(menu.getUrl());
			String tooltip = " class='tooltips' data-container='body' data-placement='right' data-html='true' data-original-title='%s'";
			// 处理默认打开首页
			// if(menu.getId().equals("menu_1")){
			// tooltip =
			// " class='start active tooltips' data-container='body' data-placement='right' data-html='true' data-original-title='%s'";
			// }
			String menuStrFormatWithSingle = "<li id='%s' " + (hasMemo ? "%s" : tooltip) + " > <a href='" + (hasUrl ? "javascript:;%s" : "%s")
					+ "'> <i class='%s'></i> " + "<span class='title'>%s</span><span></span></a></li>";
			String menuStrFormatWithChildren = "<li id='%s' title='%s'> <a href='" + (hasUrl ? "javascript:;%s" : "%s") + "'  > <i class='%s'></i> "
					+ "<span class='title'>%s</span><span class='arrow'>" + "</span></a>" + "<ul class='sub-menu'>" + "%s" + "</ul></li>";
			targetStrBuffer.append(String.format(menu.isHasChildren() ? menuStrFormatWithChildren : menuStrFormatWithSingle, menu.getId(), menu.getMemo(),
					basePath + menu.getUrl(), menu.getIcon(), menu.getName(), buildMenuStr(menu.getSubMenus(), request)));
		}

		return targetStrBuffer.toString();
	}
}
