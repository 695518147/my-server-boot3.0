package com.it.cacheredis.dao;

import com.it.cacheredis.entity.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * 订单表(OmsOrder)表数据库访问层
 *
 * @author yinian
 * @since 2023-06-08 06:14:01
 */
public interface OmsOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OmsOrder queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param omsOrder 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<OmsOrder> queryAllByLimit(@Param("omsOrder")OmsOrder omsOrder, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param omsOrder 查询条件
     * @return 总行数
     */
    long count(OmsOrder omsOrder);

    /**
     * 新增数据
     *
     * @param omsOrder 实例对象
     * @return 影响行数
     */
    int insert(OmsOrder omsOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OmsOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OmsOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OmsOrder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OmsOrder> entities);

    /**
     * 修改数据
     *
     * @param omsOrder 实例对象
     * @return 影响行数
     */
    int update(OmsOrder omsOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

