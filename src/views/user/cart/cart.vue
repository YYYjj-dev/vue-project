<template>
    <div class="cart-page">
        <NavBar />
        
        <div class="cart-container">
            <div class="cart-header">
                <h1>我的购物车</h1>
                <span class="item-count">共 {{ totalItems }} 件商品</span>
            </div>

            <!-- 购物车为空时显示 -->
            <div v-if="cartItems.length === 0" class="empty-cart">
                <div class="empty-cart-icon"></div>
                <p>购物车还是空的哦~</p>
                <router-link to="/shop_rec" class="go-shopping-btn">
                    去逛逛
                </router-link>
            </div>

            <!-- 购物车商品列表 -->
            <div v-else class="cart-content">
                <div class="cart-items">
                    <div v-for="item in cartItems" :key="item.id" class="cart-item">
                        <!-- 商品选择框 -->
                        <div class="item-select">
                            <input 
                                type="checkbox" 
                                :checked="item.selected"
                                @change="toggleSelect(item.id)"
                            >
                        </div>
                        
                        <!-- 商品图片 -->
                        <div class="item-image">
                            <img :src="item.image" :alt="item.name">
                        </div>
                        
                        <!-- 商品信息 -->
                        <div class="item-info">
                            <h3 class="item-name">{{ item.name }}</h3>
                            <p class="item-spec">{{ item.specification }}</p>
                        </div>
                        
                        <!-- 商品单价 -->
                        <div class="item-price">
                            ¥{{ item.price.toFixed(2) }}
                        </div>
                        
                        <!-- 数量调整 -->
                        <div class="item-quantity">
                            <button 
                                class="quantity-btn" 
                                @click="decreaseQuantity(item.id)"
                                :disabled="item.quantity <= 1"
                            >-</button>
                            <input 
                                type="number" 
                                v-model.number="item.quantity"
                                min="1"
                                @change="updateQuantity(item.id, item.quantity)"
                            >
                            <button 
                                class="quantity-btn"
                                @click="increaseQuantity(item.id)"
                            >+</button>
                        </div>
                        
                        <!-- 商品总价 -->
                        <div class="item-total">
                            ¥{{ (item.price * item.quantity).toFixed(2) }}
                        </div>
                        
                        <!-- 删除按钮 -->
                        <div class="item-delete">
                            <button @click="removeItem(item.id)" class="delete-btn">
                                删除
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 购物车底部 -->
                <div class="cart-footer">
                    <div class="select-all">
                        <input 
                            type="checkbox" 
                            :checked="allSelected"
                            @change="toggleSelectAll"
                        >
                        <span>全选</span>
                    </div>
                    
                    <div class="cart-summary">
                        <div class="summary-item">
                            <span>已选商品 {{ selectedCount }} 件</span>
                        </div>
                        <div class="summary-item">
                            <span>合计：</span>
                            <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
                        </div>
                        <button class="checkout-btn" @click="checkout" :disabled="selectedCount === 0">
                            结算
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, computed } from 'vue'
import NavBar from '../../../components/NavBar.vue'

export default {
    name: 'Cart',
    components: {
        NavBar
    },
    setup() {
        // 购物车数据
        const cartItems = ref([
            {
                id: 1,
                name: '示例商品1',
                specification: '规格：500g',
                price: 29.9,
                quantity: 1,
                selected: true,
                image: 'https://via.placeholder.com/100'
            },
            // 可以添加更多示例商品
        ])

        // 计算总商品数
        const totalItems = computed(() => {
            return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
        })

        // 计算选中商品数量
        const selectedCount = computed(() => {
            return cartItems.value.filter(item => item.selected).length
        })

        // 计算总价
        const totalPrice = computed(() => {
            return cartItems.value
                .filter(item => item.selected)
                .reduce((sum, item) => sum + item.price * item.quantity, 0)
        })

        // 是否全选
        const allSelected = computed(() => {
            return cartItems.value.length > 0 && cartItems.value.every(item => item.selected)
        })

        // 切换商品选中状态
        const toggleSelect = (id) => {
            const item = cartItems.value.find(item => item.id === id)
            if (item) {
                item.selected = !item.selected
            }
        }

        // 切换全选状态
        const toggleSelectAll = () => {
            const newState = !allSelected.value
            cartItems.value.forEach(item => {
                item.selected = newState
            })
        }

        // 增加商品数量
        const increaseQuantity = (id) => {
            const item = cartItems.value.find(item => item.id === id)
            if (item) {
                item.quantity++
            }
        }

        // 减少商品数量
        const decreaseQuantity = (id) => {
            const item = cartItems.value.find(item => item.id === id)
            if (item && item.quantity > 1) {
                item.quantity--
            }
        }

        // 更新商品数量
        const updateQuantity = (id, quantity) => {
            const item = cartItems.value.find(item => item.id === id)
            if (item) {
                item.quantity = Math.max(1, quantity)
            }
        }

        // 删除商品
        const removeItem = (id) => {
            const index = cartItems.value.findIndex(item => item.id === id)
            if (index !== -1) {
                cartItems.value.splice(index, 1)
            }
        }

        // 结算
        const checkout = () => {
            // 这里添加结算逻辑
            alert('跳转到结算页面')
        }

        return {
            cartItems,
            totalItems,
            selectedCount,
            totalPrice,
            allSelected,
            toggleSelect,
            toggleSelectAll,
            increaseQuantity,
            decreaseQuantity,
            updateQuantity,
            removeItem,
            checkout
        }
    }
}
</script>

