<template>
    <div class="shop-sel">
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="main-content">
            <!-- 左侧商品分类 -->
            <div class="category-sidebar">
                <div class="category-title">商品分类</div>
                <div class="category-list">
                    <button v-for="category in categories" :key="category.id" class="category-btn"
                        :class="{ active: currentCategory === category.id }" @click="changeCategory(category.id)">
                        {{ category.name }}
                    </button>
                </div>
            </div>

            <!-- 右侧商家列表 -->
            <div class="seller-list">
                <div v-if="isLoading" class="loading-state">
                    正在加载商家信息...
                </div>
                <div v-else-if="merchants.length === 0" class="no-results">
                    暂无相关商家
                </div>
                <div v-else class="seller-card" v-for="seller in merchants" :key="seller.id">
                    <div class="seller-header">
                        <div class="seller-main-info">
                            <div class="seller-logo">
                                <template v-if="seller.logo">
                                    <img :src="seller.logo" :alt="seller.name">
                                </template>
                                <template v-else>
                                    {{ seller.name.charAt(0) }}
                                </template>
                            </div>
                            <div class="seller-info">
                                <div class="seller-name">{{ seller.name }}</div>
                                <div class="seller-desc">{{ seller.description }}</div>
                                <div class="seller-tags">
                                    <span class="seller-type">{{ seller.type }}</span>
                                </div>
                            </div>
                        </div>
                        <button class="enter-store" @click="goToStore(seller)">进店</button>
                    </div>

                    <!-- 商品轮播图 -->
                    <div class="product-showcase" v-if="seller.products?.length > 0">
                        <div class="carousel-wrapper">
                            <button class="carousel-btn prev" @click="prevSlide(seller.id)">❮</button>
                            <div class="carousel-track" :style="getCarouselStyle(seller.id)">
                                <div v-for="product in seller.products" :key="product.id" class="carousel-item">
                                    <div class="product-image">
                                        <img :src="baseUrl + product.imgpath" :alt="product.name"
                                            @error="handleImageError">
                                    </div>

                                </div>
                            </div>
                            <button class="carousel-btn next" @click="nextSlide(seller.id)">❯</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../../utils/request'
import Footer from '../../../components/Footer.vue'
export default {
    name: 'shop_sel',
    components: {
        NavBar,
        Footer
    },
    setup() {
        const router = useRouter()
        const baseUrl = 'http://localhost:8080/image/'
        const currentCategory = ref(1)
        const isLoading = ref(false)
        const merchants = ref([])
        const carouselPositions = ref({})  // 存储每个商家的轮播位置

        // 分类数据
        const categories = [
            { id: 1, name: '果蔬', type: '果蔬' },
            { id: 2, name: '肉类', type: '肉类' },
            { id: 3, name: '饮料', type: '饮料' },
            { id: 4, name: '其他', type: '其他' }
        ]

        // 获取商家商品数据
        const fetchMerchantProducts = async (merchantId) => {
            try {
                const response = await request.get('/shangpin/findShangpinByMid', {
                    params: { mid: merchantId }
                })
                console.log('商品数据响应:', response) // 添加日志
                if (response.data?.code === 200) {
                    return response.data.data || []
                }
                return []
            } catch (error) {
                console.error('获取商家商品失败:', error)
                return []
            }
        }

        // 修改获取商家数据的方法
        const getMerchantsByType = async (type) => {
            try {
                isLoading.value = true
                const response = await request.get('/merchant/findMerchantByType', {
                    params: { type: type }
                })
                console.log('商家数据响应:', response) // 添加日志

                if (response.data?.code === 200) {
                    const merchantsData = response.data.data || []
                    // 获取每个商家的商品数据
                    for (const merchant of merchantsData) {
                        const products = await fetchMerchantProducts(merchant.id)
                        console.log(`商家 ${merchant.id} 的商品:`, products) // 添加日志
                        merchant.products = products
                        carouselPositions.value[merchant.id] = 0
                    }

                    merchants.value = merchantsData.map(merchant => ({
                        ...merchant,
                        name: merchant.name || '未命名商家',
                        description: merchant.description || '暂无描述',
                        logo: merchant.logo ? baseUrl + merchant.logo : null,
                        type: merchant.type || '未知类型',
                        products: merchant.products || []
                    }))
                }
            } catch (error) {
                console.error('获取商家数据错误:', error)
                merchants.value = []
            } finally {
                isLoading.value = false
            }
        }

        // 轮播控制方法
        const prevSlide = (merchantId) => {
            const products = merchants.value.find(m => m.id === merchantId)?.products || []
            if (products.length <= 4) return  // 如果商品数量小于等于4，不需要滚动

            const maxPosition = Math.max(0, products.length - 4)
            carouselPositions.value[merchantId] = Math.max(0, (carouselPositions.value[merchantId] || 0) - 1)
        }

        const nextSlide = (merchantId) => {
            const products = merchants.value.find(m => m.id === merchantId)?.products || []
            if (products.length <= 4) return  // 如果商品数量小于等于4，不需要滚动

            const maxPosition = Math.max(0, products.length - 4)
            carouselPositions.value[merchantId] = Math.min(maxPosition, (carouselPositions.value[merchantId] || 0) + 1)
        }

        const getCarouselStyle = (merchantId) => {
            const position = carouselPositions.value[merchantId] || 0
            const slideWidth = (100 / 4) + 5  // 每个项目宽度（25%）加上间距
            return {
                transform: `translateX(-${position * slideWidth}%)`
            }
        }

        // 切换分类
        const changeCategory = async (categoryId) => {
            currentCategory.value = categoryId
            const category = categories.find(c => c.id === categoryId)
            if (category) {
                console.log('切换到分类:', category.type)
                await getMerchantsByType(category.type)
            }
        }

        // 进入商家店铺
        const goToStore = (seller) => {
            console.log('跳转到店铺:', seller)
            router.push({
                path: `/shop_sel_info1/${seller.id}`,
                query: {
                    name: seller.name
                }
            }).catch(err => {
                console.error('路由跳转错误:', err)
            })
        }

        // 页面加载时获取默认分类的商家数据
        onMounted(() => {
            const defaultCategory = categories.find(c => c.id === currentCategory.value)
            if (defaultCategory) {
                getMerchantsByType(defaultCategory.type)
            }
        })

        // 添加图片错误处理
        const handleImageError = (e) => {
            e.target.src = '默认图片路径' // 设置一个默认图片
            console.log('图片加载失败')
        }

        return {
            categories,
            currentCategory,
            merchants,
            changeCategory,
            goToStore,
            isLoading,
            baseUrl,
            prevSlide,
            nextSlide,
            getCarouselStyle,
            handleImageError
        }
    }
}
</script>

