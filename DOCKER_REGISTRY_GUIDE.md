# 🐳 Docker镜像存储指南

## 📋 概述

Docker镜像需要存储在**Docker Registry**中，这样其他环境才能拉取和使用。本指南介绍几种常见的存储方案。

## 🏪 Docker Registry 类型

### 1. 公共Registry（免费）

#### Docker Hub
- **网址**: https://hub.docker.com/
- **特点**: 最流行的公共Registry
- **免费额度**: 1个私有仓库，无限公共仓库
- **适用场景**: 开源项目、学习项目

#### GitHub Container Registry (ghcr.io)
- **网址**: https://github.com/features/packages
- **特点**: GitHub官方提供
- **免费额度**: 500MB存储空间，1GB带宽/月
- **适用场景**: GitHub项目

### 2. 云服务商Registry（付费）

#### AWS ECR (Elastic Container Registry)
- **特点**: AWS官方Registry
- **优势**: 与AWS服务深度集成
- **适用场景**: AWS环境部署

#### Google Container Registry (GCR)
- **特点**: Google Cloud官方Registry
- **优势**: 与GCP服务集成
- **适用场景**: Google Cloud环境

#### Azure Container Registry (ACR)
- **特点**: Microsoft Azure官方Registry
- **优势**: 与Azure服务集成
- **适用场景**: Azure环境

### 3. 私有Registry（自建）

#### Harbor
- **特点**: 企业级私有Registry
- **优势**: 安全、权限管理、漏洞扫描
- **适用场景**: 企业内部使用

## 🚀 推荐方案

### 方案一：Docker Hub（推荐新手）

#### 优点
- 免费使用
- 配置简单
- 社区活跃
- 文档完善

#### 配置步骤

1. **注册Docker Hub账号**
   ```bash
   # 访问 https://hub.docker.com/ 注册账号
   ```

2. **在CircleCI中配置环境变量**
   ```bash
   DOCKER_USERNAME=your-dockerhub-username
   DOCKER_PASSWORD=your-dockerhub-password
   ```

3. **修改CircleCI配置**
   ```yaml
   # 在 .circleci/config.yml 中添加推送步骤
   - run:
       name: 推送Docker镜像到Docker Hub
       command: |
         echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
         docker tag library-manager:${CIRCLE_SHA1} $DOCKER_USERNAME/library-manager:${CIRCLE_SHA1}
         docker tag library-manager:${CIRCLE_SHA1} $DOCKER_USERNAME/library-manager:latest
         docker push $DOCKER_USERNAME/library-manager:${CIRCLE_SHA1}
         docker push $DOCKER_USERNAME/library-manager:latest
   ```

### 方案二：GitHub Container Registry（推荐开源项目）

#### 优点
- 与GitHub集成
- 免费额度充足
- 支持私有仓库

#### 配置步骤

1. **创建GitHub Personal Access Token**
   ```bash
   # 访问 GitHub Settings > Developer settings > Personal access tokens
   # 创建token，权限选择：write:packages, read:packages
   ```

2. **在CircleCI中配置环境变量**
   ```bash
   GITHUB_TOKEN=your-github-token
   GITHUB_USERNAME=your-github-username
   ```

3. **修改CircleCI配置**
   ```yaml
   - run:
       name: 推送Docker镜像到GitHub Container Registry
       command: |
         echo $GITHUB_TOKEN | docker login ghcr.io -u $GITHUB_USERNAME --password-stdin
         docker tag library-manager:${CIRCLE_SHA1} ghcr.io/$GITHUB_USERNAME/library-manager:${CIRCLE_SHA1}
         docker tag library-manager:${CIRCLE_SHA1} ghcr.io/$GITHUB_USERNAME/library-manager:latest
         docker push ghcr.io/$GITHUB_USERNAME/library-manager:${CIRCLE_SHA1}
         docker push ghcr.io/$GITHUB_USERNAME/library-manager:latest
   ```

## 🔧 实际配置示例

### 完整的CircleCI配置（Docker Hub）

```yaml
# .circleci/config.yml
version: 2.1

jobs:
  build-and-test:
    # ... 现有配置 ...

  docker-build-and-test:
    docker:
      - image: openjdk:17-jdk-slim
    working_directory: ~/project
    
    steps:
      - checkout
      - attach_workspace:
          at: .
      - setup_remote_docker:
          version: 20.10.14
      
      # 构建Docker镜像
      - run:
          name: 构建Docker镜像
          command: |
            docker build -t library-manager:${CIRCLE_SHA1} .
            docker tag library-manager:${CIRCLE_SHA1} library-manager:latest
      
      # 推送镜像到Docker Hub
      - run:
          name: 推送Docker镜像到Docker Hub
          command: |
            echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
            docker tag library-manager:${CIRCLE_SHA1} $DOCKER_USERNAME/library-manager:${CIRCLE_SHA1}
            docker tag library-manager:${CIRCLE_SHA1} $DOCKER_USERNAME/library-manager:latest
            docker push $DOCKER_USERNAME/library-manager:${CIRCLE_SHA1}
            docker push $DOCKER_USERNAME/library-manager:latest
          when: on_success
      
      # 部署测试
      - run:
          name: 部署测试
          command: |
            docker run -d -p 8081:8081 --name library-manager-test $DOCKER_USERNAME/library-manager:${CIRCLE_SHA1}
            sleep 60
            curl -f http://localhost:8081/api/hello
            docker stop library-manager-test
            docker rm library-manager-test
```

## 📦 镜像标签策略

### 推荐的标签方案

```bash
# 版本标签
your-username/library-manager:1.0.0
your-username/library-manager:1.0.1

# 环境标签
your-username/library-manager:dev
your-username/library-manager:staging
your-username/library-manager:prod

# 分支标签
your-username/library-manager:feature-auth
your-username/library-manager:bugfix-login

# 提交标签
your-username/library-manager:abc1234
your-username/library-manager:latest
```

## 🔒 安全最佳实践

### 1. 使用访问令牌
- 不要使用密码，使用访问令牌
- 定期轮换访问令牌
- 限制令牌权限

### 2. 镜像扫描
- 启用漏洞扫描
- 定期更新基础镜像
- 使用最小化镜像

### 3. 权限管理
- 限制推送权限
- 使用团队账号
- 启用双因素认证

## 🚀 部署使用

### 拉取镜像
```bash
# 从Docker Hub拉取
docker pull your-username/library-manager:latest

# 从GitHub Container Registry拉取
docker pull ghcr.io/your-username/library-manager:latest
```

### 运行容器
```bash
# 运行镜像
docker run -d -p 8081:8081 your-username/library-manager:latest

# 使用docker-compose
docker-compose up -d
```

## 💰 成本对比

| Registry | 免费额度 | 超出费用 | 适用场景 |
|----------|----------|----------|----------|
| Docker Hub | 1私有仓库 | $5/月/私有仓库 | 个人项目 |
| GitHub Container Registry | 500MB存储 | $0.25/GB/月 | 开源项目 |
| AWS ECR | 500MB存储 | $0.10/GB/月 | AWS环境 |
| Google GCR | 500MB存储 | $0.026/GB/月 | GCP环境 |

## 🎯 推荐选择

### 个人学习项目
- **推荐**: Docker Hub
- **原因**: 免费、简单、社区支持好

### 开源项目
- **推荐**: GitHub Container Registry
- **原因**: 与GitHub集成、免费额度充足

### 企业项目
- **推荐**: 云服务商Registry
- **原因**: 安全、集成、支持好

---

**💡 提示**: 建议先使用Docker Hub进行测试，熟悉流程后再根据实际需求选择合适的Registry。
