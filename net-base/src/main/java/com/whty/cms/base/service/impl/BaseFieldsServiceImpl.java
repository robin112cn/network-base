package com.whty.cms.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.common.DBConstant;
import com.whty.cms.base.dao.BaseFieldsMapper;
import com.whty.cms.base.pojo.BaseFields;
import com.whty.cms.base.pojo.BaseFieldsExample;
import com.whty.cms.base.pojo.BaseFieldsExample.Criteria;
import com.whty.cms.base.service.BaseFieldsService;

@Service
@Transactional
public class BaseFieldsServiceImpl implements BaseFieldsService {

	@Autowired
	private BaseFieldsMapper baseFieldsMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseFields baseFields) {
		return baseFieldsMapper.insertSelective(baseFields);
	}
	
	/**
	 * 根据主键查询
	 */
	@Override
	public BaseFields selectByPrimaryKey(String fieldId) {
		return baseFieldsMapper.selectByPrimaryKey(fieldId);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseFields> selectByExample(BaseFieldsExample example,PageBounds pageBounds){
		return baseFieldsMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据主键进行选择性更新
	 */
	@Override
	public int updateSelectiveByPrimaryKey(BaseFields record) {
		return baseFieldsMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseFields record,
			BaseFieldsExample example) {
		return baseFieldsMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	public int deleteByPrimaryKey(String fieldId) {
		return baseFieldsMapper.deleteByPrimaryKey(fieldId);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseFieldsExample example){
    	return baseFieldsMapper.deleteByExample(example);
    }

	/**
	 * @author dengzm
	 * @since 2015-4-18
	 * 根据数据字段的类型查询商数据字段
	 * @return
	 */
	@Override
	public List<BaseFields> selectEnabledByEnFieldName(String enFieldName) {
		BaseFieldsExample exmple = new BaseFieldsExample();
		Criteria criteria = exmple.createCriteria();
		criteria.andEnFieldNameEqualTo(enFieldName);
		// 排序
		exmple.setOrderByClause(" FIELD_SORT asc");
		// 启用
		criteria.andEnabledStatusEqualTo(DBConstant.BaseFieldsEnabledStatus.ENABLED);
		PageList<BaseFields> baseFields = selectByExample(exmple, new PageBounds());
	
		return baseFields;
	}

	/**
	 * 
	 * 根据数据字段的类型和值查询显示值
	 * @param enFieldName
	 * @param value
	 * @return
	 */
	@Override
	public BaseFields getEnabled(String enFieldName, String valueField) {
		BaseFieldsExample exmple = new BaseFieldsExample();
		Criteria criteria = exmple.createCriteria();
		criteria.andEnFieldNameEqualTo(enFieldName);
		criteria.andValueFieldEqualTo(valueField);
		// 排序
		exmple.setOrderByClause(" FIELD_SORT asc");
		// 启用
		criteria.andEnabledStatusEqualTo(DBConstant.BaseFieldsEnabledStatus.ENABLED);
		PageList<BaseFields> baseFields = selectByExample(exmple, new PageBounds());
		if(baseFields == null || baseFields.size()==0){
			return null;
		}
		return baseFields.get(0);
	}

	/**
	 * 根据flag选择短信发送内容
	 */
	@Override
	public String selectSmsContent(String flag) {
		BaseFieldsExample example = new BaseFieldsExample();
		Criteria cri = example.createCriteria();
		cri.andEnFieldNameEqualTo(DBConstant.BaseFieldsSMS.ENFIELDNAME);
		cri.andValueFieldEqualTo(flag);
		PageList<BaseFields> baseFields = this.selectByExample(example, new PageBounds());
		if(!CollectionUtils.isEmpty(baseFields)){
			return baseFields.get(0).getDisplayField();
		}
		return null;
	}

}
