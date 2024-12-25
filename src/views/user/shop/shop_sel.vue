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
                    <button 
                        v-for="category in categories" 
                        :key="category.id"
                        class="category-btn"
                        :class="{ active: currentCategory === category.id }"
                        @click="changeCategory(category.id)">
                        {{ category.name }}
                    </button>
                </div>
            </div>

            <!-- 右侧商家列表 -->
            <div class="seller-list">
                <div class="seller-card" v-for="seller in filteredSellers" :key="seller.id">
                    <div class="seller-header">
                        <div class="seller-logo">{{ seller.logo }}</div>
                        <div class="seller-info">
                            <div class="seller-name">{{ seller.name }}</div>
                            <div class="seller-desc">{{ seller.description }}</div>
                        </div>
                        <button class="enter-store" @click="goToStore(seller.id)">进店</button>
                    </div>
                    <div class="seller-images">
                        <button class="scroll-btn left" @click="scrollImages('left', seller.id)">&lt;</button>
                        <div class="images-wrapper">
                            <div class="images-container" :id="`seller-${seller.id}-images`">
                                <div class="image-item" v-for="(image, index) in seller.images" :key="index">
                                    <div class="image-placeholder">{{ image }}</div>
                                </div>
                            </div>
                        </div>
                        <button class="scroll-btn right" @click="scrollImages('right', seller.id)">&gt;</button>
                    </div>
                </div>
            </div>
        </div>
        <Footer />
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import Footer from '../../../components/Footer.vue'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

