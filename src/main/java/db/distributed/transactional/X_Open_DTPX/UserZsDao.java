package db.distributed.transactional.X_Open_DTPX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-11-13
 * VersionV1.0
 * @description
 */
@Transactional
@Repository
public class UserZsDao {
    @Autowired
    private UserLsDao userLsDao;

    @Resource(name = "mysqlJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public void zsMinus(int money) {
        //zs的mysql扣款
        String sql = "update MYMONEY set lostedmoney=lostedmoney-" + money + " where id=1";
        jdbcTemplate.execute(sql);
        //调用ls的oracle加钱
        userLsDao.lsAdd(money);
        //模拟出现异常 查看数据两个操作数据是否都回滚
        String a = null;
        a.toCharArray();
    }


}
