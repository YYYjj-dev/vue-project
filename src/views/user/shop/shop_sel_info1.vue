<template>
    <div class="shop-sel-info1">
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>
        
        <!-- 店铺头部信息 -->
        <div class="shop-header">
            <div class="logo">{{ storeInfo.logo }}</div>
            <input type="text" class="shop-name" :value="storeInfo.name" readonly>
            <div class="search-box">
                <input type="text" placeholder="搜索" @input="search">
            </div>
            <button class="order-btn" @click="order">订购</button>
        </div>

        <!-- 分类导航 -->
        <div class="nav-tabs">
            <button class="tab-btn" :class="{ active: activeTab === 'recommend' }" @click="switchTab('recommend')">推荐</button>
            <button class="tab-btn" :class="{ active: activeTab === 'products' }" @click="switchTab('products')">商品</button>
            <button class="tab-btn" :class="{ active: activeTab === 'new' }" @click="switchTab('new')">新品</button>
        </div>

        <!-- 热销产品标签 -->
        <div class="hot-products-tag">
            <span>热销产品</span>
        </div>

        <!-- 商品展示区 -->
        <div class="products-grid">
            <div class="product-card" v-for="product in storeInfo.products" :key="product.id" @click="goToDetail(product.id)">
                <div class="product-image">{{ product.image }}</div>
            </div>
        </div>

        <!-- 销售榜单 -->
        <div class="sales-section">
            <div class="sales-title">销售榜单</div>
            <div class="sales-list">
                <div class="sales-item" v-for="product in storeInfo.topProducts" :key="product.rank" @click="goToDetail(product.id)">
                    <div class="rank">TOP{{ product.rank }}</div>
                    <div class="product-image">{{ product.image }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'

export default {
    name: 'shop_sel_info1',
    components: {
        NavBar
    },
    props: {
        id: {
            type: [String, Number],
            default: '1'
        }
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            activeTab: 'recommend',
            storeInfo: {
                logo: '制造商logo',
                name: '制造商信息',
                products: Array(8).fill().map((_, i) => ({
                    id: i + 1,
                    image: '图片',
                    title: `商品${i + 1}`,
                    price: `${(i + 1) * 99}.00`
                })),
                topProducts: Array(3).fill().map((_, i) => ({
                    id: i + 9,
                    rank: i + 1,
                    image: '图片',
                    title: `热销商品${i + 1}`,
                    price: `${(i + 1) * 199}.00`
                }))
            }
        }
    },
    created() {
        this.loadStoreData(this.id)
    },
    methods: {
        loadStoreData(id) {
            console.log('Loading store data for ID:', id)
        },
        switchTab(tab) {
            this.activeTab = tab
            if (tab === 'products') {
                this.$router.push('/shop_sel_info2')
            } else if (tab === 'new') {
                this.$router.push('/shop_sel_info3')
            }
        },
        goToDetail(id) {
            this.router.push(`/shop_rec_info/${id}`)
        },
        search(keyword) {
            console.log('Searching:', keyword)
        },
        order() {
            console.log('Ordering...')
        }
    }
}
</script>

<style scoped>
.shop-sel-info1 {
    min-height: 100vh;
    background-color: #f8f7f2;
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
    padding: 20px;
    display: flex;
    align-items: center;
    gap: 20px;
}

.logo {
    width: 80px;
    height: 80px;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
}

.shop-name {
    width: 120px;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 20px;
    text-align: center;
}

.search-box {
    flex: 0 1 60%;
    margin: 0 auto;
}

.search-box input {
    width: 100%;
    padding: 8px 16px;
    border: 1px solid #ddd;
    border-radius: 20px;
    font-size: 14px;
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

.product-card, .sales-item {
    cursor: pointer;
    transition: transform 0.2s;
}

.product-card:hover, .sales-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}
</style>