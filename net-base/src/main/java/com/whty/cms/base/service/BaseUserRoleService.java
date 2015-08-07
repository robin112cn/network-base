package com.whty.cms.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.pojo.BaseUserRoleKey;
import com.whty.cms.base.pojo.BaseUserRoleExample;

public interface BaseUserRoleService {
	
	/**
	 * 保存用户角色信息
	 * @param BaseUserRole
	 * @return
	 */
	int insertSelective(BaseUserRoleKey BaseUserRole);
	
	/**
	 * 根据条件查询用户角色信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseUserRoleKey> selectByExample(BaseUserRoleExample example,PageBounds pageBounds);
	
	/**
	 * 根据条件修改传入的用户角色信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseUserRoleKey record,
			BaseUserRoleExample example);
	
	/**
	 * 根据条件删除用户角色信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseUserRoleExample example);
	
	/**
	 * 查询角色ID对应的用户数
	 * @param roleId
	 * @return
	 */
	int countByRoleId(String roleId);
	
}
