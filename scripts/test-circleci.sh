#!/bin/bash
# ===========================================
# CircleCI 配置测试脚本
# ===========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}===========================================${NC}"
echo -e "${BLUE}CircleCI 配置测试脚本${NC}"
echo -e "${BLUE}===========================================${NC}"

# 检查必要文件
echo -e "${YELLOW}检查CircleCI配置文件...${NC}"
if [ -f ".circleci/config.yml" ]; then
    echo -e "${GREEN}✅ .circleci/config.yml 存在${NC}"
else
    echo -e "${RED}❌ .circleci/config.yml 不存在${NC}"
    exit 1
fi

# 检查Dockerfile
echo -e "${YELLOW}检查Dockerfile...${NC}"
if [ -f "Dockerfile" ]; then
    echo -e "${GREEN}✅ Dockerfile 存在${NC}"
else
    echo -e "${RED}❌ Dockerfile 不存在${NC}"
    exit 1
fi

# 检查.dockerignore
echo -e "${YELLOW}检查.dockerignore...${NC}"
if [ -f ".dockerignore" ]; then
    echo -e "${GREEN}✅ .dockerignore 存在${NC}"
else
    echo -e "${RED}❌ .dockerignore 不存在${NC}"
    exit 1
fi

# 检查build.gradle
echo -e "${YELLOW}检查build.gradle...${NC}"
if [ -f "build.gradle" ]; then
    echo -e "${GREEN}✅ build.gradle 存在${NC}"
else
    echo -e "${RED}❌ build.gradle 不存在${NC}"
    exit 1
fi

# 检查TestNG测试
echo -e "${YELLOW}检查TestNG测试...${NC}"
if [ -d "src/test/java" ]; then
    echo -e "${GREEN}✅ TestNG测试目录存在${NC}"
else
    echo -e "${RED}❌ TestNG测试目录不存在${NC}"
    exit 1
fi

# 验证CircleCI配置语法
echo -e "${YELLOW}验证CircleCI配置语法...${NC}"
if command -v circleci &> /dev/null; then
    circleci config validate .circleci/config.yml
    echo -e "${GREEN}✅ CircleCI配置语法正确${NC}"
else
    echo -e "${YELLOW}⚠️  CircleCI CLI未安装，跳过语法验证${NC}"
fi

# 测试本地构建
echo -e "${YELLOW}测试本地构建...${NC}"
if [ -f "gradlew" ]; then
    chmod +x ./gradlew
    ./gradlew build -x test --no-daemon
    echo -e "${GREEN}✅ 本地构建成功${NC}"
else
    echo -e "${RED}❌ gradlew 不存在${NC}"
    exit 1
fi

# 测试TestNG
echo -e "${YELLOW}测试TestNG...${NC}"
./gradlew testNG --no-daemon
echo -e "${GREEN}✅ TestNG测试成功${NC}"

# 测试Docker构建
echo -e "${YELLOW}测试Docker构建...${NC}"
docker build -t library-manager:test .
echo -e "${GREEN}✅ Docker构建成功${NC}"

echo -e "${GREEN}===========================================${NC}"
echo -e "${GREEN}所有测试通过！CircleCI配置就绪${NC}"
echo -e "${GREEN}===========================================${NC}"

echo -e "${BLUE}下一步操作:${NC}"
echo -e "1. 将项目推送到GitHub"
echo -e "2. 在CircleCI中设置项目"
echo -e "3. 创建PR或推送到main分支触发CI/CD"
