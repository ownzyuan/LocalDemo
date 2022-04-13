package com.zyuan.boot.mongo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MongoService implements IMongoService{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertIntoMongo() {
        List<FirstMongoDO> insertList = new ArrayList<>();
        Long createId = 12345678910L;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i < 200001; i++) {
            FirstMongoDO firstMongoDO = new FirstMongoDO();
            firstMongoDO.setFirstName("名称"+i);
            firstMongoDO.setFirstType(1);
            firstMongoDO.setFirstTitle("标题" + i);
            firstMongoDO.setCreateId(createId);
            firstMongoDO.setCreateTime(new Date());
            insertList.add(firstMongoDO);
        }
        mongoTemplate.insert(insertList, "nextCollection");
        insertList.clear();
//        mongoTemplate.insertAll(insertList);
    }

    @Override
    public void selectCount() {
        Query query = new Query();
        mongoTemplate.count(query, "firstCollection");
    }

}
