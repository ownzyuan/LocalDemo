package com.zyuan.boot.MySQL死锁;


import com.alibaba.fastjson.JSON;
import com.zyuan.boot.entity.TestLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MySQLLockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ITestLockService testLockService;

    @Test
    public void selectAll() {
        List<TestLock> testLocks = testLockService.selectAllList();
        System.out.println(testLocks.toString());
    }

    @Test
    public void batchInsert() throws Exception {
        List<TestLock> testLockList = getList(15, 8);
        Runnable task = () -> {
            try {
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/test/lock/batchInsert")
                                // 指定 header中的 Content-Type为 application/json
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                // 通过content()存放Body的Json
                                .content(JSON.toJSONString(testLockList)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        int nThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(task);
        }
    }

    @Test
    public void batchInsertByMap() throws Exception {
        TestLock testLock = new TestLock();
        testLock.setId(15L);
        testLock.setCode(15);
        testLock.setOther(15);
        testLock.setUnionId(155L);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/test/lock/batchInsert/byMap")
                        // 指定 header中的 Content-Type为 application/json
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        // 通过content()存放Body的Json
                        .content(JSON.toJSONString(testLock)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        // 打印作为字符串
        System.out.println(content);
    }

    private List<TestLock> getList(int number, int length) {
        List<TestLock> testLockList = new ArrayList<>(length);
        for (Integer i = number; i < number + length; i++) {
            TestLock testLock = new TestLock();
            testLock.setId(i.longValue());
            testLock.setCode(i);
            testLock.setOther(i);
            testLock.setUnionId(i.longValue());
            testLockList.add(testLock);
        }
        return testLockList;
    }

}