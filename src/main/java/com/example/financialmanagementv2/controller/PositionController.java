package com.example.financialmanagementv2.controller;


import com.example.financialmanagementv2.entity.Position;
import com.example.financialmanagementv2.service.impl.PositionServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@Controller
@Transactional
public class PositionController {
    @Autowired
    PositionServiceImpl positionService;

    @GetMapping("/allPName")
    @ResponseBody
    @ApiOperation("查看所有职位信息")
    public List<Position> allDepName() {
        List<Position> list = positionService.list();
        return list;
    }
}

