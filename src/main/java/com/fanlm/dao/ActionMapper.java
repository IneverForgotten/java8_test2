package com.fanlm.dao;

import com.fanlm.entity.Action;
import com.fanlm.entity.ActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    long countByExample(ActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int deleteByExample(ActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int insert(Action record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int insertSelective(Action record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    List<Action> selectByExample(ActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    Action selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByExampleSelective(@Param("record") Action record, @Param("example") ActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByExample(@Param("record") Action record, @Param("example") ActionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByPrimaryKeySelective(Action record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table action
     *
     * @mbg.generated Mon Dec 07 23:24:13 CST 2020
     */
    int updateByPrimaryKey(Action record);
}