package com.example.financialmanagementv2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialmanagementv2.entity.Bill;
import com.example.financialmanagementv2.vo.BillVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
public interface IBillService extends IService<Bill> {

    List<BillVO> NoBillVOPage(int page,int limit,Integer did);
    List<BillVO> YBillVOPage(int page,int limit,Integer did);

}
