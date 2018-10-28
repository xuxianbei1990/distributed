package soa.rpc.http;

import soa.rpc.util.ProtocolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Provider {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(4567);
		try {
			while (true) {
				Socket client = server.accept();
				InputStream input = client.getInputStream();
				Request request = ProtocolUtil.readRequest(input);

				OutputStream output = client.getOutputStream();

				Response response = new Response();
				response.setEncode(Encode.UTF8.getIntValue());
				if ("HELLO".equals(request.getCommand())) {
					response.setResponse("hello!");
				} else {
					response.setResponse("bye bye!");
				}
				response.setResponseLength(response.getResponse().length());

				ProtocolUtil.writeResponse(output, response);
			}
		} finally {
			server.close();
		}

	}

}
