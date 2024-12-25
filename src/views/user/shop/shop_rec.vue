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
                     :key="slide.id"
                     :class="{ active: currentSlide === index }"
                     @click="goToDetail(slide.id)">
                    <div class="carousel-image">
                        <img :src="slide.image" 
                             :alt="slide.content"
                             @error="handleImageError" />
                        <div class="carousel-info">
                            <h3>{{ slide.content }}</h3>
                            <p class="price">￥{{ slide.price }}</p>
                            <p class="score">评分: {{ slide.score }}</p>
                        </div>
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
                          :key="slide.id"
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
                         v-for="item in currentPageProducts" 
                         :key="item.id"
                         @click="goToDetail(item.id)">
                        <div class="product-image">
                            <img :src="item.image" :alt="item.title" />
                        </div>
                        <div class="product-content">
                            <div class="product-description">{{ item.title }}</div>
                            <div class="product-bottom">
                                <span class="price">￥{{ item.price }}</span>
                                <span class="score">评分: {{ item.score }}</span>
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
                             v-for="item in currentHotProducts" 
                             :key="item.id"
                             @click="goToDetail(item.id)">
                            <img :src="item.image" :alt="item.title" class="hot-product-image" />
                            <div class="hot-product-info">
                                <div class="title">{{ item.title }}</div>
                                <div class="price">￥{{ item.price }}</div>
                                <div class="score">评分: {{ item.score }}</div>
                            </div>
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
import { ref, onMounted, computed } from 'vue'
import request from '../../../utils/request'

export default {
    name: 'shop_rec',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const currentSlide = ref(0)
        const slides = ref([])
        const categories = ref([])
        const currentCategory = ref(0)
        const currentPage = ref(1)
        const pageSize = ref(6)
        const totalPages = ref(5)
        const hotProducts = ref([])
        const hotProductsIndex = ref(1)

        // 获取轮播图数据
        const getCarouselData = async () => {
            try {
                const response = await request.get('/shangpin/findAllShangpin')
                console.log('轮播图数据:', response)
                // 检查数据结构
                if (response.data && response.data.data) {
                    // 如果数据在 data.data 中
                    slides.value = response.data.data.slice(0, 3).map(item => ({
                        id: item.id,
                        content: item.name,
                        image: `http://localhost:8080/image${item.img}`,
                        price: item.price,
                        score: item.score
                    }))
                    console.log(slides.value)
                }
            } catch (error) {
                console.error('获取轮播图数据失败:', error)
            }
        }

        // 获取分类数据
        const getCategoryData = async () => {
            try {
                const response = await request.get('/shangpin/findAllShangpin')
                console.log('分类数据:', response)
                if (response.data && response.data.data) {
                    // 将商品按照type分组
                    const groupedProducts = {}
                    response.data.data.forEach(product => {
                        if (!groupedProducts[product.type]) {
                            groupedProducts[product.type] = []
                        }
                        groupedProducts[product.type].push(product)
                    })

                    categories.value = Object.entries(groupedProducts).map(([name, products]) => ({
                        name,
                        products: products.map(p => ({
                            id: p.id,
                            title: p.name,
                            price: p.price,
                            image: `http://localhost:8080/image${p.img}`,
                            description: p.description,
                            score: p.score,
                            num: p.num,
                            standard: p.standard
                        }))
                    }))
                }
            } catch (error) {
                console.error('获取分类数据失败:', error)
            }
        }

        // 获取当前分类的商品
        const currentPageProducts = computed(() => {
            if (!categories.value.length) return []
            const start = (currentPage.value - 1) * pageSize.value
            return categories.value[currentCategory.value]?.products.slice(start, start + pageSize.value) || []
        })

        // 获取热销商品
        const getHotProducts = async () => {
            try {
                const response = await request.get('/shangpin/findAllShangpin')
                console.log('热销商品数据:', response)
                if (response.data && response.data.data) {
                    // 按score排序并取前12个
                    hotProducts.value = response.data.data
                        .sort((a, b) => b.score - a.score)
                        .slice(0, 12)
                        .map(p => ({
                            id: p.id,
                            title: p.name,
                            image: `http://localhost:8080${p.img}`,
                            price: p.price,
                            score: p.score
                        }))
                }
            } catch (error) {
                console.error('获取热销商品失败:', error)
            }
        }

        // 当前显示的热销商品
        const currentHotProducts = computed(() => {
            const startIndex = hotProductsIndex.value * 4
            return hotProducts.value.slice(startIndex, startIndex + 4)
        })

        onMounted(() => {
            getCarouselData()
            getCategoryData()
            getHotProducts()
            startAutoSlide()
        })

        // 自动轮播
        const startAutoSlide = () => {
            setInterval(() => {
                nextSlide()
            }, 3000)
        }

        const nextSlide = () => {
            currentSlide.value = (currentSlide.value + 1) % slides.value.length
        }

        const prevSlide = () => {
            currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length
        }

        const setSlide = (index) => {
            currentSlide.value = index
        }

        // 跳转到商品详情
        const goToDetail = (id) => {
            router.push(`/shop_rec_info/${id}`)
        }

        // 图片加载失败时的处理
        const handleImageError = (e) => {
            e.target.src = '/default-image.jpg' // 设置默���图片
        }

        return {
            currentSlide,
            slides,
            categories,
            currentCategory,
            currentPage,
            pageSize,
            totalPages,
            currentPageProducts,
            currentHotProducts,
            nextSlide,
            prevSlide,
            setSlide,
            goToDetail,
            changeCategory: (index) => {
                currentCategory.value = index
                currentPage.value = 1 // 切换分类时重置页码
            },
            changePage: (page) => {
                currentPage.value = page
                window.scrollTo({ top: 0, behavior: 'smooth' })
            },
            handleImageError
        }
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
    width: 100%;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
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
    grid-column: 1 / -1; /* 分页器占据整行 */
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

.carousel-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 10px;
}

.carousel-info {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 20px;
    background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
    color: white;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
}

.carousel-info h3 {
    margin: 0;
    font-size: 24px;
    margin-bottom: 8px;
}

.carousel-info .price {
    margin: 0;
    font-size: 20px;
    color: #4CAF50;
}

.score {
    color: #ff9800;
    font-size: 14px;
    margin-top: 4px;
}

.hot-product-info {
    padding: 8px;
}

.hot-product-info .title {
    font-size: 14px;
    margin-bottom: 4px;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.hot-product-image {
    width: 100%;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
}
</style>