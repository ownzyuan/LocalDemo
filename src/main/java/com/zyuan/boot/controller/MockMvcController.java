package com.zyuan.boot.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/mockMcv")
public class MockMvcController {

    @GetMapping("/first/connect")
    public String testConnect() {
        String result = "Hello MockMVC";
        return result;
    }

    @GetMapping("/connect/withParam")
    public String testConnectWithParam(@RequestParam("id") Long id, @RequestParam("name") String name) {
        String result = "id:" + id + ",name:" + name;
        return result;
    }

    @PostMapping("/connect/withBody")
    public String testConnectWithBody(@RequestBody Map<String,String> requestBody) {
        String result = JSONArray.toJSONString(requestBody);
        return result;
    }

}
