<template>
    <div class="shop-info">
        <!-- 添加导航栏包装器 -->
        <div class="nav-wrapper">
            <NavBar />
        </div>
        
        <div class="main-content" v-if="productData">
            <!-- 商品主图和信息 -->
            <div class="product-container">
                <!-- 左侧主图 -->
                <div class="product-image">
                    <img :src="productData.imgpath" :alt="productData.name">
                </div>
                
                <!-- 右侧信息 -->
                <div class="product-info">
                    <!-- 商品标题 -->
                    <h1 class="product-title">{{ productData.name }}</h1>
                    
                    <!-- 评分 -->
                    <div class="product-score">
                        <span class="score">{{ productData.score || '暂无评分' }}</span>
                    </div>
                    
                    <!-- 商品详细信息 -->
                    <div class="product-details">
                        <!-- 价格 -->
                        <div class="detail-item">
                            <span class="label">价格</span>
                            <span class="price">￥{{ productData.price }}</span>
                        </div>
                        
                        <!-- 规格 -->
                        <div class="detail-item">
                            <span class="label">规格</span>
                            <span class="spec">{{ productData.spec }}</span>
                        </div>
                        
                        <!-- 数量选择 -->
                        <div class="detail-item">
                            <span class="label">数量</span>
                            <div class="quantity-control">
                                <button class="quantity-btn" 
                                        @click="decreaseQuantity"
                                        :disabled="quantity <= 1">-</button>
                                <span class="quantity">{{ quantity }}</span>
                                <button class="quantity-btn" 
                                        @click="increaseQuantity"
                                        :disabled="quantity >= 99">+</button>
                            </div>
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
                    {{ productData.description }}
                </div>
            </div>

            <!-- 制造商信息 -->
            <div class="manufacturer-info" v-if="storeData">
                <div class="manufacturer-header">
                    <div class="manufacturer-basic">
                        <div class="logo">
                            <img :src="storeData.imgpath" :alt="storeData.name">
                        </div>
                        <div class="manufacturer-name">
                            {{ storeData.name }}
                        </div>
                    </div>
                    <button class="subscribe-btn">订阅</button>
                </div>
                <div class="manufacturer-content">
                    <div class="info-item">
                        <span class="info-value">{{ storeData.description }}</span>
                    </div>
                </div>
            </div>

            <!-- 评论区 -->
            <div class="comments" v-if="productData">
                <div class="comments-header">
                    <h2>评价 ({{ comments.length }})</h2>
                </div>
                <!-- 评论列表 -->
                <div class="comment-list">
                    <div class="comment-item" v-for="comment in comments" :key="comment.id">
                        <div class="user-avatar">
                            <img :src="comment.userAvatar || '/default-avatar.png'" :alt="comment.userName">
                        </div>
                        <div class="comment-content">
                            <div class="comment-header">
                                <span class="user-name">{{ comment.userName }}</span>
                                <span class="comment-date">{{ comment.date }}</span>
                            </div>
                            <div class="rating">
                                <span class="score">{{ comment.score }}分</span>
                            </div>
                            <div class="text">{{ comment.content }}</div>
                        </div>
                    </div>
                </div>

                <!-- 写评论区域 -->
                <div class="write-comment">
                    <div class="write-header">
                        <h3>评价商品</h3>
                        <div class="rating-input">
                            <span class="label">评分：</span>
                            <input type="number" v-model="newComment.score" min="1" max="5" step="1">
                        </div>
                    </div>
                    <div class="write-content">
                        <textarea 
                            v-model="newComment.content"
                            placeholder="请输入评论内容" 
                            rows="6">
                        </textarea>
                        <button class="submit-comment" @click="submitComment">提交评价</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import request from '../../../utils/request'

