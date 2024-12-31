<template>
    <div class="shop-info">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="main-content">
            <div v-if="isLoading" class="loading-state">
                加载中...
            </div>

            <div v-else-if="!productInfo.id" class="error-state">
                未找到商品信息
            </div>

            <div v-else>
                <div class="product-container">
                    <!-- 左侧主图 -->
                    <div class="product-image">
                        <img :src="productInfo.imgpath" :alt="productInfo.name"
                            @error="e => e.target.src = '/default-image.jpg'" />
                    </div>

                    <!-- 右侧信息 -->
                    <div class="product-info">
                        <div class="info-buttons">
                            <h2 class="product-name">{{ productInfo.name }}</h2>
                            <div class="score-badge">
                                {{ productInfo.score ? `评分: ${productInfo.score}` : '暂无评分' }}
                            </div>
                        </div>

                        <div class="info-group">
                            <div class="price-row">
                                <span class="price-label">价格:</span>
                                <span class="price-value">￥{{ productInfo.price }}</span>
                            </div>

                            <!-- 商品规格 -->
                            <div class="specs-row">
                                <span class="specs-label">规格:</span>
                                <div class="specs-value">{{ productInfo.standard }}</div>
                            </div>

                            <!-- 销量 -->
                            <div class="quantity-row">
                                <span class="quantity-label">销量:</span>
                                <span class="quantity-value">{{ productInfo.num }}</span>
                            </div>

                            <!-- 添加数量选择模块 -->
                            <div class="quantity-selector">
                                <span class="quantity-label">购买数量:</span>
                                <div class="quantity-control">
                                    <button class="quantity-btn" @click="decreaseQuantity"
                                        :disabled="quantity <= 1">-</button>
                                    <input type="number" v-model.number="quantity" min="1" @input="validateQuantity">
                                    <button class="quantity-btn" @click="increaseQuantity">+</button>
                                </div>
                            </div>

                        </div>

                        <!-- 底部购买按钮 -->
                        <div class="purchase-buttons">
                            <button class="cart-btn" @click="addToCart" :disabled="!userStore.token">
                                {{ userStore.token ? '添加到购物车' : '请先登录' }}
                            </button>
                            <button class="buy-btn" @click="buyNow" :disabled="!userStore.token">
                                {{ userStore.token ? '直接购买' : '请先登录' }}
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 商品描述 -->
                <div class="description-section">
                    <h3>商品描述</h3>
                    <div class="description-content">
                        {{ productInfo.description }}
                    </div>
                </div>

                <!-- 商家信息 -->
                <div class="merchant-section">
                    <h3>商家信息</h3>
                    <div v-if="merchantInfo" class="merchant-info">
                        <div class="merchant-header">
                            <div class="merchant-avatar">
                                <img v-if="merchantInfo.logo" :src="baseUrl + merchantInfo.logo"
                                    :alt="merchantInfo.name"
                                    @error="e => e.target.parentElement.innerHTML = merchantInfo.name?.charAt(0).toUpperCase()" />
                                <span v-else>{{ merchantInfo.name?.charAt(0).toUpperCase() }}</span>
                            </div>
                            <div class="merchant-details">
                                <h4 class="merchant-name">{{ merchantInfo.name }}</h4>
                                <p class="merchant-description">{{ merchantInfo.description || '暂无商家描述' }}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 评论区域 -->
                <div class="comment-section">
                    <div class="comment-header">
                        <div class="section-title">
                            <h2>评论区</h2>
                            <span class="comment-count">{{ comments.length }} 条评论</span>
                        </div>
                    </div>

                    <!-- 写评论 -->
                    <div class="write-comment">
                        <div class="comment-input">
                            <textarea v-model="newComment" :placeholder="isLoggedIn ? '写下你的评论...' : '请先登录后再发表评论...'"
                                :disabled="!isLoggedIn" @keyup.ctrl.enter="submitComment"></textarea>
                            <button class="submit-btn" @click="submitComment" :disabled="!isLoggedIn">
                                发布评论
                            </button>
                        </div>
                    </div>

                    <!-- 评论列表 -->
                    <div class="comments-list">
                        <div v-if="loading" class="loading-state">
                            加载中...
                        </div>
                        <template v-else>
                            <div v-if="comments.length === 0" class="no-comments">
                                暂无评论，来发表第一条评论吧！
                            </div>
                            <div v-else v-for="comment in comments" :key="comment.id" class="comment-item">
                                <div class="comment-user">
                                    <div class="user-avatar">
                                        <template v-if="comment.img">
                                            <img :src="baseUrl + comment.img" :alt="comment.username"
                                                @error="e => e.target.parentElement.innerHTML = comment.username?.charAt(0).toUpperCase()" />
                                        </template>
                                        <template v-else>
                                            {{ comment.username?.charAt(0).toUpperCase() }}
                                        </template>
                                    </div>
                                    <div class="user-info">
                                        <span class="username">{{ comment.username }}</span>
                                    </div>
                                </div>
                                <div class="comment-content">
                                    {{ comment.content }}
                                </div>
                                <div class="comment-footer">
                                    <span class="comment-time">{{ comment.date }}</span>
                                </div>
                            </div>
                        </template>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>

