package com.zyuan.boot.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;

public interface IRedisService {

    boolean set(final String key, String value);

    boolean set(final String key, String value, Long expire);

    // set值进缓存，如果已经存在该key，则不插入，返回false
    boolean setIfAbsent(final String key, String value);

    // set集合，添加
    Long add(final String key, String ... values);

    // 批量添加值进缓存中
    boolean multiSet(Map<String,String> maps);

    boolean expire(final String key, long timeout, TimeUnit unit);

    boolean hasKey(final String key);

    // 通过条件pattern模糊查询key
    Set<String> keys(String pattern);

    String get(final String key);

    // 通过key集合批量获取他们的value值
    List<String> multiGet(Collection<String> keys);

    Long getExpire(final String key);

    Long getExpire(final String key, TimeUnit unit);

    // 获取过期具体日期
    Date getExpireDate(final String key);

    // 从左至右获取集合元素
    List lRange(final String key, long start, long end);

    // 通过key查询set集合
    Set members(final String key);

    // 通过右插法添加集合（先进先出）
    Long rightPushAll(final String key, Collection value);

    boolean delete(final String key);

    // 通过集合的方式批量删除
    Long delete(Collection<String> keys);

    // 通过若干个key传参删除
    boolean delete(final String ... keys);

    // 通过key前缀删除
    Long deleteByPre(String pre);
}