export default {
    name: 'shop_sel',
    components: {
        NavBar,
        Footer
    },
    setup() {
        const router = useRouter()
        const currentCategory = ref(1) // 默认选中第一个分类

        // 分类数据
        const categories = [
            { id: 1, name: '商品类别1' },
            { id: 2, name: '商品类别2' },
            { id: 3, name: '商品类别3' },
            { id: 4, name: '商品类别4' }
        ]

        // 商家数据
        const sellers = [
            { 
                id: 1, 
                categoryId: 1,
                logo: '制造商A',
                name: '制造商A名称',
                description: '专注于添加剂研发与生产的领军企业',
                images: ['图片A1', '图片A2', '图片A3', '图片A4', '图片A5']
            },
            { 
                id: 2, 
                categoryId: 1,
                logo: '制造商B',
                name: '制造商B名称',
                description: '国际认证的添加剂生产商',
                images: ['图片B1', '图片B2', '图片B3', '图片B4', '图片B5']
            },
            { 
                id: 3, 
                categoryId: 1,
                logo: '制造商C',
                name: '制造商C名称',
                description: '创新型添加剂研发机构',
                images: ['图片C1', '图片C2', '图片C3', '图片C4', '图片C5']
            },
            { 
                id: 4, 
                categoryId: 1,
                logo: '制造商D',
                name: '制造商D名称',
                description: '专业的食品添加剂供应商',
                images: ['图片D1', '图片D2', '图片D3', '图片D4', '图片D5']
            },
            { 
                id: 5, 
                categoryId: 1,
                logo: '制造商E',
                name: '制造商E名称',
                description: '全球领先的添加剂制造商',
                carousel: '轮播图E',
                images: ['图片E1', '图片E2', '图片E3', '图片E4', '图片E5']
            },
            { 
                id: 6, 
                categoryId: 2,
                logo: '制造商F',
                name: '制造商F名称',
                description: '天然添加剂研发专家',
                carousel: '轮播图F',
                images: ['图片F1', '图片F2', '图片F3', '图片F4', '图片F5']
            },
            { 
                id: 7, 
                categoryId: 2,
                logo: '制造商G',
                name: '制造商G名称',
                description: '有机添加剂生产基地',
                carousel: '轮播图G',
                images: ['图片G1', '图片G2', '图片G3', '图片G4', '图片G5']
            },
            { 
                id: 8, 
                categoryId: 2,
                logo: '制造商H',
                name: '制造商H名称',
                description: '绿色环保添加剂制造商',
                carousel: '轮播图H',
                images: ['图片H1', '图片H2', '图片H3', '图片H4', '图片H5']
            },
            { 
                id: 9, 
                categoryId: 2,
                logo: '制造商I',
                name: '制造商I名称',
                description: '高科技添加剂研发中心',
                carousel: '轮播图I',
                images: ['图片I1', '图片I2', '图片I3', '图片I4', '图片I5']
            },
            { 
                id: 10, 
                categoryId: 2,
                logo: '制造商J',
                name: '制造商J名称',
                description: '创新型添加剂生产企业',
                carousel: '轮播图J',
                images: ['图片J1', '图片J2', '图片J3', '图片J4', '图片J5']
            },
            { 
                id: 11, 
                categoryId: 3,
                logo: '制造商K',
                name: '制造商K名称',
                description: '专业食品级添加剂制造商',
                carousel: '轮播图K',
                images: ['图片K1', '图片K2', '图片K3', '图片K4', '图片K5']
            },
            { 
                id: 12, 
                categoryId: 3,
                logo: '制造商L',
                name: '制造商L名称',
                description: '现代化添加剂生产基地',
                carousel: '轮播图L',
                images: ['图片L1', '图片L2', '图片L3', '图片L4', '图片L5']
            },
            { 
                id: 13, 
                categoryId: 3,
                logo: '制造商M',
                name: '制造商M名称',
                description: '国际标准添加剂供应商',
                carousel: '轮播图M',
                images: ['图片M1', '图片M2', '图片M3', '图片M4', '图片M5']
            },
            { 
                id: 14, 
                categoryId: 3,
                logo: '制造商N',
                name: '制造商N名称',
                description: '高品质添加剂研发机构',
                carousel: '轮播图N',
                images: ['图片N1', '图片N2', '图片N3', '图片N4', '图片N5']
            },
            { 
                id: 15, 
                categoryId: 3,
                logo: '制造商O',
                name: '制造商O名称',
                description: '专业添加剂技术服务商',
                carousel: '轮播图O',
                images: ['图片O1', '图片O2', '图片O3', '图片O4', '图片O5']
            },
            { 
                id: 16, 
                categoryId: 4,
                logo: '制造商P',
                name: '制造商P名称',
                description: '创新型添加剂解决方案提供商',
                carousel: '轮播图P',
                images: ['图片P1', '图片P2', '图片P3', '图片P4', '图片P5']
            },
            { 
                id: 17, 
                categoryId: 4,
                logo: '制造商Q',
                name: '制造商Q名称',
                description: '全球化添加剂研发中心',
                carousel: '轮播图Q',
                images: ['图片Q1', '图片Q2', '图片Q3', '图片Q4', '图片Q5']
            },
            { 
                id: 18, 
                categoryId: 4,
                logo: '制造商R',
                name: '制造商R名称',
                description: '专业添加剂生产企业',
                carousel: '轮播图R',
                images: ['图片R1', '图片R2', '图片R3', '图片R4', '图片R5']
            },
            { 
                id: 19, 
                categoryId: 4,
                logo: '制造商S',
                name: '制造商S名称',
                description: '高新技术添加剂制造商',
                carousel: '轮播图S',
                images: ['图片S1', '图片S2', '图片S3', '图片S4', '图片S5']
            },
            { 
                id: 20, 
                categoryId: 4,
                logo: '制造商T',
                name: '制造商T名称',
                description: '综合性添加剂服务提供商',
                carousel: '轮播图T',
                images: ['图片T1', '图片T2', '图片T3', '图片T4', '图片T5']
            }
        ]

        // 根据当前选中的分类筛选商家
        const filteredSellers = computed(() => {
            return sellers.filter(seller => seller.categoryId === currentCategory.value)
        })

        // 切换分类
        const changeCategory = (categoryId) => {
            currentCategory.value = categoryId
        }

        // 进入商家店铺
        const goToStore = (sellerId) => {
            router.push({
                name: 'shop_sel_info1',
                params: { id: sellerId }
            })
        }

        // 修改滚动逻辑
        const scrollImages = (direction, sellerId) => {
            const container = document.getElementById(`seller-${sellerId}-images`)
            if (container) {
                const scrollAmount = 220 // 图片宽度 + 间距
                const currentScroll = container.style.transform || 'translateX(0px)'
                const currentX = parseInt(currentScroll.replace(/[^\d-]/g, '')) || 0
                
                let newX
                if (direction === 'left') {
                    newX = Math.min(0, currentX + scrollAmount)
                } else {
                    const maxScroll = -(container.scrollWidth - container.parentElement.clientWidth)
                    newX = Math.max(maxScroll, currentX - scrollAmount)
                }
                
                container.style.transform = `translateX(${newX}px)`
            }
        }

        return {
            categories,
            currentCategory,
            filteredSellers,
            changeCategory,
            goToStore,
            scrollImages
        }
    }
}
</script>

<style scoped>
.shop-sel {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding-top: 60px; /* 为固定导航栏留出空间 */
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
    border-radius: 8px;
    padding: 20px;
}

.seller-header {
    display: flex;
    align-items: center;
    gap: 20px;
    margin-bottom: 20px;
}

.seller-logo {
    width: 80px;
    height: 80px;
    background: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 4px;
}

.seller-info {
    flex: 1;
    padding: 0 15px;
}

.seller-name {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 5px;
}

.seller-desc {
    font-size: 14px;
    color: #666;
}

.enter-store {
    padding: 8px 20px;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
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

/* 修改图片展示区域样式 */
.seller-images {
    position: relative;
    width: 100%;
    padding: 20px 0;
    display: flex;
    align-items: center;
    gap: 10px;
}

.images-wrapper {
    flex: 1;
    overflow: hidden;
}

.images-container {
    display: flex;
    gap: 20px;
    transition: transform 0.3s ease;
}

.image-item {
    flex: 0 0 200px;
    height: 200px;
    background: #f0f0f0;
    border-radius: 4px;
}

.image-placeholder {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
}

.scroll-btn {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.3);
    color: white;
    border: none;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    transition: background-color 0.3s;
    z-index: 1;
}

.scroll-btn:hover {
    background: rgba(0, 0, 0, 0.5);
}

.scroll-btn.left {
    left: 0;
}

.scroll-btn.right {
    right: 0;
}
</style>