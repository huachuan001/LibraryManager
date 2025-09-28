#!/bin/bash
# ===========================================
# Library Manager Docker 构建脚本
# ===========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目信息
PROJECT_NAME="library-manager"
IMAGE_NAME="library-manager"
VERSION=${1:-"latest"}
REGISTRY=${2:-""}

echo -e "${BLUE}===========================================${NC}"
echo -e "${BLUE}Library Manager Docker 构建脚本${NC}"
echo -e "${BLUE}===========================================${NC}"

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    echo -e "${RED}错误: Docker 未安装或未在PATH中找到${NC}"
    exit 1
fi

# 检查Docker是否运行
if ! docker info &> /dev/null; then
    echo -e "${RED}错误: Docker 守护进程未运行${NC}"
    exit 1
fi

echo -e "${YELLOW}构建信息:${NC}"
echo -e "  项目名称: ${PROJECT_NAME}"
echo -e "  镜像名称: ${IMAGE_NAME}"
echo -e "  版本标签: ${VERSION}"
echo -e "  注册表: ${REGISTRY:-"本地"}"
echo ""

# 构建Docker镜像
echo -e "${YELLOW}开始构建Docker镜像...${NC}"
docker build -t ${IMAGE_NAME}:${VERSION} .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Docker镜像构建成功!${NC}"
else
    echo -e "${RED}❌ Docker镜像构建失败!${NC}"
    exit 1
fi

# 显示镜像信息
echo -e "${YELLOW}镜像信息:${NC}"
docker images ${IMAGE_NAME}:${VERSION}

# 如果指定了注册表，则标记并推送镜像
if [ -n "${REGISTRY}" ]; then
    echo -e "${YELLOW}标记镜像为注册表格式...${NC}"
    docker tag ${IMAGE_NAME}:${VERSION} ${REGISTRY}/${IMAGE_NAME}:${VERSION}
    
    echo -e "${YELLOW}推送镜像到注册表...${NC}"
    docker push ${REGISTRY}/${IMAGE_NAME}:${VERSION}
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ 镜像推送成功!${NC}"
    else
        echo -e "${RED}❌ 镜像推送失败!${NC}"
        exit 1
    fi
fi

echo -e "${GREEN}===========================================${NC}"
echo -e "${GREEN}构建完成!${NC}"
echo -e "${GREEN}===========================================${NC}"

# 显示使用说明
echo -e "${BLUE}使用说明:${NC}"
echo -e "  运行容器: docker run -p 8081:8081 ${IMAGE_NAME}:${VERSION}"
echo -e "  使用Compose: docker-compose up -d"
echo -e "  查看日志: docker logs <container_id>"
echo -e "  停止容器: docker stop <container_id>"
