<script setup>

</script>

<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 重置默认样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 设置根元素和body的高度为100% */
html, body {
  height: 100%;
  background-color: #f8f7f2; /* 米白色背景 */
}

#app {
  min-height: 100vh;
  background-color: #f8f7f2; /* 米白色背景 */
  color: #2c3e50;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen,
    Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

/* 统一链接样式 */
a {
  text-decoration: none;
  color: inherit;
}

/* 统一按钮样式 */
button {
  border: none;
  background: none;
  cursor: pointer;
  font: inherit;
}

button:focus {
  outline: none;
}

/* 统一输入框样式 */
input, textarea {
  font: inherit;
}

/* 设置页面过渡效果 */
.page-enter-active,
.page-leave-active {
  transition: opacity 0.3s ease;
}

.page-enter-from,
.page-leave-to {
  opacity: 0;
}
</style>
