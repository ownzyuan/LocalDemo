package com.zyuan.boot.controller;

import com.alibaba.fastjson.JSON;
import com.zyuan.boot.entity.MockMVCInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT：
// 指示Spring Boot在随机且可用的端口上启动Web服务器，避免端口冲突
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 自动配置MockMvc和Spring IOC容器
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MockMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testConnect() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                // Get请求，url为：/mockMcv/first/connect
                MockMvcRequestBuilders.get("/mockMcv/first/connect"))
                // 检查响应状态码是否为200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 返回一个MvcResult回调对象
                .andReturn();
        // 获取响应文本
        String content = mvcResult.getResponse().getContentAsString();
        // 通过断言来验证返回是否正确
        Assert.assertTrue(content.equals("Hello MockMVC"));
    }

    @Test
    public void testConnectWithParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.get("/mockMcv/connect/withParam")
                            // 通过param()存放各个请求参数
                            .param("id", "1")
                            .param("name", "MockMVC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("MockMVC"));
    }

    @Test
    public void testConnectWithBody() throws Exception {
        MockMVCInfo mockMVCInfo = new MockMVCInfo();
        mockMVCInfo.setId(3L);
        mockMVCInfo.setName("MockMVC");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/mockMcv/connect/withBody")
                        // 指定 header中的 Content-Type为 application/json
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        // 通过content()存放Body的Json
                        .content(JSON.toJSONString(mockMVCInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("MockMVC"));
    }

}