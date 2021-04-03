package serializable.one;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.jar.Attributes.Name;

class Person implements Serializable {
	String name;
	long id;
	// 密码保密，不会被序列化
	transient String password;

	static long nextID = 0;

	static String skill = "变身";

	public Person(String name) {
		this.name = name;
		synchronized (Name.class) {
			id = nextID++;
		}
	}

}

class Aier implements Externalizable {
	String name;
	int age;

	private static class InstanceHolder {
		private static final Aier instatnce = new Aier();
	}

	public static Aier getInstance() {
		return InstanceHolder.instatnce;
	}

	public Aier() {
		System.out.println("none-arg constructor Aier");
	}

	@Override
	public String toString() {
		return name + age;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

	}

	// 返回单一实例
	/*
	 * 无论是实现Serializable接口，或是Externalizable接口，当从I/O流中读取对象时，
	 * readResolve()方法都会被调用到。实际上就是用readResolve()中 返回的对象直接替换在反序列化过程中创建的对象。
	 */
	private Object readResolve() throws ObjectStreamException {
		return InstanceHolder.instatnce;
	}

}

class Rose implements Serializable {
	String name = "玫瑰";
	transient String address;

	@Override
	public String toString() {
		return name + address;

	}

	// 利用反射调用了这两个方法 ObjectOutputStream中的writeSerialData
	// 这两个方法默认是有序列化时候调用
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(address);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		address = (String) in.readObject();
	}
}

public class ObjectDemo {

	// 分布式----190
	void commonlySerializable() throws IOException, Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ObjectOutputStream objectOut = new ObjectOutputStream(output);
		// 序列化
		Person person = new Person("123");
		objectOut.writeObject(person);
		objectOut.close();
		output.close();
		byte[] bytes = output.toByteArray();
		

		// 反序列化
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream oin = new ObjectInputStream(in);
		Person person2 = (Person) oin.readObject();
		oin.close();
		in.close();
	}

	void testObject() throws IOException, ClassNotFoundException {
		String file = "src//main//resources//filechapter//ObjectDemo.txt";
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		HashMap<String, Rose> hash = new HashMap<String, Rose>();

		Rose ro = new Rose();
		ro.address = "天界";
		hash.put("sky", ro);
		// 写入是同一个引用
		out.writeObject(hash);
		out.writeUnshared(hash);
		// 写入时候不共享引用 不知道有啥特别用处
		out.writeUnshared(hash);

		FileInputStream filein = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(filein);
		HashMap<String, Rose> hash3 = (HashMap<String, Rose>) in.readObject();
		HashMap<String, Rose> hash4 = (HashMap<String, Rose>) in.readObject();
		HashMap<String, Rose> hash2 = (HashMap<String, Rose>) in.readUnshared();

		System.out.println(hash);
		System.out.println(hash2);
		System.out.println(ro.toString());
	}

	void testExternalizable() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("output.txt");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		Aier a = new Aier();
		a.name = "sky";
		a.age = 18;
		out.writeObject(a);

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		a = (Aier) in.readObject();
		System.out.println(a);

	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		ObjectDemo od = new ObjectDemo();
		od.testObject();
		od.testExternalizable();
	}

}
