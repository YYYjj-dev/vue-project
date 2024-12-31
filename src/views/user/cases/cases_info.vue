<template>
    <div class="cases-info">
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="content-container">
            <!-- 案例标题和发布信息 -->
            <div class="case-header">
                <h1 class="case-title">{{ caseInfo.title }}</h1>
                <div class="publish-meta">
                    <span class="publish-date">发布时间：{{ caseInfo.date }}</span>
                    <span class="view-count">浏览量：2.3k</span>
                </div>
            </div>

            <!-- 案例简介 -->
            <div class="case-intro">
                <div class="case-image" v-if="caseInfo.imgpath">
                    <img :src="baseUrl + caseInfo.imgpath" :alt="caseInfo.title">
                </div>

                <h2 class="section-title">案例简介</h2>
                <div class="intro-content">
                    <div class="content-paragraphs">
                        {{ formatContent(caseInfo.content) }}
                    </div>
                </div>
            </div>

            <!-- 评论区域 -->
            <div class="comment-section">
                <div class="comment-header">
                    <div class="section-title">
                        <h2>评论区</h2>
                        <span class="comment-count">{{ comments.length }} 条评论</span>
                    </div>
                </div>

                <!-- 写评论 -->
                <div class="write-comment">
                    <div class="comment-input">
                        <textarea v-model="newComment" :placeholder="isLoggedIn ? '写下你的评论...' : '请先登录后再发表评论...'"
                            :disabled="!isLoggedIn" @keyup.ctrl.enter="submitComment">
                        </textarea>
                        <button class="submit-btn" @click="submitComment" :disabled="!isLoggedIn">
                            发布评论
                        </button>
                    </div>
                </div>

                <!-- 评论列表 -->
                <div class="comments-list">
                    <div v-if="loading" class="loading-state">
                        加载中...
                    </div>
                    <template v-else>
                        <div v-if="comments.length === 0" class="no-comments">
                            暂无评论，来发表第一条评论吧！
                        </div>
                        <div v-else v-for="comment in comments" :key="comment.id" class="comment-item">
                            <div class="comment-user">
                                <div class="user-avatar">
                                    <template v-if="comment.img">
                                        <img :src="baseUrl + comment.img" :alt="comment.username"
                                            @error="e => e.target.parentElement.innerHTML = comment.username?.charAt(0).toUpperCase()" />
                                    </template>
                                    <template v-else>
                                        {{ comment.username?.charAt(0).toUpperCase() }}
                                    </template>
                                </div>
                                <div class="user-info">
                                    <span class="username">{{ comment.username }}</span>
                                </div>
                            </div>
                            <div class="comment-content">
                                {{ comment.content }}
                            </div>
                            <div class="comment-footer">
                                <span class="comment-time">{{ comment.date }}</span>
                            </div>
                        </div>
                    </template>
                </div>
            </div>

            <!-- 相关商品推荐 -->
            <div class="related-products">
                <h2 class="section-title">相关商品推荐</h2>
                <div class="products-grid">
                    <div class="product-card" v-for="product in relatedProducts" :key="product.id"
                        @click="goToProduct(product.id)">
                        <div class="product-image">
                            <img :src="baseUrl + product.imgpath" :alt="product.name">
                            <div class="hover-overlay">
                                <span>查看详情</span>
                            </div>
                        </div>
                        <div class="product-info">
                            <h3 class="product-title">{{ product.name }}</h3>
                            <p v-if="product.description" class="product-desc">{{ product.description }}</p>
                            <p v-if="product.price" class="product-price">¥{{ product.price }}</p>
                        </div>
                    </div>
                </div>
                <div v-if="!relatedProducts.length" class="no-products">
                    <p>暂无相关商品推荐</p>
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>

<script setup>
import NavBar from '../../../components/NavBar.vue'
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import request from '../../../utils/request'
import { defineUser } from '../../../store/userStore'
import Footer from '../../../components/Footer.vue'

const router = useRouter()
const route = useRoute()
const baseUrl = 'http://localhost:8080/image/'

// 添加用户store
const userStore = defineUser()

// 添加登录状态检查
const isLoggedIn = ref(false)
const loading = ref(false)

