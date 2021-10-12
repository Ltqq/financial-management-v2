package com.example.financialmanagementv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.financialmanagementv2.entity.Department;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.service.impl.DepartmentServiceImpl;
import com.example.financialmanagementv2.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DepartmentController {

    @Autowired
    DepartmentServiceImpl departmentService;


    @Autowired
    EmployeeServiceImpl employeeService;


    @GetMapping("/allDepName")
    @ResponseBody
    @ApiOperation("查看所有部门信息")
    public List<Department> allDepName() {
        List<Department> dname = departmentService.list(null);
        return dname;
    }

    @GetMapping("/totalExpenses")
    @ResponseBody
    @ApiOperation("返回总支出金额")
    public Double totalExpenses() {
        return departmentService.totalExpenses();
    }

    @GetMapping("/totalRevenue")
    @ResponseBody
    @ApiOperation("返回总收入金额")
    public Double totalRevenue() {
        return departmentService.totalRevenue();
    }

    @GetMapping("/allDep")
    @ResponseBody
    @ApiOperation("管理员查看所有部门信息/部门主管查看本部门信息")
    public TableEntity<List<Department>> allDep(Authentication authentication) {
        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> auths = authorities.stream().findFirst();

        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();

        List<Department> dname;
        switch (auths.get().toString()) {
            case "ROLE_admin" -> dname = departmentService.list(null);
            case "ROLE_部门主管" -> {
                employeeQueryWrapper.eq("email", email);
                employeeQueryWrapper.select("department_id");
                Map<String, Object> map = employeeService.getMap(employeeQueryWrapper);
                int did = (int) map.get("department_id");
                departmentQueryWrapper.eq("did", did);
                dname = departmentService.list(departmentQueryWrapper);
            }
            default -> {
                dname = null;
            }
        }

        TableEntity<List<Department>> table = new TableEntity<>(0, "ok", dname.size(), dname);
        return table;
    }


    @GetMapping("/depPay")
    @ApiOperation("返回部门支出统计图")
    @ResponseBody
    public String depPay(int did) {
        String pay = departmentService.depPay(did);
        return pay;
    }

    @GetMapping("/payAll")
    @ApiOperation("返回总支出统计图")
    @ResponseBody
    public String payAll() {
        String pay = departmentService.payAll();
        return pay;
    }

    @GetMapping("/depCompared")
    @ApiOperation("返回各部门支出统计图")
    @ResponseBody
    public String depCompared() {
        String depCompared = departmentService.depCompared();
        return depCompared;
    }

    @GetMapping("/depRevenue")
    @ApiOperation("返回总收入统计图")
    @ResponseBody
    public String depRevenue() {
        String depRevenue = departmentService.depRevenue();
        return depRevenue;
    }
}

