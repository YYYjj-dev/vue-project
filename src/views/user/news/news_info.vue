<template>
    <div class="news-info">
        <NavBar />
        
        <div class="content-container">
            <!-- 标题 -->
            <div class="title-container" v-if="news">
                <h1 class="news-title">{{ news.data.title }}</h1>
                <div class="news-meta">
                    <span class="publish-date" >发布时间{{ news.data.date }}</span>
                    <span class="read-count">阅读量：1.2k</span>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="main-content" v-if="news">
                <div class="content-box">
                    <p>{{ news.data.content }}</p>
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
                        <textarea v-model="newComment" placeholder="写下你的评论..."></textarea>
                        <button class="submit-btn">发布评论</button>
                    </div>
                </div>

                <!-- 评论列表 -->
                <div class="comments-list">
                    <div class="comment-item" v-for="(comment, index) in comments" :key="index">
                        <div class="comment-user">
                            <div class="user-avatar"></div>
                            <div class="user-info">
                                <span class="username">用户{{ comment.userId }}</span>
                                <span class="comment-time">{{comment.date}}</span>
                            </div>
                        </div>
                        <div class="comment-content">
                            {{ comment.content }}
                        </div>
                        <div class="comment-actions">
                            <button class="action-link">
                                <i class="icon-thumbs-up"></i>
                                <span>点赞</span>
                            </button>
                            <button class="action-link">
                                <i class="icon-reply"></i>
                                <span>回复</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 相关文章 -->
            <div class="related-articles">
                <h2 class="section-title">相关文章</h2>
                <div class="articles-grid">
                    <div class="article-card" v-for="(article, index) in relatedArticles" :key="index">
                        <div class="article-image"></div>
                        <div class="article-info">
                            <h3 class="article-title">{{ article.title }}</h3>
                            <p class="article-excerpt">这是一段相关文章的简短描述，帮助用户了解文章内容...</p>
                            <div class="article-meta">
                                <span class="read-time">5分钟阅读</span>
                                <span class="publish-date">2024-01-20</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup name='news_info' components="NavBar">
import { ref,onMounted } from 'vue';
import NavBar from '../../../components/NavBar.vue'
import {useRoute} from 'vue-router'
import request from '../../../utils/request'

    
        let route = useRoute()
        let nid=route.params.id
        let news=ref({title:'',content:'',data:''})
        let comments=ref([
        { content: '这篇文章写得很好，内容很有价值！' },
        { content: '分析得很透彻，给了我很多启发。' },
        { content: '希望能看到更多类似的深度文章。' },
        { content: '观点很新颖，值得深入思考。' }
        ])
        let relatedArticles =[
        { title: '食品添加剂的发展趋势' },
        { title: '天然添加剂的优势分析' },
        { title: '添加剂安全使用指南' },
        { title: '未来食品工业展望' }
        ]

        onMounted(()=>{
            showNews(nid)
        })
    async function showNews(id){
      let {data} = await request.get(`info/news/findNewsById?id=${id}`)
      news.value = data
      let {data:commentsData} = await request.get(`info/showComment?commentId=${id}&commentType=1`)
      comments.value = commentsData.data
    }
     
    //newsData []


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
</style>