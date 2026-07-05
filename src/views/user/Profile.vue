<template>
  <div class="profile-page page-container">
    <h3>个人信息</h3>
    <el-row :gutter="40">
      <el-col :span="8">
        <div class="avatar-box">
          <el-avatar :size="120" />
          <el-button type="primary" style="margin-top: 16px;">更换头像</el-button>
        </div>
      </el-col>
      <el-col :span="16">
        <el-form label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="userForm.username" disabled />
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="userForm.nickName" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="userForm.phone" />
          </el-form-item>
          <el-form-item label="所属班级">
            <el-input v-model="userForm.className" disabled />
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="userForm.major" disabled />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveProfile">保存修改</el-button>
            <el-button @click="passwordDialog = true">修改密码</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-dialog v-model="passwordDialog" title="修改密码" width="400px">
      <el-form label-width="100px">
        <el-form-item label="原密码">
          <el-input v-model="pwdForm.oldPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="pwdForm.newPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="pwdForm.confirmPassword" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordDialog = false">取消</el-button>
        <el-button type="primary" @click="updatePassword">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {ElMessage} from 'element-plus'

const passwordDialog = ref(false)

const userForm = reactive({
  username: 'student001',
  nickName: '测试学生',
  phone: '138****8888',
  className: '石工22-1班',
  major: '石油工程'
})

const pwdForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const saveProfile = () => {
  ElMessage.success('保存成功')
}

const updatePassword = () => {
  if (pwdForm.newPassword !== pwdForm.confirmPassword) {
    ElMessage.error('两次密码输入不一致')
    return
  }
  ElMessage.success('密码修改成功')
  passwordDialog.value = false
}
</script>

<style scoped>
h3 {
  margin-bottom: 24px;
}
.avatar-box {
  text-align: center;
  padding-top: 20px;
}
</style>