<template>
  <div class="page-container">
    <div class="nav-wrapper">
      <NavBar />
    </div>

    <div class="content-wrapper">
      <!-- 主轮播图 -->
      <div class="main-carousel">
        <Carousel />
      </div>

      <!-- 天然添加剂部分 -->
      <section class="section">
        <div class="section-content">
          <div class="section-header">
            <h2>天然添加剂</h2>
            <div class="underline"></div>
          </div>

          <!-- 添加剂滚动图组件 -->
          <div class="additives-carousel">
            <button class="carousel-btn prev" :disabled="currentIndex === 0" @click="prevAdditives">&lt;</button>
            <div class="carousel-container">
              <div class="carousel-track" :style="{ transform: `translateX(-${currentIndex * 33.33}%)` }">
                <div class="carousel-item" v-for="(item, index) in additivesList" :key="index"
                  @click="goToAdditiveInfo(item.id)">
                  <div class="item-card">
                    <div class="item-image">
                      <img :src="item.image" :alt="item.title">
                      <div class="item-overlay">
                        <span class="view-more">查看详情</span>
                      </div>
                    </div>
                    <div class="item-content">
                      <h3 class="item-title">{{ item.title }}</h3>
                      <p class="item-description">{{ item.name }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <button class="carousel-btn next" :disabled="currentIndex >= additivesList.length - 3"
              @click="nextAdditives">&gt;</button>
          </div>

          <div class="more-link">
            <router-link to="/additive_type" class="more-button">查看更多</router-link>
          </div>
        </div>
      </section>

      <!-- 资讯报道部分 -->
      <section class="section">
        <div class="section-content">
          <div class="section-header">
            <h2>资讯报道</h2>
            <div class="underline"></div>
          </div>

          <div class="news-grid">
            <div class="news-card" v-for="newsItem in news" :key="newsItem.id" @click="goToNewsInfo(newsItem.id)">
              <div class="news-content">
                <h3>{{ newsItem.title }}</h3>
                <div class="news-meta">
                  <span class="news-date">{{ newsItem.date }}</span>
                </div>
                <p class="news-description">{{ newsItem.content }}</p>
              </div>
            </div>
          </div>

          <div class="more-link">
            <router-link to="/news" class="more-button">查看更多</router-link>
          </div>
        </div>
      </section>

      <!-- 应用案例部分 -->
      <section class="section">
        <div class="section-content">
          <div class="section-header">
            <h2>应用案例</h2>
            <div class="underline"></div>
          </div>

          <div class="cases-grid">
            <div class="case-card" v-for="case_item in casesData" :key="case_item.id"
              @click="goToCaseInfo(case_item.id)">
              <div class="case-image">
                <img :src="case_item.image" :alt="case_item.title">
              </div>
              <div class="case-content">
                <h3>{{ case_item.title }}</h3>
                <p class="case-date">{{ case_item.date }}</p>
              </div>
            </div>
          </div>

          <div class="more-link">
            <router-link to="/cases" class="more-button">查看更多</router-link>
          </div>
        </div>
      </section>

      <!-- 快速入口部分 -->
      <section class="section quick-links">
        <div class="section-content">
          <div class="section-header">
            <h2>快速入口</h2>
            <div class="underline"></div>
          </div>

          <div class="quick-links-grid">
            <router-link to="/additive_type" class="quick-link-card">
              <i class="icon">🔍</i>
              <span>查添加剂</span>
            </router-link>
            <router-link to="/shop_rec" class="quick-link-card">
              <i class="icon">🛒</i>
              <span>购买商品</span>
            </router-link>
            <router-link to="/feedback" class="quick-link-card">
              <i class="icon">💬</i>
              <span>问题反馈</span>
            </router-link>
          </div>
        </div>
      </section>
    </div>

    <Footer />
  </div>
</template>

<script setup>
import NavBar from '../components/NavBar.vue'
import Carousel from '../components/Carousel.vue'
import Footer from '../components/Footer.vue'
import { useRouter } from 'vue-router'
import { onMounted, ref, onUnmounted } from 'vue'
import request from '../utils/request'

const router = useRouter()
const news = ref([])
const cases = ref([])
const additivesList = ref([])
const currentIndex = ref(0)
const baseUrl = 'http://localhost:8080/image/'
const casesData = ref([])
const sections = ref([])
const observer = ref(null)

// 添加剂滚动图向左滚动
const prevAdditives = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--
  }
}

// 添加剂滚动图向右滚动
const nextAdditives = () => {
  if (currentIndex.value < additivesList.value.length - 3) {
    currentIndex.value++
  }
}

// 获取资讯报道
async function getNews() {
  try {
    const { data } = await request.get('info/findAllNews')
    news.value = data.data.slice(0, 6).map(item => ({
      id: item.id,
      title: item.title,
      content: item.content,
      date: item.date,
      type: item.type,
      image: item.img ? baseUrl + item.img : null
    }))
  } catch (error) {
    console.error('获取资讯失败:', error)
  }
}

