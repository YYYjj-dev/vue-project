<template>
  <div class="regulation">
    <!-- 导航栏 -->
    <div class="nav-wrapper">
      <NavBar />
    </div>

    <div class="regulation_content">
      <!-- 标题 -->
      <div class="page-title">
        <h1>相关法律法规</h1>
      </div>

      <!-- 法律法规内容 -->
      <div class="laws-content">
        <div class="law-item" v-for="law in currentPageLaws" :key="law.id" @click="goToLawInfo(law.id)">
          <h2>{{ law.title }}</h2>
        </div>

        <!-- 添加分页器 -->
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

    <!-- 添加页脚 -->
    <Footer />
  </div>
</template>

<script setup name="regulation" components="NavBar, Footer">
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'
import { useRouter } from 'vue-router'
import { computed, onMounted, onBeforeMount, reactive, ref } from 'vue'

let laws = ref([])
const router = useRouter()

function goToLawInfo(id) {
  router.push({ path: '/regulation_info/' + id });
}

onMounted(async () => {
  showNews()
})

async function showNews() {
  let { data } = await request.get('info/findAllRegular')
  laws.value = data.data
  console.log(laws.value)
}

// 添加分页相关的响应式数据
const pageSize = ref(10) // 每页显示的数量
const currentPage = ref(1) // 当前页码

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(laws.value.length / pageSize.value)
})

// 计算当前页显示的法规
const currentPageLaws = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return laws.value.slice(start, end)
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
.regulation {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f8f9fa;
  padding-top: 80px;
}

/* 添加导航栏包装器样式 */
.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background-color: #fff;
  /* 确保导航栏背景不透明 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  /* 添加阴影效果 */
}

.regulation_content {
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

.laws-content {
  margin-bottom: 40px;
}

.law-item {
  background: white;
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid #eee;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.law-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  border-color: #4CAF50;
}

.law-item h2 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 12px;
}

.law-item p {
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
  .page-numbers {
    display: none;
  }

  .pagination {
    margin-top: 20px;
  }
}
</style>
