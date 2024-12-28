<template>
    <div class="page-container">
        <NavBar />

        <div class="content-wrapper">
            <div class="page-header">
                <div class="title-container">
                    <h1>如何避免添加剂的危害</h1>
                    <div class="title-underline"></div>

                </div>
            </div>

            <!-- 角度按钮组 -->
            <div class="angle-buttons">
                <button v-for="(angle, index) in angles" :key="index" :class="{ active: currentAngle === angle.type }"
                    @click="switchAngle(angle.type)">
                    {{ angle.name }}
                </button>
            </div>

            <!-- 内容区域 -->
            <div class="content-container" :class="{ 'loading-state': loading }">
                <div v-if="loading" class="loading">
                    <div class="loading-spinner"></div>
                    <p>加载中...</p>
                </div>
                <div v-else-if="!currentContents.length" class="no-content">
                    <i class="empty-icon">📭</i>
                    <p>暂无相关内容</p>
                </div>
                <div class="content-box" v-for="content in currentContents" :key="content.id"
                    @click="goToNewsInfo(content.id)">
                    <h3 class="content-title">{{ content.title }}</h3>
                    <div class="content-meta">
                        <span class="content-date">{{ content.date }}</span>
                    </div>
                </div>
            </div>
        </div>

        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

export default {
    name: 'additive_zhuanqu_info1',
    components: {
        NavBar,
        Footer
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            currentAngle: '食品安全',
            loading: false,
            angles: [
                {
                    name: '食品安全',
                    type: '食品安全',
                    default: true
                },
                {
                    name: '健康饮食',
                    type: '健康饮食'
                },
                {
                    name: '科学认知',
                    type: '科学认知'
                }
            ],
            currentContents: []
        }
    },
    methods: {
        async switchAngle(type) {
            this.currentAngle = type
            await this.fetchNewsByType(type)
        },
        goToNewsInfo(id) {
            this.router.push({
                name: 'news_info',
                params: { id: id.toString() }
            })
        },
        async fetchNewsByType(type) {
            this.loading = true
            try {
                const { data } = await request.get('/info/findNewsByType', {
                    params: { type }
                })
                if (data.code === 200) {
                    this.currentContents = data.data || []
                } else {
                    console.warn('获取新闻失败:', data.msg)
                }
            } catch (error) {
                console.error('获取新闻出错:', error)
            } finally {
                this.loading = false
            }
        },
        initDefaultContent() {
            const defaultAngle = this.angles.find(angle => angle.default)
            if (defaultAngle) {
                this.currentAngle = defaultAngle.type
                this.fetchNewsByType(defaultAngle.type)
            }
        }
    },
    mounted() {
        this.initDefaultContent()
    }
}
</script>

<style scoped>
/* 基础布局 */
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f9fa;
}

/* 内容区域容器 */
.content-wrapper {
    flex: 1;
    padding:20px;
    max-width: 1200px;
    margin: 0 auto;
    width: 100%;
}

/* 页面标题 */
.page-header {
    text-align: center;
    margin: 60px auto 50px;
    position: relative;
    max-width: 800px;
    padding: 0 20px;
    width: 100%;
}

.title-container {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.page-header h1 {
    font-size: 36px;
    color: #2c3e50;
    margin-bottom: 16px;
    font-weight: 600;
    letter-spacing: 1px;
    position: relative;
    text-align: center;
}

.title-underline {
    width: 60px;
    height: 3px;
    background: linear-gradient(90deg, #42b983 0%, #3aa876 100%);
    margin: 0 auto;
    border-radius: 2px;
}

.subtitle {
    color: #666;
    font-size: 16px;
    margin-top: 10px;
    font-weight: 400;
    line-height: 1.6;
}

/* 角度按钮组样式 */
.angle-buttons {
    display: flex;
    justify-content: center;
    gap: 40px;
    margin: 40px 0;
}

.angle-buttons button {
    padding: 12px 40px;
    font-size: 16px;
    border: 1px solid #e0e0e0;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 25px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    position: relative;
    overflow: hidden;
}

.angle-buttons button.active {
    background: #42b983;
    color: white;
    border-color: #42b983;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
    font-weight: 500;
}

.angle-buttons button:hover:not(.active) {
    border-color: #42b983;
    color: #42b983;
    transform: translateY(-2px);
    background-color: rgba(66, 185, 131, 0.05);
}

/* 内容区域样式 */
.content-container {
    margin: 40px auto;
    max-width: 900px;
    padding: 0 20px;
    min-height: 400px;
    position: relative;
}

.content-box {
    width: 100%;
    padding: 24px;
    margin-bottom: 20px;
    background: white;
    border: 1px solid #eee;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    text-align: left;
    box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06);
    position: relative;
    overflow: hidden;
}

.content-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
    border-color: #42b983;
    background-color: #fafffe;
}

.content-title {
    font-size: 18px;
    color: #2c3e50;
    margin-bottom: 12px;
    font-weight: 600;
    line-height: 1.4;
}

.content-meta {
    display: flex;
    align-items: center;
    gap: 16px;
}

.content-type {
    color: #42b983;
    font-size: 14px;
    font-weight: 500;
}

.content-date {
    font-size: 14px;
    color: #666;
}

.loading,
.no-content {
    text-align: center;
    padding: 40px;
    color: #666;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100%;
}

.loading-spinner {
    width: 40px;
    height: 40px;
    border: 3px solid #f3f3f3;
    border-top: 3px solid #42b983;
    border-radius: 50%;
    margin: 0 auto 16px;
    animation: spin 1s linear infinite;
}

.empty-icon {
    font-size: 48px;
    display: block;
    margin-bottom: 16px;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

/* 响应式调整 */
@media (max-width: 768px) {
    .angle-buttons {
        flex-direction: column;
        align-items: center;
        gap: 10px;
    }

    .angle-buttons button {
        width: 80%;
        max-width: 300px;
    }

    .page-header {
        margin: 40px auto 30px;
    }

    .page-header h1 {
        font-size: 28px;
        margin-bottom: 12px;
    }

    .title-underline {
        width: 50px;
        margin-bottom: 15px;
    }

    .subtitle {
        font-size: 14px;
    }

    .content-container {
        padding: 0 15px;
        margin: 30px auto;
    }

    .content-box {
        padding: 20px;
    }

    .content-title {
        font-size: 16px;
    }

    .content-meta {
        flex-direction: column;
        align-items: flex-start;
        gap: 8px;
    }
}

@media (max-width: 480px) {
    .page-header h1 {
        font-size: 24px;
    }

    .page-header {
        margin: 30px auto 25px;
    }

    .content-container {
        margin: 20px auto;
    }

    .content-box {
        padding: 16px;
        margin-bottom: 15px;
    }
}
</style>