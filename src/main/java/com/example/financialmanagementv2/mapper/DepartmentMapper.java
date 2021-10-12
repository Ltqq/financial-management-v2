package com.example.financialmanagementv2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.financialmanagementv2.entity.Department;
import com.example.financialmanagementv2.vo.DepVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("""
            select d.dname, sum(basic_salary) as sum
            from department d
                   left join employee e on d.did = e.department_id
            group by d.dname;
            """)
    @Result(column = "sum",property = "amount")
    @Result(column = "dname",property = "name")
    List<DepVO> allDepSalary();

    @Select("""
            select d.dname, sum(projectpay) as sum
            from department d
                   left join project p on d.did = p.department_id
            group by d.dname;
            """)
    @Result(column = "sum",property = "amount")
    @Result(column = "dname",property = "name")
    List<DepVO> allDepProjectPay();

    @Select("""
            select d.dname, sum(projectrevenue) as sum
            from department d
                   left join project p on d.did = p.department_id
            group by d.dname;
            """)
    @Result(column = "sum",property = "amount")
    @Result(column = "dname",property = "name")
    List<DepVO> allDepProjectRevenue();



    @Select("""
            select d.dname, sum(bpay) as sum
            from department d
                   left join bill b on d.did = b.department_id
            group by d.dname;
            """)
    @Result(column = "sum",property = "amount")
    @Result(column = "dname",property = "name")
    List<DepVO> allDepBillPay();
}
