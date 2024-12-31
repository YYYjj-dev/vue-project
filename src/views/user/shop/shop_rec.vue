<template>
    <div class="shop-rec">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>

        <!-- 轮播图 -->
        <div class="carousel-container" @mouseenter="pauseAutoPlay" @mouseleave="resumeAutoPlay">
            <div class="carousel-wrapper" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                <div v-for="(image, index) in images" :key="index" class="carousel-item">
                    <img :src="image" :alt="`轮播图 ${index + 1}`" class="carousel-image">
                </div>
            </div>

            <!-- 轮播图指示器 -->
            <div class="carousel-indicators">
                <span v-for="(_, index) in images" :key="index"
                    :class="['indicator', { active: currentIndex === index }]" @click="setCurrentIndex(index)"></span>
            </div>

            <!-- 左右箭头 -->
            <button class="carousel-button prev" @click="prev">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                        stroke-linejoin="round" />
                </svg>
            </button>
            <button class="carousel-button next" @click="next">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                        stroke-linejoin="round" />
                </svg>
            </button>
        </div>

        <div class="main-content">
            <!-- 商品分类 -->
            <div class="sub-categories">
                <button v-for="(category, index) in categories" :key="index"
                    :class="['sub-button', { active: currentCategory === index }]" @click="changeCategory(index)">
                    {{ category.name }}
                </button>
            </div>

            <!-- 商品展示区域 -->
            <div class="content-container">
                <!-- 左侧商品列表 -->
                <div class="products-grid">
                    <div class="product-card" v-for="item in currentPageProducts" :key="item.id"
                        @click="goToDetail(item.id)">
                        <div class="product-image">
                            <img :src="item.image" :alt="item.title" />
                        </div>
                        <div class="product-content">
                            <div class="product-bottom">
                                <div class="title">{{ item.title }}</div>
                                <div class="price">￥{{ item.price }}</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 右侧热销商品 -->
                <div class="sidebar">
                    <div class="search-container">
                        <input type="text" placeholder="搜索" class="search-input" v-model="searchQuery"
                            @keyup.enter="handleSearch">
                    </div>
                    <div class="hot-products-title">热销产品</div>
                    <div class="hot-products-list">
                        <div class="hot-product-item" v-for="item in currentHotProducts" :key="item.id"
                            @click="goToDetail(item.id)">
                            <img :src="item.image" :alt="item.title" class="hot-product-image" />
                            <div class="hot-product-info">
                                <div class="title">{{ item.title }}</div>
                                <div class="price">￥{{ item.price }}</div>
                                <div class="score">
                                    {{ typeof item.score === 'number' ? `销量: ${item.score}` : '暂无评分' }}
                                </div>
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
    <Footer />
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Carousel from '../../../components/Carousel.vue'
import { useRouter } from 'vue-router'
import { ref, onMounted, onBeforeUnmount, computed } from 'vue'
import request from '../../../utils/request'
import Footer from '../../../components/Footer.vue'

