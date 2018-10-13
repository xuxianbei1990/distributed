package communicate.broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**  广播客户端
 * User: xuxb
 * Date: 2018-09-23
 * Time: 7:11
 * Version:V1.0
 */
public class BroadcastClient {

    public static void main(String[] args) {
        String host = "192.168.1.111";
        int port = 7777;
        String msg = "test";
        try {
            InetAddress adds = InetAddress.getByName(host);
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), adds, port);
            datagramSocket.send(dp);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
