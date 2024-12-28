<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />

        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 返回按钮 -->
            <button class="back-button" @click="goBack">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M19 12H5M12 19l-7-7 7-7" />
                </svg>
                <span>分类</span>
            </button>

            <!-- 顶部图片和介绍部分 -->
            <div class="top-section">
                <div class="image-box">
                    <img :src="baseUrl + additiveInfo.imgpath" :alt="additiveInfo.name">
                </div>
                <div class="intro-box">
                    <div class="intro-content">
                        <div class="title-section">
                            <h2 class="additive-name">{{ additiveInfo.name }}</h2>
                            <div class="nature-text">{{ additiveInfo.nature }}</div>
                        </div>
                        <div class="intro-text">{{ additiveInfo.description }}</div>
                    </div>
                </div>
            </div>

            <!-- 摄入标准按钮 -->
            <div class="section-title">
                摄入标准
            </div>

            <!-- 国内外标准对比 -->
            <div class="standards-section">
                <div class="standard-box">
                    <h3>国内标准</h3>
                    <div class="standard-text">{{ additiveInfo.usestandardInternal }}</div>
                </div>
                <div class="standard-box">
                    <h3>国际标准</h3>
                    <div class="standard-text">{{ additiveInfo.usestandardInternational }}</div>
                </div>
            </div>

            <!-- 健康分析按钮 -->
            <div class="section-title">
                健康分析
            </div>

            <!-- 内容展示区域 -->
            <div class="content-box">
                <div class="content-text">{{ additiveInfo.analysis }}</div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script setup>
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '../../../utils/request'

const route = useRoute()
const router = useRouter()
const baseUrl = 'http://localhost:8080/image/'

// 定义响应式数据
const additiveInfo = ref({
    id: '',
    name: '',
    description: '',
    imgpath: '',
    usestandardInternal: '',  // 国内标准
    usestandardInternational: '', // 国际标准
    analysis: '',  // 健康分析
    nature: '',  // 性质
    type: ''  // 添加type字段
})

// 获取添加剂详情数据
const getAdditiveInfo = async () => {
    try {
        const { data } = await request.get('/additive/findAdditiveById', {
            params: { id: route.params.id }
        })
        if (data.code === 200 && data.data) {
            additiveInfo.value = {
                ...data.data,
                // 确保使用typename而不是type
                type: data.data.typename || '防腐剂' // 使用typename字段
            }
            console.log('添加剂详情:', additiveInfo.value) // 用于调试
        }
    } catch (error) {
        console.error('获取添加剂详情失败:', error)
    }
}

// 返回上一页方法
const goBack = () => {
    router.push({
        name: 'additive_type',
        params: { typename: additiveInfo.value.typename || additiveInfo.value.type }  // 优先使用typename
    })
}

// 页面加载时获取数据
onMounted(() => {
    getAdditiveInfo()
})
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
    max-width: 1200px;
    width: 100%;
    margin: 80px auto 40px;
    padding: 0 40px;
}

/* 顶部部分样式优化 */
.top-section {
    display: flex;
    gap: 50px;
    margin-bottom: 40px;
    background: #fff;
    padding: 30px;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.image-box {
    flex: 0 0 450px;
    height: 450px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: transform 0.3s ease;
}

.image-box:hover {
    transform: translateY(-5px);
}

.image-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.image-box:hover img {
    transform: scale(1.05);
}

.intro-box {
    flex: 1;
    border: none;
    border-radius: 12px;
    padding: 30px;
    background: #fff;
}

/* 内容样式优化 */
.intro-content {
    height: 100%;
    display: flex;
    flex-direction: column;
    gap: 25px;
}

.additive-name {
    font-size: 32px;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 15px;
    position: relative;
    padding-bottom: 15px;
}

.additive-name::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 60px;
    height: 3px;
    background: #42b983;
    border-radius: 2px;
}

.intro-text {
    font-size: 16px;
    line-height: 1.8;
    color: #5c6a7d;
}

.nature-text {
    margin-top: 25px;
    padding: 20px;
    background: #f8f9fa;
    border-radius: 12px;
}

.nature-text h3 {
    font-size: 20px;
    color: #2c3e50;
    margin-bottom: 15px;
    font-weight: 600;
}

.nature-text p {
    font-size: 16px;
    line-height: 1.8;
    color: #5c6a7d;
}

/* 标准部分样式优化 */
.section-title {
    font-size: 26px;
    font-weight: 600;
    color: #2c3e50;
    margin: 50px 0 30px;
    padding-left: 20px;
    border-left: 4px solid #42b983;
    line-height: 1.4;
}

.standards-section {
    display: flex;
    gap: 30px;
    margin-top: 25px;
}

.standard-box {
    flex: 1;
    min-height: 280px;
    background: #fff;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
    transition: transform 0.3s ease;
}

.standard-box:hover {
    transform: translateY(-5px);
}

.standard-box h3 {
    font-size: 22px;
    color: #2c3e50;
    margin-bottom: 20px;
    text-align: center;
    font-weight: 600;
}

.standard-text {
    font-size: 16px;
    line-height: 1.8;
    color: #5c6a7d;
    white-space: pre-wrap;
}

/* 健康分析部分样式优化 */
.content-box {
    background: #fff;
    border-radius: 16px;
    padding: 35px;
    width: 100%;
    min-height: 300px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
    margin-top: 25px;
}

.content-text {
    font-size: 16px;
    line-height: 1.8;
    color: #5c6a7d;
    white-space: pre-wrap;
}

/* 响应式优化 */
@media (max-width: 1200px) {
    .content-wrapper {
        padding: 0 20px;
        margin: 60px auto 30px;
    }

    .top-section {
        gap: 30px;
        padding: 25px;
    }

    .image-box {
        flex: 0 0 400px;
        height: 400px;
    }
}

@media (max-width: 768px) {
    .content-wrapper {
        margin: 40px auto 20px;
    }

    .top-section {
        flex-direction: column;
        padding: 20px;
    }

    .image-box {
        width: 100%;
        height: 300px;
    }

    .standards-section {
        flex-direction: column;
        gap: 20px;
    }

    .standard-box {
        min-height: auto;
        padding: 25px;
    }

    .additive-name {
        font-size: 26px;
    }

    .section-title {
        font-size: 22px;
        margin: 40px 0 20px;
    }
}

/* 返回按钮样式 */
.back-button {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 10px 20px;
    background-color: #42b983;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    margin-bottom: 20px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
    font-size: 15px;
    font-weight: 500;
    color: white;
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

/* 修改标题部分样式 */
.title-section {
    display: flex;
    flex-direction: column;
    gap: 10px;
    /* 减小间距 */
}

.additive-name {
    margin-bottom: 0;
    /* 移除底部边距 */
    padding-bottom: 10px;
    /* 减小装饰线的距离 */
}

.nature-text {
    margin-top: 0;
    /* 移除顶部边距 */
    padding: 12px 16px;
    /* 减小内边距 */
    background: #f8f9fa;
    border-radius: 8px;
    font-size: 15px;
    color: #5c6a7d;
}

/* 调整内容布局 */
.intro-content {
    gap: 20px;
    /* 减小整体间距 */
}

.intro-text {
    margin-top: 10px;
    /* 添加一点顶部间距 */
}

/* 响应式调整 */
@media (max-width: 768px) {
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
</style>
