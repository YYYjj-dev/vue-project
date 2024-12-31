<template>
    <div class="news-info">
        <div class="nav-wrapper">
            <NavBar />
        </div>

        <div class="content-container">
            <!-- 标题 -->
            <div class="title-container" v-if="newsData">
                <h1 class="news-title">{{ newsData.title }}</h1>
                <div class="news-meta">
                    <span class="publish-date">发布时间：{{ newsData.date }}</span>
                    <span class="read-count">阅读量：1.2k</span>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="main-content" v-if="newsData">
                <div class="content-box">
                    <div class="content-paragraphs">
                        {{ formatContent(newsData.content) }}
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
                            :disabled="!isLoggedIn" @keyup.ctrl.enter="submitComment"></textarea>
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

            <!-- 相关文章 -->
            <div class="related-articles" v-if="relatedNews.length">
                <h2>相关文章</h2>
                <div class="articles-grid">
                    <div class="article-card" v-for="article in relatedNews" :key="article.id"
                        @click="goToNews(article.id)">
                        <h3>{{ article.title }}</h3>
                        <p>{{ article.content }}</p>
                        <span class="date">{{ article.date }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>

<script setup name='news_info' components="NavBar">
import { ref, onMounted, watch } from 'vue';
import NavBar from '../../../components/NavBar.vue'
import { useRoute, useRouter } from 'vue-router'
import request from '../../../utils/request'
import Footer from '../../../components/Footer.vue'
import { defineUser } from '../../../store/userStore'

const route = useRoute()
const router = useRouter()
const baseUrl = 'http://localhost:8080/image/'
const newsData = ref(null)
const relatedNews = ref([])
const loading = ref(false)
const comments = ref([])
const newComment = ref('')

// 定义评论类型常量
const COMMENT_TYPE = {
    NEWS: "news",      // 新闻评论
    CASE: "case",      // 案例评论
    SHANGPIN: "shangpin" // 商品评论
}

// 添加用户store
const userStore = defineUser()

// 修改登录状态检查
const isLoggedIn = ref(false)

// 检查登录状态的函数
const checkLoginStatus = () => {
    isLoggedIn.value = !!userStore.token
}

// 监听用户token变化
watch(() => userStore.token, (newToken) => {
    isLoggedIn.value = !!newToken
})

// 获取新闻详情
const getNewsDetail = async () => {
    try {
        const { data } = await request.get(`/info/news/findNewsById?id=${route.params.id}`)
        if (data.code === 200) {
            newsData.value = data.data
            // 获取新闻详情后，获取相关文章
            await getRelatedNews(data.data.type)
        }
    } catch (error) {
        console.error('获取新闻详情失败:', error)
    } finally {
        loading.value = false
    }
}

// 获取相关文章
const getRelatedNews = async (type) => {
    try {
        const { data } = await request.get('/info/news/findNewsByType', {
            params: { type }
        })
        if (data.code === 200) {
            // 过滤掉当前文章，并只取3篇
            relatedNews.value = data.data
                .filter(item => item.id !== parseInt(route.params.id))
                .slice(0, 3)
            console.log('当前文章ID:', route.params.id)
            console.log('相关文章:', relatedNews.value)
        }
    } catch (error) {
        console.error('获取相关文章失败:', error)
        console.error('当前文章ID:', route.params.id)
        console.error('所有文章:', data?.data)
    }
}

// 跳转到新闻详情
const goToNews = (id) => {
    // 刷新页面以确保数据重新加载
    router.push({
        name: 'news_info',
        params: { id: id.toString() }
    }).then(() => {
        window.location.reload()
    })
}

// 添加获取用户信息的方法
const getUserInfo = async (userId) => {
    try {
        const response = await request.get('/user/findUser', {
            params: { id: userId }
        })
        if (response.data && response.data.code === 200) {
            return response.data.data
        }
        return null
    } catch (error) {
        console.error('获取用户信息失败:', error)
        return null
    }
}

// 获取评论数据
const fetchComments = async () => {
    try {
        const response = await request.get('/info/showComment', {
            params: {
                commentId: route.params.id,
                commentType: "news"
            }
        })

        console.log('评论接口返回数据:', response.data)

        if (response.data && response.data.code === 200) {
            // 直接使用后台返回的数据，包含日期
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

// 添加获取当前日期的函数
const getCurrentDate = () => {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 修改发布评论的方法
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
            newsId: route.params.id,
            content: newComment.value.trim(),
            date: getCurrentDate(),
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
            console.error('评论失败:', response.data)
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
        await getNewsDetail()
        await fetchComments()
    } catch (error) {
        console.error('初始化数据失败:', error)
    } finally {
        loading.value = false
    }
}

// 在组件挂载时检查登录状态
onMounted(() => {
    checkLoginStatus()
    initData()
})

//newsData []

const formatContent = (content) => {
    if (!content) return '';
    // 将连续的换行符替换为单个换行符，并按段落分割
    return content.replace(/\n\s*\n/g, '\n').split('\n').map(paragraph =>
        paragraph.trim()
    ).filter(paragraph =>
        paragraph.length > 0
    ).join('\n\n');
}

// 添加头像错误处理函数
const handleAvatarError = (e) => {
    const parent = e.target.parentElement
    if (parent) {
        e.target.style.display = 'none'
        parent.innerHTML = e.target.alt?.charAt(0).toUpperCase() || '?'
    }
}

</script>

<style scoped>
.news-info {
    min-height: 100vh;
    background-color: #f8f9fa;
    padding-top: 80px;
}

/* 导航栏包装器样式 */
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

/* 标题样式 */
.title-container {
    margin-bottom: 40px;
    text-align: center;
}

.news-title {
    font-size: 2.5rem;
    color: #2c3e50;
    margin-bottom: 16px;
    font-weight: 600;
}

.news-meta {
    color: #666;
    font-size: 0.9rem;
}

.news-meta span {
    margin: 0 10px;
}

/* 主要内容区域 */
.main-content {
    margin-bottom: 60px;
}

.content-box {
    background: white;
    border-radius: 16px;
    padding: 40px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
    color: #333;
}

/* 正文段落样式 */
.content-paragraphs {
    text-align: justify;
    line-height: 1.8;
    font-size: 1.1rem;
    white-space: pre-wrap;
    padding: 20px;
    background: #fafafa;
    border-radius: 12px;
    letter-spacing: 0.5px;
}

/* 段落样式 */
.content-paragraphs p {
    text-indent: 2em;
    margin-bottom: 1.5em;
}

/* 确保最后一个段落没有底部边距 */
.content-paragraphs p:last-child {
    margin-bottom: 0;
}

/* 评论区域 */
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

.action-buttons {
    display: flex;
    gap: 16px;
}

.action-btn {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 20px;
    border: 1px solid #e0e0e0;
    border-radius: 20px;
    background: white;
    color: #666;
    cursor: pointer;
    transition: all 0.3s ease;
}

.action-btn:hover {
    background: #f5f5f5;
    color: #42b983;
    border-color: #42b983;
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

.submit-btn:hover {
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
    text-transform: uppercase;
    /* 确保字母大写 */
}

.user-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    /* 添加这行 */
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

/* 相关文章 */
.related-articles {
    margin-top: 60px;
    padding: 40px 0;
    border-top: 1px solid #eee;
}

.related-articles h2 {
    font-size: 24px;
    color: #2c3e50;
    margin-bottom: 30px;
    position: relative;
    padding-left: 15px;
}

.related-articles h2::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 20px;
    background: #42b983;
    border-radius: 2px;
}

.articles-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}

.article-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    transition: all 0.3s ease;
    border: 1px solid #eee;
    cursor: pointer;
}

.article-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    border-color: #42b983;
}

.article-card h3 {
    font-size: 18px;
    color: #2c3e50;
    margin-bottom: 10px;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.article-card p {
    font-size: 14px;
    color: #666;
    margin-bottom: 15px;
    line-height: 1.6;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.article-card .date {
    font-size: 12px;
    color: #999;
}

@media (max-width: 768px) {
    .articles-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 480px) {
    .articles-grid {
        grid-template-columns: 1fr;
    }
}

/* 添加无评论状态的样式 */
.no-comments {
    text-align: center;
    padding: 40px;
    color: #999;
    font-size: 14px;
    background: #f8f9fa;
    border-radius: 8px;
}

.loading-state {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 14px;
}

/* 修改评论相关样式 */
.comment-item {
    padding: 24px;
    border-bottom: 1px solid #eee;
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

/* 移除不需要的样式 */
.comment-meta {
    display: none;
}

/* 添加禁用状态样式 */
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
</style>