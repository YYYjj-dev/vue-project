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
        <div class="law-item" v-for="(law, index) in laws" :key="index" @click="goToLawInfo(law.id)">
          <h2>{{ law.title }}</h2>
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

/* 响应式调整 */
@media (max-width: 768px) {

  .page-btn,
  .page-num {
    width: 32px;
    height: 32px;
    font-size: 12px;
  }

  .pagination {
    gap: 4px;
  }

  .page-numbers {
    gap: 4px;
  }
}
</style>
