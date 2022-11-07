package practice.frame;

/**
 * @author: xuxianbei
 * Date: 2022/10/31
 * Time: 10:21
 * Version:V1.0
 */
public class KniSpring {
    /**
     * IOC
     *  怎么实现：
     *  扫描对应包的类，得到类的完整引用，然后使用反射得到类的实例。并且注入
     * AOP
     *  怎么实现：
     *  在IOC的基础上，给BeanDefinition做了一次动态代理
     *
     * 其他类怎么注入进来？
     *  如果不是放到指定目录下,spring无法识别，
     *  1.Import 方式：类，类路径，ImportBeanDefinitionRegistrar(这种方式就是mybatis扩展方式，
     * MapperScan--MapperScannerRegistrar), 在类加载阶段注入ImportBeanDefinitionRegistrar。
     * 2.从生命周期入手：BeanPostProcessor(这个是从类创建也就是DI之后入手主要围绕init-method方法);BeanFactoryPostProcessor(在spring结构体系中创建bean是通过
     * BeanFactory创建的也就是DI之前)InstantiationAwareBeanPostProcessorAdapter（围绕整个类创建）BeanFactoryAware，BeanNameAware，InitializingBean, DisposableBean;
     * 3.自定义标签
     */

}
