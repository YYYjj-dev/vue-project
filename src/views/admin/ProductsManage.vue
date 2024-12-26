<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>商品管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新商品</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索商品名称/编号...">
      <select v-model="searchCategory" class="search-select">
        <option value="">所有分类</option>
        <option value="食品添加剂">食品添加剂</option>
        <option value="化妆品原料">化妆品原料</option>
        <option value="医药辅料">医药辅料</option>
        <option value="工业原料">工业原料</option>
      </select>
      <select v-model="searchStatus" class="search-select">
        <option value="">所有状态</option>
        <option value="on">在售</option>
        <option value="off">下架</option>
      </select>
      <button class="search-btn" @click="handleSearch">搜索</button>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>商品编号</th>
            <th>商品名称</th>
            <th>分类</th>
            <th>价格(元)</th>
            <th>库存</th>
            <th>销量</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in productsList" :key="item.id">
            <td>{{ item.productCode }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.category }}</td>
            <td>{{ item.price }}</td>
            <td>
              <span :class="['stock-tag', getStockLevel(item.stock)]">
                {{ item.stock }}
              </span>
            </td>
            <td>{{ item.sales }}</td>
            <td>
              <span :class="['status-tag', item.status === 'on' ? 'on-sale' : 'off-sale']">
                {{ item.status === 'on' ? '在售' : '已下架' }}
              </span>
            </td>
            <td>
              <button class="edit-btn" @click="handleEdit(item)">编辑</button>
              <button 
                :class="['toggle-btn', item.status === 'on' ? 'off-btn' : 'on-btn']"
                @click="handleToggleStatus(item)">
                {{ item.status === 'on' ? '下架' : '上架' }}
              </button>
              <button class="stock-btn" @click="showStockDialog(item)">库存</button>
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
            <label>商品编号：</label>
            <input type="text" v-model="formData.productCode" required>
          </div>
          <div class="form-group">
            <label>分类：</label>
            <select v-model="formData.category" required>
              <option value="食品添加剂">食品添加剂</option>
              <option value="化妆品原料">化妆品原料</option>
              <option value="医药辅料">医药辅料</option>
              <option value="工业原料">工业原料</option>
            </select>
          </div>
          <div class="form-group">
            <label>价格(元)：</label>
            <input type="number" v-model="formData.price" min="0" step="0.01" required>
          </div>
          <div class="form-group">
            <label>初始库存：</label>
            <input type="number" v-model="formData.stock" min="0" required>
          </div>
          <div class="form-group">
            <label>规格：</label>
            <input type="text" v-model="formData.specification" required>
          </div>
          <div class="form-group">
            <label>商品图片：</label>
            <div class="image-upload">
              <input type="file" @change="handleImageUpload" accept="image/*" multiple>
              <div class="image-preview-list" v-if="formData.images && formData.images.length">
                <div v-for="(image, index) in formData.images" :key="index" class="image-preview-item">
                  <img :src="image" :alt="'商品图片' + (index + 1)">
                  <button type="button" class="remove-image" @click="removeImage(index)">×</button>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label>商品描述：</label>
            <textarea v-model="formData.description" rows="4" required></textarea>
          </div>
          <div class="form-group">
            <label>注意事项：</label>
            <textarea v-model="formData.notes" rows="3"></textarea>
          </div>
          <div class="dialog-buttons">
            <button type="submit" class="submit-btn">提交</button>
            <button type="button" class="cancel-btn" @click="closeDialog">取消</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 库存管理对话框 -->
    <div v-if="showStockDialog" class="dialog-overlay">
      <div class="dialog stock-dialog">
        <h3>库存管理</h3>
        <div class="stock-info">
          <p>当前库存：{{ selectedProduct.stock }}</p>
          <p>商品名称：{{ selectedProduct.name }}</p>
          <p>商品编号：{{ selectedProduct.productCode }}</p>
        </div>
        <form @submit.prevent="handleStockSubmit">
          <div class="form-group">
            <label>操作类型：</label>
            <select v-model="stockForm.type" required>
              <option value="in">入库</option>
              <option value="out">出库</option>
            </select>
          </div>
          <div class="form-group">
            <label>数量：</label>
            <input type="number" v-model="stockForm.amount" min="1" required>
          </div>
          <div class="form-group">
            <label>备注：</label>
            <textarea v-model="stockForm.remarks" rows="2"></textarea>
          </div>
          <div class="dialog-buttons">
            <button type="submit" class="submit-btn">确认</button>
            <button type="button" class="cancel-btn" @click="closeStockDialog">取消</button>
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
const currentPage = ref(1)
const totalPages = ref(1)
const searchQuery = ref('')
const searchCategory = ref('')
const searchStatus = ref('')
const showDialog = ref(false)
const showStockDialog = ref(false)
const isEditing = ref(false)
const selectedProduct = ref({})
const formData = ref({
  name: '',
  productCode: '',
  category: '',
  price: '',
  stock: 0,
  specification: '',
  description: '',
  notes: '',
  images: []
})
const stockForm = ref({
  type: 'in',
  amount: 1,
  remarks: ''
})

