<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>商品管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新商品</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索商品名称...">
      <select v-model="searchType" class="search-select">
        <option value="">所有种类</option>
        <option value="饮料">饮料</option>
        <option value="果蔬">果蔬</option>
        <option value="肉类">肉类</option>
        <option value="营养品">营养品</option>
        <option value="其他">其他</option>
      </select>
      <select v-model="searchGroup" class="search-select">
        <option value="">所有群体</option>
        <option value="老年">老年</option>
        <option value="婴幼儿">婴幼儿</option>
        <option value="儿童">儿童</option>
        <option value="其他">其他</option>
      </select>
      <input type="text" v-model="searchMerchant" placeholder="商家名称...">
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th>商品种类</th>
            <th>对应群体</th>
            <th>价格(元)</th>
            <th>销量</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in productsList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.group }}</td>
            <td>{{ item.price }}</td>
            <td>
              <span :class="['stock-tag', getStockLevel(item.num)]">
                {{ item.num }}
              </span>
            </td>
            <td class="operation-group">
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button class="delete-btn" @click="handleDelete(item.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑商品对话框 -->
    <div v-if="showDialog" class="dialog-overlay">
      <div class="dialog">
        <h3>{{ isEditing ? '编辑商品' : '添加新商品' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>商品名称：</label>
            <input type="text" v-model="formData.name" required>
          </div>
          <div class="form-group">
            <label>商品种类：</label>
            <select v-model="formData.type" required>
              <option value="饮料">饮料</option>
              <option value="果蔬">果蔬</option>
              <option value="肉类">肉类</option>
              <option value="营养品">营养品</option>
              <option value="其他">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>对应群体：</label>
            <select v-model="formData.group" required>
              <option value="老年">老年</option>
              <option value="婴幼儿">婴幼儿</option>
              <option value="儿童">儿童</option>
              <option value="其他">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>商家ID：</label>
            <input type="text" v-model="formData.storeId" required>
          </div>
          <div class="form-group">
            <label>价格(元)：</label>
            <input type="number" v-model="formData.price" min="0" step="0.01" required>
          </div>
          <div class="form-group" v-if="isEditing">
            <label>销量：</label>
            <input type="number" v-model="formData.num" min="0" readonly>
          </div>
          <div class="form-group">
            <label>规格：</label>
            <input type="text" v-model="formData.standard" required>
          </div>
          <div class="form-group">
            <label>商品描述：</label>
            <textarea v-model="formData.description" rows="4" required></textarea>
          </div>
          <div class="form-group">
            <label>商品图片：</label>
            <input type="file" @change="handleImageUpload" accept="image/*">
            <div v-if="imagePreview" class="image-preview">
              <img :src="imagePreview" alt="商品图片预览">
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
const productsList = ref([])
const searchQuery = ref('')
const searchType = ref('')
const searchGroup = ref('')
const searchMerchant = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const imagePreview = ref('')
const formData = ref({
  id: '',
  storeId: '',
  name: '',
  type: '',
  group: '',
  description: '',
  standard: '',
  price: '',
  num: 0,
  imgpath: ''
})

// 获取销量等级
const getStockLevel = (num) => {
  if (num <= 0) return 'no-stock'
  if (num < 10) return 'low-stock'
  if (num < 50) return 'medium-stock'
  return 'high-stock'
}

// 获取数据
const fetchData = async () => {
  try {
    const response = await request.get('shangpin/findAllShangpin')
    console.log('API返回的原始数据：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        productsList.value = []
        console.error('获取数据失败：', response.data)
        return
      }
      
      if (Array.isArray(response.data)) {
        productsList.value = response.data
      } else if (response.data.data && Array.isArray(response.data.data)) {
        productsList.value = response.data.data
      } else if (typeof response.data === 'object') {
        productsList.value = [response.data]
      } else {
        productsList.value = []
      }
    } else {
      console.error('API返回数据格式不正确：', response)
      productsList.value = []
    }
  } catch (error) {
    console.error('获取数据失败：', error)
    productsList.value = []
  }
}

// 搜索
const handleSearch = async () => {
  try {
    const params = {}
    if (searchQuery.value) params.name = searchQuery.value
    if (searchType.value) params.type = searchType.value
    if (searchGroup.value) params.group = searchGroup.value
    if (searchMerchant.value) params.merchantName = searchMerchant.value
    
    if (Object.keys(params).length > 0) {
      const response = await request.get('shangpin/findShangpin', { params })
      console.log('搜索返回的原始数据：', response)
      
      if (response.data) {
        if (response.data.code === 404) {
          productsList.value = []
          return
        }
        
        if (Array.isArray(response.data)) {
          productsList.value = response.data
        } else if (response.data.data && Array.isArray(response.data.data)) {
          productsList.value = response.data.data
        } else if (typeof response.data === 'object' && !response.data.code) {
          productsList.value = [response.data]
        } else {
          productsList.value = []
        }
      } else {
        productsList.value = []
      }
    } else {
      await fetchData()
    }
  } catch (error) {
    console.error('搜索失败：', error)
    alert('搜索失败')
    productsList.value = []
  }
}

// 显示添加对话框
const showAddDialog = () => {
  isEditing.value = false
  formData.value = {
    id: '',
    storeId: '',
    name: '',
    type: '',
    group: '',
    description: '',
    standard: '',
    price: '',
    num: 0,
    imgpath: ''
  }
  imagePreview.value = ''
  showDialog.value = true
}

// 编辑
const handleEdit = async (item) => {
  try {
    const response = await request.get(`shangpin/findShangpinById?id=${item.id}`)
    console.log('获取到的商品详情：', response)
    
    if (response.data) {
      if (response.data.code === 404) {
        alert('获取商品详情失败')
        return
      }
      
      isEditing.value = true
      const productData = response.data.data || response.data
      formData.value = { ...productData }
      imagePreview.value = productData.imgpath ? `http://localhost:8080/image/${productData.imgpath}` : ''
      showDialog.value = true
    }
  } catch (error) {
    console.error('获取商品详情失败：', error)
    alert('获取商品详情失败')
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除这个商品吗？')) {
    try {
      await request.get(`shangpin/deleteShangpinById?id=${id}`)
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
    formDataToSend.append('type', formData.value.type)
    formDataToSend.append('group', formData.value.group)
    formDataToSend.append('storeId', formData.value.storeId)
    formDataToSend.append('description', formData.value.description)
    formDataToSend.append('standard', formData.value.standard)
    formDataToSend.append('price', formData.value.price)
    formDataToSend.append('num', formData.value.num)
    
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
      const response = await request.post('shangpin/updateShangpin', formDataToSend)
      console.log('更新响应：', response)
      alert('修改成功')
    } else {
      const response = await request.post('shangpin/addShangpin', formDataToSend)
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
    storeId: '',
    name: '',
    type: '',
    group: '',
    description: '',
    standard: '',
    price: '',
    num: 0,
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

/* 库存标签样式 */
.stock-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.stock-tag.no-stock {
  background-color: #fef0f0;
  color: #f56c6c;
}

.stock-tag.low-stock {
  background-color: #fdf6ec;
  color: #e6a23c;
}

.stock-tag.medium-stock {
  background-color: #f0f9eb;
  color: #67c23a;
}

.stock-tag.high-stock {
  background-color: #ecf5ff;
  color: #409eff;
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