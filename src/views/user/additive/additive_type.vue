<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar :hideSearch="true" />
        </div>

        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 搜索框 -->
            <div class="search-container">
                <div class="search-box">
                    <i class="search-icon" @click="handleSearch" :class="{ 'clickable': searchText }">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="11" cy="11" r="8"></circle>
                            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                        </svg>
                    </i>
                    <input type="text" class="search-input" placeholder="搜索添加剂..." v-model="searchText"
                        @keyup.enter="handleSearch">
                </div>
            </div>

            <!-- 分类按钮组 -->
            <div class="category-buttons">
                <button v-for="category in categories" :key="category" class="category-btn"
                    :class="{ active: selectedCategory === category }" @click="selectCategory(category)">
                    {{ category }}
                </button>
            </div>

            <!-- 图片网格 -->
            <div class="image-grid">
                <div class="image-item" v-for="item in additivesList" :key="item.id" @click="goToDetail(item)">
                    <div class="image-wrapper">
                        <img :src="item.image" :alt="item.name">
                        <div class="overlay">
                            <span class="view-more">查看详情</span>
                        </div>
                    </div>
                    <div class="image-info">
                        <h3>{{ item.name }}</h3>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '../../../utils/request'
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'

const router = useRouter()
const route = useRoute()
const categories = ref(['防腐剂', '抗氧化剂', '甜味剂', '着色剂', '增稠剂', '香料与香精', '膨松剂'])
const selectedCategory = ref('')
const additivesList = ref([])
const baseUrl = 'http://localhost:8080/image/'
const searchText = ref('')

// 处理搜索
const handleSearch = () => {
    if (searchText.value.trim()) {
        router.push({
            name: 'additive_search',
            query: { keyword: searchText.value.trim() }
        })
    }
}

// 添加跳转详情方法
const goToDetail = (item) => {
    router.push({
        name: 'additive_type_info',
        params: { id: item.id }
    })
}

// 根据种类筛选添加剂
const filterAdditives = async (category) => {
    try {
        const { data } = await request.get('additive/findAdditiveByType', {
            params: { typename: category }
        })
        console.log(category)
        console.log(data)
        if (data.code === 200) {
            additivesList.value = data.data.map(item => ({
                id: item.id,
                name: item.name,
                typename: item.typename,
                image: item.imgpath ? baseUrl + item.imgpath : '',
                description: item.description
            }))
            console.log('获取到的添加剂列表:', additivesList.value) // 用于调试
        }
    } catch (error) {
        console.error('获取添加剂列表失败:', error)
        additivesList.value = []
    }
}

// 选择种类
const selectCategory = async (category) => {
    selectedCategory.value = category
    await filterAdditives(category)
    console.log('当前选中种类:', category) // 用于调试
}

// 监听路由参数变化
watch(
    () => route.params.typename,
    async (newType) => {
        if (newType) {
            console.log('路由参数变化:', newType) // 用于调试
            selectedCategory.value = newType
            await filterAdditives(newType)
        }
    },
    { immediate: true }
)

// 页面加载时处理
onMounted(async () => {
    try {
        // 如果有路由参数，使用参数的种类
        if (route.params.typename) {
            selectedCategory.value = route.params.typename
            await filterAdditives(route.params.typename)
        } else {
            // 否则默认选择第一个种类
            selectedCategory.value = categories.value[0]
            await filterAdditives(categories.value[0])
        }
    } catch (error) {
        console.error('获取添加剂列表失败:', error)
    }
})
</script>

<style scoped>
/* 页面容器 */
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f9fa;
    padding-top: 80px;
}

/* 导航栏包装器样式 */
.nav-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 内容包装器 */
.content-wrapper {
    flex: 1;
    margin: 20px auto 60px;
    padding: 0 20px;
    max-width: 1400px;
    width: 100%;
}

/* 搜索框样式 */
.search-container {
    width: 100%;
    padding: 20px 0;
    display: flex;
    justify-content: center;
}

