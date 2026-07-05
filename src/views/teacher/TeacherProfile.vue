<template>
  <div class="page-container">
    <h3>个人信息</h3>
    <el-row :gutter="40">
      <el-col :span="8">
        <div class="avatar-box">
          <el-avatar :size="120" />
          <el-button type="primary" style="margin-top:16px;">更换头像</el-button>
        </div>
      </el-col>
      <el-col :span="16">
        <el-form label-width="100px">
          <el-form-item label="工号">
            <el-input v-model="form.teacherNo" disabled />
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="所属院系">
            <el-input v-model="form.department" disabled />
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="form.title" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="form.phone" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save">保存修改</el-button>
            <el-button @click="pwdDialog = true">修改密码</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-dialog v-model="pwdDialog" title="修改密码" width="400px">
      <el-form label-width="100px">
        <el-form-item label="原密码">
          <el-input v-model="pwdForm.oldPwd" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="pwdForm.newPwd" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input v-model="pwdForm.confirmPwd" type="password" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="pwdDialog = false">取消</el-button>
        <el-button type="primary" @click="updatePwd">确认修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const pwdDialog = ref(false)

const form = reactive({
  teacherNo: 'T2020001',
  name: '张教授',
  department: '石油工程学院',
  title: '教授',
  phone: '139****6666'
})

const pwdForm = reactive({
  oldPwd: '',
  newPwd: '',
  confirmPwd: ''
})

const save = () => {
  ElMessage.success('保存成功')
}

const updatePwd = () => {
  if (pwdForm.newPwd !== pwdForm.confirmPwd) {
    ElMessage.error('两次密码输入不一致')
    return
  }
  ElMessage.success('密码修改成功')
  pwdDialog.value = false
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