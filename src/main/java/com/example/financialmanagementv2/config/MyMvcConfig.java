package com.example.financialmanagementv2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("login");
        registry.addViewController("/welcome-1.html").setViewName("/page/welcome-1");
        registry.addViewController("/page/welcome-1.html").setViewName("/page/welcome-1");
        registry.addViewController("/page/welcome-2.html").setViewName("/page/welcome-2");
        registry.addViewController("/page/welcome-3.html").setViewName("/page/welcome-3");
        registry.addViewController("/page/menu.html").setViewName("/page/menu");
        registry.addViewController("/page/setting.html").setViewName("/page/setting");
        registry.addViewController("/page/employee.html").setViewName("/page/employee");
        registry.addViewController("/page/form.html").setViewName("/page/form");
        registry.addViewController("/page/form-step.html").setViewName("/page/form-step");
        registry.addViewController("/page/404.html").setViewName("/page/404");
        registry.addViewController("/page/button.html").setViewName("/page/button");
        registry.addViewController("/page/layer.html").setViewName("/page/layer");
        registry.addViewController("/page/icon.html").setViewName("/page/icon");
        registry.addViewController("/page/icon-picker.html").setViewName("/page/icon-picker");
        registry.addViewController("/page/color-select.html").setViewName("/page/color-select");
        registry.addViewController("/page/table-select.html").setViewName("/page/table-select");
        registry.addViewController("/page/upload.html").setViewName("/page/upload");
        registry.addViewController("/page/editor.html").setViewName("/page/editor");
        registry.addViewController("/page/area.html").setViewName("/page/area");
        registry.addViewController("/page/error.html").setViewName("/page/404");
        registry.addViewController("/page/table/add.html").setViewName("/page/table/add");
        registry.addViewController("/page/table/edit.html").setViewName("/page/table/edit");
        registry.addViewController("/page/allDepartment.html").setViewName("/page/allDepartment");
        registry.addViewController("/page/untreatedBill.html").setViewName("/page/untreatedBill");
        registry.addViewController("/page/processedBill.html").setViewName("/page/processedBill");
        registry.addViewController("/page/user-password.html").setViewName("/page/user-password");
        registry.addViewController("/page/user-setting.html").setViewName("/page/user-setting");
        registry.addViewController("/page/announcement.html").setViewName("/page/announcement");
        registry.addViewController("/page/billCommit.html").setViewName("/page/billCommit");
        registry.addViewController("/page/table/addAnnouncement.html").setViewName("/page/table/addAnnouncement");
//        registry.addViewController("/page/department.html").setViewName("/page/department");



    }

}
