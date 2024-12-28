<template>
  <nav class="nav-container">
    <div class="logo">
      <img src="../img/logo.jpg" alt="Logo">
    </div>
    <div class="nav-items">
      <router-link to="/Home" class="nav-item" active-class="active">首页</router-link>
      <router-link to="/regulation" class="nav-item" active-class="active">安全法规</router-link>
      <router-link to="/news" class="nav-item" active-class="active">资讯报道</router-link>

      <!-- 添加剂下拉菜单 -->
      <div class="nav-item-wrapper dropdown" @mouseover="showAdditiveDropdown = true"
        @mouseleave="showAdditiveDropdown = false">
        <router-link to="/additive_type" class="nav-item" active-class="active">
          添加剂
          <i class="dropdown-icon"></i>
        </router-link>
        <transition name="dropdown">
          <div class="dropdown-menu" v-show="showAdditiveDropdown">
            <router-link to="/additive_type" class="dropdown-item">分类</router-link>
            <router-link to="/additive_zhuanqu" class="dropdown-item">专区</router-link>
          </div>
        </transition>
      </div>

      <router-link to="/cases" class="nav-item" active-class="active">应用案例</router-link>

      <!-- 商城下拉菜单 -->
      <div class="nav-item-wrapper dropdown" @mouseover="showShopDropdown = true"
        @mouseleave="showShopDropdown = false">
        <router-link to="/shop_rec" class="nav-item" active-class="active">
          商城
          <i class="dropdown-icon"></i>
        </router-link>
        <transition name="dropdown">
          <div class="dropdown-menu" v-show="showShopDropdown">
            <router-link to="/shop_rec" class="dropdown-item">推荐</router-link>
            <router-link to="/shop_sel" class="dropdown-item">加盟商</router-link>
          </div>
        </transition>
      </div>
    </div>
    <div>
      <button class="login-btn" @click="goToAdmin">管理员登录</button>
    </div>

    <div class="nav-right">
      <div class="search-box" v-if="!hideSearch">
        <input type="text" class="search-input" v-model="seachInfo" placeholder="搜索..." @keyup.enter="seach" />
        <i class="search-icon"></i>
      </div>
      <div class="cart-box" @click="goToCart">
        <div class="cart-icon"></div>
        <span class="cart-count" v-if="cartItemCount > 0">{{ cartItemCount }}</span>
      </div>

      <!-- 未登录状态 -->
      <button v-if="!userStore.username" class="login-btn" @click="goToLogin">
        登录/注册
      </button>

      <!-- 登录状态 -->
      <div v-else class="user-profile" @mouseover="showUserMenu = true" @mouseleave="showUserMenu = false">
        <div class="avatar">
          {{ userStore.username.charAt(0).toUpperCase() }}
        </div>
        <span class="username">{{ userStore.username }}</span>

        <!-- 用户菜单 -->
        <transition name="dropdown">
          <div v-show="showUserMenu" class="user-menu">
            <div class="menu-item" @click="goToProfile">
              <i class="profile-icon"></i>
              个人中心
            </div>
            <div class="menu-item" @click="logout">
              <i class="logout-icon"></i>
              退出登录
            </div>
          </div>
        </transition>
      </div>
    </div>
  </nav>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineUser } from '../store/userStore'


export default {
  name: 'NavBar',
  props: {
    hideSearch: {
      type: Boolean,
      default: false
    }
  },
  setup() {
    const userStore = defineUser()
    const router = useRouter()
    const showAdditiveDropdown = ref(false)
    const showShopDropdown = ref(false)
    const cartItemCount = ref(0)
    const seachInfo = ref()
    const showUserMenu = ref(false)

    const goToLogin = () => {
      router.push('/login')
    }

    const goToCart = () => {
      router.push('/cart')
    }

    const seach = () => {
      console.log(seachInfo.value)
    }

    const goToAdmin = () => {
      router.push('/admin')
    }

    const goToProfile = () => {
      router.push({
        name: 'Profile'
      })
      showUserMenu.value = false
    }

    const logout = () => {
      userStore.clearUserInfo()
      showUserMenu.value = false
      router.push('/')
    }

    return {
      goToLogin,
      goToCart,
      seach,
      seachInfo,
      goToAdmin,
      showAdditiveDropdown,
      showShopDropdown,
      cartItemCount,
      userStore,
      showUserMenu,
      goToProfile,
      logout
    }
  }
}
</script>

