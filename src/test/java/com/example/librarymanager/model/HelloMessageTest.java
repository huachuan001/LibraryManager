package com.example.librarymanager.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * HelloMessage模型测试类
 * 
 * 使用TestNG框架测试HelloMessage模型的所有方法和构造函数，确保100%的代码覆盖率。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
public class HelloMessageTest {

    private HelloMessage helloMessage;

    /**
     * 测试前的准备工作
     * 在每个测试方法执行前都会调用此方法
     */
    @BeforeMethod
    public void setUp() {
        helloMessage = new HelloMessage();
    }

    /**
     * 测试默认构造函数
     * 
     * 验证默认构造函数能正确创建对象
     */
    @Test
    public void testDefaultConstructor() {
        // 执行测试
        HelloMessage message = new HelloMessage();
        
        // 验证结果
        assertNotNull(message, "默认构造函数应该创建非null对象");
        assertNull(message.getMessage(), "默认构造函数创建的对象消息应该为null");
    }

    /**
     * 测试带参数的构造函数
     * 
     * 验证带参数的构造函数能正确创建对象并设置消息
     */
    @Test
    public void testParameterizedConstructor() {
        // 测试数据
        String testMessage = "Test Message";
        
        // 执行测试
        HelloMessage message = new HelloMessage(testMessage);
        
        // 验证结果
        assertNotNull(message, "带参数构造函数应该创建非null对象");
        assertEquals(message.getMessage(), testMessage, "构造函数应该正确设置消息");
    }

    /**
     * 测试带参数构造函数使用null值
     * 
     * 验证带参数的构造函数能正确处理null值
     */
    @Test
    public void testParameterizedConstructorWithNull() {
        // 执行测试
        HelloMessage message = new HelloMessage(null);
        
        // 验证结果
        assertNotNull(message, "带null参数构造函数应该创建非null对象");
        assertNull(message.getMessage(), "构造函数应该正确设置null消息");
    }

    /**
     * 测试getMessage方法
     * 
     * 验证getMessage方法能正确返回消息
     */
    @Test
    public void testGetMessage() {
        // 准备测试数据
        String testMessage = "Hello Test";
        helloMessage.setMessage(testMessage);
        
        // 执行测试
        String result = helloMessage.getMessage();
        
        // 验证结果
        assertEquals(result, testMessage, "getMessage应该返回设置的消息");
    }

    /**
     * 测试getMessage方法返回null
     * 
     * 验证getMessage方法在消息为null时返回null
     */
    @Test
    public void testGetMessageWithNull() {
        // 确保消息为null
        helloMessage.setMessage(null);
        
        // 执行测试
        String result = helloMessage.getMessage();
        
        // 验证结果
        assertNull(result, "getMessage在消息为null时应该返回null");
    }

    /**
     * 测试setMessage方法
     * 
     * 验证setMessage方法能正确设置消息
     */
    @Test
    public void testSetMessage() {
        // 测试数据
        String testMessage = "Set Message Test";
        
        // 执行测试
        helloMessage.setMessage(testMessage);
        
        // 验证结果
        assertEquals(helloMessage.getMessage(), testMessage, "setMessage应该正确设置消息");
    }

    /**
     * 测试setMessage方法设置null值
     * 
     * 验证setMessage方法能正确处理null值
     */
    @Test
    public void testSetMessageWithNull() {
        // 执行测试
        helloMessage.setMessage(null);
        
        // 验证结果
        assertNull(helloMessage.getMessage(), "setMessage应该能设置null值");
    }

    /**
     * 测试setMessage方法设置空字符串
     * 
     * 验证setMessage方法能正确处理空字符串
     */
    @Test
    public void testSetMessageWithEmptyString() {
        // 测试数据
        String emptyMessage = "";
        
        // 执行测试
        helloMessage.setMessage(emptyMessage);
        
        // 验证结果
        assertEquals(helloMessage.getMessage(), emptyMessage, "setMessage应该能设置空字符串");
    }

    /**
     * 测试setMessage和getMessage方法的组合使用
     * 
     * 验证setter和getter方法的组合使用
     */
    @Test
    public void testSetAndGetMessageCombination() {
        // 测试数据
        String[] testMessages = {"Message 1", "Message 2", "Message 3", null, ""};
        
        for (String testMessage : testMessages) {
            // 执行测试
            helloMessage.setMessage(testMessage);
            String result = helloMessage.getMessage();
            
            // 验证结果
            assertEquals(result, testMessage, "setMessage和getMessage应该保持一致");
        }
    }

    /**
     * 测试对象的独立性
     * 
     * 验证不同HelloMessage对象之间的独立性
     */
    @Test
    public void testObjectIndependence() {
        // 创建两个不同的对象
        HelloMessage message1 = new HelloMessage("Message 1");
        HelloMessage message2 = new HelloMessage("Message 2");
        
        // 验证对象独立性
        assertNotEquals(message1.getMessage(), message2.getMessage(), 
                       "不同对象的消息应该不同");
        assertNotSame(message1, message2, "不同对象应该是不同的实例");
    }
}