// 获取添加剂
async function getAdditives() {
  try {
    const { data } = await request.get('additive/findAdditiveByNature', {
      params: {
        'nature': '天然'
      }
    })
    additivesList.value = data.data.slice(0, 6)
    additivesList.value.forEach(item => {
      item.image = baseUrl + item.imgpath
    })
  } catch (error) {
    console.error('获取添加剂失败:', error)
  }
}

// 获取案例
async function getCases() {
  try {
    const { data } = await request.get('info/findAllCases')
    if (data.code === 200) {
      casesData.value = data.data.map(item => ({
        id: item.id,
        title: item.title,
        content: item.content,
        image: baseUrl + item.imgpath,
        groupType: item.drouptype,
        date: item.date
      }))
    }
  } catch (error) {
    console.error('获取案例失败:', error)
  }
}

// 初始化观察器
const initObserver = () => {
  observer.value = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        // 立即添加可见类，移除延迟
        entry.target.classList.add('visible')
        observer.value.unobserve(entry.target)
      }
    })
  }, {
    threshold: 0.2, // 略微提高阈值，使动画触发时机更准确
    rootMargin: '20px'
  })

  // 观察所有section元素
  const sectionElements = document.querySelectorAll('.section')
  sectionElements.forEach(section => {
    observer.value.observe(section)
  })
}

// 页面加载时获取数据
onMounted(async () => {
  // 获取数据
  await Promise.all([
    getNews(),
    getAdditives(),
    getCases()
  ])

  // 初始化并启动观察器
  initObserver()
})

// 添加 onUnmounted
onUnmounted(() => {
  if (observer.value) {
    observer.value.disconnect()
  }
})

// 跳转方法
const goToNewsInfo = (newsId) => {
  router.push({
    name: 'news_info',
    params: { id: newsId }
  })
}

const goToAdditiveInfo = (additiveId) => {
  router.push({
    name: 'additive_type_info',
    params: { id: additiveId }
  })
}

const goToCaseInfo = (caseId) => {
  router.push({
    name: 'cases_info',
    params: { id: caseId }
  })
}
</script>

<style scoped>
/* 页面基础布局 */
.page-container {
  min-height: 100vh;
  /* 确保最小高度占满视口 */
  padding-top: 60px;
  /* 为固定导航栏留出空间 */
  background-color: #f8f9fa;
}

/* 导航栏固定定位 */
.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  /* 确保导航栏始终在最上层 */
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 内容区域容器 */
.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 各个section通用样式 */
.section {
  margin: 60px 0;
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.4s cubic-bezier(0.16, 1, 0.3, 1);
  will-change: transform, opacity;
}

.section.visible {
  opacity: 1;
  transform: translateY(0);
}

/* 子元素动画 */
.section-content {
  opacity: 0;
  transform: translateY(20px);
  transition: all 0.4s cubic-bezier(0.16, 1, 0.3, 1);
  will-change: transform, opacity;
}

.section.visible .section-content {
  opacity: 1;
  transform: translateY(0);
}

/* 标题样式 */
.section-header {
  text-align: center;
  margin-bottom: 40px;
}

.section-header h2 {
  font-size: 28px;
  color: #2c3e50;
  margin-bottom: 15px;
}

/* 标题下方的装饰线 */
.underline {
  width: 60px;
  height: 3px;
  background: #42b983;
  margin: 0 auto;
  border-radius: 2px;
}

/* 资讯报道卡片网格布局 */
.news-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  /* 三列布局 */
  gap: 30px;
  margin: 40px 0;
}

/* 资讯卡片样式 */
.news-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  cursor: pointer;
  height: 280px;
  position: relative;
}

/* 卡片悬停效果 */
.news-card:hover {
  transform: translateY(-5px);
}

/* 资讯内容区域 */
.news-content {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
}

/* 底部渐变遮罩效果 */
.news-content::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 80px;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0.95));
  pointer-events: none;
}

.news-card:hover .news-content::after {
  opacity: 0.95;
}

.news-content::before {
  display: none;
}

.news-content h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
  font-weight: 600;
}

.news-meta {
  margin-bottom: 10px;
}

.news-date {
  color: #666;
  font-size: 14px;
}

.news-description {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-top: auto;
}

/* 应用案例样式 */
.cases-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  /* 两列布局 */
  gap: 30px;
  margin: 40px 0;
}

.case-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.case-card:hover {
  transform: translateY(-5px);
}

.case-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
}

.case-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.case-content {
  padding: 20px;
}

.case-content h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
}

.case-content p {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 15px;
}

.case-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.case-category {
  color: #42b983;
  background: rgba(66, 185, 131, 0.1);
  padding: 4px 12px;
  border-radius: 20px;
}

.case-date {
  color: #666;
}

/* 快速入口样式 */
.quick-links-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  margin: 40px 0;
}

.quick-link-card {
  background: white;
  border-radius: 12px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  text-decoration: none;
  color: #2c3e50;
}

