package com.bufigol.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OtrosMetodos {
    public static String getMyIPAddress() {
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            return ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Unable to get IP address: " + e.getMessage());
            return "1.1.1.1";
        }
    }
}
