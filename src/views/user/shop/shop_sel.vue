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
                        </div>
                        <button class="enter-store" @click="goToStore(seller)">进店</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../../utils/request'

export default {
    name: 'shop_sel',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const baseUrl = 'http://localhost:8080/image/'
        const currentCategory = ref(1)
        const isLoading = ref(false)
        const merchants = ref([])

        // 分类数据
        const categories = [
            { id: 1, name: '果蔬', type: '果蔬' },
            { id: 2, name: '肉类', type: '肉类' },
            { id: 3, name: '饮料', type: '饮料' },
            { id: 4, name: '其他', type: '其他' }
        ]

        // 获取商家数据
        const getMerchantsByType = async (type) => {
            try {
                isLoading.value = true
                console.log('发送请求的type值:', type)

                const response = await request.get('/merchant/findMerchantByType', {
                    params: { type: type }
                })
                console.log('商家数据响应:', response)

                if (response.data && response.data.code === 200) {
                    merchants.value = response.data.data.map(merchant => ({
                        id: merchant.id,
                        username: merchant.username,
                        name: merchant.name,
                        description: merchant.description || '暂无描述',
                        logo: baseUrl + merchant.logo,
                        type: merchant.type
                    }))
                    console.log('处理后的商家数据:', merchants.value)
                } else {
                    merchants.value = []
                    console.log('获取商家数据失败:', response.data)
                }
            } catch (error) {
                console.error('获取商家数据错误:', error)
                merchants.value = []
            } finally {
                isLoading.value = false
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
            router.push({
                name: 'shop_sel_info1',
                params: { username: seller.username }
            })
        }

        // 页面加载时获取默认分类的商家数据
        onMounted(() => {
            const defaultCategory = categories.find(c => c.id === currentCategory.value)
            if (defaultCategory) {
                getMerchantsByType(defaultCategory.type)
            }
        })

        return {
            categories,
            currentCategory,
            merchants,
            changeCategory,
            goToStore,
            isLoading,
            baseUrl
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
    border-radius: 12px;
    padding: 25px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.seller-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.seller-header {
    display: flex;
    align-items: center;
    gap: 25px;
}

.seller-logo {
    width: 90px;
    height: 90px;
    background: linear-gradient(145deg, #f0f0f0, #ffffff);
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 12px;
    overflow: hidden;
    font-size: 28px;
    color: #4CAF50;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.seller-logo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.seller-logo:hover img {
    transform: scale(1.05);
}

.seller-info {
    flex: 1;
    padding: 0 15px;
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.seller-name {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin-bottom: 5px;
}

.seller-desc {
    font-size: 14px;
    color: #666;
    line-height: 1.6;
    margin-bottom: 8px;
}

.seller-type {
    display: inline-block;
    padding: 4px 12px;
    background: #f0f7f0;
    color: #4CAF50;
    border-radius: 15px;
    font-size: 13px;
    font-weight: 500;
}

.enter-store {
    padding: 10px 25px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 15px;
    font-weight: 500;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(76, 175, 80, 0.2);
}

.enter-store:hover {
    background: #43a047;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
}

.seller-carousel {
    width: 100%;
    height: 200px;
    background: #f0f0f0;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.carousel-placeholder {
    color: #666;
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
</style>