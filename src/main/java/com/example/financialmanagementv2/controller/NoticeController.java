package com.example.financialmanagementv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.financialmanagementv2.entity.Notice;
import com.example.financialmanagementv2.service.impl.NoticeServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Li
 * @since 2021-10-04
 */
@RestController
@Transactional
public class NoticeController {
    @Autowired
    NoticeServiceImpl noticeService;

    @GetMapping("/allNotices")
    @ResponseBody
    @ApiOperation("查看所有公告")
    public List<Notice> allNotices(){
        List<Notice> list = noticeService.list();
        return list;
    }

    @PostMapping ("/modifyNotice")
    @ResponseBody
    @ApiOperation("管理员修改公告")
    public String modifyNotice(Notice notice){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nid", notice.getNid());
        noticeService.update(notice,queryWrapper);
        return "success";
    }

    @PostMapping("/addNotice")
    @ResponseBody
    @ApiOperation("管理员添加公告")
    public String addNotice(Notice notice){
        noticeService.save(notice);
        return "success";
    }
    @PostMapping("/deleteNotice")
    @ResponseBody
    @ApiOperation("管理员删除公告")
    public String deleteNotice(int nid){
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("nid", nid);
        noticeService.remove(wrapper);
        return "success";
    }

}

