package com.zyuan.boot.camunda.constant;

import java.util.HashMap;
import java.util.Map;

public class FlowConstant {

    // bpmn请求主键
    public static final String BPMN_XML = "bpmnXml";

    // namespace
    public static final String BPMN_NAMESPACE = "bpmn";
    public static final String BPMN2_NAMESPACE = "bpmn2";

    public static final String END_EVENT = "endEvent";

    // bpmn其他相关参数
    public static final String BPMN_SUFFIX = ".bpmn";
    public static final String BPMN_PROCESS = "bpmnProcess";
    public static final String BPMN_START_EVENT = "bpmnStartEvent";
    public static final String BPMN_END_EVENT = "bpmnEndEvent";
    public static final String BPMN_EXCLUSIVE_GATEWAY = "bpmnExclusiveGateway";
    public static final String BPMN_USER_TASK = "bpmnUserTask";
    public static final String BPMN_OUTGOING = "bpmnOutgoing";
    public static final String BPMN_INCOMING = "bpmnIncoming";
    public static final String BPMN_SEQUENCE_FLOW = "bpmnSequenceFlow";
    public static final String BPMN_SEQUENCE_FLOW_ALL = "bpmnSequenceFlowAll";
    public static final String CAMUNDA_PROPERTY = "camundaProperty";
    public static final String SEQUENCE_PRIORITY = "sequencePriority";
    public static final String CHILD_NODE_ID = "childNodeId";
    public static final String INCOMING_NODE_ID = "incomingNodeId";


    public static final Map<String, String> BPMN_MAP = new HashMap<>();
    static {
        BPMN_MAP.put(BPMN_PROCESS, "//bpmn:process");
        BPMN_MAP.put(BPMN_START_EVENT, ".//bpmn:startEvent");
        BPMN_MAP.put(BPMN_END_EVENT, ".//bpmn:endEvent");
        BPMN_MAP.put(BPMN_EXCLUSIVE_GATEWAY, ".//bpmn:bpmnExclusiveGateway");
        BPMN_MAP.put(BPMN_USER_TASK, ".//bpmn:bpmnUserTask");
        BPMN_MAP.put(BPMN_OUTGOING, ".//bpmn:outgoing");
        BPMN_MAP.put(BPMN_INCOMING, ".//bpmn:incoming");
        BPMN_MAP.put(BPMN_SEQUENCE_FLOW, "//bpmn:sequenceFlow[@id='%s']");
        BPMN_MAP.put(BPMN_SEQUENCE_FLOW_ALL, "//bpmn:sequenceFlow");
        BPMN_MAP.put(CAMUNDA_PROPERTY, "./bpmn:extensionElements/camunda:properties/camunda:property");
        BPMN_MAP.put(SEQUENCE_PRIORITY, "./bpmn:extensionElements/camunda:properties/camunda:property[@name='condition_priority']");
        BPMN_MAP.put(CHILD_NODE_ID, "./*[@id='%s']");
        BPMN_MAP.put(INCOMING_NODE_ID, ".//bpmn:incoming[text()='%s']");
    }

    public static final Map<String, String> BPMN2_MAP = new HashMap<>();
    static {
        BPMN2_MAP.put(BPMN_PROCESS, "//bpmn2:process");
        BPMN2_MAP.put(BPMN_START_EVENT, ".//bpmn2:startEvent");
        BPMN2_MAP.put(BPMN_END_EVENT, ".//bpmn2:endEvent");
        BPMN2_MAP.put(BPMN_EXCLUSIVE_GATEWAY, ".//bpmn2:bpmnExclusiveGateway");
        BPMN2_MAP.put(BPMN_USER_TASK, ".//bpmn2:bpmnUserTask");
        BPMN2_MAP.put(BPMN_OUTGOING, ".//bpmn2:outgoing");
        BPMN2_MAP.put(BPMN_INCOMING, ".//bpmn2:incoming");
        BPMN2_MAP.put(BPMN_SEQUENCE_FLOW, "//bpmn2:sequenceFlow[@id='%s']");
        BPMN2_MAP.put(BPMN_SEQUENCE_FLOW_ALL, "//bpmn2:sequenceFlow");
        BPMN2_MAP.put(CAMUNDA_PROPERTY, "./bpmn2:extensionElements/camunda:properties/camunda:property");
        BPMN2_MAP.put(SEQUENCE_PRIORITY, "./bpmn2:extensionElements/camunda:properties/camunda:property[@name='condition_priority']");
        BPMN2_MAP.put(CHILD_NODE_ID, "./*[@id='%s']");
        BPMN2_MAP.put(INCOMING_NODE_ID, ".//bpmn2:incoming[text()='%s']");
    }

}
