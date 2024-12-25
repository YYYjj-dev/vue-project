<template>
    <NavBar />
    <div class="title">
      <h1>热门案例</h1>
    </div>

    <!-- 轮播图 -->
    <div class="cases">
      <div class="cases-carousel">
        <Carousel />
      </div>

      <!-- 案例列表 -->
      <div class="cases-list">
        <div class="cases-list-content">
          <div class="cases-item" 
               v-for="(item, index) in hotCases" 
               :key="index" 
               @click="goToCase(item.id)">
            <h2>{{ item.title }}</h2>
          </div>
        </div>
        <div class="more-button-cases">
          <button @click="goToMore">查看更多</button>
        </div>
      </div>
    </div>

    <div class="title">
      <h1>不同群体对应案例</h1>
    </div>

    <!-- 群体分类 -->
    <div class="cases-group">
        <button class="cases-group-item" 
                v-for="(group, index) in groups" 
                :key="index"
                :class="{ active: currentGroup === group.type }"
                @click="switchGroup(group.type)">
            <h2>{{ group.name }}</h2>
        </button>
    </div>

    <!-- 群体案例 -->
    <div class="gruop_container">
        <!-- 群体案例标题 -->
        <div class="gruop_title">
            <div class="gruop_title_content">
                <div class="gruop_title_item" 
                     v-for="(item, index) in currentGroupCases" 
                     :key="index" 
                     @click="goToCase(item.id)">
                    <h1>{{ item.title }}</h1>
                </div>
            </div>
            <div class="more-button-title">
                <button @click="goToMore">查看更多</button>
            </div>
        </div>

        <!-- 推荐商品 -->
        <div class="gruop_product">
            <div class="gruop_product_grid">
                <div class="gruop_product_item" 
                     v-for="(product, index) in currentGroupProducts" 
                     :key="index"
                     @click="goToProduct(product.id)">
                    <div class="product-image">
                        <div class="product-image-placeholder">{{ product.title }}</div>
                    </div>
                    <h2>{{ product.title }}</h2>
                </div>
            </div>
            <div class="more-button-products">
                <button @click="goToShop">查看更多</button>
            </div>
        </div>
         
    </div>
    <Footer />





</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Carousel from '../../../components/Carousel.vue'
import { useRouter } from 'vue-router'
import Footer from '../../../components/Footer.vue'

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
      hotCases: [
        { id: 1, title: '案例一' },
        { id: 2, title: '案例二' },
        { id: 3, title: '案例三' }
      ],
      currentGroup: 'baby',
      groups: [
        { type: 'baby', name: '婴幼儿' },
        { type: 'child', name: '儿童' },
        { type: 'elder', name: '老年' },
        { type: 'other', name: '其他' }
      ],
      groupCases: {
        baby: [
          { id: 1, title: '婴幼儿案例1' },
          { id: 2, title: '婴幼儿案例2' },
          { id: 3, title: '婴幼儿案例3' },
          { id: 4, title: '婴幼儿案例4' }
        ],
        child: [
          { id: 5, title: '儿童案例1' },
          { id: 6, title: '儿童案例2' },
          { id: 7, title: '儿童案例3' },
          { id: 8, title: '儿童案例4' }
        ],
        elder: [
          { id: 9, title: '老年案例1' },
          { id: 10, title: '老年案例2' },
          { id: 11, title: '老年案例3' },
          { id: 12, title: '老年案例4' }
        ],
        other: [
          { id: 13, title: '其他案例1' },
          { id: 14, title: '其他案例2' },
          { id: 15, title: '其他案例3' },
          { id: 16, title: '其他案例4' }
        ]
      },
      groupProducts: {
        baby: [
          { id: 1, title: '婴幼儿商品1' },
          { id: 2, title: '婴幼儿商品2' },
          { id: 3, title: '婴幼儿商品3' },
          { id: 4, title: '婴幼儿商品4' }
        ],
        child: [
          { id: 5, title: '儿童商品1' },
          { id: 6, title: '儿童商品2' },
          { id: 7, title: '儿童商品3' },
          { id: 8, title: '儿童商品4' }
        ],
        elder: [
          { id: 9, title: '老年商品1' },
          { id: 10, title: '老年商品2' },
          { id: 11, title: '老年商品3' },
          { id: 12, title: '老年商品4' }
        ],
        other: [
          { id: 13, title: '其他商品1' },
          { id: 14, title: '其他商品2' },
          { id: 15, title: '其他商品3' },
          { id: 16, title: '其他商品4' }
        ]
      }
    }
  },
  computed: {
    currentGroupCases() {
      return this.groupCases[this.currentGroup]
    },
    currentGroupProducts() {
      return this.groupProducts[this.currentGroup]
    }
  },
  methods: {
    goToCase(id) {
      this.router.push({
        name: 'cases_info',
        params: { id },
        query: { title: '案例标题' }
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
    }
  }
}
</script>

<style scoped>
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
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  background: white;
  overflow: hidden;
  display: flex;
  padding: 30px;
  gap: 30px;
}

.cases-carousel {
  width: 700px;
  height: 400px;
  position: relative;
  flex-shrink: 0;
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
  height: 90px;
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
  width: 90%;
  max-width: 1400px;
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
}

.cases-group-item.active {
  background: #42b983;
  color: white;
  border-color: #42b983;
}

/* 群体内容容器 */
.gruop_container {
  width: 90%;
  max-width: 1400px;
  margin: 40px auto;
  display: flex;
  gap: 40px;
}

.gruop_title {
  width: 40%;
  padding: 20px;
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
  height: 80px;
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
}

.gruop_title_item:hover h1 {
  color: #42b983;
}

.gruop_product {
  width: 60%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  position: relative;
}

.gruop_product_grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 60px;
}

.gruop_product_item {
  background: white;
  border: 1px solid #eee;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 240px;
  display: flex;
  flex-direction: column;
}

.product-image {
  width: 100%;
  height: 160px;
  overflow: hidden;
  position: relative;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-image-placeholder {
  color: #666;
  font-size: 16px;
  text-align: center;
  padding: 20px;
}

.gruop_product_item h2 {
  margin: 0;
  padding: 15px;
  font-size: 16px;
  color: #2c3e50;
  transition: color 0.3s ease;
  text-align: center;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 更多按钮容器样式 */
.more-button-cases,
.more-button-title,
.more-button-products {
  position: absolute;
  bottom: 20px;
  right: 20px;
}

/* 按钮样式统一 */
.more-button-cases button,
.more-button-title button,
.more-button-products button {
  padding: 12px 30px;
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

.more-button-cases button:hover,
.more-button-title button:hover,
.more-button-products button:hover {
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
    height: 60px;
  }
  
  .gruop_product_item {
    height: 200px;
  }
  
  .more-button-cases,
  .more-button-title,
  .more-button-products {
    position: static;
    text-align: right;
    margin-top: 20px;
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

  .more-button-cases {
    padding-top: 15px;
  }
}
</style>

