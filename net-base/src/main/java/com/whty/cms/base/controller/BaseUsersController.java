package com.whty.cms.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.cms.base.common.DBConstant;
import com.whty.cms.base.common.DataTableQueryMySQL;
import com.whty.cms.base.pojo.BaseRoles;
import com.whty.cms.base.pojo.BaseRolesExample;
import com.whty.cms.base.pojo.BaseUserRoleExample;
import com.whty.cms.base.pojo.BaseUserRoleKey;
import com.whty.cms.base.pojo.BaseUsers;
import com.whty.cms.base.pojo.BaseUsersExample;
import com.whty.cms.base.pojo.BaseUsersExample.Criteria;
import com.whty.cms.base.service.BaseRolesService;
import com.whty.cms.base.service.BaseUserRoleService;
import com.whty.cms.base.service.BaseUsersService;
import com.whty.cms.base.ucenter.JacksonMapper;
import com.whty.cms.base.ucenter.ResponseResult;
import com.whty.cms.base.ucenter.UCenterClient;
import com.whty.cms.common.base.BaseController;
import com.whty.cms.common.base.BaseResponseDto;
import com.whty.cms.common.base.DataTableQuery;
import com.whty.cms.common.util.CheckEmpty;
import com.whty.cms.common.util.DateUtil;

@Controller
@RequestMapping("/baseUsers")
public class BaseUsersController extends BaseController {

	@Autowired
	BaseUsersService baseUsersService;

	@Autowired
	private BaseRolesService baseRolesService;

	@Autowired
	private BaseUserRoleService baseUserRoleService;

	@Value("${init_user_pwd}")
	private String initUserPwd;

	@Value("${ucenter_api_url}")
	private String ucenter_api_url;