// 定义评论类型常量
const COMMENT_TYPE = {
    NEWS: "news",      // 新闻评论
    CASE: "case",      // 案例评论
    SHANGPIN: "shangpin" // 商品评论
}

// 检查登录状态的函数
const checkLoginStatus = () => {
    isLoggedIn.value = !!userStore.token
}

// 监听用户token变化
watch(() => userStore.token, (newToken) => {
    isLoggedIn.value = !!newToken
})

// 定义响应式数据
const caseInfo = ref({
    id: '',
    title: '',
    content: '',
    imgpath: '',
    grouptype: '',
    date: ''
})

const newComment = ref('')
const comments = ref([])
const relatedProducts = ref([])

// 获取案例详情
const getCaseInfo = async () => {
    try {
        const { data } = await request.get('/info/findCasesById', {
            params: { id: route.params.id }
        })
        if (data.code === 200 && data.data) {
            console.log('后端返回的案例数据:', data.data)
            caseInfo.value = data.data
            // 检查分组类型是否存在
            if (!data.data.grouptype) {
                console.warn('案例数据中缺少grouptype字段')
                return
            }
            // 获取评论数据
            await fetchComments()
            // 获取相关商品数据
            await getRelatedProducts(data.data.grouptype)
            console.log('案例分组类型:', data.data.grouptype)
        }
    } catch (error) {
        console.error('获取案例详情失败:', error)
    }
}

// 获取评论数据
const fetchComments = async () => {
    try {
        const response = await request.get('/info/showComment', {
            params: {
                commentId: route.params.id,
                commentType: "case"
            }
        })

        console.log('评论接口返回数据:', response.data)

        if (response.data && response.data.code === 200) {
            comments.value = Array.isArray(response.data.data) ? response.data.data : []
            console.log('处理后的评论数据:', comments.value)
        } else {
            console.warn('获取评论失败:', response.data)
            comments.value = []
        }
    } catch (error) {
        console.error('获取评论失败:', error)
        comments.value = []
    }
}

// 获取相关商品数据
const getRelatedProducts = async (groupType) => {
    try {
        console.log('正在获取商品，分组类型:', groupType)
        if (!groupType) {
            console.warn('未获取到分组类型')
            return
        }

        // 打印完整的请求URL和参数
        const requestUrl = '/shangpin/findShangpinByGroup'
        console.log('发送请求:', {
            url: requestUrl,
            params: { 'group': groupType }
        })

        const { data } = await request.get(requestUrl, {
            params: {
                'group': groupType
            }

        })


        // 打印完整的响应数据
        console.log('接收到的响应:', {
            status: data.code,
            data: data.data,
            fullResponse: data
        })

        if (data.code === 200) {
            // 处理返回的数据
            if (data.data && Array.isArray(data.data)) {
                relatedProducts.value = data.data
            } else if (data.data && data.data.itemList) {
                relatedProducts.value = data.data.itemList
            } else {
                relatedProducts.value = []
            }

            if (!relatedProducts.value.length) {
                console.warn('未找到相关商品')
            } else {
                console.log('成功获取到商品:', relatedProducts.value.length, '条记录')
            }
        }
    } catch (error) {
        console.error('获取相关商品失败:', {
            error: error.response?.data || error,
            groupType,
            fullError: error,
            stack: error.stack
        })
    }
}

// 跳转到商品详情
const goToProduct = (id) => {
    router.push(`/shop_rec_info/${id}`)
}

// 添加获取当前日期的函数
const getCurrentDate = () => {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 修改提交评论的方法
const submitComment = async () => {
    if (!userStore.token) {
        alert('请先登录后再评论')
        return
    }

    if (!newComment.value.trim()) {
        alert('请输入评论内容')
        return
    }

    try {
        const commentData = {
            userId: userStore.id,
            caseId: route.params.id,
            content: newComment.value.trim(),
            date: getCurrentDate()
        }

        const tokenInfo = {
            token: userStore.token,
            commentInfo: commentData
        }

        const response = await request.post('/info/addComment', tokenInfo)

        if (response.data && response.data.code === 200) {
            await fetchComments()  // 重新获取评论列表
            newComment.value = ''  // 清空输入框
        } else {
            alert(response.data.message || '发布评论失败')
        }
    } catch (error) {
        console.error('发布评论失败:', error)
        alert('发布评论失败，请稍后重试')
    }
}

// 初始化数据
const initData = async () => {
    try {
        loading.value = true
        await getCaseInfo()
        await fetchComments()
    } catch (error) {
        console.error('初始化数据失败:', error)
    } finally {
        loading.value = false
    }
}

// 格式化内容的方法
const formatContent = (content) => {
    if (!content) return ''
    return content.split('\n').filter(Boolean).join('\n\n')
}

// 在组件挂载时初始化
onMounted(() => {
    checkLoginStatus()  // 检查登录状态
    initData()         // 初始化数据
})
</script>

<style scoped>
.cases-info {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 80px;
}

.nav-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
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
    white-space: pre-wrap;
    padding: 20px;
    background: #fafafa;
    border-radius: 12px;
}

