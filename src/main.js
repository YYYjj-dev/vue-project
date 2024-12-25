import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './style/style.css'
import pinia from './store'


const app = createApp(App)

app.use(router)
app.use(pinia)

app.mount('#app')
