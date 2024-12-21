<template>
    <div class="user-center">
        <NavBar />
        
        <div class="content-container">
            <!-- 用户信息头部 -->
            <div class="user-header">
                <div class="user-info">
                    <div class="user-avatar"></div>
                    <div class="user-details">
                        <div class="user-header-top">
                            <h2 class="username">用户名</h2>
                            <button class="become-merchant-btn" @click="applyMerchant">成为商家</button>
                        </div>
                        <p class="user-id">ID: 10086</p>
                    </div>
                </div>
            </div>

            <!-- 功能区域 -->
            <div class="function-area">
                <!-- 标签页切换 -->
                <div class="tabs">
                    <button 
                        :class="['tab-btn', { active: activeTab === 'collections' }]"
                        @click="activeTab = 'collections'"
                    >
                        我的收藏
                    </button>
                    <button 
                        :class="['tab-btn', { active: activeTab === 'orders' }]"
                        @click="activeTab = 'orders'"
                    >
                        我的订单
                    </button>
                </div>

                <!-- 收藏列表 -->
                <div v-if="activeTab === 'collections'" class="collections-list">
                    <div class="collection-item" v-for="item in collections" :key="item.id">
                        <div class="item-image"></div>
                        <div class="item-info">
                            <div class="shop-header">
                                <h3 class="shop-title">{{ item.shopName }}</h3>
                                <span class="shop-type">{{ item.type }}</span>
                            </div>
                            <p class="shop-desc">{{ item.description }}</p>
                            <div class="item-meta">
                                <div class="item-actions">
                                    <button class="action-btn" @click="viewShop(item.id)">查看店铺</button>
                                    <button class="action-btn delete" @click="removeCollection(item.id)">取消收藏</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 订单列表 -->
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
                                    <button class="action-btn" @click="viewOrderDetail(order.id)">订单详情</button>
                                    <button class="action-btn delete" v-if="order.status === '待付款'" @click="cancelOrder(order.id)">取消订单</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../components/NavBar.vue'

export default {
    name: 'user_center',
    components: {
        NavBar
    },
    data() {
        return {
            activeTab: 'collections',
            collections: [
                {
                    id: 1,
                    shopName: '添加剂专营店',
                    type: '食品添加剂',
                    description: '专业经营各类食品添加剂，质量保证，价格实惠'
                },
                {
                    id: 2,
                    shopName: '原料配料商城',
                    type: '食品原料',
                    description: '提供各类食品原料，品质优良，种类齐全'
                }
            ],
            orders: [
                {
                    id: 1,
                    orderNumber: 'ORDER20240120001',
                    status: '待付款',
                    productName: '商品名称1',
                    specification: '规格：500ml',
                    price: '299.00',
                    orderTime: '2024-01-20'
                },
                {
                    id: 2,
                    orderNumber: 'ORDER20240119001',
                    status: '已完成',
                    productName: '商品名称2',
                    specification: '规格：200ml',
                    price: '199.00',
                    orderTime: '2024-01-19'
                }
            ]
        }
    },
    methods: {
        viewDetail(id) {
            // 查看收藏详情
            console.log('查看收藏详情:', id)
        },
        removeCollection(id) {
            // 取消收藏
            console.log('取消收藏:', id)
        },
        viewOrderDetail(id) {
            this.$router.push(`/order_detail/${id}`)
        },
        cancelOrder(id) {
            // 取消订单
            console.log('取消订单:', id)
        },
        applyMerchant() {
            this.$router.push('/merchant_apply')
        },
        viewShop(id) {
            // 修改查看店铺方法，跳转到店铺详情页
            this.$router.push(`/shop_sel_info1/${id}`)
        }
    }
}
</script>

<style scoped>
.user-center {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 用户信息头部 */
.user-header {
    background: white;
    border-radius: 16px;
    padding: 30px;
    margin-bottom: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.user-info {
    display: flex;
    align-items: center;
    gap: 20px;
}

.user-avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: #f0f0f0;
}

.username {
    font-size: 1.5rem;
    color: #2c3e50;
    margin-bottom: 8px;
}

.user-id {
    color: #666;
    font-size: 0.9rem;
}

/* 功能区域 */
.function-area {
    background: white;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

/* 标签页 */
.tabs {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
    border-bottom: 1px solid #eee;
    padding-bottom: 15px;
}

.tab-btn {
    padding: 10px 20px;
    border: none;
    background: none;
    font-size: 1.1rem;
    color: #666;
    cursor: pointer;
    position: relative;
}

.tab-btn.active {
    color: #42b983;
    font-weight: 500;
}

.tab-btn.active::after {
    content: '';
    position: absolute;
    bottom: -16px;
    left: 0;
    width: 100%;
    height: 3px;
    background: #42b983;
}

/* 收藏列表 */
.collection-item {
    display: flex;
    gap: 20px;
    padding: 20px;
    border-bottom: 1px solid #eee;
}

.item-image {
    width: 120px;
    height: 120px;
    background: #f0f0f0;
    border-radius: 8px;
}

.item-info {
    flex: 1;
}

.item-title {
    font-size: 1.2rem;
    color: #2c3e50;
    margin-bottom: 8px;
}

.item-desc {
    color: #666;
    margin-bottom: 16px;
    line-height: 1.5;
}

.item-meta {
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

/* 订单列表 */
.order-item {
    background: #f9f9f9;
    border-radius: 12px;
    margin-bottom: 20px;
    overflow: hidden;
}

.order-header {
    background: white;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #eee;
}

.order-number {
    color: #666;
}

.order-status {
    color: #42b983;
    font-weight: 500;
}

.order-content {
    padding: 20px;
    background: white;
}

.product-info {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
}

.product-image {
    width: 100px;
    height: 100px;
    background: #f0f0f0;
    border-radius: 8px;
}

.product-details {
    flex: 1;
}

.product-name {
    font-size: 1.1rem;
    color: #2c3e50;
    margin-bottom: 8px;
}

.product-spec {
    color: #666;
    margin-bottom: 8px;
}

.product-price {
    color: #f56c6c;
    font-size: 1.2rem;
    font-weight: 500;
}

.order-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

.order-time {
    color: #999;
    font-size: 0.9rem;
}

/* 按钮样式 */
.action-btn {
    padding: 8px 16px;
    border: 1px solid #ddd;
    border-radius: 20px;
    background: white;
    color: #666;
    cursor: pointer;
    transition: all 0.3s ease;
    margin-left: 10px;
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

@media (max-width: 768px) {
    .content-container {
        padding: 20px 10px;
    }

    .user-info {
        flex-direction: column;
        text-align: center;
    }

    .collection-item,
    .product-info {
        flex-direction: column;
    }

    .item-image,
    .product-image {
        width: 100%;
        height: 200px;
    }

    .item-meta,
    .order-footer {
        flex-direction: column;
        gap: 10px;
    }

    .action-btn {
        width: 100%;
        margin: 5px 0;
    }
}

.user-header-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 8px;
}

.become-merchant-btn {
    padding: 8px 16px;
    background-color: #42b983;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: all 0.3s ease;
}

.become-merchant-btn:hover {
    background-color: #3aa876;
}
</style> 