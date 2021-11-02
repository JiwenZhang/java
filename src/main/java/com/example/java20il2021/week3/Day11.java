package com.example.java20il2021.week3;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *   java 8 new features
 *      1. functional interface
 *      2. lambda expression + method reference
 *      3. stream api
 *      4. default method
 *      5. optional
 *      6. CompletableFuture
 *      7. perm gen -> meta space
 *
 *      singleton
 *      factory
 *      builder
 *      ..
 *      template
 *      strategy
 *      prototype
 *
 *      proxy + dynamic proxy
 *      reflection
 */

class Java8FeatureTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(5);
        list.add(5);
//        Optional<Integer> res = list.stream().filter(x -> x < 5).findAny();
//        System.out.println(res.get());
        List<List<Integer>> res = list.stream().collect(
                () -> Arrays.asList(new ArrayList<>(), new ArrayList<>()),
                (col, val) -> col.get(val % 2).add(val),
                (col1, col2) -> col1.addAll(col2)
        );
        //res.get(0) -> even list
        //res.get(1) -> odd list
        System.out.println(res);

//        Map<Integer, Long> freq = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> freq = list.stream().collect(
                HashMap::new,
                (map, val) -> map.put(val, map.getOrDefault(val, 0l) + 1),
                HashMap::putAll
        );
        System.out.println(freq);
        //[[1, 1], [2, 1], [3, 3], [5, 2], [4, 1]]
    }
}


/**
 *  Callable vs Runnable
 */

class FutureExample {
    private static void execute() {

    }

    public static void main(String[] args) {
        execute();
//        Future<Integer> f = pool.submit(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 5;
//        });
//
//        System.out.println(f.isDone());
//        System.out.println("main");
        /**
         * 1. 3s -> print v -> print main
         * 2. print main -> 3s -> print v
         * 3. print main
         */
//        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> 5)
//                .thenApply(x -> x * 2)
//                .thenAcceptAsync(v -> {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (Exception ex) {
//
//                    }
//                    System.out.println(v);
//                });
//        cf.join();
//        System.out.println("main");
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> Thread.currentThread().getName());
//            System.out.println(cf.join());
            futures.add(cf);
        }
        String res = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApplyAsync(v -> {
                    StringBuilder sb = new StringBuilder();
                    futures.forEach(f -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sb.append(f.join());
                    });
                    return sb.toString();
                }).completeOnTimeout("aaa", 100, TimeUnit.MILLISECONDS).join();
        System.out.println(res);
        System.out.println("main");
    }
}

class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("this is run");
    }

    public static void main(String[] args) {
        new MyThread().start();
    }
}