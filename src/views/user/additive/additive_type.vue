<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar :hideSearch="true" />
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 搜索框 -->
            <div class="search-container">
                <input type="text" class="search-input" placeholder="搜索框">
            </div>

            <!-- 分类按钮组 -->
            <div class="category-buttons">
                <button 
                    v-for="category in categories" 
                    :key="category" 
                    class="category-btn"
                    :class="{ active: selectedCategory === category }"
                    @click="selectCategory(category)">
                    {{ category }}
                </button>
            </div>

            <!-- 图片网格 -->
            <div class="image-grid">
                <div class="carousel-item" 
                     v-for="(image, index) in currentPageImages" 
                     :key="index"
                     @click="goToDetail(image.id)">
                    <div class="item-card">
                        <div class="item-image">
                            <img :src="image.url" :alt="image.title">
                            <div class="item-overlay">
                                <span class="view-more">查看详情</span>
                            </div>
                        </div>
                        <div class="item-content">
                            <h3 class="item-title">{{ image.title }}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 分页器 -->
            <div class="pagination">
                <button 
                    class="page-btn" 
                    :disabled="currentPage === 1"
                    @click="changePage(currentPage - 1)">
                    上一页
                </button>
                <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
                <button 
                    class="page-btn" 
                    :disabled="currentPage === totalPages"
                    @click="changePage(currentPage + 1)">
                    下一页
                </button>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import ImageGallery from '../../../components/ImageGallery.vue'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

export default {
    name: 'additive_type',
    components: {
        NavBar,
        ImageGallery,
        Footer
    },
    data() {
        return {
            selectedCategory: localStorage.getItem('lastCategory') || '防腐剂',
            categories: [
                '防腐剂',
                '抗氧化剂',
                '甜味剂',
                '着色剂',
                '增稠剂',
                '膨松剂',
                '香料与香精'
            ],
            images: [],
            currentPage: 1,
            pageSize: 8
        }
    },
    computed: {
        filteredImages() {
            return this.images;
        },
        
        currentPageImages() {
            const start = (this.currentPage - 1) * this.pageSize;
            const end = start + this.pageSize;
            return this.filteredImages.slice(start, end);
        },
        
        totalPages() {
            return Math.ceil(this.filteredImages.length / this.pageSize);
        }
    },
    created() {
        this.loadAdditives(this.selectedCategory);
    },
    methods: {
        loadAdditives(category) {
            console.log('开始请求添加剂数据:', category);
            
            const apiMap = {
                '防腐剂': '/additive/getFangFuJi',
                '抗氧化剂': '/additive/getKangYangHuaJi',
                '甜味剂': '/additive/getTianWeiJi',
                '着色剂': '/additive/getZhuoSeJi',
                '增稠剂': '/additive/getZengChouJi',
                '膨松剂': '/additive/getPengSongJi',
                '香料与香精': '/additive/getXiangLiao'
            };
            
            const api = apiMap[category];
            if (!api) return;

            request.get(api)
                .then(res => {
                    console.log('获取到的响应:', res);
                    
                    if(res.code === '0') {
                        this.images = res.data.map(item => ({
                            id: item.id,
                            title: item.name,
                            url: item.imgpath,
                            typename: item.typename,
                            description: item.description
                        }));
                        console.log('处理后的添加剂数据:', this.images);
                    } else {
                        console.error('请求失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        },
        
        selectCategory(category) {
            this.selectedCategory = category;
            this.currentPage = 1;
            localStorage.setItem('lastCategory', category);
            this.loadAdditives(category);
        },
        
        goToDetail(id) {
            this.$router.push(`/additive_type_info/${id}`);
        },
        // 切换页面
        changePage(page) {
            if (page >= 1 && page <= this.totalPages) {
                this.currentPage = page;
            }
        }
    }
}
</script>
<style scoped>
/* 页面容器 */
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

/* 内容包装器 */
.content-wrapper {
    flex: 1;
    margin: 0 auto;
    width: 70%;
    max-width: 1200px;
    margin-bottom: 4rem;
    padding: 20px;
}

/* 搜索框样式 */
.search-container {
    width: 100%;
    padding: 20px;
    display: flex;
    justify-content: center;
}

.search-input {
    width: 100%;
    max-width: 800px;
    padding: 12px 20px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
    outline: none;
}

.search-input:focus {
    border-color: #4CAF50;
}

/* 分类按钮组样式 */
.category-buttons {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;
    margin: 30px 0;
    padding: 0 20px;
}

.category-btn {
    padding: 8px 24px;
    border: 1px solid #ddd;
    border-radius: 20px;
    background: white;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.category-btn:hover:not(.active) {
    border-color: #4CAF50;
    color: #4CAF50;
}

/* 添加选中按钮的样式 */
.category-btn.active {
    background-color: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

/* 图片网格样式 */
.image-grid {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;
    padding: 20px;
    justify-content: center;
}

/* 添加卡片样式 */
.carousel-item {
    padding: 0 15px;
}

.item-card {
    background: white;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    cursor: pointer;
    height: 100%;
}

.item-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.item-image {
    position: relative;
    height: 200px;
    overflow: hidden;
}

.item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s ease;
}

.item-card:hover .item-image img {
    transform: scale(1.1);
}

.item-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(66, 185, 131, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.item-card:hover .item-overlay {
    opacity: 1;
}

.view-more {
    color: white;
    font-size: 16px;
    font-weight: 500;
    padding: 8px 20px;
    border: 2px solid white;
    border-radius: 20px;
}

.item-content {
    padding: 20px;
}

.item-title {
    font-size: 18px;
    color: #2c3e50;
    margin-bottom: 10px;
    font-weight: 600;
    text-align: center;
}

/* 响应式调整 */
@media (max-width: 1400px) {
    .content-wrapper {
        width: 80%;
    }
}

@media (max-width: 1200px) {
    .content-wrapper {
        width: 85%;
    }
    
    .image-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 768px) {
    .content-wrapper {
        width: 95%;
    }
    
    .image-grid {
        grid-template-columns: repeat(1, 1fr);
    }
    
    .carousel-item {
        padding: 0;
    }
    
    .item-card {
        max-width: 400px;
        margin: 0 auto;
    }
}

/* 分页器样式 */
.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
    margin-top: 30px;
    padding: 20px 0;
}

.page-btn {
    padding: 8px 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
}

.page-btn:hover:not(:disabled) {
    background-color: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

.page-btn:disabled {
    background-color: #f5f5f5;
    color: #999;
    cursor: not-allowed;
}

.page-info {
    font-size: 14px;
    color: #666;
}
</style>