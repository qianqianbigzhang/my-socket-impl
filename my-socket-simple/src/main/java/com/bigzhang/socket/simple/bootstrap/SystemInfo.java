package com.bigzhang.socket.simple.bootstrap;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SystemInfo {
    public static String getFirstServiceIp() {
        Enumeration<NetworkInterface> networkInterfaces;

        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            return "";
        }

        NetworkInterface networkInterface;
        Enumeration<InetAddress> inetAddresses;
        InetAddress inetAddress;

        while (networkInterfaces.hasMoreElements()) {
            networkInterface = networkInterfaces.nextElement();
            inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                inetAddress = inetAddresses.nextElement();
                if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                    String ethIp = inetAddress.getHostAddress();
                    //只获取第一块服务网卡IP
                    if (!ethIp.equals("127.0.0.1")) {
                        return ethIp;
                    } else {
                        continue;
                    }
                }
            }
        }

        return "";
    }

    public static String getJVMRunningId() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }
        return pid;
    }
}
