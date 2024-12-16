<template>
    <div class="shop_sel_info2">
        <!-- 导航栏 -->
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
            <button class="tab-btn active">商品</button>
            <button class="tab-btn" @click="$router.push('/shop_sel_info3')">新品</button>
        </div>

        <!-- 排序选项 -->
        <div class="sort-options">
            <button class="sort-btn" :class="{ active: activeSort === 'comprehensive' }" @click="setSort('comprehensive')">综合</button>
            <button class="sort-btn" :class="{ active: activeSort === 'sales' }" @click="setSort('sales')">销量</button>
            <button class="sort-btn" :class="{ active: activeSort === 'new' }" @click="setSort('new')">新品</button>
            <button class="sort-btn" 
                    :class="{ active: activeSort === 'price' }" 
                    @click="setSort('price')">
                价格
                <span class="price-arrow" v-if="activeSort === 'price'">
                    {{ priceAscending ? '↑' : '↓' }}
                </span>
            </button>
        </div>

        <!-- 商品网格 -->
        <div class="products-grid">
            <div class="product-card" 
                 v-for="product in sortedProducts" 
                 :key="product.id"
                 @click="goToDetail(product.id)">
                <div class="product-image">{{ product.image }}</div>
                <div class="product-info">
                    <div class="product-title">{{ product.title }}</div>
                    <div class="product-price">¥{{ product.price }}</div>
                    <div class="product-sales">销量: {{ product.sales }}</div>
                </div>
            </div>
        </div>

        <!-- 分页器 -->
        <div class="pagination">
            <button class="page-btn" 
                    :disabled="currentPage === 1"
                    @click="changePage(currentPage - 1)">
                &lt;
            </button>
            <div class="page-numbers">
                <button v-for="page in totalPages" 
                        :key="page" 
                        :class="['page-number', { active: currentPage === page }]"
                        @click="changePage(page)">
                    {{ page }}
                </button>
            </div>
            <button class="page-btn" 
                    :disabled="currentPage === totalPages"
                    @click="changePage(currentPage + 1)">
                &gt;
            </button>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'

export default {
    name: 'shop_sel_info2',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            activeSort: 'comprehensive',
            priceAscending: true,
            currentPage: 1,
            pageSize: 8, // 每页显示8个商品
            totalPages: 3, // 改为3页
            // 生成24个商品数据（8个/页 × 3页）
            allProducts: Array(24).fill().map((_, i) => ({
                id: i + 1,
                image: '图片',
                title: `商品${i + 1}`,
                price: ((i % 10 + 1) * 99).toFixed(2),
                sales: Math.floor(Math.random() * 500) + 100, // 随机销量
                date: new Date(2024, 2, Math.floor(Math.random() * 30) + 1).toLocaleDateString() // 随机日期
            }))
        }
    },
    computed: {
        sortedProducts() {
            let sorted = [...this.allProducts]
            // 根据不同的排序方式处理数据
            switch (this.activeSort) {
                case 'sales':
                    sorted = sorted.sort((a, b) => b.sales - a.sales)
                    break
                case 'new':
                    sorted = sorted.sort((a, b) => new Date(b.date) - new Date(a.date))
                    break
                case 'price':
                    sorted = sorted.sort((a, b) => {
                        const diff = parseFloat(a.price) - parseFloat(b.price)
                        return this.priceAscending ? diff : -diff
                    })
                    break
            }
            // 分页处理
            const start = (this.currentPage - 1) * this.pageSize
            return sorted.slice(start, start + this.pageSize)
        }
    },
    methods: {
        setSort(sort) {
            if (sort === 'price' && this.activeSort === 'price') {
                this.priceAscending = !this.priceAscending
            } else {
                if (sort === 'price') {
                    this.priceAscending = true
                }
                this.activeSort = sort
            }
        },
        goToDetail(id) {
            this.router.push(`/shop_rec_info/${id}`)
        },
        search(e) {
            // 实现搜索逻辑
            console.log('搜索:', e.target.value)
        },
        order() {
            // 实现订阅逻辑
            console.log('订阅')
        },
        changePage(page) {
            this.currentPage = page
            // 滚动到页面顶部
            window.scrollTo({ top: 0, behavior: 'smooth' })
        }
    }
}
</script>

<style scoped>
.shop_sel_info2 {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding-top: 60px; /* 为固定导航栏留出空间 */
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

/* 排序选项样式 */
.sort-options {
    display: flex;
    gap: 20px;
    padding: 15px;
    background: white;
    margin-top: 1px;
}

.sort-btn {
    padding: 5px 15px;
    border: 1px solid #ddd;
    border-radius: 15px;
    background: white;
    cursor: pointer;
    position: relative;
    display: flex;
    align-items: center;
    gap: 4px;
}

.sort-btn.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

.price-arrow {
    font-size: 12px;
    margin-left: 2px;
}

/* 商品网格 */
.products-grid {
    max-width: 1200px;
    margin: 20px auto;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    padding: 20px;
}

.product-card {
    cursor: pointer;
    transition: all 0.3s ease;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.product-image {
    aspect-ratio: 1;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
}

.product-info {
    padding: 15px;
}

.product-title {
    font-size: 14px;
    margin-bottom: 8px;
}

.product-price {
    color: #f44336;
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 5px;
}

.product-sales {
    color: #666;
    font-size: 12px;
}

/* 分页器 */
.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
    margin: 20px 0;
    padding: 20px 0;
}

.page-numbers {
    display: flex;
    gap: 5px;
}

.page-btn, .page-number {
    min-width: 32px;
    height: 32px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: white;
    color: #333;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s;
}

.page-btn:disabled {
    background: #f5f5f5;
    color: #999;
    cursor: not-allowed;
}

.page-number.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

.page-btn:not(:disabled):hover,
.page-number:not(.active):hover {
    border-color: #4CAF50;
    color: #4CAF50;
}
</style>