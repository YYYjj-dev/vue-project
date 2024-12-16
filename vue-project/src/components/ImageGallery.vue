<template>
  <div class="image-gallery">
    <!-- 每个分类的图片和描述 -->
    <div v-if="category === '防腐剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['防腐剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '抗氧化剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['抗氧化剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '甜味剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['甜味剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '增稠剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['增稠剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '着色剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['着色剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '膨松剂'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['膨松剂']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <div v-if="category === '香料与香精'" class="gallery-row">
      <div class="gallery-item" v-for="(image, index) in images['香料与香精']" :key="index">
        <router-link :to="`/additive_type_info/${category}/${image.id}`">
          <img :src="`../img/${image.src}`" :alt="image.alt" />
          <p>{{ image.alt }}</p>
        </router-link>
      </div>
    </div>

    <!-- 其他分类的内容以类似方式展示 -->
  </div>
</template>

<script>
export default {
  props: {
    category: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      images: {
        '防腐剂': [
          { id: 1, src: 'img1.jpg', alt: '防腐剂图片1' },
          { id: 2, src: 'img2.jpg', alt: '防腐剂图片2' },
          { id: 3, src: 'img3.jpg', alt: '防腐剂图片3' }
        ],
        '抗氧化剂': [
          { id: 4, src: 'img4.jpg', alt: '抗氧化剂图片1' },
          { id: 5, src: 'img5.jpg', alt: '抗氧化剂图片2' },
          { id: 6, src: 'img6.jpg', alt: '抗氧化剂图片3' }
        ],
        '甜味剂': [
          { id: 7, src: 'img7.jpg', alt: '甜味剂图片1' },
          { id: 8, src: 'img8.jpg', alt: '甜味剂图片2' },
          { id: 9, src: 'img9.jpg', alt: '甜味剂图片3' }
        ],
        '增稠剂': [
          { id: 10, src: 'img10.jpg', alt: '增稠剂图片1' },
          { id: 11, src: 'img11.jpg', alt: '增稠剂图片2' },
          { id: 12, src: 'img12.jpg', alt: '增稠剂图片3' }
        ],
        '着色剂': [
          { id: 13, src: 'img13.jpg', alt: '着色剂图片1' },
          { id: 14, src: 'img14.jpg', alt: '着色剂图片2' },
          { id: 15, src: 'img15.jpg', alt: '着色剂图片3' }
        ],
        '膨松剂': [
          { id: 16, src: 'img16.jpg', alt: '膨松剂图片1' },
          { id: 17, src: 'img17.jpg', alt: '膨松剂图片2' },
          { id: 18, src: 'img18.jpg', alt: '膨松剂图片3' }
        ],
        '香料与香精': [
          { id: 19, src: 'img19.jpg', alt: '香料与香精图片1' },
          { id: 20, src: 'img20.jpg', alt: '香料与香精图片2' },
          { id: 21, src: 'img21.jpg', alt: '香料与香精图片3' }
        ],

        // 其他分类的图片数据也可以按照同样的方式添加
      }
    }
    }
}
</script>

<style scoped>
.image-gallery {
  text-align: center;  /* 将图片和文字的标题居中显示 */
  margin-top: 20px;
}

h3 {
  font-size: 20px;
  margin-bottom: 20px;
}

.gallery-row {
  display: flex;               /* 使用flexbox布局 */
  justify-content: center;     /* 水平居中对齐 */
  gap: 20px;                   /* 图片之间的间距 */
  margin-bottom: 40px;         /* 每行图片之间的间距 */
}

.gallery-item {
  text-align: center;          /* 图片和文字都居中 */
  border: 1px solid #ccc;
  width: 30%;
  overflow: hidden;
}

.gallery-item img {
  width: 100%;
  height: auto;                /* 保持图片比例 */
  border-radius: 8px;          /* 给图片添加圆角 */
  margin: 0 50px;         /* 图片和文字之间的间距 */

}

.gallery-item p {
  font-size: 14px;             /* 设置文字大小 */
  color: #666;                 /* 设置文字颜色 */
}
</style>