package com.example.financialmanagementv2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Project对象", description = "")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目编号")
    @TableId(value = "projectid", type = IdType.AUTO)
    private Integer projectid;

    @ApiModelProperty("项目名称")
    private String projectname;

    @ApiModelProperty("项目收入")
    private Double projectrevenue;

    @ApiModelProperty("项目支出")
    private Double projectpay;

    @ApiModelProperty("部门")
    private Integer departmentId;


}
