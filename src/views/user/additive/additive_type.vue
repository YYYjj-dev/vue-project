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
                <div class="image-item" 
                     v-for="(image, index) in filteredImages" 
                     :key="index"
                     @click="goToDetail(image)">
                    <div class="image-placeholder">{{ image.title }}</div>
                </div>
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

export default {
    name: 'additive_type',
    components: {
        NavBar,
        ImageGallery,
        Footer
    },
    data() {
        return {
            selectedCategory: '防腐剂', // 默认选中的分类
            categories: [
                '防腐剂',
                '抗氧化剂',
                '甜味剂',
                '着色剂',
                '增稠剂',
                '膨松剂',
                '香料与香精'
            ],
            // 图片数据
            images: {
                '防腐剂': [
                    { id: 1, title: '防腐剂1', url: 'path/to/image1' },
                    { id: 2, title: '防腐剂2', url: 'path/to/image2' },
                    { id: 3, title: '防腐剂3', url: 'path/to/image3' },
                    { id: 4, title: '防腐剂4', url: 'path/to/image4' },
                    { id: 5, title: '防腐剂5', url: 'path/to/image5' },
                    { id: 6, title: '防腐剂6', url: 'path/to/image6' },
                    { id: 7, title: '防腐剂7', url: 'path/to/image7' },
                    { id: 8, title: '防腐剂8', url: 'path/to/image8' }
                ],
                '抗氧化剂': [
                    { id: 9, title: '抗氧化剂1', url: 'path/to/image9' },
                    { id: 10, title: '抗氧化剂2', url: 'path/to/image10' },
                    { id: 11, title: '抗氧化剂3', url: 'path/to/image11' },
                    { id: 12, title: '抗氧化剂4', url: 'path/to/image12' },
                    { id: 13, title: '抗氧化剂5', url: 'path/to/image13' },
                    { id: 14, title: '抗氧化剂6', url: 'path/to/image14' },
                    { id: 15, title: '抗氧化剂7', url: 'path/to/image15' },
                    { id: 16, title: '抗氧化剂8', url: 'path/to/image16' }
                ],
                '甜味剂': [
                    { id: 17, title: '甜味剂1', url: 'path/to/image17' },
                    { id: 18, title: '甜味剂2', url: 'path/to/image18' },
                    { id: 19, title: '甜味剂3', url: 'path/to/image19' },
                    { id: 20, title: '甜味剂4', url: 'path/to/image20' },
                    { id: 21, title: '甜味剂5', url: 'path/to/image21' },
                    { id: 22, title: '甜味剂6', url: 'path/to/image22' },
                    { id: 23, title: '甜味剂7', url: 'path/to/image23' },
                    { id: 24, title: '甜味剂8', url: 'path/to/image24' }
                ],
                '着色剂': [
                    { id: 25, title: '着色剂1', url: 'path/to/image25' },
                    { id: 26, title: '着色剂2', url: 'path/to/image26' },
                    { id: 27, title: '着色剂3', url: 'path/to/image27' },
                    { id: 28, title: '着色剂4', url: 'path/to/image28' },
                    { id: 29, title: '着色剂5', url: 'path/to/image29' },
                    { id: 30, title: '着色剂6', url: 'path/to/image30' },
                    { id: 31, title: '着色剂7', url: 'path/to/image31' },
                    { id: 32, title: '着色剂8', url: 'path/to/image32' }
                ],
                '增稠剂': [
                    { id: 33, title: '增稠剂1', url: 'path/to/image33' },
                    { id: 34, title: '增稠剂2', url: 'path/to/image34' },
                    { id: 35, title: '增稠剂3', url: 'path/to/image35' },
                    { id: 36, title: '增稠剂4', url: 'path/to/image36' },
                    { id: 37, title: '增稠剂5', url: 'path/to/image37' },
                    { id: 38, title: '增稠剂6', url: 'path/to/image38' },
                    { id: 39, title: '增稠剂7', url: 'path/to/image39' },
                    { id: 40, title: '增稠剂8', url: 'path/to/image40' }
                ],
                '膨松剂': [
                    { id: 41, title: '膨松剂1', url: 'path/to/image41' },
                    { id: 42, title: '膨松剂2', url: 'path/to/image42' },
                    { id: 43, title: '膨松剂3', url: 'path/to/image43' },
                    { id: 44, title: '膨松剂4', url: 'path/to/image44' },
                    { id: 45, title: '膨松剂5', url: 'path/to/image45' },
                    { id: 46, title: '膨松剂6', url: 'path/to/image46' },
                    { id: 47, title: '膨松剂7', url: 'path/to/image47' },
                    { id: 48, title: '膨松剂8', url: 'path/to/image48' }
                ],
                '香料与香精': [
                    { id: 49, title: '香料与香精1', url: 'path/to/image49' },
                    { id: 50, title: '香料与香精2', url: 'path/to/image50' },
                    { id: 51, title: '香料与香精3', url: 'path/to/image51' },
                    { id: 52, title: '香料与香精4', url: 'path/to/image52' },
                    { id: 53, title: '香料与香精5', url: 'path/to/image53' },
                    { id: 54, title: '香料与香精6', url: 'path/to/image54' },
                    { id: 55, title: '香料与香精7', url: 'path/to/image55' },
                    { id: 56, title: '香料与香精8', url: 'path/to/image56' }
                ]
            }
        }
    },
    computed: {
        // 根据选中的分类过滤图片
        filteredImages() {
            return this.images[this.selectedCategory] || []
        }
    },
    methods: {
        selectCategory(category) {
            this.selectedCategory = category
        },
        goToDetail(image) {
            this.$router.push({
                name: 'additive_type_info',
                params: {
                    category: this.selectedCategory,
                    imageId: image.id
                }
            })
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
    display: grid;
    grid-template-columns: repeat(4, 250px);
    gap: 20px;
    padding: 20px;
    justify-content: center;
}

.image-item {
    aspect-ratio: 1;
    border-radius: 8px;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.image-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.image-placeholder {
    width: 100%;
    height: 100%;
    background-color: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    border: 1px solid #ddd;
    transition: all 0.3s ease;
}

.image-item:hover .image-placeholder {
    background-color: #e8e8e8;
    color: #333;
    border-color: #999;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .image-grid {
        grid-template-columns: repeat(3, 250px);
    }
}

@media (max-width: 900px) {
    .image-grid {
        grid-template-columns: repeat(2, 250px);
    }
}

@media (max-width: 600px) {
    .image-grid {
        grid-template-columns: repeat(1, 250px);
    }
}
</style>