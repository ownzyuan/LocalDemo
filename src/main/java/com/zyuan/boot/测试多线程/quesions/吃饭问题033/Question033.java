package com.zyuan.boot.测试多线程.quesions.吃饭问题033;

/**
 * 有5个人围成圈，2人之间有一根筷子，场上总共有5根筷子，规定需要2根筷子才能吃饭，
 * 通过代码让所有人都能吃饭
 */
public class Question033 {

    public static void main(String[] args) {
        Chopsticks033 c01 = new Chopsticks033();
        Chopsticks033 c02 = new Chopsticks033();
        Chopsticks033 c03 = new Chopsticks033();
        Chopsticks033 c04 = new Chopsticks033();
        Chopsticks033 c05 = new Chopsticks033();

        Persons033 p01 = new Persons033("1号", 1, c01, c02);
        Persons033 p02 = new Persons033("2号", 2, c02, c03);
        Persons033 p03 = new Persons033("3号", 3, c03, c04);
        Persons033 p04 = new Persons033("4号", 4, c04, c05);
        Persons033 p05 = new Persons033("5号", 5, c05, c01);

        p01.start();
        p02.start();
        p03.start();
        p04.start();
        p05.start();
    }

    static class Persons033 extends Thread {
        String personName;
        Integer personIndex;
        Chopsticks033 left;
        Chopsticks033 right;

        public Persons033(String personName, Integer personIndex, Chopsticks033 left, Chopsticks033 right) {
            this.personName = personName;
            this.personIndex = personIndex;
            this.left = left;
            this.right = right;
        }

        // 该写法会导致死锁，因为所有人率先取得left的对象，同时等待别人释放取得的left，但是释放的条件是获取到right的对象，因此进入死锁
//        @Override
//        public void run() {
//            synchronized (left) {
//                try {
//                    Thread.sleep(500);
//                    synchronized (right) {
//                        Thread.sleep(500);
//                        System.out.println(personIndex + "号已吃完");
//                    }
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTrace();
//                }
//            }
//        }

        // 此写法的思路：因为所有人都是先取得left，然后等待right出现死锁，这时只需要出现一个特例，及1号先取right，后取left，
        // 那么这样的话，就可以依次释放，避免了死锁。可能的释放顺序：1 -> 5 -> 4 -> 3 -> 2
        // 但是这样写仍然存在问题，因为1号无论是否拿到2双筷子，都不会影响3或4号获取筷子，换言之1号可以与3号或者4号同时吃饭，
        // 而这种写法只能一个一个吃，效率相对较慢
//        @Override
//        public void run() {
//            if (personIndex == 1) {
//                synchronized (right) {
//                    try {
//                        Thread.sleep(500);
//                        synchronized (left) {
//                            Thread.sleep(500);
//                            System.out.println(personIndex + "号已吃完");
//                        }
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                    }
//                }
//            } else {
//                synchronized (left) {
//                    try {
//                        Thread.sleep(500);
//                        synchronized (right) {
//                            Thread.sleep(500);
//                            System.out.println(personIndex + "号已吃完");
//                        }
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                    }
//                }
//            }
//        }

        // 此写法的思路：隔一人就出现一个特例，及先取right，后取left，这里选取的特例就是偶数号
        // 可能出现的顺序：1 -> 2,5 -> 3 -> 4，   1，4 -> 2 -> 5 -> 3，  2，4 -> 1 -> 3 -> 5
        @Override
        public void run() {
            if (personIndex%2 == 0) {
                synchronized (right) {
                    try {
                        Thread.sleep(500);
                        synchronized (left) {
                            Thread.sleep(500);
                            System.out.println(personIndex + "号已吃完");
                        }
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            } else {
                synchronized (left) {
                    try {
                        Thread.sleep(500);
                        synchronized (right) {
                            Thread.sleep(500);
                            System.out.println(personIndex + "号已吃完");
                        }
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        }
    }

}