.content-paragraphs {
    text-align: justify;
    text-indent: 2em;
}

.content-paragraphs::first-letter {
    font-size: 1.5em;
    font-weight: 500;
    color: #42b983;
    padding-right: 4px;
}

/* 段落间距 */
.content-paragraphs p {
    margin-bottom: 1.5em;
}

.content-paragraphs p:last-child {
    margin-bottom: 0;
}

/* 处理段落文本 */
.content-paragraphs>p {
    text-indent: 2em;
    margin-bottom: 1.5em;
    line-height: 1.8;
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

.no-products {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 1.1rem;
    width: 100%;
}

.product-price {
    color: #e74c3c;
    font-weight: 500;
    margin-top: 8px;
}

/* 案例图片样式 */
.case-image {
    width: 100%;
    max-height: 400px;
    margin: 0 auto 30px;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f8f9fa;
}

.case-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
    display: block;
    transition: transform 0.3s ease;
}

.case-image:hover img {
    transform: scale(1.02);
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

    .intro-content {
        padding: 15px;
        font-size: 1rem;
        text-indent: 2em;
    }

    .content-paragraphs::first-letter {
        font-size: 1.3em;
    }

    .case-image {
        max-height: 300px;
        margin-bottom: 20px;
    }
}

@media (max-width: 480px) {
    .intro-content {
        padding: 12px;
        font-size: 0.95rem;
    }

    .content-paragraphs p {
        margin-bottom: 1.2em;
    }

    .case-image {
        max-height: 200px;
        margin-bottom: 15px;
    }
}

/* 评论区域样式 */
.comment-section {
    background: white;
    border-radius: 16px;
    padding: 40px;
    margin-bottom: 60px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.comment-header {
    margin-bottom: 20px;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 12px;
}

.section-title h2 {
    font-size: 1.5rem;
    color: #2c3e50;
    margin: 0;
}

.comment-count {
    color: #666;
    font-size: 0.9rem;
}

/* 写评论区域 */
.write-comment {
    margin-bottom: 40px;
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

.submit-btn:hover:not(:disabled) {
    background: #3aa876;
}

/* 评论列表 */
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
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    font-weight: 500;
    overflow: hidden;
}

.user-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.user-info {
    display: flex;
    flex-direction: column;
    gap: 4px;
}

.username {
    font-weight: 500;
    color: #333;
    font-size: 14px;
}

.comment-content {
    margin: 12px 0;
    line-height: 1.6;
    color: #333;
}

.comment-footer {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    margin-top: 8px;
}

.comment-time {
    font-size: 12px;
    color: #999;
}

/* 加载和无评论状态 */
.loading-state,
.no-comments {
    text-align: center;
    padding: 40px;
    color: #999;
    font-size: 14px;
    background: #f8f9fa;
    border-radius: 8px;
}

/* 禁用状态样式 */
.comment-input textarea:disabled {
    background-color: #f5f5f5;
    cursor: not-allowed;
}

.submit-btn:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.submit-btn:disabled:hover {
    background-color: #ccc;
}

/* 响应式调整 */
@media (max-width: 768px) {
    .comment-section {
        padding: 20px;
    }

    .section-title h2 {
        font-size: 1.2rem;
    }

    .comment-input textarea {
        height: 100px;
    }
}

@media (max-width: 480px) {
    .comment-section {
        padding: 15px;
    }

    .comment-item {
        padding: 15px;
    }

    .user-avatar {
        width: 32px;
        height: 32px;
    }
}
</style>