<script setup>
import NavBar from '../../../components/NavBar.vue'
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted, watch } from 'vue'
import request from '../../../utils/request'
import { defineUser } from '../../../store/userStore'
import Footer from '../../../components/Footer.vue'

const router = useRouter()
const route = useRoute()
const userStore = defineUser()
const baseUrl = 'http://localhost:8080/image/'
const isLoading = ref(true)
const comments = ref([])
const loading = ref(false)
const newComment = ref('')
const isLoggedIn = ref(false)
const merchantInfo = ref(null)

// 商品数据的响应式引用
const productInfo = ref({
    id: null,
    name: '',
    price: 0,
    description: '',
    imgpath: '',
    score: 0,
    num: 0,
    standard: '',
    type: '',
    group: '',
    storeId: ''
})

// 添加数量选择相关的响应式变量
const quantity = ref(1)

// 数量控制方法
const decreaseQuantity = () => {
    if (quantity.value > 1) {
        quantity.value--
    }
}

const increaseQuantity = () => {
    quantity.value++
}

const validateQuantity = () => {
    if (quantity.value < 1) {
        quantity.value = 1
    }
}

// 获取当前日期的方法
const getCurrentDate = () => {
    const date = new Date()
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}-${month}-${day}`
}

// 添加到购物车方法
const addToCart = async () => {
    if (!userStore.token) {
        alert('请先登录')
        return
    }

    try {
        const orderData = {
            date: getCurrentDate(),
            shangpinId: route.params.id,
            quantity: quantity.value
        }
        console.log(orderData)
        const tokenInfo = {
            token: userStore.token,
            orderInfo: orderData
        }
        console.log(tokenInfo)
        const response = await request.post('/order/orderShangpin', tokenInfo)

        if (response.data && response.data.code === 200) {
            alert('成功添加到购物车')
        } else {
            alert(response.data?.message || '添加失败')
        }
    } catch (error) {
        console.error('添加到购物车失败:', error)
        alert('添加失败，请稍后重试')
    }
}

// 直接购买方法
const buyNow = async () => {
    if (!userStore.token) {
        alert('请先登录')
        return
    }

    try {
        const orderData = {
            date: getCurrentDate(),
            shangpinId: route.params.id,
            quantity: quantity.value
        }

        const tokenInfo = {
            token: userStore.token,
            orderInfo: orderData
        }

        const response = await request.post('/order/purchase', tokenInfo)

        if (response.data && response.data.code === 200) {
            alert('购买成功')
            // 这里可以添加跳转到订单页面的逻辑
        } else {
            alert(response.data?.message || '购买失败')
        }
    } catch (error) {
        console.error('购买失败:', error)
        alert('购买失败，请稍后重试')
    }
}

// 获取评论数据
const fetchComments = async () => {
    try {
        const response = await request.get('/info/showComment', {
            params: {
                commentId: route.params.id,
                commentType: "shangpin"
            }
        })

        if (response.data && response.data.code === 200) {
            console.log(response.data.data)
            comments.value = Array.isArray(response.data.data) ? response.data.data : []
        } else {
            console.warn('获取评论失败:', response.data)
            comments.value = []
        }
    } catch (error) {
        console.error('获取评论失败:', error)
        comments.value = []
    }
}

// 修改发布评论的方法
const submitComment = async () => {
    if (!userStore.token) {
        alert('请先登录后再评论')
        return
    }

    if (!newComment.value.trim()) {
        alert('请输入评论内容')
        return
    }

    try {
        const commentData = {
            userId: userStore.id,
            shangpinId: route.params.id,
            content: newComment.value.trim(),
            date: getCurrentDate(),
        }

        const tokenInfo = {
            token: userStore.token,
            commentInfo: commentData
        }

        const response = await request.post('/info/addComment', tokenInfo)

        if (response.data && response.data.code === 200) {
            await fetchComments()  // 重新获取评论列表
            newComment.value = ''  // 清空输入框
        } else {
            console.error('评论失败:', response.data)
            alert(response.data.message || '发布评论失败')
        }
    } catch (error) {
        console.error('发布评论失败:', error)
        alert('发布评论失败，请稍后重试')
    }
}

// 获取商家信息
const fetchMerchantInfo = async (storeId) => {
    try {
        const response = await request.get('/merchant/findMerchantById', {
            params: { id: storeId }
        })

        if (response.data?.code === 200 && response.data.data) {
            merchantInfo.value = response.data.data
        }
    } catch (error) {
        console.error('获取商家信息失败:', error)
    }
}

// 获取商品详情
const getProductInfo = async () => {
    try {
        isLoading.value = true
        const response = await request.get(`/shangpin/findShangpinById?id=${route.params.id}`)

        if (response.data && response.data.data) {
            const data = response.data.data
            productInfo.value = {
                ...data,
                imgpath: baseUrl + data.imgpath
            }
            // 获取商家信息
            if (data.storeId) {
                await fetchMerchantInfo(data.storeId)
            }
        }
    } catch (error) {
        console.error('获取商品信息失败:', error)
    } finally {
        isLoading.value = false
    }
}

// 检查登录状态
const checkLoginStatus = () => {
    isLoggedIn.value = !!userStore.token
}

// 监听用户token变化
watch(() => userStore.token, (newToken) => {
    isLoggedIn.value = !!newToken
})

// 初始化
onMounted(async () => {
    await getProductInfo()
    checkLoginStatus()
    await fetchComments()
})
</script>

<style scoped>
.shop-info {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 80px;
}

/* 添加导航栏包装器样式 */
.nav-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 100;
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.main-content {
    max-width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
}

.product-container {
    display: flex;
    gap: 50px;
    background: white;
    padding: 40px;
    border-radius: 16px;
    margin-bottom: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.product-image {
    flex: 1;
    max-width: 500px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 12px;
    transition: transform 0.3s ease;
}

.product-image img:hover {
    transform: scale(1.02);
}

.product-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 25px;
}

.info-buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.product-name {
    font-size: 28px;
    margin: 0;
    color: #333;
    font-weight: 600;
    line-height: 1.4;
}

.score-badge {
    display: inline-block;
    padding: 6px 16px;
    background: linear-gradient(45deg, #ff9800, #ff5722);
    color: white;
    border-radius: 20px;
    font-size: 15px;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(255, 152, 0, 0.3);
}

.info-group {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.price-row,
.specs-row,
.quantity-row {
    display: flex;
    align-items: center;
    gap: 15px;
    padding: 10px 0;
}

[class$="-label"] {
    color: #666;
    min-width: 70px;
    font-size: 15px;
}

.price-value {
    font-size: 32px;
    color: #ff6b6b;
    font-weight: 600;
}

.specs-value,
.quantity-value {
    color: #444;
    font-size: 15px;
    line-height: 1.6;
}

.purchase-buttons {
    display: flex;
    gap: 20px;
    margin-top: auto;
}

.cart-btn,
.buy-btn {
    flex: 1;
    padding: 14px 0;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.cart-btn {
    background: white;
    border: 2px solid #4CAF50;
    color: #4CAF50;
}

.cart-btn:hover {
    background: rgba(76, 175, 80, 0.1);
    transform: translateY(-2px);
}

.buy-btn {
    background: #4CAF50;
    color: white;
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.buy-btn:hover {
    background: #43a047;
    transform: translateY(-2px);
}

.description-section {
    background: white;
    padding: 30px;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.description-section h3 {
    font-size: 20px;
    margin: 0 0 20px 0;
    color: #333;
    font-weight: 600;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.description-content {
    color: #555;
    line-height: 1.8;
    font-size: 15px;
}

.loading-state,
.error-state {
    text-align: center;
    padding: 60px;
    font-size: 18px;
    color: #666;
    background: white;
    border-radius: 16px;
    margin: 40px 0;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.error-state {
    color: #f44336;
}

/* 响应式设计 */
@media (max-width: 1024px) {
    .product-container {
        padding: 30px;
        gap: 30px;
    }
}

@media (max-width: 768px) {
    .product-container {
        flex-direction: column;
    }

    .product-image {
        max-width: 100%;
    }

    .product-name {
        font-size: 24px;
    }

    .price-value {
        font-size: 28px;
    }

    .purchase-buttons {
        margin-top: 20px;
    }

    .shop-info {
        padding-top: 70px;
    }

    .main-content {
        margin: 30px auto;
    }
}

@media (max-width: 480px) {
    .main-content {
        padding: 0 15px;
    }

    .product-container {
        padding: 20px;
    }

    .info-buttons {
        flex-direction: column;
        align-items: flex-start;
        gap: 15px;
    }

    .score-badge {
        align-self: flex-start;
    }

    .purchase-buttons {
        flex-direction: column;
    }

    .shop-info {
        padding-top: 60px;
    }

    .main-content {
        margin: 20px auto;
    }
}

/* 添加评论区域的样式，可以复用新闻评论的样式 */
.comment-section {
    background: white;
    border-radius: 16px;
    padding: 40px;
    margin: 30px 0;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.comment-header {
    margin-bottom: 20px;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 12px;
}

.section-title h2 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin: 0;
}

.comment-count {
    color: #666;
    font-size: 0.9rem;
}

/* 评论列表 */
.comment-item {
    padding: 24px;
    border-bottom: 1px solid #eee;
}

.comment-user {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 12px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    font-weight: 500;
    overflow: hidden;
}

.user-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.user-info {
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.username {
    font-weight: 500;
    color: #333;
    font-size: 14px;
}

.comment-content {
    margin: 12px 0;
    line-height: 1.6;
    color: #333;
}

.comment-footer {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    margin-top: 8px;
}

.comment-time {
    font-size: 12px;
    color: #999;
}

/* 加载和无评论状态 */
.loading-state,
.no-comments {
    text-align: center;
    padding: 40px;
    color: #999;
    font-size: 14px;
    background: #f8f9fa;
    border-radius: 8px;
}

/* 禁用状态样式 */
.comment-input textarea:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
}

.submit-btn:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.submit-btn:disabled:hover {
    background-color: #ccc;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .comment-section {
        padding: 20px;
    }

    .section-title h2 {
        font-size: 1.2rem;
    }

    .comment-input textarea {
        height: 100px;
    }
}

@media (max-width: 480px) {
    .comment-section {
        padding: 15px;
    }

    .comment-item {
        padding: 15px;
    }

    .user-avatar {
        width: 32px;
        height: 32px;
    }
}

/* 修改评论列表样式，移除上方空白 */
.comments-list {
    margin-top: 0;
}

.no-comments {
    text-align: center;
    padding: 40px;
    color: #999;
    font-size: 14px;
    background: #f8f9fa;
    border-radius: 8px;
}

/* 移除写评论相关样式
.write-comment,
.comment-input,
.submit-btn {
    display: none;
} */

/* 数量选择器样式 */
.quantity-selector {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-top: 15px;
}

.quantity-control {
    display: flex;
    align-items: center;
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
}

.quantity-btn {
    padding: 8px 12px;
    background: #f5f5f5;
    border: none;
    cursor: pointer;
    transition: all 0.3s ease;
}

.quantity-btn:hover:not(:disabled) {
    background: #e0e0e0;
}

.quantity-btn:disabled {
    cursor: not-allowed;
    opacity: 0.5;
}

.quantity-control input {
    width: 60px;
    text-align: center;
    border: none;
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
    padding: 8px 0;
}

.quantity-control input::-webkit-inner-spin-button,
.quantity-control input::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

/* 修改购买按钮样式 */
.cart-btn:disabled,
.buy-btn:disabled {
    background-color: #ccc;
    cursor: not-allowed;
    border-color: #ccc;
}

.cart-btn:disabled:hover,
.buy-btn:disabled:hover {
    transform: none;
    box-shadow: none;
}

/* 评论区域样式 */
.write-comment {
    background: white;
    border-radius: 16px;
    margin: 30px 0;
}

.comment-input {
    position: relative;
}

.comment-input textarea {
    width: 100%;
    height: 120px;
    padding: 16px;
    border: 1px solid #e0e0e0;
    border-radius: 12px;
    resize: none;
    font-size: 1rem;
    transition: border-color 0.3s ease;
}

.comment-input textarea:focus {
    outline: none;
    border-color: #42b983;
}

.comment-input textarea:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
}

.submit-btn {
    position: absolute;
    bottom: 16px;
    right: 16px;
    padding: 8px 24px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    transition: background 0.3s ease;
}

.submit-btn:hover:not(:disabled) {
    background: #3aa876;
}

.submit-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
}

/* 评论列表样式 */
.comment-section {
    background: white;
    border-radius: 16px;
    padding: 40px;
    margin: 30px 0;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.comment-header {
    margin-bottom: 20px;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 12px;
}

.section-title h2 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin: 0;
}

.comment-count {
    color: #666;
    font-size: 0.9rem;
}

.comment-item {
    padding: 24px;
    border-bottom: 1px solid #eee;
}

.comment-user {
    display: flex;
    align-items: center;
    gap: 12px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    font-weight: 500;
    overflow: hidden;
}

.user-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.username {
    font-weight: 500;
    color: #333;
    font-size: 14px;
}

.comment-content {
    margin: 12px 0;
    line-height: 1.6;
    color: #333;
}

.comment-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 8px;
}

.comment-time {
    font-size: 12px;
    color: #999;
}

.loading-state,
.no-comments {
    text-align: center;
    padding: 40px;
    color: #999;
    font-size: 14px;
    background: #f8f9fa;
    border-radius: 8px;
}

/* 响应式调整 */
@media (max-width: 768px) {

    .write-comment,
    .comment-section {
        padding: 20px;
        margin: 20px 0;
    }

    .section-title h2 {
        font-size: 1.2rem;
    }

    .comment-input textarea {
        height: 100px;
    }
}

@media (max-width: 480px) {

    .write-comment,
    .comment-section {
        padding: 15px;
        margin: 15px 0;
    }

    .comment-item {
        padding: 15px;
    }

    .user-avatar {
        width: 32px;
        height: 32px;
    }
}

/* 商家信息模块样式 */
.merchant-section {
    background: white;
    border-radius: 12px;
    padding: 24px;
    margin: 24px 0;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.merchant-section h3 {
    font-size: 18px;
    color: #333;
    margin-bottom: 20px;
    padding-bottom: 12px;
    border-bottom: 1px solid #eee;
}

.merchant-info {
    padding: 16px;
}

.merchant-header {
    display: flex;
    align-items: center;
    gap: 20px;
}

.merchant-avatar {
    width: 64px;
    height: 64px;
    border-radius: 50%;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
}

.merchant-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.merchant-avatar span {
    font-size: 24px;
    color: #666;
    font-weight: 500;
}

.merchant-details {
    flex: 1;
}

.merchant-name {
    font-size: 18px;
    color: #333;
    margin-bottom: 8px;
}

.merchant-description {
    font-size: 14px;
    color: #666;
    line-height: 1.5;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .merchant-section {
        padding: 16px;
        margin: 16px 0;
    }

    .merchant-header {
        flex-direction: column;
        text-align: center;
        gap: 12px;
    }

    .merchant-avatar {
        margin: 0 auto;
    }
}
</style>