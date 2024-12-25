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
            currentAngle: 0,
            angles: [
                { 
                    name: '食品安全',
                    contents: []
                },
                { 
                    name: '健康饮食',
                    contents: []
                },
                { 
                    name: '科学认知',
                    contents: []
                }
            ]
        }
    },
    created() {
        // 初始加载食品安全内容
        this.loadSafetyContents();
    },
    methods: {
        // 加载食品安全内容
        loadSafetyContents() {
            console.log('开始请求食品安全��据');
            request.get('/new/getSafety')
                .then(res => {
                    if(res.code === '0') {
                        this.angles[0].contents = res.data.slice(0, 6).map(item => ({
                            id: item.id,
                            title: item.title,
                            details: item.content
                        }));
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        },
        
        // 加载健康饮食内容
        loadHealthContents() {
            console.log('开始请求健康饮食数据');
            request.get('/new/getHealth')
                .then(res => {
                    if(res.code === '0') {
                        this.angles[1].contents = res.data.slice(0, 6).map(item => ({
                            id: item.id,
                            title: item.title,
                            details: item.content
                        }));
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        },
        
        // 加载科学认知内容
        loadScienceContents() {
            console.log('开始请求科学认知数��');
            request.get('/new/getScience')
                .then(res => {
                    if(res.code === '0') {
                        this.angles[2].contents = res.data.slice(0, 6).map(item => ({
                            id: item.id,
                            title: item.title,
                            details: item.content
                        }));
                    }
                })
                .catch(error => {
                    console.error('请求出错:', error);
                });
        },
        
        switchAngle(index) {
            this.currentAngle = index;
            // 根据切换的角度加载对应数据
            switch(index) {
                case 0:
                    if (this.angles[0].contents.length === 0) {
                        this.loadSafetyContents();
                    }
                    break;
                case 1:
                    if (this.angles[1].contents.length === 0) {
                        this.loadHealthContents();
                    }
                    break;
                case 2:
                    if (this.angles[2].contents.length === 0) {
                        this.loadScienceContents();
                    }
                    break;
            }
        },
        
        goToNewsInfo(id) {
            this.router.push({
                name: 'news_info',
                params: { id: id.toString() }
            });
        }
    },
    computed: {
        currentContents() {
            return this.angles[this.currentAngle].contents;
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

/* 修改内容区域样式 */
.content-container {
    margin-top: 30px;
    display: grid;
    grid-template-columns: repeat(2, 1fr);  /* 两列布局 */
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
    height: 280px;  /* 固定高度 */
    display: flex;
    flex-direction: column;
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
    line-height: 1.4;
}

.content-preview {
    color: #666;
    font-size: 14px;
    line-height: 1.6;
    flex-grow: 1;
    overflow: hidden;
    position: relative;
    margin-bottom: 15px;
}

/* 添加渐变遮罩效果 */
.content-preview::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 40px;
    background: linear-gradient(
        to bottom,
        rgba(255, 255, 255, 0),
        rgba(255, 255, 255, 1)
    );
}

.content-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: auto;  /* 确保footer始终在底部 */
}

.read-more {
    color: #42b983;
    font-size: 14px;
    font-weight: 500;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .content-container {
        grid-template-columns: 1fr;  /* 单列布局 */
    }

    .content-box {
        height: 250px;  /* 移动端稍微降低高度 */
    }
}
</style>