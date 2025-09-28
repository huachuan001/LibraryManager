package com.example.librarymanager.controller;

import com.example.librarymanager.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello REST API控制器类
 * 
 * 提供RESTful API接口，返回JSON格式的数据。
 * 使用@RestController注解，相当于@Controller + @ResponseBody的组合。
 * 
 * 在实际的图书馆管理系统中，这里会提供更多的API接口，如：
 * - GET /api/books - 获取图书列表
 * - POST /api/books - 添加新图书
 * - GET /api/users - 获取用户列表
 * - POST /api/borrow - 借阅图书
 * - GET /api/records - 获取借阅记录
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
@RestController
@RequestMapping("/api")
public class HelloRestController {

    /**
     * Hello服务依赖注入
     * 
     * 使用@Autowired注解自动注入HelloService实例。
     * Spring容器会自动创建并注入这个依赖。
     */
    @Autowired
    private HelloService helloService;

    /**
     * 获取问候消息的REST API接口
     * 
     * 提供GET请求的API接口，返回JSON格式的问候消息。
     * 访问路径：GET /api/hello
     * 
     * 返回格式：
     * {
     *   "message": "Hello World!"
     * }
     * 
     * @return ResponseEntity<Map<String, String>> 包含问候消息的响应实体
     */
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> helloApi() {
        // 从服务层获取问候消息
        String message = helloService.getHelloMessage();
        
        // 创建响应数据
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        
        // 返回HTTP 200状态码和响应数据
        return ResponseEntity.ok(response);
    }
}
