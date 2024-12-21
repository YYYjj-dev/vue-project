<template>
    <div class="merchant-center">
        <NavBar />
        
        <div class="content-container">
            <!-- 商家信息头部 -->
            <div class="merchant-header">
                <div class="merchant-info">
                    <div class="merchant-avatar"></div>
                    <div class="merchant-details">
                        <div class="merchant-header-top">
                            <h2 class="merchant-name">店铺名称</h2>
                            <span class="merchant-status">已认证</span>
                        </div>
                        <p class="merchant-type">经营类别：食品添加剂</p>
                    </div>
                </div>
            </div>

            <!-- 功能区域 -->
            <div class="function-area">
                <!-- 标签页切换 -->
                <div class="tabs">
                    <button 
                        :class="['tab-btn', { active: activeTab === 'products' }]"
                        @click="activeTab = 'products'"
                    >
                        商品管理
                    </button>
                    <button 
                        :class="['tab-btn', { active: activeTab === 'orders' }]"
                        @click="activeTab = 'orders'"
                    >
                        订单管理
                    </button>
                    <button 
                        :class="['tab-btn', { active: activeTab === 'shop' }]"
                        @click="activeTab = 'shop'"
                    >
                        店铺管理
                    </button>
                </div>

                <!-- 商品管理 -->
                <div v-if="activeTab === 'products'" class="products-list">
                    <div class="section-header">
                        <button class="add-btn" @click="addProduct">添加商品</button>
                    </div>
                    <div class="product-item" v-for="product in products" :key="product.id">
                        <div class="product-image"></div>
                        <div class="product-info">
                            <h3 class="product-name">{{ product.name }}</h3>
                            <p class="product-spec">{{ product.specification }}</p>
                            <div class="product-meta">
                                <span class="price">¥{{ product.price }}</span>
                                <span class="stock">库存：{{ product.stock }}</span>
                            </div>
                            <div class="product-actions">
                                <button class="action-btn" @click="editProduct(product.id)">编辑</button>
                                <button class="action-btn delete" @click="deleteProduct(product.id)">下架</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 订单管理 -->
                <div v-if="activeTab === 'orders'" class="orders-list">
                    <div class="order-item" v-for="order in orders" :key="order.id">
                        <div class="order-header">
                            <span class="order-number">订单号：{{ order.orderNumber }}</span>
                            <span class="order-status">{{ order.status }}</span>
                        </div>
                        <div class="order-content">
                            <div class="product-info">
                                <div class="product-image"></div>
                                <div class="product-details">
                                    <h3 class="product-name">{{ order.productName }}</h3>
                                    <p class="product-spec">{{ order.specification }}</p>
                                    <span class="product-price">¥{{ order.price }}</span>
                                </div>
                            </div>
                            <div class="order-footer">
                                <span class="order-time">下单时间：{{ order.orderTime }}</span>
                                <div class="order-actions">
                                    <button class="action-btn" @click="processOrder(order.id)">处理订单</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 店铺管理 -->
                <div v-if="activeTab === 'shop'" class="shop-management">
                    <div class="shop-info-form">
                        <div class="form-group">
                            <label>店铺名称</label>
                            <input type="text" v-model="shopInfo.name">
                        </div>
                        <div class="form-group">
                            <label>经营类别</label>
                            <select v-model="shopInfo.category">
                                <option value="1">食品添加剂</option>
                                <option value="2">食品原料</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>店铺简介</label>
                            <textarea v-model="shopInfo.description"></textarea>
                        </div>
                        <div class="form-group">
                            <label>联系电话</label>
                            <input type="tel" v-model="shopInfo.phone">
                        </div>
                        <button class="save-btn" @click="saveShopInfo">保存修改</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'

export default {
    name: 'merchant_center',
    components: {
        NavBar
    },
    data() {
        return {
            activeTab: 'products',
            products: [
                {
                    id: 1,
                    name: '商品名称1',
                    specification: '规格：500ml',
                    price: '299.00',
                    stock: 100
                },
                {
                    id: 2,
                    name: '商品名称2',
                    specification: '规格：200ml',
                    price: '199.00',
                    stock: 50
                }
            ],
            orders: [
                {
                    id: 1,
                    orderNumber: 'ORDER20240120001',
                    status: '待发货',
                    productName: '商品名称1',
                    specification: '规格：500ml',
                    price: '299.00',
                    orderTime: '2024-01-20'
                }
            ],
            shopInfo: {
                name: '添加剂专营店',
                category: '1',
                description: '专业经营各类食品添加剂，质量保证，价格实惠',
                phone: '13800138000'
            }
        }
    },
    methods: {
        addProduct() {
            // 添加商品
            console.log('添加商品')
        },
        editProduct(id) {
            // 编辑商品
            console.log('编辑商品:', id)
        },
        deleteProduct(id) {
            // 下架商品
            console.log('下架商品:', id)
        },
        processOrder(id) {
            // 处理订单
            console.log('处理订单:', id)
        },
        saveShopInfo() {
            // 保存店铺信息
            console.log('保存店铺信息:', this.shopInfo)
        }
    }
}
</script>

