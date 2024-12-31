<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>商家管理</h2>
      <button class="add-btn" @click="showAddDialog">添加商家</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchUsername" placeholder="搜索用户名...">
      <input type="text" v-model="searchName" placeholder="搜索商家名称...">
      <select v-model="searchType" class="search-select">
        <option value="">所有类型</option>
        <option value="果蔬">果蔬</option>
        <option value="肉类">肉类</option>
        <option value="饮料">饮料</option>
        <option value="其他">其他</option>
      </select>
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>商家ID</th>
            <th>用户名</th>
            <th>商家名称</th>
            <th>商家类型</th>
            <th>商家图标</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in merchantList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.username }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.type }}</td>
            <td>
              <img v-if="item.logo" :src="'http://localhost:8080/image/' + item.logo" 
                   alt="商家图标" class="merchant-logo">
              <span v-else>无图标</span>
            </td>
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
        <h3>{{ isEditing ? '编辑商家' : '添加商家' }}</h3>
        <form @submit.prevent="handleSubmit" enctype="multipart/form-data">
          <div class="form-group">
            <label>用户名：</label>
            <input type="text" v-model="formData.username" required>
          </div>
          <div class="form-group">
            <label>商家名称：</label>
            <input type="text" v-model="formData.name" required>
          </div>
          <div class="form-group">
            <label>商家类型：</label>
            <select v-model="formData.type" required>
              <option value="果蔬">果蔬</option>
              <option value="肉类">肉类</option>
              <option value="饮料">饮料</option>
              <option value="其他">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>商家描述：</label>
            <textarea v-model="formData.description" required rows="4"></textarea>
          </div>
          <div class="form-group" v-if="isEditing">
            <label>商家图标：</label>
            <input type="file" @change="handleImageUpload" accept="image/*">
            <div v-if="imagePreview" class="image-preview">
              <img :src="imagePreview" alt="图标预览">
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
const merchantList = ref([])
const searchUsername = ref('')
const searchName = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  username: '',
  name: '',
  type: '',
  description: '',
  logo: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('merchant/findAllMerchants')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        merchantList.value = []
        console.error('获取数据失败：', response.data)
        return
      }
      
      if (Array.isArray(response.data)) {
        merchantList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        merchantList.value = response.data.data
      } else if (typeof response.data === 'object') {
        merchantList.value = [response.data]
      } else {
        merchantList.value = []
      }
    } else {
      console.error('API返回数据格式不正确：', response)
      merchantList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    merchantList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    const params = {}
    if (searchUsername.value) params.username = searchUsername.value
    if (searchName.value) params.name = searchName.value
    if (searchType.value) params.type = searchType.value
    
    if (Object.keys(params).length > 0) {
      const response = await request.get('merchant/findMerchant', { params })
      console.log('搜索返回的原始数据：', response)
      
      if (response.data) {
        if (response.data.code === 404) {
          merchantList.value = []
          return
        }
        
        if (Array.isArray(response.data)) {
          merchantList.value = response.data
        } else if (response.data.data && Array.isArray(response.data.data)) {
          merchantList.value = response.data.data
        } else if (typeof response.data === 'object' && !response.data.code) {
          merchantList.value = [response.data]
        } else {
          merchantList.value = []
        }
      } else {
        merchantList.value = []
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
    merchantList.value = []
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    username: '',
    name: '',
    type: '',
    description: '',
    logo: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`merchant/findMerchantById?id=${item.id}`)
    console.log('获取到的商家详情：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        alert('获取商家详情失败')
        return
      }
      
      isEditing.value = true
      const merchantData = response.data.data || response.data
      formData.value = { ...merchantData }
      imagePreview.value = merchantData.logo ? `http://localhost:8080/image/${merchantData.logo}` : ''
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取商家详情失败：', error)
    alert('获取商家详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这个商家吗？')) {
    try {
      await request.get(`merchant/deleteMerchantById?id=${id}`)
      alert('删除成功')
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
    formDataToSend.append('username', formData.value.username)
    formDataToSend.append('name', formData.value.name)
    formDataToSend.append('type', formData.value.type)
    formDataToSend.append('description', formData.value.description)
    
    if (isEditing.value) {
      formDataToSend.append('id', formData.value.id)
      // 如果是编辑模式且没有新上传的图片，则传入原图片路径
      if (!formData.value.imageFile && formData.value.logo) {
        formDataToSend.append('logo', formData.value.logo)
      }
    }
    
    // 添加新上传的图片文件
    if (formData.value.imageFile) {
      formDataToSend.append('file', formData.value.imageFile)
    }
    
    console.log('提交的数据：', Object.fromEntries(formDataToSend))

    if (isEditing.value) {
      const response = await request.post('merchant/updateMerchant', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('merchant/addMerchant', formDataToSend)
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
    username: '',
    name: '',
    type: '',
    description: '',
    logo: ''
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

.merchant-logo {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  object-fit: cover;
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