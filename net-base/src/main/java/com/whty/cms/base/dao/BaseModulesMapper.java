package com.whty.cms.base.dao;

import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.pojo.BaseModules;
import com.whty.cms.base.pojo.BaseModulesExample;
import com.whty.cms.base.pojo.BaseUsersExample;
import com.whty.cms.base.pojo.udf.BaseModulesComplexExample;

public interface BaseModulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int countByExample(BaseModulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int deleteByExample(BaseModulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int deleteByPrimaryKey(String moduleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int insert(BaseModules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int insertSelective(BaseModules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    PageList<BaseModules> selectByExample(BaseModulesExample example,PageBounds pageBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    BaseModules selectByPrimaryKey(String moduleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByExampleSelective(@Param("record") BaseModules record, @Param("example") BaseModulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByExample(@Param("record") BaseModules record, @Param("example") BaseModulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByPrimaryKeySelective(BaseModules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_MODULES
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByPrimaryKey(BaseModules record);
    
    
    List<BaseModules> selectMyModules(BaseModulesComplexExample example);
}