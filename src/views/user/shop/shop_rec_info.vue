<template>
    <div class="shop-info">
        <NavBar />
        
        <div class="main-content">
            <!-- 商品主图和信息 -->
            <div class="product-container">
                <!-- 左侧主图 -->
                <div class="product-image">
                    <div class="image-placeholder">图片</div>
                </div>
                
                <!-- 右侧信息 -->
                <div class="product-info">
                    <!-- 顶部按钮组 -->
                    <div class="info-buttons">
                        <button class="info-btn">产品名称</button>
                        <button class="info-btn">评分</button>
                    </div>
                    
                    <!-- 中间信息组 - 调整布局 -->
                    <div class="info-group">
                        <!-- 单价 -->
                        <div class="price-row">
                            <button class="info-btn">单价</button>
                        </div>
                        
                        <!-- 规格选择 -->
                        <div class="specs-row">
                            <div class="specs-group">
                                <button class="spec-btn">规格1</button>
                                <button class="spec-btn">规格2</button>
                                <button class="spec-btn">规格3</button>
                            </div>
                        </div>
                        
                        <!-- 数量 -->
                        <div class="quantity-row">
                            <button class="info-btn">数量</button>
                        </div>
                    </div>
                    
                    <!-- 底部购买按钮 -->
                    <div class="purchase-buttons">
                        <button class="cart-btn">添加到购物车</button>
                        <button class="buy-btn">直接购买</button>
                    </div>
                </div>
            </div>

            <!-- 产品介绍 -->
            <div class="section">
                <h2>产品介绍</h2>
                <div class="content-box">
                    内容
                </div>
            </div>

            <!-- 制造商信息 -->
            <div class="manufacturer-info">
                <div class="manufacturer-header">
                    <div class="logo">制造商logo</div>
                    <button class="subscribe-btn">订阅</button>
                </div>
                <div class="manufacturer-content">
                    <div class="info-item">
                        <span class="label">制造商名称：</span>
                        <span class="value">某某制造商</span>
                    </div>
                    <div class="info-item">
                        <span class="label">联系方式：</span>
                        <span class="value">xxx-xxxxxxxx</span>
                    </div>
                    <div class="info-item">
                        <span class="label">地址：</span>
                        <span class="value">某某省某某市某某区</span>
                    </div>
                </div>
            </div>

            <!-- 评论区 -->
            <div class="comments">
                <div class="comments-header">
                    <h2>评价</h2>
                </div>
                <!-- 评论列表 -->
                <div class="comment-list">
                    <div class="comment-item" v-for="i in 3" :key="i">
                        <div class="user-avatar">图片</div>
                        <div class="comment-content">
                            <div class="rating">评分</div>
                            <div class="text">内容</div>
                        </div>
                    </div>
                </div>

                <!-- 写评论区域 -->
                <div class="write-comment">
                    <div class="write-header">
                        <h3>打分</h3>
                    </div>
                    <div class="write-content">
                        <textarea placeholder="请输入评论内容" rows="6"></textarea>
                    </div>
                </div>
            </div>

            <!-- 相似商品 -->
            <div class="similar-products">
                <div class="header">
                    <h2>相似商品</h2>
                    <button class="refresh-btn" @click="refreshSimilarProducts">换一换</button>
                </div>
                <div class="products-grid">
                    <div class="product-item" 
                         v-for="item in currentSimilarProducts" 
                         :key="item.id"
                         @click="goToDetail(item.id)">
                        <div class="image-placeholder">{{ item.title }}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter, useRoute } from 'vue-router'
import { ref, computed } from 'vue'

export default {
    name: 'shop_rec_info',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const route = useRoute()
        const similarProductsIndex = ref(0)

        // 相似商品数据
        const similarProductsPool = [
            { id: 201, title: '相似商品1' },
            { id: 202, title: '相似商品2' },
            { id: 203, title: '相似商品3' },
            { id: 204, title: '相似商品4' },
            { id: 205, title: '相似商品5' },
            { id: 206, title: '相似商品6' },
            { id: 207, title: '相似商品7' },
            { id: 208, title: '相似商品8' },
            { id: 209, title: '相似商品9' }
        ]

        // 获取当前显示的相似商品
        const currentSimilarProducts = computed(() => {
            const startIndex = similarProductsIndex.value * 3
            return similarProductsPool.slice(startIndex, startIndex + 3)
        })

        // 跳转到商品详情
        const goToDetail = (id) => {
            // 如果当前页面的商品ID与点击的商品ID相同，不进行跳转
            if (route.params.id !== id.toString()) {
                router.push(`/shop_rec_info/${id}`)
            }
        }

        // 刷新相似商品列表
        const refreshSimilarProducts = () => {
            const maxIndex = Math.floor(similarProductsPool.length / 3)
            similarProductsIndex.value = (similarProductsIndex.value + 1) % maxIndex
        }

        return {
            currentSimilarProducts,
            goToDetail,
            refreshSimilarProducts
        }
    }
}
</script>

