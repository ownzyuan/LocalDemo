package com.zyuan.boot.camunda.service;

import camundajar.impl.scala.Tuple3;
import com.zyuan.boot.camunda.constant.FlowConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.xpath.DefaultXPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StartFirstFlowService {

    @Autowired
    private RepositoryService repositoryService;

    public String deployConfigByBpmn(String bpmnXml) {
        // 定义名称、绑定需要部署的文件、部署流程
//        Deployment deployment = repositoryService.createDeployment()
//                .name("尝试开启一个流程")
//                .addClasspathResource("userApprove.bpmn")
//                .deploy();
        String bpmn = new String(Base64.getDecoder().decode(bpmnXml.getBytes(StandardCharsets.UTF_8)));
        byte[] bytes = bpmn.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream baos = checkAndOrder(inputStream);
        Deployment depLoy = repositoryService.createDeployment()
                .name("firstFlow")
                .source("firstFlow")
                .tenantId("zy")
                .addInputStream("firstFlow" + FlowConstant.BPMN_SUFFIX, new ByteArrayInputStream(baos.toByteArray()))
                .deploy();
        String id = depLoy.getId();
        return id;
    }

    /**
     * 检查流程图并排序
     * @param inputStream
     * @return
     */
    private ByteArrayOutputStream checkAndOrder(ByteArrayInputStream inputStream) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            // 获取bpm命名空间的map集合
            Map<String, String> namespaceMap = getNamespaceMap(root);
            // 获取bpmn集合所需前缀集合
            Map<String, String> bpmnMap = getBpmnMap(namespaceMap);
            String bpmnProcess = bpmnMap.get(FlowConstant.BPMN_PROCESS);
            Element process = (Element) document.selectSingleNode(bpmnProcess);
            // 流程图基本校验
            getSimpleCheckConfig(bpmnMap, process);
            sortSeqByPriority(process, namespaceMap, bpmnMap);
            // =============output===============
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputFormat format = OutputFormat.createCompactFormat();
            format.setEncoding(StandardCharsets.UTF_8.name());
            XMLWriter writer = new XMLWriter(baos, format);
            writer.write(document);
            return baos;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取bpm命名空间的map集合
     * @param root
     * @return
     */
    private Map<String,String> getNamespaceMap(Element root) {
        Map<String,String> namespaceMap = new HashMap<>();
        // 获取元素声明中的附加命名空间信息
        String namespacePrefix = root.getNamespacePrefix();
        // 获取元素声明中的url
        String namespaceURI = root.getNamespaceURI();
        namespaceMap.put(namespacePrefix, namespaceURI);
        List<Namespace> namespaces = root.additionalNamespaces();
        if (CollectionUtils.isNotEmpty(namespaces)) {
            namespaces.stream()
                    .filter(namespace -> StringUtils.isNoneBlank(namespace.getPrefix()))
                    .forEach(namespace -> namespaceMap.put(namespace.getPrefix(), namespace.getURI()));
        }
        return namespaceMap;
    }

    /**
     * 获取bpmn集合所需前缀集合
     * @param namespaceMap
     */
    private Map<String, String> getBpmnMap(Map<String, String> namespaceMap) {
        if (namespaceMap.get(FlowConstant.BPMN_NAMESPACE) != null) {
            return FlowConstant.BPMN_MAP;
        }
        if (namespaceMap.get(FlowConstant.BPMN2_NAMESPACE) != null) {
            return FlowConstant.BPMN2_MAP;
        }
        return new HashMap<>();
//        throw new InterruptedException();
    }

    /**
     * 流程图基本校验
     * 1. 流程图中有且仅有一个开始节点和结束节点
     * 2. 流程图中总节点个数不超过100
     * 3. 每个节点连接线的起始 节点均为发起节点，末端结点均为结束节点
     * 4. 流程图不能有环
     * @param bpmnMap
     * @param process
     */
    private void getSimpleCheckConfig(Map<String, String> bpmnMap, Element process) {
        List<Node> startEvents = process.selectNodes(bpmnMap.get(FlowConstant.BPMN_START_EVENT));
        List<Node> endEvents = process.selectNodes(bpmnMap.get(FlowConstant.BPMN_END_EVENT));
        int startSize = Optional.of(startEvents).orElse(Collections.emptyList()).size();
        int endSize = Optional.of(endEvents).orElse(Collections.emptyList()).size();
        if (startSize != 1 || endSize != 1) {
//                throw new InterruptedException();
        }
        List<Node> exclusiveGateways = process.selectNodes(bpmnMap.get(FlowConstant.BPMN_EXCLUSIVE_GATEWAY));
        List<Node> userTasks = process.selectNodes(bpmnMap.get(FlowConstant.BPMN_USER_TASK));
        int exclusiveSize = Optional.of(exclusiveGateways).orElse(Collections.emptyList()).size();
        int userTaskSize = Optional.of(userTasks).orElse(Collections.emptyList()).size();
        int totalSize = startSize + endSize + exclusiveSize + userTaskSize;
        if (totalSize > 100) {
//                throw new InterruptedException();
        }
        List<Node> nodes = new LinkedList<>(Optional.of(startEvents).orElse(Collections.emptyList()));
        nodes.addAll(Optional.of(endEvents).orElse(Collections.emptyList()));
        nodes.addAll(Optional.of(exclusiveGateways).orElse(Collections.emptyList()));
        nodes.addAll(Optional.of(userTasks).orElse(Collections.emptyList()));
        List<String> names = nodes.stream().map(it -> (Element) it).map(it -> it.attributeValue("name")).collect(Collectors.toList());
        if (nodes.stream().map(it -> (Element) it).map(it -> it.attributeValue("name")).anyMatch(StringUtils::isEmpty)) {
//                throw new InterruptedException();
        }
        // 每个节点连接线的起始节点均为发起节点，末端结点均为结束节点，而且流程图中不能有环
        isolatedCheck(bpmnMap, exclusiveGateways);
        isolatedCheck(bpmnMap, userTasks);
        Map<String, Integer> visitMap = new HashMap<>();
        seqWholeCheck(process, bpmnMap, (Element) startEvents.get(0), visitMap);
    }

    /**
     * 节点必须要有进入的线和走出的线
     */
    private void isolatedCheck(Map<String, String> bpmnMap, List<Node> nodeList) {
        if (!CollectionUtils.isEmpty(nodeList)) {
            for (Node node : nodeList) {
                List<Node> incomingNodes = node.selectNodes(bpmnMap.get(FlowConstant.BPMN_INCOMING));
                List<Node> outgoingNodes = node.selectNodes(bpmnMap.get(FlowConstant.BPMN_OUTGOING));
                // 节点没有连入的线或者连出的线
                if (CollectionUtils.isEmpty(incomingNodes) || CollectionUtils.isEmpty(outgoingNodes)) {
//                    throw new InterruptedException();
                }
            }
        }
    }

    /**
     * 完整性检验: 深度优先遍历+判断是否有环-> 每个节点连接线的起始节点均为发起节点，末端结点均为结束节点，而且流程图中不能有环
     * visitMap: key为id, value 为访问状态
     * 如果value为nulL:第- 次访问
     * 如果value = 1:访问过程中的节点再次访问，流程图有环
     * 如果value = 2:访问过该节点，由于是深度优先遍历，说明当前节点之后的所有情况已经查看过
     */
    private void seqWholeCheck(Element process, Map<String, String> bpmnMap, Element element, Map<String, Integer> visitMap) {
        String id = element.attributeValue("id");
        Integer status = visitMap.get(id);
        if (Objects.isNull(status)) {
            visitMap.put(id, 1);
        } else if (status == 1) {
//            throw new InterruptedException();
        } else {
            return;
        }
        List<Element> outSeqs = element.selectNodes(bpmnMap.get(FlowConstant.BPMN_OUTGOING))
                .stream().map(it -> (Element) it).collect(Collectors.toList());
        // 当前节点没有向外的连线，而且不是结束节点，报异常
        if (CollectionUtils.isEmpty(outSeqs) && !Objects.equals(element.getName(), FlowConstant.END_EVENT)) {

        }
        for (Element outSeq : outSeqs) {
            String incoming = String.format(bpmnMap.get(FlowConstant.INCOMING_NODE_ID), outSeq.getText());
            Node incomeSeq = process.selectSingleNode(incoming);
            // 连线出去了找不到与之连接的节点或者网关
            if (Objects.isNull(incomeSeq)) {

            } else {
                Element next = incomeSeq.getParent();
                seqWholeCheck(process, bpmnMap, next, visitMap);
            }
        }
        visitMap.put(id, 2);
    }

    /**
     * 依据优先级调整sequenceFLow以及outgoing的排序
     */
    private void sortSeqByPriority(Element process, Map<String, String> nsMap, Map<String, String> bpmnMap) {
        List<Element> elements = process.elements();
        List<Node> gateways = process.selectNodes(bpmnMap.get(FlowConstant.BPMN_EXCLUSIVE_GATEWAY));
        if (!CollectionUtils.isEmpty(gateways)) {
            for (Node gateway : gateways) {
                List<Element> gatewayElements = ((Element) gateway).elements();
                List<Node> outgoingFlows = gateway.selectNodes(bpmnMap.get(FlowConstant.BPMN_OUTGOING));
                // 记录当前节点的位置方便删除
                List<Integer> seqIndexList = new ArrayList<>();
                List<Integer> outIndexList = new ArrayList<>();
                // (节点优先级，sequenceFLow, outgoing)
                List<Tuple3<Long, Element, Element>> elementList = new ArrayList<>();
                Set<Long> priorities = new HashSet<>();
                for (Node outgoingFlow : outgoingFlows) {
                    String flowSeqId = outgoingFlow.getText();
                    Element seqFlow = (Element) process.selectSingleNode(String.format(bpmnMap.get(FlowConstant.BPMN_SEQUENCE_FLOW), flowSeqId));
                    Element clone = (Element) seqFlow.clone();
                    seqIndexList.add(elements.indexOf(seqFlow));
                    outIndexList.add(gatewayElements.indexOf((Element) outgoingFlow));
                    Long priority = getPriority(nsMap, bpmnMap, clone);
                    // 不允许优先级重复
                    if (priorities.contains(priority)) {

                    }
                    priorities.add(priority);
                    elementList.add(new Tuple3<>(priority, clone, (Element) outgoingFlow.clone()));
                    seqIndexList.sort(Comparator.naturalOrder());
                    outIndexList.sort(Comparator.naturalOrder());
                    elementList.sort(Comparator.comparing(Tuple3::_1));
                    int size = elementList.size();
                    // 删除原有节点并添加排序后的节点
                    for (int i = 0; i < size; i++) {
                        int seqIndex = seqIndexList.get(i);
                        int outIndex = outIndexList.get(i);
                        elements.remove(seqIndex);
                        elements.add(seqIndex, elementList.get(i)._2());
                        gatewayElements.remove(outIndex);
                        gatewayElements.add(outIndex, elementList.get(i)._3());
                    }
                }
            }
        }
    }

    /**
     * 计算当前路径的优先级
     */
    private Long getPriority(Map<String, String> nsMap, Map<String, String> bpmnMap, Element seqFlow) {
        String xPathStr = bpmnMap.get(FlowConstant.SEQUENCE_PRIORITY);
        XPath xPath = new DefaultXPath(xPathStr);
        xPath.setNamespaceURIs(nsMap);
        Element priority = (Element) xPath.selectSingleNode(seqFlow);
        try {
            return Long.parseLong(priority.attributeValue("value"));
        } catch (Exception e) {
            throw e;
        }
    }

}
