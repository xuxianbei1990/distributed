package soa.rpc.tcp;

import soa.zookeeper.register.anno.RpcAnnotation;

@RpcAnnotation(SayHelloService.class)
public class SayHelloServiceImpl implements SayHelloService {

	@Override
	public String sayHello(String helloAry) {
		if ("hello".equals(helloAry)) {
			return "hello";
		} else {
			return "bye bye";
		}
	}

}
