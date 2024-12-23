<template>
    <div>
      <input type="file" @change="handleFileUpload" accept="image/*" />
      <button @click="uploadImage">上传图片</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        selectedFile: null
      };
    },
    methods: {
      handleFileUpload(event) {
        this.selectedFile = event.target.files[0];
      },
      uploadImage() {
        const formData = new FormData();
        formData.append('image', this.selectedFile);
  
        axios.post('/api/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(response => {
          console.log('图片上传成功', response.data);
          // 在这里可以处理上传成功后的逻辑，例如显示上传成功的提示
        })
        .catch(error => {
          console.error('图片上传失败', error);
          // 在这里可以处理上传失败后的逻辑，例如显示上传失败的提示
        });
      }
    }
  };
  </script>
  
  <style scoped>
  /* 可以在这里添加样式 */
  </style>