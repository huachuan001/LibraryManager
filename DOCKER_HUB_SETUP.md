# 🐳 Docker Hub 配置指南

## 📋 您的Docker Hub仓库信息

- **仓库地址**: https://hub.docker.com/repository/docker/huachuan001/test/general
- **镜像名称**: `huachuan001/test`
- **仓库类型**: 公开仓库

## ⚙️ CircleCI 环境变量配置

### 1. 在CircleCI中设置环境变量

1. 访问您的CircleCI项目设置页面
2. 点击左侧菜单的 "Environment Variables"
3. 添加以下环境变量：

```bash
DOCKER_USERNAME=huachuan001
DOCKER_PASSWORD=your-dockerhub-password
```

**注意**: 
- `DOCKER_USERNAME` 设置为您的Docker Hub用户名 `huachuan001`
- `DOCKER_PASSWORD` 设置为您的Docker Hub密码或访问令牌

### 2. 使用访问令牌（推荐）

为了安全起见，建议使用访问令牌而不是密码：

1. 访问 Docker Hub: https://hub.docker.com/settings/security
2. 点击 "New Access Token"
3. 输入令牌名称，如 "CircleCI"
4. 选择权限：Read, Write, Delete
5. 复制生成的令牌
6. 在CircleCI中设置：
   ```bash
   DOCKER_USERNAME=huachuan001
   DOCKER_PASSWORD=your-access-token
   ```

## 🔄 CI/CD 工作流程

配置完成后，CircleCI将执行以下操作：

### 1. 构建阶段
```bash
# 构建Docker镜像
docker build -t library-manager:${CIRCLE_SHA1} .
```

### 2. 推送阶段
```bash
# 登录Docker Hub
docker login -u huachuan001 --password-stdin

# 标记镜像
docker tag library-manager:${CIRCLE_SHA1} huachuan001/test:${CIRCLE_SHA1}
docker tag library-manager:${CIRCLE_SHA1} huachuan001/test:latest

# 推送镜像
docker push huachuan001/test:${CIRCLE_SHA1}
docker push huachuan001/test:latest
```

### 3. 测试阶段
```bash
# 运行容器进行测试
docker run -d -p 8081:8081 --name library-manager-test huachuan001/test:${CIRCLE_SHA1}
```

## 📦 镜像标签策略

CircleCI将自动创建以下标签：

- `huachuan001/test:latest` - 最新版本
- `huachuan001/test:abc1234` - 基于Git提交SHA的版本标签

## 🚀 使用推送的镜像

### 拉取镜像
```bash
# 拉取最新版本
docker pull huachuan001/test:latest

# 拉取特定版本
docker pull huachuan001/test:abc1234
```

### 运行容器
```bash
# 运行最新版本
docker run -d -p 8081:8081 --name library-manager huachuan001/test:latest

# 运行特定版本
docker run -d -p 8081:8081 --name library-manager huachuan001/test:abc1234
```

### 使用docker-compose
```yaml
version: '3.8'
services:
  library-manager:
    image: huachuan001/test:latest
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=production
```

## 🔍 验证配置

### 1. 检查环境变量
在CircleCI构建日志中，您应该看到：
```
推送镜像到Docker Hub...
✅ 镜像推送成功到 huachuan001/test
```

### 2. 检查Docker Hub
访问 https://hub.docker.com/repository/docker/huachuan001/test/general
- 应该能看到新推送的镜像
- 标签包括 `latest` 和提交SHA

### 3. 测试拉取
```bash
# 测试拉取镜像
docker pull huachuan001/test:latest

# 测试运行
docker run -d -p 8081:8081 --name test-container huachuan001/test:latest
curl http://localhost:8081/api/hello
```

## 🛠️ 故障排除

### 常见问题

#### 1. 推送失败 - 认证错误
**错误**: `denied: requested access to the resource is denied`
**解决**: 检查Docker Hub用户名和密码是否正确

#### 2. 推送失败 - 权限不足
**错误**: `denied: access forbidden`
**解决**: 确保Docker Hub账号有推送权限

#### 3. 镜像标签错误
**错误**: `invalid reference format`
**解决**: 检查镜像名称格式是否正确

### 调试步骤

1. **检查环境变量**:
   ```bash
   # 在CircleCI构建日志中查看
   echo "DOCKER_USERNAME: $DOCKER_USERNAME"
   echo "DOCKER_PASSWORD: [HIDDEN]"
   ```

2. **检查登录状态**:
   ```bash
   docker login -u huachuan001 --password-stdin
   ```

3. **检查镜像标签**:
   ```bash
   docker images | grep huachuan001
   ```

## 📊 监控和通知

### 1. 设置通知
- 在CircleCI中配置构建成功/失败通知
- 在Docker Hub中设置仓库通知

### 2. 查看构建历史
- CircleCI Dashboard: 查看构建状态和日志
- Docker Hub: 查看镜像推送历史

## 🎯 下一步

### 1. 触发第一次构建
```bash
git add .
git commit -m "Configure Docker Hub integration"
git push origin main
```

### 2. 验证推送
- 检查CircleCI构建日志
- 访问Docker Hub查看新镜像
- 测试拉取和运行镜像

### 3. 设置自动部署
- 配置生产环境自动拉取最新镜像
- 设置健康检查和监控

---

**🎉 配置完成！**

现在每次推送到GitHub都会自动构建并推送镜像到您的Docker Hub仓库 `huachuan001/test`。
