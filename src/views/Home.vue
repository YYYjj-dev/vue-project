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
        <div class="section-header">
          <h2>天然添加剂</h2>
          <div class="underline"></div>
        </div>

        <!-- 添加滚动图组件 -->
        <div class="additives-carousel">
          <button class="carousel-btn prev" :disabled="currentIndex === 0" @click="prevAdditives">&lt;</button>
          <div class="carousel-container">
            <div class="carousel-track" :style="{ transform: `translateX(-${currentIndex * 33.33}%)` }">
              <div class="carousel-item" 
                   v-for="(item, index) in additivesList" 
                   :key="index"
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
                  </div>
                </div>
              </div>
            </div>
          </div>
          <button class="carousel-btn next" 
                  :disabled="currentIndex >= additivesList.length - 3" 
                  @click="nextAdditives">&gt;</button>
        </div>

        <div class="more-link">
          <router-link to="/additive_type" class="more-button">查看更多</router-link>
        </div>
      </section>

      <!-- 资讯报道部分 -->
      <section class="section">
        <div class="section-header">
          <h2>资讯报道</h2>
          <div class="underline"></div>
        </div>

        <div class="news-grid">
          <div class="news-card" v-for="news in newsData" :key="news.id" @click="goToNewsInfo(news.id)">
            <div class="news-content">
              <h3>{{ news.title }}</h3>
              <div class="news-meta">
                <span class="news-date">{{ news.date }}</span>
              </div>
              <p class="news-description">{{ news.description }}</p>
            </div>
          </div>
        </div>

        <div class="more-link">
          <router-link to="/news" class="more-button">查看更多</router-link>
        </div>
      </section>

      <!-- 应用案例部分 -->
      <section class="section">
        <div class="section-header">
          <h2>应用案例</h2>
          <div class="underline"></div>
        </div>

        <div class="cases-grid">
          <div class="case-card" v-for="case_item in casesData" :key="case_item.id" @click="goToCaseInfo(case_item.id)">
            <div class="case-image">
              <img :src="case_item.image" :alt="case_item.title">
            </div>
            <div class="case-content">
              <h3>{{ case_item.title }}</h3>
              <p>{{ case_item.description }}</p>
              <div class="case-meta">
                <span class="case-date">{{ case_item.date }}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="more-link">
          <router-link to="/cases" class="more-button">查看更多</router-link>
        </div>
      </section>

      <!-- 快速入口部分 -->
      <section class="section quick-links">
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
      </section>
    </div>

    <Footer />
  </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'
import Carousel from '../components/Carousel.vue'
import Footer from '../components/Footer.vue'
import request from '../utils/request'

export default {
  name: 'Home',
  components: {
    NavBar,
    Carousel,
    Footer
  },
  data() {
    return {
      // 资讯报道数据
      newsData: [
        { 
          id: 1, 
          title: '资讯标题1', 
          description: '这是资讯1的简要描述...', 
          date: '2024-01-01',
          details: '这是资讯1的详细内容...'
        },
        { 
          id: 2, 
          title: '资讯标题2', 
          description: '这是资讯2的简要描述...', 
          date: '2024-01-02',
          details: '这是资讯2的详细内容...'
        },
        { 
          id: 3, 
          title: '资讯标题3', 
          description: '这是资讯3的简要描述...', 
          date: '2024-01-03',
          details: '这是资讯3的详细内容...'
        }
      ],
      // 应用案例数据
      casesData: [
        {
          id: 1,
          title: '天然防腐剂在果酱中的应用',
          description: '探索如何使用天然防腐剂延长果酱保质期，同时保持口感和营养价值...',
          image: '../img/carrot.jpg',
          category: '防腐技术',
          date: '2024-01-15',
          details: '这是案例1的详细内容...'
        },
        {
          id: 2,
          title: '植物色素��饮料中的创新应用',
          description: '研究天然色素在功能性饮料中的应用，实现健康与美味的完美结合...',
          image: '../img/carrot.jpg',
          category: '色素应用',
          date: '2024-01-12',
          details: '这是案例2的详细内容...'
        },
        {
          id: 3,
          title: '案例标题3',
          description: '这是案例3的简要描述...',
          image: '../img/carrot.jpg',
          details: '这是案例3的详细内容...'
        },
        {
          id: 4,
          title: '案例标题4',
          description: '这是案例4的简要描述...',
          image: '../img/carrot.jpg',
          details: '这是案例4的详细内容...'
        }
      ],
      currentIndex: 0,
      additivesList: []
    }
  },
  created() {
    this.loadAdditives();
  },
  methods: {
    loadAdditives() {
      console.log('开始请求添加剂数据');
      
      request.get('/additive/getAdditives')
        .then(res => {
          console.log('获取到的响应:', res);
          
          if(res.code === '0') {
            this.additivesList = res.data.map(item => ({
              id: item.id,
              title: item.name,
              image: item.imgpath,
              category: item.category
            }));
            console.log('处理后的添加剂数据:', this.additivesList);
          } else {
            console.error('请求失败:', res.msg);
          }
        })
        .catch(error => {
          console.error('请求出错:', error);
        });
    },
    // 转到资讯详情页
    goToNewsInfo(newsId) {
      this.$router.push({
        name: 'news_info',
        params: { id: newsId }
      })
    },
    // 跳转到案例详情页
    goToCaseInfo(caseId) {
      this.$router.push({
        name: 'cases_info',
        params: { id: caseId }
      })
    },
    // 跳转到添加剂详情页
    goToAdditiveInfo(additiveId) {
      this.$router.push({
        name: 'additive_type_info',
        params: { id: additiveId}
      })  
    },
    prevAdditives() {
      if (this.currentIndex > 0) {
        this.currentIndex--
      }
    },
    nextAdditives() {
      if (this.currentIndex < this.additivesList.length - 3) {
        this.currentIndex++
      }
    },
    
  }
}
</script> 
<style scoped>
.page-container {
  min-height: 100vh;
  padding-top: 60px;
  background-color: #f8f9fa;
}

