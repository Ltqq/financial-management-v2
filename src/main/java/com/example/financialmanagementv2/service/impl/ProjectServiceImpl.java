package com.example.financialmanagementv2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagementv2.entity.Project;
import com.example.financialmanagementv2.mapper.ProjectMapper;
import com.example.financialmanagementv2.service.IProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Service
@Transactional
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    public double projectE(int did){
        QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.eq("department_id", did);
        projectQueryWrapper.select("sum(projectpay) as sumProject");
        Map<String, Object> Pmap = getMap(projectQueryWrapper);
        if(Pmap!=null){
            return (double) Pmap.get("sumProject");
        }else {
            return 0;
        }

    }
}
