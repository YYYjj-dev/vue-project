<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>应用案例管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新案例</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索案例标题...">
      <select v-model="searchType" class="search-select">
        <option value="">所有群体</option>
        <option value="老年">老年</option>
        <option value="婴幼儿">婴幼儿</option>
        <option value="儿童">儿童</option>
        <option value="其他">其他</option>
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
            <th>群体种类</th>
            <th>发布日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in casesList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.title }}</td>
            <td>{{ item.grouptype }}</td>
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
        <h3>{{ isEditing ? '编辑案例' : '添加新案例' }}</h3>
        <form @submit.prevent="handleSubmit" enctype="multipart/form-data">
          <div class="form-group">
            <label>标题：</label>
            <input type="text" v-model="formData.title" required>
          </div>
          <div class="form-group">
            <label>群体种类：</label>
            <select v-model="formData.grouptype" required>
              <option value="老年">老年</option>
              <option value="婴幼儿">婴幼儿</option>
              <option value="儿童">儿童</option>
              <option value="其他">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>内容：</label>
            <textarea v-model="formData.content" required rows="6"></textarea>
          </div>
          <div class="form-group">
            <label>案例图片：</label>
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
const casesList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  title: '',
  content: '',
  grouptype: '',
  date: '',
  imgpath: ''
})

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('info/findAllCases')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (Array.isArray(response.data)) {
        casesList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        casesList.value = response.data.data
      } else if (typeof response.data === 'object') {
        casesList.value = [response.data]
      } else {
        casesList.value = []
      }
    } else {
      console.error('API返回数据格式不正确：', response)
      casesList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    casesList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    const params = {}
    if (searchQuery.value) params.title = searchQuery.value
    if (searchType.value) params.grouptype = searchType.value
    
    if (Object.keys(params).length > 0) {
      const response = await request.get('info/findCases', { params })
      console.log('搜索返回的原始数据：', response)
      
      if (response.data) {
        if (Array.isArray(response.data)) {
          casesList.value = response.data
        } else if (response.data.data && Array.isArray(response.data.data)) {
          casesList.value = response.data.data
        } else if (typeof response.data === 'object') {
          casesList.value = [response.data]
        } else {
          casesList.value = []
        }
      } else {
        casesList.value = []
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
    casesList.value = []
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    title: '',
    content: '',
    grouptype: '',
    date: new Date().toISOString().split('T')[0],
    imgpath: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`info/findCasesById?id=${item.id}`)
    console.log('获取到的案例详情：', response)
    
    if (response.data) {
      isEditing.value = true
      const caseData = response.data.data || response.data
      formData.value = { ...caseData }
      imagePreview.value = caseData.imgpath ? `http://localhost:8080/image/${caseData.imgpath}` : ''
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取案例详情失败：', error)
    alert('获取案例详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这条案例吗？')) {
    try {
      await request.get(`info/deleteCases?id=${id}`)
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
    formDataToSend.append('title', formData.value.title)
    formDataToSend.append('content', formData.value.content)
    formDataToSend.append('grouptype', formData.value.grouptype)
    formDataToSend.append('date', formData.value.date)
    
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
      const response = await request.post('info/updateCases', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('info/addCases', formDataToSend)
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
    grouptype: '',
    date: '',
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