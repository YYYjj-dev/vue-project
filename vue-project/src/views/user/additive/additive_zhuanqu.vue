<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <Carousel />
            
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
            </div>

            <!-- 营养品与添加剂部分 -->
            <div class="section-container">
                <div class="section-header">
                    <h1>营养品与添加剂</h1>
                    <button class="overall-more-button" @click="goToZhuanquInfo2">更多</button>
                </div>
                <!-- 新增的内容盒子 -->
                <div class="content-box">
                    <div class="icon-text-box" 
                         v-for="item in nutritionItems" 
                         :key="item.id"
                         @click="goToProduct(item.id)">
                        <div class="icon">{{ item.icon }}</div>
                        <div class="text">{{ item.content }}</div>
                    </div>
                </div>
            </div>

            <!-- 商品推荐部分 -->
            <div class="product-section">
                <h2>商品推荐</h2>
                <div class="product-carousel">
                    <button class="carousel-btn prev" @click="prevSlide">
                        &lt;
                    </button>
                    <div class="product-container">
                        <div class="product-wrapper" :style="{ transform: `translateX(-${currentIndex * (100 / 4)}%)` }">
                            <div v-for="(product, index) in products" :key="index" class="product-box">
                                <div class="product-image">
                                    <img :src="product.image" :alt="product.title">
                                </div>
                                <div class="product-info">
                                    <h3 class="product-title">{{ product.title }}</h3>
                                    <p class="product-price">¥{{ product.price }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-btn next" @click="nextSlide">
                        &gt;
                    </button>
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
        }
    },
    data() {
        return {
            news: [
                { id: 1, title: '资讯报道 1', description: '这是资讯报道 1 的简要描述。' },
                { id: 2, title: '资讯报道 2', description: '这是资讯报道 2 的简要描述。' },
                { id: 3, title: '资讯报道 3', description: '这是资讯报道 3 的简要描述。' },
            ],
            currentIndex: 0,
            products: [
                { id: 1, image: '商品图片1', title: '商品1', price: '99.00' },
                { id: 2, image: '商品图片2', title: '商品2', price: '199.00' },
                { id: 3, image: '商品图片3', title: '商品3', price: '299.00' },
                { id: 4, image: '商品图片4', title: '商品4', price: '399.00' },
                { id: 5, image: '商品图片5', title: '商品5', price: '499.00' },
                { id: 6, image: '商品图片6', title: '商品6', price: '599.00' }
            ],
            nutritionItems: [
                { id: 101, icon: '图标1', content: '营养品内容1', price: '199.00' },
                { id: 102, icon: '图标2', content: '营养品内容2', price: '299.00' },
                { id: 103, icon: '图标3', content: '营养 品内容3', price: '399.00' }
            ]
        }
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
    background-color: #f8f7f2;
}

.content-wrapper {
    flex: 1;
    margin-bottom: 4rem;
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 标题和按钮容器 */
.section-container {
    width: 90%;
    max-width: 1200px;
    margin: 40px auto;
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
    width: 90%;
    max-width: 1200px;
    margin: 20px auto;
    padding: 25px;
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
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
    border: 1px solid #eee;
}

.icon-text-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
    border-color: #42b983;
}

.icon {
    width: 60px;
    height: 60px;
    background: linear-gradient(135deg, #42b983 0%, #3aa876 100%);
    margin-right: 20px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 24px;
    flex-shrink: 0;
    transition: all 0.3s ease;
}

.icon-text-box:hover .icon {
    transform: scale(1.1);
}

.text {
    flex-grow: 1;
    font-size: 16px;
    color: #2c3e50;
    line-height: 1.5;
}

/* 商品推荐部分 */
.product-section {
    width: 90%;
    max-width: 1200px;
    margin: 40px auto;
}

.product-carousel {
    position: relative;
    display: flex;
    align-items: center;
    margin: 20px 0;
    padding: 0 50px;
}

.carousel-btn {
    width: 40px;
    height: 40px;
    background: white;
    border: none;
    border-radius: 50%;
    cursor: pointer;
    z-index: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    position: absolute;
    color: #42b983;
    font-size: 18px;
}

.carousel-btn.prev {
    left: -10px;
}

.carousel-btn.next {
    right: -10px;
}

.carousel-btn:hover {
    background: #42b983;
    color: white;
    transform: scale(1.1);
}

.carousel-btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.product-container {
    width: 100%;
    overflow: hidden;
}

.product-wrapper {
    display: flex;
    gap: 20px;
    transition: transform 0.5s ease;
}

.product-box {
    flex: 0 0 calc((100% - 60px) / 4);
    background: white;
    border-radius: 12px;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
}

.product-image {
    width: 100%;
    height: 200px;
    background: #f8f9fa;
    display: flex;
    align-items: center;
    justify-content: center;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.product-info {
    padding: 15px;
}

.product-title {
    font-size: 16px;
    color: #2c3e50;
    margin-bottom: 10px;
}

.product-price {
    color: #42b983;
    font-weight: 600;
    font-size: 18px;
}

@media (max-width: 1024px) {
    .product-box {
        flex: 0 0 calc((100% - 40px) / 3);
    }
}

@media (max-width: 768px) {
    .product-box {
        flex: 0 0 calc((100% - 20px) / 2);
    }
}

@media (max-width: 480px) {
    .product-box {
        flex: 0 0 100%;
    }
}
</style>