# ===========================================
# Library Manager Docker 极简构建文件
# ===========================================

# 使用OpenJDK 17作为基础镜像
FROM openjdk:17-slim

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY . .

# 给gradlew执行权限
RUN chmod +x ./gradlew

# 暴露端口
EXPOSE 8081

# 启动应用（使用Gradle运行）
CMD ["./gradlew", "run"]
