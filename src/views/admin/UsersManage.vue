<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>用户管理</h2>
      <button class="add-btn" @click="showAddDialog">添加用户</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索用户名...">
      <select v-model="searchType" class="search-select">
        <option value="">所有类型</option>
        <option value="admin">管理员</option>
        <option value="merchant">商家</option>
        <option value="normal">普通用户</option>
      </select>
      <select v-model="searchGender" class="search-select">
        <option value="">所有性别</option>
        <option value="男">男</option>
        <option value="女">女</option>
        <option value="保密">保密</option>
      </select>
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>用户类型</th>
            <th>性别</th>
            <th>头像</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in userList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.username }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.gender }}</td>
            <td>
              <img v-if="item.img" :src="'http://localhost:8080/image/' + item.img" 
                   alt="用户头像" class="user-avatar">
              <span v-else>无头像</span>
            </td>
            <td class="operation-group">
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button class="delete-btn" @click="handleDelete(item.username)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑对话框 -->
    <div v-if="showDialog" class="dialog-overlay">
      <div class="dialog">
        <h3>{{ isEditing ? '编辑用户' : '添加用户' }}</h3>
        <form @submit.prevent="handleSubmit" enctype="multipart/form-data">
          <div class="form-group">
            <label>用户名：</label>
            <input type="text" v-model="formData.username" required>
          </div>
          <div class="form-group" v-if="!isEditing">
            <label>密码：</label>
            <input type="password" v-model="formData.password" required>
          </div>
          <div class="form-group">
            <label>用户类型：</label>
            <select v-model="formData.type" required>
              <option value="admin">管理员</option>
              <option value="merchant">商家</option>
              <option value="normal">普通用户</option>
            </select>
          </div>
          <div class="form-group">
            <label>性别：</label>
            <select v-model="formData.gender" required>
              <option value="男">男</option>
              <option value="女">女</option>
              <option value="保密">保密</option>
            </select>
          </div>
          <div class="form-group">
            <label>用户头像：</label>
            <input type="file" @change="handleImageUpload" accept="image/*">
            <div v-if="imagePreview" class="image-preview">
              <img :src="imagePreview" alt="头像预览">
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
const userList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const searchGender = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  username: '',
  password: '',
  type: '',
  gender: '',
  img: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('user/findAllUser')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        userList.value = []
        console.error('获取数据失败：', response.data)
        return
      }
      
      if (Array.isArray(response.data)) {
        userList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        userList.value = response.data.data
      } else if (typeof response.data === 'object') {
        userList.value = [response.data]
      } else {
        userList.value = []
      }
    } else {
      console.error('API返回数据格式不正确：', response)
      userList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    userList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    const params = {}
    if (searchQuery.value) params.username = searchQuery.value
    if (searchType.value) params.type = searchType.value
    if (searchGender.value) params.gender = searchGender.value
    
    if (Object.keys(params).length > 0) {
      const response = await request.get('user/findUser', { params })
      console.log('搜索返回的原始数据：', response)
      
      if (response.data) {
        if (response.data.code === 404) {
          userList.value = []
          return
        }
        
        if (Array.isArray(response.data)) {
          userList.value = response.data
        } else if (response.data.data && Array.isArray(response.data.data)) {
          userList.value = response.data.data
        } else if (typeof response.data === 'object' && !response.data.code) {
          userList.value = [response.data]
        } else {
          userList.value = []
        }
      } else {
        userList.value = []
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
    userList.value = []
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    username: '',
    password: '',
    type: 'normal',
    gender: '保密',
    img: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    console.log('开始编辑用户：', item)
    isEditing.value = true
    formData.value = { ...item } // 先使用传入的数据
    imagePreview.value = item.img ? `http://localhost:8080/image/${item.img}` : ''
    showDialog.value = true // 立即显示对话框
    
    // 然后获取详细信息
    const response = await request.get(`user/findUserById?id=${item.id}`)
    console.log('获取到的用户详情：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        alert('获取用户详情失败')
        return
      }
      
      const userData = response.data.data || response.data
      formData.value = { 
        ...userData,
        password: '' // 清空密码字段
      }
      imagePreview.value = userData.img ? `http://localhost:8080/image/${userData.img}` : ''
    }
  } catch (error) {
    console.error('获取用户详情失败：', error)
    alert('获取用户详情失败')
  }
}

// 删除
const handleDelete = async (username) => {
  if (confirm('确定要删除这个用户吗？')) {
    try {
      await request.get(`user/deleteUser?username=${username}`)
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
    formDataToSend.append('type', formData.value.type)
    formDataToSend.append('gender', formData.value.gender)
    
    if (!isEditing.value) {
      formDataToSend.append('password', formData.value.password)
    }
    
    if (isEditing.value) {
      formDataToSend.append('id', formData.value.id)
      // 如果是编辑模式且没有新上传的图���，则传入原图片路径
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
      const response = await request.post('user/updateUser', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('user/addUser', formDataToSend)
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
    password: '',
    type: '',
    gender: '',
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

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
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