package com.ellen.service;

import com.baomidou.mybatisplus.service.IService;
import com.ellen.commons.result.PageInfo;
import com.ellen.model.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}