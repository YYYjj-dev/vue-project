<template>
  <div class="manage-container">
    <div class="page-header">
      <h2>用户管理</h2>
      <button class="add-btn" @click="showAddDialog">添加新用户</button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="搜索用户名/邮箱...">
      <select v-model="searchRole" class="search-select">
        <option value="">所有角色</option>
        <option value="admin">管理员</option>
        <option value="user">普通用户</option>
        <option value="business">企业用户</option>
      </select>
      <select v-model="searchStatus" class="search-select">
        <option value="">所有状态</option>
        <option value="active">正常</option>
        <option value="locked">已锁定</option>
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
            <th>邮箱</th>
            <th>角色</th>
            <th>注册时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in usersList" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>
              <span :class="['role-tag', user.role]">
                {{ getRoleName(user.role) }}
              </span>
            </td>
            <td>{{ user.registerTime }}</td>
            <td>
              <span :class="['status-tag', user.status === 'active' ? 'active' : 'locked']">
                {{ user.status === 'active' ? '正常' : '已锁定' }}
              </span>
            </td>
            <td>
              <button class="edit-btn" @click="handleEdit(user)">编辑</button>
              <button 
                :class="['toggle-btn', user.status === 'active' ? 'lock-btn' : 'unlock-btn']"
                @click="handleToggleStatus(user)">
                {{ user.status === 'active' ? '锁定' : '解锁' }}
              </button>
              <button class="delete-btn" @click="handleDelete(user.id)">删除</button>
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
        <h3>{{ isEditing ? '编辑用户' : '添加新用户' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>用户名：</label>
            <input type="text" v-model="formData.username" required>
          </div>
          <div class="form-group">
            <label>邮箱：</label>
            <input type="email" v-model="formData.email" required>
          </div>
          <div class="form-group" v-if="!isEditing">
            <label>密码：</label>
            <input type="password" v-model="formData.password" required>
          </div>
          <div class="form-group">
            <label>角色：</label>
            <select v-model="formData.role" required>
              <option value="admin">管理员</option>
              <option value="user">普通用户</option>
              <option value="business">企业��户</option>
            </select>
          </div>
          <div class="form-group">
            <label>手机号：</label>
            <input type="tel" v-model="formData.phone">
          </div>
          <div class="form-group">
            <label>公司名称：</label>
            <input type="text" v-model="formData.company" 
                   :required="formData.role === 'business'">
          </div>
          <div class="form-group">
            <label>备注：</label>
            <textarea v-model="formData.remarks" rows="3"></textarea>
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
const usersList = ref([])
const currentPage = ref(1)
const totalPages = ref(1)
const searchQuery = ref('')
const searchRole = ref('')
const searchStatus = ref('')
const showDialog = ref(false)
const isEditing = ref(false)
const formData = ref({
  username: '',
  email: '',
  password: '',
  role: 'user',
  phone: '',
  company: '',
  remarks: ''
})

// 获取角色名称
const getRoleName = (role) => {
  const roleMap = {
    admin: '管理员',
    user: '普通用户',
    business: '企业用户'
  }
  return roleMap[role] || role
}

// 获取数据
const fetchData = async () => {
  try {
    const { data } = await request.get('user/findAll', {
      params: {
        page: currentPage.value,
        query: searchQuery.value,
        role: searchRole.value,
        status: searchStatus.value
      }
    })
    usersList.value = data.data
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
    username: '',
    email: '',
    password: '',
    role: 'user',
    phone: '',
    company: '',
    remarks: ''
  }
  showDialog.value = true
}

// 编辑
const handleEdit = (user) => {
  isEditing.value = true
  formData.value = { ...user }
  delete formData.value.password // 编辑时不显示密码字段
  showDialog.value = true
}

// 切换用户状态
const handleToggleStatus = async (user) => {
  const action = user.status === 'active' ? 'lock' : 'unlock'
  const confirmMessage = `确定要${action === 'lock' ? '锁定' : '解锁'}该用户吗？`
  
  if (confirm(confirmMessage)) {
    try {
      await request.put(`user/${action}/${user.id}`)
      fetchData()
    } catch (error) {
      console.error(`${action}失败：`, error)
    }
  }
}

// 删除
const handleDelete = async (id) => {
  if (confirm('确定要删除该用户吗？此操作不可恢复！')) {
    try {
      await request.delete(`user/delete/${id}`)
      fetchData()
    } catch (error) {
      console.error('删除失败：', error)
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    if (isEditing.value) {
      await request.put(`user/update/${formData.value.id}`, formData.value)
    } else {
      await request.post('user/add', formData.value)
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
    username: '',
    email: '',
    password: '',
    role: 'user',
    phone: '',
    company: '',
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

/* 角色标签样式 */
.role-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.role-tag.admin {
  background-color: #fef0f0;
  color: #f56c6c;
}

.role-tag.user {
  background-color: #f0f9eb;
  color: #67c23a;
}

.role-tag.business {
  background-color: #f4f4f5;
  color: #909399;
}

/* 状态标签样式 */
.status-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status-tag.active {
  background-color: #f0f9eb;
  color: #67c23a;
}

.status-tag.locked {
  background-color: #fef0f0;
  color: #f56c6c;
}

/* 按钮样式 */
.edit-btn, .toggle-btn, .delete-btn {
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

.lock-btn {
  background-color: #f56c6c;
  color: white;
}

.unlock-btn {
  background-color: #67c23a;
  color: white;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
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
  width: 500px;
  max-width: 90%;
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