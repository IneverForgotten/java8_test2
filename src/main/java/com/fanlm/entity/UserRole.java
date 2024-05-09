package com.fanlm.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user_role")
public class UserRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.ROLE_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role.USER_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.ID
     *
     * @return the value of user_role.ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.ID
     *
     * @param id the value for user_role.ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.ROLE_ID
     *
     * @return the value of user_role.ROLE_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.ROLE_ID
     *
     * @param roleId the value for user_role.ROLE_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role.USER_ID
     *
     * @return the value of user_role.USER_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role.USER_ID
     *
     * @param userId the value for user_role.USER_ID
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}