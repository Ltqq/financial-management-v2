package com.example.financialmanagementv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.service.impl.EmployeeServiceImpl;
import com.example.financialmanagementv2.vo.EmpVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @GetMapping("/allEmp")
    @ResponseBody
    @ApiOperation("管理员查看所有员工/部门主管查看本部门员工")
    public TableEntity<List<EmpVO>> allEmp(int page, int limit, @RequestParam(value = "eid", required = false) String eid, Authentication authentication) {

        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> auths = authorities.stream().findFirst();
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();

        List<EmpVO> empVOS;
        long count;
        switch (auths.get().toString()) {
            case "ROLE_admin" -> {
                empVOS = employeeService.EmpVOPage(page, limit, eid);
                count = employeeService.count();
            }
            case "ROLE_部门主管" -> {
                employeeQueryWrapper.eq("email", email);
                employeeQueryWrapper.select("department_id");
                Map<String, Object> map = employeeService.getMap(employeeQueryWrapper);
                int did = (int) map.get("department_id");
                employeeQueryWrapper = new QueryWrapper<>();
                employeeQueryWrapper.eq("department_id", did);
                empVOS = employeeService.EmpVODep(page, limit, eid, did);
                count = empVOS.size();
            }
            default -> {
                empVOS = null;
                count = 0;
            }
        }
        TableEntity<List<EmpVO>> table = new TableEntity<>(0, "ok", count, empVOS);
        return table;
    }

    @GetMapping("/EmpNums")
    @ResponseBody
    @ApiOperation("员工总数")
    public long EmpNums() {
        long count = employeeService.count();
        return count;
    }

    @PostMapping("/updateEmp")
    @ApiOperation("管理员修改员工")
    @ResponseBody
    public String updateEmp(Employee employee) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("eid", employee.getEid());
        employeeService.update(employee, wrapper);
        return "success";
    }

    @PostMapping("/deleteEmp")
    @ApiOperation("管理员删除员工")
    @ResponseBody
    public String deleteEmp(int eid) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("eid", eid);
        employeeService.remove(wrapper);
        return "success";
    }

    @PostMapping("/addEmp")
    @ApiOperation("管理员增加员工")
    @ResponseBody
    public String addEmp(Employee employee) {
        employeeService.save(employee);
        return "success";
    }

    @GetMapping("/empSex")
    @ApiOperation("返回员工性别比例图")
    @ResponseBody
    public String empSex() {
        String sexR = employeeService.empSexR();
        return sexR;
    }


    @GetMapping("/DepEmps")
    @ResponseBody
    @ApiOperation("查看某部门员工")
    public TableEntity<List<EmpVO>> DepEmps(int page, int limit, int did) {
        TableEntity<List<EmpVO>> empVOPage = employeeService.DepEmpVOPage(page, limit, did);
        return empVOPage;
    }


    @PostMapping("/findByEid")
    @ResponseBody
    @ApiOperation("根据id搜索员工")
    public TableEntity<List<EmpVO>> findByEid(int eid) {
        QueryWrapper<EmpVO> wrapper = new QueryWrapper<>();
        wrapper.eq("eid", eid);
        List<EmpVO> empVOS = employeeService.getBaseMapper().EmpVO(wrapper);
        TableEntity<List<EmpVO>> table = new TableEntity<>(0, "ok", empVOS.size(), empVOS);
        return table;
    }


    @PostMapping("/updatePwd")
    @ResponseBody
    @ApiOperation("根据id修改密码")
    public String updatePwd(String email,String old_password,String password) {
        UpdateWrapper<Employee> wrapper = new UpdateWrapper<>();
        wrapper.eq("email", email);
        QueryWrapper<Employee> wrapper1 = new QueryWrapper<>();
        wrapper.eq("email", email);
        Employee employee = employeeService.getOne(wrapper);
        if(employee!=null){
            if(employee.getPassword().equals(old_password)){
                wrapper.set("password", password);
                boolean update = employeeService.update(wrapper);
                return update?"success":"fail";
            }else{
                return "fail";
            }
        }else {
            return "fail";
        }


    }
}