export default {
    name: 'shop_rec',
    components: {
        NavBar,
        Carousel,
        Footer
    },
    setup() {
        const router = useRouter()
        const baseUrl = 'http://localhost:8080/image/'
        const currentIndex = ref(0)
        const images = ref([
            '/src/img/tea4.jpg',
            '/src/img/xilanhua.jpg',
            '/src/img/oranges.jpg',
            '/src/img/juice.jpg',
            '/src/img/huotui2.jpg',
            '/src/img/ningmengsuan.jpg',
        ])
        const autoPlayInterval = ref(null)
        const isPaused = ref(false)
        const slides = ref([])
        const categories = ref([
            { name: '饮料', type: '饮料' },
            { name: '果蔬', type: '果蔬' },
            { name: '肉类', type: '肉类' },
            { name: '其他', type: '其他' },

        ])
        const currentCategory = ref(0)
        const hotProducts = ref([])
        const hotProductsIndex = ref(0)
        const currentPageProducts = ref([])
        const searchQuery = ref('')

        // 根据分类获取商品数据
        const getProductsByCategory = async (type) => {
            try {
                // 对中文参数进行编码
                console.log('发送的type值:', type)


                const response = await request.get('/shangpin/findShangpinByType', {
                    params: { type },
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                })

                if (response.data && response.data.code === 200) {
                    return response.data.data.map(p => ({
                        id: p.id,
                        title: p.name,
                        price: p.price,
                        image: baseUrl + p.imgpath,
                        description: p.description,
                        num: p.num,
                        score: p.score,
                        standard: p.standard,
                        group: p.group,
                        type: p.type
                    }))
                }
                return []
            } catch (error) {
                console.error(`获取${type}分类商品失败:`, error)
                return []
            }
        }

        // 切换分类时获取对应商品
        const changeCategory = async (index) => {
            currentCategory.value = index
            const type = categories.value[index].type
            console.log('选择的分类:', type)  // 添加日志
            try {
                const products = await getProductsByCategory(type)
                currentPageProducts.value = products
            } catch (error) {
                console.error('切换分类失败:', error)
                currentPageProducts.value = []
            }
        }

        // 获取热销商品
        const getHotProducts = async () => {
            try {
                const response = await request.get('/shangpin/findAllShangpin')
                if (response.data && response.data.code === 200) {
                    hotProducts.value = response.data.data
                        .sort((a, b) => b.num - a.num)
                        .slice(0, 12)
                        .map(p => ({
                            id: p.id,
                            title: p.name,
                            image: baseUrl + p.imgpath,
                            price: p.price,
                            score: p.num ?? '暂无评分',  // 添加默认值处理
                            num: p.num
                        }))
                }
            } catch (error) {
                console.error('获取热销商品失败:', error)
            }
        }

        // 当前显示的热销商品
        const currentHotProducts = computed(() => {
            const startIndex = (hotProductsIndex.value % 3) * 4
            return hotProducts.value.slice(startIndex, startIndex + 4)
        })

        // 换一换按钮点击处理
        const refreshHotProducts = () => {
            hotProductsIndex.value = (hotProductsIndex.value + 1) % 3
        }

        // 轮播图方法
        const next = () => {
            currentIndex.value = (currentIndex.value + 1) % images.value.length
        }

        const prev = () => {
            currentIndex.value = currentIndex.value === 0
                ? images.value.length - 1
                : currentIndex.value - 1
        }

        const setCurrentIndex = (index) => {
            currentIndex.value = index
        }

        const startAutoPlay = () => {
            if (!isPaused.value) {
                autoPlayInterval.value = setInterval(() => {
                    next()
                }, 5000)
            }
        }

        const stopAutoPlay = () => {
            if (autoPlayInterval.value) {
                clearInterval(autoPlayInterval.value)
                autoPlayInterval.value = null
            }
        }

        const pauseAutoPlay = () => {
            isPaused.value = true
            stopAutoPlay()
        }

        const resumeAutoPlay = () => {
            isPaused.value = false
            startAutoPlay()
        }

        const handleSearch = () => {
            if (searchQuery.value.trim()) {  // 检查搜索框是否有内容
                router.push({
                    name: 'shop_rec_search',
                    query: { keyword: searchQuery.value.trim() }
                })
            }
        }

        onMounted(() => {
            changeCategory(0)  // 初始加载第一个分类的商品
            getHotProducts()
            startAutoPlay()
        })

        onBeforeUnmount(() => {
            stopAutoPlay()
        })

        // 跳转到商品详情
        const goToDetail = (id) => {
            router.push({ path: '/shop_rec_info/' + id });
        }

        // 图片加载失败时的处理
        const handleImageError = (e) => {
            e.target.src = '/default-image.jpg' // 设置默认图片
        }

        return {
            slides,
            categories,
            currentCategory,
            currentPageProducts,
            currentHotProducts,
            currentIndex,
            images,
            next,
            prev,
            setCurrentIndex,
            pauseAutoPlay,
            resumeAutoPlay,
            goToDetail,
            changeCategory,
            handleImageError,
            refreshHotProducts,
            searchQuery,
            handleSearch
        }
    }
}
</script>

<style scoped>
.shop-rec {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 60px;
    /* 为固定导航栏留出空间 */
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
    position: relative;
    width: 90%;
    max-width: 1200px;
    margin: 40px auto;
    height: auto;
    max-height: 500px;
    overflow: hidden;
    border-radius: 20px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
    display: flex;
    justify-content: center;
    align-items: center;
    aspect-ratio: 16 / 9;
}

