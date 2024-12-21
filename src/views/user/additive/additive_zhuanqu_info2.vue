<template>
    <div class="page-container">
        <!-- 导航栏 -->
        <NavBar />
        
        <!-- 主要内容区域 -->
        <div class="content-wrapper">
            <!-- 分类按钮组 -->
            <div class="filter-section">
                <div class="filter-group">
                    <span class="filter-label">分类</span>
                    <div class="filter-buttons">
                        <button 
                            v-for="(type, index) in types" 
                            :key="index"
                            :class="['filter-btn', { active: currentType === type.id }]"
                            @click="changeType(type.id)">
                            {{ type.name }}
                        </button>
                    </div>
                </div>

                <div class="filter-group">
                    <span class="filter-label">年龄分段</span>
                    <div class="filter-buttons">
                        <button 
                            v-for="(age, index) in ages" 
                            :key="index"
                            :class="['filter-btn', { active: currentAge === age.id }]"
                            @click="changeAge(age.id)">
                            {{ age.name }}
                        </button>
                    </div>
                </div>
            </div>

            <!-- 内容列表 -->
            <div class="content-list">
                <div class="content-item" v-for="item in filteredItems" :key="item.id">
                    <div class="item-image">{{ item.image }}</div>
                    <div class="item-info">
                        <div class="item-text">{{ item.content }}</div>
                        <button class="buy-btn" @click="goToShop(item.id)">去购买</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 页脚 -->
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'
import Footer from '../../../components/Footer.vue'

export default {
    name: 'additive_zhuanqu_info2',
    components: {
        NavBar,
        Footer
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            currentType: 1,
            currentAge: 1,
            types: [
                { id: 1, name: '种类1' },
                { id: 2, name: '种类2' },
                { id: 3, name: '种类3' }
            ],
            ages: [
                { id: 1, name: '分段1' },
                { id: 2, name: '分段2' },
                { id: 3, name: '分段3' }
            ],
            items: [
                { id: 1, type: 1, age: 1, image: '图片1-1', content: '种类1分段1内容1', price: '99.00' },
                { id: 2, type: 1, age: 1, image: '图片1-2', content: '种类1分段1内容2', price: '199.00' },
                { id: 3, type: 1, age: 1, image: '图片1-3', content: '种类1分段1内容3', price: '299.00' },
                { id: 4, type: 1, age: 1, image: '图片1-4', content: '种类1分段1内容4', price: '399.00' },
                { id: 5, type: 1, age: 1, image: '图片1-5', content: '种类1分段1内容5', price: '499.00' },
                { id: 6, type: 1, age: 1, image: '图片1-6', content: '种类1分段1内容6', price: '599.00' },
                { id: 7, type: 1, age: 2, image: '图片2-1', content: '种类1分段2内容1', price: '699.00' },
                { id: 8, type: 1, age: 2, image: '图片2-2', content: '种类1分段2内容2', price: '799.00' },
                { id: 9, type: 1, age: 2, image: '图片2-3', content: '种类1分段2内容3', price: '899.00' },
                { id: 10, type: 1, age: 2, image: '图片2-4', content: '种类1分段2内容4', price: '999.00' },
                { id: 11, type: 1, age: 2, image: '图片2-5', content: '种类1分段2内容5', price: '1099.00' },
                { id: 12, type: 1, age: 3, image: '图片3-1', content: '种类1分段3内容1', price: '1199.00' },
                { id: 13, type: 1, age: 3, image: '图片3-2', content: '种类1分段3内容2', price: '1299.00' },
                { id: 14, type: 1, age: 3, image: '图片3-3', content: '种类1分段3内容3', price: '1399.00' },
                { id: 15, type: 1, age: 3, image: '图片3-4', content: '种类1分段3内容4', price: '1499.00' },
                { id: 16, type: 1, age: 3, image: '图片3-5', content: '种类1分段3内容5', price: '1599.00' },
                { id: 17, type: 2, age: 1, image: '图片4-1', content: '种类2分段1内容1', price: '1699.00' },
                { id: 18, type: 2, age: 1, image: '图片4-2', content: '种类2分段1内容2', price: '1799.00' },
                { id: 19, type: 2, age: 1, image: '图片4-3', content: '种类2分段1内容3', price: '1899.00' },
                { id: 20, type: 2, age: 1, image: '图片4-4', content: '种类2分段1内容4', price: '1999.00' },
                { id: 21, type: 2, age: 1, image: '图片4-5', content: '种类2分段1内容5', price: '2099.00' },
                { id: 22, type: 2, age: 1, image: '图片4-6', content: '种类2分段1内容6', price: '2199.00' },
                { id: 23, type: 2, age: 2, image: '图片5-1', content: '种类2分段2内容1', price: '2299.00' },
                { id: 24, type: 2, age: 2, image: '图片5-2', content: '种类2分段2内容2', price: '2399.00' },
                { id: 25, type: 2, age: 2, image: '图片5-3', content: '种类2分段2内容3', price: '2499.00' },
                { id: 26, type: 2, age: 2, image: '图片5-4', content: '种类2分段2内容4', price: '2599.00' },
                { id: 27, type: 2, age: 2, image: '图片5-5', content: '种类2分段2内容5', price: '2699.00' },
                { id: 28, type: 2, age: 3, image: '图片6-1', content: '种类2分段3内容1', price: '2799.00' },
                { id: 29, type: 2, age: 3, image: '图片6-2', content: '种类2分段3内容2', price: '2899.00' },
                { id: 30, type: 2, age: 3, image: '图片6-3', content: '种类2分段3内容3', price: '2999.00' },
                { id: 31, type: 2, age: 3, image: '图片6-4', content: '种类2分段3内容4', price: '3099.00' },
                { id: 32, type: 2, age: 3, image: '图片6-5', content: '种类2分段3内容5', price: '3199.00' },
                { id: 33, type: 2, age: 3, image: '图片6-6', content: '种类2分段3内容6', price: '3299.00' },
                { id: 34, type: 3, age: 1, image: '图片7-1', content: '种类3分段1内容1', price: '3399.00' },
                { id: 35, type: 3, age: 1, image: '图片7-2', content: '种类3分段1内容2', price: '3499.00' },
                { id: 36, type: 3, age: 1, image: '图片7-3', content: '种类3分段1内容3', price: '3599.00' },
                { id: 37, type: 3, age: 1, image: '图片7-4', content: '种类3分段1内容4', price: '3699.00' },
                { id: 38, type: 3, age: 1, image: '图片7-5', content: '种类3分段1内容5', price: '3799.00' },
                { id: 39, type: 3, age: 2, image: '图片8-1', content: '种类3分段2内容1', price: '3899.00' },
                { id: 40, type: 3, age: 2, image: '图片8-2', content: '种类3分段2内容2', price: '3999.00' },
                { id: 41, type: 3, age: 2, image: '图片8-3', content: '种类3分段2内容3', price: '4099.00' },
                { id: 42, type: 3, age: 2, image: '图片8-4', content: '种类3分段2内容4', price: '4199.00' },
                { id: 43, type: 3, age: 2, image: '图片8-5', content: '种类3分段2内容5', price: '4299.00' },
                { id: 44, type: 3, age: 2, image: '图片8-6', content: '种类3分段2内容6', price: '4399.00' },
                { id: 45, type: 3, age: 3, image: '图片9-1', content: '种类3分段3内容1', price: '4499.00' },
                { id: 46, type: 3, age: 3, image: '图片9-2', content: '种类3分段3内容2', price: '4599.00' },
                { id: 47, type: 3, age: 3, image: '图片9-3', content: '种类3分段3内容3', price: '4699.00' },
                { id: 48, type: 3, age: 3, image: '图片9-4', content: '种类3分段3内容4', price: '4799.00' },
                { id: 49, type: 3, age: 3, image: '图片9-5', content: '种类3分段3内容5', price: '4899.00' }
            ]
        }
    },
    computed: {
        filteredItems() {
            return this.items.filter(item => 
                item.type === this.currentType && 
                item.age === this.currentAge
            )
        }
    },
    methods: {
        changeType(typeId) {
            this.currentType = typeId
        },
        changeAge(ageId) {
            this.currentAge = ageId
        },
        goToShop(id) {
            this.router.push({
                name: 'shop_rec_info',
                params: { id: id.toString() }
            })
        }
    }
}
</script>

