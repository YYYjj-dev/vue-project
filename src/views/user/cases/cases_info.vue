<template>
    <div class="cases-info">
        <!-- 导航栏 -->
        <NavBar />

        <div class="content-container">
            <!-- 案例标题和发布信息 -->
            <div class="case-header">
                <h1 class="case-title">{{ caseTitle }}</h1>
                <div class="publish-meta">
                    <span class="publish-date">发布时间：2024-01-20</span>
                    <span class="view-count">浏览量：2.3k</span>
                </div>
            </div>

            <!-- 案例简介 -->
            <div class="case-intro">
                <h2 class="section-title">案例简介</h2>
                <div class="intro-content">
                    <p>{{ caseIntro }}</p>
                </div>
            </div>

            <!-- 评论区域 -->
            <div class="comments-section">
                <h2 class="section-title">
                    评论区
                    <span class="comment-count">{{ comments.length }} 条评论</span>
                </h2>

                <!-- 写评论 -->
                <div class="write-comment">
                    <div class="comment-input">
                        <textarea v-model="newComment" placeholder="写下你的看法..."></textarea>
                        <button class="submit-btn">发布评论</button>
                    </div>
                </div>

                <!-- 评论列表 -->
                <div class="comments-list">
                    <div class="comment-item" v-for="(comment, index) in comments" :key="index">
                        <div class="comment-user">
                            <div class="user-avatar"></div>
                            <div class="user-info">
                                <span class="username">用户{{ index + 1 }}</span>
                                <span class="comment-time">2024-01-20</span>
                            </div>
                        </div>
                        <div class="comment-content">{{ comment.content }}</div>
                        <div class="comment-actions">
                            <button class="action-link">
                                <i class="icon-reply"></i>
                                <span>回复</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 相关商品推荐 -->
            <div class="related-products">
                <h2 class="section-title">相关商品推荐</h2>
                <div class="products-grid">
                    <div class="product-card" 
                         v-for="(product, index) in relatedProducts" 
                         :key="index"
                         @click="goToProduct(product.id)">
                        <div class="product-image">
                            <img :src="product.image" :alt="product.title">
                            <div class="hover-overlay">
                                <span>查看��情</span>
                            </div>
                        </div>
                        <div class="product-info">
                            <h3 class="product-title">商品{{ index + 1 }}</h3>
                            <p class="product-desc">这是一段商品描述文字...</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter, useRoute } from 'vue-router'

export default {
    name: 'cases_info',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const route = useRoute()
        return { router, route }
    },
    data() {
        return {
            caseTitle: '标题',
            publishInfo: '发布信息',
            caseIntro: '',
            newComment: '',
            comments: [
                { content: '评论1' },
                { content: '评论2' },
                { content: '评论3' },
                { content: '评论4' }
            ],
            relatedProducts: [
                { id: 1, image: '/src/img/product1.jpg', title: '商品A' },
                { id: 2, image: '/src/img/product2.jpg', title: '商品B' },
                { id: 3, image: '/src/img/product3.jpg', title: '商品C' },
                { id: 4, image: '/src/img/product4.jpg', title: '商品D' }
            ]
        }
    },
    methods: {
        goToProduct(id) {
            this.router.push(`/shop_rec_info/${id}`)
        }
    }
}
</script>

<style scoped>
.cases-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
}

/* 案例标题和元信息 */
.case-header {
    margin-bottom: 40px;
    text-align: center;
}

.case-title {
    font-size: 2.5rem;
    color: #2c3e50;
    margin-bottom: 16px;
    font-weight: 600;
    line-height: 1.4;
}

.publish-meta {
    color: #666;
    font-size: 0.9rem;
}

.publish-meta span {
    margin: 0 10px;
    display: inline-flex;
    align-items: center;
}

/* 内容区块通用样式 */
.case-intro,
.comments-section,
.related-products {
    background: white;
    border-radius: 16px;
    padding: 40px;
    margin-bottom: 40px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.section-title {
    font-size: 1.5rem;
    color: #2c3e50;
    margin-bottom: 24px;
    font-weight: 600;
    display: flex;
    align-items: center;
    gap: 12px;
}

.comment-count {
    font-size: 0.9rem;
    color: #666;
    font-weight: normal;
}

/* 案例简介内容 */
.intro-content {
    line-height: 1.8;
    color: #333;
    font-size: 1.1rem;
}

/* 评论区样式 */
.write-comment {
    margin-bottom: 30px;
}

.comment-input {
    position: relative;
}

.comment-input textarea {
    width: 100%;
    height: 120px;
    padding: 16px;
    border: 1px solid #e0e0e0;
    border-radius: 12px;
    resize: none;
    font-size: 1rem;
    transition: border-color 0.3s ease;
}

.comment-input textarea:focus {
    outline: none;
    border-color: #42b983;
}

.submit-btn {
    position: absolute;
    bottom: 16px;
    right: 16px;
    padding: 8px 24px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    transition: background 0.3s ease;
}

.submit-btn:hover {
    background: #3aa876;
}

/* 评论列表样式 */
.comment-item {
    padding: 24px;
    border-bottom: 1px solid #eee;
}

.comment-user {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 12px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #f0f0f0;
}

.user-info {
    display: flex;
    flex-direction: column;
}

.username {
    font-weight: 500;
    color: #333;
}

.comment-time {
    font-size: 0.8rem;
    color: #999;
}

.comment-content {
    margin: 12px 0;
    line-height: 1.6;
    color: #333;
}

.comment-actions {
    display: flex;
    gap: 16px;
}

.action-link {
    display: flex;
    align-items: center;
    gap: 4px;
    background: none;
    border: none;
    color: #666;
    cursor: pointer;
    font-size: 0.9rem;
}

.action-link:hover {
    color: #42b983;
}

/* 相关商品推荐 */
.products-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 24px;
}

.product-card {
    border-radius: 12px;
    overflow: hidden;
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    transition: transform 0.3s ease;
    cursor: pointer;
}

.product-card:hover {
    transform: translateY(-5px);
}

.product-image {
    position: relative;
    padding-top: 75%;
    overflow: hidden;
}

.product-image img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.hover-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(66, 185, 131, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.product-card:hover .hover-overlay {
    opacity: 1;
}

.hover-overlay span {
    color: white;
    font-size: 1rem;
    padding: 8px 16px;
    border: 2px solid white;
    border-radius: 20px;
}

.product-info {
    padding: 16px;
}

.product-title {
    font-size: 1.1rem;
    color: #2c3e50;
    margin-bottom: 8px;
}

.product-desc {
    font-size: 0.9rem;
    color: #666;
    line-height: 1.5;
}

@media (max-width: 768px) {
    .content-container {
        padding: 20px;
    }

    .case-title {
        font-size: 1.8rem;
    }

    .case-intro,
    .comments-section,
    .related-products {
        padding: 20px;
    }

    .products-grid {
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    }
}
</style>