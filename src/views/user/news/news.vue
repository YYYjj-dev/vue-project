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
        <div class="news-item" v-for="newsInfo,index in news" :key="index" @click="goToNewsInfo(news.id)">
          <h2>{{ newsInfo.title }}</h2>
          <p> news </p>
          <!-- <p>{{ news.description }}</p> -->
        </div>
      </div>
    </div>
    
    <!-- 分页器 -->
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
</template>

<script setup name="news" components="NavBar">
import NavBar from '../../../components/NavBar.vue'
import {defineNews} from '../../../store/newsStore'
import { computed,onMounted,onBeforeMount,reactive,ref } from 'vue';
import request from '../../../utils/request'

  
      let currentPage = 1
      let itemsPerPage = 6 
      let news= ref([])
      // [
      //   { id: 1, title: '资讯报道 1', description: '这是资讯报道 1 的简要描述。' },
      //   { id: 2, title: '资讯报道 2', description: '这是资讯报道 2 的简要描述。' },
      //   { id: 3, title: '资讯报道 3', description: '这是资讯报道 3 的简要描述。' },
      //   { id: 4, title: '资讯报道 4', description: '这是资讯报道 4 的简要描述。' },
      //   { id: 5, title: '资讯报道 5', description: '这是资讯报道 5 的简要描述。' },
      //   { id: 6, title: '资讯报道 6', description: '这是资讯报道 6 的简要描述。' },
      //   { id: 7, title: '资讯报道 7', description: '这是资讯报道 7 的简要描述。' },
      //   { id: 8, title: '资讯报道 8', description: '这是资讯报道 8 的简要描述。' },
      //   { id: 9, title: '资讯报道 9', description: '这是资讯报道 9 的简要描述。' },
      //   { id: 10, title: '资讯报道 10', description: '这是资讯报道 10 的简要描述。' },
      //   { id: 11, title: '资讯报道 11', description: '这是资讯报道 11 的简要描述。' },
      //   { id: 12, title: '资讯报道 12', description: '这是资讯报道 12 的简要描述。' },
      // ]
    // 每页最多显示6条资讯报道
  

    const totalPages = computed( ()=>{
      return Math.ceil(news.length / itemsPerPage)  // 总页数
    })


    let pagedNews = () =>{
      const start = (currentPage - 1) * itemsPerPage;
      const end = start + itemsPerPage;
      console.log(start)
      console.log(end)
      console.log(news)
     
      return news;  // 获取当前页需要展示的资讯报道
    }
    const displayedPages= computed(()=> {
      const pages = [];
      let start = Math.max(1, currentPage - 1);
      let end = Math.min(start + 2, totalPages);
      
      // 调整起始页，确保始终显示3个页码
      if (end - start < 2) {
        start = Math.max(1, end - 2);
      }
      
      for (let i = start; i <= end; i++) {
        pages.push(i);
      }
      return pages;
    })

    function changePage(page) {
      if (page >= 1 && page <= totalPages) {
        currentPage = page;  // 切换页面
      }
    }
    function oToNewsInfo(id) {
      this.$router.push({ name: 'news_info', params: { id } });  // 跳转到 'news_info' 页面，并传递 'id'
    }
  
    onMounted(async ()=> {
      showNews()
    })

    async function showNews(){
      let {data} = await request.get('info/news/findAllNews')
      news.value = data.data.itemList
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
  padding: 24px;
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid #eee;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.news-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  border-color: #4CAF50;
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
</style>
