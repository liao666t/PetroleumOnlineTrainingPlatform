<template>
  <div class="page-container">
    <div class="page-header">
      <h3>课程管理</h3>
      <el-button type="primary" @click="openDialog()">新增课程</el-button>
    </div>

    <el-table :data="courseList" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="课程名称" min-width="200" />
      <el-table-column prop="teacher" label="授课教师" width="120" />
      <el-table-column prop="studentCount" label="选课人数" width="120" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '开课中' : '已下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button type="primary" link @click="openDialog(row)">编辑</el-button>
          <el-button type="success" link v-if="row.status === 0" @click="changeStatus(row, 1)">上架</el-button>
          <el-button type="warning" link v-else @click="changeStatus(row, 0)">下架</el-button>
          <el-button type="danger" link @click="delCourse(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑课程' : '新增课程'" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="课程简介">
          <el-input v-model="form.intro" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="授课教师">
          <el-input v-model="form.teacher" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const dialogVisible = ref(false)
const isEdit = ref(false)

const courseList = ref([
  { id: 1, name: '石油工程基础', teacher: '张教授', studentCount: 256, status: 1, intro: '石油工程入门课程' },
  { id: 2, name: '钻井工程技术', teacher: '李教授', studentCount: 189, status: 1, intro: '钻井核心技术讲解' },
  { id: 3, name: '采油工程原理', teacher: '王教授', studentCount: 312, status: 1, intro: '采油工程系统讲解' },
  { id: 4, name: '油藏工程导论', teacher: '赵教授', studentCount: 145, status: 0, intro: '油藏工程基础' }
])

const form = reactive({
  id: null,
  name: '',
  intro: '',
  teacher: ''
})

const openDialog = (row) => {
  if (row) {
    isEdit.value = true
    Object.assign(form, row)
  } else {
    isEdit.value = false
    form.id = null
    form.name = ''
    form.intro = ''
    form.teacher = ''
  }
  dialogVisible.value = true
}

const submitForm = () => {
  if (isEdit.value) {
    const idx = courseList.value.findIndex(i => i.id === form.id)
    courseList.value[idx] = { ...courseList.value[idx], ...form }
    ElMessage.success('修改成功')
  } else {
    courseList.value.push({
      id: Date.now(),
      ...form,
      studentCount: 0,
      status: 0
    })
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
}

const changeStatus = (row, status) => {
  row.status = status
  ElMessage.success(status === 1 ? '已上架' : '已下架')
}

const delCourse = (row) => {
  ElMessageBox.confirm('确定删除该课程？', '提示', { type: 'warning' }).then(() => {
    courseList.value = courseList.value.filter(i => i.id !== row.id)
    ElMessage.success('删除成功')
  })
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>