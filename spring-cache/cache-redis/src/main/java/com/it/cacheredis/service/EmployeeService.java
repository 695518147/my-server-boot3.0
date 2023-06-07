package com.it.cacheredis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @CacheConfig 注解是声明本类所有方法的缓存名称，如果方法上设置了自己的名称则以方法上名称为准，
 * 比如类上设置了名称employee_info,方法上设置了employee_all，
 * 则最终的名称为employee_all，具体看本类的实例
 */
@CacheConfig(cacheNames = {"employee_info"})
@Slf4j
@Service
public class EmployeeService {


    /**
     * 查询所有员工信息
     * <p>
     * 这里指定了缓存的名称：employee_all，则最终的缓存名称为employee_all
     * 我这里顺便也实现了自定义的key生成方法keyGenerator
     * <p>
     * cacheNames/value:二选一使用
     * key/keyGenerator:二选一使用
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public String findAll() {
        log.info("findAll查询数据库");
        return "findAll1";
    }

    /**
     * 根据id查询员工信息（id长度为8才缓存）
     * <p>
     * key/keyGenerator:二选一使用
     * condition表示的是条件（为true才缓存）
     */
    @Cacheable(value = "employee_info1",key = "#id", condition = "#id.length()==8")
    public String findById(String id) {
        log.info("findById查询数据库");
        return "findById";
    }

    /**
     * 更加id更新员工工资（员工工资大于0才缓存）
     *
     * @CachePut 缓存的是返回值，所以更新方法的返回值一定要注意
     * <p>
     * key/keyGenerator:二选一使用
     * condition表示的是条件（为true才缓存）
     */
    @CachePut(value = "redisCacheDemo:employee_info1", key = "#id", condition = "#salary>0")
    public String updateSalaryById(String id, double salary) {
        log.info("updateSalaryById查询数据库");
        return "updateSalaryById";
    }

    /**
     * 更加id删除员工
     *
     * @CacheEvict Spring会在调用该方法之前清除缓存中的指定元素
     * allEntries : 为true表示清除value空间名里的所有的数据，默认为false
     * beforeInvocation 缓存的清除是在方法前执行还是方法后执行，默认是为false,方法执行后删除
     * beforeInvocation = false : 方法执行后删除，如果出现异常缓存就不会清除
     * beforeInvocation = true : 方法执行前删除，无论方法是否出现异常，缓存都清除
     */
    @CacheEvict(key = "#id", beforeInvocation = true)
    public String deleteById(String id) {
        log.info("deleteById查询数据库");
        return "deleteById";
    }
}

