<template>
    <div class="shop-sel-info1">
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>

        <!-- 加载状态显示 -->
        <div v-if="isLoading" class="loading-state">
            正在加载商家信息...
        </div>

        <!-- 商家信息显示 -->
        <template v-else>
            <!-- 店铺头部信息 -->
            <div class="shop-header">
                <div class="shop-basic-info">
                    <div class="logo">
                        <template v-if="storeInfo.logo">
                            <img :src="baseUrl + storeInfo.logo" :alt="storeInfo.name">
                        </template>
                        <template v-else>
                            {{ storeInfo.name?.charAt(0)?.toUpperCase() || 'S' }}
                        </template>
                    </div>
                    <div class="shop-info">
                        <h1 class="shop-name">{{ storeInfo.name }}</h1>
                        <span class="shop-type">经营类型: {{ storeInfo.type }}</span>
                    </div>
                </div>

                <div class="search-container">
                    <div class="search-box">
                        <i class="search-icon" @click="handleSearch" :class="{ 'clickable': searchText }">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
                                fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                stroke-linejoin="round">
                                <circle cx="11" cy="11" r="8"></circle>
                                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                            </svg>
                        </i>
                        <input type="text" class="search-input" placeholder="搜索店内商品..." v-model="searchText"
                            @keyup.enter="handleSearch">
                    </div>
                </div>
            </div>

            <!-- 分类导航 -->
            <div class="nav-tabs">
                <button class="tab-btn" :class="{ active: activeTab === 'recommend' }"
                    @click="switchTab('recommend')">推荐</button>
                <button class="tab-btn" :class="{ active: activeTab === 'products' }"
                    @click="switchTab('products')">商品</button>
                <button class="tab-btn" :class="{ active: activeTab === 'new' }" @click="switchTab('new')">新品</button>
            </div>

            <!-- 根据选项卡显示不同内容 -->
            <template v-if="activeTab === 'recommend'">
                <!-- 热销产品部分 -->
                <div class="hot-products-section">
                    <h3 class="section-title">热销产品</h3>
                    <div class="products-grid">
                        <div v-for="product in topEightProducts" :key="product.id" class="product-card"
                            @click="goToProductDetail(product.id)">
                            <div class="product-image">
                                <img :src="baseUrl + product.imgpath" :alt="product.name" @error="handleImageError">
                                <div class="overlay">
                                    <span class="view-details">查看详情</span>
                                </div>
                            </div>
                            <div class="product-info">
                                <div class="product-name">{{ product.name }}</div>
                                <div class="product-price">¥{{ product.price }}</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 销售榜单部分 -->
                <div class="sales-ranking">
                    <h3 class="section-title">销售榜单</h3>
                    <div class="ranking-list">
                        <div v-for="(product, index) in topThreeProducts" :key="product.id" class="ranking-card"
                            @click="goToProductDetail(product.id)">
                            <div class="rank-badge">TOP{{ index + 1 }}</div>
                            <div>
                                <div class="card-content">
                                    <div class="product-image">
                                        <img :src="baseUrl + product.imgpath" :alt="product.name"
                                            @error="handleImageError">
                                        <div class="overlay">
                                            <span class="view-details">查看详情</span>
                                        </div>
                                    </div>
                                    <div class="product-info">
                                        <div class="product-name">{{ product.name }}</div>
                                        <div class="product-price">¥{{ product.price }}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>

            <!-- 商品列表部分 -->
            <template v-else-if="activeTab === 'products'">
                <div class="all-products-section">
                    <div class="products-container">
                        <div class="products-grid all-products">
                            <div v-for="product in allProducts" :key="product.id" class="product-card"
                                @click="goToProductDetail(product.id)">
                                <div class="product-image">
                                    <img :src="baseUrl + product.imgpath" :alt="product.name" @error="handleImageError">
                                    <div class="overlay">
                                        <span class="view-details">查看详情</span>
                                    </div>
                                </div>
                                <div class="product-info">
                                    <div class="product-name">{{ product.name }}</div>
                                    <div class="product-price">¥{{ product.price }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>

            <!-- 新品部分 -->
            <template v-else-if="activeTab === 'new'">
                <div class="new-products-section">
                    <div class="new-products-container">
                        <!-- 大卡片 -->
                        <div v-if="newProducts[0]" class="new-product-card large"
                            @click="goToProductDetail(newProducts[0].id)">
                            <div class="product-image-wrapper">
                                <img :src="baseUrl + newProducts[0].imgpath" :alt="newProducts[0].name"
                                    @error="handleImageError">
                                <div class="overlay">
                                    <span class="view-details">查看详情</span>
                                </div>
                            </div>
                        </div>

                        <!-- 小卡片容器 -->
                        <div class="small-cards-container">
                            <div v-for="product in newProducts.slice(1, 3)" :key="product.id"
                                class="new-product-card small" @click="goToProductDetail(product.id)">
                                <div class="product-image-wrapper">
                                    <img :src="baseUrl + product.imgpath" :alt="product.name" @error="handleImageError">
                                    <div class="overlay">
                                        <span class="view-details">查看详情</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </template>
        </template>
    </div>
</template>

<script setup>
import NavBar from '../../../components/NavBar.vue'
import { ref, onMounted, watch, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import request from '../../../utils/request'

const router = useRouter()
const route = useRoute()
const baseUrl = 'http://localhost:8080/image/'
const activeTab = ref('recommend')
const storeInfo = ref({
    id: '',
    username: '',
    name: '',
    logo: '',
    description: '',
    type: '',
    products: []
})
const isLoading = ref(true)

// 获取商品数据的方法
const fetchProducts = async (merchantId) => {
    try {
        const response = await request.get('/shangpin/findShangpinByMid', {
            params: { mid: merchantId }
        })
        console.log('商品数据响应:', response)

        if (response.data?.code === 200) {
            storeInfo.value.products = response.data.data || []
        } else {
            storeInfo.value.products = []
            console.error('获取商品数据失败:', response.data)
        }
    } catch (error) {
        console.error('获取商品数据错误:', error)
        storeInfo.value.products = []
    }
}

// 修改商家信息加载方法
const loadStoreData = async () => {
    try {
        const storeId = route.params.id
        console.log('获取到的店铺ID:', storeId)

        if (!storeId) {
            console.error('店铺ID不存在')
            return
        }

        // 调用接口获取商家信息
        const response = await request.get('/merchant/findMerchantById', {
            params: {
                id: storeId
            }
        })

        console.log('商家信息接口响应:', response)

        if (response.data?.code === 200 && response.data?.data) {
            const merchantData = response.data.data

            // 更新商家基本信息
            storeInfo.value = {
                id: merchantData.id,
                username: merchantData.username,
                name: merchantData.name || route.query.name,
                logo: merchantData.logo,
                description: merchantData.description || '暂无描述',
                type: merchantData.type || '未知类型',
                products: []
            }

            // 获取商品数据
            await fetchProducts(storeId)

            console.log('更新后的商家信息:', storeInfo.value)
        } else {
            console.error('未找到商家信息')
            storeInfo.value = {
                id: '',
                username: '',
                name: route.query.name || '',
                logo: '',
                description: '暂无描述',
                type: '未知类型',
                products: []
            }
        }
    } catch (error) {
        console.error('加载商家信息错误:', error)
    } finally {
        isLoading.value = false
    }
}

// 初始化数据
const initData = async () => {
    try {
        isLoading.value = true
        await loadStoreData()
    } catch (error) {
        console.error('初始化数据失败:', error)
    } finally {
        isLoading.value = false
    }
}

// 修改监听逻辑
watch(() => route.params.id, (newId) => {
    if (newId) {
        initData()
    }
}, { immediate: true })

// 初始化加载
onMounted(() => {
    if (route.params.id) {
        initData()
    }
})

// 计算热销商品（前8个）
const topEightProducts = computed(() => {
    return [...(storeInfo.value.products || [])]
        .sort((a, b) => b.num - a.num)
        .slice(0, 8)
})

// 计算销售榜单（前3个）
const topThreeProducts = computed(() => {
    return [...(storeInfo.value.products || [])]
        .sort((a, b) => b.num - a.num)
        .slice(0, 3)
})

// 添加跳转方法
const goToProductDetail = (productId) => {
    router.push({
        name: 'shop_rec_info',
        params: { id: productId }
    }).catch(err => {
        console.error('路由跳转错误:', err)
    })
}

// 切换标签页方法
const switchTab = (tab) => {
    activeTab.value = tab
}

// 计算所有商品
const allProducts = computed(() => {
    return storeInfo.value.products || []
})

// 修改新品计算方法，获取最后三条数据
const newProducts = computed(() => {
    const products = storeInfo.value.products || []
    return products.slice(-3).reverse() // 获取最后三条并反转顺序
})

// 添加搜索相关的状态和方法
const searchText = ref('')

// 处理搜索
const handleSearch = () => {
    if (!searchText.value.trim()) return

    router.push({
        name: 'shop_sel_info_search',
        params: {
            id: route.params.id  // 传递当前商家ID
        },
        query: {
            keyword: searchText.value.trim()  // 传递搜索关键词
        }
    })
}
</script>

<style scoped>
.shop-sel-info1 {
    min-height: 100vh;
    background-color: #f8f9fa;
    ;
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

/* 店铺头部样式 */
.shop-header {
    position: relative;
    background: white;
    padding: 30px;
    display: flex;
    align-items: center;
    gap: 30px;
    margin-bottom: 20px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.logo {
    width: 100px;
    height: 100px;
    background: linear-gradient(145deg, #f0f0f0, #ffffff);
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 12px;
    overflow: hidden;
    font-size: 32px;
    color: #4CAF50;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.logo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.shop-info {
    flex: 1;
}

.shop-name {
    font-size: 24px;
    font-weight: 600;
    color: #333;
    margin-bottom: 8px;
}

.shop-desc {
    font-size: 14px;
    color: #666;
    margin-bottom: 10px;
    line-height: 1.6;
}

.shop-type {
    display: inline-block;
    padding: 4px 12px;
    background: #f0f7f0;
    color: #4CAF50;
    border-radius: 15px;
    font-size: 13px;
    font-weight: 500;
}

.search-container {
    width: 100%;
    padding: 20px 0;
    display: flex;
    justify-content: center;
}

.search-box {
    width: 100%;
    max-width: 600px;
    position: relative;
    display: flex;
    align-items: center;
}

.search-icon {
    position: absolute;
    left: 20px;
    color: #999;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
    cursor: default;
}

.search-icon.clickable {
    cursor: pointer;
    color: #42b983;
}

.search-icon.clickable:hover {
    transform: scale(1.1);
}

.search-input {
    width: 100%;
    padding: 14px 24px 14px 56px;
    font-size: 16px;
    border: 2px solid #eaeaea;
    border-radius: 30px;
    outline: none;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    background-color: white;
}

.search-input:focus {
    border-color: #42b983;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.1);
}

.search-input:focus+.search-icon,
.search-box:hover .search-icon {
    color: #42b983;
}

.search-input::placeholder {
    color: #999;
    font-weight: 400;
}

.order-btn {
    padding: 8px 24px;
    background: white;
    color: #333;
    border: 1px solid #ddd;
    border-radius: 20px;
    cursor: pointer;
}

.order-btn:hover {
    background: #f5f5f5;
}

/* 导航标签样式 */
.nav-tabs {
    display: flex;
    justify-content: center;
    gap: 30px;
    padding: 20px;
    background: white;
    margin-top: 20px;
}

.tab-btn {
    padding: 8px 30px;
    border: none;
    background: none;
    cursor: pointer;
    font-size: 16px;
}

.tab-btn.active {
    color: #4CAF50;
    border-bottom: 2px solid #4CAF50;
}

/* 热销产品部分样式 */
.hot-products-section {
    margin: 30px auto;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    max-width: 1200px;
    width: 95%;
}

.section-title {
    font-size: 22px;
    font-weight: 600;
    color: #333;
    margin-bottom: 30px;
    text-align: center;
    position: relative;
    padding-bottom: 15px;
}

.section-title::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 60px;
    height: 3px;
    background: #4CAF50;
    border-radius: 2px;
}

.products-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 25px;
    padding: 10px;
    max-width: 1200px;
    margin: 0 auto;
}

.product-card {
    aspect-ratio: auto;
    border-radius: 12px;
    overflow: hidden;
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    display: flex;
    flex-direction: column;
    width: 100%;
    max-width: 260px;
    margin: 0 auto;
    cursor: pointer;
}

.product-card:hover {
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.product-image {
    position: relative;
    width: 100%;
    aspect-ratio: 1;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-card:hover .product-image img {
    transform: scale(1.08);
}

.product-card:hover .overlay {
    opacity: 1;
}

.product-card:hover .view-details {
    transform: translateY(0);
}

.product-info {
    padding: 12px;
    background: white;
}

.product-name {
    font-size: 14px;
    color: #333;
    margin-bottom: 6px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.product-price {
    font-size: 16px;
    color: #f56c6c;
    font-weight: 500;
}

/* 销售榜单部分样式 */
.sales-ranking {
    margin: 30px auto;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    max-width: 1200px;
    width: 95%;
}

.ranking-list {
    display: flex;
    justify-content: center;
    gap: 40px;
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

.ranking-card {
    flex: 1;
    width: 260px;
    max-width: 260px;
    position: relative;
    border-radius: 12px;
    background: white;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    overflow: visible;
    cursor: pointer;
}

.rank-badge {
    position: absolute;
    top: -15px;
    left: 50%;
    transform: translateX(-50%);
    padding: 8px 24px;
    background: white;
    color: #333;
    border-radius: 20px;
    font-weight: 600;
    font-size: 15px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    z-index: 2;
}

.ranking-card:nth-child(1) .rank-badge {
    background: linear-gradient(45deg, #FFD700, #FFA500);
    color: white;
}

.ranking-card:nth-child(2) .rank-badge {
    background: linear-gradient(45deg, #C0C0C0, #A9A9A9);
    color: white;
}

.ranking-card:nth-child(3) .rank-badge {
    background: linear-gradient(45deg, #CD7F32, #8B4513);
    color: white;
}

.card-content {
    width: 100%;
    height: 100%;
    border-radius: 12px;
    overflow: hidden;
}

.ranking-card .product-image {
    position: relative;
    width: 100%;
    aspect-ratio: 4/3;
    overflow: hidden;
}

.ranking-card .product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.ranking-card:hover .product-image img {
    transform: scale(1.1);
}

.ranking-card:hover .overlay {
    opacity: 1;
}

.ranking-card:hover .view-details {
    transform: translateY(0);
}

.ranking-card .product-info {
    position: relative;
    bottom: auto;
    padding: 15px;
    background: white;
    border-top: 1px solid rgba(0, 0, 0, 0.05);
}

.ranking-card:hover {
    transform: translateY(-6px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
}

.ranking-card .product-name {
    color: #333;
    font-weight: 500;
    margin-bottom: 8px;
}

.ranking-card .product-price {
    font-size: 18px;
    color: #f56c6c;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .product-card {
        max-width: 220px;
    }
}

@media (max-width: 992px) {
    .products-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 768px) {
    .products-grid {
        grid-template-columns: repeat(2, 1fr);
    }

    .ranking-list {
        flex-direction: column;
        align-items: center;
    }

    .ranking-card {
        width: 100%;
        max-width: 300px;
    }

    .rank-badge {
        top: -12px;
        padding: 6px 20px;
        font-size: 14px;
    }
}

/* 添加商品列表样式 */
.all-products-section,
.new-products-section {
    margin: 30px auto;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    max-width: 1200px;
    width: 95%;
}

.products-container {
    padding: 20px 0;
}

.all-products {
    grid-template-columns: repeat(5, 1fr);
    /* 显示5列 */
    gap: 20px;
}

/* 响应式调整 */
@media (max-width: 1400px) {
    .all-products {
        grid-template-columns: repeat(4, 1fr);
    }
}

@media (max-width: 1100px) {
    .all-products {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 768px) {
    .all-products {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 480px) {
    .all-products {
        grid-template-columns: repeat(1, 1fr);
    }
}

/* 统一商品网格布局 */
.products-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 25px;
    padding: 10px;
    max-width: 1200px;
    margin: 0 auto;
}

/* 移除 all-products 特殊样式 */
.all-products {
    grid-template-columns: repeat(4, 1fr) !important;
    /* 强制使用4列布局 */
}

/* 统一响应式布局 */
@media (max-width: 1200px) {
    .product-card {
        max-width: 220px;
    }
}

@media (max-width: 992px) {

    .products-grid,
    .all-products {
        grid-template-columns: repeat(3, 1fr) !important;
    }
}

@media (max-width: 768px) {

    .products-grid,
    .all-products {
        grid-template-columns: repeat(2, 1fr) !important;
    }
}

@media (max-width: 480px) {

    .products-grid,
    .all-products {
        grid-template-columns: repeat(1, 1fr) !important;
    }
}

/* 移除旧的响应式样式 */
@media (max-width: 1400px) {
    .all-products {
        grid-template-columns: repeat(4, 1fr);
    }
}

@media (max-width: 1100px) {
    .all-products {
        grid-template-columns: repeat(3, 1fr);
    }
}

/* 统一商品卡片样式 */
.product-card {
    aspect-ratio: auto;
    border-radius: 12px;
    overflow: hidden;
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    display: flex;
    flex-direction: column;
    width: 100%;
    max-width: 260px;
    margin: 0 auto;
    cursor: pointer;
}

.product-image {
    position: relative;
    width: 100%;
    aspect-ratio: 1;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-card:hover {
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.product-card:hover .product-image img {
    transform: scale(1.08);
}

.product-info {
    padding: 12px;
    background: white;
}

.product-name {
    font-size: 14px;
    color: #333;
    margin-bottom: 6px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.product-price {
    font-size: 16px;
    color: #f56c6c;
    font-weight: 500;
}

/* 统一容器样式 */
.hot-products-section,
.all-products-section,
.new-products-section {
    margin: 30px auto;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
    max-width: 1200px;
    width: 95%;
}

.products-container {
    padding: 10px 0;
    /* 减小内边距 */
}

/* 新品部分样式 */
.new-products-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
    width: 70%;
    margin: 0 auto;
}

.new-product-card {
    position: relative;
    background: white;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    transition: all 0.3s ease;
    cursor: pointer;
}

.new-product-card.large {
    width: 100%;
}

.small-cards-container {
    display: flex;
    gap: 20px;
    width: 100%;
}

.new-product-card.small {
    flex: 1;
}

.product-image-wrapper {
    position: relative;
    width: 100%;
    overflow: hidden;
}

.large .product-image-wrapper {
    height: 400px;
}

.small .product-image-wrapper {
    height: 250px;
}

.product-image-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.view-details {
    color: white;
    font-size: 16px;
    font-weight: 500;
    padding: 8px 16px;
    border: 2px solid white;
    border-radius: 20px;
    transform: translateY(20px);
    transition: transform 0.3s ease;
}

.new-product-card:hover .product-image-wrapper img {
    transform: scale(1.1);
}

.new-product-card:hover .overlay {
    opacity: 1;
}

.new-product-card:hover .view-details {
    transform: translateY(0);
}

/* 响应式调整 */
@media (max-width: 992px) {
    .new-products-container {
        width: 85%;
    }

    .large .product-image-wrapper {
        height: 300px;
    }

    .small .product-image-wrapper {
        height: 200px;
    }
}

@media (max-width: 768px) {
    .new-products-container {
        width: 95%;
    }

    .small-cards-container {
        flex-direction: column;
    }

    .large .product-image-wrapper,
    .small .product-image-wrapper {
        height: 250px;
    }
}

/* 销售榜单卡片特殊样式 */
.ranking-card .overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.ranking-card .view-details {
    color: white;
    font-size: 18px;
    font-weight: 500;
    padding: 10px 20px;
    border: 2px solid white;
    border-radius: 20px;
    transform: translateY(20px);
    transition: transform 0.3s ease;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .ranking-card .view-details {
        font-size: 16px;
        padding: 8px 16px;
    }
}
</style>