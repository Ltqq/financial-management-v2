package com.example.financialmanagementv2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUserService implements UserDetailsService {

    @Autowired
    EmployeeServiceImpl employeeService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", s);
        Employee employee = employeeService.getOne(queryWrapper);
        if (employee == null) {
            throw new UsernameNotFoundException("用户名不存在");
        } else {
            String role = employeeService.getBaseMapper().queryPnameByEmpEmail(s);
            QueryWrapper<Employee> wrapper = new QueryWrapper<>();
            List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+role);
            return new User(employee.getEmail()
                    , new BCryptPasswordEncoder().encode(employee.getPassword())
                    , list);
        }
    }
}
