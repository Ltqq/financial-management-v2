package com.example.financialmanagementv2.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

@Controller
public class IndexController2 {


    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/login.html")
    public String loginHtml() {
        return "login";
    }

    @GetMapping("/page/department.html")
    @ApiOperation(value = "department_detail", httpMethod = "GET")
    public String department_detail(int did, HttpServletRequest request) {
        request.setAttribute("did", did);
        return "page/department";
    }


    @ApiOperation(value = "根据角色初始化页面", httpMethod = "GET")
    @RequestMapping("/init")
    @ResponseBody
    public String initPath(Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> auths = authorities.stream().findFirst();

        String filepaht= ClassUtils.getDefaultClassLoader().getResource("static").getPath();

        InputStream is = switch (auths.get().toString()) {
            case "ROLE_admin" ->  this.getClass().getClassLoader().getResourceAsStream("static/api/init.json");
            case "ROLE_部门主管" ->this.getClass().getClassLoader().getResourceAsStream("static/api/init3.json");
            default -> this.getClass().getClassLoader().getResourceAsStream("static/api/init2.json");
        };

        BufferedInputStream bis = new BufferedInputStream(is);

        StringBuffer sb = new StringBuffer();
        //读取
        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = bis.read(bys)) != -1) {
            sb.append(new String(bys, 0, len));  //通过使用平台的默认字符集解码指定的 byte 子数组，构造一个新的 String。
        }
        //关闭
        sb.toString().replace("/n", "");
        return sb.toString();
    }

}
