package com.whty.cms.base.pojo.udf;

import com.whty.cms.base.pojo.BaseModulesExample;
import com.whty.cms.base.pojo.BaseUsersExample;

public class BaseModulesComplexExample {
	public BaseModulesComplexExample(){
		this.baseModulesExample = new BaseModulesExample();
		this.baseUsersExample = new BaseUsersExample();
	}
	
	private BaseModulesExample baseModulesExample;
	
	private BaseUsersExample baseUsersExample;

	public BaseModulesExample getBaseModulesExample() {
		return baseModulesExample;
	}

	public void setBaseModulesExample(BaseModulesExample baseModulesExample) {
		this.baseModulesExample = baseModulesExample;
	}

	public BaseUsersExample getBaseUsersExample() {
		return baseUsersExample;
	}

	public void setBaseUsersExample(BaseUsersExample baseUsersExample) {
		this.baseUsersExample = baseUsersExample;
	}
}
