package com.zyuan.boot.mapper;

import com.zyuan.boot.entity.EventOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventOrderMapperTest {

    @Autowired
    private EventOrderMapper eventOrderMapper;

    @Test
    public void batchInsertByValues() {
        List<EventOrder> eventOrders = getList();
        eventOrderMapper.batchInsertByValues(eventOrders);
    }

    @Test
    public void batchInsertBySet() {
        List<EventOrder> eventOrders = getList();
        eventOrderMapper.batchInsertBySet(eventOrders);
    }

    private List<EventOrder> getList() {
        List<EventOrder> eventOrders = new ArrayList<>();
        for (Long i = 11L; i <= 20; i++) {
            EventOrder addEntity = new EventOrder();
            addEntity.setId(i);
            addEntity.setEventName("事件" + i);
            addEntity.setEventType(9);
            addEntity.setOrderNo("8");
            eventOrders.add(addEntity);
        }
        return eventOrders;
    }

    @Test
    public void batchUpdate() {
        List<EventOrder> eventOrders = getUpdateList();
        eventOrderMapper.batchUpdate(eventOrders);
    }

    @Test
    public void batchUpdateByForeach() {
        List<EventOrder> eventOrders = getUpdateList();
        eventOrderMapper.batchUpdateByForeach(eventOrders);
    }

    @Test
    public void batchUpdateByMap() {
        // key为id，value为对象
        Map<Long,EventOrder> eventOrderMap = getUpdateMap();
        eventOrderMapper.batchUpdateByMap(eventOrderMap);
    }

    private List<EventOrder> getUpdateList() {
        List<EventOrder> eventOrders = new ArrayList<>();
        for (Long i = 1L; i <= 10; i++) {
            EventOrder addEntity = new EventOrder();
            addEntity.setId(i);
            addEntity.setEventName(i + "事件");
            addEntity.setEventType(0);
            addEntity.setOrderNo("777");
            eventOrders.add(addEntity);
        }
        return eventOrders;
    }

    private Map<Long,EventOrder> getUpdateMap() {
        Map<Long,EventOrder> eventOrderMap = new HashMap<>();
        for (Long i = 1L; i <= 10; i++) {
            EventOrder addEntity = new EventOrder();
            addEntity.setId(i);
            addEntity.setEventName(i + "事件");
            addEntity.setEventType(11);
            addEntity.setOrderNo("20.21");
            eventOrderMap.put(i,addEntity);
        }
        return eventOrderMap;
    }

}