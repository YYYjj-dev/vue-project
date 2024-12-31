<template>
    <div class="register-container">
        <div class="register-box">
            <div class="form-container">
                <!-- 用户类型选择 -->
                <div class="type-selection">
                    <h3>选择注册类型</h3>
                    <div class="type-options">
                        <label class="type-option">
                            <input type="radio" v-model="registUser.type" value="normal" name="userType">
                            <span class="option-text">注册为普通用户</span>
                        </label>
                        <label class="type-option">
                            <input type="radio" v-model="registUser.type" value="merchant" name="userType">
                            <span class="option-text">注册为商家</span>
                        </label>
                    </div>
                </div>

                <div class="input-item">
                    <input type="text" v-model="registUser.username" placeholder="账号" @blur="checkUsername">
                    <span class="msg" v-text="usernameMsg"></span>
                </div>
                <div class="input-item">
                    <input type="password" v-model="registUser.password" placeholder="密码">
                </div>
                <div class="input-item">
                    <input type="password" v-model="confirmPassword" placeholder="确认密码">
                </div>
                <div class="button-container">
                    <button class="register-btn" @click="Regist" :disabled="!registUser.type">注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../utils/request'

let confirmPassword = ref('')
let usernameMsg = ref('')
const baseUrl = 'http://localhost:8080/image/'
const router = useRouter()
let registUser = reactive({
    username: '',
    password: '',
    type: ''  // 添加 type 字段
})

async function checkUsername() {
    if (!registUser.username.trim()) {
        usernameMsg.value = '账号不能为空'
        return false
    }

    let { data } = await request.post(`/user/checkUsernameUsed?username=${registUser.username}`)
    if (data.code != 200) {
        usernameMsg.value = '账号已存在'
        return false
    }
    usernameMsg.value = '可用'
    return true
}

async function Regist() {
    // 检查是否选择了用户类型
    if (!registUser.type) {
        alert('请选择注册类型')
        return
    }

    if (registUser.password != confirmPassword.value) {
        alert('两次输入的密码不一致')
        return
    }

    if (await checkUsername()) {
        try {
            let { data } = await request.post('/user/regist', registUser)
            if (data.code == 200) {
                alert('注册成功,前去登录')
                router.push('/login')
            } else {
                alert(data.message || '注册失败')
            }
        } catch (error) {
            console.error('注册失败:', error)
            alert('注册失败，请重试')
        }
    }
}
</script>

<style scoped>
.msg {
    color: red;
}

.register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-image: url('/src/img/background.jpg');
    /* 添加背景图 */
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    padding: 20px;
    position: relative;
}

/* 添加遮罩层 */
.register-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    /* 半透明白色遮罩 */
    backdrop-filter: blur(5px);
    /* 背景模糊效果 */
}

.register-box {
    background: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    position: relative;
    /* 确保在遮罩层之上 */
    z-index: 1;
}

.form-container {
    width: 100%;
}

.input-item {
    margin-bottom: 20px;
    width: 100%;
}

input {
    width: 100%;
    padding: 12px 15px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 16px;
    outline: none;
    box-sizing: border-box;
}

input:focus {
    border-color: #4CAF50;
}

.button-container {
    margin-top: 30px;
}

.register-btn {
    width: 100%;
    padding: 12px 0;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.register-btn:hover {
    background: #45a049;
}

/* 响应式调整 */
@media (max-width: 480px) {
    .register-box {
        padding: 30px 20px;
    }
}

/* 添加用户类型选择样式 */
.type-selection {
    margin-bottom: 30px;
    text-align: center;
}

.type-selection h3 {
    margin-bottom: 15px;
    color: #333;
    font-size: 18px;
}

.type-options {
    display: flex;
    gap: 20px;
    justify-content: center;
    margin-top: 10px;
}

.type-option {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    padding: 10px 20px;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.type-option:hover {
    border-color: #4CAF50;
}

.type-option input[type="radio"] {
    width: auto;
    margin: 0;
}

.type-option input[type="radio"]:checked+.option-text {
    color: #4CAF50;
    font-weight: 500;
}

.type-option:has(input[type="radio"]:checked) {
    border-color: #4CAF50;
    background-color: #f1f8f1;
}

.option-text {
    font-size: 14px;
    color: #666;
}

/* 禁用状态的注册按钮 */
.register-btn:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}

.register-btn:disabled:hover {
    background-color: #cccccc;
}
</style>
