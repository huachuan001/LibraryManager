package com.example.librarymanager.controller;

import com.example.librarymanager.service.HelloService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.HashMap;
import java.util.Map;

/**
 * HelloController测试类
 * 
 * 使用TestNG框架测试HelloController的所有方法，确保100%的代码覆盖率。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
public class HelloControllerTest {

    private HelloController helloController;
    private HelloService helloService;

    /**
     * 测试前的准备工作
     * 在每个测试方法执行前都会调用此方法
     */
    @BeforeMethod
    public void setUp() {
        helloService = new HelloService();
        helloController = new HelloController();
        // 使用反射设置私有字段
        try {
            java.lang.reflect.Field field = HelloController.class.getDeclaredField("helloService");
            field.setAccessible(true);
            field.set(helloController, helloService);
        } catch (Exception e) {
            throw new RuntimeException("无法设置helloService字段", e);
        }
    }

    /**
     * 测试helloPage方法
     * 
     * 验证helloPage方法能正确处理请求并返回正确的视图名称
     */
    @Test
    public void testHelloPage() {
        // 创建模拟的Model对象
        MockModel model = new MockModel();
        
        // 执行测试
        String result = helloController.helloPage(model);

        // 验证结果
        assertEquals(result, "hello", "应该返回'hello'视图名称");
        assertTrue(model.hasAttribute("message"), "Model应该包含message属性");
        assertEquals(model.getAttribute("message"), "Hello World!", "Model中的消息应该是'Hello World!'");
    }

    /**
     * 测试helloPage方法的返回值
     * 
     * 验证helloPage方法始终返回正确的视图名称
     */
    @Test
    public void testHelloPageReturnValue() {
        // 创建模拟的Model对象
        MockModel model = new MockModel();
        
        // 执行测试
        String result = helloController.helloPage(model);

        // 验证返回值
        assertNotNull(result, "返回值不应该为null");
        assertEquals(result, "hello", "返回值应该是'hello'");
        assertTrue(result.equals("hello"), "返回值应该等于'hello'");
    }

    /**
     * 测试helloPage方法的多次调用
     * 
     * 验证helloPage方法多次调用的行为
     */
    @Test
    public void testHelloPageMultipleCalls() {
        // 执行多次测试
        MockModel model1 = new MockModel();
        MockModel model2 = new MockModel();
        MockModel model3 = new MockModel();
        
        String result1 = helloController.helloPage(model1);
        String result2 = helloController.helloPage(model2);
        String result3 = helloController.helloPage(model3);

        // 验证结果
        assertEquals(result1, "hello", "第一次调用应该返回'hello'");
        assertEquals(result2, "hello", "第二次调用应该返回'hello'");
        assertEquals(result3, "hello", "第三次调用应该返回'hello'");
        
        // 验证所有Model都包含正确的消息
        assertEquals(model1.getAttribute("message"), "Hello World!", "第一次调用的消息应该正确");
        assertEquals(model2.getAttribute("message"), "Hello World!", "第二次调用的消息应该正确");
        assertEquals(model3.getAttribute("message"), "Hello World!", "第三次调用的消息应该正确");
    }

    /**
     * 简单的MockModel类用于测试
     */
    private static class MockModel implements org.springframework.ui.Model {
        private Map<String, Object> attributes = new HashMap<>();

        @Override
        public org.springframework.ui.Model addAttribute(String attributeName, Object attributeValue) {
            attributes.put(attributeName, attributeValue);
            return this;
        }

        @Override
        public org.springframework.ui.Model addAttribute(Object attributeValue) {
            return this;
        }

        @Override
        public org.springframework.ui.Model addAllAttributes(java.util.Collection<?> attributeValues) {
            return this;
        }

        @Override
        public org.springframework.ui.Model addAllAttributes(java.util.Map<String, ?> attributes) {
            this.attributes.putAll(attributes);
            return this;
        }

        @Override
        public org.springframework.ui.Model mergeAttributes(java.util.Map<String, ?> attributes) {
            this.attributes.putAll(attributes);
            return this;
        }

        @Override
        public boolean containsAttribute(String attributeName) {
            return attributes.containsKey(attributeName);
        }

        @Override
        public Object getAttribute(String attributeName) {
            return attributes.get(attributeName);
        }

        @Override
        public java.util.Map<String, Object> asMap() {
            return new HashMap<>(attributes);
        }

        public boolean hasAttribute(String attributeName) {
            return attributes.containsKey(attributeName);
        }
    }
}
