import { defineStore } from 'pinia';

export const defineUser = defineStore('user', {
  state: () => ({
    username: localStorage.getItem('username') || '', // 从本地存储读取用户名
    token: localStorage.getItem('token') || '',       // 从本地存储读取token
    type: localStorage.getItem('type') || '',       // 从本地存储读取type
  }),
  actions: {
    // 设置用户信息
    setUserInfo(username, token, type) {
      this.username = username
      this.token = token
      // 保存到本地存储
      localStorage.setItem('username', username)
      localStorage.setItem('token', token)
      localStorage.setItem('type', type)
    },
    // 清除用户信息
    clearUserInfo() {
      this.username = ''
      this.token = ''
      this.type = ''
      // 清除本地存储
      localStorage.removeItem('username')
      localStorage.removeItem('token')
      localStorage.removeItem('type')
    }
  }
})