<style scoped>
/* 基础布局样式 */
.merchant-center {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 商家头部样式优化 */
.merchant-header {
    background: white;
    border-radius: 16px;
    padding: 30px;
    margin-bottom: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.merchant-info {
    display: flex;
    align-items: center;
    gap: 20px;
}

.merchant-avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: #f0f0f0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.merchant-header-top {
    display: flex;
    align-items: center;
    gap: 15px;
}

.merchant-name {
    font-size: 1.5rem;
    color: #2c3e50;
    margin: 0;
    font-weight: 600;
}

.merchant-status {
    padding: 4px 12px;
    background: #e1f3d8;
    color: #67c23a;
    border-radius: 12px;
    font-size: 0.9rem;
    font-weight: 500;
}

.merchant-type {
    color: #666;
    margin-top: 8px;
}

/* 标签页样式优化 */
.tabs {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.tab-btn {
    padding: 10px 20px;
    border: none;
    background: none;
    color: #666;
    font-size: 1rem;
    cursor: pointer;
    position: relative;
    transition: all 0.3s ease;
}

.tab-btn.active {
    color: #42b983;
    font-weight: 500;
}

.tab-btn.active::after {
    content: '';
    position: absolute;
    bottom: -11px;
    left: 0;
    width: 100%;
    height: 2px;
    background: #42b983;
}

/* 商品列表样式优化 */
.products-list {
    display: grid;
    gap: 20px;
}

.section-header {
    margin-bottom: 20px;
}

.add-btn {
    padding: 10px 24px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.3s ease;
}

.add-btn:hover {
    background: #3aa876;
    transform: translateY(-2px);
}

.product-item {
    display: flex;
    gap: 20px;
    padding: 20px;
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
}

.product-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.product-image {
    width: 120px;
    height: 120px;
    border-radius: 8px;
    background: #f0f0f0;
    object-fit: cover;
}

.product-info {
    flex: 1;
}

.product-name {
    font-size: 1.2rem;
    color: #2c3e50;
    margin: 0 0 8px 0;
}

.product-spec {
    color: #666;
    margin-bottom: 12px;
}

.product-meta {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}

.price {
    color: #f56c6c;
    font-size: 1.2rem;
    font-weight: 500;
}

.stock {
    color: #666;
}

.product-actions {
    display: flex;
    gap: 10px;
}

.action-btn {
    padding: 6px 16px;
    border-radius: 15px;
    border: 1px solid #ddd;
    background: white;
    color: #666;
    cursor: pointer;
    transition: all 0.3s ease;
}

.action-btn:hover {
    border-color: #42b983;
    color: #42b983;
}

.action-btn.delete {
    color: #f56c6c;
    border-color: #f56c6c;
}

.action-btn.delete:hover {
    background: #f56c6c;
    color: white;
}

/* 订单列表样式优化 */
.order-item {
    background: white;
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.order-number {
    color: #666;
}

.order-status {
    color: #42b983;
    font-weight: 500;
}

/* 店铺管理表单样式优化 */
.shop-info-form {
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    color: #2c3e50;
    font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1rem;
    transition: all 0.3s ease;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
    outline: none;
    border-color: #42b983;
    box-shadow: 0 0 0 2px rgba(66, 185, 131, 0.1);
}

.form-group textarea {
    height: 120px;
    resize: vertical;
}

.save-btn {
    width: 100%;
    padding: 12px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
}

.save-btn:hover {
    background: #3aa876;
}

/* 响应式设计优化 */
@media (max-width: 768px) {
    .content-container {
        padding: 20px 10px;
    }
    
    .merchant-info {
        flex-direction: column;
        text-align: center;
    }
    
    .merchant-header-top {
        justify-content: center;
    }

    .tabs {
        overflow-x: auto;
        padding-bottom: 5px;
    }

    .product-item {
        flex-direction: column;
    }

    .product-image {
        width: 100%;
        height: 200px;
    }

    .product-actions {
        justify-content: flex-end;
    }
}
</style>
