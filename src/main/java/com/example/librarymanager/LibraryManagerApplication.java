package com.example.librarymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Library Manager 应用程序主入口类
 * 
 * 这是一个基于Spring Boot的图书馆管理系统应用程序。
 * 使用@SpringBootApplication注解，它包含了以下功能：
 * - @Configuration: 标记为配置类
 * - @EnableAutoConfiguration: 启用Spring Boot自动配置
 * - @ComponentScan: 启用组件扫描
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication
public class LibraryManagerApplication {

    /**
     * 应用程序主入口方法
     * 
     * 启动Spring Boot应用程序，初始化所有配置的组件和依赖。
     * 应用程序将在配置的端口（默认8081）上启动。
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerApplication.class, args);
    }

}
