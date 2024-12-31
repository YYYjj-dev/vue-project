<template>
    <div class="cases-more">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="main-content">
            <!-- 标题 -->
            <div class="page-title">
                <h1>全部案例</h1>
            </div>

            <!-- 案例列表 -->
            <div class="cases-list">
                <div class="case-item" v-for="item in currentPageCases" :key="item.id" @click="goToCase(item.id)">
                    <div class="case-image" v-if="item.imgpath">
                        <img :src="baseUrl + item.imgpath" :alt="item.title">
                    </div>
                    <div class="case-content">
                        <h2>{{ item.title }}</h2>
                        <p class="case-desc">{{ item.content }}</p>
                        <div class="case-meta">
                            <span class="date">{{ item.date }}</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 分页器 -->
            <div class="pagination">
                <button class="page-btn" :disabled="currentPage === 1" @click="changePage(currentPage - 1)">
                    上一页
                </button>

                <div class="page-numbers">
                    <button v-for="page in totalPages" :key="page"
                        :class="['page-number', { active: currentPage === page }]" @click="changePage(page)">
                        {{ page }}
                    </button>
                </div>

                <button class="page-btn" :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">
                    下一页
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import NavBar from '../../../components/NavBar.vue'
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../../utils/request'

const router = useRouter()
const baseUrl = 'http://localhost:8080/image/'
const cases = ref([])
const pageSize = ref(10)
const currentPage = ref(1)

// 获取案例数据
const fetchCases = async () => {
    try {
        const response = await request.get('/info/findAllCases')
        if (response.data && response.data.code === 200) {
            cases.value = response.data.data
        }
    } catch (error) {
        console.error('获取案例失败:', error)
    }
}

// 计算总页数
const totalPages = computed(() => {
    return Math.ceil(cases.value.length / pageSize.value)
})

// 计算当前页显示的案例
const currentPageCases = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    return cases.value.slice(start, end)
})

// 切换页码
const changePage = (page) => {
    currentPage.value = page
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    })
}

// 跳转到案例详情
const goToCase = (id) => {
    router.push({
        name: 'cases_info',
        params: { id }
    })
}

onMounted(() => {
    fetchCases()
})
</script>

<style scoped>
.cases-more {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 60px;
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

.main-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

.page-title {
    margin-bottom: 40px;
}

.page-title h1 {
    font-size: 28px;
    font-weight: bold;
    color: #333;
    position: relative;
    padding-left: 15px;
}

.page-title h1::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 24px;
    background-color: #4CAF50;
    border-radius: 2px;
}

.cases-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin-bottom: 40px;
}

.case-item {
    background: white;
    border-radius: 12px;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    gap: 30px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.case-item:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.case-image {
    flex: 0 0 200px;
    height: 150px;
    border-radius: 8px;
    overflow: hidden;
}

.case-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.case-item:hover .case-image img {
    transform: scale(1.05);
}

.case-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.case-content h2 {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin: 0;
}

.case-desc {
    color: #666;
    font-size: 14px;
    line-height: 1.6;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.case-meta {
    margin-top: auto;
    color: #999;
    font-size: 13px;
}

/* 分页器样式 */
.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
    margin-top: 40px;
    padding: 20px 0;
}

.page-btn {
    padding: 8px 16px;
    border: none;
    background: #4CAF50;
    color: white;
    border-radius: 20px;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.3s ease;
}

.page-btn:hover:not(:disabled) {
    background: #45a049;
    transform: translateY(-2px);
}

.page-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
}

.page-numbers {
    display: flex;
    gap: 8px;
}

.page-number {
    width: 36px;
    height: 36px;
    border: none;
    background: white;
    border-radius: 50%;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 14px;
}

.page-number:hover {
    background: #f0f0f0;
}

.page-number.active {
    background: #4CAF50;
    color: white;
}

@media (max-width: 768px) {
    .case-item {
        flex-direction: column;
        gap: 15px;
    }

    .case-image {
        flex: 0 0 auto;
        height: 200px;
    }

    .pagination {
        gap: 10px;
    }

    .page-btn {
        padding: 6px 12px;
        font-size: 13px;
    }

    .page-number {
        width: 32px;
        height: 32px;
        font-size: 13px;
    }
}

@media (max-width: 480px) {
    .page-numbers {
        display: none;
    }

    .pagination {
        margin-top: 20px;
    }
}
</style>