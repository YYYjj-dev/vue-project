<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>添加剂管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新添加剂</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索添加剂名称...">
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>类型</th>
            <th>描述</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in additivesList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.description }}</td>
            <td>{{ item.createTime }}</td>
            <td>
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button class="delete-btn" @click="handleDelete(item.id)">删除</button>
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
        <h3>{{ isEditing ? '编辑添加剂' : '添加新添加剂' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>名称：</label>
            <input type="text" v-model="formData.name" required>
          </div>
          <div class="form-group">
            <label>类型：</label>
            <select v-model="formData.type" required>
              <option value="type1">类型1</option>
              <option value="type2">类型2</option>
              <option value="type3">类型3</option>
            </select>
          </div>
          <div class="form-group">
            <label>描述：</label>
            <textarea v-model="formData.description" required></textarea>
          </div>
          <div class="form-group">
            <label>图片：</label>
            <input type="file" @change="handleImageUpload">
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
const additivesList = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const searchQuery = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const formData = ref({
  name: '',
  type: '',
  description: '',
  image: null
})

// 获取数据
const fetchData = async () => {
  try {
    const { data } = await request.get('additive/findAllAdditives')
    additivesList.value = data.data
    // 这里可以处理分页数据
  } catch (error) {
    console.error('获取数据失败：', error)
  }
}

// 搜索
const handleSearch = () => {
  // 实现搜索逻辑
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
    name: '',
    type: '',
    description: '',
    image: null
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
  if (confirm('确定要删除这条记录吗？')) {
    try {
      await request.delete(`additive/delete/${id}`)
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
    }
  }
}

// 处理图片上传
const handleImageUpload = (event) => {
  const file = event.target.files[0]
  if (file) {
    formData.value.image = file
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await request.put(`additive/update/${formData.value.id}`, formData.value)
    } else {
      await request.post('additive/add', formData.value)
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
    name: '',
    type: '',
    description: '',
    image: null
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.manage-container {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.add-btn {
  background-color: #42b983;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.search-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.search-bar input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-btn {
  background-color: #2c3e50;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.edit-btn, .delete-btn {
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

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.pagination button {
  padding: 8px 15px;
  border: 1px solid #ddd;
  background-color: white;
  cursor: pointer;
  border-radius: 4px;
}

.pagination button:disabled {
  background-color: #f8f9fa;
  cursor: not-allowed;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-group textarea {
  height: 100px;
}

.dialog-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.submit-btn,
.cancel-btn {
  padding: 8px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-btn {
  background-color: #42b983;
  color: white;
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
}
</style> 