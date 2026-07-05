<template>
  <div class="not-found">
    <div class="error-box">
      <h1>404</h1>
      <p class="desc">抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

const goHome = () => {
  router.push('/home')
}
</script>

<style scoped>
.not-found {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f0f2f5;
}
.error-box {
  text-align: center;
}
.error-box h1 {
  font-size: 120px;
  color: #165dff;
  line-height: 1;
  margin-bottom: 16px;
}
.desc {
  color: #86909c;
  margin-bottom: 24px;
  font-size: 16px;
}
</style>