<style scoped>
/* 页面容器 */
.page-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f8f7f2;
}

/* 内容包装器 */
.content-wrapper {
    flex: 1;
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
    width: 90%;
}

/* 筛选区域样式 */
.filter-section {
    padding: 30px;
    background-color: white;
    border-radius: 12px;
    margin-bottom: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.filter-group {
    margin-bottom: 20px;
}

.filter-group:last-child {
    margin-bottom: 0;
}

.filter-label {
    display: inline-block;
    min-width: 80px;
    color: #2c3e50;
    margin-right: 20px;
    font-weight: 500;
}

.filter-buttons {
    display: inline-flex;
    gap: 12px;
    flex-wrap: wrap;
}

.filter-btn {
    padding: 8px 20px;
    border: 1px solid #e0e0e0;
    background: white;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #666;
    font-size: 14px;
}

.filter-btn.active {
    background: #42b983;
    color: white;
    border-color: #42b983;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.filter-btn:hover:not(.active) {
    border-color: #42b983;
    color: #42b983;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 内容列表样式 */
.content-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 30px;
    padding: 10px;
}

.content-item {
    background: white;
    border-radius: 12px;
    overflow: hidden;
    transition: all 0.3s ease;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
    border: 1px solid #eee;
}

.content-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
    border-color: #42b983;
}

.item-image {
    width: 100%;
    height: 200px;
    background: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    position: relative;
}

.item-image::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(66, 185, 131, 0.1);
    opacity: 0;
    transition: opacity 0.3s ease;
}

.content-item:hover .item-image::after {
    opacity: 1;
}

.item-info {
    padding: 20px;
}

.item-text {
    font-size: 16px;
    color: #2c3e50;
    margin-bottom: 15px;
    line-height: 1.5;
}

.buy-btn {
    width: 100%;
    padding: 12px 0;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 15px;
    font-weight: 500;
    box-shadow: 0 4px 12px rgba(66, 185, 131, 0.2);
}

.buy-btn:hover {
    background: #3aa876;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(66, 185, 131, 0.3);
}

/* 响应式调整 */
@media (max-width: 768px) {
    .content-wrapper {
        width: 95%;
        padding: 20px 10px;
    }

    .filter-section {
        padding: 20px;
    }

    .filter-group {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .filter-label {
        margin-bottom: 5px;
    }

    .filter-buttons {
        width: 100%;
    }

    .filter-btn {
        flex: 1;
        text-align: center;
        min-width: 80px;
    }

    .content-list {
        gap: 20px;
        padding: 5px;
    }

    .item-image {
        height: 180px;
    }

    .item-info {
        padding: 15px;
    }
}

@media (min-width: 769px) and (max-width: 1024px) {
    .content-list {
        grid-template-columns: repeat(2, 1fr);
    }
}
</style>