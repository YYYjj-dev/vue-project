<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar :hideSearch="true" />

        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 搜索框 -->
            <div class="search-container">
                <!-- 返回按钮 -->
                <button class="back-button" @click="goBack">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M19 12H5M12 19l-7-7 7-7" />
                    </svg>
                    <span>分类</span>
                </button>

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

            <!-- 搜索结果状态 -->
            <div v-if="loading" class="search-status loading">
                <div class="loading-spinner"></div>
                <p>正在搜索...</p>
            </div>
            <div v-else-if="searched && !additivesList.length" class="search-status empty">
                <p>未找到相关添加剂</p>
            </div>
            <div v-else-if="searched" class="search-status results">
                <p>找到 {{ additivesList.length }} 个相关结果</p>
            </div>

            <!-- 图片网格 -->
            <div class="image-grid">
                <div class="image-item" v-for="item in additivesList" :key="item.id" @click="goToDetail(item)">
                    <div class="image-wrapper">
                        <img :src="item.image" :alt="item.name">
                        <div class="overlay">
                            <h3>{{ item.name }}</h3>
                            <p class="type">{{ item.typename }}</p>
                        </div>
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
const additivesList = ref([])
const baseUrl = 'http://localhost:8080/image/'
const searchText = ref('')
const loading = ref(false)
const searched = ref(false)

// 添加跳转详情方法
const goToDetail = (item) => {
    router.push({
        name: 'additive_type_info',
        params: { id: item.id }
    })
}

// 搜索添加剂
const searchAdditives = async (name) => {
    loading.value = true
    searched.value = true
    try {
        const { data } = await request.get('additive/findAdditiveByName', {
            params: { name }
        })
        console.log('搜索关键词:', name)
        if (data.code === 200) {
            additivesList.value = data.data.map(item => ({
                id: item.id,
                name: item.name,
                typename: item.typename,
                image: item.imgpath ? baseUrl + item.imgpath : '',
                description: item.description
            }))
            console.log('搜索结果:', additivesList.value)
        }
    } catch (error) {
        console.error('搜索添加剂失败:', error)
        additivesList.value = []
    } finally {
        loading.value = false
    }
}

// 处理搜索
const handleSearch = () => {
    if (searchText.value.trim()) {
        searchAdditives(searchText.value.trim())
    }
}

// 监听路由参数变化
watch(
    () => route.query.keyword,
    async (newKeyword) => {
        if (newKeyword) {
            console.log('搜索关键词变化:', newKeyword)
            searchText.value = newKeyword
            await searchAdditives(newKeyword)
        }
    },
    { immediate: true }
)

// 页面加载时处理
onMounted(async () => {
    const keyword = route.query.keyword
    if (keyword) {
        searchText.value = keyword
        await searchAdditives(keyword)
    }
})

// 返回分类页面
const goBack = () => {
    router.push({ name: 'additive_type' })
}
</script>

<style scoped>
/* 页面容器 */
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f5f7fa;
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
    padding: 20px 0;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 20px;
    max-width: 800px;
    margin: 0 auto;
    width: 100%;
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
    z-index: 1;
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
    transition: transform 0.3s ease;
}

.overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px;
    background: #ffffff;
    text-align: center;
    border-top: 1px solid #f0f0f0;
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.overlay h3 {
    margin: 0;
    font-size: 16px;
    color: #333;
    font-weight: 500;
    line-height: 1.4;
}

.overlay .type {
    font-size: 14px;
    color: #666;
    margin: 0;
}

/* 悬停效果 */
.image-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
}

.image-item:hover .image-wrapper img {
    transform: scale(1.05);
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

    .overlay h3 {
        font-size: 14px;
    }

    .search-icon {
        left: 16px;
    }

    .search-icon svg {
        width: 18px;
        height: 18px;
    }

    .search-status {
        padding: 15px;
        font-size: 14px;
    }

    .loading-spinner {
        width: 24px;
        height: 24px;
    }

    .search-container {
        padding: 15px;
        gap: 15px;
    }

    .back-button {
        padding: 8px 16px;
        font-size: 14px;
    }

    .back-button svg {
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

    .search-container {
        padding: 10px;
        gap: 10px;
    }

    .back-button {
        padding: 6px 12px;
    }

    .back-button span {
        display: none;  /* 在小屏幕上只显示图标 */
    }

    .back-button svg {
        width: 16px;
        height: 16px;
    }
}

/* 搜索状态样式 */
.search-status {
    text-align: center;
    padding: 20px;
    color: #666;
    margin: 20px 0;
}

.loading-spinner {
    width: 30px;
    height: 30px;
    border: 3px solid #f3f3f3;
    border-top: 3px solid #42b983;
    border-radius: 50%;
    margin: 0 auto 10px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

/* 返回按钮样式 */
.back-button {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 10px 20px;
    border: none;
    border-radius: 25px;
    background-color: #42b983;
    color: white;
    font-size: 15px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
}

.back-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.3);
    background-color: #3aa876;
}

.back-button svg {
    transition: transform 0.3s ease;
}

.back-button:hover svg {
    transform: translateX(-3px);
}
</style>