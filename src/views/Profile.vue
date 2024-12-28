<template>
  <div class="profile">
    <div class="nav-wrapper">
      <NavBar />
    </div>

    <div class="profile-container">
      <div class="profile-header">
        <div class="avatar">
          {{ userStore.username.charAt(0).toUpperCase() }}
        </div>
        <h1 class="username">{{ userStore.username }}</h1>
      </div>

      <div class="profile-content">
        <!-- 这里可以添加个人中心的具体内容 -->
        <div class="profile-section">
          <h2>个人信息</h2>
          <!-- 添加个人信息内容 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'
import { defineUser } from '../store/userStore'

export default {
  name: 'Profile',
  components: {
    NavBar
  },
  setup() {
    const userStore = defineUser()

    return {
      userStore
    }
  }
}
</script>

<style scoped>
.profile {
  min-height: 100vh;
  background-color: #f8f9fa;
  padding-top: 60px;
}

.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.profile-container {
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 20px;
}

.profile-header {
  background: white;
  padding: 40px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.avatar {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #4CAF50, #45a049);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.username {
  font-size: 24px;
  color: #333;
  margin: 0;
}

.profile-content {
  margin-top: 30px;
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.profile-section {
  margin-bottom: 30px;
}

.profile-section h2 {
  font-size: 20px;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
</style>