.quick-link-card:hover {
  transform: translateY(-5px);
}

.quick-link-card .icon {
  font-size: 32px;
  margin-bottom: 15px;
  display: block;
}

.quick-link-card span {
  font-size: 16px;
  font-weight: 500;
}

/* 更多按钮样式 */
.more-link {
  text-align: center;
  margin-top: 40px;
}

.more-button {
  display: inline-block;
  padding: 12px 30px;
  background: #42b983;
  color: white;
  border-radius: 25px;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s ease;
}

.more-button:hover {
  background: #3aa876;
  transform: translateY(-2px);
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .news-grid {
    grid-template-columns: repeat(2, 1fr);
    /* 平板端两列布局 */
  }

  .card {
    flex: 0 0 calc((100% - 20px) / 2);
  }
}

@media (max-width: 768px) {
  .section-header h2 {
    font-size: 24px;
  }

  .news-grid,
  .cases-grid,
  .quick-links-grid {
    grid-template-columns: 1fr;
    /* 移动端单列布局 */
  }

  .card {
    flex: 0 0 100%;
  }

  .additives-carousel {
    padding: 20px 40px;
  }

  .arrow {
    width: 36px;
    height: 36px;
  }

  .arrow.left {
    left: 10px;
  }

  .arrow.right {
    right: 10px;
  }

  .news-grid {
    grid-template-columns: repeat(1, 1fr);
  }

  .news-card {
    height: 240px;
  }

  .news-content::after {
    height: 60px;
  }

  .carousel-item {
    flex: 0 0 100%;
    /* 移动端轮播一次显示一个 */
  }
}

/* 添加滚动图组件样式 */
.additives-carousel {
  position: relative;
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 60px;
}

.carousel-container {
  overflow: hidden;
  padding: 20px 0;
}

.carousel-track {
  display: flex;
  transition: transform 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

/* 轮播项目样式 */
.carousel-item {
  flex: 0 0 33.33%;
  padding: 0 12px;
  height: 320px;
  /* 调整卡片高度 */
}

/* 卡片样式优化 */
.item-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
}

/* 图片容器样式 */
.item-image {
  position: relative;
  height: 220px;
  /* 调整图片高度 */
  overflow: hidden;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

/* 内容区域样式 */
.item-content {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  text-align: center;
}

.item-title {
  font-size: 18px;
  color: #333;
  margin: 0;
  font-weight: 500;
}

/* 卡片悬停效果 */
.item-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.item-card:hover .item-image img {
  transform: scale(1.08);
}

/* 轮播按钮样式优化 */
.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  border: none;
  border-radius: 50%;
  background: #42b983;
  color: white;
  font-size: 18px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
  z-index: 1;
}

.carousel-btn:hover:not(:disabled) {
  background: #3aa876;
}

.carousel-btn:disabled {
  background: #e0e0e0;
  cursor: not-allowed;
  box-shadow: none;
  opacity: 0.6;
}

.carousel-btn.prev {
  left: 0;
}

.carousel-btn.next {
  right: 0;
}

/* 响应式调整 */
@media (max-width: 1024px) {
  .additives-carousel {
    max-width: 900px;
  }

  .carousel-item {
    flex: 0 0 50%;
  }
}

@media (max-width: 768px) {
  .additives-carousel {
    max-width: 100%;
    padding: 0 40px;
  }

  .carousel-item {
    flex: 0 0 100%;
  }
}

/* 遮罩层样式 */
.item-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  /* 半透明黑色遮罩 */
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

/* 查看详情文字样式 */
.view-more {
  color: white;
  font-size: 16px;
  padding: 8px 20px;
  border: 2px solid white;
  border-radius: 20px;
  transform: translateY(20px);
  transition: all 0.3s ease;
}

/* 悬停效果 */
.item-card:hover .item-overlay {
  opacity: 1;
}

.item-card:hover .view-more {
  transform: translateY(0);
}

.main-carousel {
  width: 90%;
  max-width: 1200px;
  margin: 40px auto;
  height: 500px;
  position: relative;
  overflow: hidden;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.main-carousel :deep(.carousel-container) {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: 0;
  padding: 0;
}

.main-carousel :deep(.carousel-wrapper) {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: 0;
  padding: 0;
}

.main-carousel :deep(.carousel-item) {
  position: relative;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

.main-carousel :deep(.carousel-image) {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  display: block;
  margin: 0;
  padding: 0;
}

.main-carousel :deep(.carousel-indicators) {
  bottom: 20px;
}

.main-carousel :deep(.carousel-button) {
  opacity: 0;
  transition: opacity 0.3s ease;
}

.main-carousel:hover :deep(.carousel-button) {
  opacity: 1;
}

@media (max-width: 768px) {
  .main-carousel {
    height: 300px;
  }

  .main-carousel :deep(.carousel-indicators) {
    bottom: 10px;
  }
}

@media (max-width: 480px) {
  .main-carousel {
    height: 200px;
  }
}
</style>