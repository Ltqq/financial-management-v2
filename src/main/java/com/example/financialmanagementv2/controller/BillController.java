package com.example.financialmanagementv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagementv2.entity.Bill;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.service.impl.BillServiceImpl;
import com.example.financialmanagementv2.service.impl.EmployeeServiceImpl;
import com.example.financialmanagementv2.vo.BillVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Controller
@Transactional
public class BillController {
    @Autowired
    BillServiceImpl billService;

    @Autowired
    EmployeeServiceImpl employeeService;

    @GetMapping("/allUntreatedBill")
    @ResponseBody
    @ApiOperation("管理员查看所有未处理账单/部门主管查看本部门未处理账单")
    public TableEntity<List<BillVO>> allUntreatedBill(int page, int limit, Authentication authentication) {
        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> auths = authorities.stream().findFirst();

        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();

        List<BillVO> billVOS ;
        long count;

        switch (auths.get().toString()) {
            case "ROLE_admin" -> {billVOS = billService.NoBillVOPage(page, limit,null);
                count = allNoProcessedBillNums();
            }
            case "ROLE_部门主管" -> {
                employeeQueryWrapper.eq("email", email);
                employeeQueryWrapper.select("department_id");
                Map<String, Object> map = employeeService.getMap(employeeQueryWrapper);
                int did = (int) map.get("department_id");
                billVOS = billService.NoBillVOPage(page, limit, did);
                count = billVOS.size();
            }
            default -> {
                billVOS = null;
                count=0L;
            }
        }
        TableEntity<List<BillVO>> table = new TableEntity<>(0,"ok",count,billVOS);

        return table;
    }

    public Long allUntreatedBillNums() {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "未处理");
        return billService.count(queryWrapper);
    }

    @GetMapping("/allProcessedBill")
    @ResponseBody
    @ApiOperation("管理员查看所有已处理账单/部门主管查看本部门已处理账单")
    public TableEntity<List<BillVO>> allProcessedBill(int page, int limit, Authentication authentication) {
        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> auths = authorities.stream().findFirst();

        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();

        List<BillVO> billVOS ;
        long count;

        switch (auths.get().toString()) {
            case "ROLE_admin" -> {billVOS = billService.YBillVOPage(page, limit,null);
            count = allProcessedBillNums();
            }
            case "ROLE_部门主管" -> {
                employeeQueryWrapper.eq("email", email);
                employeeQueryWrapper.select("department_id");
                Map<String, Object> map = employeeService.getMap(employeeQueryWrapper);
                int did = (int) map.get("department_id");
                billVOS = billService.YBillVOPage(page, limit, did);
                count = billVOS.size();
            }
            default -> {
                billVOS = null;
                count=0L;
            }
        }
        TableEntity<List<BillVO>> table = new TableEntity<>(0,"ok",count,billVOS);

        return table;
    }

    public Long allProcessedBillNums() {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "已处理");
        return billService.count(queryWrapper);
    }
    public Long allNoProcessedBillNums() {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "未处理");
        return billService.count(queryWrapper);
    }

    @PostMapping("/passThrough")
    @ResponseBody
    @ApiOperation("通过bill申请")
    public String passThrough(int bid) {
        UpdateWrapper<Bill> wrapper = new UpdateWrapper<>();
        wrapper.eq("bid", bid);
        wrapper.set("status", "已处理");
        billService.update(wrapper);
        return "success";
    }

    @PostMapping("/rejected")
    @ResponseBody
    @ApiOperation("驳回bill申请")
    public String turnDown(int bid, String reason) {
        UpdateWrapper<Bill> wrapper = new UpdateWrapper<>();
        wrapper.eq("bid", bid);
        wrapper.set("status", "已驳回");
        wrapper.set("reason", reason);
        billService.update(wrapper);
        return "success";
    }

    @PostMapping("/deleteBill")
    @ResponseBody
    @ApiOperation("管理员bill删除记录")
    public String deleteBill(int bid) {
        UpdateWrapper<Bill> wrapper = new UpdateWrapper<>();
        wrapper.eq("bid", bid);
        billService.remove(wrapper);
        return "success";
    }

    @GetMapping("/depBill")
    @ResponseBody
    @ApiOperation("管理员查看部门所有bill")
    public TableEntity<List<Bill>> depBill(int page,int limit, int did) {
        UpdateWrapper<Bill> wrapper = new UpdateWrapper<>();
        wrapper.eq("department_id", did);
        Page<Bill> page1 = new Page<>(page,limit, false);
        Page<Bill> billPage = billService.page(page1, wrapper);
        List<Bill> records = billPage.getRecords();
        Long aLong = depBillNums(did);
        TableEntity<List<Bill>> table = new TableEntity<>(0,"ok",aLong,records);
        return table;
    }

    public Long depBillNums(int did) {
        UpdateWrapper<Bill> wrapper = new UpdateWrapper<>();
        wrapper.eq("department_id", did);
        return billService.count(wrapper);
    }


    @RequestMapping("/commit")
    @ResponseBody
    public String billCommit(String bname,Double bpay,Integer departmentId, MultipartFile file) throws IOException {
        Bill bill = new Bill();
        bill.setBname(bname);
        bill.setBpay(bpay);
        bill.setDepartmentId(departmentId);
        bill.setStatus("未处理");
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName= System.currentTimeMillis()+suffixName;
        file.transferTo(new File("D:\\Study\\igeek\\financial-management-v2\\src\\main\\resources\\static\\img\\"+newFileName));
        bill.setBimage(newFileName);
        billService.save(bill);
        return "1";
    }

}

