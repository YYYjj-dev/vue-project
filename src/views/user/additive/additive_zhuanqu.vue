<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />

        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <div class="carousel-container" @mouseenter="pauseAutoPlay" @mouseleave="resumeAutoPlay">
                <div class="carousel-wrapper" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
                    <div v-for="(image, index) in images" :key="index" class="carousel-item">
                        <img :src="image" :alt="`轮播图 ${index + 1}`" class="carousel-image">
                    </div>
                </div>

                <!-- 轮播图指示器 -->
                <div class="carousel-indicators">
                    <span v-for="(_, index) in images" :key="index"
                        :class="['indicator', { active: currentIndex === index }]"
                        @click="setCurrentIndex(index)"></span>
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

            <!-- 第一个标题和按钮的容器 -->
            <div class="section-container">
                <div class="section-header">
                    <h1>如何避免添加剂的危害</h1>
                    <button class="overall-more-button" @click="goToZhuanquInfo">更多</button>
                </div>
            </div>

            <!-- 新增的三个竖向排列的盒子 -->
            <div class="info-box" v-for="item in news" :key="item.id" @click="goToNewsInfo(item.id)">
                {{ item.title }}
                <div v-if="!news.length" class="no-news">
                    <p>暂无相关新闻</p>
                </div>
            </div>

            <!-- 营养品与添加剂部分 -->
            <div class="section-container">
                <div class="section-header">
                    <h1>营养品与添加剂</h1>
                    <button class="overall-more-button" @click="goToZhuanquInfo2">更多</button>
                </div>
                <!-- 内容盒子 -->
                <div class="content-box">
                    <div v-if="loading" class="loading-state">
                        <p>加载中...</p>
                    </div>
                    <div v-else-if="!nutritionItems.length" class="empty-state">
                        <p>暂无相关商品</p>
                    </div>
                    <div class="icon-text-box" v-for="item in nutritionItems" :key="item.id"
                        @click="goToProduct(item.id)">
                        <div class="product-image">
                            <img v-if="item.imgpath" :src="baseUrl + item.imgpath" :alt="item.name">
                            <div v-else class="placeholder">
                                <span>{{ item.name?.charAt(0) }}</span>
                            </div>
                        </div>
                        <div class="product-info">
                            <h3>{{ item.name }}</h3>
                            <p>{{ item.description }}</p>
                            <span class="price" v-if="item.price">¥{{ item.price }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import Carousel from '../../../components/Carousel.vue';
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

export default {
    name: 'additive_zhuanqu',
    components: {
        NavBar,
        Carousel,
        Footer
    },
    methods: {
        goToNewsInfo(id) {
            this.$router.push({ name: 'news_info', params: { id } });
        },
        goToZhuanquInfo() {
            this.$router.push('/additive_zhuanqu_info1')
        },
        goToZhuanquInfo2() {
            this.$router.push('/additive_zhuanqu_info2')
        },
        async fetchNews() {
            try {
                const { data } = await request.get('/info/findAllNews')
                if (data.code === 200) {
                    this.news = data.data.slice(0, 5)
                } else {
                    console.warn('获取新闻失败:', data.msg)
                }
            } catch (error) {
                console.error('获取新闻出错:', error)
            }
        },
        prevSlide() {
            if (this.currentIndex > 0) {
                this.currentIndex--;
            } else {
                this.currentIndex = this.maxIndex;
            }
        },
        nextSlide() {
            if (this.currentIndex < this.maxIndex) {
                this.currentIndex++;
            } else {
                this.currentIndex = 0;
            }
        },
        goToProduct(id) {
            this.$router.push({
                name: 'shop_rec_info',
                params: { id: id.toString() }
            })
        },
        // 获取营养品数据
        async fetchNutritionItems() {
            this.loading = true
            try {
                const { data } = await request.get('/shangpin/findShangpinByType', {
                    params: { type: '营养品' }  // 假设 'nutrition' 是营养品分组
                })
                if (data.code === 200) {
                    this.nutritionItems = data.data || []
                } else {
                    console.warn('获取营养品数据失败:', data.msg)
                }
            } catch (error) {
                console.error('获取营养品数据出错:', error)
            } finally {
                this.loading = false
            }
        },
        next() {
            this.currentIndex = (this.currentIndex + 1) % this.images.length
        },
        prev() {
            this.currentIndex = this.currentIndex === 0
                ? this.images.length - 1
                : this.currentIndex - 1
        },
        setCurrentIndex(index) {
            this.currentIndex = index
        },
        startAutoPlay() {
            if (!this.isPaused) {
                this.autoPlayInterval = setInterval(() => {
                    this.next()
                }, 5000)
            }
        },
        stopAutoPlay() {
            if (this.autoPlayInterval) {
                clearInterval(this.autoPlayInterval)
                this.autoPlayInterval = null
            }
        },
        pauseAutoPlay() {
            this.isPaused = true
            this.stopAutoPlay()
        },
        resumeAutoPlay() {
            this.isPaused = false
            this.startAutoPlay()
        },
    },
    data() {
        return {
            news: [],
            currentIndex: 0,
            nutritionItems: [],
            loading: false,
            baseUrl: 'http://localhost:8080/image/',
            images: [
                '/src/img/img4.jpg',
                '/src/img/img7.jpg',
                '/src/img/img6.jpg',
                '/src/img/img2.jpg',
                '/src/img/img3.jpg',
                '/src/img/img5.jpg',
            ],
            autoPlayInterval: null,
            isPaused: false,
        }
    },
    mounted() {
        this.fetchNews()
        this.fetchNutritionItems()
        this.startAutoPlay()
    },
    beforeUnmount() {
        this.stopAutoPlay()
    },
    computed: {
        maxIndex() {
            return Math.max(0, this.products.length - 4);
        }
    }
}
</script>

<style scoped>
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f9fa;
}

