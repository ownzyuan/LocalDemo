package com.zyuan.boot.camunda.controller;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongyuan
 */
@RestController
@RequestMapping("/first/flow")
public class StartFirstFlowController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/deploy")
    public Deployment deploy() {
        // 定义名称、绑定需要部署的文件、部署流程
        Deployment deployment = repositoryService.createDeployment()
                .name("尝试开启一个流程")
                .addClasspathResource("userApprove.bpmn")
                .deploy();
        return deployment;
    }

}
