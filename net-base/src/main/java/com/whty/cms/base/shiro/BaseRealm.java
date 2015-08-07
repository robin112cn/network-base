package com.whty.cms.base.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.common.Constant;
import com.whty.cms.base.pojo.BaseModules;
import com.whty.cms.base.pojo.BaseUsers;
import com.whty.cms.base.pojo.BaseUsersExample;
import com.whty.cms.base.service.BaseModulesService;
import com.whty.cms.base.service.BaseUsersService;
import com.whty.cms.base.ucenter.UCenterClient;
import com.whty.cms.base.ucenter.UCenterUser;
import com.whty.cms.common.util.MD5Util;

/**
 *
 * @ClassName: MdmRealm
 * @author liyang
 * @date 2015-4-9
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class BaseRealm  extends AuthorizingRealm{
	 private static final Logger logger = LoggerFactory
	            .getLogger(BaseRealm.class);

	@Autowired
	private BaseUsersService baseUsersService;

	@Autowired
	private BaseModulesService baseModulesService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Subject subjuct = SecurityUtils.getSubject();
		SimpleAuthorizationInfo  sainfo=new SimpleAuthorizationInfo();
		List<BaseModules> bList= baseModulesService.selectMyModules((String)subjuct.getSession().getAttribute(Constant.BaseUsersConstant.USERID));
		if (! CollectionUtils.isEmpty(bList)){
			for (BaseModules baseModules:bList){
				if(Constant.LeafTypeConstant.LEAFTYPEBUTTON==baseModules.getLeafType()){
					sainfo.addStringPermission(baseModules.getModuleUrl());
				}
			}
		}
		return sainfo;
	}

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		logger.debug("----登录验证");
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		char[] passWords = userToken.getPassword();
		String password = String.valueOf(passWords);
		try {
			BaseUsers baseUsers = baseUsersService.selectByUserPwd(
					userToken.getUsername(), password);
			if (null == baseUsers) {
				// 用户名/密码错误
				throw new UnknownAccountException();
			}
			if (Constant.BaseUsersConstant.LOCKED.equals(baseUsers
					.getUserStatus())) {
				throw new LockedAccountException();
			}
			Subject subjuct = SecurityUtils.getSubject();
			subjuct.getSession().setAttribute(
					Constant.BaseUsersConstant.USERID, baseUsers.getUserId());
			subjuct.getSession().setAttribute(
					Constant.BaseUsersConstant.USERNAME,
					baseUsers.getUserName());
			return new SimpleAuthenticationInfo(userToken.getUsername(),
					password, getName());
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
	}


}
