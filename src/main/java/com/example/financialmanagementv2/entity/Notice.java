package com.example.financialmanagementv2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value = "Notice对象", description = "")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公告编号")
    @TableId(value = "nid", type = IdType.AUTO)
    private Integer nid;

    @ApiModelProperty("发布时间")
    private LocalDate date;

    @ApiModelProperty("标题")
    private String topic;

    @ApiModelProperty("内容")
    private String content;


}
