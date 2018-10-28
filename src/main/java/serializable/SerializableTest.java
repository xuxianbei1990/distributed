package serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: xuxb
 * Date: 2018-09-26
 * Time: 10:47
 * Version:V1.0
 */
public class SerializableTest {

    public static void main(String[] args) throws IOException {
        serizlizerList();

//        deepCloneTest();
        int testCount = 100;
        // 数量小时候 性能高于fastjson
        executejkson(testCount);
        // 数量大的时候性能高效
        executeFastjson(testCount);
        // 创建时候比较费时间。序列化时候性能也很高。 序列化之后 压缩了数据。更适合网络传输。
        executeProtobuf(testCount);
        // 性能更高，但是消耗的空间更大。
        executeHessian(testCount);

    }

    private static void serizlizerList() {
        List<Student> list = new ArrayList<>();
        List<Student> list2;
        for (int i = 0; i < 5; i++) {
            list.add(getStudent());
        }
        String str = JSONObject.toJSONString(list);
        list2 = JSONObject.parseArray(str, Student.class);
        System.out.println(list2.toString());
    }

    private static void executeFastjson(int testCount) throws IOException {
        Student student = getStudent();
        String test = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            test = JSON.toJSONString(student);
        }
        System.out.println("Fastjson 序列化:" + (System.currentTimeMillis() - start) + "ms; 总大小："
                + test.getBytes().length);

        Student student1 = JSON.parseObject(test, Student.class);
        System.out.println(student1);
    }

    private static void executejkson(int testCount) throws IOException {
        Student student = getStudent();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] writeBytes = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            writeBytes = objectMapper.writeValueAsBytes(student);
        }
        System.out.println("jkson 序列化:" + (System.currentTimeMillis() - start) + "ms; 总大小："
                + writeBytes.length);

        Student student1 = objectMapper.readValue(writeBytes, Student.class);
        System.out.println(student1);
    }

    private static void executeProtobuf(int testCount) throws IOException {
        Student student = getStudent();
        Codec<Student> codec = ProtobufProxy.create(Student.class);
        long start = System.currentTimeMillis();
        byte[] bytes = null;
        for (int i = 0; i < testCount; i++) {
            bytes = codec.encode(student);
        }
        System.out.println("Protobuf 序列化:" + (System.currentTimeMillis() - start) + "ms; 总大小："
                + bytes.length);

        Student student1 = codec.decode(bytes);
        System.out.println(student1);
    }

    private static void executeHessian(int testCount) throws IOException {
        StudentSerializable student = getStudent2();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        long start = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            ho.writeObject(student);
            if (i == 0) {
                System.out.println(os.toByteArray().length);
            }
        }
        System.out.println("Hessian 序列化:" + (System.currentTimeMillis() - start) + "ms; 总大小："
                + os.toByteArray().length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(os.toByteArray());
        HessianInput hessianInput = new HessianInput(byteArrayInputStream);
        StudentSerializable student1 = (StudentSerializable) hessianInput.readObject();
        System.out.println(student1);
    }


    private static void deepCloneTest() {
        StudentSerializable student = getStudent2();
        StudentSerializable student1 = student.deepClone();
        student1.getTeacher().setName("仙灵");
        System.out.println(student + "::" + student1);
    }

    private static StudentSerializable getStudent2() {
        StudentSerializable student = new StudentSerializable();
        student.setName("许贤贝");
        student.setAge(28);
        student.getTeacher().setName("璐璐");
        return student;
    }

    private static Student getStudent() {
        Student student = new Student();
        student.setName("许贤贝");
        student.setAge(28);
        student.getTeacher().setName("璐璐");
        return student;
    }
}
