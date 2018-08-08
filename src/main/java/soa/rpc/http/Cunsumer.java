package soa.rpc.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import soa.rpc.util.ProtocolUtil;

public class Cunsumer {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Request request = new Request();
		request.setCommand("HELLO");
		request.setCommandLength(request.getCommand().length());
		request.setEncode(Encode.UTF8.getIntValue());

		Socket client = new Socket("127.0.0.1", 4567);
		OutputStream output = client.getOutputStream();

		ProtocolUtil.writeRequest(output, request);

		InputStream input = client.getInputStream();
		Response response = ProtocolUtil.readResponse(input);
		System.out.println(response.getResponse());
		client.close();
	}

}