<style scoped>
.nav-container {
  display: flex;
  align-items: center;
  padding: 0.8rem 2rem;
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.logo {
  padding: 0.5rem 1rem;
  margin-right: 2rem;
}

.logo img {
  height: 40px;
  object-fit: contain;
}

.nav-items {
  display: flex;
  gap: 2.5rem;
  flex-grow: 1;
  align-items: center;
}

.nav-item-wrapper {
  position: relative;
}

.nav-item {
  position: relative;
  text-decoration: none;
  color: #333;
  font-size: 1rem;
  font-weight: 500;
  padding: 0.5rem 0;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
  gap: 4px;
}

.nav-item:hover,
.nav-item.active {
  color: #42b983;
}

/* 修改下划线效果 */
.nav-item::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #42b983;
  transition: width 0.3s ease;
}

.nav-item:hover::after,
.nav-item.active::after {
  width: 100%;
}

/* 下拉菜单样式 */
.dropdown {
  display: flex;
  align-items: center;
}

.dropdown-icon {
  display: inline-block;
  width: 0;
  height: 0;
  margin-left: 6px;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 4px solid currentColor;
  transition: transform 0.3s ease;
}

.dropdown:hover .dropdown-icon {
  transform: rotate(180deg);
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translateX(-50%);
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  min-width: 140px;
  padding: 0.5rem 0;
  margin-top: 0.5rem;
  z-index: 1000;
  transform-origin: top;
}

.dropdown-item {
  display: block;
  padding: 0.8rem 1.2rem;
  color: #333;
  text-decoration: none;
  transition: all 0.3s ease;
  text-align: center;
  white-space: nowrap;
}

.dropdown-item:hover {
  background-color: #f8f9fa;
  color: #42b983;
}

/* 下拉菜单动画 */
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.3s ease;
  transform-origin: top;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateX(-50%) scaleY(0);
}

.dropdown-enter-to,
.dropdown-leave-from {
  opacity: 1;
  transform: translateX(-50%) scaleY(1);
}

/* 下拉菜单项动画 */
.dropdown-item {
  transition: all 0.3s ease;
  transition-delay: calc(var(--index) * 0.05s);
}

.dropdown-enter-active .dropdown-item {
  opacity: 0;
  transform: translateY(-10px);
}

.dropdown-enter-to .dropdown-item {
  opacity: 1;
  transform: translateY(0);
}

/* 右侧区域样式 */
.nav-right {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-left: 2rem;
}

/* 搜索框样式 */
.search-box {
  position: relative;
  display: flex;
  align-items: center;
}

.search-input {
  width: 240px;
  padding: 0.6rem 1rem;
  padding-right: 2.5rem;
  font-size: 0.9rem;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  transition: all 0.3s ease;
  background-color: #f8f9fa;
}

.search-input:focus {
  outline: none;
  border-color: #42b983;
  background-color: white;
  box-shadow: 0 0 0 3px rgba(66, 185, 131, 0.1);
}

.search-icon {
  position: absolute;
  right: 1rem;
  width: 16px;
  height: 16px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23666' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Ccircle cx='11' cy='11' r='8'/%3E%3Cline x1='21' y1='21' x2='16.65' y2='16.65'/%3E%3C/svg%3E") no-repeat center;
  opacity: 0.5;
  transition: opacity 0.3s ease;
  pointer-events: none;
}

.search-input:focus+.search-icon {
  opacity: 1;
}

/* 登录按钮样式 */
.login-btn {
  padding: 0.6rem 1.5rem;
  border: none;
  border-radius: 20px;
  background-color: #42b983;
  color: white;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
}

