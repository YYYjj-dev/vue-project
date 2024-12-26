<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>新闻管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新闻</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索新闻标题...">
      <select v-model="searchType" class="search-select">
        <option value="">所有类型</option>
        <option value="公司新闻">公司新闻</option>
        <option value="行业动态">行业动态</option>
        <option value="政策法规">政策法规</option>
      </select>
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>标题</th>
            <th>类型</th>
            <th>发布日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in newsList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.date }}</td>
            <td class="operation-group">
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button class="delete-btn" @click="handleDelete(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑对话框 -->
    <div v-if="showDialog" class="dialog-overlay">
      <div class="dialog">
        <h3>{{ isEditing ? '编辑新闻' : '添加新闻' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>标题：</label>
            <input type="text" v-model="formData.title" required>
          </div>
          <div class="form-group">
            <label>类型：</label>
            <select v-model="formData.type" required>
              <option value="食品安全">食品安全</option>
              <option value="健康饮食">健康饮食</option>
              <option value="科学认知">政策法规</option>
            </select>
          </div>
          <div class="form-group">
            <label>发布日期：</label>
            <input type="date" v-model="formData.date" required>
          </div>
          <div class="form-group">
            <label>内容：</label>
            <textarea v-model="formData.content" required rows="6"></textarea>
          </div>
          <div class="dialog-buttons">
            <button type="submit" class="submit-btn">提交</button>
            <button type="button" class="cancel-btn" @click="closeDialog">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'
import '../../../src/style/admin-common.css'

// 数据状态
const newsList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const formData = ref({
  id: '',
  title: '',
  type: '',
  date: '',
  content: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('info/findAllNews')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      // 如果data直接就是数组
      if (Array.isArray(response.data)) {
        newsList.value = response.data
      } 
      // 如果data是对象，且包含data属性
      else if (response.data.data && Array.isArray(response.data.data)) {
        newsList.value = response.data.data
      } 
      // 如果data是单个对象，转换为数组
      else if (typeof response.data === 'object') {
        newsList.value = [response.data]
      }
      else {
        newsList.value = []
      }
      console.log('处理后的数据列表：', newsList.value)
    } else {
      console.error('API返回数据格式不正确：', response)
      newsList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    newsList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    if (searchQuery.value) {
      const response = await request.get(`info/findNewsByTitle?title=${searchQuery.value}`)
      if (response.data) {
        newsList.value = Array.isArray(response.data) ? response.data : [response.data]
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    title: '',
    type: '',
    date: '',
    content: ''
  }
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`info/findNewsById?id=${item.id}`)
    console.log('获取到的新闻详情：', response)
    
    if (response.data) {
      isEditing.value = true
      const newsData = response.data.data || response.data
      formData.value = { 
        ...newsData,
        date: newsData.date ? newsData.date.split('T')[0] : ''
      }
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取新闻详情失败：', error)
    alert('获取新闻详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这条新闻吗？')) {
    try {
      await request.get(`info/deleteNews?id=${id}`)
      alert('删除成功')
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
      alert('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    const submitData = {
      id: formData.value.id,
      title: formData.value.title,
      type: formData.value.type,
      content: formData.value.content,
      date: formData.value.date || new Date().toISOString().split('T')[0]
    }
    
    console.log('提交的数据：', submitData)

    if (isEditing.value) {
      const response = await request.post('info/updateNews', submitData)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      delete submitData.id
      const response = await request.post('info/addNews', submitData)
      console.log('添加响应：', response)
      alert('添加成功')
    }
    closeDialog()
    await fetchData()
  } catch (error) {
    console.error('提交失败：', error)
    alert('操作失败，请重试')
  }
}

// 关闭对话框
const closeDialog = () => {
  showDialog.value = false
  formData.value = {
    id: '',
    title: '',
    type: '',
    date: '',
    content: ''
  }
}

onMounted(async () => {
  await fetchData()
})
</script>

<style scoped>
@import '../../../src/style/admin-common.css';

.manage-container {
  min-height: 500px;
}

.operation-group {
  display: flex;
  gap: 8px;
}

.dialog {
  max-height: 80vh;
  overflow-y: auto;
}
</style>