package com.zyuan.boot.redis.impl;

import com.zyuan.boot.redis.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final Integer extraExpireTime = 3;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, String value) {
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("redis set失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean set(String key, String value, Long expire) {
        boolean result = false;
        try {
            if (expire > 0L) {
                ValueOperations valueOperations = redisTemplate.opsForValue();
                valueOperations.set(key, value, expire, TimeUnit.SECONDS);
                result = true;
            }
        } catch (Exception e) {
            log.error("redis set(设置存活时间)失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            result = valueOperations.setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("redis setIfAbsent失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean multiSet(Map<String, String> maps) {
        boolean result = false;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.multiSet(maps);
            result = true;
        } catch (Exception e) {
            log.error("redis 批量添加失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        boolean result = false;
        try {
            unit = ObjectUtils.isEmpty(unit) ? TimeUnit.SECONDS : unit;
            long actualTimeout = getActualTimeOut(timeout, unit);
            result = redisTemplate.expire(key, actualTimeout, unit);
        } catch (Exception e) {
            log.error("redis 设置过期时间失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean hasKey(String key) {
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis 判断是否存在对应key失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        Set result = null;
        try {
            result = redisTemplate.keys(pattern);
        } catch (Exception e) {
            log.error("redis 查询匹配的key失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String get(String key) {
        String result = null;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            result = (String) valueOperations.get(key);
        } catch (Exception e) {
            log.error("redis 读取数据失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        List result = null;
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            result = valueOperations.multiGet(keys);
        } catch (Exception e) {
            log.error("redis 批量获取value失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Long getExpire(String key) {
        Long result = 0L;
        try {
            result = redisTemplate.getExpire(key);
        } catch (Exception e) {
            log.error("redis 获取过期时间失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Long getExpire(String key, TimeUnit unit) {
        Long result = 0L;
        try {
            result = redisTemplate.getExpire(key, unit);
        } catch (Exception e) {
            log.error("redis 获取过期时间(带unit)失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Date getExpireDate(String key) {
        Date result = null;
        try {
            Long expire = redisTemplate.getExpire(key);
            if (expire != -1L && expire != -2L) {
                result = new Date(System.currentTimeMillis() + expire*1000L);
            }
        } catch (Exception e) {
            log.error("redis 获取过期日期失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List lRange(String key, long start, long end) {
        List result = null;
        try {
            result = redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("redis range失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Long rightPushAll(String key, Collection value) {
        Long result = null;
        try {
            result = redisTemplate.opsForList().rightPushAll(key, value);
        } catch (Exception e) {
            log.error("redis push all失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(String key) {
        boolean result = false;
        try {
            result = redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("redis 通过key删除失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Long delete(Collection<String> keys) {
        Long result = 0L;
        try {
            result = redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("redis 通过key批量删除失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(String... keys) {
        boolean result = false;
        try {
            String[] keyArr = keys;
            int length = keyArr.length;
            for (int i = 0; i < length; i++) {
                String key = keyArr[i];
                this.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis 通过多个key删除失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Long deleteByPre(String pre) {
        Long result = 0L;
        try {
            Set<String> keys = redisTemplate.keys(pre + "*");
            Integer size = keys.size();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                redisTemplate.delete(key);
            }
            result = (long) size;
        } catch (Exception e) {
            log.error("redis 通过key前缀删除失败：" + e.getMessage());
        }
        return result;
    }

    private long getActualTimeOut(Long expire, TimeUnit unit) {
        Random random = new Random();
        int extraTime = random.nextInt(extraExpireTime);
        switch (unit) {
            case DAYS:
                return 86400L * expire + (long) extraTime;
            case HOURS:
                return 3600L * expire + (long) extraTime;
            case MINUTES:
                return 60L * expire + (long) extraTime;
            case SECONDS:
                return expire + (long) extraTime;
            default:
                throw new IllegalArgumentException("未知的时间类型，请传递正确的TimeUnit");
        }
    }

}
