package com.ellen.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ellen.commons.result.Tree;
import com.ellen.model.Organization;

/**
 *
 * Organization 表数据服务层接口
 *
 */
public interface IOrganizationService extends IService<Organization> {

    List<Tree> selectTree();

    List<Organization> selectTreeGrid();

}