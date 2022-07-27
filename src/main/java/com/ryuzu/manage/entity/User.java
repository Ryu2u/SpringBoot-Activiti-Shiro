package com.ryuzu.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.lang.String;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "工号")
    private String jobno;

    @ApiModelProperty(value = "员工名字")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "加盐")
    private String salt;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "身份证号")
    private String idno;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "状态：1在职,2离职")
    private String status;

    @ApiModelProperty(value = "生成者")
    @TableField("created_by")
    private Integer createdBy;

    @ApiModelProperty(value = "生成时间")
    @TableField("created_time")
    private String createdTime;

    @ApiModelProperty(value = "修改者")
    @TableField("modified_by")
    private Integer modifiedBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("modified_time")
    private String modifiedTime;


}
