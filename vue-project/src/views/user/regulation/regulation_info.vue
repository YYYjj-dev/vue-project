<template>
    <NavBar />
    <div class="regulation-info">
        <!-- 标题区域 -->
        <div class="title">
            <h1>{{ law ? law.title : '未找到法律条文' }}</h1>
        </div>

        <!-- 主要内容区域 -->
        <div class="content-container" v-if="law">
            <div class="content-wrapper">
                <div class="content-section">
                    <div class="content-text">
                        {{ law.details }}
                    </div>
                </div>
            </div>
        </div>
        <div class="error-message" v-else>
            <p>未找到相关法律条文信息</p>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'

export default {
    name: 'regulation_info',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            law: null,
            laws: [
                { 
                    id: 1, 
                    title: '法律条文 1', 
                    details: '这是法律条文 1 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 2, 
                    title: '法律条文 2', 
                    details: '这是法律条文 2 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 3, 
                    title: '法律条文 3', 
                    details: '这是法律条文 3 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 4, 
                    title: '法律条文 4', 
                    details: '这是法律条文 4 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 5, 
                    title: '法律条文 5', 
                    details: '这是法律条文 5 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 6, 
                    title: '法律条文 6', 
                    details: '这是法律条文 6 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 7, 
                    title: '法律条文 7', 
                    details: '这是法律条文 7 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 8, 
                    title: '法律条文 8', 
                    details: '这是法律条文 8 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 9, 
                    title: '法律条文 9', 
                    details: '这是法律条文 9 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 10, 
                    title: '法律条文 10', 
                    details: '这是法律条文 10 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 11, 
                    title: '法律条文 11', 
                    details: '这是法律条文 11 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                },
                { 
                    id: 12, 
                    title: '法律条文 12', 
                    details: '这是法律条文 12 的详细内容。这里可以包含更多的细节信息，比如法律条文的具体应用场景、注意事项等。' 
                }
            ]
        }
    },
    created() {
        const lawId = parseInt(this.$route.params.id, 10);
        this.law = this.laws.find(law => law.id === lawId);
        if (!this.law) {
            this.router.push('/regulation');
        }
    },
    methods: {
        goBack() {
            this.router.push('/regulation');
        }
    }
}
</script>

<style scoped>
/* 基础布局 */
.regulation-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
    padding-bottom: 60px;
}

/* 标题样式 */
.title {
    text-align: center;
    margin: 40px 0;
    position: relative;
}

.title h1 {
    font-size: 28px;
    color: #333;
    margin: 0;
    padding-bottom: 12px;
    display: inline-block;
    position: relative;
}

.title h1::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 60px;
    height: 3px;
    background-color: #42b983;
    border-radius: 2px;
}

/* 内容容器样式 */
.content-container {
    width: 90%;
    max-width: 1200px;
    margin: 0 auto;
}

.content-wrapper {
    background: white;
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
    padding: 40px;
}

/* 内容部分样式 */
.content-section {
    padding: 20px;
}

.content-text {
    font-size: 16px;
    line-height: 1.8;
    color: #333;
    text-align: justify;
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

    .content-wrapper {
        padding: 20px;
    }

    .title h1 {
        font-size: 24px;
    }

    .content-section {
        padding: 15px;
    }

    .content-text {
        font-size: 15px;
    }
}
</style>