export default {
    name: 'shop_rec_info',
    components: {
        NavBar
    },
    data() {
        return {
            productData: null,
            quantity: 1,
            storeData: null,
            comments: [],  // 评论列表
            newComment: {  // 新评论
                score: 5,
                content: ''
            }
        }
    },
    created() {
        const productId = this.$route.params.id;
        console.log('商品ID:', productId);
        this.loadProductInfo(productId);
        this.loadComments(productId);  // 加载评论数据
    },
    methods: {
        loadProductInfo(id) {
            console.log('开始请求商品详情，ID:', id);
            
            request.get('/shangpin')
                .then(res => {
                    console.log('获取到的响应:', res);
                    
                    if(res.code === '0') {
                        // 确保使用数字类型进行比较
                        const productItem = res.data.find(item => Number(item.id) === Number(id));
                        if (productItem) {
                            this.productData = productItem;
                            console.log('找到的商品数据:', this.productData);
                            // 检查并加载制造商信息
                            if (productItem.storeId) {
                                console.log('商品关联的storeId:', productItem.storeId);
                                this.loadStoreInfo(productItem.storeId);
                            } else {
                                console.warn('商品没有关联的storeId');
                            }
                        } else {
                            console.error('未找到对应ID的商品:', id);
                        }
                    } else {
                        console.error('获取商品详情失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求商品详情出错:', error);
                });
        },
        // 添加数量控制方法
        increaseQuantity() {
            if (this.quantity < 99) {
                this.quantity++;
            }
        },
        decreaseQuantity() {
            if (this.quantity > 1) {
                this.quantity--;
            }
        },
        // 添加加载制造商信息的方法
        loadStoreInfo(storeId) {
            console.log('开始请求制造商信息，storeId:', storeId);
            
            request.get('/store')
                .then(res => {
                    console.log('获取到的制造商响应:', res);
                    
                    if(res.code === '0') {
                        // 确保使用数字类型进行比较
                        const storeItem = res.data.find(item => Number(item.id) === Number(storeId));
                        if (storeItem) {
                            this.storeData = storeItem;
                            console.log('找到的制造商数据:', this.storeData);
                        } else {
                            console.error('未找到对应storeId的制造商:', storeId);
                        }
                    } else {
                        console.error('获取制造商信息失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求制造商信息出错:', error);
                });
        },
        // 加载评论数据
        loadComments(productId) {
            console.log('开始请求商品评论，商品ID:', productId);
            
            request.get(`/comment/shangpin/${productId}`)
                .then(res => {
                    console.log('获取到的评论响应:', res);
                    
                    if(res.code === '0') {
                        this.comments = res.data;
                        console.log('处理后的评论数据:', this.comments);
                    } else {
                        console.error('获取评论失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求评论出错:', error);
                });
        },
        
        // 提交评论
        submitComment() {
            if (!this.newComment.content.trim()) {
                alert('请输入评论内容');
                return;
            }
            
            const commentData = {
                productId: this.productData.id,
                score: this.newComment.score,
                content: this.newComment.content
            };
            
            request.post('/comment/product', commentData)
                .then(res => {
                    if(res.code === '0') {
                        alert('评论成功');
                        this.newComment.content = '';  // 清空评论内容
                        this.loadComments(this.productData.id);  // 重新加载评论
                    } else {
                        alert('评论失败：' + res.msg);
                    }
                })
                .catch(error => {
                    console.error('提交评论出错:', error);
                    alert('评论失败，请稍后重试');
                });
        }
    }
}
</script>

<style scoped>
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

.shop-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding-top: 60px; /* 为固定导航栏留出空间 */
}

.main-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px 20px 40px; /* 调整上边距，因为已经有了导航栏的padding */
}

/* 商品主图和信息容器 */
.product-container {
    display: flex;
    gap: 60px;
    margin-bottom: 40px;
    background: white;
    padding: 40px;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

/* 左侧主图 */
.product-image {
    flex: 0 0 500px;
    height: 500px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.product-image:hover img {
    transform: scale(1.05);
}

/* 右侧信息区域 */
.product-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 30px;
}

/* 按钮样式 */
.info-buttons {
    display: flex;
    gap: 15px;
    margin-bottom: 20px;
}

.info-btn {
    padding: 12px 24px;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    font-weight: 500;
    background: #f5f5f5;
    color: #333;
}

.info-btn:first-child {
    background: #42b983;
    color: white;
}

/* 价格样式 */
.price-row .info-btn {
    font-size: 24px;
    color: #ff6b6b;
    background: #fff0f0;
    font-weight: 600;
}

/* 规格选择 */
.specs-group {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

.spec-btn {
    padding: 10px 20px;
    border: 1px solid #ddd;
    border-radius: 6px;
    background: white;
    cursor: pointer;
    transition: all 0.3s ease;
}

.spec-btn:hover {
    border-color: #42b983;
    color: #42b983;
}

.spec-btn.active {
    background: #42b983;
    color: white;
    border-color: #42b983;
}

/* 购买按钮 */
.purchase-buttons {
    display: flex;
    gap: 20px;
    margin-top: auto;
}

.cart-btn, .buy-btn {
    flex: 1;
    padding: 15px 0;
    border-radius: 8px;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
}

.cart-btn {
    background: white;
    border: 2px solid #42b983;
    color: #42b983;
}

.buy-btn {
    background: #42b983;
    border: none;
    color: white;
}

.cart-btn:hover, .buy-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

/* 产品介绍部分 */
.section {
    background: white;
    border-radius: 16px;
    padding: 30px;
    margin-bottom: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.section h2 {
    font-size: 20px;
    color: #2c3e50;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 2px solid #f0f0f0;
    position: relative;
}

.section h2::after {
    content: '';
    position: absolute;
    bottom: -2px;
    left: 0;
    width: 60px;
    height: 2px;
    background: #42b983;
}

.content-box {
    line-height: 1.8;
    color: #666;
    font-size: 15px;
}

/* 响应式调整 */
@media (max-width: 1024px) {
    .product-container {
        gap: 40px;
        padding: 30px;
    }

    .product-image {
        flex: 0 0 400px;
        height: 400px;
    }
}

@media (max-width: 768px) {
    .product-container {
        flex-direction: column;
        gap: 30px;
    }

    .product-image {
        width: 100%;
        height: 300px;
    }

    .info-buttons {
        flex-wrap: wrap;
    }

    .purchase-buttons {
        flex-direction: column;
    }
}

/* 制造商信息 */
.manufacturer-info {
    background: white;
    border-radius: 16px;
    padding: 30px;
    margin-bottom: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.manufacturer-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.manufacturer-basic {
    display: flex;
    align-items: center;
    gap: 20px;
}

.logo {
    width: 80px;
    height: 80px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.logo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.manufacturer-name {
    font-size: 20px;
    font-weight: 600;
    color: #2c3e50;
}

.manufacturer-content {
    padding-top: 10px;
}

.info-value {
    color: #666;
    font-size: 14px;
    line-height: 1.8;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .manufacturer-info {
        padding: 20px;
    }

    .manufacturer-basic {
        gap: 15px;
    }

    .logo {
        width: 60px;
        height: 60px;
    }

    .manufacturer-name {
        font-size: 18px;
    }
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

/* 商品标题样式 */
.product-title {
    font-size: 28px;
    color: #2c3e50;
    margin: 0 0 15px;
    font-weight: 600;
    line-height: 1.4;
}

/* 评分样式 */
.product-score {
    margin-bottom: 30px;
}

.score {
    padding: 6px 12px;
    background: #f5f5f5;
    border-radius: 4px;
    color: #666;
    font-size: 14px;
}

/* 商品详细信息样式 */
.product-details {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 30px 0;
    border-top: 1px solid #eee;
    border-bottom: 1px solid #eee;
    margin-bottom: 30px;
}

.detail-item {
    display: flex;
    align-items: center;
    gap: 20px;
}

.label {
    width: 60px;
    color: #666;
    font-size: 15px;
}

.price {
    color: #ff6b6b;
    font-size: 24px;
    font-weight: 600;
}

.spec {
    color: #2c3e50;
    font-size: 15px;
}

/* 数量选择器样式 */
.quantity-control {
    display: flex;
    align-items: center;
    gap: 15px;
}

.quantity-btn {
    width: 32px;
    height: 32px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: #666;
    transition: all 0.3s ease;
}

.quantity-btn:not(:disabled):hover {
    border-color: #42b983;
    color: #42b983;
}

.quantity-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.quantity {
    font-size: 16px;
    color: #2c3e50;
    min-width: 40px;
    text-align: center;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .product-title {
        font-size: 24px;
    }
    
    .product-details {
        padding: 20px 0;
        gap: 15px;
    }
    
    .price {
        font-size: 20px;
    }
}

/* 修改订阅按钮样式 */
.subscribe-btn {
    padding: 8px 20px;
    border: 1px solid #42b983;
    border-radius: 20px;
    background: white;
    color: #42b983;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
    gap: 5px;
}

.subscribe-btn::before {
    content: '+';
    font-size: 16px;
    font-weight: 500;
}

.subscribe-btn:hover {
    background: #42b983;
    color: white;
    transform: translateY(-1px);
    box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
}

.subscribe-btn:active {
    transform: translateY(0);
    box-shadow: none;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .subscribe-btn {
        padding: 6px 16px;
        font-size: 13px;
    }
}

/* 响应式调整 */
@media (max-width: 768px) {
    .shop-info {
        padding-top: 50px; /* 移动端导航栏可能更小 */
    }
    
    .main-content {
        padding: 15px 15px 30px;
    }
}

/* 评论样式优化 */
.comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
}

.user-name {
    font-weight: 500;
    color: #2c3e50;
}

.comment-date {
    font-size: 12px;
    color: #999;
}

.user-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
}

.rating-input {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-top: 10px;
}

.rating-input input {
    width: 60px;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.submit-comment {
    margin-top: 15px;
    padding: 10px 25px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.submit-comment:hover {
    background: #3aa876;
    transform: translateY(-1px);
}

.score {
    display: inline-block;
    padding: 4px 8px;
    background: #f8f9fa;
    border-radius: 4px;
    color: #ff6b6b;
    font-weight: 500;
}
</style>