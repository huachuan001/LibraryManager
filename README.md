# Library Manager

一个基于Spring Boot的图书馆管理系统项目，用于学习和实践现代Java Web开发技术。

## 📋 项目概述

Library Manager是一个使用Spring Boot框架构建的Web应用程序，展示了现代Java企业级应用开发的最佳实践。项目采用MVC架构模式，提供了Web页面和REST API两种访问方式。

## 🏗️ 技术架构

### 核心技术栈
- **Java 17** - 编程语言
- **Spring Boot 3.1.5** - 应用框架
- **Spring MVC** - Web框架
- **Thymeleaf** - 模板引擎
- **Gradle** - 构建工具

### 架构模式
- **MVC模式** - Model-View-Controller分层架构
- **依赖注入** - Spring IoC容器管理
- **RESTful API** - REST风格接口设计

## 📁 项目结构

```
LibraryManager/
├── src/main/java/com/example/librarymanager/
│   ├── LibraryManagerApplication.java    # 应用程序主入口
│   ├── controller/                       # 控制器层
│   │   ├── HelloController.java         # Web页面控制器
│   │   └── HelloRestController.java     # REST API控制器
│   ├── service/                         # 服务层
│   │   └── HelloService.java           # 业务逻辑服务
│   └── model/                          # 模型层
│       └── HelloMessage.java           # 数据传输对象
├── src/main/resources/
│   ├── application.properties          # 应用配置
│   └── templates/
│       └── hello.html                  # Thymeleaf模板
├── build.gradle                        # Gradle构建配置
└── README.md                          # 项目文档
```

## 🚀 快速开始

### 环境要求

#### 方式一：Docker部署（推荐）
- Docker 20.0 或更高版本
- Docker Compose 2.0 或更高版本

#### 方式二：本地开发
- Java 17 或更高版本
- Gradle 7.0 或更高版本

### 运行步骤

#### 🐳 Docker部署（推荐）

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd LibraryManager
   ```

2. **使用Docker Compose启动（推荐）**
   ```bash
   # 构建并启动服务
   docker-compose up -d
   
   # 查看服务状态
   docker-compose ps
   
   # 查看日志
   docker-compose logs -f
   ```

3. **使用构建脚本部署**
   ```bash
   # 构建Docker镜像
   ./scripts/build-docker.sh
   
   # 部署应用
   ./scripts/docker-deploy.sh
   ```

4. **手动Docker命令**
   ```bash
   # 构建镜像
   docker build -t library-manager .
   
   # 运行容器
   docker run -d -p 8081:8081 --name library-manager library-manager
   ```

5. **访问应用**
   - Web页面: http://localhost:8081/
   - REST API: http://localhost:8081/api/hello

#### 💻 本地开发

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd LibraryManager
   ```

2. **构建项目**
   ```bash
   ./gradlew build
   ```

3. **运行应用**
   ```bash
   ./gradlew run
   ```

4. **访问应用**
   - Web页面: http://localhost:8081/
   - REST API: http://localhost:8081/api/hello

## 📚 功能特性

### 当前功能
- ✅ 基础Web页面展示
- ✅ REST API接口
- ✅ 依赖注入示例
- ✅ 模板引擎集成

### 计划功能
- 🔄 图书管理（增删改查）
- 🔄 用户管理
- 🔄 借阅记录管理
- 🔄 数据库集成
- 🔄 用户认证与授权
- 🔄 数据验证
- 🔄 异常处理
- 🔄 日志记录

## 🔧 配置说明

### 应用配置 (application.properties)
```properties
# 服务器端口
server.port=8081

# 应用名称
spring.application.name=library-manager
```

### 构建配置 (build.gradle)
- Java版本: 17
- Spring Boot版本: 3.1.5
- 主要依赖: Spring Web, Thymeleaf

## 🐳 Docker配置

### Docker文件结构
```
LibraryManager/
├── Dockerfile                 # 多阶段构建配置
├── .dockerignore             # Docker构建忽略文件
├── docker-compose.yml        # 容器编排配置
└── scripts/
    ├── build-docker.sh       # Docker构建脚本
    ├── docker-deploy.sh      # Docker部署脚本
    └── docker-entrypoint.sh  # 容器启动脚本
```

### Docker特性
- **多阶段构建**: 优化镜像大小，分离构建和运行环境
- **Alpine Linux**: 使用轻量级Alpine作为运行时环境
- **非root用户**: 安全最佳实践，使用非特权用户运行应用
- **健康检查**: 自动监控应用状态
- **环境变量**: 支持灵活的配置管理
- **日志管理**: 支持日志挂载和收集

### Docker环境变量
```bash
# 应用配置
SPRING_PROFILES_ACTIVE=docker
SERVER_PORT=8081
SPRING_APPLICATION_NAME=library-manager

# JVM配置
JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport

# 时区配置
TZ=Asia/Shanghai
```

### Docker管理命令
```bash
# 查看运行中的容器
docker ps

# 查看容器日志
docker logs library-manager

# 进入容器
docker exec -it library-manager sh

# 停止容器
docker stop library-manager

# 删除容器
docker rm library-manager

# 查看镜像
docker images library-manager

# 删除镜像
docker rmi library-manager
```

