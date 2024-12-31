<template>
    <div class="cart-page">
        <NavBar />

        <div class="cart-container">
            <div class="cart-header">
                <h1>我的购物车</h1>
                <span class="item-count">共 {{ totalItems }} 件商品</span>
            </div>

            <!-- 加载状态 -->
            <div v-if="isLoading" class="loading-state">
                加载中...
            </div>

            <!-- 购物车为空时显示 -->
            <div v-else-if="cartItems.length === 0" class="empty-cart">
                <div class="empty-cart-icon"></div>
                <p>购物车中没有未支付的商品~</p>
                <router-link to="/shop_rec" class="go-shopping-btn">
                    去逛逛
                </router-link>
            </div>

            <!-- 购物车商品列表 -->
            <div v-else class="cart-content">
                <div class="cart-items">
                    <div v-for="item in cartItems" :key="item.id" class="cart-item">
                        <div class="item-select">
                            <input type="checkbox" :checked="item.selected" @change="toggleSelect(item.id)">
                        </div>

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

                        <div class="item-delete">
                            <button @click="removeItem(item.id)" class="delete-btn">
                                删除
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 购物车底部 -->
                <div class="cart-footer">
                    <div class="select-all">
                        <input type="checkbox" :checked="allSelected" @change="toggleSelectAll">
                        <span>全选</span>
                    </div>

                    <div class="cart-summary">
                        <div class="summary-item">
                            <span>已选商品 {{ selectedCount }} 件</span>
                        </div>
                        <div class="summary-item">
                            <span>合计：</span>
                            <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
                        </div>
                        <button class="checkout-btn" @click="checkout" :disabled="selectedCount === 0">
                            结算
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import NavBar from '../../../components/NavBar.vue'
import request from '../../../utils/request'
import { defineUser } from '../../../store/userStore'

const userStore = defineUser()
const cartItems = ref([])
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
            // 只获取未支付的订单
            const orders = response.data.data.filter(order => order.status === '未支付')

            // 获取每个订单对应的商品信息
            const orderPromises = orders.map(async (order) => {
                try {
                    const productResponse = await request.get(`/shangpin/findShangpinById?id=${order.shangpinId}`)

                    if (productResponse.data?.data) {
                        const productInfo = productResponse.data.data
                        return {
                            id: order.id,
                            shangpinId: order.shangpinId,
                            name: productInfo.name,
                            price: productInfo.price,
                            image: baseUrl + productInfo.imgpath,
                            specification: productInfo.standard,
                            quantity: order.quantity,
                            selected: false,
                            date: order.date,
                            status: order.status
                        }
                    }
                } catch (error) {
                    console.error('获取商品信息失败:', error)
                    return null
                }
            })

            // 等待所有商品信息获取完成
            const results = await Promise.all(orderPromises)
            cartItems.value = results.filter(item => item !== null)
        }
    } catch (error) {
        console.error('获取订单失败:', error)
    } finally {
        isLoading.value = false
    }
}

