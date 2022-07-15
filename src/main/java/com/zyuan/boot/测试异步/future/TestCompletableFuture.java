package com.zyuan.boot.测试异步.future;

import java.util.concurrent.CompletableFuture;

public class TestCompletableFuture {

    public static void main(String[] args) {
//        testSupplyAsync();
        try {
            Thread.sleep(5000);
            CompletableFuture<String> future = new CompletableFuture<>();
            String join = future.join();
            System.out.println(join);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testSupplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "sleepAndPrint";
            } catch (Exception e) {
                return "failingPrint";
            }
        });
        future.complete("manualPrint");
        System.out.println(future.join());
    }

}
