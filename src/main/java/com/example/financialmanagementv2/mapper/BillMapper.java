package com.example.financialmanagementv2.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagementv2.entity.Bill;
import com.example.financialmanagementv2.vo.BillVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
public interface BillMapper extends BaseMapper<Bill> {
    @Select("""
    select b.bid,b.bname,b.bpay,b.bimage,d.dname,b.status
    from bill b,department d
    where b.department_id = d.did and b.status='未处理'
    ${ew.customSqlSegment}
    """)
    IPage<BillVO> NoBillVOPage(Page<BillVO> page, @Param(Constants.WRAPPER) Wrapper<BillVO> wrapper); @Select("""
    select b.bid,b.bname,b.bpay,b.bimage,d.dname,b.status
    from bill b,department d
    ${ew.customSqlSegment}
    and b.department_id = d.did and b.status='未处理'
    Order by b.bid asc
    """)
    IPage<BillVO> NoBillVOPage2(Page<BillVO> page, @Param(Constants.WRAPPER) Wrapper<BillVO> wrapper);

    @Select("""
    select b.bid,b.bname,b.bpay,b.bimage,d.dname,b.status
    from bill b,department d
    where b.department_id = d.did and b.status='已处理'
    ${ew.customSqlSegment}
    """)
    IPage<BillVO> YBillVOPage(Page<BillVO> page, @Param(Constants.WRAPPER) Wrapper<BillVO> wrapper);
    @Select("""
    select b.bid,b.bname,b.bpay,b.bimage,d.dname,b.status
    from bill b,department d
    ${ew.customSqlSegment}
    and b.department_id = d.did and b.status='已处理'
    Order by b.bid asc
    """)
    IPage<BillVO> YBillVOPage2(Page<BillVO> page, @Param(Constants.WRAPPER) Wrapper<BillVO> wrapper);

}
