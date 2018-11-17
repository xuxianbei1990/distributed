package db.distributed.transactional.X_Open_DTPX;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Name  用户更新钱
 *
 * @author xuxb
 * Date 2018-11-13
 * VersionV1.0
 * @description
 */
@Transactional
@Repository
public class UserLsDao {

    @Resource(name = "collegeGoodsJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public void lsAdd(int money) {
        String sql = "update MYMONEY set lostedmoney=lostedmoney+" + money + " where id=1";
        jdbcTemplate.execute(sql);
    }

}
