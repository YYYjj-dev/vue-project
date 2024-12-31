<template>
    <div class="profile-container">
        <div class="orders-section">
            <h2 class="section-title">我的订单</h2>

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
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'
import { defineUser } from '../../store/userStore'

const userStore = defineUser()
const orders = ref([])
const isLoading = ref(true)
const baseUrl = 'http://localhost:8080/image/'

// 获取订单信息
const fetchOrders = async () => {
    try {
        isLoading.value = true
        const response = await request.post('/order/findOrderByUid', {
            token: userStore.token
        })

        console.log('订单响应:', response.data) // 调试日志

        if (response.data?.code === 200 && Array.isArray(response.data.data)) {
            // 获取每个订单对应的商品信息
            const orderPromises = response.data.data.map(async (order) => {
                try {
                    const productResponse = await request.get(`/shangpin/findShangpinById?id=${order.shangpinId}`)
                    console.log('商品信息:', productResponse.data) // 调试日志

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

            try {
                // 等待所有商品信息获取完成
                const results = await Promise.all(orderPromises)
                orders.value = results.filter(item => item !== null)
                console.log('最终订单数据:', orders.value) // 调试日志
            } catch (error) {
                console.error('处理订单数据失败:', error)
            }
        }
    } catch (error) {
        console.error('获取订单失败:', error)
    } finally {
        isLoading.value = false
    }
}

// 修改初始化逻辑
onMounted(() => {
    if (userStore.token) {
        fetchOrders()
    } else {
        console.log('用户未登录') // 调试日志
    }
})
</script>

<style scoped>
.profile-container {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 2rem;
}

.cart-content {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
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
    .profile-container {
        padding: 1rem;
    }

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