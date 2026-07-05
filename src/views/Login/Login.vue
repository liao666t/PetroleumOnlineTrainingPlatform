<template>
  <div class="login-page">
    <div class="login-box">
      <h2 class="title">石油智能教学实训平台</h2>

      <el-tabs v-model="loginType" class="login-tabs" stretch>
        <el-tab-pane label="学生端登录" name="student" />
        <el-tab-pane label="教师端登录" name="teacher" />
      </el-tabs>

      <el-form ref="loginFormRef" :model="loginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号" size="large">
            <template #prefix><el-icon><User /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" size="large" show-password>
            <template #prefix><el-icon><Lock /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-button type="primary" size="large" style="width:100%" :loading="loading" @click="handleLogin">
          登 录
        </el-button>
      </el-form>

      <div class="footer-text" v-if="loginType === 'student'">
        没有账号？<span class="link" @click="$router.push('/register')">立即注册</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../store/user'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)
const loginType = ref('student')

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = () => {
  loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      await userStore.login({
        ...loginForm,
        type: loginType.value
      })
      ElMessage.success('登录成功')

      // 根据身份跳转
      if (loginType.value === 'teacher') {
        router.push('/teacher/course')
      } else {
        router.push('/home')
      }
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-page {
  height: 100vh;
  background: linear-gradient(135deg, #165dff 0%, #0e2d6b 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-box {
  width: 420px;
  background: #fff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0,0,0,0.15);
}
.title {
  text-align: center;
  color: #165dff;
  margin-bottom: 8px;
  font-size: 24px;
}
.login-tabs {
  margin-bottom: 20px;
}
.footer-text {
  text-align: center;
  margin-top: 20px;
  color: #86909c;
  font-size: 14px;
}
.link {
  color: #165dff;
  cursor: pointer;
}
</style>