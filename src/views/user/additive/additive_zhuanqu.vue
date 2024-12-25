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
                    <h1>如何减少添加剂的危害</h1>
                    <button class="overall-more-button" @click="goToZhuanquInfo">更多</button>
                </div>
            </div>

            <!-- 新增的三个竖向排列的盒子 -->
            <div class="info-box" 
                 v-for="item in news.slice(0, 5)" 
                 :key="item.id" 
                 @click="goToNewsInfo(item.id)">
                <div class="info-title">{{ item.title }}</div>
                <div class="info-date">{{ item.date }}</div>
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
                         v-for="item in nutritionItems.slice(0, 3)" 
                         :key="item.id"
                         @click="goToProduct(item.id)">
                        <div class="icon">
                            <img :src="item.imgpath" :alt="item.name">
                        </div>
                        <div class="text-content">
                            <div class="text-title">{{ item.name }}</div>
                            <div class="text-description">{{ item.description }}</div>
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
        goToProduct(id) {
            console.log('跳转到商品详情，ID:', id);
            this.$router.push({
                name: 'shop_rec_info',
                params: { id: id.toString() }
            });
        },
        loadNews() {
            console.log('开始请求新闻数据');
            
            request.get('/new/getReduceDamage')
                .then(res => {
                    console.log('获取到的新闻响应:', res);
                    
                    if(res.code === '0') {
                        this.news = res.data.map(item => ({
                            id: item.id,
                            title: item.title,
                            date: item.date
                        }));
                        console.log('处理后的新闻数据:', this.news);
                    } else {
                        console.error('请求失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        },
        loadNutrition() {
            console.log('开始请求营养品数据');
            
            request.get('/shangpin')
                .then(res => {
                    console.log('获取到的营养品响应:', res);
                    
                    if(res.code === '0') {
                        this.nutritionItems = res.data.map(item => ({
                            id: item.id,
                            imgpath: item.imgpath,
                            name: item.name,
                            description: item.description
                        }));
                        console.log('处理后的营养品数据:', this.nutritionItems);
                    } else {
                        console.error('请求失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        }
    },
    data() {
        return {
            news: [],
            nutritionItems: []
        }
    },
    created() {
        this.loadNews();
        this.loadNutrition();
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
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.info-title {
    font-size: 16px;
    color: #2c3e50;
    flex-grow: 1;
    margin-right: 20px;
}

.info-date {
    color: #666;
    font-size: 14px;
    white-space: nowrap;
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
    padding: 25px;
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
    width: 120px;
    height: 120px;
    margin-right: 25px;
    border-radius: 12px;
    overflow: hidden;
    flex-shrink: 0;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.icon img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.icon-text-box:hover .icon img {
    transform: scale(1.1);
}

.text-content {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.text-title {
    font-size: 18px;
    font-weight: 600;
    color: #2c3e50;
}

.text-description {
    font-size: 14px;
    color: #666;
    line-height: 1.5;
}

.text {
    display: none;
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

    .icon {
        width: 100px;
        height: 100px;
        margin-right: 20px;
    }

    .icon-text-box {
        padding: 20px;
    }
}

@media (max-width: 480px) {
    .product-box {
        flex: 0 0 100%;
    }

    .icon {
        width: 80px;
        height: 80px;
        margin-right: 15px;
    }
}
</style>