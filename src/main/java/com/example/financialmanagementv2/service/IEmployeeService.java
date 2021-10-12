package com.example.financialmanagementv2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.vo.EmpVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
public interface IEmployeeService extends IService<Employee> {


    List<EmpVO> EmpVOPage(int page,int limit,String eid);

    TableEntity<List<EmpVO>> DepEmpVOPage(int page, int limit, int did);

    String empSexR();


}
