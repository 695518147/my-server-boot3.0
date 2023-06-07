package com.example.rediscluster;

import com.example.rediscluster.entity.Message;
import com.example.rediscluster.redis_queue.OrderCancelDelayQueue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class RedisClusterApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(RedisClusterApplication.class, args);
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Resource
    private OrderCancelDelayQueue orderCancelDelayQueue;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        executor.execute(() -> {
            log.info("{} ===============================", Thread.currentThread().getName());
            while(true){
                List<Message> messageList = orderCancelDelayQueue.pull();
                if(messageList.size()==0){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (Message message : messageList) {
                    log.info("{} ===============================", Thread.currentThread().getName());
                    log.warn("consumer message:{},date:{}",message, System.currentTimeMillis()/1000);
                    this.orderCancelDelayQueue.remove(message);
                }
            }
        });

    }

}
