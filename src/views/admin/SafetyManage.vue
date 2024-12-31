<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>安全法规管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新法规</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索法规标题...">
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>标题</th>
            <th>发布日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in safetyList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.title }}</td>
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
        <h3>{{ isEditing ? '编辑法规' : '添加新法规' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
          </div>
          <div class="form-group">
            <label>标题：</label>
            <input type="text" v-model="formData.title" required>
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
const safetyList = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const searchQuery = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const formData = ref({
  id: '',
  title: '',
  date: '',
  content: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('info/findAllRegular')
    console.log('API返回的原始数据：', response)
    
    // 检查返回的数据结构
    if (response.data) {
      // 如果data直接就是数组
      if (Array.isArray(response.data)) {
        safetyList.value = response.data
      } 
      // 如果data是对象，且包含data属性
      else if (response.data.data && Array.isArray(response.data.data)) {
        safetyList.value = response.data.data
      } 
      // 如果data是单个对象，转换为数组
      else if (typeof response.data === 'object') {
        safetyList.value = [response.data]
      }
      else {
        safetyList.value = []
      }
      console.log('处理后的数据列表：', safetyList.value)
    } else {
      console.error('API返回数据格式不正确：', response)
      safetyList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    safetyList.value = []
  }
}

// 搜索
const handleSearch = () => {
  fetchData()
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    title: '',
    date: '',
    content: ''
  }
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`info/findRegularById?id=${item.id}`)
    console.log('获取到的法规详情：', response)
    
    if (response.data) {
      isEditing.value = true
      // 如果response.data中包含data属性
      const regulationData = response.data.data || response.data
      formData.value = { 
        ...regulationData,
        // 确保日期格式正确
        date: regulationData.date ? regulationData.date.split('T')[0] : ''
      }
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取法规详情失败：', error)
    alert('获取法规详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这条记录吗？')) {
    try {
      await request.get(`info/deleteRegular?id=${id}`)
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
      id: formData.value.id, // 直接使用表单中的ID，新增时为空字符串，编辑时为实际ID
      title: formData.value.title,
      content: formData.value.content,
      date: formData.value.date || new Date().toISOString().split('T')[0]
    }
    
    console.log('提交的数据：', submitData)

    if (isEditing.value) {
      const response = await request.post('info/updateRegular', submitData)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      // 新增时删除id字段
      delete submitData.id
      const response = await request.post('info/addRegular', submitData)
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

.add-btn {
  padding: 6px 16px;
  background-color: white;
  color: #4CAF50;
  border: 2px solid #4CAF50;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
  margin-left: 12px;
}

.add-btn:hover {
  background-color: #4CAF50;
  color: white;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(76, 175, 80, 0.35);
}
</style> 
