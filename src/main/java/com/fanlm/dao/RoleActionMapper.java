package com.fanlm.dao;

import com.fanlm.entity.RoleAction;
import com.fanlm.entity.RoleActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleActionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    long countByExample(RoleActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int deleteByExample(RoleActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int insert(RoleAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int insertSelective(RoleAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    List<RoleAction> selectByExample(RoleActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    RoleAction selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByExampleSelective(@Param("record") RoleAction record, @Param("example") RoleActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByExample(@Param("record") RoleAction record, @Param("example") RoleActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByPrimaryKeySelective(RoleAction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByPrimaryKey(RoleAction record);
}