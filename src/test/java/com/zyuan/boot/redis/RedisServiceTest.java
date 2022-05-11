package com.zyuan.boot.redis;

import com.alibaba.fastjson.JSONArray;
import com.zyuan.boot.redis.dto.ThisIsDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceTest {

    @Autowired
    private IRedisService redisService;

    @Test
    public void testSetAndGet() {
        String key = "list_to_string_01";
        List<ThisIsDTO> addDTOList = getAddDTOList();
        String listToString = JSONArray.toJSONString(addDTOList);
        boolean setSuccess = redisService.set(key, listToString);
        System.out.println("插入是否成功：" + setSuccess);
        boolean hasKey = redisService.hasKey(key);
        System.out.println("key是否存在：" + hasKey);
        String thisDTOListObject = redisService.get(key);
        List<ThisIsDTO> thisIsDTOList = JSONArray.parseArray(thisDTOListObject, ThisIsDTO.class);
        if (CollectionUtils.isNotEmpty(thisIsDTOList)) {
            System.out.println(thisIsDTOList.toString());
        }
    }

    @Test
    public void testSetIfAbsent() {
        String key = "list_to_string_01";
        boolean hasKey = redisService.hasKey(key);
        System.out.println("key是否存在：" + hasKey);
        List<ThisIsDTO> addDTOList = getAddDTOList();
        String listToString = JSONArray.toJSONString(addDTOList);
        boolean setSuccess = redisService.setIfAbsent(key, listToString);
        System.out.println("插入是否成功：" + setSuccess);
    }

    @Test
    public void testKeysAndMultiSetAndMultiGet() {
        Map<String,String> maps = new HashMap<>();
        maps.put("specialty_key_01", "value_01");
        maps.put("specialty_key_02", "value_02");
        maps.put("specialty_key_03", "value_03");
        boolean multiSetSuccess = redisService.multiSet(maps);
        System.out.println("批量添加是否成功：" + multiSetSuccess);
        String pattern = "specialty*";
        Set<String> keys = redisService.keys(pattern);
        System.out.println("keys：" + (CollectionUtils.isNotEmpty(keys) ? keys.toString() : "null"));
//        List<String> keys = Arrays.asList("specialty_key_01", "specialty_key_02", "specialty_key_03");
        List<String> values = redisService.multiGet(keys);
        System.out.println("values：" + (CollectionUtils.isNotEmpty(values) ? values.toString() : "null"));
    }

    @Test
    public void testExpire() {
        String key = "need_expire_key_01";
        boolean setSuccess = redisService.set(key, "expire_01");
        System.out.println("添加是否成功：" + setSuccess);
        boolean hasKey01 = redisService.hasKey(key);
        System.out.println("未设置过期时间，是否存在：" + hasKey01);
        boolean expireSuccess = redisService.expire(key, 10, TimeUnit.SECONDS);
        System.out.println("设置过期时间是否成功：" + expireSuccess);
        Long expire = redisService.getExpire(key);
//        Long expire = redisService.getExpire(key, TimeUnit.SECONDS);
        System.out.println("剩余过期时间为：" + expire + "s");
        Date date = redisService.getExpireDate(key);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotEmpty(date)) {
            String expireDate = dateFormat.format(date);
            System.out.println("过期日期为：" + expireDate);
        }
        try {
            Thread.sleep(15000);
            boolean hasKey02 = redisService.hasKey(key);
            System.out.println("设置过期时间且休眠后，是否存在：" + hasKey02);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLRange() {
        String key = "right_push_all_01";
//        List<ThisIsDTO> dtoList = redisService.lRange(key, 0, -1);
//        System.out.println(dtoList.toString());
//        for (ThisIsDTO thisIsDTO : dtoList) {
//            String name = thisIsDTO.getName();
//        }
        List<LinkedHashMap<String, Object>> linkedHashMapList = redisService.lRange(key, 0, -1);
        for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
            for (String concurrentKey : linkedHashMap.keySet()) {
                Object value = linkedHashMap.get(concurrentKey);
                System.out.println(concurrentKey + "--->" + value);
            }
            System.out.println("=============");
        }
    }

    @Test
    public void testRightPushAll() {
        String key = "right_push_all_01";
        List<ThisIsDTO> addDTOList = getAddDTOList();
        Long result = redisService.rightPushAll(key, addDTOList);
        System.out.println(result);
    }

    @Test
    public void testBatchDelete() {
        Map<String,String> maps = new HashMap<>();
        maps.put("need_delete_key_01", "delete_01");
        maps.put("need_delete_key_02", "delete_02");
        maps.put("need_delete_key_03", "delete_03");
        maps.put("need_delete_key_04", "delete_04");
        maps.put("need_delete_key_05", "delete_05");
        maps.put("need_delete_key_06", "delete_06");
        maps.put("need_delete_key_07", "delete_07");
        boolean multiSetSuccess = redisService.multiSet(maps);
        System.out.println("批量添加是否成功：" + multiSetSuccess);
//        boolean deleteOne = redisService.delete("need_delete_key_10");
        boolean deleteOne = redisService.delete("need_delete_key_01");
        System.out.println("单个删除是否成功：" + deleteOne);
        List<String> deleteKeyList = Arrays.asList("need_delete_key_02", "need_delete_key_03");
        Long deleteKeyNumber = redisService.delete(deleteKeyList);
        System.out.println("批量删除了：" + deleteKeyNumber + "个key");
        boolean deleteMulti = redisService.delete("need_delete_key_04", "need_delete_key_05");
        System.out.println("多个key删除是否成功：" + deleteMulti);
        Long deleteByPreNumber = redisService.deleteByPre("123456");
        System.out.println("通过key前缀删除了：" + deleteByPreNumber + "个key");
    }

    private List<ThisIsDTO> getAddDTOList() {
        List<ThisIsDTO> addDTOList = new ArrayList<>();
        for (int i = 8; i <= 10; i++) {
            ThisIsDTO dto = new ThisIsDTO();
            String name = "name" + i + i;
            Integer age = i*30;
            Long time = i*20L;
            dto.setName(name);
            dto.setAge(age);
            dto.setTime(time);
            addDTOList.add(dto);
        }
        return addDTOList;
    }

}