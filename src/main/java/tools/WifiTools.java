package tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class WifiTools {

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.getName().startsWith("wlan")) {
                    System.out.println("Interface Name: " + networkInterface.getName());
                    byte[] macAddress = networkInterface.getHardwareAddress();
                    if (macAddress != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < macAddress.length; i++) {
                            sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : ""));
                        }
                        System.out.println("MAC Address: " + sb.toString());
                    }
//                    System.out.println("Speed: " + networkInterface.getSpeed());
                    System.out.println("MTU: " + networkInterface.getName());
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (address.isLoopbackAddress() || address.isLinkLocalAddress()) {
                            continue; // 跳过非广播地址和回环地址
                        }
                        System.out.println("SSID: " + address.getHostName() + " " + address.getHostAddress());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
