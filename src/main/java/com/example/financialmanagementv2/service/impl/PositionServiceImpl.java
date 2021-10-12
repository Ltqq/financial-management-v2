package com.example.financialmanagementv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.financialmanagementv2.entity.Position;
import com.example.financialmanagementv2.mapper.PositionMapper;
import com.example.financialmanagementv2.service.IPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
