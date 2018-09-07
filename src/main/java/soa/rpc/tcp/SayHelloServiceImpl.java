package soa.rpc.tcp;

import soa.zookeeper.register.anno.RpcAnnotation;

@RpcAnnotation(SayHelloService.class)
public class SayHelloServiceImpl implements SayHelloService {

	@Override
	public String sayHello(String helloAry) {
		if (helloAry.equals("hello")) {
			return "hello";
		} else {
			return "bye bye";
		}
	}

}
