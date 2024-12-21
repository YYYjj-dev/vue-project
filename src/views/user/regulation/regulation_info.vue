<template>
    <div class="regulation-info">
        <NavBar />
        
        <div class="content-container">
            <!-- 标题 -->
            <div class="title-container" v-if="law">
                <h1 class="regulation-title">{{ law.title }}</h1>
                <div class="regulation-meta">
                    <span class="publish-date">{{ law.date }}</span>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="main-content" v-if="law">
                <div class="content-box">
                    <p>{{ law.content }}</p>
                </div>
            </div>
            
            <!-- 错误信息 -->
            <div class="error-message" v-if="!law">
                <p>未找到相关法律条文信息</p>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import request from '../../../utils/request'

export default {
    name: 'regulation_info',
    components: {
        NavBar
    },
    data() {
        return {
            law: null
        }
    },
    created() {
        const lawId = this.$route.params.id;
        console.log('法规ID:', lawId);
        this.loadRegulationInfo(lawId);
    },
    methods: {
        loadRegulationInfo(id) {
            console.log('开始请求法规详情，ID:', id);
            
            request.get("/regulation")
                .then(res => {
                    console.log('获取到的响应:', res);
                    
                    if(res.code === '0') {
                        const regulationItem = res.data.find(item => item.id === parseInt(id));
                        if (regulationItem) {
                            this.law = regulationItem;
                            console.log('找到的法规数据:', this.law);
                        } else {
                            console.error('未找到对应ID的法规');
                        }
                    } else {
                        console.error('获取法规详情失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求法规详情出错:', error);
                });
        }
    }
}
</script>

<style scoped>
.regulation-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 标题样式 */
.title-container {
    margin-bottom: 40px;
    text-align: center;
}

.regulation-title {
    font-size: 2.5rem;
    color: #2c3e50;
    margin-bottom: 16px;
    font-weight: 600;
}

.regulation-meta {
    color: #666;
    font-size: 0.9rem;
}

/* 主要内容区域 */
.main-content {
    margin-bottom: 60px;
}

.content-box {
    background: white;
    border-radius: 16px;
    padding: 40px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    line-height: 1.8;
    font-size: 1.1rem;
    color: #333;
}

/* 错误信息样式 */
.error-message {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 16px;
}

/* 响应式布局 */
@media (max-width: 768px) {
    .content-container {
        width: 95%;
    }

    .content-box {
        padding: 20px;
    }

    .regulation-title {
        font-size: 24px;
    }
}
</style>