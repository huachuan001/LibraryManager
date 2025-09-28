package com.example.librarymanager.service;

import org.springframework.stereotype.Service;

/**
 * Hello服务类
 * 
 * 提供基本的问候消息服务。这是一个示例服务类，展示了Spring Boot中
 * 服务层的设计模式。在实际的图书馆管理系统中，这里会包含业务逻辑。
 * 
 * @Service注解标记这是一个Spring服务组件，会被自动扫描并注册到Spring容器中。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
@Service
public class HelloService {

    /**
     * 获取问候消息
     * 
     * 返回一个简单的问候消息。在实际应用中，这个方法可能会：
     * - 从数据库获取配置信息
     * - 处理复杂的业务逻辑
     * - 调用其他服务或外部API
     * 
     * @return 问候消息字符串
     */
    public String getHelloMessage() {
        return "Hello World!";
    }
}
