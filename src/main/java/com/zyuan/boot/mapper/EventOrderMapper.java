package com.zyuan.boot.mapper;

import com.zyuan.boot.entity.EventOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EventOrderMapper {

    List<EventOrder> selectAll();

    EventOrder selectById(@Param("id") Long id);

    void batchInsertByValues(@Param("eventOrders") List<EventOrder> eventOrders);

    void batchInsertBySet(@Param("eventOrders") List<EventOrder> eventOrders);

    void batchUpdate(@Param("eventOrders") List<EventOrder> eventOrders);

    void batchUpdateByForeach(@Param("eventOrders") List<EventOrder> eventOrders);

    void batchUpdateByMap(@Param("eventOrderMap") Map<Long,EventOrder> eventOrderMap);

}
