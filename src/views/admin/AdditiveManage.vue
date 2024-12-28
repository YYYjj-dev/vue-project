<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>添加剂管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新添加剂</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索添加剂名称...">
      <select v-model="searchType" class="search-select">
        <option value="">所有类型</option>
        <option value="防腐剂">防腐剂</option>
        <option value="抗氧化剂">抗氧化剂</option>
        <option value="甜味剂">甜味剂</option>
        <option value="着色剂">着色剂</option>
        <option value="增稠剂">增稠剂</option>
        <option value="膨松剂">膨松剂</option>
        <option value="香料与香精">香料与香精</option>
      </select>
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>名称</th>
            <th>种类</th>
            <!-- <th>描述</th> -->
            <th>来源于自然</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in additivesList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.typename }}</td>
            <!-- <td>{{ item.description }}</td> -->
            <td>{{ item.nature}}</td>
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
        <h3>{{ isEditing ? '编辑添加剂' : '添加新添加剂' }}</h3>
        <form @submit.prevent="handleSubmit" enctype="multipart/form-data">
          <div class="form-group">
            <label>名称：</label>
            <input type="text" v-model="formData.name" required>
          </div>
          <div class="form-group">
            <label>种类：</label>
            <select v-model="formData.typename" required>
              <option value="防腐剂">防腐剂</option>
              <option value="抗氧化剂">抗氧化剂</option>
              <option value="甜味剂">甜味剂</option>
              <option value="着色剂">着色剂</option>
              <option value="增稠剂">增稠剂</option>
              <option value="膨松剂">膨松剂</option>
              <option value="香料与香精">香料与香精</option>
            </select>
          </div>
          <div class="form-group">
            <label>描述：</label>
            <textarea v-model="formData.description" required rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>国内使用标准：</label>
            <textarea v-model="formData.usestandardInternal" required rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>国际使用标准：</label>
            <textarea v-model="formData.usestandardInternational" required rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>分析：</label>
            <textarea v-model="formData.analysis" required rows="3"></textarea>
          </div>
          <div class="form-group">
            <label>来源于自然：</label>
            <select v-model="formData.nature" required>
              <option value="天然">天然</option>
              <option value="人工">人工</option>
            </select>
          </div>
          <div class="form-group">
            <label>添加剂图片：</label>
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
const additivesList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  name: '',
  typename: '',
  description: '',
  usestandardInternal: '',
  usestandardInternational: '',
  analysis: '',
  nature: true,
  imgpath: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('additive/findAllAdditives')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (Array.isArray(response.data)) {
        additivesList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        additivesList.value = response.data.data
      } else if (typeof response.data === 'object') {
        additivesList.value = [response.data]
      } else {
        additivesList.value = []
      }
    } else {
      console.error('API返回数据格式不正确：', response)
      additivesList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    additivesList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    if (searchType.value) {
      const response = await request.get(`additive/findAdditiveByType?type=${searchType.value}`)
      if (response.data) {
        additivesList.value = Array.isArray(response.data) ? response.data : [response.data]
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
    name: '',
    typename: '',
    description: '',
    usestandardInternal: '',
    usestandardInternational: '',
    analysis: '',
    nature: true,
    imgpath: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`additive/findAdditiveById?id=${item.id}`)
    console.log('获取到的添加剂详情：', response)
    
    if (response.data) {
      isEditing.value = true
      const additiveData = response.data.data || response.data
      formData.value = { ...additiveData }
      imagePreview.value = additiveData.imgpath ? `http://localhost:8080/image/${additiveData.imgpath}` : ''
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取添加剂详情失败：', error)
    alert('获取添加剂详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这条添加剂吗？')) {
    try {
      await request.get(`additive/deleteAdditive?id=${id}`)
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
    formDataToSend.append('name', formData.value.name)
    formDataToSend.append('typename', formData.value.typename)
    formDataToSend.append('description', formData.value.description)
    formDataToSend.append('usestandardInternal', formData.value.usestandardInternal)
    formDataToSend.append('usestandardInternational', formData.value.usestandardInternational)
    formDataToSend.append('analysis', formData.value.analysis)
    formDataToSend.append('nature', formData.value.nature)
    
    if (isEditing.value) {
      formDataToSend.append('id', formData.value.id)
      // 如果是编辑模式且没有新上传的图片，则传入原图片路径
      if (!formData.value.imageFile && formData.value.imgpath) {
        formDataToSend.append('imgpath', formData.value.imgpath)
      }
    }
    
    // 添加新上传的图片文件
    if (formData.value.imageFile) {
      formDataToSend.append('file', formData.value.imageFile)
    }
    
    console.log('提交的数据：', Object.fromEntries(formDataToSend))

    if (isEditing.value) {
      const response = await request.post('additive/updateAdditive', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('additive/addAdditive', formDataToSend)
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
    name: '',
    typename: '',
    description: '',
    usestandardInternal: '',
    usestandardInternational: '',
    analysis: '',
    nature: true,
    imgpath: ''
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