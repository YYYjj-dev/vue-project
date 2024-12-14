<template>
    <div class="feedback">
        <NavBar />
        
        <div class="feedback-container">
            <div class="feedback-card">
                <!-- 标题区域 -->
                <h1 class="main-title">用户反馈</h1>
                
                <!-- 个人信息区域 -->
                <div class="section">
                    <h2 class="section-title">个人信息</h2>
                    <div class="form-group">
                        <div class="input-group">
                            <label>手机号</label>
                            <input 
                                type="tel" 
                                v-model="phone"
                                placeholder="请输入您的手机号"
                                class="form-input"
                            >
                        </div>
                        <div class="input-group">
                            <label>邮箱</label>
                            <input 
                                type="email" 
                                v-model="email"
                                placeholder="请输入您的邮箱"
                                class="form-input"
                            >
                        </div>
                    </div>
                </div>

                <!-- 反馈类型区域 -->
                <div class="section">
                    <h2 class="section-title">反馈类型</h2>
                    <div class="feedback-type-group">
                        <div 
                            v-for="type in feedbackTypes" 
                            :key="type.value"
                            class="type-option"
                            :class="{ active: selectedType === type.value }"
                            @click="selectedType = type.value"
                        >
                            {{ type.label }}
                        </div>
                    </div>
                    <!-- 其他类型的详细说明输入框 -->
                    <div v-if="selectedType === 'other'" class="other-type-input">
                        <input 
                            v-model="otherTypeDetail"
                            type="text"
                            placeholder="请简要描述您的问题类型"
                            class="form-input"
                        >
                    </div>
                </div>

                <!-- 反馈内容区域 -->
                <div class="section">
                    <h2 class="section-title">反馈内容</h2>
                    <div class="feedback-content">
                        <textarea 
                            v-model="content" 
                            placeholder="请详细描述您的问题或建议..."
                            class="content-textarea"
                            rows="6"
                        ></textarea>
                        <div class="char-count">{{ content.length }}/500</div>
                    </div>
                </div>

                <!-- 提交按钮 -->
                <div class="submit-section">
                    <button 
                        class="submit-button"
                        @click="handleSubmit"
                        :disabled="!isFormValid"
                    >
                        提交反馈
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

export default {
    name: 'feedback',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        const phone = ref('')
        const email = ref('')
        const selectedType = ref('')
        const content = ref('')
        const otherTypeDetail = ref('')

        const feedbackTypes = [
            { label: '商品质量问题', value: 'quality' },
            { label: '商品与预期不符', value: 'expectation' },
            { label: '问题食品曝光', value: 'food' },
            { label: '其他', value: 'other' }
        ]

        const isFormValid = computed(() => {
            const baseValidation = phone.value && 
                   email.value && 
                   selectedType.value && 
                   content.value.length > 0 &&
                   content.value.length <= 500

            // 如果选择了其他类型，还需要验证其他类型的详细说明
            if (selectedType.value === 'other') {
                return baseValidation && otherTypeDetail.value.length > 0
            }
            return baseValidation
        })

        const handleSubmit = () => {
            if (!isFormValid.value) return
            
            // 构建反馈数据
            const feedbackData = {
                phone: phone.value,
                email: email.value,
                type: selectedType.value,
                otherTypeDetail: selectedType.value === 'other' ? otherTypeDetail.value : '',
                content: content.value
            }
            
            console.log('提交的反馈数据：', feedbackData)
            alert('感谢您的反馈！')
            router.push('/home')
        }

        return {
            phone,
            email,
            selectedType,
            content,
            otherTypeDetail,
            feedbackTypes,
            isFormValid,
            handleSubmit,
            router
        }
    }
}
</script>

<style scoped>
.feedback {
    width: 100%;
    min-height: 100vh;
    background-color: #f8f7f2;
    padding: 0 20px;
    box-sizing: border-box;
}

.feedback-container {
    max-width: 800px;
    margin: 0 auto;
    padding-top: 20px;
}

.feedback-card {
    background: white;
    border-radius: 16px;
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.08);
    padding: 40px;
    animation: slideUp 0.5s ease-out;
}

.main-title {
    font-size: 32px;
    color: #2c3e50;
    text-align: center;
    margin-bottom: 40px;
    font-weight: 600;
}

.section {
    margin-bottom: 36px;
}

.section-title {
    font-size: 20px;
    color: #2c3e50;
    margin-bottom: 20px;
    font-weight: 500;
    position: relative;
    padding-left: 16px;
}

.section-title::before {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    width: 4px;
    height: 20px;
    background: #3498db;
    border-radius: 2px;
}

.form-group {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
}

.input-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.input-group label {
    font-size: 14px;
    color: #666;
    margin-bottom: 4px;
}

.form-input {
    width: 100%;
    padding: 12px 16px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 14px;
    transition: all 0.3s;
}

.form-input:focus {
    border-color: #3498db;
    box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.1);
    outline: none;
}

.feedback-type-group {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
    margin-bottom: 16px;
}

.type-option {
    padding: 10px 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s;
    font-size: 14px;
    background: #f8f9fa;
}

.type-option:hover {
    background: #e9ecef;
}

.type-option.active {
    background: #3498db;
    color: white;
    border-color: #3498db;
}

.other-type-input {
    margin-top: 16px;
    animation: slideDown 0.3s ease-out;
}

.feedback-content {
    position: relative;
}

.content-textarea {
    width: 100%;
    padding: 16px;
    border: 1px solid #ddd;
    border-radius: 8px;
    resize: vertical;
    min-height: 150px;
    font-size: 14px;
    line-height: 1.6;
    transition: all 0.3s;
}

.content-textarea:focus {
    border-color: #3498db;
    box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.1);
    outline: none;
}

.char-count {
    position: absolute;
    right: 12px;
    bottom: 12px;
    font-size: 12px;
    color: #999;
}

.submit-section {
    text-align: center;
    margin-top: 40px;
}

.submit-button {
    background: #3498db;
    color: white;
    border: none;
    padding: 14px 48px;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s;
    font-weight: 500;
}

.submit-button:hover {
    background: #2980b9;
    transform: translateY(-1px);
}

.submit-button:disabled {
    background: #ccc;
    cursor: not-allowed;
    transform: none;
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes slideDown {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* 响应式适配 */
@media (max-width: 600px) {
    .feedback-card {
        padding: 20px;
    }

    .main-title {
        font-size: 24px;
    }

    .section-title {
        font-size: 18px;
    }

    .submit-button {
        width: 100%;
    }
}
</style>