<template>
  <div class="carousel-container">
    <div class="carousel-wrapper" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
      <div v-for="(image, index) in images" :key="index" class="carousel-item">
        <img :src="image" :alt="`轮播图 ${index + 1}`">
      </div>
    </div>
    
    <!-- 轮播图指示器 -->
    <div class="carousel-indicators">
      <span 
        v-for="(_, index) in images" 
        :key="index"
        :class="['indicator', { active: currentIndex === index }]"
        @click="setCurrentIndex(index)"
      ></span>
    </div>
    
    <!-- 左右箭头 -->
    <button class="carousel-button prev" @click="prev">&#10094;</button>
    <button class="carousel-button next" @click="next">&#10095;</button>
  </div>
</template>

<script>
import request from '../utils/request'

export default {
  name: 'Carousel',
  data() {
    return {
      currentIndex: 0,
      images: [],
      autoPlayInterval: null
    }
  },
  mounted() {
    this.loadCarouselImages()
    this.startAutoPlay()
  },
  beforeUnmount() {
    this.stopAutoPlay()
  },
  methods: {
    loadCarouselImages() {
      console.log('开始请求轮播图数据');
      
      request.get('/image/images/carousel')
        .then(res => {
          console.log('获取到的响应:', res);
          
          if(res.code === '0') {
            this.images = res.data.map(item => item.path);
            console.log('处理后的轮播图数据:', this.images);
          } else {
            console.error('请求失败:', res.msg);
          }
        })
        .catch(error => {
          console.error('请求出错:', error);
        });
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
      this.autoPlayInterval = setInterval(() => {
        this.next()
      }, 5000) // 每5秒切换一次
    },
    stopAutoPlay() {
      clearInterval(this.autoPlayInterval)
    }
  }
}
</script>

<style scoped>
.carousel-container {
  width: 100%;
  height: 400px; /* 根据需要调整高度 */
  position: relative;
  overflow: hidden;
}

.carousel-wrapper {
  display: flex;
  transition: transform 0.5s ease;
  height: 100%;
}

.carousel-item {
  min-width: 100%;
  height: 100%;
}

.carousel-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 16px;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s;
}

.carousel-container:hover .carousel-button {
  opacity: 1;
}

.prev {
  left: 10px;
}

.next {
  right: 10px;
}

.carousel-indicators {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
}

.indicator {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  cursor: pointer;
}

.indicator.active {
  background: white;
}
</style>