<template>
  <div class="news">
    <!-- 导航栏 -->
    <div class="nav-wrapper">
      <NavBar />
    </div>

    <div class="news_content">
      <!-- 标题 -->
      <div class="page-title">
        <h1>热门资讯</h1>
      </div>

      <!-- 资讯报道内容 -->
      <div class="news-content">
        <div class="news-item" v-for="newsInfo, index in news" :key="index" @click="goToNewsInfo(newsInfo.id)">
          <h2>{{ newsInfo.title }}</h2>
        </div>
      </div>
    </div>
  </div>

  <!-- 页脚 -->
  <Footer />
</template>

<script setup name="news" components="NavBar">
import NavBar from '../../../components/NavBar.vue'
import { computed, onMounted, onBeforeMount, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import request from '../../../utils/request'
import Footer from '../../../components/Footer.vue'

const router = useRouter()
let news = ref([])

function goToNewsInfo(id) {
  router.push({ path: '/news_info/' + id });  // 跳转到 'news_info' 页面，并传递 'id'
}

onMounted(async () => {
  showNews()
})

async function showNews() {
  let { data } = await request.get('info/news/findAllNews')
  console.log(data)
  news.value = data.data
}

</script>

<style scoped>
.news {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8f9fa;
  padding-top: 60px;
}

/* 添加导航栏包装器样式 */
.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: #fff;
  /* 确保导航栏背景不透明 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  /* 添加阴影效果 */
}

.news_content {
  flex: 1;
  display: flex;
  flex-direction: column;
  width: 80%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px 0;
}

.page-title {
  margin-bottom: 40px;
}

.page-title h1 {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  position: relative;
  padding-left: 15px;
}

.page-title h1::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 24px;
  background-color: #4CAF50;
  border-radius: 2px;
}

.news-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.news-item {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s ease;
}

.news-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.news-item h2 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 12px;
}

.news-item p {
  color: #666;
  line-height: 1.6;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .news {
    padding-top: 50px;
  }

  .news_content {
    width: 95%;
    padding: 15px 0;
  }

  .news-item {
    padding: 20px;
  }
}

@media (max-width: 480px) {
  .news-item {
    padding: 16px;
  }
}
</style>
