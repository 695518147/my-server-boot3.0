package com.it.cacheredis.service.impl;

import com.it.cacheredis.dao.OmsOrderDao;
import com.it.cacheredis.entity.OmsOrder;
import com.it.cacheredis.service.OmsOrderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 订单表(OmsOrder)表服务实现类
 *
 * @author yinian
 * @since 2023-06-08 21:26:15
 */
@Service("omsOrderService")
public class OmsOrderServiceImpl implements OmsOrderService {
    @Autowired
    private OmsOrderDao omsOrderDao;

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @Cacheable(value ="employee_info", keyGenerator = "keyGenerator")
    public OmsOrder queryById(Long id) {
        return this.omsOrderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param omsOrder    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<OmsOrder> queryByPage(OmsOrder omsOrder, PageRequest pageRequest) {
        long total = this.omsOrderDao.count(omsOrder);
        return new PageImpl<>(this.omsOrderDao.queryAllByLimit(omsOrder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param omsOrder 实例对象
     * @return 实例对象
     */
    @Override
    public OmsOrder insert(OmsOrder omsOrder) {
        this.omsOrderDao.insert(omsOrder);
        return omsOrder;
    }

    /**
     * 修改数据
     *
     * @param omsOrder 实例对象
     * @return 实例对象
     */
    @Override
    public OmsOrder update(OmsOrder omsOrder) {
        this.omsOrderDao.update(omsOrder);
        return this.queryById(omsOrder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.omsOrderDao.deleteById(id) > 0;
    }
}
