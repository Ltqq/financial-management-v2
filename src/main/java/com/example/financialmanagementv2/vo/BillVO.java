package com.example.financialmanagementv2.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillVO {
    private Integer bid;
    private String bname;
    private Double bpay;
    private String bimage;
    private String dname;
    private String status;
}