.carousel-wrapper {
    display: flex;
    transition: transform 0.5s ease;
    width: 100%;
    height: 100%;
    position: relative;
}

.carousel-item {
    min-width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
}

.carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
    border-radius: 20px;
}

.carousel-indicators {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    justify-content: center;
    gap: 8px;
    z-index: 20;
}

.indicator {
    width: 10px;
    height: 10px;
    margin: 0 5px;
    background-color: rgba(255, 255, 255, 0.5);
    border-radius: 50%;
    cursor: pointer;
    transition: background-color 0.3s;
}

.indicator.active {
    background-color: rgba(255, 255, 255, 1);
}

.carousel-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
    border-radius: 50%;
    z-index: 10;
    transition: background-color 0.3s;
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.carousel-button:hover {
    background-color: rgba(0, 0, 0, 0.8);
}

.carousel-button.prev {
    left: 10px;
}

.carousel-button.next {
    right: 10px;
}

.image-content {
    font-size: 24px;
    color: #666;
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
    padding: 15px;
}

.sub-button {
    padding: 10px 30px;
    border: 2px solid transparent;
    border-radius: 25px;
    background-color: white;
    color: #666;
    font-size: 15px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.sub-button::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    width: 0;
    height: 0;
    background: rgba(76, 175, 80, 0.1);
    border-radius: 50%;
    transform: translate(-50%, -50%);
    transition: width 0.6s ease, height 0.6s ease;
}

.sub-button:hover::before {
    width: 200%;
    height: 200%;
}

.sub-button:hover {
    color: #4CAF50;
    border-color: #4CAF50;
    transform: translateY(-2px);
    background-color: white;
}

.sub-button.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.sub-button.active:hover {
    background: #45a049;
    transform: translateY(-2px);
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
    border: 1px solid #eee;
    border-radius: 12px;
    overflow: hidden;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(66, 185, 131, 0.1);
    border-color: #42b983;
}

.product-image {
    width: 100%;
    height: 240px;
    background: #f5f5f5;
    position: relative;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s ease;
}

.product-card:hover .product-image img {
    transform: scale(1.08);
}

.product-content {
    padding: 15px;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
    gap: 8px;
}

.product-bottom {
    text-align: left;
}

.product-bottom .title {
    font-size: 15px;
    color: #333;
    margin-bottom: 8px;
    line-height: 1.4;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    height: 42px;
}

.product-bottom .price {
    color: #ff6b6b;
    font-size: 18px;
    font-weight: 600;
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
    background: white;
    border-radius: 8px;
    padding: 10px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    flex-direction: column;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.hot-product-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.hot-product-image {
    width: 100%;
    height: 120px;
    object-fit: cover;
    border-radius: 6px;
    margin-bottom: 8px;
    transition: transform 0.3s ease;
}

.hot-product-item:hover .hot-product-image {
    transform: scale(1.05);
}

.hot-product-info {
    padding: 5px;
}

.hot-product-info .title {
    font-size: 14px;
    color: #333;
    margin-bottom: 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.hot-product-info .price {
    color: #ff6b6b;
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 3px;
}

.hot-product-info .score {
    font-size: 12px;
    color: #666;
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

@media (max-width: 768px) {
    .carousel-container {
        aspect-ratio: 4 / 3;
    }

    .product-image {
        height: 180px;
    }

    .product-title {
        padding: 10px;
        font-size: 14px;
    }

    .price {
        font-size: 16px;
    }

    .sub-categories {
        gap: 15px;
        padding: 12px;
        margin: 15px 0 25px;
        overflow-x: auto;
        justify-content: flex-start;
        -webkit-overflow-scrolling: touch;
    }

    .sub-button {
        padding: 8px 20px;
        font-size: 14px;
        flex-shrink: 0;
    }
}

@media (max-width: 480px) {
    .carousel-container {
        aspect-ratio: 1 / 1;
    }

    .product-image {
        height: 160px;
    }

    .product-title {
        padding: 8px;
        font-size: 13px;
    }

    .price {
        font-size: 15px;
    }

    .sub-categories {
        gap: 10px;
        padding: 10px;
    }

    .sub-button {
        padding: 6px 16px;
        font-size: 13px;
    }
}
</style>