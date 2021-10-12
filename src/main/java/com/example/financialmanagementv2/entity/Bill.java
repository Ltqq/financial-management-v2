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
@ApiModel(value = "Bill对象", description = "")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账单编号")
    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    @ApiModelProperty("凭证名称")
    private String bname;

    @ApiModelProperty("报销金额")
    private double bpay;

    @ApiModelProperty("凭证图片")
    private String bimage;

    @ApiModelProperty("部门名称")
    private Integer departmentId;





    @ApiModelProperty("是否处理(0未处理,1已处理)")
    private String status;

    @ApiModelProperty("驳回原因")
    private String reason;
}