<style scoped>
.cart-page {
    min-height: 100vh;
    background-color: #f8f7f2;
}

.cart-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
}

.cart-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 2rem;
}

.cart-header h1 {
    font-size: 24px;
    color: #2c3e50;
    font-weight: 600;
}

.item-count {
    color: #666;
    font-size: 14px;
}

.empty-cart {
    text-align: center;
    padding: 4rem 0;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.empty-cart-icon {
    width: 120px;
    height: 120px;
    margin: 0 auto 1rem;
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23999' stroke-width='1' stroke-linecap='round' stroke-linejoin='round'%3E%3Ccircle cx='9' cy='21' r='1'/%3E%3Ccircle cx='20' cy='21' r='1'/%3E%3Cpath d='M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6'/%3E%3C/svg%3E") no-repeat center;
    opacity: 0.5;
}

.empty-cart p {
    color: #666;
    margin-bottom: 1.5rem;
}

.go-shopping-btn {
    display: inline-block;
    padding: 0.8rem 2rem;
    background: #42b983;
    color: white;
    border-radius: 4px;
    text-decoration: none;
    transition: all 0.3s ease;
}

.go-shopping-btn:hover {
    background: #3aa876;
    transform: translateY(-1px);
}

.cart-content {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.cart-items {
    padding: 1rem;
}

.cart-item {
    display: grid;
    grid-template-columns: auto 100px 2fr 1fr 1fr 1fr auto;
    align-items: center;
    gap: 1rem;
    padding: 1rem;
    border-bottom: 1px solid #eee;
}

.cart-item:last-child {
    border-bottom: none;
}

.item-image img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
}

.item-info {
    padding: 0 1rem;
}

.item-name {
    font-size: 16px;
    color: #2c3e50;
    margin-bottom: 0.5rem;
}

.item-spec {
    font-size: 14px;
    color: #666;
}

.item-price {
    color: #e74c3c;
    font-weight: 500;
}

.item-quantity {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.quantity-btn {
    width: 28px;
    height: 28px;
    border: 1px solid #ddd;
    background: white;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.quantity-btn:hover:not(:disabled) {
    background: #f8f9fa;
    border-color: #42b983;
}

.quantity-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.item-quantity input {
    width: 50px;
    height: 28px;
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.item-total {
    color: #e74c3c;
    font-weight: 600;
}

.delete-btn {
    padding: 0.5rem 1rem;
    border: none;
    background: none;
    color: #666;
    cursor: pointer;
    transition: color 0.3s ease;
}

.delete-btn:hover {
    color: #e74c3c;
}

.cart-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 2rem;
    background: #f8f9fa;
    border-top: 1px solid #eee;
    border-radius: 0 0 8px 8px;
}

.select-all {
    display: flex;
    align-items: center;
    gap: 0.5rem;
}

.cart-summary {
    display: flex;
    align-items: center;
    gap: 2rem;
}

.summary-item {
    color: #666;
}

.total-price {
    color: #e74c3c;
    font-size: 20px;
    font-weight: 600;
    margin-left: 0.5rem;
}

.checkout-btn {
    padding: 0.8rem 2rem;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

.checkout-btn:hover:not(:disabled) {
    background: #3aa876;
    transform: translateY(-1px);
}

.checkout-btn:disabled {
    background: #ccc;
    cursor: not-allowed;
    transform: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .cart-container {
        padding: 1rem;
    }

    .cart-item {
        grid-template-columns: auto 1fr auto;
        gap: 0.5rem;
    }

    .item-price,
    .item-total {
        display: none;
    }

    .item-info {
        padding: 0;
    }

    .cart-footer {
        flex-direction: column;
        gap: 1rem;
    }

    .cart-summary {
        flex-direction: column;
        align-items: flex-end;
        gap: 0.5rem;
    }

    .checkout-btn {
        width: 100%;
    }
}
</style> 