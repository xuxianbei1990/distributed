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

import soa.zookeeper.register.IRegisterCenter;
import soa.zookeeper.register.RegisterCenterImpl;
import soa.zookeeper.register.anno.RpcAnnotation;

public class Provider {

	private IRegisterCenter registerCenter;
	private String serviceAddress;
	// 存储服务名称和服务对象关系
	private Map<String, Object> handlerMap = new HashMap<>();

	public Provider(IRegisterCenter iRegisterCenter, String serviceAddress) {
		super();
		this.registerCenter = iRegisterCenter;
		this.serviceAddress = serviceAddress;
	}

	// 绑定服务名称和服务对象
	public void bing(Object... services) {
		for (Object service: services) {
			RpcAnnotation rpcAnnotation = service.getClass().getAnnotation(RpcAnnotation.class);
			String serviceName = rpcAnnotation.value().getName();
			handlerMap.put(serviceName, service);
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		IRegisterCenter register = new RegisterCenterImpl();
		SayHelloService sayHelloService = new SayHelloServiceImpl();
		Provider provider = new Provider(register, "127.0.0.1:8080");
		provider.bing(sayHelloService);
		provider.publisher();
	}

	public void publisher() throws IOException, ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String[] addrs = serviceAddress.split(":");
		ServerSocket server = new ServerSocket(Integer.parseInt(addrs[1]));

		for (String interfaceName: handlerMap.keySet()){
			registerCenter.register(interfaceName, serviceAddress);
		}
		
		Map<String, SayHelloService> services = new HashMap<String, SayHelloService>();
		services.put(SayHelloService.class.getName(), new SayHelloServiceImpl());

		while (true) {
			Socket socket = server.accept();

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			String interfacename = input.readUTF();
			String methodName = input.readUTF();
			Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
			Object[] arguments = (Object[]) input.readObject();

			Class<?> serviceinterfaceclass = Class.forName(interfacename);

			Object service = services.get(interfacename);
			Method method = serviceinterfaceclass.getMethod(methodName, parameterTypes);

			Object result = method.invoke(service, arguments);

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}

}