<style scoped>
.shop-rec-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.shop-info {
    width: 100%;
    min-height: 100vh;
}

.main-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

/* 商品主图和信息容器 */
.product-container {
    display: flex;
    gap: 40px;
    margin-bottom: 30px;
}

/* 左侧主图 */
.product-image {
    flex: 1;
    aspect-ratio: 1;
    background: #fff;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1px solid #ddd;
}

.image-placeholder {
    color: #666;
    font-size: 24px;
}

/* 右侧信息区域 */
.product-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

/* 中间信息组样式调整 */
.info-group {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

/* 单价行 */
.price-row {
    margin-bottom: 5px;
}

/* 规格行 */
.specs-row {
    margin: 10px 0;
}

/* 规格按钮组 */
.specs-group {
    display: flex;
    gap: 10px;
}

/* 数量行 */
.quantity-row {
    margin-top: 5px;
}

/* 按钮基础样式 */
.info-btn, .spec-btn {
    padding: 8px 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: white;
    cursor: pointer;
    font-size: 14px;
}

.spec-btn {
    padding: 8px 15px;
}

.spec-btn.active {
    border-color: #4CAF50;
    color: #4CAF50;
}

/* 顶部按钮组 */
.info-buttons {
    display: flex;
    gap: 15px;
}

/* 底部购买按钮 */
.purchase-buttons {
    display: flex;
    gap: 20px;
    margin-top: 20px;
}

.cart-btn, .buy-btn {
    flex: 1;
    padding: 12px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
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

/* 按钮悬停效果 */
.cart-btn:hover, .buy-btn:hover {
    opacity: 0.9;
}

.info-btn:hover, .spec-btn:hover {
    border-color: #4CAF50;
}

/* 产品介绍 */
.section {
    margin-bottom: 30px;
}

.content-box {
    background: white;
    padding: 20px;
    border-radius: 8px;
}

/* 制造商信息 */
.manufacturer-info {
    background: white;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 30px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.manufacturer-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 1px solid #f0f0f0;
}

.logo {
    font-size: 20px;
    font-weight: bold;
    color: #333;
}

.subscribe-btn {
    padding: 8px 24px;
    border: 1px solid #4CAF50;
    border-radius: 20px;
    background: white;
    color: #4CAF50;
    cursor: pointer;
    transition: all 0.3s;
    font-size: 14px;
}

.subscribe-btn:hover {
    background: #4CAF50;
    color: white;
}

.manufacturer-content {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.info-item {
    display: flex;
    align-items: center;
}

.label {
    width: 100px;
    color: #666;
    font-size: 14px;
}

.value {
    color: #333;
    font-size: 14px;
}

/* 评论区 */
.comments {
    background: white;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 30px;
}

.comments-header {
    border-bottom: 2px solid #f5f5f5;
    padding-bottom: 15px;
    margin-bottom: 20px;
}

.comment-list {
    display: flex;
    flex-direction: column;  /* 改为竖向排列 */
    gap: 20px;
    margin-bottom: 30px;
}

.comment-item {
    display: flex;
    gap: 15px;
    padding: 15px;
    border-bottom: 1px solid #f5f5f5;
}

.user-avatar {
    width: 50px;
    height: 50px;
    background: #f5f5f5;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.comment-content {
    flex: 1;
}

.rating {
    font-weight: bold;
    margin-bottom: 10px;
}

.text {
    color: #666;
    line-height: 1.5;
}

/* 写评论区域样式 */
.write-comment {
    border-top: 2px solid #f5f5f5;
    padding-top: 20px;
}

.write-header {
    margin-bottom: 15px;
}

.write-content textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    resize: vertical;
    min-height: 120px;
    font-size: 14px;
}

.write-content textarea:focus {
    outline: none;
    border-color: #4CAF50;
}

/* 相似商品 */
.similar-products {
    margin-bottom: 30px;
}

.similar-products .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.refresh-btn {
    padding: 6px 15px;
    border: 1px solid #4CAF50;
    border-radius: 15px;
    background: white;
    color: #4CAF50;
    cursor: pointer;
    transition: all 0.3s;
    font-size: 14px;
}

.refresh-btn:hover {
    background: #4CAF50;
    color: white;
}

.products-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}

.product-item {
    background: white;
    border-radius: 8px;
    padding: 20px;
    border: 1px solid #ddd;
    cursor: pointer;
    transition: all 0.3s;
}

.product-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}
</style>