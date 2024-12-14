<template>
    <div class="cases-more">
        <NavBar />
        
        <!-- 标题 -->
        <div class="header">
            <button class="all-cases-btn">全部案例</button>
        </div>

        <!-- 案例列表 -->
        <div class="cases-list">
            <div class="case-item" 
                 v-for="(item, index) in cases" 
                 :key="index"
                 @click="goToCase(item.id)">
                <div class="case-content">内容</div>
            </div>
        </div>

        <!-- 分页器 -->
        <div class="pagination">
            <button class="page-btn" @click="prevPage">&lt;</button>
            <span>{{ currentPage }} / {{ totalPages }}</span>
            <button class="page-btn" @click="nextPage">&gt;</button>
        </div>
    </div>
</template>

<script>
import NavBar from '../../../components/NavBar.vue'
import { useRouter } from 'vue-router'

export default {
    name: 'cases_more',
    components: {
        NavBar
    },
    setup() {
        const router = useRouter()
        return { router }
    },
    data() {
        return {
            currentPage: 1,
            totalPages: 5,
            cases: Array(4).fill().map((_, i) => ({
                id: i + 1,
                title: `案例${i + 1}`,
                content: '内容'
            }))
        }
    },
    methods: {
        goToCase(id) {
            this.router.push({
                name: 'cases_info',
                params: { id },
                query: { title: '案例标题' }
            })
        },
        prevPage() {
            if (this.currentPage > 1) {
                this.currentPage--
            }
        },
        nextPage() {
            if (this.currentPage < this.totalPages) {
                this.currentPage++
            }
        }
    }
}
</script>

<style scoped>
.cases-more {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
}

.header {
    margin: 20px 0;
}

.all-cases-btn {
    padding: 8px 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: white;
    cursor: pointer;
    font-size: 16px;
}

.cases-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin: 30px 0;
}

.case-item {
    background: white;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    cursor: pointer;
    transition: all 0.3s ease;
    height: 150px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.case-item:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
    margin-top: 30px;
}

.page-btn {
    border: none;
    background: none;
    cursor: pointer;
    font-size: 18px;
    padding: 5px 10px;
}

.page-btn:hover {
    color: #4CAF50;
}
</style>