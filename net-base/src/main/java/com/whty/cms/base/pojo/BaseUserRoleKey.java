package com.whty.cms.base.pojo;

public class BaseUserRoleKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BASE_USER_ROLE.USER_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BASE_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    private String roleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BASE_USER_ROLE.USER_ID
     *
     * @return the value of BASE_USER_ROLE.USER_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BASE_USER_ROLE.USER_ID
     *
     * @param userId the value for BASE_USER_ROLE.USER_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BASE_USER_ROLE.ROLE_ID
     *
     * @return the value of BASE_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BASE_USER_ROLE.ROLE_ID
     *
     * @param roleId the value for BASE_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Fri Apr 10 11:28:31 CST 2015
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}