// 获取库存等级
const getStockLevel = (stock) => {
  if (stock <= 0) return 'no-stock'
  if (stock < 10) return 'low-stock'
  if (stock < 50) return 'medium-stock'
  return 'high-stock'
}

// 获取数据
const fetchData = async () => {
  try {
    const { data } = await request.get('product/findAll', {
      params: {
        page: currentPage.value,
        query: searchQuery.value,
        category: searchCategory.value,
        status: searchStatus.value
      }
    })
    productsList.value = data.data
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
    name: '',
    productCode: '',
    category: '',
    price: '',
    stock: 0,
    specification: '',
    description: '',
    notes: '',
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

// 切换商��状态
const handleToggleStatus = async (item) => {
  const action = item.status === 'on' ? 'off' : 'on'
  const confirmMessage = `确定要${action === 'on' ? '上架' : '下架'}该商品吗？`
  
  if (confirm(confirmMessage)) {
    try {
      await request.put(`product/${action}/${item.id}`)
      fetchData()
    } catch (error) {
      console.error(`${action}失败：`, error)
    }
  }
}

// 显示库存管理对话框
const showStockDialog2 = (item) => {
  selectedProduct.value = item
  stockForm.value = {
    type: 'in',
    amount: 1,
    remarks: ''
  }
  showStockDialog.value = true
}

// 处理库存变更
const handleStockSubmit = async () => {
  try {
    await request.post('product/stock/change', {
      productId: selectedProduct.value.id,
      type: stockForm.value.type,
      amount: stockForm.value.amount,
      remarks: stockForm.value.remarks
    })
    closeStockDialog()
    fetchData()
  } catch (error) {
    console.error('库存操作失败：', error)
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除该商品吗？此操作不可恢复！')) {
    try {
      await request.delete(`product/delete/${id}`)
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
    }
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
      await request.put(`product/update/${formData.value.id}`, formDataToSend)
    } else {
      await request.post('product/add', formDataToSend)
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
    productCode: '',
    category: '',
    price: '',
    stock: 0,
    specification: '',
    description: '',
    notes: '',
    images: []
  }
}

// 关闭库存对话框
const closeStockDialog = () => {
  showStockDialog.value = false
  stockForm.value = {
    type: 'in',
    amount: 1,
    remarks: ''
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

/* 状态标签样式 */
.status-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status-tag.on-sale {
  background-color: #f0f9eb;
  color: #67c23a;
}

.status-tag.off-sale {
  background-color: #f4f4f5;
  color: #909399;
}

/* 按钮样式 */
.edit-btn, .toggle-btn, .stock-btn, .delete-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 12px;
}

.edit-btn {
  background-color: #ffc107;
  color: #000;
}

.on-btn {
  background-color: #67c23a;
  color: white;
}

.off-btn {
  background-color: #909399;
  color: white;
}

.stock-btn {
  background-color: #409eff;
  color: white;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
}

/* 图片上传样式 */
.image-upload {
  margin-top: 10px;
}

.image-preview-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
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
  height: 100px;
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
}

/* 库存对话框样式 */
.stock-dialog {
  width: 400px;
}

.stock-info {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.stock-info p {
  margin: 5px 0;
  font-size: 14px;
}

/* 表单样式 */
.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
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
  width: 120px;
}

/* 对话框样式 */
.dialog {
  max-height: 90vh;
  overflow-y: auto;
}

/* 分页样式 */
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
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
</style> 