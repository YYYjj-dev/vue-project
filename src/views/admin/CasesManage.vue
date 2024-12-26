<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>应用案例管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新案例</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索案例标题...">
      <select v-model="searchIndustry" class="search-select">
        <option value="">所有行业</option>
        <option value="食品饮料">食品饮料</option>
        <option value="医药保健">医药保健</option>
        <option value="化妆品">化妆品</option>
        <option value="日化用品">日化用品</option>
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
            <th>行业</th>
            <th>使用产品</th>
            <th>发布时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in casesList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.industry }}</td>
            <td>{{ item.product }}</td>
            <td>{{ item.publishTime }}</td>
            <td>
              <span :class="['status-tag', item.status === '已发布' ? 'published' : 'draft']">
                {{ item.status }}
              </span>
            </td>
            <td>
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button class="delete-btn" @click="handleDelete(item.id)">删除</button>
              <button 
                class="publish-btn" 
                v-if="item.status !== '已发布'"
                @click="handlePublish(item.id)">
                发布
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <button :disabled="currentPage === 1" @click="handlePageChange(currentPage - 1)">上一页</button>
      <span>第 {{ currentPage }} 页</span>
      <button :disabled="currentPage === totalPages" @click="handlePageChange(currentPage + 1)">下一页</button>
    </div>

    <!-- 添加/编辑对话框 -->
    <div v-if="showDialog" class="dialog-overlay">
      <div class="dialog">
        <h3>{{ isEditing ? '编辑案例' : '添加新案例' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>标题：</label>
            <input type="text" v-model="formData.title" required>
          </div>
          <div class="form-group">
            <label>行业：</label>
            <select v-model="formData.industry" required>
              <option value="食品饮料">食品饮料</option>
              <option value="医药保健">医药保健</option>
              <option value="化妆品">化妆品</option>
              <option value="日化用品">日化用品</option>
            </select>
          </div>
          <div class="form-group">
            <label>使用产品：</label>
            <input type="text" v-model="formData.product" required>
          </div>
          <div class="form-group">
            <label>客户名称：</label>
            <input type="text" v-model="formData.customer">
          </div>
          <div class="form-group">
            <label>项目背景：</label>
            <textarea v-model="formData.background" required rows="4"></textarea>
          </div>
          <div class="form-group">
            <label>解决方案：</label>
            <textarea v-model="formData.solution" required rows="6"></textarea>
          </div>
          <div class="form-group">
            <label>应用效果：</label>
            <textarea v-model="formData.result" required rows="4"></textarea>
          </div>
          <div class="form-group">
            <label>案例图片：</label>
            <div class="image-upload">
              <input type="file" @change="handleImageUpload" accept="image/*" multiple>
              <div class="image-preview-list" v-if="formData.images && formData.images.length">
                <div v-for="(image, index) in formData.images" :key="index" class="image-preview-item">
                  <img :src="image" :alt="'案例图片' + (index + 1)">
                  <button type="button" class="remove-image" @click="removeImage(index)">×</button>
                </div>
              </div>
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
const casesList = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const searchQuery = ref('')
const searchIndustry = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const formData = ref({
  title: '',
  industry: '',
  product: '',
  customer: '',
  background: '',
  solution: '',
  result: '',
  images: []
})

// 获取数据
const fetchData = async () => {
  try {
    const { data } = await request.get('info/findAllCases', {
      params: {
        page: currentPage.value,
        title: searchQuery.value,
        industry: searchIndustry.value
      }
    })
    casesList.value = data.data
    totalPages.value = data.totalPages || 1
  } catch (error) {
    console.error('获取数据失败：', error)
  }
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchData()
}

// 分页
const handlePageChange = (page) => {
  currentPage.value = page
  fetchData()
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    title: '',
    industry: '',
    product: '',
    customer: '',
    background: '',
    solution: '',
    result: '',
    images: []
  }
  showDialog.value = true
}

// 编辑
const handleEdit = (item) => {
  isEditing.value = true
  formData.value = { ...item }
  showDialog.value = true
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这个案例吗？')) {
    try {
      await request.delete(`cases/delete/${id}`)
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
    }
  }
}

// 发布
const handlePublish = async (id) => {
  try {
    await request.put(`cases/publish/${id}`)
    fetchData()
  } catch (error) {
    console.error('发布失败：', error)
  }
}

// 处理图片上传
const handleImageUpload = (event) => {
  const files = event.target.files
  if (files) {
    Array.from(files).forEach(file => {
      const reader = new FileReader()
      reader.onload = (e) => {
        formData.value.images.push(e.target.result)
      }
      reader.readAsDataURL(file)
    })
  }
}

// 移除图片
const removeImage = (index) => {
  formData.value.images.splice(index, 1)
}

// 提交表单
const handleSubmit = async () => {
  try {
    const formDataToSend = new FormData()
    for (const key in formData.value) {
      if (key === 'images') {
        formData.value.images.forEach(async (image, index) => {
          // 如果是新上传的图片（base64格式），需要转换为文件
          if (image.startsWith('data:')) {
            const blob = await fetch(image).then(r => r.blob())
            formDataToSend.append(`images[${index}]`, blob)
          } else {
            formDataToSend.append(`images[${index}]`, image)
          }
        })
      } else if (formData.value[key] !== null) {
        formDataToSend.append(key, formData.value[key])
      }
    }

    if (isEditing.value) {
      await request.put(`cases/update/${formData.value.id}`, formDataToSend)
    } else {
      await request.post('cases/add', formDataToSend)
    }
    closeDialog()
    fetchData()
  } catch (error) {
    console.error('提交失败：', error)
  }
}

// 关闭对话框
const closeDialog = () => {
  showDialog.value = false
  formData.value = {
    title: '',
    industry: '',
    product: '',
    customer: '',
    background: '',
    solution: '',
    result: '',
    images: []
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
/* 复用基础样式 */
.manage-container {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

/* 添加新的样式 */
.image-upload {
  margin-top: 10px;
}

.image-preview-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 10px;
  margin-top: 10px;
}

.image-preview-item {
  position: relative;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.image-preview-item img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  display: block;
}

.remove-image {
  position: absolute;
  top: 5px;
  right: 5px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  line-height: 1;
}

.remove-image:hover {
  background: rgba(0, 0, 0, 0.7);
}

/* 表单样式 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.form-group textarea {
  resize: vertical;
}

/* 状态标签 */
.status-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status-tag.published {
  background-color: #e6f6f0;
  color: #42b983;
}

.status-tag.draft {
  background-color: #f8f9fa;
  color: #6c757d;
}

/* 按钮样式 */
.edit-btn, .delete-btn, .publish-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
}

.edit-btn {
  background-color: #ffc107;
  color: #000;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
}

.publish-btn {
  background-color: #42b983;
  color: white;
}

/* 对话框样式 */
.dialog {
  max-height: 90vh;
  overflow-y: auto;
  width: 600px;
  max-width: 90%;
}

/* 搜索栏样式 */
.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-bar input,
.search-select {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-bar input {
  flex: 1;
}

.search-select {
  width: 150px;
}

.search-btn {
  background-color: #2c3e50;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
}
</style> 