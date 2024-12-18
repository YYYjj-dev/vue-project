<template>
    <div class="shop-rec">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>

        <!-- 轮播图 -->
        <div class="carousel-container">
            <div class="carousel-wrapper">
                <div class="carousel-slide" 
                     v-for="(slide, index) in slides" 
                     :key="index"
                     :class="{ active: currentSlide === index }">
                    <div class="carousel-image">
                        <div class="image-content">{{ slide.content }}</div>
                    </div>
                </div>
                <!-- 轮播图控制按钮 -->
                <div class="carousel-controls">
                    <button class="prev" @click="prevSlide">&lt;</button>
                    <button class="next" @click="nextSlide">&gt;</button>
                </div>
                <!-- 轮播图指示器 -->
                <div class="carousel-indicators">
                    <span v-for="(slide, index) in slides" 
                          :key="index"
                          :class="{ active: currentSlide === index }"
                          @click="setSlide(index)">
                    </span>
                </div>
            </div>
        </div>

        <div class="main-content">
            <!-- 商品分类 -->
            <div class="sub-categories">
                <button v-for="(category, index) in categories" 
                        :key="index"
                        :class="['sub-button', { active: currentCategory === index }]"
                        @click="changeCategory(index)">
                    {{ category.name }}
                </button>
            </div>

            <!-- 商品展示区域 -->
            <div class="content-container">
                <!-- 左侧商品列表 -->
                <div class="products-grid">
                    <div class="product-card" 
                         v-for="(item, index) in currentPageProducts" 
                         :key="index"
                         @click="goToDetail(item.id)">
                        <div class="product-image">
                            <div class="image-placeholder">图片</div>
                        </div>
                        <div class="product-content">
                            <div class="product-description">{{ item.title }}</div>
                            <div class="product-bottom">
                                <span class="price">单价</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 添加分页器 -->
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

                <!-- 右侧热销商品 -->
                <div class="sidebar">
                    <div class="search-container">
                        <input type="text" placeholder="搜索" class="search-input">
                    </div>
                    <div class="hot-products-title">热销产品</div>
                    <div class="hot-products-list">
                        <div class="hot-product-item" 
                             v-for="(item, index) in currentHotProducts" 
                             :key="index"
                             @click="goToDetail(item.id)">
                            <div class="hot-product-image">{{ item.title }}</div>
                        </div>
                    </div>
                    <div class="refresh-container">
                        <button class="refresh-button" @click="refreshHotProducts">换一换</button>
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
    name: 'shop_rec',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            currentSlide: 0,
            slides: [
                { content: '轮播图1' },
                { content: '轮播图2' },
                { content: '轮播图3' }
            ],
            currentCategory: 0,
            categories: [
                { 
                    name: '分类1',
                    products: Array(30).fill().map((_, i) => ({
                        id: i + 1,
                        title: `分类1商品${i + 1}`,
                        price: `${(i % 10 + 1) * 99}.00`
                    }))
                },
                {
                    name: '分类2',
                    products: Array(30).fill().map((_, i) => ({
                        id: i + 31,
                        title: `分类2商品${i + 1}`,
                        price: `${(i % 10 + 1) * 88}.00`
                    }))
                },
                {
                    name: '分类3',
                    products: Array(30).fill().map((_, i) => ({
                        id: i + 61,
                        title: `分类3商品${i + 1}`,
                        price: `${(i % 10 + 1) * 77}.00`
                    }))
                },
                {
                    name: '分类4',
                    products: Array(30).fill().map((_, i) => ({
                        id: i + 91,
                        title: `分类4商品${i + 1}`,
                        price: `${(i % 10 + 1) * 66}.00`
                    }))
                }
            ],
            hotProductsPool: [
                { id: 101, title: '热销商品1' },
                { id: 102, title: '热销商品2' },
                { id: 103, title: '热销商品3' },
                { id: 104, title: '热销商品4' },
                { id: 105, title: '热销商品5' },
                { id: 106, title: '热销商品6' },
                { id: 107, title: '热销商品7' },
                { id: 108, title: '热销商品8' },
                { id: 109, title: '热销商品9' },
                { id: 110, title: '热销商品10' },
                { id: 111, title: '热销商品11' },
                { id: 112, title: '热销商品12' }
            ],
            hotProductsIndex: 0,
            currentPage: 1,
            pageSize: 6, // 每页显示6个商品
            totalPages: 5, // 总页数为5
        }
    },
    computed: {
        products() {
            return this.categories[this.currentCategory].products
        },
        currentHotProducts() {
            const startIndex = this.hotProductsIndex * 4;
            return this.hotProductsPool.slice(startIndex, startIndex + 4);
        },
        // 获取当前页的商品
        currentPageProducts() {
            const start = (this.currentPage - 1) * this.pageSize
            return this.products.slice(start, start + this.pageSize)
        }
    },
    methods: {
        nextSlide() {
            this.currentSlide = (this.currentSlide + 1) % this.slides.length
        },
        prevSlide() {
            this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length
        },
        setSlide(index) {
            this.currentSlide = index
        },
        goToDetail(id) {
            this.router.push(`/shop_rec_info/${id}`)
        },
        changeCategory(index) {
            this.currentCategory = index
        },
        refreshHotProducts() {
            const maxIndex = Math.floor(this.hotProductsPool.length / 4);
            this.hotProductsIndex = (this.hotProductsIndex + 1) % maxIndex;
        },
        // 添加页面切换方法
        changePage(page) {
            this.currentPage = page
            // 滚动到页面顶部
            window.scrollTo({ top: 0, behavior: 'smooth' })
        }
    },
    mounted() {
        // 自动播
        setInterval(this.nextSlide, 3000)
    }
}
</script>

