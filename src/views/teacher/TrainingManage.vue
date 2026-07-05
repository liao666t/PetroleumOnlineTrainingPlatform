<template>
  <div class="page-container">
    <div class="page-header">
      <h3>实训作业管理</h3>
      <el-button type="primary" @click="openDialog()">发布实训</el-button>
    </div>

    <el-table :data="trainingList" border stripe>
      <el-table-column prop="title" label="实训标题" min-width="200" />
      <el-table-column prop="courseName" label="所属课程" width="180" />
      <el-table-column prop="deadline" label="截止时间" width="180" />
      <el-table-column prop="submitCount" label="已提交/总人数" width="140" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '进行中' : '已结束' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="primary" link @click="viewDetail(row)">查看详情</el-button>
          <el-button type="danger" link @click="delTraining(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="发布实训作业" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="实训标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="所属课程">
          <el-select v-model="form.courseId" style="width:100%">
            <el-option v-for="c in courseOptions" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="截止时间">
          <el-date-picker v-model="form.deadline" type="datetime" style="width:100%" />
        </el-form-item>
        <el-form-item label="实训要求">
          <el-input v-model="form.requirement" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const dialogVisible = ref(false)

const courseOptions = ref([
  { id: 1, name: '石油工程基础' },
  { id: 2, name: '钻井工程技术' },
  { id: 3, name: '采油工程原理' }
])

const trainingList = ref([
  { id: 1, title: '岩心渗透率测定实训', courseName: '油田化学应用', deadline: '2026-07-10 23:59', submitCount: '32/45', status: 1 },
  { id: 2, title: '钻井液性能测试实训', courseName: '钻井工程技术', deadline: '2026-07-15 23:59', submitCount: '28/45', status: 1 },
  { id: 3, title: '抽油机工作原理仿真', courseName: '采油工程原理', deadline: '2026-06-30 23:59', submitCount: '40/45', status: 0 }
])

const form = reactive({
  title: '',
  courseId: '',
  deadline: '',
  requirement: ''
})

const openDialog = () => {
  dialogVisible.value = true
}

const submitForm = () => {
  trainingList.value.unshift({
    id: Date.now(),
    title: form.title,
    courseName: courseOptions.value.find(c => c.id === form.courseId)?.name,
    deadline: form.deadline,
    submitCount: '0/45',
    status: 1
  })
  ElMessage.success('发布成功')
  dialogVisible.value = false
}

const viewDetail = (row) => {
  ElMessage.info('跳转到批阅页面查看学生提交')
}

const delTraining = (row) => {
  ElMessageBox.confirm('确定删除该实训？', '提示', { type: 'warning' }).then(() => {
    trainingList.value = trainingList.value.filter(i => i.id !== row.id)
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