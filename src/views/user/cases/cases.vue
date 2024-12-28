<template>
  <div class="page-wrapper">
    <div class="nav-wrapper">
      <NavBar />
    </div>
    <div class="title">
      <h1>热门案例</h1>
    </div>

    <!-- 轮播图 -->
    <div class="cases">
      <div class="cases-carousel">
        <div class="carousel-container" @mouseenter="pauseAutoPlay" @mouseleave="resumeAutoPlay">
          <div class="carousel-wrapper" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
            <div v-for="(image, index) in images" :key="index" class="carousel-item">
              <img :src="image" :alt="`轮播图 ${index + 1}`" class="carousel-image">
            </div>
          </div>

          <!-- 轮播图指示器 -->
          <div class="carousel-indicators">
            <span v-for="(_, index) in images" :key="index" :class="['indicator', { active: currentIndex === index }]"
              @click="setCurrentIndex(index)"></span>
          </div>

          <!-- 左右箭头 -->
          <button class="carousel-button prev" @click="prev">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                stroke-linejoin="round" />
            </svg>
          </button>
          <button class="carousel-button next" @click="next">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                stroke-linejoin="round" />
            </svg>
          </button>
        </div>
      </div>

      <!-- 案例列表 -->
      <div class="cases-list">
        <div class="cases-list-content">
          <div class="cases-item" v-for="(item, index) in hotCases" :key="index" @click="goToCase(item.id)">
            <h2>{{ item.title }}</h2>
          </div>
        </div>
      </div>
    </div>

    <!-- 热门案例查看更多按钮 -->
    <div class="more-button-center hot-cases-button">
      <button @click="goToMore">查看更多</button>
    </div>

    <div class="title">
      <h1>不同群体对应案例</h1>
    </div>

    <!-- 群体分类 -->
    <div class="cases-group">
      <button class="cases-group-item" v-for="(group, index) in groups" :key="index"
        :class="{ active: currentGroup === group.type }" @click="switchGroup(group.type)">
        <h2>{{ group.name }}</h2>
      </button>
    </div>

    <!-- 群体案例 -->
    <div class="gruop_container">
      <!-- 群体案例标题 -->
      <div class="gruop_content">
        <div class="gruop_title">
          <div class="gruop_title_content">
            <div class="gruop_title_item" v-for="(item, index) in currentGroupCases" :key="index"
              @click="goToCase(item.id)">
              <h1>{{ item.title }}</h1>
            </div>
          </div>
          <div class="more-button-center">
            <button @click="goToMore">查看更多</button>
          </div>
        </div>

        <!-- 推荐商品 -->
        <div class="gruop_product">
          <h2 class="product-section-title">推荐商品</h2>
          <div class="gruop_product_grid">
            <div class="gruop_product_item" v-for="product in currentGroupProducts" :key="product.id"
              @click="goToProduct(product.id)">
              <div class="product-image">
                <img v-if="product.image" :src="product.image" :alt="product.title">
                <div v-else class="product-image-placeholder">{{ product.title }}</div>
                <div class="product-title">{{ product.title }}</div>
              </div>
            </div>
          </div>
          <div class="more-button-center">
            <button @click="goToShop">查看更多</button>
          </div>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Carousel from '../../../components/Carousel.vue'
import { useRouter } from 'vue-router'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

