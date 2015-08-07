package com.whty.cms.common.base;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private String id;	//菜单ID

	private String name;	//菜单名称		

	private String icon;	//菜单图标

	private String url;		//菜单地址

	private String parentId;	//父级菜单

	private int order;		//排序

	private String memo = "";	//备注

	private List<Menu> subMenus = new ArrayList<Menu>();
	
	public Menu(){
		super();
	}
	
	public Menu(String id, String name, String icon, String url,
			String parentId, int order, String memo, List<Menu> subMenus) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.url = url;
		this.parentId = parentId;
		this.order = order;
		this.memo = memo;
		this.subMenus.addAll(subMenus);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isHasChildren() {
		return this.subMenus.size() > 0;
	}
	
	/**
	 * @param subMenus the subMenus to set
	 */
	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}
}
