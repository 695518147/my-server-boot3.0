package com.it.cacheehcache;

import com.it.cacheehcache.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheEhcacheApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Test
    void contextLoads() {
        System.out.println( employeeService.findAll());
        System.out.println( employeeService.findAll());
    }

}