export default {
  name: 'cases',
  components: {
    NavBar,
    Carousel,
    Footer
  },
  setup() {
    const router = useRouter()
    return { router }
  },
  data() {
    return {
      hotCases: [],
      baseUrl: 'http://localhost:8080/image/',
      currentGroup: '婴幼儿',
      groups: [
        { type: '婴幼儿', name: '婴幼儿' },
        { type: '儿童', name: '儿童' },
        { type: '老年', name: '老年' },
        { type: '其他', name: '其他' }
      ],
      currentGroupCases: [],
      currentGroupProducts: [],
      currentIndex: 0,
      images: [
        '/src/img/img4.jpg',
        '/src/img/img7.jpg',
        '/src/img/img6.jpg',
        '/src/img/img2.jpg',
        '/src/img/img3.jpg',
        '/src/img/img5.jpg',
      ],
      autoPlayInterval: null,
      isPaused: false,
    }
  },
  mounted() {
    this.fetchHotCases()
    this.fetchGroupData(this.currentGroup)
    this.startAutoPlay()
  },
  beforeUnmount() {
    this.stopAutoPlay()
  },
  methods: {
    // 获取热门案例数据
    async fetchHotCases() {
      try {
        const { data } = await request.get('/info/findAllCases')
        if (data.code === 200) {
          // 获取前4个案例
          this.hotCases = data.data.slice(0, 4).map(item => ({
            id: item.id,
            title: item.title,
            content: item.content,
            image: item.imgpath ? this.baseUrl + item.imgpath : null,
            date: item.date
          }))
        }
      } catch (error) {
        console.error('获取热门案例失败:', error)
      }
    },
    goToCase(id) {
      this.router.push({
        name: 'cases_info',
        params: { id }
      })
    },
    goToGroupCase(group) {
      this.router.push({
        name: 'cases_info',
        query: { group }
      })
    },
    goToMore() {
      this.router.push('/cases_more')
    },
    goToProduct(id) {
      this.router.push(`/shop_rec_info/${id}`)
    },
    goToShop() {
      this.router.push('/shop_rec')
    },
    switchGroup(type) {
      this.currentGroup = type
      this.fetchGroupData(type)
    },
    // 获取群体相关数据
    async fetchGroupData(type) {
      try {
        // 获取案例数据
        const casesResponse = await request.get('/info/findCasesByType', {
          params: { type }
        })

        if (casesResponse.data.code === 200) {
          this.currentGroupCases = casesResponse.data.data.map(item => ({
            id: item.id,
            title: item.title,
            content: item.content,
            image: item.imgpath ? this.baseUrl + item.imgpath : null,
            date: item.date
          }))
        }

        // 获取商品数据
        const productsResponse = await request.get('/shangpin/findShangpinByGroup', {
          params: { group: type }
        })

        if (productsResponse.data.code === 200) {
          this.currentGroupProducts = productsResponse.data.data.map(item => ({
            id: item.id,
            title: item.name,
            description: item.description,
            image: item.imgpath ? this.baseUrl + item.imgpath : null,
            price: item.price
          }))
        }
      } catch (error) {
        console.error('获取群体数据失败:', error)
      }
    },
    next() {
      this.currentIndex = (this.currentIndex + 1) % this.images.length
    },
    prev() {
      this.currentIndex = this.currentIndex === 0
        ? this.images.length - 1
        : this.currentIndex - 1
    },
    setCurrentIndex(index) {
      this.currentIndex = index
    },
    startAutoPlay() {
      if (!this.isPaused) {
        this.autoPlayInterval = setInterval(() => {
          this.next()
        }, 5000)
      }
    },
    stopAutoPlay() {
      if (this.autoPlayInterval) {
        clearInterval(this.autoPlayInterval)
        this.autoPlayInterval = null
      }
    },
    pauseAutoPlay() {
      this.isPaused = true
      this.stopAutoPlay()
    },
    resumeAutoPlay() {
      this.isPaused = false
      this.startAutoPlay()
    }
  }
}
</script>

<style scoped>
.page-wrapper {
  min-height: 100vh;
  background-color: #f8f9fa;
  padding-top: 80px;
}

/* 导航栏包装器样式 */
.nav-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 基础布局 */
.title {
  text-align: center;
  margin: 40px 0;
  border-bottom: none;
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

/* 热门案例区域 */
.cases {
  width: 1200px;
  margin: 40px auto;
  overflow: hidden;
  display: flex;
  padding: 30px;
  gap: 30px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.cases-carousel {
  width: 700px;
  height: 420px;
  position: relative;
  flex-shrink: 0;
  border-radius: 12px;
  overflow: hidden;
  background: #f5f5f5;
}

/* 确保轮播图内容也有圆角 */
.cases-carousel :deep(.carousel-container) {
  height: 100%;
  border-radius: 12px;
  overflow: hidden;
}

.cases-carousel :deep(.carousel-wrapper) {
  height: 100%;
}

.cases-carousel :deep(.carousel-item) {
  height: 100%;
}

.cases-carousel :deep(.carousel-image) {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.cases-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  position: relative;
  height: 400px;
  padding: 0;
}

.cases-list-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
  flex: 1;
  padding-right: 10px;
}

.cases-item {
  background: white;
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s ease;
  border: 1px solid #eee;
  cursor: pointer;
  display: flex;
  align-items: center;
  min-height: 90px;
}

.cases-item:last-child {
  margin-bottom: 30px;
}

.cases-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  border-color: #42b983;
}

.cases-item h2 {
  margin: 0;
  font-size: 16px;
  color: #2c3e50;
  transition: color 0.3s ease;
}

.cases-item:hover h2 {
  color: #42b983;
}

