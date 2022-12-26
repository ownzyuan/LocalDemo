package com.zyuan.boot.Java封装类.Number;

public class TestDouble {

    public static void main(String[] args) {
        Double double01 = 100.00;
        Double double02 = 100.00;
        Double double03 = 200.00;
        Double double04 = 200.00;
        System.out.println(double01==double02); // false
        System.out.println(double03==double04); // false
        System.out.println(double01.doubleValue());
    }

}