.login-btn:hover {
  background-color: #3aa876;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

/* 购物车样式 */
.cart-box {
  position: relative;
  cursor: pointer;
  padding: 8px;
  border-radius: 50%;
  transition: background-color 0.3s ease;
}

.cart-box:hover {
  background-color: rgba(66, 185, 131, 0.1);
}

.cart-icon {
  width: 24px;
  height: 24px;
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23333' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Ccircle cx='9' cy='21' r='1'/%3E%3Ccircle cx='20' cy='21' r='1'/%3E%3Cpath d='M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6'/%3E%3C/svg%3E") no-repeat center;
  transition: transform 0.3s ease;
}

.cart-box:hover .cart-icon {
  transform: translateY(-2px);
}

.cart-count {
  position: absolute;
  top: 0;
  right: 0;
  background-color: #e74c3c;
  color: white;
  font-size: 12px;
  font-weight: bold;
  min-width: 18px;
  height: 18px;
  border-radius: 9px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
  transform: translate(25%, -25%);
  animation: bounce 0.3s ease;
}

@keyframes bounce {

  0%,
  100% {
    transform: translate(25%, -25%) scale(1);
  }

  50% {
    transform: translate(25%, -25%) scale(1.2);
  }
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .nav-items {
    gap: 1.5rem;
  }

  .search-input {
    width: 200px;
  }
}

@media (max-width: 768px) {
  .nav-container {
    padding: 0.8rem 1rem;
  }

  .nav-items {
    gap: 1rem;
  }

  .search-input {
    width: 160px;
  }

  .login-btn {
    padding: 0.5rem 1rem;
  }

  .cart-box {
    padding: 6px;
  }

  .cart-icon {
    width: 20px;
    height: 20px;
  }

  .cart-count {
    min-width: 16px;
    height: 16px;
    font-size: 10px;
  }
}

/* 添加用户头像和菜单样式 */
.user-profile {
  position: relative;
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 25px;
  transition: all 0.3s ease;
}

.user-profile:hover {
  background: rgba(76, 175, 80, 0.08);
}

.avatar {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #4CAF50, #45a049);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(76, 175, 80, 0.2);
  transition: all 0.3s ease;
}

.user-profile:hover .avatar {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.username {
  font-size: 14px;
  font-weight: 500;
  color: #333;
  transition: color 0.3s ease;
}

.user-profile:hover .username {
  color: #4CAF50;
}

.user-menu {
  position: absolute;
  top: calc(100% + 5px);
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  padding: 8px 0;
  min-width: 160px;
  transform-origin: top right;
  z-index: 1000;
}

.menu-item {
  padding: 12px 20px;
  display: flex;
  align-items: center;
  gap: 12px;
  color: #333;
  font-size: 14px;
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
}

.menu-item:hover {
  background: rgba(76, 175, 80, 0.08);
  color: #4CAF50;
  padding-left: 25px;
}

.menu-item:hover i {
  opacity: 1;
}

.menu-item::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 20px;
  right: 20px;
  height: 1px;
  background: #f0f0f0;
}

.menu-item:last-child::after {
  display: none;
}

/* 优化下拉菜单动画 */
.dropdown-enter-active {
  animation: dropdown-in 0.3s ease-out;
}

.dropdown-leave-active {
  animation: dropdown-in 0.3s ease-in reverse;
}

@keyframes dropdown-in {
  0% {
    opacity: 0;
    transform: translateY(-20px) scale(0.9);
  }

  50% {
    transform: translateY(5px) scale(1.02);
  }

  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.profile-icon,
.logout-icon {
  width: 16px;
  height: 16px;
  opacity: 0.7;
}

.profile-icon {
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2'/%3E%3Ccircle cx='12' cy='7' r='4'/%3E%3C/svg%3E") no-repeat center;
}

.logout-icon {
  background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpath d='M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4'/%3E%3Cpolyline points='16 17 21 12 16 7'/%3E%3Cline x1='21' y1='12' x2='9' y2='12'/%3E%3C/svg%3E") no-repeat center;
}

/* 下拉菜单动画 */
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.3s ease;
  transform-origin: top;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: scaleY(0);
}
</style>