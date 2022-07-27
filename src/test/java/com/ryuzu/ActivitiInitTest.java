package com.ryuzu;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Priority;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryuzu
 * @date 2022/7/26 9:14
 */
@SpringBootTest
public class ActivitiInitTest {

    @Resource
    private ProcessEngine processEngine;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private HistoryService historyService;

    @Test
    public void test01(){
        System.out.println(processEngine);
    }

    @Test
    public void test02(){
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("vacationProcess").list();
        for (Task task : taskList) {
            System.out.println(task.getId());
            System.out.println(task.getName());
        }

    }

    /**
     * 查询所有定义流程并删除
     */
    @Test
    public void test03(){

        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey("vacationProcess").list();
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义id: " + processDefinition.getId());
            System.out.println("流程定义名称: " + processDefinition.getName());
            System.out.println("流程定义key: " + processDefinition.getKey());
            System.out.println("正在删除流程....");
            // true 表示强制删除,默认值为false
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        }

    }

    /**
     * 查询用户的任务
     */
    @Test
    public void test04(){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("校长").list();
        for (Task task : taskList) {
            System.out.println("任务名称: "+task.getName());
            System.out.println("任务办理人: "+task.getAssignee());
            Map<String, Object> map = new HashMap<>(2);
            map.put("msg", "yes");

            // 完成全部任务
            System.out.println("正在完成任务.....");
            taskService.complete(task.getId(),map);

        }

    }

}
