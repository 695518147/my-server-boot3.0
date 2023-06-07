package com.it.cacheredis;

import com.it.cacheredis.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheRedisApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Test
    void contextLoads() {
        System.out.println( employeeService.findAll());
        System.out.println( employeeService.findById("testtest"));
        System.out.println( employeeService.deleteById("testId"));
    }


}
