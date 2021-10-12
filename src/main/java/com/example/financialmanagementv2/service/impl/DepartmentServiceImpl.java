package com.example.financialmanagementv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagementv2.entity.Bill;
import com.example.financialmanagementv2.entity.Department;
import com.example.financialmanagementv2.entity.Employee;
import com.example.financialmanagementv2.entity.Project;
import com.example.financialmanagementv2.mapper.DepartmentMapper;
import com.example.financialmanagementv2.service.IDepartmentService;
import com.example.financialmanagementv2.vo.DepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Margin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    BillServiceImpl billService;


    public Double totalExpenses() {
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.select("sum(basic_salary) as sumAll");
        Map<String, Object> map = employeeService.getMap(employeeQueryWrapper);
        Double sumSalary = (Double) map.get("sumAll");

        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.select("sum(projectpay) as sumAll");
        Map<String, Object> map1 = projectService.getMap(projectQueryWrapper);
        Double sumPro = (Double) map1.get("sumAll");

        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.select("sum(bpay) as sumAll");
        Map<String, Object> map2 = billService.getMap(billQueryWrapper);
        Double sumBill = (Double) map2.get("sumAll");
        return sumSalary + sumBill + sumPro;
    }

    public Double totalRevenue() {
        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.select("sum(projectrevenue) as sumAll");
        Map<String, Object> map1 = projectService.getMap(projectQueryWrapper);
        Double sumPro = (Double) map1.get("sumAll");
        return sumPro;
    }

    @Override
    public String depPay(int did) {
        //查询部门员工所有工资
        double salary = employeeService.salary(did);
        //查询部门所有项目支出
        double projectE = projectService.projectE(did);
        //查询所有已处理账单支出
        double billE = billService.BillE(did);
        List<Double> payList = new ArrayList<>();
        payList.add(salary);
        payList.add(projectE);
        payList.add(billE);

        List<String> itemList = new ArrayList<>();
        itemList.add("工资支出");
        itemList.add("项目支出");
        itemList.add("账单支出");
        StringColumn name = StringColumn.create("name", itemList);
        DoubleColumn pay = DoubleColumn.create("pay", payList);
        Table table = Table.create("pay", name, pay);

        Margin margin = Margin.builder()
                .left(20)
                .right(0)
                .bottom(20)
                .autoExpand(true)
                .top(40).build();
        Layout layout = Layout.builder()
                .width(600)
                .height(400)
                .paperBgColor("#ebeff2")
                .plotBgColor("#ebeff2")
                .title("支出比例")
                .margin(margin)
                .build();
        Figure figure = PiePlot.create("expenditure", table, "name", "pay");
        figure.setLayout(layout);
        return figure.asJavascript("dataGraph");
    }


    /**
     * 查看所有部门的总支出
     */
    public String payAll() {
        List<DepVO> allDepSalary = baseMapper.allDepSalary();
        List<DepVO> allDepProjectPay = baseMapper.allDepProjectPay();
        List<DepVO> allDepBillPay = baseMapper.allDepBillPay();

        Optional<Double> salary = allDepSalary.stream().map(DepVO::getAmount).reduce(Double::sum);
        Optional<Double> projectPay = allDepProjectPay.stream().map(DepVO::getAmount).reduce(Double::sum);
        Optional<Double> BillPay = allDepBillPay.stream().map(DepVO::getAmount).reduce(Double::sum);

        List<Double> list = new ArrayList<>();

        if (salary.isPresent()) {
            list.add(salary.get());
        } else {
            list.add(0.0);
        }
        if (projectPay.isPresent()) {
            list.add(projectPay.get());
        } else {
            list.add(0.0);
        }
        if (BillPay.isPresent()) {
            list.add(BillPay.get());
        } else {
            list.add(0.0);
        }


        List<String> itemList = new ArrayList<>();
        itemList.add("工资支出");
        itemList.add("项目支出");
        itemList.add("账单支出");
        StringColumn name = StringColumn.create("name", itemList);
        DoubleColumn doubles = DoubleColumn.create("pay", list);
        Table t = Table.create("支出占比", name, doubles);

        Margin margin = Margin.builder()
                .bottom(20)
                .autoExpand(true)
                .top(40).build();
        Layout layout = Layout.builder()
                .width(400)
                .height(300)
                .paperBgColor("#ebeff2")
                .plotBgColor("red")
                .title("支出比例")
                .margin(margin)
                .build();


        Figure figure = HorizontalBarPlot.create("总支出占比", t, "name", "pay");
        figure.setLayout(layout);
        return figure.asJavascript("payAll");
    }

    public String depCompared() {
        List<DepVO> allDepSalary = baseMapper.allDepSalary();
        List<DepVO> allDepProjectPay = baseMapper.allDepProjectPay();
        List<DepVO> allDepBillPay = baseMapper.allDepBillPay();

        List<String> depName = allDepSalary.stream().map(DepVO::getName).toList();
        List<Double> salary = allDepSalary.stream().map(DepVO::getAmount).toList();
        List<Double> projectPay = allDepProjectPay.stream().map(DepVO::getAmount).toList();
        List<Double> BillPay = allDepBillPay.stream().map(DepVO::getAmount).toList();

        StringColumn dep = StringColumn.create("name", depName);
        DoubleColumn depSalary = DoubleColumn.create("salary", salary);
        DoubleColumn depProjectPay = DoubleColumn.create("project", projectPay);
        DoubleColumn depBillPay = DoubleColumn.create("bill", BillPay);
        Table t = Table.create("dep", dep, depSalary, depProjectPay, depBillPay);

        List<Double> list = new ArrayList<>();
        for (Row row : t) {
            Double salary1 = row.getDouble(1);
            Double project1 = row.getDouble(2);
            Double bill1 = row.getDouble(3);
            list.add(salary1 + project1 + bill1);
        }
        DoubleColumn sum = DoubleColumn.create("sum", list);
        Table table = Table.create("各部门支出比例", dep, sum);
        Margin margin = Margin.builder()
                .bottom(20)
                .autoExpand(true)
                .top(40).build();
        Layout layout = Layout.builder()
                .width(480)
                .height(400)
                .paperBgColor("#ebeff2")
                .plotBgColor("red")
                .title("各部门支出比例")
                .margin(margin)
                .build();

        Figure figure = PiePlot.create("总支出占比", table, "name", "sum");
        figure.setLayout(layout);
        return figure.asJavascript("spendingRatio");

    }

    public String depRevenue() {
        List<DepVO> allDepRevenue = baseMapper.allDepProjectRevenue();

        List<String> depName = allDepRevenue.stream().map(DepVO::getName).toList();
        List<Double> revenue = allDepRevenue.stream().map(DepVO::getAmount).toList();

        StringColumn dep = StringColumn.create("name", depName);
        DoubleColumn depRevenue = DoubleColumn.create("revenue", revenue);


        Table table = Table.create("各部门支出比例", dep, depRevenue);
        Margin margin = Margin.builder()
                .bottom(20)
                .autoExpand(true)
                .top(40).build();
        Layout layout = Layout.builder()
                .width(480)
                .height(400)
                .paperBgColor("#ebeff2")
                .plotBgColor("red")
                .title("各部门收入比例")
                .margin(margin)
                .build();

        Figure figure = PiePlot.create("总收入占比", table, "name", "revenue");
        figure.setLayout(layout);
        return figure.asJavascript("incomeRatio");

    }
}
