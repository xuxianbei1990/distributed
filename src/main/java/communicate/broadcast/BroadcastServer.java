package communicate.broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * User: xuxb
 * Date: 2018-09-23
 * Time: 7:10
 * Version:V1.0
 */
public class BroadcastServer {

    public static void main(String[] args) {
        int port = 7777;
        DatagramSocket ds = null;
        DatagramPacket datagramPacket = null;
        byte[] buf = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ds = new DatagramSocket(port);
            datagramPacket = new DatagramPacket(buf, buf.length);
            System.out.println("监视广播端口打开：");
            ds.receive(datagramPacket);
            ds.close();
            for (int i = 0; i < 1024; i++) {
                if (buf[i] == 0) {
                    break;
                }
                stringBuilder.append((char)buf[i]);
            }
            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
