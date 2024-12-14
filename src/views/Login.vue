<template>
    <div class="login-container">
        <div class="login-box">
            <div class="form-container">
                <div class="input-item">
                    <span class="label">账号</span>
                    <input type="text" v-model="username" placeholder="请输入账号">
                </div>
                <div class="input-item">
                    <span class="label">密码</span>
                    <input type="password" v-model="password" placeholder="请输入密码">
                </div>
                <div class="button-container">
                    <button class="login-btn" @click="handleLogin">登录</button>
                    <button class="register-btn" @click="goToRegister">去注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
    name: 'Login',
    setup() {
        const router = useRouter()
        const username = ref('')
        const password = ref('')

        const handleLogin = () => {
            // 这里添加登录逻辑
            console.log('登录:', username.value, password.value)
        }

        const goToRegister = () => {
            router.push('/register')
        }

        return {
            username,
            password,
            handleLogin,
            goToRegister
        }
    }
}
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f5f5f5;
    padding: 20px;
}

.login-box {
    background: white;
    padding: 40px 30px;
    border-radius: 12px;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    margin: auto;
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

.login-btn, .register-btn {
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
