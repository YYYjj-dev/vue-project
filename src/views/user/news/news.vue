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
        <div class="news-item" v-for="(news, index) in pagedNews" :key="index" @click="goToNewsInfo(news.id)">
          <div class="news-title-wrapper">
            <h2>{{ news.title }}</h2>
            <span class="news-date">{{ news.date }}</span>
          </div>
        </div>
      </div>

      <!-- 添加分页器 -->
      <div class="pagination">
        <button 
          class="page-btn" 
          :disabled="currentPage === 1"
          @click="changePage(currentPage - 1)"
        >
          上一页
        </button>
        
        <div class="page-numbers">
          <button 
            v-for="pageNum in displayedPages" 
            :key="pageNum"
            class="page-num"
            :class="{ active: currentPage === pageNum }"
            @click="changePage(pageNum)"
          >
            {{ pageNum }}
          </button>
        </div>

        <button 
          class="page-btn"
          :disabled="currentPage === totalPages"
          @click="changePage(currentPage + 1)"
        >
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import request from '../../../utils/request'

export default {
  name: 'news',
  components: {
    NavBar
  },
  data() {
    return {
      news: [],
      currentPage: 1,
      itemsPerPage: 6
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.news.length / this.itemsPerPage);
    },
    pagedNews() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.news.slice(start, end);
    },
    displayedPages() {
      const pages = [];
      let start = Math.max(1, this.currentPage - 1);
      let end = Math.min(start + 2, this.totalPages);
      
      if (end - start < 2) {
        start = Math.max(1, end - 2);
      }
      
      for (let i = start; i <= end; i++) {
        pages.push(i);
      }
      return pages;
    }
  },
  methods: {
    changePage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    loadNews() {
      console.log('开始请求新闻数据');
      
      request.get("/new")
        .then(res => {
          console.log('获取到的响应:', res);
          
          if(res.code === '0') {
            this.news = res.data.map(item => ({
              ...item,
              commentId: item.commentId || 0,
              adminId: item.adminId || 0
            }));
            console.log('处理后的新闻数据:', this.news);
          } else {
            console.error('请求失败:', res.msg);
          }
        })
        .catch(error => {
          console.error('请求出错:', error);
        });
    },
    goToNewsInfo(id) {
      this.$router.push({
        name: 'news_info',
        params: { id: id.toString() }
      });
    }
  },
  created() {
    this.loadNews();
  }
}
</script>

<style scoped>
.news {
  min-height: 100vh;
  background-color: #f8f7f2;
  padding-top: 60px; /* 为固定定位的导航栏留出空间 */
  display: flex;
  flex-direction: column;
}

/* 添加导航栏包装器样式 */
.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background-color: #fff; /* 确保导航栏背景不透明 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
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
  margin-bottom: 40px;
}

.news-item {
  background: white;
  border-radius: 12px;
  padding: 16px 24px;  /* 减小padding使标题更紧凑 */
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid #eee;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.news-title-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.news-item h2 {
  font-size: 18px;
  font-weight: 500;
  color: #333;
  margin: 0;  /* 移除标题的margin */
  flex: 1;    /* 让标题占据剩余空间 */
  padding-right: 20px;  /* 为日期留出空间 */
}

.news-date {
  color: #999;
  font-size: 14px;
  white-space: nowrap;  /* 防止日期换行 */
}

.news-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #4CAF50;
}

@media (max-width: 768px) {
  .news {
    padding: 20px;
  }
  
  .news_content {
    width: 95%;
    padding: 20px 0;
  }
  
  .news-item {
    padding: 20px;
  }
  
  .news-item h2 {
    font-size: 18px;
  }
}

/* 添加分页器样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
  margin-top: 40px;
  margin-bottom: 20px;
}

.page-numbers {
  display: flex;
  gap: 8px;
}

.page-btn,
.page-num {
  min-width: 40px;
  height: 40px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background: white;
  color: #666;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 12px;
}

.page-btn:hover:not(:disabled),
.page-num:hover:not(.active) {
  border-color: #4CAF50;
  color: #4CAF50;
}

.page-btn:disabled {
  background-color: #f5f5f5;
  border-color: #e0e0e0;
  color: #999;
  cursor: not-allowed;
}

.page-num.active {
  background-color: #4CAF50;
  border-color: #4CAF50;
  color: white;
  cursor: default;
}
</style>