## 📖 API文档

### REST API接口

#### 获取问候消息
- **URL**: `/api/hello`
- **方法**: `GET`
- **响应格式**:
  ```json
  {
    "message": "Hello World!"
  }
  ```

## 🏛️ 架构设计

### 分层架构
```
┌─────────────────┐
│   Controller    │ ← 处理HTTP请求，调用服务层
├─────────────────┤
│    Service      │ ← 业务逻辑处理
├─────────────────┤
│     Model       │ ← 数据传输对象
└─────────────────┘
```

### 组件说明

#### 1. 控制器层 (Controller)
- **HelloController**: 处理Web页面请求，返回HTML视图
- **HelloRestController**: 处理REST API请求，返回JSON数据

#### 2. 服务层 (Service)
- **HelloService**: 提供业务逻辑服务，可扩展为复杂的业务处理

#### 3. 模型层 (Model)
- **HelloMessage**: 数据传输对象，用于封装消息数据

## 🛠️ 开发指南

### 代码规范
- 使用JavaDoc注释所有公共类和方法
- 遵循Spring Boot最佳实践
- 采用RESTful API设计原则

### 扩展建议
1. **添加数据库支持**: 集成JPA/Hibernate
2. **实现业务功能**: 图书、用户、借阅管理
3. **添加安全机制**: Spring Security集成
4. **完善测试**: 单元测试和集成测试
5. **API文档**: 集成Swagger/OpenAPI

## 🔄 CI/CD 配置

### CircleCI 自动化流程

项目已配置CircleCI自动化构建和部署流程：

#### 触发条件
- **PR创建/更新**: 自动触发构建和测试
- **推送到main分支**: 执行完整的CI/CD流程
- **推送到feature/*或bugfix/*分支**: 执行构建和测试

#### 工作流程
1. **构建和测试**: 编译项目并运行TestNG测试
2. **Docker构建**: 构建Docker镜像并运行部署测试
3. **健康检查**: 验证应用在容器中正常运行

#### 配置说明
- 配置文件: `.circleci/config.yml`
- 详细指南: [CIRCLECI_SETUP.md](CIRCLECI_SETUP.md)
- Docker Registry指南: [DOCKER_REGISTRY_GUIDE.md](DOCKER_REGISTRY_GUIDE.md)

#### 快速开始
1. 将项目推送到GitHub
2. 在CircleCI中设置项目
3. 配置Docker Registry（可选）：
   - **Docker Hub**: 设置 `DOCKER_USERNAME` 和 `DOCKER_PASSWORD`
   - **GitHub Container Registry**: 设置 `GITHUB_USERNAME` 和 `GITHUB_TOKEN`
4. 创建PR或推送到main分支即可触发自动化流程

#### Docker镜像存储
- 支持推送到Docker Hub或GitHub Container Registry
- 自动生成版本标签和latest标签
- 可在任何支持Docker的环境中拉取使用

## ☁️ 云部署指南

### 支持的云平台

#### AWS ECS/Fargate
```bash
# 构建并推送到ECR
aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin <account>.dkr.ecr.us-west-2.amazonaws.com
docker tag library-manager:latest <account>.dkr.ecr.us-west-2.amazonaws.com/library-manager:latest
docker push <account>.dkr.ecr.us-west-2.amazonaws.com/library-manager:latest
```

#### Google Cloud Run
```bash
# 构建并推送到GCR
gcloud builds submit --tag gcr.io/<project-id>/library-manager
gcloud run deploy --image gcr.io/<project-id>/library-manager --platform managed
```

#### Azure Container Instances
```bash
# 构建并推送到ACR
az acr build --registry <registry-name> --image library-manager .
az container create --resource-group <resource-group> --name library-manager --image <registry-name>.azurecr.io/library-manager
```

#### Kubernetes
```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: library-manager
spec:
  replicas: 3
  selector:
    matchLabels:
      app: library-manager
  template:
    metadata:
      labels:
        app: library-manager
    spec:
      containers:
      - name: library-manager
        image: library-manager:latest
        ports:
        - containerPort: 8081
        env:
        - name: JAVA_OPTS
          value: "-Xms512m -Xmx1024m"
```

### 环境变量配置
```bash
# 生产环境配置
export SPRING_PROFILES_ACTIVE=production
export SERVER_PORT=8081
export JAVA_OPTS="-Xms1g -Xmx2g -XX:+UseG1GC"
export TZ=Asia/Shanghai
```

## 📝 更新日志

### v0.0.1-SNAPSHOT
- 初始项目结构
- 基础Web页面和API
- 完整的代码注释
- 项目文档
- ✅ Docker化支持
- ✅ 多阶段构建优化
- ✅ 云部署就绪
- ✅ CircleCI CI/CD配置
- ✅ 自动化测试和部署

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👥 作者

Library Manager Team

---

**注意**: 这是一个学习项目，用于演示Spring Boot应用开发的基本概念和最佳实践。
