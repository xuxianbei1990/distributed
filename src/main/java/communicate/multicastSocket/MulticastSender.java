package communicate.multicastSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

public class MulticastSender {
	private int port;
	private String host;
	private String data;
	
	public MulticastSender(String host, int port, String data) {
		this.port = port;
		this.host = host;
		this.data = data;
	}
	
	public void send() {
		try {
			InetAddress ip = InetAddress.getByName(this.host);
			DatagramPacket packet = new DatagramPacket(this.data.getBytes(),
					this.data.length(), ip, this.port);
			MulticastSocket ms = new MulticastSocket();
			ms.send(packet);
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = 1234;
		String host = "224.1.1.1"; //地址段  224.0.0.0 - 239.255.255.255
		MulticastSender ml = new MulticastSender(host, port, "helloworld");
		ml.send();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
