package com.zyuan.boot.测试异步.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class TestFuture {

    /**
     * CompletableFuture的runAsync(Runnable runnable,Executor executor)方法效果与executorService的submit(Runnable task)相同
     * CompletableFuture的supplyAsync(Supplier<U> supplier,Executor executor)方法效果与executorService的submit(Callable<T> task)相同
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        testExecutorService();
//        testCompletableFuture();
//        testFutureList();
//        testThenApplyAndThenAcceptAndThenRun();
//        testThenApplyAsync();
//        testExceptionally();
//        testWhenComplete();
//        testHandle();
//        testThenCombineAndThenAcceptBothAndRunAfterBoth();
//        testApplyToEitherAndAcceptEitherAndRunAfterEither();
        testThenCompose();
    }

    private static void testExecutorService() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Double> future = executorService.submit(() -> {
            System.out.println("async start ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("async failing ->" + Thread.currentThread());
            }
//            System.out.println(1/0);
            System.out.println("async end ->" + Thread.currentThread());
            return 1.2;
        });
        System.out.println("main start");
        System.out.println(future.get());
        System.out.println("main end");
    }

    private static void testCompletableFuture() throws InterruptedException, ExecutionException {
        // 如果没有指定默认的executor，则默认为ForkJoinPool
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        CompletableFuture<Void> future01 = CompletableFuture.runAsync(() -> {
            System.out.println("无返回值异步任务开始执行 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("无返回值异步任务执行失败 ->" + Thread.currentThread());
            }
//            System.out.println(1/0);
            System.out.println("无返回值异步任务执行成功 ->" + Thread.currentThread());
        }, forkJoinPool);

        // 可以指定executor
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Double> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("带返回值异步任务开始执行 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("带返回值异步任务执行失败 ->" + Thread.currentThread());
            }
//            System.out.println(1/0);
            System.out.println("带返回值异步任务执行成功 ->" + Thread.currentThread());
            return 2.4;
        }, executorService);

        System.out.println("main start");
        System.out.println(future01.get());
        System.out.println(future02.get());
        System.out.println("main end");
    }

    private static void testFutureList() throws InterruptedException, ExecutionException {
        List<Future<String>> futureList = new ArrayList<>();
        asyncExecuteTask(futureList, () -> {
            System.out.println("任务A开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("任务A失败 ->" + Thread.currentThread());
            }
            return "任务A";
        });
        asyncExecuteTask(futureList, () -> {
            System.out.println("任务B开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("任务B失败 ->" + Thread.currentThread());
            }
            return "任务B";
        });
        asyncExecuteTask(futureList, () -> {
            System.out.println("任务C开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("任务C失败 ->" + Thread.currentThread());
            }
            return "任务C";
        });
        for (Future<String> future : futureList) {
            System.out.println(future.get());
        }
    }

    private static void asyncExecuteTask(List<Future<String>> futureList, Supplier<String> supplier) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            String task = supplier.get();
            return task + "完成";
        });
        futureList.add(future);
    }

    /**
     * thenApply：某个任务执行完成之后的执行动作，即回调方法，将该任务的返回值作为参数传入回调方法中，可指定返回值
     * thenAccept：某个任务执行完成之后的执行动作，即回调方法，将该任务的返回值作为参数传入回调方法中，但无返回值
     * thenRun：某个任务执行完成之后的执行动作，即回调方法，但无入参，无返回值
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testThenApplyAndThenAcceptAndThenRun() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return "return 步骤一";
        });
        CompletableFuture<String> future02 = future01.thenApply((result) -> {
            System.out.println("步骤一完成，然后执行步骤二 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
                System.out.println("步骤二接收到的传参：" + result);
            } catch (Exception e) {
                System.out.println("步骤二执行失败 ->" + Thread.currentThread());
            }
            System.out.println("然后执行完成 ->" + Thread.currentThread());
            return "return 步骤二执行" + "_" + result;
        });
        CompletableFuture<Void> future03 = future02.thenAccept((result) -> {
            System.out.println("步骤二完成，然后执行步骤三 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
                System.out.println("步骤三接收到的传参：" + result);
            } catch (Exception e) {
                System.out.println("步骤三执行失败 ->" + Thread.currentThread());
            }
            System.out.println("步骤三执行完成 ->" + Thread.currentThread());
        });
        CompletableFuture<Void> future04 = future03.thenRun(() -> {
            System.out.println("步骤三完成，然后执行步骤四 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤四执行失败 ->" + Thread.currentThread());
            }
            System.out.println("步骤四执行完成 ->" + Thread.currentThread());
        });
        System.out.println("main start");
        System.out.println(future01.get());
        System.out.println(future02.get());
        System.out.println(future03.get());
        System.out.println(future04.get());
        System.out.println("main end");
    }

    /**
     * thenApplyAsync：某个任务执行完成之后的 异步 执行动作，将该任务的返回值作为参数传入回调方法中异步执行
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testThenApplyAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("任务一失败 ->" + Thread.currentThread());
            }
            System.out.println("任务一完成 ->" + Thread.currentThread());
            return "任务一";
        });
        CompletableFuture<String> future02 = future01.thenApplyAsync((result) -> {
            System.out.println("任务一完成，然后异步执行 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("然后异步执行失败 ->" + Thread.currentThread());
            }
            System.out.println("然后异步执行完成 ->" + Thread.currentThread());
            return "然后异步执行" + "_" + result;
        });
        System.out.println("main start");
        System.out.println(future01.get());
        System.out.println(future02.get());
        System.out.println("main end");
    }

    /**
     * exceptionally：某个任务执行异常后，调用的回调方法，将抛出的异常作为参数传递进入回调方法中
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testExceptionally() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return "return 步骤一123";
        }, executorService);
        // 步骤一会抛出异常，这里就获取并处理
        CompletableFuture<String> futureExceptionally = future.exceptionally((exception) -> {
            System.out.println("进入exceptionally方法");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("exceptionally方法失败 ->" + Thread.currentThread());
            }
            System.out.println("=======打印异常信息=======");
            exception.printStackTrace();
            System.out.println("exceptionally方法完成 ->" + Thread.currentThread());
            return "return exceptionally";
        });
        CompletableFuture<Void> futureAccept = future.thenAccept((param) -> {
            System.out.println("步骤一不抛异常，就执行步骤二 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤二失败 ->" + Thread.currentThread());
            }
            System.out.println("步骤二完成 ->" + Thread.currentThread());
        });
        System.out.println("main start");
        System.out.println(future.get());
        System.out.println(futureExceptionally.get());
        System.out.println(futureAccept.get());
        System.out.println("main end");
    }

    /**
     * whenComplete：某个任务执行之后，调用的回调方法，会接收方法的执行结果和抛出的异常，如果无异常则异常为null，该方法无返回值
     * 注：whenComplete有异步执行，whenComplete -> whenCompleteAsync
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testWhenComplete() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return "return 步骤一123";
        }, executorService);
        CompletableFuture<String> futureWhenComplete = future.whenComplete((param, exception) -> {
            System.out.println("进入whenComplete方法");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("whenComplete方法失败 ->" + Thread.currentThread());
            }
            System.out.println("=======打印参数=======");
            System.out.println("步骤一传来的参数：" + param);
            if (exception != null) {
                System.out.println("=======如果执行异常，打印异常信息=======");
                exception.printStackTrace();
            }
            System.out.println("whenComplete方法完成 ->" + Thread.currentThread());
        });
        System.out.println("main start");
        System.out.println(future.get());
        System.out.println(futureWhenComplete.get());
        System.out.println("main end");
    }

    /**
     * handle：某个任务执行之后，调用的回调方法，会接收方法的执行结果和抛出的异常，如果无异常则异常为null，该方法可以指定返回值
     * 注：handle有异步执行，handle -> handleAsync
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testHandle() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return "return 步骤一123";
        }, executorService);
        CompletableFuture<Double> futureHandle = future.handle((param, exception) -> {
            System.out.println("进入handle方法");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("handle方法失败 ->" + Thread.currentThread());
            }
            System.out.println("=======打印参数=======");
            System.out.println("步骤一传来的参数：" + param);
            if (exception != null) {
                System.out.println("=======如果执行异常，打印异常信息=======");
                exception.printStackTrace();
            }
            System.out.println("handle方法完成 ->" + Thread.currentThread());
            return 1.5;
        });
        System.out.println("main start");
        System.out.println(future.get());
        System.out.println(futureHandle.get());
        System.out.println("main end");
    }

    /**
     * thenCombine：将2个CompletableFuture组合起来，只有两个任务都成功执行后才会执行该方法（如果其中一个出现异常则不执行），参数为两个任务的执行结果，且thenCombine可以指定返回值
     * thenAcceptBoth：将2个CompletableFuture组合起来，只有两个任务都成功执行后才会执行该方法（如果其中一个出现异常则不执行），参数为两个任务的执行结果，但thenAcceptBoth无返回值
     * runAfterBoth：将2个CompletableFuture组合起来，只有两个任务都成功执行后才会执行该方法（如果其中一个出现异常则不执行），但无参数，无返回值
     * 注：3个方法都有各自的异步执行，thenCombine -> thenCombineAsync， thenAcceptBoth -> thenAcceptBothAsync， runAfterBoth -> runAfterBothAsync
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testThenCombineAndThenAcceptBothAndRunAfterBoth() throws InterruptedException, ExecutionException {
        CompletableFuture<Double> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return 1.1;
        });
        CompletableFuture<Double> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤二开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤二失败 ->" + Thread.currentThread());
            }
            System.out.println("步骤二完成 ->" + Thread.currentThread());
            return 2.9;
        });
        CompletableFuture<Double> futureThenCombine = future01.thenCombine(future02, (param01, param02) -> {
            System.out.println("步骤一、步骤二都完成后，thenCombine开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("thenCombine失败 ->" + Thread.currentThread());
            }
            System.out.println("thenCombine完成 ->" + Thread.currentThread());
            return param01 + param02;
        });
        CompletableFuture<Void> futureThenAcceptBoth = future01.thenAcceptBoth(future02, (param01, param02) -> {
            System.out.println("步骤一、步骤二都完成后，thenAcceptBoth开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("thenAcceptBoth失败 ->" + Thread.currentThread());
            }
            System.out.println("thenAcceptBoth方法无返回值，在此处打印参数和：" + (param01 + param02));
            System.out.println("thenAcceptBoth完成 ->" + Thread.currentThread());
        });
        CompletableFuture<Void> futureRunAfterBoth = future01.runAfterBoth(future02, () -> {
            System.out.println("步骤一、步骤二都完成后，runAfterBoth开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("runAfterBoth失败 ->" + Thread.currentThread());
            }
            System.out.println("runAfterBoth方法无返回值，也没有参数");
            System.out.println("runAfterBoth完成 ->" + Thread.currentThread());
        });
        System.out.println("main start");
        System.out.println("future01：" + future01.get());
        System.out.println("future02：" + future02.get());
        System.out.println("futureThenCombine：" + futureThenCombine.get());
        System.out.println("futureThenAcceptBoth：" + futureThenAcceptBoth.get());
        System.out.println("futureRunAfterBoth：" + futureRunAfterBoth.get());
        System.out.println("main end");
    }

    /**
     * applyToEither：将2个CompletableFuture组合起来，其中一个任务完成执行后就会执行该方法（先抛出异常则不做处理），参数为完成任务的执行结果，且applyToEither可以指定返回值
     * acceptEither：将2个CompletableFuture组合起来，其中一个任务完成执行后就会执行该方法（先抛出异常则不做处理），参数为完成任务的执行结果，但acceptEither无返回值
     * runAfterEither：将2个CompletableFuture组合起来，其中一个任务完成执行后就会执行该方法（先抛出异常则不做处理），但无参数，无返回值
     * 注：3个方法都有各自的异步执行，applyToEither -> applyToEitherAsync， acceptEither -> acceptEitherAsync， runAfterEither -> runAfterEitherAsync
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testApplyToEitherAndAcceptEitherAndRunAfterEither() throws InterruptedException, ExecutionException {
        CompletableFuture<Double> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return 1.1;
        });
        CompletableFuture<Double> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤二开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("步骤二失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤二完成 ->" + Thread.currentThread());
            return 2.9;
        });
        CompletableFuture<Double> futureApplyToEither = future01.applyToEither(future02, (param) -> {
            System.out.println("步骤一、步骤二任一完成后，applyToEither开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("applyToEither失败 ->" + Thread.currentThread());
            }
            System.out.println("applyToEither获取的参数为" + param);
            System.out.println("applyToEither完成 ->" + Thread.currentThread());
            return 1.0;
        });
        CompletableFuture<Void> futureAcceptEither = future01.acceptEither(future02, (param) -> {
            System.out.println("步骤一、步骤二任一完成后，acceptEither开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("acceptEither失败 ->" + Thread.currentThread());
            }
            System.out.println("acceptEither方法无返回值，在此处打印参数：" + param);
            System.out.println("acceptEither完成 ->" + Thread.currentThread());
        });
        CompletableFuture<Void> futureRunAfterEither = future01.runAfterEither(future02, () -> {
            System.out.println("步骤一、步骤二任一完成后，runAfterEither开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("runAfterEither失败 ->" + Thread.currentThread());
            }
            System.out.println("runAfterEither方法无返回值，也没有参数");
            System.out.println("runAfterEither完成 ->" + Thread.currentThread());
        });
        System.out.println("main start");
        System.out.println("future01：" + future01.get());
        System.out.println("future02：" + future02.get());
        System.out.println("futureApplyToEither：" + futureApplyToEither.get());
        System.out.println("futureAcceptEither：" + futureAcceptEither.get());
        System.out.println("futureRunAfterEither：" + futureRunAfterEither.get());
        System.out.println("main end");
    }

    /**
     * thenCompose：任务完成后，就会执行该方法，参数为完成任务的执行结果，然后thenCompose会返回一个新的CompletableFuture实例，该实例执行结果会返回给thenCompose
     * 注：thenCompose方法有异步执行，thenCompose -> thenComposeAsync
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testThenCompose() throws InterruptedException, ExecutionException {
        CompletableFuture<Double> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("步骤一开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("步骤一失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("步骤一完成 ->" + Thread.currentThread());
            return 1.1;
        });
        CompletableFuture<Double> future02 = future01.thenCompose((param) -> {
            System.out.println("步骤一完成后，thenCompose开始 ->" + Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("thenCompose失败 ->" + Thread.currentThread());
            }
            // 抛异常
//            int a = 1/0;
            System.out.println("thenCompose完成 ->" + Thread.currentThread());
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("thenCompose返回的supplyAsync开始 ->" + Thread.currentThread());
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    System.out.println("thenCompose返回的supplyAsync失败 ->" + Thread.currentThread());
                }
                // 抛异常
//                int a = 1/0;
                System.out.println("thenCompose返回的supplyAsync完成 ->" + Thread.currentThread());
                return null;
            });
        });
        System.out.println("main start");
        System.out.println("future01：" + future01.get());
        System.out.println("future02：" + future02.get());
        System.out.println("main end");
    }

}
