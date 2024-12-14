<template>
    <div class="register-container">
        <div class="register-box">
            <div class="form-container">
                <div class="input-item">
                    <input type="text" v-model="registUser.username" placeholder="账号">
                </div>
                <div class="input-item">
                    <input type="password" v-model="registUser.password" placeholder="密码">
                </div>
                <div class="input-item">
                    <input type="password" v-model="confirmPassword" placeholder="确认密码">
                </div>
                <div class="button-container">
                    <button class="register-btn" @click="Regist">注册</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref,reactive } from 'vue'
import { useRouter } from 'vue-router'
import request from '../utils/request'

        const router = useRouter()
        let registUser = reactive({
        username:'',
        password:''
     })
        let confirmPassword = ref('')

        async function Regist (){
            if (registUser.password!= confirmPassword.value) {
                console.log( registUser.password+'  '+confirmPassword.value)
                alert('两次输入的密码不一致')
            }else{
                await request.post('/user/regist',registUser)
                alert('注册成功')
                console.log('注册:', registUser.username+registUser.password)
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
