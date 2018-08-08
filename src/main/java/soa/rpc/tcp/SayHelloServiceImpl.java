package soa.rpc.tcp;

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
