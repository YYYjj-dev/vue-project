<template>
  <div class="admin-container">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <div class="logo">
        <h2>后台管理系统</h2>
      </div>
      <div class="menu">
        <router-link to="/admin/safety" class="menu-item" active-class="active">
          <span>安全法规管理</span>
        </router-link>
        <router-link to="/admin/news" class="menu-item" active-class="active">
          <span>资讯报道管理</span>
        </router-link>
        <router-link to="/admin/cases" class="menu-item" active-class="active">
          <span>应用案例管理</span>
        </router-link>
        <router-link to="/admin/additives" class="menu-item" active-class="active">
          <span>添加剂管理</span>
        </router-link>
        <router-link to="/admin/users" class="menu-item" active-class="active">
          <span>用户管理</span>
        </router-link>
        <router-link to="/admin/products" class="menu-item" active-class="active">
          <span>商品管理</span>
        </router-link>
      </div>
    </div>

    <!-- 主要内容区 -->
    <div class="main-content">
      <!-- 顶部栏 -->
      <div class="header">
        <div class="breadcrumb">
          <span>首页</span>
          <span class="separator">/</span>
          <span>{{ getCurrentPageTitle() }}</span>
        </div>
        <div class="user-info">
          <span class="welcome">欢迎回来，</span>
          <span class="username">管理员</span>
          <button class="logout-btn" @click="handleLogout">
            退出登录
          </button>
        </div>
      </div>

      <!-- 内容区 -->
      <div class="content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const getCurrentPageTitle = () => {
  const pathMap = {
    '/admin/safety': '安全法规管理',
    '/admin/news': '资讯报道管理',
    '/admin/cases': '应用案例管理',
    '/admin/additives': '添加剂管理',
    '/admin/users': '用户管理',
    '/admin/products': '商品管理'
  }
  return pathMap[route.path] || '管理后台'
}

const handleLogout = () => {
  // 实现登出逻辑
  router.push('/login')
}
</script>

<style scoped>
.admin-container {
  display: flex;
  min-height: 100vh;
  background-color: #f0f2f5;
}

.sidebar {
  width: 260px;
  background: linear-gradient(180deg, #001529 0%, #002140 100%);
  color: white;
  position: fixed;
  height: 100vh;
  left: 0;
  top: 0;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.15);
  z-index: 1000;
  display: flex;
  flex-direction: column;
  transition: all 0.3s ease;
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.05);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  letter-spacing: 1px;
  color: #fff;
}

.menu {
  padding: 16px 0;
  flex: 1;
  overflow-y: auto;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 12px 24px;
  color: rgba(255, 255, 255, 0.85);
  text-decoration: none;
  transition: all 0.3s ease;
  margin: 4px 16px;
  border-radius: 6px;
  font-size: 14px;
}

.menu-item:hover {
  color: white;
  background-color: rgba(255, 255, 255, 0.1);
  transform: translateX(4px);
}

.menu-item.active {
  color: white;
  background: #1890ff;
  box-shadow: 0 2px 8px rgba(24, 144, 255, 0.35);
}

.icon {
  margin-right: 12px;
  font-size: 18px;
  opacity: 0.9;
}

.main-content {
  flex: 1;
  margin-left: 260px;
  min-height: 100vh;
  background-color: #f0f2f5;
}

.header {
  height: 64px;
  background-color: white;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  position: sticky;
  top: 0;
  z-index: 999;
}

.breadcrumb {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #666;
}

.separator {
  margin: 0 8px;
  color: #999;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.welcome {
  font-size: 14px;
  color: #666;
}

.username {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.logout-btn {
  padding: 6px 16px;
  background-color: #ff4d4f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
}

.logout-btn:hover {
  background-color: #ff7875;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(255, 77, 79, 0.35);
}

.content {
  padding: 24px;
  min-height: calc(100vh - 64px);
}

/* 滚动条样式 */
.menu::-webkit-scrollbar {
  width: 6px;
}

.menu::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.menu::-webkit-scrollbar-track {
  background-color: transparent;
}
</style> 