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

        <!-- 搜索框区域 -->
        <div class="search-section">
            <div class="search-container">
                <input type="text" class="search-input" :placeholder="'搜索商品...'" v-model="searchQuery"
                    @keyup.enter="handleSearch">
                <button class="search-button" @click="handleSearch">
                    <i class="fas fa-search"></i>
                    搜索
                </button>
            </div>
        </div>

        <!-- 搜索结果展示区域 -->
        <div class="search-results">
            <div v-if="isLoading" class="loading-state">
                正在搜索...
            </div>
            <div v-else-if="searchResults.length === 0 && hasSearched" class="no-results">
                未找到相关商品
            </div>
            <div v-else class="products-grid">
                <div class="product-card" v-for="item in searchResults" :key="item.id" @click="goToDetail(item.id)">
                    <div class="product-image">
                        <img :src="item.image" :alt="item.title" @error="e => e.target.src = '/default-image.jpg'" />
                    </div>
                    <div class="product-content">
                        <div class="product-bottom">
                            <div class="title">{{ item.title }}</div>
                            <div class="price">￥{{ item.price }}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Carousel from '../../../components/Carousel.vue'
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted, onBeforeUnmount, computed } from 'vue'
import request from '../../../utils/request'

export default {
    name: 'shop_rec',
    components: {
        NavBar,
        Carousel
    },
    setup() {
        const router = useRouter()
        const route = useRoute()
        const baseUrl = 'http://localhost:8080/image/'
        const currentIndex = ref(0)
        const images = ref([
            '/src/img/img4.jpg',
            '/src/img/img7.jpg',
            '/src/img/img6.jpg',
            '/src/img/img2.jpg',
            '/src/img/img3.jpg',
            '/src/img/img5.jpg',
        ])
        const autoPlayInterval = ref(null)
        const isPaused = ref(false)
        const slides = ref([])
        const searchQuery = ref('')
        const searchResults = ref([])
        const isLoading = ref(false)
        const hasSearched = ref(false)

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

        // 搜索相关方法
        const handleSearch = () => {
            if (searchQuery.value.trim()) {
                performSearch(searchQuery.value.trim())
            }
        }

        const performSearch = async (name) => {
            try {
                isLoading.value = true
                const response = await request.get('/shangpin/findShangpinByName', {
                    params: { name: name }
                })
                console.log('搜索响应:', response)

                if (response.data && response.data.code === 200) {
                    searchResults.value = response.data.data.map(p => ({
                        id: p.id,
                        title: p.name,
                        price: p.price,
                        image: baseUrl + p.imgpath,
                        description: p.description,
                        score: p.score,
                        num: p.num,
                        standard: p.standard
                    }))
                    console.log('处理后的搜索结果:', searchResults.value)
                } else {
                    searchResults.value = []
                    console.log('搜索无结果:', response.data)
                }
            } catch (error) {
                console.error('搜索商品失败:', error)
                searchResults.value = []
            } finally {
                isLoading.value = false
                hasSearched.value = true
            }
        }

        // 跳转到商品详情
        const goToDetail = (id) => {
            router.push(`/shop_rec_info/${id}`)
        }

        onMounted(() => {
            startAutoPlay()
            searchQuery.value = route.query.keyword || ''
            if (searchQuery.value) {
                performSearch(searchQuery.value)
            }
        })

        onBeforeUnmount(() => {
            stopAutoPlay()
        })

        return {
            currentIndex,
            images,
            next,
            prev,
            setCurrentIndex,
            pauseAutoPlay,
            resumeAutoPlay,
            goToDetail,
            searchQuery,
            handleSearch,
            searchResults,
            isLoading,
            hasSearched
        }
    }
}
</script>

<style scoped>
.shop-rec {
    min-height: 100vh;
    background-color: #f8f7f2;
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
    border: 1px solid #eee;
    border-radius: 12px;
    overflow: hidden;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    flex-direction: column;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(66, 185, 131, 0.1);
    border-color: #42b983;
}

.product-image {
    width: 100%;
    height: 200px;
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
    flex: 1;
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
    min-height: 42px;
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

.search-section {
    max-width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
}

.search-container {
    display: flex;
    gap: 15px;
    max-width: 600px;
    margin: 0 auto;
}

.search-input {
    flex: 1;
    padding: 12px 20px;
    font-size: 16px;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.search-input:focus {
    outline: none;
    border-color: #4CAF50;
    box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
}

.search-button {
    padding: 0 30px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    gap: 8px;
}

.search-button:hover {
    background: #43a047;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.search-results {
    max-width: 1200px;
    margin: 20px auto;
    padding: 0 20px;
}

.loading-state,
.no-results {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 16px;
    background: white;
    border-radius: 8px;
    margin: 20px 0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.products-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
    padding: 20px 0;
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

    .search-section {
        margin: 30px auto;
    }

    .search-container {
        padding: 0 15px;
    }

    .search-input {
        padding: 10px 15px;
        font-size: 15px;
    }

    .search-button {
        padding: 0 20px;
        font-size: 15px;
    }

    .products-grid {
        grid-template-columns: repeat(2, 1fr);
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

    .search-section {
        margin: 20px auto;
    }

    .search-container {
        flex-direction: column;
    }

    .search-button {
        padding: 12px 0;
    }

    .products-grid {
        grid-template-columns: 1fr;
    }
}
</style>