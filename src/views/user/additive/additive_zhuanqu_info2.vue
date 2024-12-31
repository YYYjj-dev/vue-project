<template>
    <div class="page-container">
        <NavBar />

        <div class="content-wrapper">
            <!-- 左侧分类 -->
            <div class="filter-section">
                <h3 class="filter-title">人群分类</h3>
                <div class="filter-group">
                    <button v-for="age in ages" :key="age.value"
                        :class="['filter-btn', { active: currentGroup === age.value }]" @click="changeGroup(age.value)">
                        {{ age.label }}
                    </button>
                </div>
            </div>

            <!-- 商品列表 -->
            <div class="products-section">
                <div class="products-grid">
                    <div v-for="item in productsList" :key="item.id" class="product-card">
                        <div class="product-image">
                            <img :src="'http://localhost:8080/image/' + item.imgpath" :alt="item.name">
                        </div>
                        <div class="product-info">
                            <h3 class="product-name">{{ item.name }}</h3>
                            <p class="product-desc">{{ item.description }}</p>
                            <div class="product-footer">
                                <span class="product-price">¥{{ item.price }}</span>
                                <button class="buy-btn" @click="goToShop(item.id)">去购买</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import request from '../../../utils/request'

const router = useRouter()
const currentGroup = ref('婴幼儿')
const productsList = ref([])

const ages = [
    { label: '婴幼儿', value: '婴幼儿' },
    { label: '儿童', value: '儿童' },
    { label: '老年', value: '老年' },
    { label: '其他', value: '其他' }
]

// 获取商品数据
const fetchProducts = async (group) => {
    try {
        const response = await request.get('/shangpin/findShangpinByGroup', {
            params: { group }
        })
        if (response.data?.code === 200) {
            productsList.value = response.data.data || []
        }
    } catch (error) {
        console.error('获取商品数据失败：', error)
    }
}

// 切换年龄分段
const changeGroup = (group) => {
    currentGroup.value = group
    fetchProducts(group)
}

// 跳转到商品详情
const goToShop = (id) => {
    router.push({
        name: 'shop_rec_info',
        params: { id: id.toString() }
    })
}

onMounted(() => {
    fetchProducts(currentGroup.value)
})
</script>

<style scoped>
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f9fa;
}

.content-wrapper {
    flex: 1;
    max-width: 1400px;
    margin: 2rem auto;
    padding: 0 2rem;
    display: flex;
    gap: 2rem;
}

/* 筛选区域样式 */
.filter-section {
    width: 200px;
    flex-shrink: 0;
    background-color: white;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    height: fit-content;
}

.filter-title {
    font-size: 1.1rem;
    color: #333;
    margin: 0 0 1.5rem 0;
    padding-bottom: 1rem;
    border-bottom: 1px solid #eee;
}

.filter-group {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.filter-btn {
    padding: 0.75rem 1rem;
    border: 2px solid #e0e0e0;
    background: white;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 1rem;
    font-weight: 500;
    color: #666;
    text-align: left;
}

.filter-btn.active {
    background: #4CAF50;
    color: white;
    border-color: #4CAF50;
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.filter-btn:hover:not(.active) {
    border-color: #4CAF50;
    color: #4CAF50;
    transform: translateX(5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

/* 商品区域样式 */
.products-section {
    flex: 1;
}

/* 商品网格布局 */
.products-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 1.5rem;
}

/* 商品卡片样式 */
.product-card {
    background: white;
    border-radius: 12px;
    overflow: hidden;
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.product-image {
    width: 100%;
    height: 200px;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-card:hover .product-image img {
    transform: scale(1.05);
}

.product-info {
    padding: 1.2rem;
}

.product-name {
    font-size: 1.1rem;
    color: #333;
    margin: 0 0 0.5rem 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.product-desc {
    color: #666;
    font-size: 0.9rem;
    margin: 0 0 1rem 0;
    line-height: 1.5;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    height: 2.8em;
}

.product-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 1rem;
}

.product-price {
    font-size: 1.25rem;
    font-weight: 600;
    color: #ff6b6b;
}

.buy-btn {
    padding: 0.5rem 1.2rem;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
}

.buy-btn:hover {
    background: #45a049;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

/* 响应式调整 */
@media (max-width: 1400px) {
    .products-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 1100px) {
    .products-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 768px) {
    .content-wrapper {
        flex-direction: column;
        padding: 0 1rem;
    }

    .filter-section {
        width: 100%;
    }

    .filter-group {
        flex-direction: row;
        flex-wrap: wrap;
    }

    .filter-btn {
        flex: 1;
        min-width: 120px;
        text-align: center;
    }

    .products-grid {
        grid-template-columns: repeat(2, 1fr);
        gap: 1rem;
    }
}

@media (max-width: 480px) {
    .products-grid {
        grid-template-columns: 1fr;
    }
}
</style>