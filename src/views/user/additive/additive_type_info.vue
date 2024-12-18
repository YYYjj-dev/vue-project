<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 顶部图片和介绍部分 -->
            <div class="top-section">
                <div class="image-box">
                    <img :src="imageUrl" alt="添加剂图片">
                </div>
                <div class="intro-box">
                    <div class="intro-text">添加剂介绍</div>
                </div>
            </div>

            <!-- 摄入标准按钮 -->
            <div class="section-title">
                摄入标准
            </div>

            <!-- 国内外标准对比 -->
            <div class="standards-section">
                <div class="standard-box">
                    <div class="standard-text">国内</div>
                </div>
                <div class="standard-box">
                    <div class="standard-text">国际</div>
                </div>
            </div>

            <!-- 健康分析按钮 -->
            <div class="section-title">
                健康分析
            </div>

            <!-- 内容展示区域 -->
            <div class="content-box">
                <div class="content-text">内容</div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'

export default {
    components: {
        NavBar,
        Footer
    },
    props: ['category', 'imageId'],
    data() {
        return {
            imageUrl: '../img/default.jpg'
        }
    }
}
</script>

<style scoped>
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.content-wrapper {
    flex: 1;
    padding: 20px 40px;
    margin-bottom: 4rem;
}

/* 顶部图片和介绍部分 */
.top-section {
    display: flex;
    gap: 40px;
    margin-bottom: 30px;
}

.image-box {
    flex: 0 0 400px;
    height: 400px;
    background-color: #f0f0f0;
    border-radius: 12px;
    overflow: hidden;
}

.image-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.intro-box {
    flex: 1;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* 添加���的标题样式 */
.section-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
    margin: 40px 0 20px 0;
    padding-left: 15px;
    border-left: 4px solid #4a90e2;
    line-height: 1.4;
}

/* 国内外标准对比 */
.standards-section {
    display: flex;
    gap: 40px;
    margin-top: 20px;
}

.standard-box {
    flex: 1;
    height: 250px;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
}

/* 内容展示区域 */
.content-box {
    min-height: 300px;
    border-radius: 12px;
    padding: 30px;
    width: 100%;
    height: 200px;
    border: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .content-wrapper {
        padding: 20px;
    }
    
    .top-section {
        gap: 20px;
    }

    .image-box {
        flex: 0 0 350px;
        height: 350px;
    }
}

@media (max-width: 768px) {
    .top-section {
        flex-direction: column;
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
        height: 200px;
    }
}
</style>
