<template>
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
</template>

<script>
export default {
  name: 'Carousel',
  data() {
    return {
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
    this.startAutoPlay()
  },
  beforeUnmount() {
    this.stopAutoPlay()
  },
  methods: {
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
.carousel-container {
  position: relative;
  width: 100%;
  height: 500px;
  overflow: hidden;
  border-radius: 12px;
  margin-top: 20px;
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

@media (max-width: 768px) {
  .carousel-container {
    height: 300px;
  }
}

@media (max-width: 480px) {
  .carousel-container {
    height: 200px;
  }
}
</style>
