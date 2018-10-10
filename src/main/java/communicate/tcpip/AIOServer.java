package communicate.tcpip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * User: xuxb
 * Date: 2018-09-28
 * Time: 21:09
 * Version:V1.0
 */
public class AIOServer {
    private int port = 8080;

    public AIOServer(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    result.read(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array()));
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOServer(8080).listen();
    }
}
