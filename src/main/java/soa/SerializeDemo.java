package soa;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianProtocolException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeDemo {

	private static Person zhanSan;

	/*
	 * jdk 1.7 hessian 4.0.7 i5
	 */
	// 使用java内部的序列化
	static void testJavaSerialize(List<Person> objects) throws IOException, ClassNotFoundException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(os);
		for (Person object : objects) {
            out.writeObject(object);
        }
		byte[] zhanSanByte = os.toByteArray();
		System.out.print("java   :" + zhanSanByte.length + "--");

		ByteArrayInputStream is = new ByteArrayInputStream(zhanSanByte);
		ObjectInputStream in = new ObjectInputStream(is);
		Person person = null;
		try {
			while ((person = (Person) in.readObject()) != null) {

			}
		} catch (EOFException e) {

		}

		is.close();
		os.close();
		if (person == null) {
			System.out.print(1);
		}
		// System.out.print(person.getName());
	}

	static void testHessian(List<Person> objects) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Hessian2Output ho = new Hessian2Output(os);
		for (Person object : objects) {
            ho.writeObject(object);
        }
		byte[] zhanSanByte = os.toByteArray();
		System.out.print("Hession:" + zhanSanByte.length + "--");

		ByteArrayInputStream is = new ByteArrayInputStream(zhanSanByte);
		Hessian2Input hi = new Hessian2Input(is);
		Person person = null;

		try {
			while ((person = (Person) hi.readObject()) != null) {

			}
		} catch (HessianProtocolException e) {

		}

		is.close();
		os.close();
		if (person == null) {
			System.out.print(1);
		}
		// System.out.print(person.getName());
	}

	static List<Person> fillPersons(int maxFillCount) {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < maxFillCount; i++) {
			Person person = new Person();
			person.setName("库拉" + i);
			person.setAge(i);
			person.setChild(i % 2 == 1);
			person.setTest1("asdf");
			person.setTest2(String.valueOf(i));
			person.setTest3(String.valueOf(i));
			person.setTest4(String.valueOf(i));
			person.setTest10(String.valueOf(i));
			person.setTest6(String.valueOf(i));
			person.setTest7(String.valueOf(i));
			person.setTest8(String.valueOf(i));
			person.setTest9(String.valueOf(i));
			persons.add(person);
		}
		return persons;
	}

	public static void main(String[] args) {
		List<Person> persons = fillPersons(3000);
		try {
			//
			long startime = System.nanoTime();
			testJavaSerialize(persons);
			long estimatedTime = System.nanoTime() - startime;
			System.out.println(estimatedTime);

			/*
			 * 10000010 165193055 4600199 898369759
			 */
			long startime1 = System.nanoTime();
			testHessian(persons);
			long estimatedTime1 = System.nanoTime() - startime1;
			System.out.println(estimatedTime1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Person getZhanSan() {
		return zhanSan;
	}

	public static void setZhanSan(Person zhanSan) {
		SerializeDemo.zhanSan = zhanSan;
	}

}

class Person implements Serializable {

	private static final long serialVersionUID = -1994868361071496902L;

	private String name;

	private int age;

	private boolean isChild;

	private String test1;

	private String test2;

	private String test3;

	private String test4;

	private String test6;

	private String test7;

	private String test8;

	private String test9;

	private String test10;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isChild() {
		return isChild;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

	public String getTest3() {
		return test3;
	}

	public void setTest3(String test3) {
		this.test3 = test3;
	}

	public String getTest4() {
		return test4;
	}

	public void setTest4(String test4) {
		this.test4 = test4;
	}

	public String getTest6() {
		return test6;
	}

	public void setTest6(String test6) {
		this.test6 = test6;
	}

	public String getTest7() {
		return test7;
	}

	public void setTest7(String test7) {
		this.test7 = test7;
	}

	public String getTest8() {
		return test8;
	}

	public void setTest8(String test8) {
		this.test8 = test8;
	}

	public String getTest9() {
		return test9;
	}

	public void setTest9(String test9) {
		this.test9 = test9;
	}

	public String getTest10() {
		return test10;
	}

	public void setTest10(String test10) {
		this.test10 = test10;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}