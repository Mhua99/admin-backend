package com.mhua.adminbackend.controller.sys;

import com.google.gson.Gson;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.pojo.dto.ProcessDefinitionDTO;
import com.mhua.adminbackend.pojo.dto.TaskQueryDTO;
import com.mhua.adminbackend.pojo.entity.ProcessModal;
import com.mhua.adminbackend.pojo.vo.SysUserVO;
import com.mhua.adminbackend.pojo.vo.TaskVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.CommService;
import com.mhua.adminbackend.service.SysUserService;
import com.mhua.adminbackend.utils.BaseContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mhua.adminbackend.utils.DateFormatterUtils.formatDuration;

@RestController
@RequestMapping("/system/flow")
@Tag(name = "系统流程管理")
public class FlowController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CommService commService;


    // 部署流程
    @PostMapping("/deploy")
    public Result deploy(@RequestBody ProcessModal processModal) {
        Deployment deployment = repositoryService.createDeployment()
                .name("强制更新-" + System.currentTimeMillis())  // 每次部署使用唯一名称
                .key(processModal.getProcessDefinitionKey())
                .deploy();
        return Result.success(deployment.getId());
    }

    // 查询流程定义列表
    @GetMapping("/list")
    public Result<PageResult<ProcessModal>> list(ProcessDefinitionDTO processDefinitionDTO) throws IOException {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();

        String processDefinitionKey = processDefinitionDTO.getProcessDefinitionKey();

        /**
         * 求数量
         */
        ProcessDefinitionQuery countProcessDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if(processDefinitionKey != null) {
            query.processDefinitionKey(processDefinitionKey);
        } else {
            query.latestVersion();
            countProcessDefinitionQuery.latestVersion();
        }

        List<ProcessDefinition> list = query
                .orderByProcessDefinitionKey()
                .asc()
                .listPage(processDefinitionDTO.getPage() - 1, processDefinitionDTO.getPageSize());

//        for (ProcessDefinition definition : list) {
//            InputStream xmlStream = repositoryService.getProcessModel(definition.getId());
//            String xmlContent = IOUtils.toString(xmlStream, StandardCharsets.UTF_8);
//            System.out.println(xmlContent); // 打印 XML 内容
//        }

        // 总数
        long total = countProcessDefinitionQuery.count();

        List<ProcessModal> result = new ArrayList<>();
        for (ProcessDefinition pd : list) {
            InputStream xmlStream = repositoryService.getProcessModel(pd.getId());
            ProcessModal processModal = new ProcessModal();
            processModal.setId(pd.getId());
            processModal.setName(pd.getName());
            processModal.setProcessDefinitionKey(pd.getKey());
            processModal.setVersion(pd.getVersion());
            processModal.setDeploymentId(pd.getDeploymentId());
            processModal.setBpmnXml(IOUtils.toString(xmlStream, StandardCharsets.UTF_8));
            result.add(processModal);
        }
        return Result.success(new PageResult(total, result));
    }

    // 删除流程定义
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable String ids) {
        repositoryService.deleteDeployment(ids, true); // true表示级联删除运行中的实例
        return Result.success(true);
    }

    // 获取流程XML
    @GetMapping("/xml/{processDefinitionId}")
    public void getProcessXml(HttpServletResponse response,
                              @PathVariable String processDefinitionId) throws IOException {
        InputStream xmlStream = repositoryService.getProcessModel(processDefinitionId);
        IOUtils.copy(xmlStream, response.getOutputStream());
        response.setContentType("application/xml");
    }

    /**
     * 保存流程
     * @param body
     * @return
     */
    @PostMapping
    public Result create(@RequestBody Map<String, String> body) {
        String bpmnXml = body.get("bpmnXml");
        String name = body.get("name");
        String key = body.get("processDefinitionKey");
        if (bpmnXml == null || bpmnXml.isEmpty()) {
            throw new BaseException("BPMN内容不能为空");
        }
        if (name == null || name.isEmpty()) {
            throw new BaseException("流程名称不能为空");
        }

        try {
            // 1. 解析XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(bpmnXml.getBytes(StandardCharsets.UTF_8)));

            // 2. 找到<process>节点，设置name属性
            NodeList processList = doc.getElementsByTagName("process");
            if (processList.getLength() > 0) {
                Element process = (Element) processList.item(0);
                process.setAttribute("name", name);
                process.setAttribute("id", key);
            }

            // 3. 转回字符串
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String newBpmnXml = writer.getBuffer().toString();

            // 4. 部署
            InputStream inputStream = new ByteArrayInputStream(newBpmnXml.getBytes(StandardCharsets.UTF_8));
            repositoryService.createDeployment()
                    .addInputStream("process.bpmn20.xml", inputStream)
                    .name(name)
                    .deploy();
            return Result.success("部署成功");
        } catch (Exception e) {
            throw new BaseException("流程定义部署失败: " + e.getMessage());
        }
    }

    /**
     * 更新流程
     * @param body
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Map<String, Object> body) {
        String bpmnXml = (String) body.get("bpmnXml");
        String name = (String) body.get("name");
        String processKey = (String) body.get("processDefinitionKey");

        /**
         * 是否新建部署
         */
        Boolean isNewDeployment = (Boolean) body.getOrDefault("isNew", false);

        if (bpmnXml == null || bpmnXml.isEmpty()) {
            throw new BaseException("BPMN内容不能为空");
        }

        // 强制在 BPMN 内容中插入时间戳，确保哈希变化
        String modifiedBpmnXml = bpmnXml + "\n<!-- Deployed at: " + System.currentTimeMillis() + " -->";
        InputStream inputStream = new ByteArrayInputStream(modifiedBpmnXml.getBytes(StandardCharsets.UTF_8));

        if (isNewDeployment) {
            /**
             * 发布新版本
             */
            Map<String, Object> variables = new HashMap<>();
            variables.put("approvalLevel", 3);
            variables.put("department", "财务部");
            // 发布为新流程定义
            repositoryService.createDeployment()
                    .addInputStream("process.bpmn20.xml", inputStream)
                    .deploy();
            return Result.success("新流程已发布");
        } else {
            /**
             * 更新原有版本
             */
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(processKey)
                    .latestVersion()
                    .singleResult();

            if (processDefinition == null) {
                throw new BaseException("未找到对应的流程定义");
            }

            // 删除旧部署
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);

            // 重新部署，复用原有流程定义 Key
            repositoryService.createDeployment()
                    .name(processDefinition.getDeploymentId())
                    .key(processDefinition.getKey())
                    .addInputStream("process.bpmn20.xml", inputStream)
                    .deploy();
            return Result.success("流程图已更新");
        }
    }

    /**
     * 获取xml数据
     */
    @GetMapping("/{id}")
    public Result<String> getInfo(@PathVariable("id") String id){
        try {
            // 获取流程定义的输入流
            InputStream xmlStream = repositoryService.getProcessModel(id);

            // 将输入流转换为字符串
            String xmlContent = IOUtils.toString(xmlStream, StandardCharsets.UTF_8);

            // 返回 XML 内容
            return Result.success(xmlContent);
        } catch (IOException e) {
            throw new BaseException("无法读取流程定义 XML: " + e.getMessage());
        }
    }

    /**
     * 获取当前任务节点配置项
     * @param taskQueryDTO
     * @return 节点信息及扩展属性
     */
    @GetMapping("/currentProcess")
    public Result getCurrentProcess(TaskQueryDTO taskQueryDTO) {

        String processDefinitionKey = taskQueryDTO.getProcessDefinitionKey();
        String taskId = taskQueryDTO.getTaskId();
        String historyId = taskQueryDTO.getHistoryId();

        Process process = null;
        BpmnModel bpmnModel = null;
        FlowElement currentElement = null;

        // 提取扩展属性
        Map<String, Object> result = new HashMap<>();

        if(taskId != null && !taskId.isEmpty()) {

            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();

            if (task == null) {
                throw new BaseException("未找到对应的任务");
            }

            bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            process = bpmnModel.getMainProcess();

            // ✅ 获取任务变量
            Map<String, Object> variables = taskService.getVariables(taskId);
            String formDataStr = variables.get("formData").toString();
            result.put("formData",new Gson().fromJson(formDataStr, Map.class));

            // 获取当前节点
            currentElement = process.getFlowElement(task.getTaskDefinitionKey());

        } else if(historyId != null) {
            // 先查出 historicTaskInstance
            HistoricTaskInstance historicTask = historyService.createHistoricTaskInstanceQuery()
                    .taskId(historyId)
                    .singleResult();

            if (historicTask == null) {
                return Result.error("未找到对应的历史任务");
            }

            String processDefinitionId = historicTask.getProcessDefinitionId();
            String taskDefinitionKey = historicTask.getTaskDefinitionKey();

            bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            process = bpmnModel.getMainProcess();

            List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicTask.getProcessInstanceId())
                    .variableName("formData")
                    .list();

            Map<String, Object> variables = new HashMap<>();
            for (HistoricVariableInstance variable : variableInstances) {
                result.put("formData",new Gson().fromJson((String) variable.getValue(), Map.class));
            }

            currentElement = process.getFlowElement(taskDefinitionKey);

        } else  {

            // 获取 BpmnModel
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(taskQueryDTO.getProcessDefinitionKey())
                    .latestVersion()
                    .singleResult();

            if (processDefinition == null) {
                throw new BaseException("未找到对应流程");
            }

            bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            process = bpmnModel.getMainProcess();

            currentElement = process.getInitialFlowElement();
        }

        if (currentElement == null) {
            return Result.error("未找到流程节点");
        }

        result.put("processId", currentElement.getId());
        result.put("name", currentElement.getName());

        // 提取全局和当前节点的 formOptions
        Map<String, Object> formOptionsRes = new HashMap<>();

        extractFormOptions(formOptionsRes, process.getExtensionElements(), "all");
        extractFormOptions(formOptionsRes, currentElement.getExtensionElements(), "current");

        result.put("formOption", formOptionsRes);

        return Result.success(result);
    }

    /**
     * 提取 formOptions 扩展属性
     */
    private void extractFormOptions(Map<String, Object> target,
                                    Map<String, List<ExtensionElement>> extensionElementsMap,
                                    String scopeKey) {
        if (extensionElementsMap == null || !extensionElementsMap.containsKey("formOptions")) {
            return;
        }

        for (ExtensionElement formOption : extensionElementsMap.get("formOptions")) {
            formOption.getAttributes().forEach((key, values) -> {
                if ("formOption".equals(key)) {
                    values.forEach(value -> target.put(scopeKey, value.getValue()));
                }
            });
        }
    }


    /**
     * 获取下一个节点办理人
     */
    @GetMapping("/getNextNodeAssignee")
    public Result getNextNodeAssignee(@RequestParam Map<String, Object> params) {
        String taskId = (String) params.get("taskId");
        String processDefinitionKey = (String) params.get("processDefinitionKey");

        if(taskId == null && processDefinitionKey == null) {
            throw new BaseException("taskId 和 processDefinitionKey 不能为空");
        }

        BpmnModel bpmnModel = null;
        FlowNode currentNode = null;

        if(taskId != null && !taskId.isEmpty()) {
            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();

            if (task == null) {
                throw new BaseException("未找到对应的任务");
            }

            bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());

            currentNode = (FlowNode) bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        } else {
            // 获取 BpmnModel
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey((String) params.get("processDefinitionKey"))
                    .latestVersion()
                    .singleResult();

            if (processDefinition == null) {
                throw new BaseException("未找到流程定义");
            }

            bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            // 查找当前节点
            currentNode = (FlowNode) bpmnModel.getFlowElement((String) params.get("currentNodeId"));
        }

        if (currentNode == null) {
            return Result.error( "未找到当前节点");
        }

        // 获取当前节点的所有 outgoing 流
        List<SequenceFlow> outgoingFlows = currentNode.getOutgoingFlows();
        if (outgoingFlows.isEmpty()) {
            return Result.error("当前节点没有流出路径");
        }

        // 取第一个流出路径的目标节点
        SequenceFlow firstFlow = outgoingFlows.get(0);
        FlowElement nextElement = firstFlow.getTargetFlowElement();

        Map<String, Object> nextProcess = new HashMap<>();
        nextProcess.put("processName", nextElement.getName());
        nextProcess.put("processId", nextElement.getId());

        Map<String, List<ExtensionElement>> extensionElementsMap = nextElement.getExtensionElements();

        extensionElementsMap.keySet().forEach(key -> {
            List<ExtensionElement> extensionElementList = extensionElementsMap.get(key);
            extensionElementList.forEach(extensionElement -> {
                if("assignee".equals(extensionElement.getName())) {
                    String id = extensionElement.getAttributeValue(null, "assignee");
                    if(!id.isEmpty()) {
                        SysUserVO sysUserRet = sysUserService.getById(Integer.valueOf(id));
                        nextProcess.put(extensionElement.getName(), sysUserRet);
                    }
                }
            });
        });

        if (nextElement instanceof EndEvent) {
            nextProcess.put("isEnd", true);
        }

        return Result.success(nextProcess);
    }

    /**
     * 启动流程
     */
    @PostMapping("/startProcess")
    @Transactional
    public Result startProcess(@RequestBody Map<String, Object> params) {
        Map<String, Object> variables = new HashMap<>();

        Object formData = params.get("formData");
        Map<String, Object> variableFormData;

        if(formData instanceof Map && ((Map<Object, Object>)formData).containsKey("id")) {
            variableFormData = commService.update((String) params.get("sign"), (Map<String, Object>) formData);
        } else {
            variableFormData = commService.insert((String) params.get("sign"), (Map<String, Object>) formData);
        }

        Object approveForm = params.get("approveForm");

        if(approveForm instanceof Map) {
            ((Map<?, ?>) approveForm).forEach((key, value) -> variables.put(key.toString(), value));
        }

        variableFormData.put("form_process_key", (String) params.get("sign"));
        // ✅ 将对象转成 JSON 字符串
        String variableFormDataJson = new Gson().toJson(variableFormData);
        variables.put("formData", variableFormDataJson);

        Map<String, Object> poinionMap = new HashMap<>();
        if(approveForm instanceof Map) {
            poinionMap.put("start", ((Map<?, ?>) approveForm).get("opinion"));
            variables.put("opinion", new Gson().toJson(poinionMap));
        }

        // ✅ 主动设置 initiator
        Integer userId = BaseContext.get("userId");  // 获取当前用户

        ProcessInstance test = runtimeService.startProcessInstanceByKey("test", variables);

        // ✅ 再次确认变量是否设置成功
        runtimeService.setVariable(test.getId(), "initiator", userId);

        return Result.success(test.getId());
    }

    /**
     * 查询待办任务
     * @param taskQueryDTO
     * @return
     */
    @GetMapping("/task")
    public Result todo(TaskQueryDTO taskQueryDTO) {
        Integer id = BaseContext.get("userId");
        String assignee = String.valueOf(id);
        String processDefinitionName = taskQueryDTO.getProcessDefinitionName();  // 新增参数

        List<TaskVO> records;
        long count;

        if ("todo".equals(taskQueryDTO.getType())) {
            // 查询待办任务
            List<Task> tasks = taskService.createTaskQuery()
                    .taskAssignee(assignee)
                    .orderByTaskCreateTime().desc()
                    .list();

            // 转换并过滤
            records = tasks.stream()
                    .map(task -> {
                        TaskVO vo = convertToTaskVO(task);
                        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                                .processInstanceId(task.getProcessInstanceId())
                                .singleResult();
                        if (processInstance != null) {
                            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                    .processDefinitionId(processInstance.getProcessDefinitionId())
                                    .singleResult();
                            if (processDefinition != null) {
                                vo.setProcessDefinitionName(processDefinition.getName());
                            }
                        }
                        return vo;
                    })
                    .filter(vo -> {
                        if (processDefinitionName == null || processDefinitionName.isEmpty()) {
                            return true; // 无条件放行
                        }
                        return vo.getProcessDefinitionName() != null &&
                                vo.getProcessDefinitionName().contains(processDefinitionName);
                    })
                    .skip((taskQueryDTO.getPage() - 1) * taskQueryDTO.getPageSize())
                    .limit(taskQueryDTO.getPageSize())
                    .toList();

            count = records.size(); // 如果要精确分页，应先查出全部再过滤

        } else if ("done".equals(taskQueryDTO.getType())) {
            // 查询已办任务
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(assignee)
                    .finished()
                    .orderByHistoricTaskInstanceEndTime().desc()
                    .list();

            // 转换并过滤
            records = historicTaskInstances.stream()
                    .map(instance -> {
                        TaskVO vo = convertToTaskVOFromHistory(instance);
                        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                                .processInstanceId(instance.getProcessInstanceId())
                                .singleResult();
                        if (historicProcessInstance != null) {
                            vo.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
                        }
                        return vo;
                    })
                    .filter(vo -> {
                        if (processDefinitionName == null || processDefinitionName.isEmpty()) {
                            return true;
                        }
                        return vo.getProcessDefinitionName() != null &&
                                vo.getProcessDefinitionName().contains(processDefinitionName);
                    })
                    .skip((taskQueryDTO.getPage() - 1) * taskQueryDTO.getPageSize())
                    .limit(taskQueryDTO.getPageSize())
                    .toList();

            count = records.size();

        } else {
            return Result.error("类型错误，支持: todo / done");
        }

        return Result.success(new PageResult(count, records));
    }

    @PostMapping("/goProcess")
    @Transactional
    public Result goProcess(@RequestBody Map<String, Object> params) {
        String taskId = params.get("taskId").toString();

        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> variableFormData;

        Object formData = params.get("formData");
        if(formData instanceof Map && ((Map<Object, Object>)formData).containsKey("id")) {
            variableFormData = commService.update((String) params.get("sign"), (Map<String, Object>) formData);
        } else {
            variableFormData = commService.insert((String) params.get("sign"), (Map<String, Object>) formData);
        }

        Object approveForm = params.get("approveForm");

        if(approveForm instanceof Map) {
            ((Map<?, ?>) approveForm).forEach((key, value) -> variables.put(key.toString(), value));
        }
        Map<String, Object> allVariables = taskService.getVariables(taskId);
        Map<String, Object> opinion = new Gson().fromJson((String) allVariables.get("opinion"), Map.class);
        opinion.put(taskId, ((Map<?, ?>) approveForm).get("opinion"));
        variables.put("opinion", new Gson().toJson(opinion));
        variableFormData.put("form_process_key", (String) params.get("sign"));
        // ✅ 将对象转成 JSON 字符串
        String variableFormDataJson = new Gson().toJson(variableFormData);
        variables.put("formData", variableFormDataJson);

        taskService.complete(taskId, variables);

        return Result.success(true);
    }


    /**
     * 查询流程实例的完整运行轨迹
     *
     * @param processInstanceId 流程实例ID
     * @return 历史轨迹信息
     */
    @GetMapping("/history")
    public Result getProcessHistory(@RequestParam String processInstanceId) {
        // 获取历史任务列表
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime()
                .asc()
                .list();

        List<Map<String, Object>> taskList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 获取发起人
        List<HistoricVariableInstance> startRowVariables = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .variableName("initiator")
                .list();


        List<SysUserVO> allUserList = sysUserService.getAll();
        SysUserVO targetUser = null;

        // ✅ 获取该任务的所有变量
        List<HistoricVariableInstance> opinionVarList = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .variableName("opinion")
                .list();

        if(!startRowVariables.isEmpty()) {
            HistoricVariableInstance historicVariableInstance = startRowVariables.get(0);
            String formatCreateTime = historicVariableInstance.getCreateTime() != null ? sdf.format(historicVariableInstance.getCreateTime()) : null;
            Map<String, Object> taskInfo = new HashMap<>();
            taskInfo.put("taskName", "发起");
            taskInfo.put("assignee",historicVariableInstance.getValue());
            taskInfo.put("startTime", formatCreateTime);
            taskInfo.put("endTime", formatCreateTime);
            taskInfo.put("duration", "/");
            taskInfo.put("status", "已完成");
            taskInfo.put("taskId", "start");
            List<SysUserVO> list = allUserList.stream()
                    .filter(user -> user.getId().equals((Integer) historicVariableInstance.getValue()))
                    .toList();

            if(!list.isEmpty()) {
                targetUser = list.get(0);
                taskInfo.put("assigneeName", targetUser.getNickname());
            }
            taskList.add(taskInfo);
        }

        for (HistoricTaskInstance task : historicTasks) {
            Map<String, Object> taskInfo = new HashMap<>();
            String formatCreateTime = task.getCreateTime()!= null ? sdf.format(task.getCreateTime()) : null;
            String formatEndTime = task.getEndTime()!= null ? sdf.format(task.getEndTime()) : null;
            taskInfo.put("taskId", task.getId());
            taskInfo.put("taskName", task.getName());
            taskInfo.put("assignee", task.getAssignee());
            taskInfo.put("startTime", formatCreateTime);
            taskInfo.put("endTime", formatEndTime);
            taskInfo.put("duration", formatDuration(task.getDurationInMillis()));
            taskInfo.put("status", task.getEndTime() == null ? "进行中" : "已完成");

            List<SysUserVO> list = allUserList.stream()
                    .filter(user -> user.getId().equals(Integer.valueOf(task.getAssignee())))
                    .toList();

            if(!list.isEmpty()) {
                targetUser = list.get(0);
                taskInfo.put("assigneeName", targetUser.getNickname());
            }

            taskList.add(taskInfo);
        }

        // 获取流程实例信息
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (historicProcessInstance == null) {
            return Result.error("未找到对应的流程实例");
        }

        HistoricVariableInstance historicVariableInstance = opinionVarList.get(0);
        taskList.forEach(item ->{
            Map map = new Gson().fromJson((String) historicVariableInstance.getValue(), Map.class);
            item.put("opinion", map.get(item.get("taskId")));
        });

        Map<String, Object> result = new HashMap<>();
        result.put("processInstanceId", processInstanceId);
        result.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
        result.put("processDefinitionKey", historicProcessInstance.getProcessDefinitionKey());
        result.put("processDefinitionName", historicProcessInstance.getProcessDefinitionName());
        result.put("startTime", historicProcessInstance.getStartTime());
        result.put("endTime", historicProcessInstance.getEndTime());
        result.put("status", historicProcessInstance.getEndTime() == null ? "运行中" : "已完成");
        result.put("tasks", taskList);

        return Result.success(result);
    }


    private TaskVO convertToTaskVO(Task task) {
        return TaskVO.builder()
                .id(task.getId())
                .key(task.getTaskDefinitionKey())
                .name(task.getName())
                .processInstanceId(task.getProcessInstanceId())
                .executionId(task.getExecutionId())
                .processDefinitionId(task.getProcessDefinitionId())
                .assignee(task.getAssignee())
                .createTime(task.getCreateTime())
                .build();
    }


    private TaskVO convertToTaskVOFromHistory(HistoricTaskInstance task) {
        return TaskVO.builder()
                .id(task.getId())
                .key(task.getTaskDefinitionKey())
                .name(task.getName())
                .processInstanceId(task.getProcessInstanceId())
                .executionId(task.getExecutionId())
                .processDefinitionId(task.getProcessDefinitionId())
                .assignee(task.getAssignee())
                .createTime(task.getStartTime())
                .build();
    }
}
