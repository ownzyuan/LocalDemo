package com.zyuan.boot.ip;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;

public class AddressTest {

    public static void main(String[] args) {
        AddressUtils addressUtils = new AddressUtils();
        try {
            String innetIp = addressUtils.getInnetIp();
            System.out.println(innetIp);
            String addresses = addressUtils.getAddresses(innetIp, "UTF-8");
            System.out.println(addresses);
        } catch (UnsupportedEncodingException | SocketException e) {
            e.printStackTrace();
        }

    }

}
