package com.example.redissentinel;

public class TestMain {

    public static void main(String[] args) {
        System.out.println((Integer)(Integer.MAX_VALUE + 1));
    }
//    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executor = new ThreadPoolExecutor(1, 1000,
//                10, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
//                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
//        for (long i = 0; i < 10000000000l; i++) {
//            executor.execute(() -> {
//                System.out.println(Thread.currentThread().getName() + "");
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//            });
//        }
//    }
}
