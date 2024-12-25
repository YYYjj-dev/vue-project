<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />
        
        <!-- 返回按钮 -->
        <div class="back-button-container">
            <button class="back-button" @click="goBack">
                <span class="back-icon">←</span> 返回
            </button>
        </div>
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 加载状态 -->
            <div v-if="loading" class="loading-state">
                正在加载...
            </div>
            
            <div v-else>
                <!-- 顶部图片和介绍部分 -->
                <div class="top-section">
                    <div class="image-box">
                        <img :src="additiveData.imgpath" :alt="additiveData.name">
                    </div>
                    <div class="intro-box">
                        <div class="intro-content">
                            <h2 class="additive-name">{{ additiveData.name }}</h2>
                            <div class="nature-tag" :class="{ 'natural': additiveData.nature === 'yes' }">
                                {{ additiveData.nature }}
                            </div>
                            <p class="description">{{ additiveData.description || '暂无介绍' }}</p>
                        </div>
                    </div>
                </div>

                <!-- 摄入标准部分 -->
                <div class="section-title">摄入标准</div>
                <div class="standards-section">
                    <div class="standard-box">
                        <h3>国内标准</h3>
                        <p>{{ additiveData.usestandardInternal || '暂无数据' }}</p>
                    </div>
                    <div class="standard-box">
                        <h3>国际标准</h3>
                        <p>{{ additiveData.usestandardInternational || '暂无数据' }}</p>
                    </div>
                </div>

                <!-- 健康分析部分 -->
                <div class="section-title">健康分析</div>
                <div class="content-box">
                    <p>{{ additiveData.analysis || '暂无分析数据' }}</p>
                </div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

export default {
    components: {
        NavBar,
        Footer
    },
    data() {
        return {
            additiveData: {
                id: null,
                name: '',
                imgpath: '',
                description: '',
                usestandardInternal: '',
                usestandardInternational: '',
                analysis: '',
                nature: ''
            },
            loading: false
        }
    },
    created() {
        // 从路由参数获取id
        const id = this.$route.params.id;
        if (id) {
            this.loadAdditiveInfo(id);
        } else {
            console.error('未找到id参数');
        }
    },
    methods: {
        loadAdditiveInfo(id) {
            this.loading = true;
            request.get('/additive/getById', {
                params: { id: id }
            })
            .then(res => {
                console.log('获取到的添加剂信息:', res);
                if (res.code === '0' && res.data) {
                    const data = res.data;
                    this.additiveData = {
                        id: data.id,
                        name: data.name,
                        imgpath: data.imgpath,
                        description: data.description,
                        usestandardInternal: data.usestandardInternal,
                        usestandardInternational: data.usestandardInternational,
                        analysis: data.analysis,
                        nature: data.nature,
                        typename: data.typename
                    };
                    localStorage.setItem('lastCategory', data.typename);
                }
            })
            .catch(error => {
                console.error('获取添加剂信息失败:', error);
            })
            .finally(() => {
                this.loading = false;
            });
        },
        goBack() {
            this.$router.go(-1); // 返回上一页
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
    padding: 40px;
    margin: 0 auto;
    max-width: 1200px;
    margin-bottom: 4rem;
}

.loading-state {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 16px;
}

/* 顶部部分样式优化 */
.top-section {
    display: flex;
    gap: 40px;
    margin-bottom: 40px;
    background: white;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.image-box {
    flex: 0 0 300px;
    height: 300px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.image-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.intro-box {
    flex: 1;
    padding: 20px 40px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}

.intro-content {
    width: 100%;
    max-width: 800px;
}

.additive-name {
    font-size: 32px;
    color: #2c3e50;
    margin-bottom: 20px;
    font-weight: 600;
}

.nature-tag {
    display: inline-block;
    padding: 6px 16px;
    border-radius: 20px;
    font-size: 14px;
    color: white;
    background-color: #ff4444;
    margin-bottom: 25px;
    box-shadow: 0 2px 8px rgba(255, 68, 68, 0.2);
}

.nature-tag.natural {
    background-color: #42b983;
    box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
}

.description {
    font-size: 16px;
    line-height: 1.8;
    color: #666;
    margin-bottom: 20px;
    max-width: 100%;
}

/* 标题样式优化 */
.section-title {
    font-size: 24px;
    font-weight: 600;
    color: #2c3e50;
    margin: 40px 0 25px 0;
    padding-left: 15px;
    border-left: 4px solid #42b983;
    line-height: 1.4;
}

/* 标准部分样式优化 */
.standards-section {
    display: flex;
    gap: 30px;
    margin: 25px 0;
}

.standard-box {
    flex: 1;
    background: white;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.standard-box h3 {
    font-size: 20px;
    color: #2c3e50;
    margin-bottom: 20px;
    font-weight: 600;
}

.standard-box p {
    font-size: 16px;
    color: #666;
    line-height: 1.8;
}

/* 内容展示区域优化 */
.content-box {
    background: white;
    border-radius: 16px;
    padding: 30px;
    margin-top: 25px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.content-box p {
    font-size: 16px;
    line-height: 1.8;
    color: #666;
}

/* 响应式优化 */
@media (max-width: 1200px) {
    .content-wrapper {
        padding: 20px;
    }
    
    .top-section {
        gap: 30px;
        padding: 20px;
    }

    .image-box {
        flex: 0 0 250px;
        height: 250px;
    }

    .intro-box {
        padding: 20px;
    }
}

@media (max-width: 768px) {
    .top-section {
        flex-direction: column;
    }

    .image-box {
        width: 200px;
        height: 200px;
        margin: 0 auto;
    }

    .intro-box {
        padding: 20px 0;
    }

    .standards-section {
        flex-direction: column;
        gap: 20px;
    }

    .additive-name {
        font-size: 28px;
    }
}

/* 返回按钮容器 */
.back-button-container {
    padding: 20px 40px;
    max-width: 1200px;
    margin: 0 auto;
    width: 100%;
}

/* 返回按钮样式 */
.back-button {
    display: flex;
    align-items: center;
    padding: 8px 16px;
    background-color: #42b983;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 16px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
}

.back-button:hover {
    background-color: #3aa876;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.3);
}

.back-icon {
    margin-right: 8px;
    font-size: 18px;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .back-button-container {
        padding: 20px;
    }
}

@media (max-width: 768px) {
    .back-button {
        font-size: 14px;
        padding: 6px 12px;
    }
}
</style>
