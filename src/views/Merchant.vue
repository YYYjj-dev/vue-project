<template>
  <div class="profile">
    <div class="nav-wrapper">
      <NavBar />
    </div>

    <div class="profile-container">
      <!-- 用户基本信息卡片 -->
      <div class="profile-header">
        <div class="avatar">
          <template v-if="userInfo.img">
            <img :src="baseUrl + userInfo.img" :alt="userInfo.username" @error="handleAvatarError" />
          </template>
          <template v-else>
            {{ userInfo.username?.charAt(0).toUpperCase() }}
          </template>
        </div>
        <div class="user-info">
          <h1 class="username">{{ userInfo.username }}</h1>
          <p class="user-type">商家</p>
        </div>
      </div>

      <!-- 用户详细信息 -->
      <div class="profile-content">
        <div class="profile-section">
          <h2>个人信息</h2>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">用户名</span>
              <span class="value">{{ userInfo.username }}</span>
            </div>
            <div class="info-item">
              <span class="label">账户类型</span>
              <span class="value">商家</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 添加商家订单部分 -->
      <div class="orders-section">
        <h2 class="section-title">商家订单</h2>

        <!-- 加载状态 -->
        <div v-if="isLoading" class="loading-state">
          加载中...
        </div>

        <!-- 无订单时显示 -->
        <div v-else-if="orders.length === 0" class="empty-orders">
          暂无订单记录
        </div>

        <!-- 订单列表 -->
        <div v-else class="cart-content">
          <div class="cart-items">
            <div v-for="item in orders" :key="item.id" class="cart-item">
              <div class="item-image">
                <img :src="item.image" :alt="item.name">
              </div>

              <div class="item-info">
                <h3 class="item-name">{{ item.name }}</h3>
                <p class="item-spec">{{ item.specification }}</p>
                <p class="item-date">下单时间：{{ item.date }}</p>
                <p class="item-status">状态：{{ item.status }}</p>
              </div>

              <div class="item-price">
                ¥{{ item.price.toFixed(2) }}
              </div>

              <div class="item-quantity">
                数量：{{ item.quantity }}
              </div>

              <div class="item-total">
                ¥{{ (item.price * item.quantity).toFixed(2) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from '../components/NavBar.vue'
import { defineUser } from '../store/userStore'
import { ref, onMounted, watch } from 'vue'
import request from '../utils/request'

const userStore = defineUser()
const orders = ref([])
const isLoading = ref(true)
const baseUrl = 'http://localhost:8080/image/'
const userInfo = ref({
  username: userStore.username,
  type: userStore.type,
  img: userStore.img
})

// 头像加载失败处理
const handleAvatarError = (e) => {
  e.target.style.display = 'none'
  e.target.parentElement.innerHTML = userInfo.value.username?.charAt(0).toUpperCase()
}

// 监听用户信息变化
watch(() => [userStore.username, userStore.type, userStore.img], ([newUsername, newType, newImg]) => {
  userInfo.value = {
    ...userInfo.value,
    username: newUsername,
    type: newType,
    img: newImg
  }
})

// 获取商家订单信息
const fetchMerchantOrders = async () => {
  try {
    isLoading.value = true
    const response = await request.post('/order/findOrderByMUsername', {
      token: userStore.token
    })
    console.log(userStore.token)
    if (response.data?.code === 200 && Array.isArray(response.data.data)) {
      // 获取每个订单对应的商品信息
      const orderPromises = response.data.data.map(async (order) => {
        try {
          const productResponse = await request.get(`/shangpin/findShangpinById?id=${order.shangpinId}`)
          if (productResponse.data?.code === 200 && productResponse.data?.data) {
            const productInfo = productResponse.data.data
            return {
              id: order.id,
              name: productInfo.name,
              price: productInfo.price,
              image: baseUrl + productInfo.imgpath,
              specification: productInfo.standard,
              quantity: order.quantity,
              date: order.date,
              status: order.status
            }
          }
          return null
        } catch (error) {
          console.error('获取商品信息失败:', error)
          return null
        }
      })

      // 等待所有商品信息获取完成
      const results = await Promise.all(orderPromises)
      orders.value = results.filter(item => item !== null)
    }
  } catch (error) {
    console.error('获取订单失败:', error)
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  if (userStore.token) {
    fetchMerchantOrders()
  }
})
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
  gap: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.avatar {
  width: 100px;
  height: 100px;
  background: linear-gradient(135deg, #4CAF50, #45a049);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
  overflow: hidden;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  flex: 1;
}

.username {
  font-size: 28px;
  color: #333;
  margin: 0 0 8px 0;
}

.user-type {
  color: #666;
  font-size: 14px;
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

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.info-item {
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.label {
  color: #666;
  font-size: 14px;
}

.value {
  color: #333;
  font-size: 16px;
  font-weight: 500;
}

@media (max-width: 768px) {
  .profile-header {
    padding: 30px;
    gap: 20px;
  }

  .avatar {
    width: 80px;
    height: 80px;
    font-size: 32px;
  }

  .username {
    font-size: 24px;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .profile-header {
    padding: 20px;
    flex-direction: column;
    text-align: center;
  }

  .profile-content {
    padding: 20px;
  }
}

/* 添加订单相关样式 */
.orders-section {
  margin-top: 30px;
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.section-title {
  font-size: 20px;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.cart-content {
  background: white;
  border-radius: 8px;
}

.cart-items {
  padding: 1rem;
}

.cart-item {
  display: grid;
  grid-template-columns: 100px 2fr 1fr 1fr 1fr;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

.cart-item:last-child {
  border-bottom: none;
}

.item-image img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.item-info {
  padding: 0 1rem;
}

.item-name {
  font-size: 16px;
  color: #2c3e50;
  margin-bottom: 0.5rem;
}

.item-spec {
  font-size: 14px;
  color: #666;
}

.item-date {
  font-size: 14px;
  color: #666;
  margin-top: 4px;
}

.item-status {
  font-size: 14px;
  color: #ff6b6b;
  margin-top: 4px;
}

.item-price {
  color: #e74c3c;
  font-weight: 500;
}

.item-quantity {
  color: #666;
  font-size: 14px;
}

.item-total {
  color: #e74c3c;
  font-weight: 600;
}

.loading-state,
.empty-orders {
  text-align: center;
  padding: 40px;
  color: #999;
  background: #f8f9fa;
  border-radius: 8px;
}

@media (max-width: 768px) {
  .cart-item {
    grid-template-columns: 1fr;
    gap: 0.5rem;
  }

  .item-image img {
    width: 100%;
    height: 200px;
  }

  .item-info {
    padding: 0;
  }

  .item-price,
  .item-total {
    text-align: right;
  }
}
</style>
