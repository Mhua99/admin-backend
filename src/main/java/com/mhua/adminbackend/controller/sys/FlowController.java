package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.pojo.dto.ProcessDefinitionDTO;
import com.mhua.adminbackend.pojo.entity.ProcessModal;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@RestController
@RequestMapping("/system/flow")
@Tag(name = "系统流程管理")
public class FlowController {

    @Autowired
    private RepositoryService repositoryService;

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
    public Result<PageResult<ProcessModal>> list(ProcessDefinitionDTO processDefinitionDTO) {
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

        // 总数
        long total = countProcessDefinitionQuery.count();

        List<ProcessModal> result = new ArrayList<>();
        for (ProcessDefinition pd : list) {
            ProcessModal processModal = new ProcessModal();
            processModal.setId(pd.getId());
            processModal.setName(pd.getName());
            processModal.setProcessDefinitionKey(pd.getKey());
            processModal.setVersion(pd.getVersion());
            processModal.setDeploymentId(pd.getDeploymentId());
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
        String key = body.get("key");
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
        String processKey = (String) body.get("processDefinitionKey");
        Boolean isNewDeployment = (Boolean) body.getOrDefault("isNew", false);

        if (bpmnXml == null || bpmnXml.isEmpty()) {
            throw new BaseException("BPMN内容不能为空");
        }

        // 强制在 BPMN 内容中插入时间戳，确保哈希变化
        String modifiedBpmnXml = bpmnXml + "\n<!-- Deployed at: " + System.currentTimeMillis() + " -->";
        InputStream inputStream = new ByteArrayInputStream(modifiedBpmnXml.getBytes(StandardCharsets.UTF_8));

        if (isNewDeployment) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("approvalLevel", 3);
            variables.put("department", "财务部");
            // 发布为新流程定义
            repositoryService.createDeployment()
                    .name("Deployment-" + System.currentTimeMillis())
                    .key(UUID.randomUUID().toString())  // 使用唯一 Key 确保是新流程
                    .addInputStream("process.bpmn20.xml", inputStream)
                    .deploy();
            return Result.success("新流程已发布");
        } else {
            // 查找已有流程定义
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
            return Result.success("流程图已更新（未新增版本）");
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
}
