package com.zyuan.boot.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestESClient {

    public static void main(String[] args) {
        try {
            // 创建ES客户端
            RestHighLevelClient esClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("127.0.0.1", 9200))
            );

            // 创建名称为user的索引
//            createIndex(esClient);
            // 查询user索引
//            getIndex(esClient);
            // 删除user索引
//            deleteIndex(esClient);

            // ====================================================

            // 插入数据
//            createData(esClient);
            // 查询数据
//            getData(esClient);
            // 修改数据
//            updateData(esClient);
            // 删除数据
//            deleteData(esClient);

            // ====================================================

            // 批量插入
//            batchCreateData(esClient);
            // 批量删除
            batchDeleteData(esClient);

            esClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createIndex(RestHighLevelClient esClient) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
        CreateIndexResponse createResponse =
                esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        // 响应状态
        boolean createAcknowledged = createResponse.isAcknowledged();
        System.out.println("创建索引结果：" + createAcknowledged);
    }

    private static void getIndex(RestHighLevelClient esClient) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse =
                esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        // user索引结构参考：
        // {
        //    "user": {
        //        "aliases": {},
        //        "mappings": {},
        //        "settings": {
        //            "index": {
        //                "routing": {
        //                    "allocation": {
        //                        "include": {
        //                            "_tier_preference": "data_content"
        //                        }
        //                    }
        //                },
        //                "number_of_shards": "1",
        //                "provided_name": "user",
        //                "creation_date": "1690695724384",
        //                "number_of_replicas": "1",
        //                "uuid": "KMoFELUeSA-cIipW-aK9OQ",
        //                "version": {
        //                    "created": "7170699"
        //                }
        //            }
        //        }
        //    }
        //}
        Map<String, List<AliasMetaData>> aliases = getIndexResponse.getAliases();
        Map<String, MappingMetaData> mappings = getIndexResponse.getMappings();
        Map<String, Settings> settings = getIndexResponse.getSettings();
    }

    private static void deleteIndex(RestHighLevelClient esClient) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user");
        AcknowledgedResponse deleteResponse =
                esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        boolean deleteAcknowledged = deleteResponse.isAcknowledged();
        System.out.println("删除结果：" + deleteAcknowledged);
    }

    private static void createData(RestHighLevelClient esClient) throws IOException {
        // 设置索引名称为user，文档id为1001
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");

        ESUser user = new ESUser();
        user.setName("张三");
        user.setAge(20);
        user.setSex("男-1");

        // user对象转json，并存入indexRequest的source中
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        indexRequest.source(userJson, XContentType.JSON);

        IndexResponse indexResponse =
                esClient.index(indexRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println("创建结果：" + result);
    }

    private static void getData(RestHighLevelClient esClient) throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1001");
        GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> source = getResponse.getSource();
    }

    private static void updateData(RestHighLevelClient esClient) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id("1001");
        // name字段改为update，age改为8
        updateRequest.doc(XContentType.JSON, "name", "update-1", "age", 8);

        UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = updateResponse.getResult();
    }

    private static void deleteData(RestHighLevelClient esClient) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("user").id("1001");
        DeleteResponse deleteResponse = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = deleteResponse.getResult();
    }

    private static void batchCreateData(RestHighLevelClient esClient) throws IOException {
        // 设置索引名称为user，文档id为1001
        BulkRequest bulkRequest = new BulkRequest();

        // 批量录入
        bulkRequest.add(new IndexRequest().index("user").id("1002")
                        .source(XContentType.JSON,"name", "batch-1", "age", 2));
        bulkRequest.add(new IndexRequest().index("user").id("1003")
                .source(XContentType.JSON,"name", "batch-2", "age", 3));
        bulkRequest.add(new IndexRequest().index("user").id("1004")
                .source(XContentType.JSON,"name", "batch-2", "age", 4));

        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        TimeValue took = bulkResponse.getTook();
        BulkItemResponse[] items = bulkResponse.getItems();
    }

    private static void batchDeleteData(RestHighLevelClient esClient) throws IOException {
        // 设置索引名称为user，文档id为1001
        BulkRequest bulkRequest = new BulkRequest();

        // 批量录入
        bulkRequest.add(new DeleteRequest().index("user").id("1002"));
        bulkRequest.add(new DeleteRequest().index("user").id("1003"));
        bulkRequest.add(new DeleteRequest().index("user").id("1004"));

        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        TimeValue took = bulkResponse.getTook();
        BulkItemResponse[] items = bulkResponse.getItems();
    }
}
