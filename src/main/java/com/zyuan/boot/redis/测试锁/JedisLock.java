package com.zyuan.boot.redis.测试锁;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;


@Service
public class JedisLock {

    @Value("${spring.redis.host}")
    private String host = "127.0.0.1";
    @Value("${spring.redis.port}")
    private Integer port = 6379;
//    @Value("${spring.redis.password}")
    private String password = "";

    private static final String lockKey = "lock_key";
    private static final String lockValue = "lock_value";
    private static final Long timeOut = 2000L;

    /**
     * 通过setNx方法实现锁功能（非原子操作）
     * 因为加锁和设置超时时间是分开进行的，假如设置超时时间出错，那么该key就不再会过期，可能导致redis内存不足
     *
     * @return
     */
    public Long testSetNx() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        // 先加锁
        Long setnx = jedis.setnx(lockKey, lockValue);
        // 模拟报错，就不会设置超时时间
//        int i = 1/0;
        // 加锁成功后，设置超时时间
        if (setnx == 1L) {
            jedis.expire(lockKey, 5);
        }
        return setnx;
    }

    /**
     * 通过set方法实现加锁+设置超时时间，然后执行完成再手动释放锁
     * 但是这种方式会造成释放了别人的锁的情况
     * 比如：线程A、线程B的方法需要执行2s，且都是加的同一个锁。
     * A先加锁，而锁只设置了1s的超时，那么1s后锁过期删除，
     * 这时B就获取到了锁，那么再过1s后，A执行完成走进finally就会将B锁释放
     * 解决办法：每次请求的时候都会有一个requestId，那就将此id作为value存放，
     * 然后释放锁前对比值是否相同，防止释放其他线程锁的问题
     *
     * @return
     */
    public String testSet(Long requestId) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        String setnx = null;
        try {
            // 先加锁
//            setnx = jedis.set(lockKey, lockValue, "NX", "PX", 1000);
            // value设置为requestId，后续取出来比对
            setnx = jedis.set(lockKey, requestId.toString(), "NX", "PX", 1000);
            // 加锁成功后，设置超时时间
            if (StringUtils.equals("OK", setnx)) {
                jedis.expire(lockKey, 1);
            } else {
                setnx = "NO";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String value = jedis.get(lockKey);
            // 保证是同一个请求，再释放锁
            if (StringUtils.equals(value, requestId.toString())) {
                // 最后一定要释放锁
                jedis.del(lockKey);
            }
        }
        return setnx;
    }

    /**
     * 手写一个自旋锁
     *
     * @param requestId
     * @return
     */
    public String spinLock(Long requestId) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        try {
            // 记录尝试加锁的次数
            int tryLockCount = 1;
            long start = System.currentTimeMillis();
            while (true) {
                String result = jedis.set(lockKey, requestId.toString(), "NX", "PX", 2000);
                // 为“OK”说明加锁成功
                if (StringUtils.equals("OK", result)) {
                    System.out.println(requestId + "：" + "第" + tryLockCount + "次" + "尝试加锁成功");
                    // 花100ms执行业务逻辑
                    Thread.sleep(1000);
                    return result;
                } else {
                    System.out.println(requestId + "：" + "第" + tryLockCount + "次" + "尝试加锁失败");
                }
                // 本次循环所使用时间
                long currentTime = System.currentTimeMillis() - start;
                // 如果超时了，就返回“No”
                if (currentTime >= timeOut) {
                    return "No";
                }
                try {
                    // 50ms后重试
                    Thread.sleep(50);
                    tryLockCount++;
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String value = jedis.get(lockKey);
            if (StringUtils.equals(value, requestId.toString())) {
                jedis.del(lockKey);
            }
        }
        return null;
    }

}
