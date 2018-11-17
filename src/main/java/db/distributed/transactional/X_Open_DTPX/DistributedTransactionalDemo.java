package db.distributed.transactional.X_Open_DTPX;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Name 分布式事务
 *
 * @author xuxb
 * Date 2018-11-13
 * VersionV1.0
 * @description https://blog.csdn.net/liaomin416100569/article/details/78651525
 */
public class DistributedTransactionalDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac=new ClassPathXmlApplicationContext("spring-db.xml");
        UserZsDao userZsDao=(UserZsDao)cpxac.getBean("userZsDao");
        // 下面代码一定会抛出异常，所以最后钱不会发生变化
        userZsDao.zsMinus(100);
        cpxac.close();
    }
}
