#!/bin/bash
# ===========================================
# Docker Registry 设置脚本
# ===========================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}===========================================${NC}"
echo -e "${BLUE}Docker Registry 设置脚本${NC}"
echo -e "${BLUE}===========================================${NC}"

echo -e "${YELLOW}请选择Docker Registry方案:${NC}"
echo "1. Docker Hub (推荐新手)"
echo "2. GitHub Container Registry (推荐开源项目)"
echo "3. 跳过Registry配置 (仅本地测试)"
echo ""

read -p "请输入选择 (1-3): " choice

case $choice in
    1)
        echo -e "${YELLOW}配置Docker Hub...${NC}"
        echo ""
        echo "请按照以下步骤操作："
        echo "1. 访问 https://hub.docker.com/ 注册账号"
        echo "2. 在CircleCI项目设置中添加环境变量："
        echo "   - DOCKER_USERNAME: 您的Docker Hub用户名"
        echo "   - DOCKER_PASSWORD: 您的Docker Hub密码"
        echo ""
        echo "配置完成后，CircleCI会自动推送镜像到Docker Hub"
        echo "镜像地址格式: your-username/library-manager:latest"
        ;;
    2)
        echo -e "${YELLOW}配置GitHub Container Registry...${NC}"
        echo ""
        echo "请按照以下步骤操作："
        echo "1. 访问 GitHub Settings > Developer settings > Personal access tokens"
        echo "2. 创建新的token，权限选择：write:packages, read:packages"
        echo "3. 在CircleCI项目设置中添加环境变量："
        echo "   - GITHUB_USERNAME: 您的GitHub用户名"
        echo "   - GITHUB_TOKEN: 您创建的token"
        echo ""
        echo "配置完成后，CircleCI会自动推送镜像到GitHub Container Registry"
        echo "镜像地址格式: ghcr.io/your-username/library-manager:latest"
        ;;
    3)
        echo -e "${YELLOW}跳过Registry配置${NC}"
        echo ""
        echo "镜像将只在CircleCI中构建和测试，不会推送到外部存储"
        echo "适合纯测试场景"
        ;;
    *)
        echo -e "${RED}无效选择，请重新运行脚本${NC}"
        exit 1
        ;;
esac

echo ""
echo -e "${GREEN}===========================================${NC}"
echo -e "${GREEN}配置说明完成！${NC}"
echo -e "${GREEN}===========================================${NC}"

echo -e "${BLUE}下一步操作:${NC}"
echo "1. 按照上述说明配置环境变量"
echo "2. 推送代码到GitHub触发CI/CD"
echo "3. 在CircleCI中查看构建日志"
echo "4. 验证镜像是否成功推送"

echo ""
echo -e "${YELLOW}相关文档:${NC}"
echo "- 详细配置指南: DOCKER_REGISTRY_GUIDE.md"
echo "- 快速设置指南: QUICK_START.md"
echo "- CircleCI配置: .circleci/config.yml"