// 计算属性
const totalItems = computed(() => {
    return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

const selectedCount = computed(() => {
    return cartItems.value.filter(item => item.selected).length
})

const totalPrice = computed(() => {
    return cartItems.value
        .filter(item => item.selected)
        .reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const allSelected = computed(() => {
    return cartItems.value.length > 0 && cartItems.value.every(item => item.selected)
})

// 方法
const toggleSelect = (id) => {
    const item = cartItems.value.find(item => item.id === id)
    if (item) {
        item.selected = !item.selected
    }
}

const toggleSelectAll = () => {
    const newState = !allSelected.value
    cartItems.value.forEach(item => {
        item.selected = newState
    })
}

const removeItem = async (orderId) => {
    if (!confirm('确定要删除这个订单吗？')) {
        return
    }

    try {
        const response = await request.post('/order/deleteOrder', {
            token: userStore.token,
            id: orderId
        })

        if (response.data?.code === 200) {
            await fetchOrders()
        } else {
            alert(response.data?.message || '删除失败')
        }
    } catch (error) {
        console.error('删除订单失败:', error)
        alert('删除失败，请稍后重试')
    }
}

const checkout = async () => {
    const selectedItems = cartItems.value.filter(item => item.selected)
    if (selectedItems.length === 0) {
        alert('请选择要结算的商品')
        return
    }

    try {
        const requestData = {
            token: userStore.token,
            oidList: selectedItems.map(item => item.id)
        }

        const response = await request.post('/order/payOrder', requestData)

        if (response.data?.code === 200) {
            alert('支付成功')
            // 重新获取购物车数据
            await fetchOrders()
        } else {
            alert(response.data?.message || '支付失败')
        }
    } catch (error) {
        console.error('支付失败:', error)
        alert('支付失败，请稍后重试')
    }
}

// 初始化
onMounted(() => {
    if (userStore.token) {
        fetchOrders()
    }
})
</script>

<style scoped>
.cart-page {
    min-height: 100vh;
    background-color: #f8f9fa;
}

.cart-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
}

.cart-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 2rem;
}

.cart-header h1 {
    font-size: 24px;
    color: #2c3e50;
    font-weight: 600;
}

.item-count {
    color: #666;
    font-size: 14px;
}

.empty-cart {
    text-align: center;
    padding: 4rem 0;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.empty-cart-icon {
    width: 120px;
    height: 120px;
    margin: 0 auto 1rem;
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23999' stroke-width='1' stroke-linecap='round' stroke-linejoin='round'%3E%3Ccircle cx='9' cy='21' r='1'/%3E%3Ccircle cx='20' cy='21' r='1'/%3E%3Cpath d='M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6'/%3E%3C/svg%3E") no-repeat center;
    opacity: 0.5;
}

.empty-cart p {
    color: #666;
    margin-bottom: 1.5rem;
}

.go-shopping-btn {
    display: inline-block;
    padding: 0.8rem 2rem;
    background: #42b983;
    color: white;
    border-radius: 4px;
    text-decoration: none;
    transition: all 0.3s ease;
}

.go-shopping-btn:hover {
    background: #3aa876;
    transform: translateY(-1px);
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
    grid-template-columns: auto 100px 2fr 1fr 1fr 1fr auto;
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

.item-price {
    color: #e74c3c;
    font-weight: 500;
}

.item-quantity {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.quantity-btn {
    width: 28px;
    height: 28px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.quantity-btn:hover:not(:disabled) {
    background: #f8f9fa;
    border-color: #42b983;
}

.quantity-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.item-quantity input {
    width: 50px;
    height: 28px;
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.item-total {
    color: #e74c3c;
    font-weight: 600;
}

.delete-btn {
    padding: 6px 12px;
    background: none;
    border: 1px solid #ff4d4f;
    color: #ff4d4f;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.delete-btn:hover {
    background: #ff4d4f;
    color: white;
}

.cart-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 2rem;
    background: #f8f9fa;
    border-top: 1px solid #eee;
    border-radius: 0 0 8px 8px;
}

.select-all {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.cart-summary {
    display: flex;
    align-items: center;
    gap: 2rem;
}

.summary-item {
    color: #666;
}

.total-price {
    color: #e74c3c;
    font-size: 20px;
    font-weight: 600;
    margin-left: 0.5rem;
}

.checkout-btn {
    padding: 0.8rem 2rem;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.checkout-btn:hover:not(:disabled) {
    background: #3aa876;
    transform: translateY(-1px);
}

.checkout-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
    transform: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .cart-container {
        padding: 1rem;
    }

    .cart-item {
        grid-template-columns: auto 1fr auto;
        gap: 0.5rem;
    }

    .item-price,
    .item-total {
        display: none;
    }

    .item-info {
        padding: 0;
    }

    .cart-footer {
        flex-direction: column;
        gap: 1rem;
    }

    .cart-summary {
        flex-direction: column;
        align-items: flex-end;
        gap: 0.5rem;
    }

    .checkout-btn {
        width: 100%;
    }
}

/* 添加状态样式 */
.item-status {
    font-size: 14px;
    color: #ff6b6b;
    margin-top: 4px;
}
</style>