/* 更多按钮容器样式 */
.more-button-cases {
  display: flex;
  justify-content: flex-end;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.more-button-cases button {
  padding: 10px 25px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 15px;
  font-weight: 500;
  box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.more-button-cases button:hover {
  background: #3aa876;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(66, 185, 131, 0.3);
}

/* 群体分类 */
.cases-group {
  width: 1200px;
  margin: 40px auto;
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.cases-group-item {
  padding: 12px 30px;
  border: 1px solid #e0e0e0;
  border-radius: 25px;
  background: white;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 120px;
}

.cases-group-item h2 {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.cases-group-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(66, 185, 131, 0.15);
  border-color: #42b983;
  color: #42b983;
}

.cases-group-item.active {
  background: #42b983;
  color: white;
  border-color: #42b983;
}

/* 群体内容容器 */
.gruop_container {
  width: 1200px;
  margin: 40px auto;
  display: flex;
  flex-direction: column;
  gap: 30px;
  background: white;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.gruop_content {
  display: flex;
  flex-direction: column;
  gap: 40px;
}

.gruop_title {
  width: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  position: relative;
}

.gruop_title_content {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 60px;
}

.gruop_title_item {
  background: white;
  border: 1px solid #eee;
  border-radius: 12px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  min-height: 80px;
  display: flex;
  align-items: center;
}

.gruop_title_item:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  border-color: #42b983;
}

.gruop_title_item h1 {
  margin: 0;
  font-size: 20px;
  color: #2c3e50;
  transition: color 0.3s ease;
  line-height: 1.4;
}

.gruop_title_item:hover h1 {
  color: #42b983;
}

.gruop_product {
  width: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  position: relative;
}

.gruop_product_grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.gruop_product_item {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 240px;
  display: flex;
  flex-direction: column;
}

.gruop_product_item:hover {
  border-color: #42b983;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.product-image {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
  background: #f5f5f5;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.gruop_product_item:hover .product-image img {
  transform: scale(1.1);
}

.product-image-placeholder {
  color: #666;
  font-size: 16px;
  text-align: center;
  padding: 20px;
  position: relative;
  z-index: 1;
  transition: color 0.3s ease;
}

.gruop_product_item:hover .product-image-placeholder {
  color: #42b983;
}

.product-title {
  margin: 0;
  padding: 12px 15px;
  font-size: 16px;
  color: #333;
  transition: all 0.3s ease;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;
  background: rgba(255, 255, 255, 0.9);
  border-top: 1px solid #eee;
}

.gruop_product_item:hover .product-title {
  transform: scale(1.05);
  color: #42b983;
  background: rgba(255, 255, 255, 0.95);
}

.product-section-title {
  font-size: 20px;
  color: #333;
  margin-bottom: 20px;
  padding-left: 15px;
  border-left: 4px solid #42b983;
}

.more-button-center {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  position: relative;
  padding-top: 20px;
}

/* 热门案例按钮样式 */
.hot-cases-button {
  margin: 40px auto;
  width: 1200px;
}

/* 按钮样式 */
.more-button-center button {
  padding: 10px 30px;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 15px;
  font-weight: 500;
  box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.more-button-center button:hover {
  background: #3aa876;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(66, 185, 131, 0.3);
}

/* 响应式布局 */
@media (max-width: 768px) {

  .cases,
  .gruop_container {
    flex-direction: column;
  }

  .cases-carousel,
  .cases-list,
  .gruop_title,
  .gruop_product {
    width: 100%;
  }

  .gruop_product_grid {
    grid-template-columns: 1fr;
  }

  .gruop_title_item {
    min-height: 60px;
    padding: 15px;
  }

  .gruop_product_item {
    height: 200px;
  }

  .more-button-center {
    position: static;
    text-align: right;
    margin-top: 20px;
    padding-top: 15px;
  }

  .more-button-center::before {
    left: 10%;
    right: 10%;
  }

  .more-button-center button {
    padding: 8px 25px;
    font-size: 14px;
  }

  .product-image {
    height: 100px;
  }

  .product-title {
    padding: 10px;
    font-size: 14px;
  }
}

/* 响应式布局 */
@media (max-width: 1280px) {
  .cases {
    width: 90%;
    flex-direction: column;
    height: auto;
    padding: 20px;
  }

  .cases-carousel {
    width: 100%;
    height: 300px;
    margin-bottom: 20px;
  }

  .cases-carousel :deep(.carousel-container) {
    height: 100%;
  }

  .cases-list {
    width: 100%;
    height: auto;
  }

  .cases-item {
    height: 70px;
  }

  .cases-item:last-child {
    margin-bottom: 20px;
  }

  .more-button-center {
    padding-top: 15px;
  }

  .cases-group,
  .gruop_container {
    width: 90%;
  }

  .gruop_container {
    padding: 20px 25px;
  }

  .gruop_title,
  .gruop_product {
    padding: 0 10px;
  }

  .gruop_product_grid {
    grid-template-columns: repeat(3, 1fr);
  }

  .hot-cases-button {
    width: 90%;
  }

  .gruop_product_item {
    height: 220px;
  }
}

@media (max-width: 768px) {
  .cases-carousel {
    height: 250px;
  }

  .gruop_product_grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .gruop_product_item {
    height: 200px;
  }
}

@media (max-width: 480px) {
  .cases-carousel {
    height: 200px;
  }

  .gruop_product_grid {
    grid-template-columns: 1fr;
  }

  .more-button-center {
    padding-top: 12px;
  }

  .more-button-center::before {
    left: 5%;
    right: 5%;
  }

  .more-button-center button {
    padding: 8px 20px;
    font-size: 13px;
  }

  .gruop_product_item {
    height: 180px;
  }

  .product-title {
    padding: 8px;
    font-size: 13px;
  }
}

.carousel-container {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  border-radius: 12px;
}

.carousel-wrapper {
  display: flex;
  transition: transform 0.5s ease;
  width: 100%;
  height: 100%;
}

.carousel-item {
  min-width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.carousel-indicators {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
  z-index: 20;
}

.indicator {
  width: 10px;
  height: 10px;
  margin: 0 5px;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.indicator.active {
  background-color: rgba(255, 255, 255, 1);
}

.carousel-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 50%;
  z-index: 10;
  transition: background-color 0.3s;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.carousel-button:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

.carousel-button.prev {
  left: 10px;
}

.carousel-button.next {
  right: 10px;
}
</style>
