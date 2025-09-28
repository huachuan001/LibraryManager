package com.example.librarymanager.controller;

import com.example.librarymanager.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello控制器类
 * 
 * 处理Web页面的请求，负责渲染HTML页面。
 * 使用@Controller注解标记为Spring MVC控制器，用于处理视图渲染。
 * 
 * 在实际的图书馆管理系统中，这里会包含更多的控制器方法，如：
 * - 图书列表页面
 * - 用户管理页面
 * - 借阅记录页面
 * 
 * @Controller注解与@RestController的区别：
 * - @Controller: 用于返回视图（HTML页面）
 * - @RestController: 用于返回JSON数据（REST API）
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
@Controller
@RequestMapping("/")
public class HelloController {

    /**
     * Hello服务依赖注入
     * 
     * 使用@Autowired注解自动注入HelloService实例。
     * Spring容器会自动创建并注入这个依赖。
     */
    @Autowired
    private HelloService helloService;

    /**
     * 处理首页请求
     * 
     * 当用户访问根路径"/"时，会调用此方法。
     * 该方法会：
     * 1. 调用HelloService获取问候消息
     * 2. 将消息添加到Model中，供Thymeleaf模板使用
     * 3. 返回视图名称"hello"，对应templates/hello.html
     * 
     * @param model Spring MVC的Model对象，用于向视图传递数据
     * @return 视图名称，对应templates目录下的hello.html模板
     */
    @GetMapping("/")
    public String helloPage(Model model) {
        // 从服务层获取问候消息
        String message = helloService.getHelloMessage();
        
        // 将消息添加到模型中，供模板使用
        model.addAttribute("message", message);
        
        // 返回视图名称，Spring会查找templates/hello.html
        return "hello";
    }
}
