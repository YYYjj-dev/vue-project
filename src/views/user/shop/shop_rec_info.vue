<template>
    <div class="shop-info">
        <!-- 导航栏 -->
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="main-content">
            <div v-if="isLoading" class="loading-state">
                加载中...
            </div>

            <div v-else-if="!productInfo.id" class="error-state">
                未找到商品信息
            </div>

            <div v-else>
                <div class="product-container">
                    <!-- 左侧主图 -->
                    <div class="product-image">
                        <img :src="productInfo.imgpath" :alt="productInfo.name"
                            @error="e => e.target.src = '/default-image.jpg'" />
                    </div>

                    <!-- 右侧信息 -->
                    <div class="product-info">
                        <div class="info-buttons">
                            <h2 class="product-name">{{ productInfo.name }}</h2>
                            <div class="score-badge">评分: {{ productInfo.score }}</div>
                        </div>

                        <div class="info-group">
                            <div class="price-row">
                                <span class="price-label">价格:</span>
                                <span class="price-value">￥{{ productInfo.price }}</span>
                            </div>

                            <!-- 商品规格 -->
                            <div class="specs-row">
                                <span class="specs-label">规格:</span>
                                <div class="specs-value">{{ productInfo.standard }}</div>
                            </div>

                            <!-- 销量 -->
                            <div class="quantity-row">
                                <span class="quantity-label">销量:</span>
                                <span class="quantity-value">{{ productInfo.num }}</span>
                            </div>

                        </div>

                        <!-- 底部购买按钮 -->
                        <div class="purchase-buttons">
                            <button class="cart-btn">添加到购物车</button>
                            <button class="buy-btn">直接购买</button>
                        </div>
                    </div>
                </div>

                <!-- 商品描述 -->
                <div class="description-section">
                    <h3>商品描述</h3>
                    <div class="description-content">
                        {{ productInfo.description }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted, computed, watch } from 'vue'
import request from '../../../utils/request'

export default {
    name: 'shop_rec_info',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const route = useRoute()
        const isLoading = ref(true)

        // 商品数据的响应式引用
        const productInfo = ref({
            id: null,
            name: '',
            price: 0,
            description: '',
            imgpath: '',
            score: 0,
            num: 0,
            standard: '',
            type: '',
            group: '',
            storeId: ''
        })

        // 在 setup 函数中添加 baseUrl 常量
        const baseUrl = 'http://localhost:8080/image/'

        // 获取商品详情
        const getProductInfo = async () => {
            try {
                isLoading.value = true
                const response = await request.get(`/shangpin/findShangpinById?id=${route.params.id}`)

                if (response.data && response.data.data) {
                    // 处理图片路径
                    const data = response.data.data
                    productInfo.value = {
                        ...data,
                        imgpath: baseUrl + data.imgpath // 拼接完整的图片URL
                    }
                    console.log('处理后的商品详情:', productInfo.value)
                } else {
                    console.error('响应数据格式不正确:', response)
                }
            } catch (error) {
                console.error('获取商品信息失败:', error)
                console.error('错误详情:', {
                    message: error.message,
                    status: error.response?.status,
                    data: error.response?.data
                })
            } finally {
                isLoading.value = false
            }
        }

        // 监听路由参数变化
        watch(() => route.params.id, (newId) => {
            if (newId) {
                getProductInfo()
            }
        })

        onMounted(() => {
            if (route.params.id) {
                getProductInfo()
            }
        })

        return {
            productInfo,
            isLoading
        }
    }
}
</script>

<style scoped>
.shop-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding-top: 80px;
}

/* 添加导航栏包装器样式 */
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
    max-width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
}

.product-container {
    display: flex;
    gap: 50px;
    background: white;
    padding: 40px;
    border-radius: 16px;
    margin-bottom: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.product-image {
    flex: 1;
    max-width: 500px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 12px;
    transition: transform 0.3s ease;
}

.product-image img:hover {
    transform: scale(1.02);
}

.product-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 25px;
}

.info-buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.product-name {
    font-size: 28px;
    margin: 0;
    color: #333;
    font-weight: 600;
    line-height: 1.4;
}

.score-badge {
    display: inline-block;
    padding: 6px 16px;
    background: linear-gradient(45deg, #ff9800, #ff5722);
    color: white;
    border-radius: 20px;
    font-size: 15px;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(255, 152, 0, 0.3);
}

.info-group {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.price-row,
.specs-row,
.quantity-row {
    display: flex;
    align-items: center;
    gap: 15px;
    padding: 10px 0;
}

[class$="-label"] {
    color: #666;
    min-width: 70px;
    font-size: 15px;
}

.price-value {
    font-size: 32px;
    color: #ff6b6b;
    font-weight: 600;
}

.specs-value,
.quantity-value {
    color: #444;
    font-size: 15px;
    line-height: 1.6;
}

.purchase-buttons {
    display: flex;
    gap: 20px;
    margin-top: auto;
}

.cart-btn,
.buy-btn {
    flex: 1;
    padding: 14px 0;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    transition: all 0.3s ease;
}

.cart-btn {
    background: white;
    border: 2px solid #4CAF50;
    color: #4CAF50;
}

.cart-btn:hover {
    background: rgba(76, 175, 80, 0.1);
    transform: translateY(-2px);
}

.buy-btn {
    background: #4CAF50;
    color: white;
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.2);
}

.buy-btn:hover {
    background: #43a047;
    transform: translateY(-2px);
}

.description-section {
    background: white;
    padding: 30px;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.description-section h3 {
    font-size: 20px;
    margin: 0 0 20px 0;
    color: #333;
    font-weight: 600;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.description-content {
    color: #555;
    line-height: 1.8;
    font-size: 15px;
}

.loading-state,
.error-state {
    text-align: center;
    padding: 60px;
    font-size: 18px;
    color: #666;
    background: white;
    border-radius: 16px;
    margin: 40px 0;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.error-state {
    color: #f44336;
}

/* 响应式设计 */
@media (max-width: 1024px) {
    .product-container {
        padding: 30px;
        gap: 30px;
    }
}

@media (max-width: 768px) {
    .product-container {
        flex-direction: column;
    }

    .product-image {
        max-width: 100%;
    }

    .product-name {
        font-size: 24px;
    }

    .price-value {
        font-size: 28px;
    }

    .purchase-buttons {
        margin-top: 20px;
    }

    .shop-info {
        padding-top: 70px;
    }

    .main-content {
        margin: 30px auto;
    }
}

@media (max-width: 480px) {
    .main-content {
        padding: 0 15px;
    }

    .product-container {
        padding: 20px;
    }

    .info-buttons {
        flex-direction: column;
        align-items: flex-start;
        gap: 15px;
    }

    .score-badge {
        align-self: flex-start;
    }

    .purchase-buttons {
        flex-direction: column;
    }

    .shop-info {
        padding-top: 60px;
    }

    .main-content {
        margin: 20px auto;
    }
}
</style>