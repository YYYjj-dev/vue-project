<template>
    <div class="register-container">
        <div class="register-box">
            <div class="form-container">
                <div class="input-item">
                    <input type="text" v-model="username" placeholder="账号">
                </div>
                <div class="input-item">
                    <input type="password" v-model="password" placeholder="密码">
                </div>
                <div class="input-item">
                    <input type="password" v-model="confirmPassword" placeholder="确认密码">
                </div>
                <div class="button-container">
                    <button class="register-btn" @click="handleRegister">注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
    name: 'Register',
    setup() {
        const router = useRouter()
        const username = ref('')
        const password = ref('')
        const confirmPassword = ref('')

        const handleRegister = () => {
            if (password.value !== confirmPassword.value) {
                alert('两次输入的密码不一致')
                return
            }
            // 这里添加注册逻辑
            console.log('注册:', username.value, password.value)
        }

        return {
            username,
            password,
            confirmPassword,
            handleRegister
        }
    }
}
</script>

<style scoped>
.register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #f5f5f5;
    padding: 20px;
}

.register-box {
    background: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
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
</style>
