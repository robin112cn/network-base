package com.whty.cms.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.common.Constant;
import com.whty.cms.base.common.DataTableQueryMySQL;
import com.whty.cms.base.dto.BaseFieldsListCondDto;
import com.whty.cms.base.pojo.BaseFields;
import com.whty.cms.base.pojo.BaseFieldsExample;
import com.whty.cms.base.pojo.BaseFieldsExample.Criteria;
import com.whty.cms.base.service.BaseFieldsService;
import com.whty.cms.common.base.BaseController;
import com.whty.cms.common.base.BaseResponseDto;
import com.whty.cms.common.base.DataTableQuery;
import com.whty.cms.common.util.CheckEmpty;
import com.whty.cms.common.util.UUIDUtil;

@Controller
@RequestMapping("/baseFields")
public class BaseFieldsController extends BaseController {
	@Autowired
	BaseFieldsService baseFieldsService;

	/**
	 * 显示主列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseFields/baseFieldsUI";
	}

	/**
	 * 查看APK详情 打开详情页面，无论是编辑还是查看详情，思路是一致的
	 * 
	 * @param response
	 * @param productId
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public void view(HttpServletResponse response, String fieldId) {
		BaseFields baseFields = baseFieldsService.selectByPrimaryKey(fieldId);
		writeJSONResult(baseFields, response);
	}

	/**
	 * 查询
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public void find(HttpServletResponse response, HttpServletRequest request,BaseFieldsListCondDto baseFieldsListCondDto) {
		DataTableQuery dt = new DataTableQueryMySQL(request);
		Map<String,Object> result = buildTableData(dt, baseFieldsListCondDto);
		writeJSONResult(result, response);
	}

	/**
	 * 构建列表数据
	 * 
	 * @return
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt,
			BaseFieldsListCondDto baseFieldsListCondDto) {
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		BaseFieldsExample example = buildExample(dt,baseFieldsListCondDto);
		PageList<BaseFields> baseFields = baseFieldsService.selectByExample(
				example, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", baseFields);
		records.put("recordsTotal", baseFields.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseFields.getPaginator()
				.getTotalCount());
		//
		return records;
	}

	/**
	 * 构建列表查询条件
	 * 
	 * @param baseFieldsListCondDto
	 * @return
	 */
	private BaseFieldsExample buildExample(DataTableQuery dt,
			BaseFieldsListCondDto baseFieldsListCondDto) {
		BaseFieldsExample baseFieldsExample = new BaseFieldsExample();
		Criteria criteria = baseFieldsExample.createCriteria();
		if (CheckEmpty.isNotEmpty(baseFieldsListCondDto.getFieldName())) {
			criteria.andFieldNameLike("%"
					+ baseFieldsListCondDto.getFieldName() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			baseFieldsExample.setOrderByClause(orderByClause.toString());
		}
		return baseFieldsExample;
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(BaseFields baseFields, HttpServletResponse response,
			String tag) {
		BaseFieldsExample example = new BaseFieldsExample();
		Criteria criteria = example.createCriteria();
		criteria.andEnFieldNameEqualTo(baseFields.getEnFieldName());
		criteria.andValueFieldEqualTo(baseFields.getValueField());
		// 查询数据库是否有 字段+字段值 相同的记录
		PageList<BaseFields> queryField = baseFieldsService.selectByExample(
				example, new PageBounds());
		// 新增 则直接判定是否有相同的记录
		if (Constant.SAVESTATUSADD.equals(tag)) {
			if (!queryField.isEmpty()) {
				writeJSONResult(new BaseResponseDto(false, "字段+字段值不能重复!"),
						response);
			} else {
				if (CheckEmpty.isEmpty(baseFields.getFieldId())) {
					baseFields.setFieldId(UUIDUtil.getUuidString());
					baseFieldsService.insertSelective(baseFields);
					writeJSONResult(new BaseResponseDto(true, "新增数据字典成功"),response);
				}
			}
		} else if (Constant.SAVESTATUSUPDATE.equals(tag)) { // 编辑时，如果字段+字段值没有变化
															// 编辑为成功；如果改动字段或者字段值后，与字段+字段值与其他记录相同
															// 则编辑失败
			if (!queryField.isEmpty()) {
				BaseFieldsExample example1 = new BaseFieldsExample();
				Criteria criteria1 = example1.createCriteria();
				criteria1.andEnFieldNameEqualTo(baseFields.getEnFieldName());
				PageList<BaseFields> queryExist = baseFieldsService
						.selectByExample(example, new PageBounds());
				if (!CollectionUtils.isEmpty(queryExist)) {
					String valueField = queryExist.get(0).getValueField();
					if (!valueField.equals(baseFields.getValueField())) {
						writeJSONResult(new BaseResponseDto(false,
								"字段+字段值不能重复!"), response);
					} else {
						baseFieldsService
								.updateSelectiveByPrimaryKey(baseFields);
						writeJSONResult(new BaseResponseDto(true, "编辑数据字典成功"),
								response);
					}
				}
			} else {
				baseFieldsService.updateSelectiveByPrimaryKey(baseFields);
				writeJSONResult(new BaseResponseDto(true, "编辑数据字典成功"), response);
			}
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(String fieldId, HttpServletResponse response) {
		baseFieldsService.deleteByPrimaryKey(fieldId);
		writeJSONResult(new BaseResponseDto(true), response);
	}
}
