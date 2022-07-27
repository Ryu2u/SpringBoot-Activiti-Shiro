package com.ryuzu.activiti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 请假审批实体类
 * @author Ryuzu
 * @date 2022/7/26 15:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacation {

    /**
     * 请假申请人
     */
    private String username;
    /**
     * act_re_procdef 中的key
     * 流程定义key
     */
    private String key;
    /**
     * 请假天数
     */
    private Integer days;
    /**
     * 请假类型
     */
    private String type;
    /**
     * 请假原因
     */
    private String content;

}
