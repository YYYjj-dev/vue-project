<template>
    <div class="login-container">
        <div class="login-box">
            <div class="form-container">
                <div class="input-item">
                    <span class="label">账号</span>
                    <input type="text" v-model="loginUser.username" placeholder="请输入账号">
                </div>
                <div class="input-item">
                    <span class="label">密码</span>
                    <input type="password" v-model="loginUser.password" placeholder="请输入密码">
                </div>
                <div class="button-container">
                    <button class="login-btn" @click="Login">登录</button>
                    <button class="register-btn" @click="goToRegister">去注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../utils/request'
import { defineUser } from '../store/userStore'


const baseUrl = 'http://localhost:8080/image/'
const router = useRouter()
let loginUser = reactive({
    username: '',
    password: ''
})
let userStore = defineUser()

async function Login() {
    try {
        const response = await request.post('/user/login', {
            username: loginUser.username,
            password: loginUser.password
        })

        if (response.data.code === 200) {
            // 使用store的action来设置用户信息，包括type
            userStore.setUserInfo(
                response.data.data.username,
                response.data.data.token,
                response.data.data.type,  // 存储用户类型
                response.data.data.img
            )
            router.push('/')
        } else {
            console.error('登录失败:', response.data.message)
        }
    } catch (error) {
        console.error('登录错误:', error)
    }
}

const goToRegister = () => {
    router.push('/register')
}
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-image: url('/src/img/background.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    padding: 20px;
    position: relative;
}

.login-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(5px);
}

.login-box {
    background: white;
    padding: 40px 30px;
    border-radius: 12px;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    margin: auto;
    position: relative;
    z-index: 1;
}

.form-container {
    width: 100%;
    padding: 0;
}

.input-item {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.label {
    width: 60px;
    font-size: 16px;
    color: #333;
}

input {
    flex: 1;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
    outline: none;
}

input:focus {
    border-color: #4CAF50;
}

.button-container {
    display: flex;
    gap: 20px;
    padding-left: 60px;
}

.login-btn,
.register-btn {
    flex: 1;
    padding: 10px 0;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s;
}

.login-btn {
    background: #4CAF50;
    color: white;
}

.login-btn:hover {
    background: #45a049;
}

.register-btn {
    background: white;
    border: 1px solid #4CAF50;
    color: #4CAF50;
}

.register-btn:hover {
    background: #f5f5f5;
}
</style>
