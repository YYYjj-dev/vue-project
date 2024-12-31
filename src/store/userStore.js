import { defineStore } from 'pinia';

export const defineUser = defineStore('user', {
  state: () => ({
    username: localStorage.getItem('username') || '',
    token: localStorage.getItem('token') || '',
    type: localStorage.getItem('type') || '',
    img: localStorage.getItem('img') || ''
  }),
  actions: {
    // 设置用户信息
    setUserInfo(username, token, type, img) {
      this.username = username
      this.token = token
      this.type = type
      this.img = img
      // 保存到本地存储
      localStorage.setItem('username', username)
      localStorage.setItem('token', token)
      localStorage.setItem('type', type)
      localStorage.setItem('img', img)
    },
    // 清除用户信息
    clearUserInfo() {
      this.username = ''
      this.token = ''
      this.type = ''
      this.img = ''
      // 清除本地存储
      localStorage.removeItem('username')
      localStorage.removeItem('token')
      localStorage.removeItem('type')
      localStorage.removeItem('img')
    }
  }
})