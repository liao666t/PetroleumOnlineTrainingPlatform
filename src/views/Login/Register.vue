<template>
  <div class="login-page">
    <div class="login-box">
      <h2 class="title">石油智能教学实训平台</h2>
      <p class="subtitle">学生账号注册</p>

      <el-form ref="registerFormRef" :model="registerForm" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入学号/账号" size="large">
            <template #prefix><el-icon><User /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="nickName">
          <el-input v-model="registerForm.nickName" placeholder="请输入昵称" size="large">
            <template #prefix><el-icon><UserFilled /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="className">
          <el-select v-model="registerForm.className" placeholder="请选择班级" size="large" style="width:100%">
            <el-option label="石工22-1班" value="石工22-1班" />
            <el-option label="石工22-2班" value="石工22-2班" />
            <el-option label="石工22-3班" value="石工22-3班" />
          </el-select>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号" size="large">
            <template #prefix><el-icon><Phone /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" size="large" show-password>
            <template #prefix><el-icon><Lock /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" size="large" show-password>
            <template #prefix><el-icon><Lock /></el-icon></template>
          </el-input>
        </el-form-item>

        <el-button type="primary" size="large" style="width:100%" :loading="loading" @click="handleRegister">
          注 册
        </el-button>
      </el-form>

      <div class="footer-text">
        已有账号？<span class="link" @click="$router.push('/login')">立即登录</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
// 对接后端时导入接口
// import { registerApi } from '../../api/user'

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  username: '',
  nickName: '',
  className: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

const validateConfirm = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次密码输入不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  className: [{ required: true, message: '请选择班级', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirm, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  await registerFormRef.value.validate()
  loading.value = true
  try {
    // 模拟注册
    await new Promise(resolve => setTimeout(resolve, 800))
    ElMessage.success('注册成功，请登录')
    router.push('/login')

    // 真实接口
    // await registerApi(registerForm)
    // ElMessage.success('注册成功，请登录')
    // router.push('/login')
  } finally {
    loading.value = false
  }
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
  padding: 36px 40px;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0,0,0,0.15);
}
.title {
  text-align: center;
  color: #165dff;
  margin-bottom: 4px;
  font-size: 24px;
}
.subtitle {
  text-align: center;
  color: #86909c;
  margin-bottom: 20px;
  font-size: 14px;
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