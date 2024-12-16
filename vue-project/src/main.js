import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './style/style.css'
import pinia from './pinia'


const app = createApp(App)

app.use(router)
app.use(pinia)

app.mount('#app')