.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

/* 通用部分样式 */
.section {
  margin: 60px 0;
}

.section-header {
  text-align: center;
  margin-bottom: 40px;
}

.section-header h2 {
  font-size: 28px;
  color: #2c3e50;
  margin-bottom: 15px;
}

.underline {
  width: 60px;
  height: 3px;
  background: #42b983;
  margin: 0 auto;
  border-radius: 2px;
}

/* 资讯报道样式 */
.news-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  margin: 40px 0;
}

.news-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
  cursor: pointer;
}

.news-card:hover {
  transform: translateY(-5px);
}

.news-content {
  padding: 20px;
}

.news-content h3 {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
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
}

/* 应用案例样式 */
.cases-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
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
  justify-content: flex-end;
  align-items: center;
  font-size: 14px;
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
}

.additives-carousel {
  position: relative;
  max-width: 1200px;
  margin: 40px auto;
  padding: 0 40px;
}

.carousel-container {
  overflow: hidden;
  padding: 20px 0;
}

.carousel-track {
  display: flex;
  transition: transform 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.carousel-item {
  flex: 0 0 33.33%;
  padding: 0 15px;
}

.item-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
  height: 100%;
}

.item-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.item-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.item-card:hover .item-image img {
  transform: scale(1.1);
}

.item-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(66, 185, 131, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.item-card:hover .item-overlay {
  opacity: 1;
}

.view-more {
  color: white;
  font-size: 16px;
  font-weight: 500;
  padding: 8px 20px;
  border: 2px solid white;
  border-radius: 20px;
}

.item-content {
  padding: 20px;
}

.item-title {
  font-size: 18px;
  color: #2c3e50;
  margin-bottom: 10px;
  font-weight: 600;
}

.item-description {
  font-size: 14px;
  color: #666;
  line-height: 1.5;
}

.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 44px;
  height: 44px;
  border: none;
  border-radius: 50%;
  background: #42b983;
  color: white;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  box-shadow: 0 2px 10px rgba(66, 185, 131, 0.3);
  z-index: 1;
}

.carousel-btn:hover:not(:disabled) {
  background: #3aa876;
  transform: translateY(-50%) scale(1.1);
}

.carousel-btn:disabled {
  background: #e0e0e0;
  cursor: not-allowed;
  box-shadow: none;
}

.carousel-btn.prev {
  left: 10px;
}

.carousel-btn.next {
  right: 10px;
}

@media (max-width: 768px) {
  .carousel-item {
    flex: 0 0 100%;
  }
  
  .item-card {
    max-width: 400px;
    margin: 0 auto;
  }
}

@media (min-width: 769px) and (max-width: 1024px) {
  .carousel-item {
    flex: 0 0 50%;
  }
}
</style>