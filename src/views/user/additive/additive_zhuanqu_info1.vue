<template>
    <div class="page-container">
        <NavBar />
        
        <div class="content-wrapper">
            <!-- 页面标题 -->
            <div class="page-header">
                <h1>如何减少添加剂的危害</h1>
                <div class="header-underline"></div>
            </div>

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
                    <h3 class="content-title">{{ content.title }}</h3>
                    <p class="content-preview">{{ content.details }}</p>
                    <div class="content-footer">
                        <span class="read-more">查看详情 ></span>
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
                    name: '食品安全',
                    contents: [
                        { 
                            id: 1, 
                            title: '食品添加剂的安全使用标准', 
                            details: '了解食品添加剂的使用限量和安全标准，避免过量使用带来的健康风险。' 
                        },
                        { 
                            id: 2, 
                            title: '常见食品添加剂的危害识别', 
                            details: '识别常见食品添加剂可能带来的潜在危害，提高食品安全意识。' 
                        },
                        { 
                            id: 3, 
                            title: '儿童食品中的添加剂控制', 
                            details: '特别关注儿童食品中的添加剂使用，保护儿童健康成长。' 
                        },
                        { 
                            id: 4, 
                            title: '食品添加剂的替代方案', 
                            details: '探索使用天然原料替代化学添加剂的可能性，追求更健康的饮食方式。' 
                        }
                    ]
                },
                { 
                    name: '健康饮食',
                    contents: [
                        { 
                            id: 5, 
                            title: '选择天然食材的重要性', 
                            details: '了解选择天然、新鲜食材对健康的积极影响，减少添加剂的摄入。' 
                        },
                        { 
                            id: 6, 
                            title: '健康烹饪方式推荐', 
                            details: '掌握健康的烹饪方法，在保持食物美味的同时减少添加剂的使用。' 
                        },
                        { 
                            id: 7, 
                            title: '营养均衡搭配指南', 
                            details: '学习科学的膳食搭配方法，确保营养均衡的同时避免过度加工食品。' 
                        },
                        { 
                            id: 8, 
                            title: '日常饮食习惯改善', 
                            details: '培养健康的饮食习惯，从源头减少不必要的食品添加剂摄入。' 
                        }
                    ]
                },
                { 
                    name: '科学认知',
                    contents: [
                        { 
                            id: 9, 
                            title: '食品添加剂的科学认识', 
                            details: '正确理解食品添加剂的作用和必要性，避免过度恐慌。' 
                        },
                        { 
                            id: 10, 
                            title: '添加剂安全评估方法', 
                            details: '了解食品添加剂的安全性评估标准和流程，建立科学的认知。' 
                        },
                        { 
                            id: 11, 
                            title: '食品标签的正确解读', 
                            details: '学会阅读食品标签上的添加剂信息，做出明智的购买选择。' 
                        },
                        { 
                            id: 12, 
                            title: '添加剂相关法规解析', 
                            details: '了解食品添加剂的相关法律法规，保护消费者权益。' 
                        }
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
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f7f2;
}

.content-wrapper {
    flex: 1;
    max-width: 1000px;
    margin: 0 auto;
    padding: 40px 20px 60px;
    width: 90%;
}

/* 页面标题样式 */
.page-header {
    text-align: center;
    margin-bottom: 40px;
}

.page-header h1 {
    font-size: 28px;
    color: #2c3e50;
    margin-bottom: 15px;
    font-weight: 600;
}

.header-underline {
    width: 60px;
    height: 3px;
    background: #42b983;
    margin: 0 auto;
    border-radius: 2px;
}

/* 角度按钮组样式 */
.angle-buttons {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin: 40px 0;
}

.angle-buttons button {
    padding: 12px 30px;
    font-size: 16px;
    border: none;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 25px;
    color: #666;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.angle-buttons button.active {
    background: #42b983;
    color: white;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.angle-buttons button:hover:not(.active) {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* 内容区域样式 */
.content-container {
    margin-top: 30px;
    display: grid;
    gap: 30px;
    padding: 10px;
}

.content-box {
    background: white;
    border-radius: 12px;
    padding: 30px;
    cursor: pointer;
    transition: all 0.3s ease;
    border: 1px solid #eee;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.content-box:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    border-color: #42b983;
}

.content-title {
    font-size: 18px;
    color: #2c3e50;
    margin-bottom: 12px;
    font-weight: 500;
}

.content-preview {
    color: #666;
    font-size: 14px;
    line-height: 1.6;
    margin-bottom: 15px;
}

.content-footer {
    display: flex;
    justify-content: flex-end;
}

.read-more {
    color: #42b983;
    font-size: 14px;
    font-weight: 500;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .content-wrapper {
        padding: 20px 15px 40px;
    }

    .page-header h1 {
        font-size: 24px;
    }

    .angle-buttons {
        flex-wrap: wrap;
        gap: 12px;
    }

    .angle-buttons button {
        padding: 10px 20px;
        font-size: 14px;
        flex: 1;
        min-width: 120px;
    }

    .content-container {
        gap: 25px;
        padding: 5px;
    }

    .content-box {
        padding: 25px;
    }
}

@media (min-width: 768px) {
    .content-container {
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    }
}
</style>