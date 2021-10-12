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
@ApiModel(value = "Department对象", description = "")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门编号")
    @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    private String dname;

    @ApiModelProperty("部门人数")
    private Integer ecount;


}
