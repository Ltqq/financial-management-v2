package com.example.financialmanagementv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagementv2.entity.Bill;
import com.example.financialmanagementv2.mapper.BillMapper;
import com.example.financialmanagementv2.service.IBillService;
import com.example.financialmanagementv2.vo.BillVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Service
@Transactional
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

    @Override
    public List<BillVO> NoBillVOPage(int page,int limit,Integer did) {
        if(did==null){
            Page<BillVO> page1 = new Page<>(page,limit, false);
            QueryWrapper<BillVO> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("bid");
            IPage<BillVO> billVOPage = baseMapper.NoBillVOPage(page1, wrapper);
            return billVOPage.getRecords();
        }else {
            Page<BillVO> page1 = new Page<>(page,limit, false);
            QueryWrapper<BillVO> wrapper = new QueryWrapper<>();
            wrapper.eq("department_id", did);
            IPage<BillVO> billVOPage = baseMapper.NoBillVOPage2(page1, wrapper);
            return billVOPage.getRecords();
        }
    }

    @Override
    public List<BillVO> YBillVOPage(int page,int limit,Integer did) {
        if(did==null){
            Page<BillVO> page1 = new Page<>(page,limit, false);
            QueryWrapper<BillVO> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("bid");
            IPage<BillVO> billVOPage = baseMapper.YBillVOPage(page1, wrapper);
            return billVOPage.getRecords();
        }else {
            Page<BillVO> page1 = new Page<>(page,limit, false);
            QueryWrapper<BillVO> wrapper = new QueryWrapper<>();
            wrapper.eq("department_id", did);
            IPage<BillVO> billVOPage = baseMapper.YBillVOPage2(page1, wrapper);
            return billVOPage.getRecords();
        }

    }

    public double BillE(int did) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("department_id", did);
        billQueryWrapper.select("sum(bpay) as sumBill");
        Map<String, Object> Bmap = getMap(billQueryWrapper);
        if (Bmap != null) {
            return (double) Bmap.get("sumBill");
        } else {
            return 0;
        }
    }
}
