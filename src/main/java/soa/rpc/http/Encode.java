package soa.rpc.http;

public enum Encode {
	UTF8("UTF8", (byte)1),
	GBK("GBK", (byte)2);
	
	
	private String name;
	
	private byte intValue;

	Encode(String name, byte intValue){
		this.name = name;
		this.intValue = intValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getIntValue() {
		return intValue;
	}

	public void setIntValue(byte intValue) {
		this.intValue = intValue;
	}
	
}
