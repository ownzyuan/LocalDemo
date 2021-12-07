package com.zyuan.boot.Java封装类;

public class TestString {
    public static void main(String[] args) {
        String str = new String();
        str = "1234";
        int hashCode = str.hashCode();
        System.out.println(hashCode);
        boolean b = str.equals("1236");
        System.out.println(b);
        String str2 = new String("sad");
        System.out.println(str2);
    }
}
