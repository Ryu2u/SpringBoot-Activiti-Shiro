# SpringBoot-Activiti-Shiro
这是一个springboot+activiti+shiro的项目demo,用于快速创建项目

同时使用了swagger2进行接口的管理和测试

### 注意:
1.如果初始化项目创建activiti表结构时,只创建17张表(一共需要创建15张表)
需要将activiti配置放在spring.datasource下.
2. 如果报错Unknown column ‘VERSION_‘ in ‘field list‘,
   1. 如果你的依赖是7.1.0.M5的话,需要手动将缺少的字段添加上
   2. 或者修改activiti版本为7.0.0.Beta2的版本



