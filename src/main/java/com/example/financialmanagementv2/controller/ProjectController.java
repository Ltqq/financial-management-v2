package com.example.financialmanagementv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.financialmanagementv2.entity.Project;
import com.example.financialmanagementv2.entity.TableEntity;
import com.example.financialmanagementv2.service.impl.ProjectServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Controller
@Transactional
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectService;

    @ApiOperation("根据部门编号查看部门项目")
    @GetMapping("/allDepProject")
    @ResponseBody
    public TableEntity<List<Project>> depProject(int page,int limit,int did){

        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("department_id", did);
        Page<Project> page1 = new Page<>(page,limit,false);
        Page<Project> projectPage = projectService.page(page1, wrapper);
        List<Project> list = projectPage.getRecords();
        QueryWrapper<Project> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("department_id", did);
        long count = projectService.count(wrapper1);
        TableEntity<List<Project>> table = new TableEntity<>(0,"ok",count,list);
        return table;
    }

    @ApiOperation("查看项目总数")
    @GetMapping("/totalProject")
    @ResponseBody
    public long totalProject(){
        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.select("count(*) as nums");
        Map<String, Object> map1 = projectService.getMap(projectQueryWrapper);
        long sumPro = (Long) map1.get("nums");
        return sumPro;
    }
}

