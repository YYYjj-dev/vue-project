<template>
    <div class="order-detail">
        <NavBar />
        
        <div class="content-container">
            <div class="detail-card">
                <!-- 订单状态 -->
                <div class="status-section">
                    <div class="status-info">
                        <h2>订单状态：<span class="status">{{ orderInfo.status }}</span></h2>
                    </div>
                    <div class="delivery-info" v-if="orderInfo.status !== '待付款'">
                        <div class="delivery-item">
                            <span class="label">快递公司：</span>
                            <span>{{ orderInfo.expressCompany }}</span>
                        </div>
                        <div class="delivery-item">
                            <span class="label">快递单号：</span>
                            <span>{{ orderInfo.trackingNumber }}</span>
                        </div>
                    </div>
                </div>

                <!-- 收货信息 -->
                <div class="section">
                    <h3>收货信息</h3>
                    <div class="info-content">
                        <p><span class="label">收货人：</span>{{ orderInfo.receiver }}</p>
                        <p><span class="label">联系电话：</span>{{ orderInfo.phone }}</p>
                        <p><span class="label">收货地址：</span>{{ orderInfo.address }}</p>
                    </div>
                </div>

                <!-- 商品信息 -->
                <div class="section">
                    <h3>商品信息</h3>
                    <div class="product-info">
                        <div class="product-image"></div>
                        <div class="product-details">
                            <h4>{{ orderInfo.productName }}</h4>
                            <p class="spec">{{ orderInfo.specification }}</p>
                            <div class="price-qty">
                                <span class="price">¥{{ orderInfo.price }}</span>
                                <span class="quantity">x{{ orderInfo.quantity }}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 订单信息 -->
                <div class="section">
                    <h3>订单信息</h3>
                    <div class="info-content">
                        <p><span class="label">订单编号：</span>{{ orderInfo.orderNumber }}</p>
                        <p><span class="label">创建时间：</span>{{ orderInfo.createTime }}</p>
                    </div>
                </div>

                <!-- 金额信息 -->
                <div class="section price-section">
                    <div class="price-item">
                        <span>商品总额</span>
                        <span>¥{{ orderInfo.totalAmount }}</span>
                    </div>
                    <div class="price-item">
                        <span>运费</span>
                        <span>¥{{ orderInfo.shippingFee }}</span>
                    </div>
                    <div class="price-item total">
                        <span>实付款</span>
                        <span>¥{{ orderInfo.finalAmount }}</span>
                    </div>
                </div>

                <!-- 底部按钮 -->
                <div class="action-buttons">
                    <button v-if="orderInfo.status === '待付款'" class="primary-btn" @click="payOrder">立即支付</button>
                    <button v-if="orderInfo.status === '待付款'" class="secondary-btn" @click="cancelOrder">取消订单</button>
                    <button v-if="orderInfo.status === '待收货'" class="primary-btn" @click="confirmReceive">确认收货</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'

export default {
    name: 'order_detail',
    components: {
        NavBar
    },
    data() {
        return {
            orderInfo: {
                id: '',
                status: '待付款',
                orderNumber: 'ORDER20240120001',
                productName: '商品名称',
                specification: '规格：500ml',
                price: '299.00',
                quantity: 1,
                totalAmount: '299.00',
                shippingFee: '0.00',
                finalAmount: '299.00',
                receiver: '张三',
                phone: '13800138000',
                address: '北京市朝阳区xx街道xx号',
                createTime: '2024-01-20 14:30:00',
                expressCompany: '顺丰快递',
                trackingNumber: 'SF1234567890'
            }
        }
    },
    computed: {
        getStatusDescription() {
            const statusMap = {
                '待发货': '商家正在处理您的订单，请耐心等待',
                '待收货': '商品已发出，请注意查收',
                '已完成': '订单已完成，感谢您的购买',
                '已取消': '订单已取消'
            }
            return statusMap[this.orderInfo.status] || ''
        }
    },
    methods: {
        payOrder() {
            console.log('支付订单')
            // TODO: 实现支付逻辑
        },
        cancelOrder() {
            console.log('取消订单')
            // TODO: 实现取消订单逻辑
        },
        confirmReceive() {
            console.log('确认收货')
            // TODO: 实现确认收货逻辑
        }
    },
    created() {
        // 获取路由参数中的订单ID
        const orderId = this.$route.params.id
        // TODO: 根据订单ID获取订单详情
        console.log('订单ID:', orderId)
    }
}
</script>

<style scoped>
.order-detail {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

.detail-card {
    background: white;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.status-section {
    background: #f8f9fe;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 30px;
}

.status-info h2 {
    color: #2c3e50;
    margin-bottom: 10px;
}

.status {
    color: #42b983;
}

.status-desc {
    color: #666;
    font-size: 0.9rem;
}

.delivery-info {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

.delivery-item {
    margin-bottom: 5px;
    color: #666;
}

.section {
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.section h3 {
    color: #2c3e50;
    margin-bottom: 15px;
}

.info-content p {
    margin-bottom: 10px;
    color: #666;
}

.label {
    color: #999;
    margin-right: 10px;
}

.product-info {
    display: flex;
    gap: 20px;
    padding: 15px;
    background: #f9f9f9;
    border-radius: 8px;
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

.product-details h4 {
    color: #2c3e50;
    margin-bottom: 8px;
}

.spec {
    color: #666;
    font-size: 0.9rem;
    margin-bottom: 8px;
}

.price-qty {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.price {
    color: #f56c6c;
    font-size: 1.1rem;
    font-weight: 500;
}

.quantity {
    color: #999;
}

.price-section {
    border-bottom: none;
}

.price-item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    color: #666;
}

.price-item.total {
    color: #2c3e50;
    font-weight: 500;
    font-size: 1.1rem;
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

.action-buttons {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 20px;
}

.primary-btn, .secondary-btn {
    padding: 10px 20px;
    border-radius: 20px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: all 0.3s ease;
}

.primary-btn {
    background: #42b983;
    color: white;
    border: none;
}

.secondary-btn {
    background: white;
    color: #666;
    border: 1px solid #ddd;
}

.primary-btn:hover {
    background: #3aa876;
}

.secondary-btn:hover {
    border-color: #42b983;
    color: #42b983;
}

@media (max-width: 768px) {
    .content-container {
        padding: 10px;
    }
    
    .detail-card {
        padding: 20px;
    }
    
    .product-info {
        flex-direction: column;
    }
    
    .product-image {
        width: 100%;
        height: 200px;
    }
}
</style>
