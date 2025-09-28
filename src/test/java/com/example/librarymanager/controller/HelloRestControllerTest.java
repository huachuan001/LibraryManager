package com.example.librarymanager.controller;

import com.example.librarymanager.service.HelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.Map;

/**
 * HelloRestController测试类
 * 
 * 使用TestNG框架测试HelloRestController的所有方法，确保100%的代码覆盖率。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
public class HelloRestControllerTest {

    private HelloRestController helloRestController;
    private HelloService helloService;

    /**
     * 测试前的准备工作
     * 在每个测试方法执行前都会调用此方法
     */
    @BeforeMethod
    public void setUp() {
        helloService = new HelloService();
        helloRestController = new HelloRestController();
        // 使用反射设置私有字段
        try {
            java.lang.reflect.Field field = HelloRestController.class.getDeclaredField("helloService");
            field.setAccessible(true);
            field.set(helloRestController, helloService);
        } catch (Exception e) {
            throw new RuntimeException("无法设置helloService字段", e);
        }
    }

    /**
     * 测试helloApi方法
     * 
     * 验证helloApi方法能正确处理请求并返回正确的响应
     */
    @Test
    public void testHelloApi() {
        // 执行测试
        ResponseEntity<Map<String, String>> response = helloRestController.helloApi();

        // 验证结果
        assertNotNull(response, "响应不应该为null");
        assertEquals(response.getStatusCode(), HttpStatus.OK, "HTTP状态码应该是200 OK");
        
        Map<String, String> responseBody = response.getBody();
        assertNotNull(responseBody, "响应体不应该为null");
        assertEquals(responseBody.get("message"), "Hello World!", "响应体中的消息应该正确");
    }

    /**
     * 测试helloApi方法的响应结构
     * 
     * 验证helloApi方法返回的响应结构正确
     */
    @Test
    public void testHelloApiResponseStructure() {
        // 执行测试
        ResponseEntity<Map<String, String>> response = helloRestController.helloApi();

        // 验证响应结构
        assertNotNull(response, "响应不应该为null");
        assertNotNull(response.getBody(), "响应体不应该为null");
        assertTrue(response.getBody() instanceof Map, "响应体应该是Map类型");
        assertTrue(response.getBody().containsKey("message"), "响应体应该包含'message'键");
        assertEquals(response.getBody().size(), 1, "响应体应该只包含一个键值对");
    }

    /**
     * 测试helloApi方法的HTTP状态码
     * 
     * 验证helloApi方法返回正确的HTTP状态码
     */
    @Test
    public void testHelloApiHttpStatusCode() {
        // 执行测试
        ResponseEntity<Map<String, String>> response = helloRestController.helloApi();

        // 验证HTTP状态码
        assertEquals(response.getStatusCode(), HttpStatus.OK, "HTTP状态码应该是200 OK");
        assertTrue(response.getStatusCode().is2xxSuccessful(), "HTTP状态码应该在2xx范围内");
    }

    /**
     * 测试helloApi方法的多次调用
     * 
     * 验证helloApi方法多次调用的行为
     */
    @Test
    public void testHelloApiMultipleCalls() {
        // 执行多次测试
        ResponseEntity<Map<String, String>> response1 = helloRestController.helloApi();
        ResponseEntity<Map<String, String>> response2 = helloRestController.helloApi();
        ResponseEntity<Map<String, String>> response3 = helloRestController.helloApi();

        // 验证结果
        assertEquals(response1.getStatusCode(), HttpStatus.OK, "第一次调用应该返回200 OK");
        assertEquals(response2.getStatusCode(), HttpStatus.OK, "第二次调用应该返回200 OK");
        assertEquals(response3.getStatusCode(), HttpStatus.OK, "第三次调用应该返回200 OK");
        
        // 验证所有响应都包含正确的消息
        assertEquals(response1.getBody().get("message"), "Hello World!", "第一次调用的消息应该正确");
        assertEquals(response2.getBody().get("message"), "Hello World!", "第二次调用的消息应该正确");
        assertEquals(response3.getBody().get("message"), "Hello World!", "第三次调用的消息应该正确");
    }
}
