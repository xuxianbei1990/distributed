package soa.zookeeper.register;

public interface IRegisterCenter {

	boolean register(String serviceName, String serviceAddress);
}
