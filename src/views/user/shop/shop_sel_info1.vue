<template>
    <div class="shop-sel-info1">
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>

        <!-- 店铺头部信息 -->
        <div class="shop-header">
            <div class="logo">
                <template v-if="storeInfo.logo">
                    <img :src="storeInfo.logo" :alt="storeInfo.name">
                </template>
                <template v-else>
                    {{ storeInfo.name ? storeInfo.name.charAt(0) : 'S' }}
                </template>
            </div>
            <div class="shop-info">
                <h1 class="shop-name">{{ storeInfo.name }}</h1>
                <p class="shop-desc">{{ storeInfo.description }}</p>
                <span class="shop-type">经营类型: {{ storeInfo.type }}</span>
            </div>
            <div class="search-box">
                <input type="text" placeholder="搜索店内商品" class="search-input">
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

        <!-- 热销产品标签 -->
        <div class="hot-products-tag">
            <span>热销产品</span>
        </div>

        <!-- 商品展示区 -->
        <div class="products-grid">
            <div class="product-card" v-for="product in storeInfo.products" :key="product.username"
                @click="goToDetail(product.username)">
                <div class="product-image">{{ product.image }}</div>
            </div>
        </div>

        <!-- 销售榜单 -->
        <div class="sales-section">
            <div class="sales-title">销售榜单</div>
            <div class="sales-list">
                <div class="sales-item" v-for="product in storeInfo.topProducts" :key="product.rank"
                    @click="goToDetail(product.id)">
                    <div class="rank">TOP{{ product.rank }}</div>
                    <div class="product-image">{{ product.image }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import request from '../../../utils/request'

export default {
    name: 'shop_sel_info1',
    components: {
        NavBar
    },
    props: {
        username: {
            type: String,
            required: true
        }
    },
    setup(props) {
        const router = useRouter()
        const route = useRoute()
        const baseUrl = 'http://localhost:8080/image/'
        const activeTab = ref('recommend')
        const storeInfo = ref({})
        const isLoading = ref(false)

        // 获取商家详细信息
        const loadStoreData = async () => {
            try {
                isLoading.value = true
                const username = props.username || route.params.username
                console.log('获取商家信息，username:', username)

                const response = await request.get('/merchant/findMerchantByUsername', {
                    params: { username: username }
                })
                console.log('商家详情响应:', response)

                if (response.data && response.data.code === 200) {
                    const merchantData = response.data.data
                    storeInfo.value = {
                        id: merchantData.id,
                        username: merchantData.username,
                        name: merchantData.name,
                        logo: merchantData.logo ? baseUrl + merchantData.logo : null,
                        description: merchantData.description,
                        type: merchantData.type,
                        products: [], // 初始化商品列表
                        topProducts: [] // 初始化热销商品列表
                    }
                    console.log('处理后的商家信息:', storeInfo.value)
                } else {
                    console.log('获取商家详情失败:', response.data)
                }
            } catch (error) {
                console.error('获取商家详情错误:', error)
            } finally {
                isLoading.value = false
            }
        }

        // 监听路由参数变化
        watch(() => props.username, (newUsername) => {
            if (newUsername) {
                loadStoreData()
            }
        })

        const switchTab = (tab) => {
            activeTab.value = tab
            if (tab === 'products') {
                router.push('/shop_sel_info2')
            } else if (tab === 'new') {
                router.push('/shop_sel_info3')
            }
        }

        const goToDetail = (id) => {
            router.push(`/shop_rec_info/${id}`)
        }

        onMounted(() => {
            loadStoreData()
        })

        return {
            storeInfo,
            activeTab,
            switchTab,
            goToDetail,
            isLoading
        }
    }
}
</script>

<style scoped>
.shop-sel-info1 {
    min-height: 100vh;
    background-color:#f8f9fa;;
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

.search-box {
    flex: 0 0 300px;
}

.search-input {
    width: 100%;
    padding: 12px 20px;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    font-size: 14px;
    transition: all 0.3s ease;
}

.search-input:focus {
    outline: none;
    border-color: #4CAF50;
    box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
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

/* 热销产品标签 */
.hot-products-tag {
    text-align: center;
    margin: 20px 0;
}

.hot-products-tag span {
    padding: 5px 20px;
    background: white;
    border-radius: 20px;
}

/* 商品网格 */
.products-grid {
    max-width: 1200px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    padding: 20px;
}

.product-card {
    background: white;
    border-radius: 8px;
    overflow: hidden;
}

.product-image {
    aspect-ratio: 1;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* 分页器 */
.pagination {
    display: none;
}

/* 销售榜单 */
.sales-section {
    max-width: 1200px;
    margin: 20px auto;
    padding: 20px;
}

.sales-title {
    text-align: center;
    margin-bottom: 20px;
}

.sales-list {
    display: flex;
    justify-content: space-between;
    gap: 20px;
}

.sales-item {
    flex: 1;
    background: white;
    border-radius: 8px;
    padding: 20px;
}

.rank {
    text-align: center;
    margin-bottom: 10px;
    font-weight: bold;
}

.sales-item .product-image {
    aspect-ratio: 1;
    background: #f0f0f0;
    border-radius: 8px;
}

.product-card,
.sales-item {
    cursor: pointer;
    transition: transform 0.2s;
}

.product-card:hover,
.sales-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
</style>