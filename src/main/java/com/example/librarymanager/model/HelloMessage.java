package com.example.librarymanager.model;

/**
 * Hello消息模型类
 * 
 * 这是一个简单的数据传输对象（DTO），用于封装问候消息。
 * 在实际的图书馆管理系统中，这里会包含更复杂的实体类，如：
 * - Book（图书）
 * - User（用户）
 * - BorrowRecord（借阅记录）
 * 
 * 该类遵循JavaBean规范，提供标准的getter和setter方法。
 * 
 * @author Library Manager Team
 * @version 1.0.0
 * @since 2024
 */
public class HelloMessage {
    
    /**
     * 消息内容
     */
    private String message;

    /**
     * 默认构造函数
     * 
     * 用于创建空的HelloMessage对象，通常用于JSON反序列化。
     */
    public HelloMessage() {}

    /**
     * 带参数的构造函数
     * 
     * @param message 要设置的消息内容
     */
    public HelloMessage(String message) {
        this.message = message;
    }

    /**
     * 获取消息内容
     * 
     * @return 当前消息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息内容
     * 
     * @param message 要设置的消息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
