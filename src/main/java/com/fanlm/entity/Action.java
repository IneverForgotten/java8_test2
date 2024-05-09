package com.fanlm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("action")
@Data
public class Action {
    @TableId
    private String id;


    private String url;

}