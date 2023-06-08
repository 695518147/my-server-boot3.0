package com.it.cacheredis.service;

import com.it.cacheredis.entity.OmsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 订单表(OmsOrder)表服务接口
 *
 * @author yinian
 * @since 2023-06-08 21:26:15
 */
public interface OmsOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OmsOrder queryById(Long id);

    /**
     * 分页查询
     *
     * @param omsOrder    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<OmsOrder> queryByPage(OmsOrder omsOrder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param omsOrder 实例对象
     * @return 实例对象
     */
    OmsOrder insert(OmsOrder omsOrder);

    /**
     * 修改数据
     *
     * @param omsOrder 实例对象
     * @return 实例对象
     */
    OmsOrder update(OmsOrder omsOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
