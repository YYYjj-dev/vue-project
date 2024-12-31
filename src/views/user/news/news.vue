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
        <div class="news-item" v-for="newsInfo in currentPageNews" :key="newsInfo.id"
          @click="goToNewsInfo(newsInfo.id)">
          <h2>{{ newsInfo.title }}</h2>
        </div>

        <!-- 分页器 -->
        <div class="pagination">
          <button class="page-btn" :disabled="currentPage === 1" @click="changePage(currentPage - 1)">
            上一页
          </button>

          <div class="page-numbers">
            <button v-for="page in totalPages" :key="page" :class="['page-number', { active: currentPage === page }]"
              @click="changePage(page)">
              {{ page }}
            </button>
          </div>

          <button class="page-btn" :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">
            下一页
          </button>
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

// 添加分页相关的响应式数据
const pageSize = ref(10) // 每页显示的数量
const currentPage = ref(1) // 当前页码

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(news.value.length / pageSize.value)
})

// 计算当前页显示的新闻
const currentPageNews = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return news.value.slice(start, end)
})

// 切换页码
const changePage = (page) => {
  currentPage.value = page
  // 回到顶部
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
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

/* 分页器样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 40px;
  padding: 20px 0;
}

.page-btn {
  padding: 8px 16px;
  border: none;
  background: #4CAF50;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s ease;
}

.page-btn:hover:not(:disabled) {
  background: #45a049;
  transform: translateY(-2px);
}

.page-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 8px;
}

.page-number {
  width: 36px;
  height: 36px;
  border: none;
  background: white;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
}

.page-number:hover {
  background: #f0f0f0;
}

.page-number.active {
  background: #4CAF50;
  color: white;
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

  .pagination {
    gap: 10px;
  }

  .page-btn {
    padding: 6px 12px;
    font-size: 13px;
  }

  .page-number {
    width: 32px;
    height: 32px;
    font-size: 13px;
  }
}

@media (max-width: 480px) {
  .news-item {
    padding: 16px;
  }

  .page-numbers {
    display: none;
  }

  .pagination {
    margin-top: 20px;
  }
}
</style>
