package com.ryuzu.activiti.controller;

import com.ryuzu.activiti.entity.Vacation;
import com.ryuzu.common.result.ResultMsg;
import com.ryuzu.manage.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;

import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.collections.map.FixedSizeMap;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryuzu
 * @date 2022/7/26 15:54
 */
@RestController
@Slf4j
@Api(tags = "ActivitiController")
@RequestMapping("/act")
public class ActivitiController {

    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private HistoryService historyService;

    @GetMapping("/deploy")
    @ApiOperation("部署流程")
    public ResultMsg deployActiviti() {
        log.info("正在部署流程....");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/vacationProcess.bpmn20.xml")
                .addClasspathResource("process/vacationProcess.png")
                .name("请假申请")
                .deploy();
        log.info("流程部署id为: {}", deployment.getId());
        log.info("流程部署名称为: {}", deployment.getName());
        return ResultMsg.ok();
    }

    @PostMapping("/task")
    @ApiOperation("创建流程实例")
    public ResultMsg instanceActiviti(@RequestBody Vacation vacation) {
        log.info("正在创建流程实例....");
        String definedKey = vacation.getKey();
        String username = vacation.getUsername();
        String type = vacation.getType();
        Integer days = vacation.getDays();
        String content = vacation.getContent();
        Map<String, Object> attribute = new HashMap<>(16);
        attribute.put("username", username);
        attribute.put("type", type);
        attribute.put("assignee0", "刘班长");
        attribute.put("assignee1", "赵老师");
        attribute.put("assignee2", "李主任");
        attribute.put("days", days);
        attribute.put("content", content);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Authentication.setAuthenticatedUserId(user.getUsername());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(definedKey, attribute);
        log.info("流程定义Id: {}", processInstance.getProcessDefinitionId());
        log.info("流程定义Key: {}", processInstance.getProcessDefinitionKey());
        log.info("流程实例id: {}", processInstance.getId());
        return ResultMsg.ok();
    }

    @PostMapping("/delete/all")
    @ApiOperation("删除所有流程实例")
    public ResultMsg deleteAllProcessDefinition() {
        log.info("正在删除所有流程实例......");
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationProcess").list();
        for (ProcessDefinition processDefinition : definitionList) {
            log.info("流程定义id: {}", processDefinition.getId());
            log.info("流程定义名称: {}", processDefinition.getName());
            log.info("流程部署id: {}", processDefinition.getDeploymentId());
            log.info("正在删除此流程定义......");
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        }
        return ResultMsg.ok();
    }

    @PostMapping("/delete/task")
    @ApiOperation("删除对应的任务")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId",value = "任务id,用户查询实例id,并删除",dataType = "String",required = true),
            @ApiImplicitParam(name = "deleteReason",value = "删除任务原因",dataType = "String",required = true)
    })
    public ResultMsg deleteTaskByTaskId(@RequestParam("taskId") String taskId,@RequestParam("deleteReason") String deleteReason) {
        log.info("正在删除对应的任务实例,taskId = {},deleteReason = {}", taskId, deleteReason);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        log.info("查询出对应的task为: {}", task.toString());
        String processInstanceId = task.getProcessInstanceId();
        log.info("查询出对应的流程实例id: {}", processInstanceId);
        log.info("正在删除......");
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
        return ResultMsg.ok();
    }
}
