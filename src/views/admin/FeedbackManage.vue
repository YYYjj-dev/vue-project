<template>
  <div class="container">
    <div class="search-box">
      <div class="search-input">
        <input
          v-model="searchId"
          type="text"
          placeholder="请输入反馈ID"
          class="input"
        />
        <button class="button primary" @click="handleSearch">
          搜索
        </button>
        <button class="button" @click="fetchData">重置</button>
      </div>
    </div>

    <div class="table-container">
      <table class="table">
        <thead>
          <tr>
            <th style="width: 80px">ID</th>
            <th>内容</th>
            <th style="width: 120px">类型</th>
            <th style="width: 180px">邮箱</th>
            <th style="width: 150px">电话</th>
            <th style="width: 100px">操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in tableData" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.content }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.email }}</td>
            <td>{{ item.phone }}</td>
            <td>
              <button
                class="button danger"
                @click="handleDelete(item.id)"
              >
                删除
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="pagination">
      <span class="total">共 {{ total }} 条</span>
      <div class="page-sizes">
        <select v-model="pageSize" @change="handleSizeChange">
          <option v-for="size in [10, 20, 30, 50]" :key="size" :value="size">
            {{ size }}条/页
          </option>
        </select>
      </div>
      <div class="page-buttons">
        <button
          class="button"
          :disabled="currentPage === 1"
          @click="handleCurrentChange(currentPage - 1)"
        >
          上一页
        </button>
        <span class="page-number">{{ currentPage }}</span>
        <button
          class="button"
          :disabled="currentPage * pageSize >= total"
          @click="handleCurrentChange(currentPage + 1)"
        >
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const tableData = ref([])
const loading = ref(false)
const searchId = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 获取所有反馈数据
const fetchData = async () => {
  loading.value = true
  try {
    const response = await axios.get('/info/findAllFeedback')
    if (response.data) {
      tableData.value = response.data
      total.value = response.data.length
    }
  } catch (error) {
    alert('获取反馈数据失败')
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}

// 根据ID搜索反馈
const handleSearch = async () => {
  if (!searchId.value) {
    fetchData()
    return
  }
  
  loading.value = true
  try {
    const response = await axios.get(`/info/findFeedbackById?id=${searchId.value}`)
    if (response.data) {
      tableData.value = [response.data]
      total.value = 1
    }
  } catch (error) {
    alert('搜索反馈失败')
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}

// 删除反馈
const handleDelete = async (id) => {
  if (!confirm('确定要删除这条反馈吗？')) {
    return
  }

  try {
    await axios.delete(`/info/deleteFeedback?id=${id}`)
    alert('删除成功')
    fetchData()
  } catch (error) {
    alert('删除失败')
    console.error('Error:', error)
  }
}

// 分页相关方法
const handleSizeChange = (event) => {
  pageSize.value = Number(event.target.value)
  fetchData()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchData()
}

// 组件挂载时获取数据
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.container {
  padding: 20px;
  background-color: #fff;
}

.search-box {
  margin-bottom: 20px;
}

.search-input {
  display: flex;
  gap: 10px;
  align-items: center;
}

.input {
  padding: 8px 12px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  width: 200px;
}

.button {
  padding: 8px 15px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  background-color: #fff;
  cursor: pointer;
  transition: all 0.3s;
}

.button:hover {
  background-color: #f5f7fa;
}

.button.primary {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
}

.button.primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.button.danger {
  color: #f56c6c;
}

.button.danger:hover {
  color: #fff;
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.button:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

.table-container {
  margin-bottom: 20px;
  overflow-x: auto;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 12px;
  border: 1px solid #ebeef5;
  text-align: left;
}

.table th {
  background-color: #f5f7fa;
  font-weight: 500;
}

.table tr:hover td {
  background-color: #f5f7fa;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 20px;
}

.total {
  color: #606266;
}

.page-sizes select {
  padding: 5px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.page-buttons {
  display: flex;
  align-items: center;
  gap: 10px;
}

.page-number {
  padding: 0 10px;
}

.loading {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
</style> 