<template>
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
        <div v-else class="orders-list">
            <div v-for="order in orders" :key="order.id" class="order-item">
                <div class="order-header">
                    <span class="order-date">下单时间：{{ order.date }}</span>
                    <span :class="['order-status', getStatusClass(order.status)]">
                        {{ order.status }}
                    </span>
                </div>

                <div class="order-content">
                    <div class="product-image">
                        <img :src="baseUrl + order.productInfo.imgpath" :alt="order.productInfo.name">
                    </div>
                    <div class="order-details">
                        <h3 class="product-name">{{ order.productInfo.name }}</h3>
                        <p class="product-spec">规格：{{ order.productInfo.standard }}</p>
                        <div class="order-info">
                            <span class="quantity">数量：{{ order.quantity }}</span>
                            <span class="price">￥{{ order.productInfo.price }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'
import { defineUser } from '../store/userStore'

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

        if (response.data?.code === 200 && Array.isArray(response.data.data)) {
            // 获取订单对应的商品信息
            const ordersWithProducts = await Promise.all(
                response.data.data.map(async (order) => {
                    try {
                        const productResponse = await request.get(`/shangpin/findShangpinById?id=${order.shangpinId}`)
                        if (productResponse.data?.data) {
                            return {
                                ...order,
                                productInfo: productResponse.data.data
                            }
                        }
                    } catch (error) {
                        console.error('获取商品信息失败:', error)
                        return null
                    }
                })
            )
            orders.value = ordersWithProducts.filter(order => order !== null)
        }
    } catch (error) {
        console.error('获取订单失败:', error)
    } finally {
        isLoading.value = false
    }
}

// 获取状态样式类
const getStatusClass = (status) => {
    switch (status) {
        case '未支付':
            return 'status-unpaid'
        case '已支付':
            return 'status-paid'
        default:
            return ''
    }
}

onMounted(() => {
    if (userStore.token) {
        fetchOrders()
    }
})
</script>

<style scoped>
.orders-section {
    background: white;
    border-radius: 12px;
    padding: 24px;
    margin-top: 24px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.section-title {
    font-size: 20px;
    color: #333;
    margin-bottom: 24px;
    padding-bottom: 16px;
    border-bottom: 1px solid #eee;
}

.orders-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.order-item {
    border: 1px solid #eee;
    border-radius: 8px;
    overflow: hidden;
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
    background: #f8f9fa;
    border-bottom: 1px solid #eee;
}

.order-date {
    color: #666;
    font-size: 14px;
}

.order-status {
    font-size: 14px;
    font-weight: 500;
}

.status-unpaid {
    color: #ff4d4f;
}

.status-paid {
    color: #52c41a;
}

.order-content {
    display: flex;
    padding: 16px;
    gap: 16px;
}

.product-image {
    width: 80px;
    height: 80px;
    flex-shrink: 0;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 4px;
}

.order-details {
    flex: 1;
}

.product-name {
    font-size: 16px;
    color: #333;
    margin-bottom: 8px;
}

.product-spec {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
}

.order-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.quantity {
    color: #666;
    font-size: 14px;
}

.price {
    color: #ff4d4f;
    font-weight: 500;
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
    .orders-section {
        padding: 16px;
        margin-top: 16px;
    }

    .order-content {
        flex-direction: column;
    }

    .product-image {
        width: 100%;
        height: 200px;
    }
}
</style>