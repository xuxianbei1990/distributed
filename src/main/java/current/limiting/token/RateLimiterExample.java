package current.limiting.token;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Name 令牌
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description
 * https://mp.weixin.qq.com/s/5CbjktmOIEMAPLmVgouHIg 这个也不错
 */
@Slf4j
public class RateLimiterExample {
    /**
     * 每秒钟放入5个令牌，相当于每秒只允许执行5个请求
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(5);

    public static void main(String[] args) {
        // 模拟有100个请求
        for (int i = 0; i < 100; i++) {
            // 尝试从令牌桶中获取令牌，若获取不到则等待300毫秒看能不能获取到
            if (RATE_LIMITER.tryAcquire(300, TimeUnit.MILLISECONDS)) {
                // 获取成功，执行相应逻辑
                handle(i);
            }
        }
    }

    private static void handle(int i) {
        log.info("{}", i);
    }
}
