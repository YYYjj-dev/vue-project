<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>关于我们</h3>
        <p>我们致力于为用户提供最优质的服务</p>
      </div>
      
      <div class="footer-section">
        <h3>联系方式</h3>
        <p>邮箱: contact@example.com</p>
        <p>电话: (123) 456-7890</p>
      </div>
      
      <div class="footer-section">
        <h3>关注我们</h3>
        <div class="social-links">
          <a href="#" class="social-link">微信</a>
          <a href="#" class="social-link">微博</a>
          <a href="#" class="social-link">GitHub</a>
        </div>
      </div>
    </div>
    
    <div class="footer-bottom">
      <p>&copy; 2024 您的公司名称. 保留所有权利.</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.footer {
  background-color: #2c3e50;
  color: #ffffff;
  padding: 2rem 0 0 0;
  width: 100vw;
  margin: 0;
  margin-left: calc(-50vw + 50%);
  margin-right: calc(-50vw + 50%);
  box-sizing: border-box;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  padding: 0 2rem;
}

.footer-section {
  flex: 1;
  min-width: 250px;
  margin: 1rem;
}

.footer-section h3 {
  color: #42b983;
  margin-bottom: 1rem;
}

.social-links {
  display: flex;
  gap: 1rem;
}

.social-link {
  color: #ffffff;
  text-decoration: none;
  transition: color 0.3s;
}

.social-link:hover {
  color: #42b983;
}

.footer-bottom {
  text-align: center;
  margin-top: 2rem;
  padding: 1rem 0;
  border-top: 1px solid #3a4f63;
  background-color: #243342;
}

@media (max-width: 768px) {
  .footer-section {
    flex: 100%;
    text-align: center;
  }
  
  .social-links {
    justify-content: center;
  }
}
</style> 