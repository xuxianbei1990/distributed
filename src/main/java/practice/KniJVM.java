package practice;

/**
 * @author: xuxianbei
 * Date: 2022/10/28
 * Time: 9:41
 * Version:V1.0
 */
public class KniJVM {
    /**
     * 加载文件：
     * 启动类加载器 java安装路径下，lib下的jar包，我们常用的String，BigDecimal就在rt.jar包里面
     * 扩展类加载器 核心包基础上，加载/lib/ext
     * 系统类加载
     *
     * 对象信息：
     * 在64位操作系统是12字节，占用16个字节
     * 1.Header 对象头
     *   Mark word 自身运行时数据 8字节
     *     Hash值；GC分代年龄（jdk1.7是有用的）；锁状态标志；线程持有锁；偏向线程ID；偏向时间戳；
     *  类型指针 4个字节
     *    class pointer/Class Metadata Address
     *  数字长度（只要数组对象才有）
     * 2.InstanceData
     *   相同宽度的数据分配到一起
     * 3.Padding(对其填充)
     *   8个字节整数倍。
     *
     *
     *  JVM运行时数据
     *
     */
}
