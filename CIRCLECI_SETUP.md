# CircleCI 部署指南

## 📋 概述

本指南将帮助您在CircleCI上配置Library Manager项目的CI/CD流程，实现：
- 提交PR后自动触发构建
- 执行TestNG测试
- 构建Docker镜像
- 部署测试

## 🚀 快速开始

### 1. 准备工作

#### 1.1 确保项目已推送到GitHub
```bash
git add .
git commit -m "Add CircleCI configuration"
git push origin main
```

#### 1.2 在CircleCI上设置项目
1. 访问 [CircleCI](https://circleci.com/)
2. 使用GitHub账号登录
3. 在Dashboard中点击"Add Projects"
4. 找到您的Library Manager项目
5. 点击"Set Up Project"

### 2. 配置环境变量（可选）

如果您需要推送Docker镜像到Docker Hub，可以设置以下环境变量：

1. 在CircleCI项目设置中，进入"Environment Variables"
2. 添加以下变量：
   - `DOCKER_USERNAME`: 您的Docker Hub用户名
   - `DOCKER_PASSWORD`: 您的Docker Hub密码或访问令牌

### 3. 工作流程说明

#### 3.1 触发条件
- **PR创建/更新**: 自动触发构建和测试
- **推送到main分支**: 执行完整的CI/CD流程
- **推送到feature/*或bugfix/*分支**: 执行构建和测试

#### 3.2 作业流程
```
1. build-and-test
   ├── 检出代码
   ├── 恢复Gradle缓存
   ├── 构建项目
   ├── 运行TestNG测试
   ├── 保存测试报告
   └── 保存构建产物

2. docker-build-and-test (依赖build-and-test)
   ├── 恢复构建产物
   ├── 设置Docker环境
   ├── 构建Docker镜像
   ├── 运行容器
   ├── 健康检查
   ├── 集成测试
   └── 清理容器
```

## 🔧 配置文件说明

### .circleci/config.yml 结构

```yaml
version: 2.1

jobs:
  build-and-test:          # 构建和测试作业
  docker-build-and-test:   # Docker构建和部署测试作业

workflows:
  build-test-deploy:       # 工作流程定义
```

### 关键配置说明

#### 1. 执行器配置
```yaml
docker:
  - image: openjdk:17-jdk-slim  # 使用OpenJDK 17
working_directory: ~/project    # 工作目录
```

#### 2. 缓存配置
```yaml
# 恢复Gradle缓存
- restore_cache:
    keys:
      - gradle-{{ checksum "build.gradle" }}
      - gradle-

# 保存Gradle缓存
- save_cache:
    paths:
      - ~/.gradle
    key: gradle-{{ checksum "build.gradle" }}
```

#### 3. 测试报告
```yaml
# 保存TestNG测试报告
- store_test_results:
    path: build/test-results/testNG
```

#### 4. Docker配置
```yaml
# 设置远程Docker环境
- setup_remote_docker:
    version: 20.10.14
```

## 📊 监控和调试

### 1. 查看构建状态
- 在CircleCI Dashboard中查看构建状态
- 点击具体的构建查看详细日志

### 2. 测试报告
- TestNG测试报告会自动保存到CircleCI
- 在构建详情页面的"Tests"标签中查看

### 3. 常见问题排查

#### 构建失败
1. 检查Java版本兼容性
2. 确认Gradle Wrapper权限
3. 查看构建日志中的错误信息

#### Docker构建失败
1. 检查Dockerfile语法
2. 确认.dockerignore配置
3. 查看Docker构建日志

#### 测试失败
1. 检查TestNG测试配置
2. 确认测试依赖
3. 查看测试报告详情

## 🔄 自定义配置

### 1. 修改触发分支
```yaml
filters:
  branches:
    only:
      - main
      - develop
      - /feature\/.*/
```

### 2. 添加更多测试
```yaml
- run:
    name: 运行集成测试
    command: |
      ./gradlew integrationTest --no-daemon
```

### 3. 部署到生产环境
```yaml
- run:
    name: 部署到生产环境
    command: |
      # 部署脚本
      ./scripts/deploy-production.sh
```

## 📝 最佳实践

### 1. 分支策略
- 使用feature分支开发新功能
- 通过PR合并到main分支
- 保持main分支的稳定性

### 2. 提交信息
- 使用清晰的提交信息
- 遵循约定式提交规范

### 3. 测试覆盖
- 确保所有新功能都有测试
- 保持测试的独立性
- 定期更新测试用例

## 🆘 支持

如果遇到问题，可以：
1. 查看CircleCI官方文档
2. 检查项目GitHub Issues
3. 联系项目维护者

---

**注意**: 首次运行可能需要较长时间，因为需要下载依赖和构建Docker镜像。后续构建会利用缓存，速度会显著提升。
