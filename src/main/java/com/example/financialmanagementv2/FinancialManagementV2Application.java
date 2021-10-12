package com.example.financialmanagementv2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@MapperScan("com.example.financialmanagementv2.mapper")
public class FinancialManagementV2Application {

    public static void main(String[] args) {
        SpringApplication.run(FinancialManagementV2Application.class, args);
    }

}
