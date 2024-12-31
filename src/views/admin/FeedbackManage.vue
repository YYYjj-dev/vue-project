<template>
    <div class="manage-container">
        <div class="page-header">
            <h2>用户反馈管理</h2>
        </div>

        <!-- 搜索栏 -->
        <div class="search-bar">
            <input type="text" v-model="searchId" placeholder="输入反馈ID搜索..." class="search-input">
            <button class="btn" @click="handleSearch">搜索</button>
            <button class="btn" @click="loadAllFeedback">重置</button>
        </div>

        <!-- 数据表格 -->
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>类型</th>
                        <th>内容</th>
                        <th>邮箱</th>
                        <th>电话</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in feedbackList" :key="item.id">
                        <td>{{ item.id }}</td>
                        <td>
                            <span :class="getTypeClass(item.type)">{{ item.type }}</span>
                        </td>
                        <td>{{ item.content }}</td>
                        <td>{{ item.email }}</td>
                        <td>{{ item.phone }}</td>
                        <td class="operation-group">
                            <button class="delete-btn" @click="handleDelete(item.id)">删除</button>
                            <button class="edit-btn" @click="showDetail(item)">详情</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 详情对话框 -->
        <div v-if="showDialog" class="dialog-overlay">
            <div class="dialog">
                <h3>反馈详情</h3>
                <div class="feedback-detail">
                    <div class="detail-item">
                        <span class="label">ID：</span>
                        <span>{{ currentFeedback.id }}</span>
                    </div>
                    <div class="detail-item">
                        <span class="label">类型：</span>
                        <span :class="getTypeClass(currentFeedback.type)">
                            {{ currentFeedback.type }}
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="label">邮箱：</span>
                        <span>{{ currentFeedback.email }}</span>
                    </div>
                    <div class="detail-item">
                        <span class="label">电话：</span>
                        <span>{{ currentFeedback.phone }}</span>
                    </div>
                    <div class="detail-item content">
                        <span class="label">反馈内容：</span>
                        <p>{{ currentFeedback.content }}</p>
                    </div>
                </div>
                <div class="dialog-buttons">
                    <button class="btn cancel-btn" @click="closeDialog">关闭</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const feedbackList = ref([])
const searchId = ref('')
const showDialog = ref(false)
const currentFeedback = ref({})

// 加载所有反馈
const loadAllFeedback = async () => {
    try {
        const response = await request.get('/info/findAllFeedback')
        if (response.data?.code === 200) {
            feedbackList.value = response.data.data || []
        } else {
            alert('获取反馈列表失败')
        }
    } catch (error) {
        console.error('加载反馈列表错误:', error)
        alert('加载反馈列表失败')
    }
}

// 搜索
const handleSearch = async () => {
    if (!searchId.value.trim()) {
        loadAllFeedback()
        return
    }

    try {
        const response = await request.get('/info/findFeedbackById', {
            params: { id: searchId.value.trim() }
        })
        if (response.data?.code === 200) {
            feedbackList.value = response.data.data ? [response.data.data] : []
        } else {
            alert('未找到相关反馈')
            feedbackList.value = []
        }
    } catch (error) {
        console.error('搜索反馈错误:', error)
        alert('搜索反馈失败')
    }
}

// 删除反馈
const handleDelete = async (id) => {
    if (confirm('确定要删除这条反馈吗？')) {
        try {
            const response = await request.get('/info/deleteFeedback', {
                params: { id }
            })
            if (response.data?.code === 200) {
                alert('删除成功')
                loadAllFeedback()
            } else {
                alert('删除失败')
            }
        } catch (error) {
            console.error('删除反馈错误:', error)
            alert('删除失败')
        }
    }
}

// 显示详情
const showDetail = (item) => {
    currentFeedback.value = { ...item }
    showDialog.value = true
}

// 关闭对话框
const closeDialog = () => {
    showDialog.value = false
    currentFeedback.value = {}
}

// 获取类型样式
const getTypeClass = (type) => {
    const typeMap = {
        '功能建议': 'status-success',
        '问题反馈': 'status-warning',
        '其他': 'status-info'
    }
    return ['status-tag', typeMap[type] || 'status-info']
}

onMounted(() => {
    loadAllFeedback()
})
</script>

<style scoped>
@import '../../../src/style/admin-common.css';

.feedback-detail {
    padding: 20px;
}

.detail-item {
    margin-bottom: 15px;
    display: flex;
    align-items: flex-start;
}

.detail-item .label {
    font-weight: bold;
    width: 100px;
    color: #666;
}

.detail-item.content {
    flex-direction: column;
}

.detail-item.content .label {
    margin-bottom: 10px;
}

.detail-item.content p {
    margin: 0;
    padding: 15px;
    background: #f8f9fa;
    border-radius: 4px;
    width: 100%;
    white-space: pre-wrap;
}

.status-info {
    background-color: #e6f7ff;
    color: #1890ff;
    border: 1px solid #91d5ff;
}
</style>