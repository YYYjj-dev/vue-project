<template>
    <div class="shop-info">
        <NavBar />
        
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
                        <img :src="`http://localhost:8080/image${productInfo.img}`" 
                             :alt="productInfo.name"
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
                            
                            <!-- 库存数量 -->
                            <div class="quantity-row">
                                <span class="quantity-label">库存:</span>
                                <span class="quantity-value">{{ productInfo.num }}</span>
                            </div>

                            <!-- 商品类型 -->
                            <div class="type-row">
                                <span class="type-label">类型:</span>
                                <span class="type-value">{{ productInfo.type }}</span>
                            </div>

                            <!-- 商品分组 -->
                            <div class="group-row">
                                <span class="group-label">分组:</span>
                                <span class="group-value">{{ productInfo.group }}</span>
                            </div>

                            <!-- 店铺ID -->
                            <div class="store-row">
                                <span class="store-label">店铺ID:</span>
                                <span class="store-value">{{ productInfo.storeId }}</span>
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
            img: '',
            score: 0,
            num: 0,
            standard: '',
            type: '',
            group: '',
            storeId: ''
        })

        // 获取商品详情
        const getProductInfo = async () => {
            try {
                isLoading.value = true
                const response = await request.get(`/shangpin/findShangpinById?id=${route.params.id}`)
                console.log('请求URL:', `/shangpin/findShangpinById?id=${route.params.id}`)
                console.log('响应数据:', response)
                
                if (response.data && response.data.data) {
                    productInfo.value = response.data.data
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
}

.main-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.product-container {
    display: flex;
    gap: 40px;
    background: white;
    padding: 30px;
    border-radius: 8px;
    margin-bottom: 20px;
}

.product-image {
    flex: 1;
    max-width: 500px;
}

.product-image img {
    width: 100%;
    height: auto;
    border-radius: 8px;
}

.product-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.product-name {
    font-size: 24px;
    margin: 0;
    color: #333;
}

.score-badge {
    display: inline-block;
    padding: 4px 12px;
    background: #ff9800;
    color: white;
    border-radius: 4px;
    font-size: 14px;
}

.info-group {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.price-row, .specs-row, .quantity-row, .type-row, .group-row {
    display: flex;
    align-items: center;
    gap: 10px;
}

.price-value {
    font-size: 24px;
    color: #f44336;
    font-weight: bold;
}

.specs-value, .quantity-value, .type-value, .group-value {
    color: #666;
}

.purchase-buttons {
    display: flex;
    gap: 20px;
    margin-top: 30px;
}

.cart-btn, .buy-btn {
    flex: 1;
    padding: 12px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    transition: all 0.3s;
}

.cart-btn {
    background: white;
    border: 1px solid #4CAF50;
    color: #4CAF50;
}

.buy-btn {
    background: #4CAF50;
    color: white;
}

.cart-btn:hover, .buy-btn:hover {
    opacity: 0.9;
}

.description-section {
    background: white;
    padding: 20px;
    border-radius: 8px;
}

.description-section h3 {
    margin: 0 0 15px 0;
    color: #333;
}

.description-content {
    color: #666;
    line-height: 1.6;
}

[class$="-label"] {
    color: #999;
    min-width: 60px;
}

.loading-state {
    text-align: center;
    padding: 40px;
    font-size: 18px;
    color: #666;
}

.error-state {
    text-align: center;
    padding: 40px;
    font-size: 18px;
    color: #f44336;
}
</style>