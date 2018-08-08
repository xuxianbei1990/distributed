package soa.rpc.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cunsumer {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, UnknownHostException, IOException {
		String interfacename = SayHelloService.class.getName();
		
		Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
		
		Object[] arguments = {"hello"};
		
		Socket socket = new Socket("127.0.0.1", 1234);
		
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		
		output.writeUTF(interfacename);
		output.writeUTF(method.getName());
		output.writeObject(method.getParameterTypes());
		output.writeObject(arguments);
		
		
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		try {
			Object result = input.readObject();
			System.out.println(result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
