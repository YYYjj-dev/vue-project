import { defineStore } from 'pinia';

export const defineUser = defineStore('user', {
  state: () => ({
    username: localStorage.getItem('username') || '', // 从本地存储读取用户名
    token: localStorage.getItem('token') || '',       // 从本地存储读取token
  }),
  actions: {
    // 设置用户信息
    setUserInfo(username, token) {
      this.username = username
      this.token = token
      // 保存到本地存储
      localStorage.setItem('username', username)
      localStorage.setItem('token', token)
    },
    // 清除用户信息
    clearUserInfo() {
      this.username = ''
      this.token = ''
      // 清除本地存储
      localStorage.removeItem('username')
      localStorage.removeItem('token')
    }
  }
})