package com.whty.cms.base.pojo;

public class BaseRoleModuleKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BASE_ROLE_MODULE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BASE_ROLE_MODULE.MODULE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    private String moduleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BASE_ROLE_MODULE.ROLE_ID
     *
     * @return the value of BASE_ROLE_MODULE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BASE_ROLE_MODULE.ROLE_ID
     *
     * @param roleId the value for BASE_ROLE_MODULE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BASE_ROLE_MODULE.MODULE_ID
     *
     * @return the value of BASE_ROLE_MODULE.MODULE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BASE_ROLE_MODULE.MODULE_ID
     *
     * @param moduleId the value for BASE_ROLE_MODULE.MODULE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
    }
}