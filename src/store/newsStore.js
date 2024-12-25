import {defineStore} from 'pinia'

export const defineNews = defineStore('newsList', {
  state: () => {
    return {
	  itemList:[]
    }
  },

  getters: {

  },

  actions: {
   
  },
  persist: {
    key: 'piniaStore', //存储名称
    storage: sessionStorage, // 存储方式
    paths: [], //指定 state 中哪些数据需要被持久化。[] 表示不持久化任何状态，undefined 或 null 表示持久化整个 state
},

})