.search-box {
    width: 100%;
    max-width: 600px;
    position: relative;
    display: flex;
    align-items: center;
}

.search-icon {
    position: absolute;
    left: 20px;
    color: #999;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;
    cursor: default;
}

.search-icon.clickable {
    cursor: pointer;
    color: #42b983;
}

.search-icon.clickable:hover {
    transform: scale(1.1);
}

.search-input {
    width: 100%;
    padding: 14px 24px 14px 56px;
    font-size: 16px;
    border: 2px solid #eaeaea;
    border-radius: 30px;
    outline: none;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    background-color: white;
}

.search-input:focus {
    border-color: #42b983;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.1);
}

.search-input:focus+.search-icon,
.search-box:hover .search-icon {
    color: #42b983;
}

.search-input::placeholder {
    color: #999;
    font-weight: 400;
}

/* 分类按钮组样式 */
.category-buttons {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 16px;
    margin: 40px 0;
    padding: 0 20px;
}

.category-btn {
    padding: 10px 28px;
    border: 2px solid #e0e0e0;
    border-radius: 25px;
    background: white;
    font-size: 15px;
    font-weight: 500;
    color: #666;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.category-btn:hover:not(.active) {
    border-color: #42b983;
    color: #42b983;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.15);
}

.category-btn.active {
    background-color: #42b983;
    color: white;
    border-color: #42b983;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

/* 图片网格样式 */
.image-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 30px;
    padding: 20px 0;
    margin: 0 auto;
}

.image-item {
    background: white;
    border-radius: 12px;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.image-wrapper {
    width: 100%;
    padding-top: 100%;
    /* 保持1:1的宽高比 */
    position: relative;
    overflow: hidden;
}

.image-wrapper img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.4s ease;
}

.overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.5);
    text-align: center;
    opacity: 0;
    transition: opacity 0.3s ease;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
}

.view-more {
    margin: 0;
    font-size: 16px;
    color: white;
    font-weight: 500;
    padding: 8px 20px;
    border: 2px solid white;
    border-radius: 20px;
    transform: translateY(20px);
    transition: transform 0.3s ease;
}

/* 添加剂信息样式 */
.image-info {
    padding: 15px;
    background: white;
    border-top: 1px solid #f0f0f0;
}

.image-info h3 {
    margin: 0;
    font-size: 16px;
    color: #333;
    font-weight: 500;
    line-height: 1.4;
    text-align: center;
}

/* 悬停效果 */
.image-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
}

.image-item:hover .image-wrapper img {
    transform: scale(1.08);
}

.image-item:hover .overlay {
    opacity: 1;
}

.image-item:hover .view-more {
    transform: translateY(0);
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .content-wrapper {
        max-width: 1100px;
    }
}

@media (max-width: 768px) {
    .content-wrapper {
        padding: 0 15px;
        margin: 10px auto 40px;
    }

    .search-input {
        padding: 12px 20px 12px 48px;
        font-size: 15px;
    }

    .category-buttons {
        gap: 12px;
        margin: 30px 0;
    }

    .category-btn {
        padding: 8px 20px;
        font-size: 14px;
    }

    .image-grid {
        grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
        gap: 20px;
    }

    .view-more {
        font-size: 16px;
        padding: 6px 16px;
    }

    .image-info h3 {
        font-size: 14px;
    }

    .search-icon {
        left: 16px;
    }

    .search-icon svg {
        width: 18px;
        height: 18px;
    }
}

@media (max-width: 480px) {
    .search-input {
        padding: 10px 16px 10px 44px;
        font-size: 14px;
    }

    .search-icon {
        left: 14px;
    }

    .search-icon svg {
        width: 16px;
        height: 16px;
    }

    .view-more {
        font-size: 14px;
        padding: 5px 14px;
        border-width: 1px;
    }

    .image-info h3 {
        font-size: 13px;
    }
}
</style>