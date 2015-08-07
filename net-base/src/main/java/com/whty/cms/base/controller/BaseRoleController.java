package com.whty.cms.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.common.Constant;
import com.whty.cms.base.common.DataTableQueryMySQL;
import com.whty.cms.base.pojo.BaseModules;
import com.whty.cms.base.pojo.BaseModulesExample;
import com.whty.cms.base.pojo.BaseRoleModuleExample;
import com.whty.cms.base.pojo.BaseRoleModuleKey;
import com.whty.cms.base.pojo.BaseRoles;
import com.whty.cms.base.pojo.BaseRolesExample;
import com.whty.cms.base.pojo.BaseRolesExample.Criteria;
import com.whty.cms.base.service.BaseModulesService;
import com.whty.cms.base.service.BaseRoleModuleService;
import com.whty.cms.base.service.BaseRolesService;
import com.whty.cms.base.service.BaseUserRoleService;
import com.whty.cms.common.base.BaseController;
import com.whty.cms.common.base.BaseResponseDto;
import com.whty.cms.common.base.DataTableQuery;
import com.whty.cms.common.base.ZTree;
import com.whty.cms.common.util.CheckEmpty;
import com.whty.cms.common.util.UUIDUtil;

/**
 * 角色controller
 * @ClassName: BaseRoleController  
 * @author liyang
 * @date 2015-4-17
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@RequestMapping("/baseRoles")
public class BaseRoleController extends BaseController{
	@Value("${admin_role}")
	private String admin_role; 
	
	
	@Autowired
	private BaseRolesService baseRolesService;
	
	@Autowired
	private BaseRoleModuleService baseRoleModuleService;
	
	@Autowired
	private BaseModulesService baseModulesService;

	@Autowired
	private BaseUserRoleService baseUserRoleService;

	private static final Logger logger = LoggerFactory.getLogger(BaseRoleController.class);
	
	
	/**
	 * 显示主列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseRole/baseRoleUI";
	}

	/**
	 * 查询
	 * @param request
	 * @param response
	 * @param baseRoles
	 * @throws IOException
	 */
	@RequestMapping(value = "/find")
	public void find(HttpServletRequest request,
			HttpServletResponse response, BaseRoles baseRoles)
			throws IOException {
		DataTableQuery dt = new DataTableQueryMySQL(request);
		Map<String,Object> result = buildTableData(dt, baseRoles);
		writeJSONResult(result, response);
	}

	private BaseRolesExample buildExample(DataTableQuery dt,BaseRoles baseRoles){
		BaseRolesExample exmple = new BaseRolesExample();
		Criteria criteria = exmple.createCriteria();
		if (CheckEmpty.isNotEmpty(baseRoles.getRoleName())) {
			criteria.andRoleNameLike("%" + baseRoles.getRoleName() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy()) && CheckEmpty.isNotEmpty(dt.getOrderParam())){
			// 
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ").append(dt.getOrderBy());
			exmple.setOrderByClause(orderByClause.toString());
		}
		return exmple;
	}
	
	/**
	 * 构建数据树
	 * 
	 * @param length
	 * @param start
	 * @param draw
	 * @param modules
	 * @return
	 * @throws IOException
	 */
	private Map<String,Object> buildTableData(DataTableQuery dt,BaseRoles baseRoles) throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber, dt.getPageLength());
		BaseRolesExample exmple = buildExample(dt, baseRoles);
		PageList<BaseRoles> roles = baseRolesService.selectByExample(
				exmple, pageBounds);
		Map<String,Object> records = new HashMap<String,Object>();
		records.put("data", roles);
		records.put("draw", dt.getPageDraw());
		records.put("recordsTotal", roles.getPaginator().getTotalCount());
		records.put("recordsFiltered", roles.getPaginator()
				.getTotalCount());
		return records;
	}

	
	/**
	 * 新建和编辑角色
	 * @param request
	 * @param response
	 * @param baseModules
	 * @param tag
	 * @throws IOException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response,
			BaseRoles baseRoles, String tag) throws IOException {
		int flag = 0;
		BaseResponseDto baseResponseDto = null;
		// 判断
		if ((Constant.SAVESTATUSADD).equals(tag)){
			baseRoles.setRoleId(UUIDUtil.getUuidString());
			String roleName=baseRoles.getRoleName();
			PageBounds pageBounds = new PageBounds();
			BaseRolesExample exmple = new BaseRolesExample();
			Criteria criteria=exmple.createCriteria();
			criteria.andRoleNameEqualTo(roleName.trim());
			PageList<BaseRoles> br=baseRolesService.selectByExample(exmple, pageBounds);
			if(br.size()>0){
				baseResponseDto=new BaseResponseDto(false,"角色名称不能重复");
			}else{
				flag = baseRolesService.insertSelective(baseRoles);
				baseResponseDto = flag>0?new BaseResponseDto(true,"添加角色成功"):new BaseResponseDto(false,"添加角色失败，请联系管理员");
			}
		} else{
			String roleName=baseRoles.getRoleName();
			PageBounds pageBounds = new PageBounds();
			BaseRolesExample exmple = new BaseRolesExample();
			Criteria criteria=exmple.createCriteria();
			criteria.andRoleNameEqualTo(roleName.trim());
			criteria.andRoleIdNotEqualTo(baseRoles.getRoleId());
			PageList<BaseRoles> br=baseRolesService.selectByExample(exmple, pageBounds);
			if(br.size()>0){
				baseResponseDto=new BaseResponseDto(false,"角色名称不能重复");
			}else{
				flag = baseRolesService.updateSelectiveByPrimaryKey(baseRoles);
				baseResponseDto = flag>0?new BaseResponseDto(true,"修改角色成功"):new BaseResponseDto(false,"修改角色失败，请联系管理员");
			}
		}
		writeJSONResult(baseResponseDto, response);
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @param roleId
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request,
			HttpServletResponse response, String roleId) throws IOException {
		BaseResponseDto baseResponseDto = checkDelete(roleId);
		if (baseResponseDto.isSuccess()){
			int flag = baseRolesService.deleteByPrimaryKey(roleId);
			baseResponseDto = flag>0?new BaseResponseDto(true):new BaseResponseDto(false,"删除失败");
		}
		writeJSONResult(baseResponseDto, response);
	}
	
	private BaseResponseDto checkDelete(String roleId){
		if (roleId.equals(admin_role)){
			return new BaseResponseDto(false,"该角色是系统预制角色，不允许删除");
		} else if (baseUserRoleService.countByRoleId(roleId)>0){
			return new BaseResponseDto(false,"该角色已绑定用户，不允许删除");
		}
		return new BaseResponseDto(true);
	}
	
	/**
	 * 通过主键查询
	 * @param request
	 * @param response
	 * @param moduleId
	 * @throws IOException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public void view(HttpServletRequest request,
			HttpServletResponse response, String roleId) throws IOException {
		BaseRoles baseRoles=baseRolesService.selectByPrimaryKey(roleId);
		writeJSONResult(baseRoles, response);
	}
	
	/**
	 * 获取当前角色对应的数据树
	 * @param request
	 * @param response
	 * @param roleId
	 * @throws IOException
	 */
	@RequestMapping(value = "/getZtreeData", method = RequestMethod.POST)
	public void getZtreeData(HttpServletRequest request,
			HttpServletResponse response, String roleId) throws IOException {
		
		PageBounds pageBounds=new PageBounds();
		//获取当前角色对应的权限
		BaseRoleModuleExample baseRoleModuleExample=new BaseRoleModuleExample();
		BaseRoleModuleExample.Criteria criteria=baseRoleModuleExample.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		PageList<BaseRoleModuleKey> baseRoleModuleKeyList=baseRoleModuleService.selectByExample(baseRoleModuleExample, pageBounds);
		//获取当前权限树
		BaseModulesExample baseModulesExample=new BaseModulesExample();
		PageList<BaseModules> baseModulesList=baseModulesService.selectByExample(baseModulesExample, pageBounds);
		////获取权限树
		List<String> roleModuleIdList=new ArrayList<String>();
		//ztreeList 
		List<ZTree> listZtree=new ArrayList<ZTree>(); 
		//获取已经有权限集合
		for (int j = 0; j < baseRoleModuleKeyList.size(); j++) {
			roleModuleIdList.add(baseRoleModuleKeyList.get(j).getModuleId());
		}
		for (int i = 0; i < baseModulesList.size(); i++) {
			ZTree ztree=new ZTree();
			ztree.setId(baseModulesList.get(i).getModuleId());
			ztree.setName(baseModulesList.get(i).getModuleName());
			ztree.setpId(baseModulesList.get(i).getParentId());
			if(roleModuleIdList.contains(baseModulesList.get(i).getModuleId())){
				ztree.setChecked(true);
			}
			if("1".equals(baseModulesList.get(i).getExpandedStatus())){
				ztree.setOpen(true);
			}
			listZtree.add(ztree);
		}
		writeJSONArrayResult(listZtree, response);
	}
	
	/**
	 * 给角色授权
	 * @param request
	 * @param response
	 * @param moduleIds
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveRoleMoudle", method = RequestMethod.POST)
	public void saveRoleMoudle(HttpServletRequest request,
			HttpServletResponse response, String[] moduleId,String roleId) throws IOException {
		
		//首先删除角色对应的权限，然后赋值
		try {
			BaseRoleModuleExample baseRoleModuleExample=new BaseRoleModuleExample();
			BaseRoleModuleExample.Criteria criteria=baseRoleModuleExample.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			baseRoleModuleService.deleteByExample(baseRoleModuleExample);
			//添加对应的数据
			for (int i = 0; i < moduleId.length-1; i++) {
				BaseRoleModuleKey bm=new BaseRoleModuleKey();
				bm.setRoleId(roleId);
				bm.setModuleId(moduleId[i]);
				baseRoleModuleService.insertSelective(bm);
			}
			//成功返回1
			writeJSONArrayResult("1", response);
		} catch (Exception e) {
			//失败返回0
			logger.info(e.getMessage());
			writeJSONArrayResult("0", response);
		}
		
	}
}
