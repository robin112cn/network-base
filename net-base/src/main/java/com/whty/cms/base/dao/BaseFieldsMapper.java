package com.whty.cms.base.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.pojo.BaseFields;
import com.whty.cms.base.pojo.BaseFieldsExample;

import org.apache.ibatis.annotations.Param;

public interface BaseFieldsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int countByExample(BaseFieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int deleteByExample(BaseFieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int deleteByPrimaryKey(String fieldId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int insert(BaseFields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int insertSelective(BaseFields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    PageList<BaseFields> selectByExample(BaseFieldsExample example,PageBounds pageBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    BaseFields selectByPrimaryKey(String fieldId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByExampleSelective(@Param("record") BaseFields record, @Param("example") BaseFieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByExample(@Param("record") BaseFields record, @Param("example") BaseFieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByPrimaryKeySelective(BaseFields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BASE_FIELDS
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    int updateByPrimaryKey(BaseFields record);
}