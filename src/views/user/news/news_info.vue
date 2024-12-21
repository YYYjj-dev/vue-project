<template>
    <div class="news-info">
        <NavBar />
        
        <div class="content-container">
            <!-- 标题 -->
            <div class="title-container" v-if="newsData">
                <h1 class="news-title">{{ newsData.title }}</h1>
                <div class="news-meta">
                    <span class="publish-date">{{ newsData.date }}</span>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="main-content" v-if="newsData">
                <div class="content-box">
                    <p>{{ newsData.content }}</p>
                </div>
            </div>

            <!-- 评论区域 -->
            <div class="comment-section">
                <div class="comment-header">
                    <div class="section-title">
                        <h2>评论区</h2>
                        <span class="comment-count">{{ comments.length }}条评论</span>
                    </div>
                </div>

                <!-- 评论列表 -->
                <div class="comments-list">
                    <div class="comment-item" v-for="comment in comments" :key="comment.id">
                        <div class="comment-user">
                            <div class="user-info">
                                <span class="username">用户ID: {{ comment.userId }}</span>
                                <span class="comment-time">{{ comment.date }}</span>
                            </div>
                        </div>
                        <div class="comment-content">
                            {{ comment.content }}
                        </div>
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
    name: 'news_info',
    components: {
        NavBar
    },
    data() {
        return {
            newsData: null,
            comments: []
        }
    },
    created() {
        const newsId = this.$route.params.id;
        console.log('新闻ID:', newsId);
        this.loadNewsInfo(newsId);
        this.loadComments(newsId);
    },
    methods: {
        loadNewsInfo(id) {
            console.log('开始请求新闻详情，ID:', id);
            
            // 修改请求路径，使用与新闻列表相同的接口
            request.get("/new")
                .then(res => {
                    console.log('获取到的响应:', res);
                    
                    if(res.code === '0') {
                        // 从返回的数据中找到对应 id 的新闻
                        const newsItem = res.data.find(item => item.id === parseInt(id));
                        if (newsItem) {
                            this.newsData = newsItem;
                            console.log('找到的新闻数据:', this.newsData);
                        } else {
                            console.error('未找到对应ID的新闻');
                        }
                    } else {
                        console.error('获取新闻详情失败:', res.msg);
                    }
                })
                .catch(error => {
                    console.error('请求新闻详情出错:', error);
                });
        },
        loadComments(newsId) {
            request.get(`/comment/news/${newsId}`)
                .then(res => {
                    console.log('获取到的评论:', res);
                    if (res.code === '0') {
                        this.comments = res.data;
                    } else {
                        console.error('获取评论失败:', res.msg);
                        this.comments = [];
                    }
                })
                .catch(error => {
                    console.error('请求评论出错:', error);
                    this.comments = [];
                });
        }
    }
}
</script>

<style scoped>
.news-info {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
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
    line-height: 1.8;
    font-size: 1.1rem;
    color: #333;
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

/* 相关文章 */
.related-articles {
    background: white;
    border-radius: 16px;
    padding: 40px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.articles-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 24px;
}

.article-card {
    border-radius: 12px;
    overflow: hidden;
    transition: transform 0.3s ease;
    cursor: pointer;
}

.article-card:hover {
    transform: translateY(-5px);
}

.article-image {
    height: 160px;
    background: #f0f0f0;
}

.article-info {
    padding: 20px;
}

.article-title {
    font-size: 1.1rem;
    color: #2c3e50;
    margin-bottom: 8px;
}

.article-excerpt {
    font-size: 0.9rem;
    color: #666;
    margin-bottom: 12px;
    line-height: 1.5;
}

.article-meta {
    display: flex;
    justify-content: space-between;
    color: #999;
    font-size: 0.8rem;
}

@media (max-width: 768px) {
    .content-container {
        padding: 20px;
    }

    .news-title {
        font-size: 1.8rem;
    }

    .content-box {
        padding: 20px;
    }

    .articles-grid {
        grid-template-columns: 1fr;
    }

    .comment-section,
    .related-articles {
        padding: 20px;
    }
}

/* 添加评论列表样式 */
.comments-list {
    margin-top: 20px;
}

.comment-item {
    padding: 20px;
    border-bottom: 1px solid #eee;
}

.comment-item:last-child {
    border-bottom: none;
}

.comment-user {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
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
    font-size: 12px;
    color: #999;
    margin-top: 4px;
}

.comment-content {
    color: #666;
    line-height: 1.6;
}

.section-title {
    display: flex;
    align-items: center;
    gap: 12px;
}

.comment-count {
    color: #666;
    font-size: 14px;
}
</style>