<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 角度按钮组 -->
            <div class="angle-buttons">
                <button 
                    v-for="(angle, index) in angles" 
                    :key="index"
                    :class="{ active: currentAngle === index }"
                    @click="switchAngle(index)">
                    {{ angle.name }}
                </button>
            </div>

            <!-- 内容区域 -->
            <div class="content-container">
                <div class="content-box" 
                     v-for="(content, index) in currentContents" 
                     :key="index"
                     @click="goToNewsInfo(content.id)">
                    {{ content.title }}
                </div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'
import Footer from '../../../components/Footer.vue'

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
            currentAngle: 0,
            angles: [
                { 
                    name: '角度1',
                    contents: [
                        { id: 1, title: '内容1-1', details: '详细内容1-1' },
                        { id: 2, title: '内容1-2', details: '详细内容1-2' },
                        { id: 3, title: '内容1-3', details: '详细内容1-3' }
                    ]
                },
                { 
                    name: '角度2',
                    contents: [
                        { id: 4, title: '内容2-1', details: '详细内容2-1' },
                        { id: 5, title: '内容2-2', details: '详细内容2-2' },
                        { id: 6, title: '内容2-3', details: '详细内容2-3' }
                    ]
                },
                { 
                    name: '角度3',
                    contents: [
                        { id: 7, title: '内容3-1', details: '详细内容3-1' },
                        { id: 8, title: '内容3-2', details: '详细内容3-2' },
                        { id: 9, title: '内容3-3', details: '详细内容3-3' }
                    ]
                }
            ]
        }
    },
    computed: {
        currentContents() {
            return this.angles[this.currentAngle].contents
        }
    },
    methods: {
        switchAngle(index) {
            this.currentAngle = index
        },
        goToNewsInfo(id) {
            this.router.push({
                name: 'news_info',
                params: { id: id.toString() }
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
    max-width: 1200px;
    margin: 0 auto 4rem;
}

/* 角度按钮组样式 */
.angle-buttons {
    display: flex;
    justify-content: center;
    gap: 40px;
    margin: 30px 0;
}

.angle-buttons button {
    padding: 8px 40px;
    font-size: 16px;
    border: 1px solid #ccc;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 4px;
}

.angle-buttons button.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
}

.angle-buttons button:hover:not(.active) {
    border-color: #4CAF50;
    color: #4CAF50;
}

/* 内容区域样式 */
.content-container {
    margin-top: 40px;
}

.content-box {
    width: 100%;
    padding: 20px;
    margin-bottom: 20px;
    background: white;
    border: 1px solid #ddd;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    text-align: left;
    font-size: 16px;
}

.content-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
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
    }
}
</style>