<style scoped>
.shop-sel {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 60px;
    /* 为固定导航栏留出空间 */
}

.nav-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 100;
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.main-content {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    gap: 20px;
}

/* 左侧分类栏样式 */
.category-sidebar {
    width: 200px;
    background: white;
    border-radius: 8px;
    padding: 15px;
}

.category-title {
    font-size: 18px;
    font-weight: bold;
    padding: 10px;
    border-bottom: 1px solid #eee;
}

.category-list {
    margin-top: 15px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.category-btn {
    width: 100%;
    padding: 10px;
    text-align: left;
    border: none;
    background: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    color: #333;
    transition: all 0.3s;
}

.category-btn:hover {
    background: #f5f5f5;
    color: #4CAF50;
}

.category-btn.active {
    background: #4CAF50;
    color: white;
}

/* 右侧商家列表样式 */
.seller-list {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.seller-card {
    background: white;
    border-radius: 16px;
    padding: 24px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.seller-card:hover {
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.seller-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.seller-main-info {
    display: flex;
    align-items: center;
    gap: 20px;
}

.seller-logo {
    width: 80px;
    height: 80px;
    background: linear-gradient(145deg, #f0f0f0, #ffffff);
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    display: flex;
    /* 添加 flex 布局 */
    align-items: center;
    justify-content: center;
    font-size: 24px;
    color: #4CAF50;
}

.seller-logo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.seller-info {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.seller-name {
    font-size: 20px;
    font-weight: 600;
    color: #333;
}

.seller-desc {
    font-size: 14px;
    color: #666;
    line-height: 1.4;
}

.seller-tags {
    display: flex;
    gap: 10px;
    margin-top: 4px;
}

.seller-type,
.seller-status {
    padding: 4px 12px;
    border-radius: 20px;
    font-size: 12px;
    font-weight: 500;
}

.seller-type {
    background: #e6f7ff;
    color: #1890ff;
}

.seller-status {
    background: #f6ffed;
    color: #52c41a;
}

.enter-store {
    padding: 10px 24px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s ease;
}

/* 商品展示区样式 */
.product-showcase {
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #f0f0f0;
}

.showcase-title {
    font-size: 16px;
    font-weight: 500;
    color: #333;
    margin-bottom: 16px;
}

.carousel-wrapper {
    position: relative;
    width: 100%;
    height: 200px;
    /* 调整高度 */
    background: #f8f9fa;
    border-radius: 12px;
    overflow: hidden;
    padding: 20px 0;
    /* 添加上下内边距 */
}

.carousel-track {
    display: flex;
    height: 100%;
    transition: transform 0.3s ease;
    gap: 20px;
    /* 增加图片间距 */
    padding: 0 20px;
}

.carousel-item {
    flex: 0 0 calc(25% - 15px);
    height: 100%;
    border-radius: 8px;
    overflow: hidden;
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    transition: box-shadow 0.3s ease;
}

.carousel-item:hover {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.product-image {
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-info {
    padding: 8px;
}

.product-name {
    font-size: 13px;
    color: #333;
    margin-bottom: 4px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.product-price {
    font-size: 14px;
    color: #f56c6c;
    font-weight: 500;
}

.carousel-btn {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.9);
    color: #333;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    z-index: 2;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.carousel-btn:hover {
    background: white;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.carousel-btn.prev {
    left: 10px;
}

.carousel-btn.next {
    right: 10px;
}

/* 添加加载状态和无结果状态的样式 */
.loading-state,
.no-results {
    text-align: center;
    padding: 50px;
    color: #666;
    font-size: 16px;
    background: white;
    border-radius: 12px;
    margin: 20px 0;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

/* 修改图片悬停效果 */
.carousel-item:hover .product-image img {
    transform: scale(1.1);
}
</style>