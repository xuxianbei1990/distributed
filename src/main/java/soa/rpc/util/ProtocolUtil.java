package soa.rpc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import soa.rpc.http.Encode;
import soa.rpc.http.Request;
import soa.rpc.http.Response;

/**
 * @author xuxb 协议
 */
public class ProtocolUtil {

	public static Request readRequest(InputStream input) throws IOException {
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];

		byte[] commandLengthBytes = new byte[4];
		input.read(commandLengthBytes);

		int commandLength = ByteUtil.bytes2Int(commandLengthBytes);

		byte[] commandBytes = new byte[commandLength];
		input.read(commandBytes);
		String command = "";
		if (Encode.GBK.getIntValue() == encode) {
			command = new String(commandBytes, Encode.GBK.getName());
		} else {
			command = new String(commandBytes, Encode.UTF8.getName());
		}

		// 组装请求返回
		Request request = new Request();
		request.setCommand(command);
		request.setCommandLength(commandLength);
		request.setEncode(encode);

		return request;
	}

	public static Response readResponse(InputStream input) throws IOException {
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];

		byte[] commandLengthBytes = new byte[4];
		input.read(commandLengthBytes);

		int commandLength = ByteUtil.bytes2Int(commandLengthBytes);

		byte[] commandBytes = new byte[commandLength];
		input.read(commandBytes);
		String command = "";
		if (Encode.GBK.getIntValue() == encode) {
			command = new String(commandBytes, Encode.GBK.getName());
		} else {
			command = new String(commandBytes, Encode.UTF8.getName());
		}

		// 组装请求返回
		Response response = new Response();
		response.setResponse(command);
		response.setResponseLength(commandLength);
		response.setEncode(encode);

		return response;
	}

	public static void writeRequest(OutputStream output, Request request) throws IOException {
		output.write(request.getEncode());
		// 直接write一个int类型，会截取低8位传输，丢弃高24位。
		// output.write(response.getResponseLength());
		output.write(ByteUtil.int2ByteArray(request.getCommandLength()));
		if (Encode.GBK.getIntValue() == request.getEncode()) {
			output.write(request.getCommand().getBytes("GBK"));
		} else {
			output.write(request.getCommand().getBytes("UTF8"));
		}
		output.flush();
	}

	public static void writeResponse(OutputStream output, Response response) throws IOException {
		output.write(response.getEncode());
		// 直接write一个int类型，会截取低8位传输，丢弃高24位。
		// output.write(response.getResponseLength());
		output.write(ByteUtil.int2ByteArray(response.getResponseLength()));
		if (Encode.GBK.getIntValue() == response.getEncode()) {
			output.write(response.getResponse().getBytes("GBK"));
		} else {
			output.write(response.getResponse().getBytes("UTF8"));
		}
		output.flush();
	}
}
