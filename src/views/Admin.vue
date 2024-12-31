<template>
  <div class="admin-container">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <div class="logo">
        <h2>净食之窗后台管理系统</h2>
        <div class="home-icon" @click="goToAdmin">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
            <polyline points="9 22 9 12 15 12 15 22"></polyline>
          </svg>
        </div>
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
        <div class="menu-group">
          <div class="menu-item" @click="toggleUserMenu">
            <span>用户管理</span>
            <span class="arrow" :class="{ 'arrow-down': showUserMenu }">▼</span>
          </div>
          <div class="submenu" v-show="showUserMenu">
            <router-link to="/admin/users" class="menu-item" active-class="active">
              <span>用户列表</span>
            </router-link>
            <router-link to="/admin/feedback" class="menu-item" active-class="active">
              <span>用户反馈</span>
            </router-link>
          </div>
        </div>
        <router-link to="/admin/products" class="menu-item" active-class="active">
          <span>商品管理</span>
        </router-link>
        <router-link to="/admin/merchants" class="menu-item" active-class="active">
          <span>商家管理</span>
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
          <button class="home-btn" @click="goToHome">
            返回首页
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
import { ref } from 'vue'

const router = useRouter()
const route = useRoute()
const showUserMenu = ref(false)

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

const getCurrentPageTitle = () => {
  const pathMap = {
    '/admin/safety': '安全法规管理',
    '/admin/news': '资讯报道管理',
    '/admin/cases': '应用案例管理',
    '/admin/additives': '添加剂管理',
    '/admin/users': '用户管理',
    '/admin/feedback': '用户反馈',
    '/admin/products': '商品管理',
    '/admin/merchants': '商家管理'
  }
  return pathMap[route.path] || '管理后台'
}

const handleLogout = () => {
  router.push('/login')
}

const goToHome = () => {
  router.push('/')
}

const goToAdmin = () => {
  router.push('/admin')
}
</script>

<style scoped>
.admin-container {
  display: flex;
  min-height: 100vh;
  background-color: #f0f2f5;
}

.sidebar {
  width: 300px;
  background: #f5f5f5;
  color: white;
  position: fixed;
  height: 100vh;
  left: 0;
  top: 0;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
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
  background-color: #e8e8e8;
  border-bottom: 1px solid #dcdcdc;
  position: relative;
}

.logo h2 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  letter-spacing: 1px;
  color: #333;
  padding: 0 40px;
}

.home-icon {
  position: absolute;
  right: 20px;
  color: #666;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 6px;
  border-radius: 50%;
}

.home-icon:hover {
  color: #4CAF50;
  background-color: rgba(76, 175, 80, 0.1);
  transform: scale(1.1);
}

.menu {
  padding: 16px 0;
  flex: 1;
  overflow-y: auto;
}

.menu-item {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 24px;
  color: #333;
  text-decoration: none;
  transition: all 0.3s ease;
  margin: 4px 16px;
  border-radius: 6px;
  font-size: 16px;
  border: 2px solid transparent;
}

.menu-item:hover {
  color: #4CAF50;
  border-color: #4CAF50;
  box-shadow: 0 2px 8px rgba(76, 175, 80, 0.2);
  transform: translateX(2px);
}

.menu-item.active {
  color: white;
  background: #4CAF50;
  border-color: #4CAF50;
  box-shadow: 0 2px 8px rgba(76, 175, 80, 0.35);
}

.icon {
  margin-right: 12px;
  font-size: 18px;
  opacity: 0.9;
}

.main-content {
  flex: 1;
  margin-left: 300px;
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
  background-color: white;
  color: #ff4d4f;
  border: 2px solid #ff4d4f;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
}

.logout-btn:hover {
  color: white;
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
  background-color: #ccc;
  border-radius: 3px;
}

.menu::-webkit-scrollbar-track {
  background-color: transparent;
}

.menu-group {
  position: relative;
}

.submenu {
  padding-left: 20px;
  background-color: rgba(0, 0, 0, 0.03);
  border-left: 2px solid #e0e0e0;
}

.submenu .menu-item {
  font-size: 15px;
  padding: 10px 20px;
}

.arrow {
  font-size: 12px;
  margin-left: 12px;
  transition: transform 0.3s ease;
}

.arrow-down {
  transform: rotate(180deg);
}

.home-btn {
  padding: 6px 16px;
  background-color: white;
  color: #4CAF50;
  border: 2px solid #4CAF50;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
  margin-left: 12px;
}

.home-btn:hover {
  background-color: #4CAF50;
  color: white;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(76, 175, 80, 0.35);
}
</style>