.content-wrapper {
    flex: 1;
    padding: 0 0 40px 0;
    max-width: 1200px;
    margin: 0 auto;
}

/* 标题和按钮容器 */
.section-container {
    width: 100%;
    max-width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    position: relative;
    padding-bottom: 15px;
    border-bottom: 2px solid #f0f0f0;
}

.section-header h1 {
    margin: 0;
    font-size: 24px;
    color: #2c3e50;
    font-weight: 600;
    position: relative;
}

.section-header h1::after {
    content: '';
    position: absolute;
    bottom: -17px;
    left: 0;
    width: 60px;
    height: 2px;
    background: #42b983;
}

.overall-more-button {
    padding: 10px 25px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.overall-more-button:hover {
    background: #3aa876;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(66, 185, 131, 0.3);
}

/* 信息盒子 */
.info-box {
    width: 100%;
    max-width: 1200px;
    margin: 20px auto;
    padding: 25px 20px;
    background: white;
    text-align: left;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    border: 1px solid #eee;
}

.info-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
    border-color: #42b983;
}

/* 内容盒子 */
.content-box {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin-top: 30px;
}

.icon-text-box {
    display: flex;
    align-items: center;
    background: white;
    padding: 20px;
    border-radius: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.03);
    border: 1px solid #eee;
    overflow: hidden;
    min-height: 200px;
    position: relative;
}

.icon-text-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 20px 30px rgba(66, 185, 131, 0.1);
    border-color: #42b983;
}

.product-image {
    width: 200px;
    height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    transition: all 0.3s ease;
    overflow: hidden;
    position: relative;
    background: #f8f9fa;
    border-radius: 8px;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    transition: transform 0.5s ease;
}

.icon-text-box:hover .product-image img {
    transform: scale(1.08);
}

.placeholder {
    width: 100%;
    height: 100%;
    background: linear-gradient(145deg, #42b983 0%, #3aa876 60%, #2d8b5f 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 32px;
    text-transform: uppercase;
    font-weight: 600;
    border-radius: 8px;
}

.product-info {
    flex-grow: 1;
    color: #2c3e50;
    padding: 0 20px;
    display: flex;
    flex-direction: column;
    background: white;
    position: relative;
    z-index: 1;
}

.product-info h3 {
    font-size: 18px;
    color: #2c3e50;
    margin: 0;
    margin-bottom: 12px;
    font-weight: 600;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.product-info p {
    font-size: 14px;
    color: #666;
    margin: 0;
    flex-grow: 1;
    line-height: 1.6;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    opacity: 0.85;
}

.product-info .price {
    color: #42b983;
    font-weight: 500;
    font-size: 18px;
    margin-top: 12px;
    display: flex;
    align-items: center;
    gap: 4px;
}

.product-info .price::before {
    content: '￥';
    font-size: 0.8em;
    opacity: 0.9;
}

/* 响应式调整 */
@media (max-width: 1024px) {
    .content-wrapper {
        padding: 0 15px 40px;
    }
}

@media (max-width: 768px) {
    .section-container {
        margin: 30px auto;
    }

    .info-box {
        margin: 15px auto;
        padding: 20px;
    }

    .icon-text-box {
        min-height: 160px;
        padding: 15px;
    }

    .product-image {
        width: 160px;
        height: 160px;
    }

    .product-info {
        padding: 0 15px;
    }

    .product-info h3 {
        font-size: 16px;
        margin-bottom: 8px;
        -webkit-line-clamp: 1;
    }

    .product-info p {
        font-size: 13px;
        -webkit-line-clamp: 2;
    }

    .product-info .price {
        font-size: 16px;
        margin-top: 8px;
    }
}

@media (max-width: 480px) {
    .section-header h1 {
        font-size: 20px;
    }

    .content-box {
        padding: 0 15px;
        gap: 15px;
    }

    .icon-text-box {
        min-height: 140px;
        padding: 12px;
    }

    .product-image {
        width: 140px;
        height: 140px;
    }

    .product-info {
        padding: 0 12px;
    }

    .product-info h3 {
        font-size: 15px;
        margin-bottom: 6px;
    }

    .product-info p {
        font-size: 12px;
        -webkit-line-clamp: 2;
    }

    .product-info .price {
        font-size: 15px;
        margin-top: 6px;
    }

    .placeholder {
        font-size: 24px;
    }
}

.no-news {
    text-align: center;
    padding: 30px;
    color: #666;
    font-size: 1rem;
}

.loading-state,
.empty-state {
    text-align: center;
    padding: 40px;
    color: #666;
}

.carousel-container {
    position: relative;
    width: 100%;
    height: 500px;
    overflow: hidden;
    border-radius: 12px;
    margin-top: 20px;
}

.carousel-wrapper {
    display: flex;
    transition: transform 0.5s ease;
    width: 100%;
    height: 100%;
}

.carousel-item {
    min-width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
}

.carousel-indicators {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
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

@media (max-width: 768px) {
    .carousel-container {
        height: 300px;
    }
}

@media (max-width: 480px) {
    .carousel-container {
        height: 200px;
    }
}
</style>