<style scoped>
.shop-rec {
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

.carousel-container {
    width: 100%;
    background-color: #fff;
    padding: 20px 0;
}

.carousel-wrapper {
    position: relative;
    width: 90%;
    max-width: 1200px;
    margin: 0 auto;
    height: 400px;
}

.carousel-slide {
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0;
    transition: opacity 0.5s ease-in-out;
}

.carousel-slide.active {
    opacity: 1;
}

.carousel-image {
    width: 100%;
    height: 100%;
    background-color: #f0f0f0;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.image-content {
    font-size: 24px;
    color: #666;
}

.carousel-controls button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background: rgba(255, 255, 255, 0.7);
    border: none;
    padding: 15px;
    cursor: pointer;
    border-radius: 50%;
}

.carousel-controls .prev {
    left: 10px;
}

.carousel-controls .next {
    right: 10px;
}

.carousel-indicators {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    gap: 10px;
}

.carousel-indicators span {
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.5);
    cursor: pointer;
}

.carousel-indicators span.active {
    background: white;
}

.main-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.sub-categories {
    display: flex;
    justify-content: center;
    gap: 30px;
    margin: 20px 0 30px;
}

.sub-button {
    padding: 6px 30px;
    border: 1px solid #ddd;
    border-radius: 15px;
    background: white;
    cursor: pointer;
}

.content-container {
    display: flex;
    gap: 30px;
}

.products-grid {
    flex: 3;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: repeat(3, 1fr);
    gap: 20px;
}

.product-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    background: white;
    cursor: pointer;
    transition: transform 0.2s;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.product-image {
    width: 100%;
    height: 200px;
    background: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
}

.product-content {
    padding: 15px;
}

.product-description {
    margin-bottom: 10px;
}

.product-bottom {
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.add-to-cart {
    display: none;
}

.sidebar {
    flex: 1;
    max-width: 300px;
}

.search-container {
    margin-bottom: 20px;
}

.search-input {
    width: 100%;
    padding: 8px 15px;
    border: 1px solid #ddd;
    border-radius: 20px;
    font-size: 14px;
}

.hot-products-title {
    padding: 10px;
    background: #f5f5f5;
    border-radius: 4px;
    margin-bottom: 15px;
}

.hot-products-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.hot-product-item {
    height: 100px;
    background: #f5f5f5;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: transform 0.2s;
}

.hot-product-item:hover {
    transform: translateY(-2px);
    background: #e8e8e8;
}

.hot-product-image {
    font-size: 16px;
    color: #666;
}

.sub-button.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

.refresh-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 15px;
}

.refresh-button {
    padding: 6px 15px;
    border: 1px solid #4CAF50;
    border-radius: 15px;
    background: white;
    color: #4CAF50;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.3s;
}

.refresh-button:hover {
    background: #4CAF50;
    color: white;
}

/* 分页器样式 */
.pagination {
    grid-column: 1 / -1; /* ��页器占据整行 */
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