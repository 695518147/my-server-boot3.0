package com.it.cacheredis;

import com.it.cacheredis.dao.OmsOrderDao;
import com.it.cacheredis.dao.OmsOrderDao1;
import com.it.cacheredis.entity.OmsOrder;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
class CacheRedisOrderTests {

    @Resource
    OmsOrderDao omsOrderDao;
    @Resource
    OmsOrderDao1 omsOrderDao1;

    @Test
    void query() {
        OmsOrder omsOrder = omsOrderDao.queryById(22l);
    }

    @Test
    void queryList() {
        PageRequest pageRequest = PageRequest.of(1, 20);
        OmsOrder omsOrder1 = new OmsOrder();
        List<OmsOrder> omsOrder = omsOrderDao.queryAllByLimit(omsOrder1, pageRequest);
    }

    @Test
    void update() {
        OmsOrder omsOrder = omsOrderDao1.queryById(22l);
        omsOrder.setOrderSn("这是一个测试3");
        omsOrderDao.update(omsOrder);
    }


}
