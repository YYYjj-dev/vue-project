<template>
    <div class="merchant-apply">
        <NavBar />
        
        <div class="content-container">
            <div class="apply-form">
                <h2 class="form-title">商家入驻申请</h2>
                
                <div class="form-group">
                    <label>店铺名称</label>
                    <input type="text" v-model="formData.shopName" placeholder="请输入店铺名称">
                </div>

                <div class="form-group">
                    <label>经营类别</label>
                    <select v-model="formData.category">
                        <option value="">请选择经营类别</option>
                        <option value="1">食品添加剂</option>
                        <option value="2">食品原料</option>
                        <option value="3">其他</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>营业执照</label>
                    <div class="upload-area">
                        <input type="file" @change="handleFileUpload" accept="image/*">
                        <div class="upload-tip">点击上传营业执照照片</div>
                    </div>
                </div>

                <div class="form-group">
                    <label>联系人姓名</label>
                    <input type="text" v-model="formData.contactName" placeholder="请输入联系人姓名">
                </div>

                <div class="form-group">
                    <label>联系电话</label>
                    <input type="tel" v-model="formData.phone" placeholder="请输入联系电话">
                </div>

                <div class="form-group">
                    <label>经营地址</label>
                    <input type="text" v-model="formData.address" placeholder="请输入经营地址">
                </div>

                <div class="form-group">
                    <label>经营描述</label>
                    <textarea v-model="formData.description" placeholder="请简要描述您的经营范围和特色"></textarea>
                </div>

                <button class="submit-btn" @click="submitApplication">提交申请</button>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'

export default {
    name: 'merchant_apply',
    components: {
        NavBar
    },
    data() {
        return {
            formData: {
                shopName: '',
                category: '',
                license: null,
                contactName: '',
                phone: '',
                address: '',
                description: ''
            }
        }
    },
    methods: {
        handleFileUpload(event) {
            this.formData.license = event.target.files[0]
        },
        submitApplication() {
            // 这里处理表单提交逻辑
            console.log('提交申请:', this.formData)
            // TODO: 发送到后端API
        }
    }
}
</script>

<style scoped>
.merchant-apply {
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 20px;
}

.content-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 40px 20px;
}

.apply-form {
    background: white;
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.form-title {
    text-align: center;
    color: #2c3e50;
    margin-bottom: 30px;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    color: #2c3e50;
    font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1rem;
}

.form-group textarea {
    height: 120px;
    resize: vertical;
}

.upload-area {
    border: 2px dashed #ddd;
    padding: 20px;
    text-align: center;
    border-radius: 8px;
    cursor: pointer;
}

.upload-tip {
    color: #666;
    margin-top: 10px;
}

.submit-btn {
    width: 100%;
    padding: 12px;
    background: #42b983;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1.1rem;
    cursor: pointer;
    transition: all 0.3s ease;
}

.submit-btn:hover {
    background: #3aa876;
}

@media (max-width: 768px) {
    .content-container {
        padding: 20px 10px;
    }
    
    .apply-form {
        padding: 20px;
    }
}
</style>