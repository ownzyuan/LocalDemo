package com.zyuan.boot.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearch配置文件
 */
@Configuration
@Slf4j
public class ElasticSearchRestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.first-host}")
    private String firstHost;
    @Value("${es.first-port}")
    private int firstPort;

    @Value("${es.username}")
    private String username;
    @Value("${es.password}")
    private String password;

    @Value("${es.charset}")
    private String charset;
    @Value("${es.scheme}")
    private String scheme;
    @Value("${es.client.connectTimeOut}")
    private int connectTimeOut;
    @Value("${es.client.socketTimeOut}")
    private int socketTimeOut;


    @Bean
    public RestClientBuilder restClientBuilder() {
        // 用户名，密码设置
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                username, password
        ));
        // host、port、scheme设置
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost(firstHost, firstPort, scheme)
        );
        restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            // 最大连接数
            httpAsyncClientBuilder.setMaxConnTotal(300);
            // 最大路由连接数
            httpAsyncClientBuilder.setMaxConnPerRoute(200);
            // 赋予连接凭证
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpAsyncClientBuilder;
        });

        Header[] defaultHeaders = new Header[]{
                new BasicHeader("Accept", "*/*"),
                new BasicHeader("Charset", charset)
        };
        restClientBuilder.setDefaultHeaders(defaultHeaders);

        restClientBuilder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                log.error("监听某个es节点失败，nodeHost：{}", node.getHost());
            }
        });

        restClientBuilder.setRequestConfigCallback(builder ->
                builder.setConnectTimeout(connectTimeOut).setSocketTimeout(socketTimeOut)
        );
        return restClientBuilder;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean
    public RestClient restClient(RestClientBuilder restClientBuilder) {
        return restClientBuilder.build();
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(firstHost + ":" + firstPort)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
