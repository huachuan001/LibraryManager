#!/bin/bash
# ===========================================
# Library Manager Docker 部署脚本
# ===========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 默认配置
PROJECT_NAME="library-manager"
IMAGE_NAME="library-manager"
VERSION=${1:-"latest"}
PORT=${2:-"8081"}

echo -e "${BLUE}===========================================${NC}"
echo -e "${BLUE}Library Manager Docker 部署脚本${NC}"
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

echo -e "${YELLOW}部署信息:${NC}"
echo -e "  项目名称: ${PROJECT_NAME}"
echo -e "  镜像名称: ${IMAGE_NAME}"
echo -e "  版本标签: ${VERSION}"
echo -e "  端口映射: ${PORT}:8081"
echo ""

# 停止并删除现有容器
echo -e "${YELLOW}停止现有容器...${NC}"
if docker ps -a --format "table {{.Names}}" | grep -q "^${PROJECT_NAME}$"; then
    docker stop ${PROJECT_NAME} || true
    docker rm ${PROJECT_NAME} || true
    echo -e "${GREEN}✅ 现有容器已停止并删除${NC}"
else
    echo -e "${YELLOW}未找到现有容器${NC}"
fi

# 检查镜像是否存在
if ! docker images --format "table {{.Repository}}:{{.Tag}}" | grep -q "^${IMAGE_NAME}:${VERSION}$"; then
    echo -e "${YELLOW}镜像不存在，开始构建...${NC}"
    ./scripts/build-docker.sh ${VERSION}
fi

# 运行新容器
echo -e "${YELLOW}启动新容器...${NC}"
docker run -d \
    --name ${PROJECT_NAME} \
    --restart unless-stopped \
    -p ${PORT}:8081 \
    -e TZ=Asia/Shanghai \
    -e JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport" \
    ${IMAGE_NAME}:${VERSION}

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ 容器启动成功!${NC}"
else
    echo -e "${RED}❌ 容器启动失败!${NC}"
    exit 1
fi

# 等待应用启动
echo -e "${YELLOW}等待应用启动...${NC}"
sleep 10

# 检查容器状态
echo -e "${YELLOW}容器状态:${NC}"
docker ps --filter "name=${PROJECT_NAME}" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# 健康检查
echo -e "${YELLOW}执行健康检查...${NC}"
for i in {1..30}; do
    if curl -f http://localhost:${PORT}/api/hello &> /dev/null; then
        echo -e "${GREEN}✅ 应用健康检查通过!${NC}"
        break
    else
        echo -e "${YELLOW}等待应用启动... (${i}/30)${NC}"
        sleep 2
    fi
    
    if [ $i -eq 30 ]; then
        echo -e "${RED}❌ 应用启动超时!${NC}"
        echo -e "${YELLOW}查看容器日志:${NC}"
        docker logs ${PROJECT_NAME}
        exit 1
    fi
done

echo -e "${GREEN}===========================================${NC}"
echo -e "${GREEN}部署完成!${NC}"
echo -e "${GREEN}===========================================${NC}"

# 显示访问信息
echo -e "${BLUE}访问信息:${NC}"
echo -e "  Web页面: http://localhost:${PORT}/"
echo -e "  REST API: http://localhost:${PORT}/api/hello"
echo -e "  健康检查: http://localhost:${PORT}/api/hello"

echo -e "${BLUE}管理命令:${NC}"
echo -e "  查看日志: docker logs ${PROJECT_NAME}"
echo -e "  进入容器: docker exec -it ${PROJECT_NAME} sh"
echo -e "  停止容器: docker stop ${PROJECT_NAME}"
echo -e "  删除容器: docker rm ${PROJECT_NAME}"
