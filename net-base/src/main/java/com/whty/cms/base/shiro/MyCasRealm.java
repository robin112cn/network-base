package com.whty.cms.base.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.whty.cms.base.common.Constant;
import com.whty.cms.base.pojo.BaseModules;
import com.whty.cms.base.service.BaseModulesService;
import com.whty.cms.base.service.BaseUsersService;

public class MyCasRealm extends CasRealm {

	@Autowired
	private BaseUsersService baseUsersService;

	@Autowired
	private BaseModulesService baseModulesService;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 // retrieve user information
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        List<Object> listPrincipals = principalCollection.asList();
        Map<String, String> attributes = (Map<String, String>) listPrincipals.get(1);
        String userName = (String) listPrincipals.get(0);

//		Subject subjuct = SecurityUtils.getSubject();
		SimpleAuthorizationInfo  sainfo=new SimpleAuthorizationInfo();
		List<BaseModules> bList= baseModulesService.selectMyModulesByName(userName);
		if (! CollectionUtils.isEmpty(bList)){
			for (BaseModules baseModules:bList){
				if(Constant.LeafTypeConstant.LEAFTYPEBUTTON==baseModules.getLeafType()){
					sainfo.addStringPermission(baseModules.getModuleUrl());
				}
			}
		}
		return sainfo;
	}
}
