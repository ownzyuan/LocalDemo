package com.zyuan.boot.camunda.controller;

import com.zyuan.boot.camunda.constant.FlowConstant;
import com.zyuan.boot.camunda.service.StartFirstFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhongyuan
 */
@RestController
@RequestMapping("/first/flow")
public class StartFirstFlowController {

    @Autowired
    private StartFirstFlowService startFirstFlowService;

    @PostMapping("/deploy")
    public String deploy(@RequestBody Map<String, String> map) {
        String bpmnXml = map.get(FlowConstant.BPMN_XML);
        String id = startFirstFlowService.deployConfigByBpmn(bpmnXml);
        return id;
    }

}
