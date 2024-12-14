<template>
    <div class="shop_sel_info3">
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>
        <!-- 店铺头部信息 -->
        <div class="shop-header">
            <div class="logo">制造商logo</div>
            <input type="text" class="shop-name" value="制造商信息" readonly>
            <div class="search-box">
                <input type="text" placeholder="搜索" @input="search">
            </div>
            <button class="order-btn" @click="order">订阅</button>
        </div>

        <!-- 分类导航 -->
        <div class="nav-tabs">
            <button class="tab-btn" @click="$router.push('/shop_sel_info1/1')">推荐</button>
            <button class="tab-btn" @click="$router.push('/shop_sel_info2')">商品</button>
            <button class="tab-btn active">新品</button>
        </div>

        <!-- 商品展示部分 -->
        <div class="product-section">
            <!-- 第一组商品 -->
            <div class="product-group">
                <div class="large-product" @click="goToDetail(products[0].id)">
                    <div class="product-image">
                        {{ products[0].image }}
                        <div class="product-info">
                            <div class="product-title">{{ products[0].title }}</div>
                            <div class="product-price">¥{{ products[0].price }}</div>
                        </div>
                    </div>
                </div>
                <div class="small-products">
                    <div class="product-row" 
                         v-for="index in 2" 
                         :key="index"
                         @click="goToDetail(products[index].id)">
                        <div class="product-image">
                            {{ products[index].image }}
                            <div class="product-info">
                                <div class="product-title">{{ products[index].title }}</div>
                                <div class="product-price">¥{{ products[index].price }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 第二组商品 -->
            <div class="product-group">
                <div class="large-product" @click="goToDetail(products[3].id)">
                    <div class="product-image">
                        {{ products[3].image }}
                        <div class="product-info">
                            <div class="product-title">{{ products[3].title }}</div>
                            <div class="product-price">¥{{ products[3].price }}</div>
                        </div>
                    </div>
                </div>
                <div class="small-products">
                    <div class="product-row" 
                         v-for="i in 2" 
                         :key="i"
                         @click="goToDetail(products[i + 3].id)">
                        <div class="product-image">
                            {{ products[i + 3].image }}
                            <div class="product-info">
                                <div class="product-title">{{ products[i + 3].title }}</div>
                                <div class="product-price">¥{{ products[i + 3].price }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 第三组商品 -->
            <div class="product-group">
                <div class="large-product" @click="goToDetail(products[6].id)">
                    <div class="product-image">
                        {{ products[6].image }}
                        <div class="product-info">
                            <div class="product-title">{{ products[6].title }}</div>
                            <div class="product-price">¥{{ products[6].price }}</div>
                        </div>
                    </div>
                </div>
                <div class="small-products">
                    <div class="product-row" 
                         v-for="i in 2" 
                         :key="i"
                         @click="goToDetail(products[i + 6].id)">
                        <div class="product-image">
                            {{ products[i + 6].image }}
                            <div class="product-info">
                                <div class="product-title">{{ products[i + 6].title }}</div>
                                <div class="product-price">¥{{ products[i + 6].price }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'

export default {
    name: 'shop_sel_info3',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            storeInfo: {
                logo: '制造商logo',
                name: '制造商信息'
            },
            products: [
                { id: 1, image: '图片', title: '新品1', price: '299.00', date: '2024-03-30' },
                { id: 2, image: '图片', title: '新品2', price: '399.00', date: '2024-03-29' },
                { id: 3, image: '图片', title: '新品3', price: '499.00', date: '2024-03-28' },
                { id: 4, image: '图片', title: '新品4', price: '599.00', date: '2024-03-27' },
                { id: 5, image: '图片', title: '新品5', price: '699.00', date: '2024-03-26' },
                { id: 6, image: '图片', title: '新品6', price: '799.00', date: '2024-03-25' },
                { id: 7, image: '图片', title: '新品7', price: '899.00', date: '2024-03-24' },
                { id: 8, image: '图片', title: '新品8', price: '999.00', date: '2024-03-23' },
                { id: 9, image: '图片', title: '新品9', price: '1099.00', date: '2024-03-22' }
            ]
        }
    },
    methods: {
        goToDetail(id) {
            this.router.push(`/shop_rec_info/${id}`)
        },
        search(e) {
            console.log('搜索:', e.target.value)
        },
        order() {
            console.log('订阅')
        }
    }
}
</script>

<style scoped>
.shop_sel_info3 {
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
    width: 120px;  /* 减小宽度 */
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 20px;  /* 增加圆角 */
    text-align: center;  /* 文字居中 */
}

.search-box {
    flex: 0 1 60%;  /* 占60%宽度 */
    margin: 0 auto;
}

.search-box input {
    width: 100%;
    padding: 8px 16px;  /* 增加左右内边距 */
    border: 1px solid #ddd;
    border-radius: 20px;  /* 增加圆角 */
    font-size: 14px;
}

.order-btn {
    padding: 8px 24px;  /* 增加左右内边距 */
    background: white;  /* 改为白色背景 */
    color: #333;  /* 改为深色文字 */
    border: 1px solid #ddd;  /* 添加边框 */
    border-radius: 20px;  /* 增加圆角 */
    cursor: pointer;
}

.order-btn:hover {
    background: #f5f5f5;  /* 悬停效果 */
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

/* 商品展示区 */
.product-section {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.large-product {
    margin-bottom: 20px;
}

.large-product .product-image {
    width: 100%;
    height: 300px;
    background: #f0f0f0;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.small-products {
    display: flex;
    gap: 20px;
}

.product-row {
    flex: 1;
}

.product-row .product-image {
    width: 100%;
    height: 200px;
    background: #f0f0f0;
    border-radius: 8px;
    margin-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* 添加新样式 */
.large-product, .product-row {
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;
}

.large-product:hover, .product-row:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.product-info {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px;
    background: rgba(255, 255, 255, 0.9);
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

.product-title {
    font-size: 16px;
    margin-bottom: 8px;
}

.product-price {
    color: #f44336;
    font-size: 18px;
    font-weight: bold;
}

/* 添加新样式 */
.product-group {
    margin-bottom: 40px;
    background: white;
    padding: 20px;
    border-radius: 8px;
}

.product-group:last-child {
    margin-bottom: 0;
}

/* 移除时间标签相关样式 */
.time-label {
    display: none;
}
</style>