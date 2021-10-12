package com.example.financialmanagementv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.mapper.EmployeeMapper;
import com.example.financialmanagementv2.service.IEmployeeService;
import com.example.financialmanagementv2.vo.EmpVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.api.PiePlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Margin;

import java.util.List;
import java.util.Map;

import static tech.tablesaw.aggregate.AggregateFunctions.count;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Service
@Transactional
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {


    /**
     * 根据用户名密码判断登陆
     *
     */
    public boolean is_login(String username, String password) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("ename", username)
                .eq("password", password);
        Employee employee = getOne(wrapper);
        return employee != null;
    }



    @Override
    public List<EmpVO> EmpVOPage(int page,int limit,String eid) {
        if(eid!=""&&eid!=null){
            Integer integer = Integer.valueOf(eid);
            EmployeeMapper baseMapper = this.baseMapper;
            Page<EmpVO> page1 = new Page<>(page, limit,false);
            QueryWrapper<EmpVO> wrapper = new QueryWrapper<>();
            wrapper.eq("eid", integer);
            IPage<EmpVO> empVOS = baseMapper.EmpVOPage(page1, wrapper);
            return empVOS.getRecords();
        }else{
            EmployeeMapper baseMapper = this.baseMapper;
            Page<EmpVO> page1 = new Page<>(page, limit,false);
            QueryWrapper<EmpVO> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("eid");
            IPage<EmpVO> empVOS = baseMapper.EmpVOPage2(page1, wrapper);
            return empVOS.getRecords();
        }

    }

    public List<EmpVO> EmpVODep(int page,int limit,String eid,int did) {
        if(eid!=""&&eid!=null){
            Integer integer = Integer.valueOf(eid);
            EmployeeMapper baseMapper = this.baseMapper;
            Page<EmpVO> page1 = new Page<>(page, limit,false);
            QueryWrapper<EmpVO> wrapper = new QueryWrapper<>();
            wrapper.eq("eid", integer);
            wrapper.eq("department_id",did );
            IPage<EmpVO> empVOS = baseMapper.EmpVOPage(page1, wrapper);
            return empVOS.getRecords();
        }else{
            Page<EmpVO> page1 = new Page<>(page, limit,false);
            QueryWrapper<EmpVO> wrapper = new QueryWrapper<>();
            wrapper.eq("department_id",did);
            IPage<EmpVO> empVOS = baseMapper.EmpVOPage3(page1, wrapper);
            return empVOS.getRecords();
        }

    }

    @Override
    public String empSexR() {
        //        System.setProperty("java.awt.headless","false");
        List<Employee> list = list();
        List<String> ename = list.stream().map(Employee::getEname).toList();
        List<String> esex = list.stream().map(Employee::getSex).toList();
        StringColumn name = StringColumn.create("name", ename);
        StringColumn sex = StringColumn.create("sex", esex);
        Table t = Table.create("Employee", name, sex);
        Table t1 = t.summarize("name", count).by("sex");
        System.out.println(t1.print());
        Margin margin = Margin.builder()
                .left(20)
                .right(0)
                .bottom(0)
                .autoExpand(true)
                .top(40).build();
        Layout layout = Layout.builder()
                .width(300)
                .height(300)
                .title("性别比例")
                .margin(margin)
                .build();
        Figure figure = PiePlot.create("employee", t1, "sex", "Count [name]");
        figure.setLayout(layout);
        return figure.asJavascript("sexR");
    }


    @Override
    public TableEntity<List<EmpVO>> DepEmpVOPage(int page,int limit, int did) {
        EmployeeMapper baseMapper = this.baseMapper;
        Page<EmpVO> page1 = new Page<>(page, limit,false);
        IPage<EmpVO> empVOS = baseMapper.DepEmpVOPage(page1, did);
        List<EmpVO> records = empVOS.getRecords();

        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", did);
        long count = count(wrapper);

        TableEntity<List<EmpVO>> table = new TableEntity<>(0,"ok",count,records);
        return table;
    }


    public double salary(int did) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", did);
        wrapper.select("sum(basic_salary) as sumSalary");
        Map<String, Object> Smap = getMap(wrapper);
        if (Smap != null) {
            return (double) Smap.get("sumSalary");
        } else {
            return 0;
        }
    }

}
