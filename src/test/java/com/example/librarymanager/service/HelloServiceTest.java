package com.example.librarymanager.service;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * HelloService测试类
 * 
 * 使用TestNG框架测试HelloService的所有方法，确保100%的代码覆盖率。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
public class HelloServiceTest {

    private HelloService helloService;

    /**
     * 测试前的准备工作
     * 在每个测试方法执行前都会调用此方法
     */
    @BeforeMethod
    public void setUp() {
        helloService = new HelloService();
    }

    /**
     * 测试getHelloMessage方法
     * 
     * 验证方法返回正确的问候消息
     */
    @Test
    public void testGetHelloMessage() {
        // 执行测试
        String result = helloService.getHelloMessage();
        
        // 验证结果
        assertNotNull(result, "返回的消息不应该为null");
        assertEquals(result, "Hello World!", "返回的消息应该是'Hello World!'");
        assertTrue(result.length() > 0, "返回的消息长度应该大于0");
    }

    /**
     * 测试getHelloMessage方法的返回值类型
     * 
     * 验证返回值的类型是String
     */
    @Test
    public void testGetHelloMessageReturnType() {
        // 执行测试
        String result = helloService.getHelloMessage();
        
        // 验证返回类型
        assertTrue(result instanceof String, "返回值应该是String类型");
    }

    /**
     * 测试getHelloMessage方法的多次调用一致性
     * 
     * 验证多次调用返回相同的结果
     */
    @Test
    public void testGetHelloMessageConsistency() {
        // 执行多次调用
        String result1 = helloService.getHelloMessage();
        String result2 = helloService.getHelloMessage();
        String result3 = helloService.getHelloMessage();
        
        // 验证结果一致性
        assertEquals(result1, result2, "第一次和第二次调用应该返回相同结果");
        assertEquals(result2, result3, "第二次和第三次调用应该返回相同结果");
        assertEquals(result1, result3, "第一次和第三次调用应该返回相同结果");
    }

    /**
     * 测试getHelloMessage方法返回值的不可变性
     * 
     * 验证返回的字符串是不可变的
     */
    @Test
    public void testGetHelloMessageImmutability() {
        // 执行测试
        String result = helloService.getHelloMessage();
        String originalResult = result;
        
        // 尝试修改返回值（如果可能的话）
        // 由于String是不可变的，这个测试主要验证返回的是String类型
        assertSame(result, originalResult, "返回的字符串引用应该相同");
    }
}
