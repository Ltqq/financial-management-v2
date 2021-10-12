package com.example.financialmanagementv2.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.vo.EmpVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    @Select("""
            select
            e.eid ,e.ename,e.email,e.sex,e.password,e.entry_date,e.basic_salary,d.dname,p.pname 
            from employee e,department d,position p 
            ${ew.customSqlSegment}
            and e.department_id = d.did and e.position_id = p.pid
            """)
    List<EmpVO> EmpVO(@Param(Constants.WRAPPER) Wrapper<EmpVO> wrapper);

    /**
     * 根据搜索条件eid 查询指定emp
     * @param page
     * @param wrapper
     * @return
     */
    @Select("""
            select
            e.eid ,e.ename,e.email,e.sex,e.password,e.entry_date,e.basic_salary,d.dname,p.pname 
            from employee e,department d,position p 
            ${ew.customSqlSegment}
            and e.department_id = d.did and e.position_id = p.pid
            """)
    IPage<EmpVO> EmpVOPage(Page<EmpVO> page, @Param(Constants.WRAPPER) Wrapper<EmpVO> wrapper);

    /**
     * 根据查询条件did 查询指定emp
     * @param page
     * @param wrapper
     * @return
     */
    @Select("""
            select
            e.eid ,e.ename,e.email,e.sex,e.password,e.entry_date,e.basic_salary,d.dname,p.pname 
            from employee e,department d,position p 
            ${ew.customSqlSegment}
            and e.department_id = d.did and e.position_id = p.pid
            """)
    IPage<EmpVO> EmpVOPage3(Page<EmpVO> page, @Param(Constants.WRAPPER) Wrapper<EmpVO> wrapper);



    /**
     * 查询所有emp
     */
    @Select("""
            select
            e.eid ,e.ename,e.email,e.sex,e.password,e.entry_date,e.basic_salary,d.dname,p.pname 
            from employee e,department d,position p 
            where e.department_id = d.did and e.position_id = p.pid
            ${ew.customSqlSegment}
            """)
    IPage<EmpVO> EmpVOPage2(Page<EmpVO> page, @Param(Constants.WRAPPER) Wrapper<EmpVO> wrapper);



    @Select("""
            select
            e.ename,e.email,e.sex,e.entry_date,e.basic_salary,p.pname 
            from employee e,position p 
            where e.department_id = #{did} and e.position_id = p.pid
            """)
    IPage<EmpVO> DepEmpVOPage(Page<EmpVO> page, int did);

    @Select("""
            select pname from employee e,position p 
            where e.email=#{email} and e.position_id=pid
            """)
    String queryPnameByEmpEmail(String email);

}
