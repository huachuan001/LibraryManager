#!/bin/sh
# ===========================================
# Library Manager Docker 启动脚本
# ===========================================

set -e

echo "==========================================="
echo "Library Manager 启动中..."
echo "==========================================="

# 显示环境信息
echo "Java版本:"
java -version

echo "JVM参数: $JAVA_OPTS"
echo "应用端口: ${SERVER_PORT:-8081}"
echo "时区: $(date)"

# 等待数据库连接（如果有的话）
# 这里可以添加数据库连接检查逻辑
# wait-for-it.sh mysql:3306 --timeout=30

# 启动应用
echo "启动Library Manager应用..."
exec java $JAVA_OPTS -jar app.jar "$@"
