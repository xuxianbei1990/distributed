package soa.rpc.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Provider {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ServerSocket server = new ServerSocket(1234);
		
		Map<String, SayHelloService> services = new HashMap<String, SayHelloService>();
		services.put(SayHelloService.class.getName(), new SayHelloServiceImpl());
		
		while (true) {
			Socket socket = server.accept();
			
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			String interfacename = input.readUTF();
			String methodName = input.readUTF();
			Class<?>[] parameterTypes = (Class<?>[])input.readObject();
			Object[] arguments = (Object[])input.readObject();
			
			Class<?> serviceinterfaceclass = Class.forName(interfacename);

			Object service = services.get(interfacename);
			Method method = serviceinterfaceclass.getMethod(methodName, parameterTypes);
			
			Object result = method.invoke(service, arguments);
			
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}

}
