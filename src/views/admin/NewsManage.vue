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
        <option value="健康饮食">健康饮食</option>
        <option value="科学认知">科学认知</option>
        <option value="食品安全">食品安全</option>
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
            <th>资讯类型</th>
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
        <form @submit.prevent="handleSubmit" enctype="multipart/form-data">
          <div class="form-group">
            <label>标题：</label>
            <input type="text" v-model="formData.title" required>
          </div>
          <div class="form-group">
            <label>资讯类型：</label>
            <select v-model="formData.type" required>
              <option value="健康饮食">健康饮食</option>
              <option value="科学认知">科学认知</option>
              <option value="食品安全">食品安全</option>
            </select>
          </div>
          <div class="form-group">
            <label>发布日期：</label>
            <input type="date" v-model="formData.date" required>
          </div>
          <div class="form-group">
            <label>资讯内容：</label>
            <textarea v-model="formData.content" required rows="6"></textarea>
          </div>
          <div class="form-group">
            <label>新闻图片：</label>
            <input type="file" @change="handleImageUpload" accept="image/*">
            <div v-if="imagePreview" class="image-preview">
              <img :src="imagePreview" alt="图片预览">
            </div>
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

// 数据状态
const newsList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  title: '',
  content: '',
  type: '',
  date: '',
  img: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('info/findAllNews')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        newsList.value = []
        console.error('获取数据失败：', response.data)
        return
      }
      
      if (Array.isArray(response.data)) {
        newsList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        newsList.value = response.data.data
      } else if (typeof response.data === 'object') {
        newsList.value = [response.data]
      } else {
        newsList.value = []
      }
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
    const params = {}
    if (searchQuery.value) params.title = searchQuery.value
    if (searchType.value) params.type = searchType.value
    
    if (Object.keys(params).length > 0) {
      const response = await request.get('info/findNews', { params })
      console.log('搜索返回的原始数据：', response)
      
      if (response.data) {
        if (response.data.code === 404) {
          newsList.value = []
          return
        }
        
        // 处理返回的数据结构
        if (Array.isArray(response.data)) {
          newsList.value = response.data
        } else if (response.data.data && Array.isArray(response.data.data)) {
          newsList.value = response.data.data
        } else if (typeof response.data === 'object' && !response.data.code) {
          // 如果返回单个对象且不是错误响应
          newsList.value = [response.data]
        } else {
          newsList.value = []
        }
      } else {
        newsList.value = []
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
    newsList.value = []
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    title: '',
    content: '',
    type: '',
    date: new Date().toISOString().split('T')[0],
    img: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`info/findNewsById?id=${item.id}`)
    console.log('获取到的新闻详情：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        alert('获取新闻详情失败')
        return
      }
      
      isEditing.value = true
      const newsData = response.data.data || response.data
      formData.value = { 
        ...newsData,
        date: newsData.date ? newsData.date.split('T')[0] : ''
      }
      imagePreview.value = newsData.img ? `http://localhost:8080/image/${newsData.img}` : ''
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
      alert('删��成功')
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
      alert('删除失败')
    }
  }
}

// 处理图片上传
const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      imagePreview.value = e.target.result
    }
    reader.readAsDataURL(file)
    formData.value.imageFile = file
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    const formDataToSend = new FormData()
    
    // 添加基本字段
    formDataToSend.append('title', formData.value.title)
    formDataToSend.append('content', formData.value.content)
    formDataToSend.append('type', formData.value.type)
    formDataToSend.append('date', formData.value.date)
    
    if (isEditing.value) {
      formDataToSend.append('id', formData.value.id)
      // 如果是编辑模式且没有新上传的图片，则传入原图片路径
      if (!formData.value.imageFile && formData.value.img) {
        formDataToSend.append('img', formData.value.img)
      }
    }
    
    // 添加新上传的图片文件
    if (formData.value.imageFile) {
      formDataToSend.append('file', formData.value.imageFile)
    }
    
    console.log('提交的数据：', Object.fromEntries(formDataToSend))

    if (isEditing.value) {
      const response = await request.post('info/updateNews', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('info/addNews', formDataToSend)
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
    content: '',
    type: '',
    date: '',
    img: ''
  }
  imagePreview.value = ''
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
  max-height: 90vh;
  overflow-y: auto;
}

.image-preview {
  margin-top: 10px;
  max-width: 200px;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.image-preview img {
  width: 100%;
  height: auto;
  display: block;
}
</style>