<template>
    <NavBar />
    <div class="regulation-info">
        <!-- 标题区域 -->
        <div class="title" >
            <h1>{{law.data.title}}</h1>
        </div>

        <!-- 主要内容区域 -->
        <div class="content-container" v-if="law">
            <div class="content-wrapper">
                <div class="content-section">
                    <div class="content-text">
                       {{ law.data.content }}
                    </div>
                </div>
            </div>
        </div>
        <div class="error-message" v-else>
            <p>未找到相关法律条文信息</p>
        </div>
    </div>
</template>

<script setup name='regulation_info' comments="NavBar">
import NavBar from '../../../components/NavBar.vue'
import { useRouter,useRoute } from 'vue-router'
import request from '../../../utils/request'
import {ref,reactive,onMounted } from 'vue';
    
    const router = useRouter()
    const route = useRoute()
    let law=ref({title:'',content:'',data:''})
    let lid=route.params.id

    onMounted(()=>{
            showLaws(lid)
        })
    

    async function showLaws(id){
    let {data} = await request.get(`info/news/findRegularById?id=${id}`)
    law.value=data
    }
    function goBack() {
        this.router.push('/regulation');
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