	/**
	 * 显示主列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "/modules/base/baseUser/userUI";
	}

	/**
	 * 表格请求
	 *
	 * @param pageSize
	 * @param currentNumber
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/tableAjax")
	public void tableAjaxData(HttpServletRequest request,
			HttpServletResponse response, BaseUsers user) throws IOException {
		// 每页条数
		DataTableQuery dt = new DataTableQueryMySQL(request);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Map<String, Object> result = buildTableData(dt, user);
		writeJSONResult(result, response, DateUtil.yyyy_MM_dd_HH_mm_ss_EN);
	}

	/**
	 * 构建数据树
	 *
	 * @param length
	 * @param start
	 * @param draw
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> buildTableData(DataTableQuery dt, BaseUsers user)
			throws IOException {
		// 当前页数
		int currentNumber = dt.getPageStart() / dt.getPageLength() + 1;
		PageBounds pageBounds = new PageBounds(currentNumber,
				dt.getPageLength());
		BaseUsersExample exmple = new BaseUsersExample();
		Criteria criteria = exmple.createCriteria();
		if (CheckEmpty.isNotEmpty(user.getUserAccount())) {
			criteria.andUserAccountLike("%" + user.getUserAccount() + "%");
		}
		if (CheckEmpty.isNotEmpty(user.getUserName())) {
			criteria.andUserNameLike("%" + user.getUserName() + "%");
		}
		// 排序条件
		if (CheckEmpty.isNotEmpty(dt.getOrderBy())
				&& CheckEmpty.isNotEmpty(dt.getOrderParam())) {
			//
			StringBuilder orderByClause = new StringBuilder("");
			orderByClause.append(dt.getOrderParam()).append(" ")
					.append(dt.getOrderBy());
			exmple.setOrderByClause(orderByClause.toString());
		}
		PageList<BaseUsers> baseUsers = baseUsersService.selectByExample(
				exmple, pageBounds);
		Map<String, Object> records = new HashMap<String, Object>();
		records.put("data", baseUsers);
		records.put("recordsTotal", baseUsers.getPaginator().getTotalCount());
		records.put("recordsFiltered", baseUsers.getPaginator().getTotalCount());
		return records;
	}

	/**
	 * 新增/编辑用戶
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request,
			HttpServletResponse response, BaseUsers user, String userRole)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		int flag = 0;
		String msg = "";
		List<Object> list = new ArrayList<Object>();
		PageBounds pageBounds = new PageBounds();
		BaseUsersExample bsExample = new BaseUsersExample();
		Criteria criteriaBs = bsExample.createCriteria();
		criteriaBs.andUserAccountEqualTo(user.getUserAccount());
		if (CheckEmpty.isNotEmpty(user.getUserId())) {
			criteriaBs.andUserIdNotEqualTo(user.getUserId());
		}
		PageList<BaseUsers> bsUser = baseUsersService.selectByExample(
				bsExample, pageBounds);
		if (bsUser.size() > 0) {
			flag = 2;
			msg = "账号已存在";
			list.add(flag);
			list.add(msg);
			writeJSONArrayResult(list, response);
			return;
		}
		if (CheckEmpty.isEmpty(user.getUserId())) {
			//新建用户，先走用户中心,用户中心注册
			ResponseResult<String> resp = UCenterClient.uc_register(user.getUserAccount(), user.getUserPassword(), user.getUserEmail(),
					user.getUserMobile(),"","1",ucenter_api_url);
			if(!(resp.getData() == null)){
				String ucUid = resp.getData().toString();
				String userId = ucUid;
				user.setUserId(userId);
				user.setUserStatus("0");
				// 未选择角色
				try {
					if (!"-1".equals(userRole)) {
						BaseUserRoleKey bs = new BaseUserRoleKey();
						bs.setRoleId(userRole);
						bs.setUserId(user.getUserId());
						baseUserRoleService.insertSelective(bs);
					}
					flag = baseUsersService.insertSelective(user);
				} catch (Exception e) {
					flag = 0;
				}
				if (flag > 0) {
					msg = "添加用户成功";
				} else {
					msg = "添加用户失败，请联系管理员";
				}
			} else {
				list.add(resp.getErrorCode());
				list.add(resp.getMessage());
			}
		} else {
			//修改用户基础信息不用连接用户中心
			flag = baseUsersService.updateSelectiveByPrimaryKey(user);
			BaseUserRoleExample example = new BaseUserRoleExample();
			BaseUserRoleExample.Criteria criteria = example
					.createCriteria();
			criteria.andUserIdEqualTo(user.getUserId());
			baseUserRoleService.deleteByExample(example);
			if (!"-1".equals(userRole)) {
				BaseUserRoleKey bs = new BaseUserRoleKey();
				bs.setRoleId(userRole);
				bs.setUserId(user.getUserId());
				baseUserRoleService.insertSelective(bs);
			}
			if (flag > 0) {
				msg = "修改用户成功";
			} else {
				msg = "修改用户失败，请联系管理员";
			}
		}
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}

	/**
	 * 根据主键删除用户
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		int flag = 0;
		String msg = "";

		// 系统管理员信息不允许删除
		Boolean isAdmin = isAdmin(userId);
		if (isAdmin) {
			flag = 2;
		} else {
			flag = baseUsersService.deleteByPrimaryKey(userId);
		}

		if (flag == 0) {
			msg = "删除失败，请联系管理员";
		} else if (flag == 2) {
			msg = "系统管理员用户不允许删除！";
		} else {
			msg = "删除成功";
		}

		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}

	/**
	 * 根据主键查询用户信息,包括角色信息
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectById", method = RequestMethod.POST)
	public void selectUserById(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		BaseUsers baseUser = baseUsersService.selectByPrimaryKey(userId);
		BaseUserRoleExample baseUserRoleExample = new BaseUserRoleExample();
		PageBounds pageBounds = new PageBounds();
		baseUserRoleExample.createCriteria().andUserIdEqualTo(userId);
		PageList<BaseUserRoleKey> baseUserRole = baseUserRoleService
				.selectByExample(baseUserRoleExample, pageBounds);
		int flag = 0;
		if (baseUser == null) {
			flag = 0;
		} else {
			flag = 1;
		}
		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(baseUser);
		if (baseUserRole.size() > 0) {
			list.add(baseUserRole.get(0).getRoleId());
		} else {
			// 用户无权限的时候传入 -1
			list.add("-1");
		}
		list.add(baseUserRole);
		writeJSONArrayResult(list, response);
	}

	/**
	 * 获取角色列表
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public void getRoleList(HttpServletRequest request,
			HttpServletResponse response, String userId) throws IOException {
		response.setCharacterEncoding("utf-8");
		BaseRolesExample example = new BaseRolesExample();
		PageBounds pageBounds = new PageBounds();
		PageList<BaseRoles> baseRoles = baseRolesService.selectByExample(
				example, pageBounds);
		writeJSONArrayResult(baseRoles, response);
	}

	/**
	 * 启用/禁用商户
	 *
	 * @param request
	 * @param response
	 * @param userId
	 * @param status
	 */
	@RequestMapping(value = "/startOrstopUser", method = RequestMethod.POST)
	public void startOrstopUser(HttpServletRequest request,
			HttpServletResponse response, String userId, String status) {
		int flag = 0;
		String msg = "";
		// 系统管理员信息不允许启用禁用
		Boolean isAdmin = isAdmin(userId);
		if (isAdmin) {
			flag = 2;
		} else {
			// 更改用户状态
			BaseUsers baseUsers = new BaseUsers();
			baseUsers.setUserId(userId);
			if (DBConstant.BaseUserStatus.START.equals(status)) {
				baseUsers.setUserStatus(DBConstant.BaseUserStatus.STOP);
			} else {
				baseUsers.setUserStatus(DBConstant.BaseUserStatus.START);
			}
			flag = baseUsersService.upateUserStatus(baseUsers);
		}

		// 封装显示信息
		if (flag == 0) {
			msg = "操作失败，请联系管理员";
		} else if (flag == 2) {
			msg = "系统管理员用户不允许启用禁用！";
		} else {
			msg = "操作成功";
		}

		List<Object> list = new ArrayList<Object>();
		list.add(flag);
		list.add(msg);
		writeJSONArrayResult(list, response);
	}

	//重置密码
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public void resetPass(HttpServletRequest request,
			HttpServletResponse response, String userId) {
		//修改密码连接用户中心
		BaseUsers user = baseUsersService.selectByPrimaryKey(userId);
		String json = UCenterClient.uc_edit(user.getUserAccount(),initUserPwd,ucenter_api_url);
		Map<String,Object> map = JacksonMapper.jsonToClass(json, Map.class);
		boolean success = (Boolean)map.get("success");
//		BaseUsers baseUsers=new BaseUsers();
//		baseUsers.setUserId(userId);
//		baseUsers.setUserPassword(initUserPwd);
		BaseResponseDto baseResponseDto=new BaseResponseDto(success);
		writeJSONResult(baseResponseDto, response);
	}

	/**
	 * 判断是否是系统管理员用户
	 *
	 * @param userId
	 * @return
	 */
	private boolean isAdmin(String userId) {
		BaseUsers baseUsers = this.baseUsersService.selectByPrimaryKey(userId);
		if ("admin".equals(baseUsers.getUserAccount())) {
			return true;
		}
		return false;
	}
}
