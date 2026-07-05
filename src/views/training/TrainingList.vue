<template>
  <div class="training-list-page page-container">
    <h3>实训作业列表</h3>
    <el-table :data="trainingList" border stripe>
      <el-table-column prop="title" label="实训标题" min-width="200" />
      <el-table-column prop="courseName" label="所属课程" width="180" />
      <el-table-column prop="teacher" label="发布教师" width="120" />
      <el-table-column prop="deadline" label="截止时间" width="180" />
      <el-table-column label="提交状态" width="120">
        <template #default="{ row }">
          <el-tag :type="row.submitted ? 'success' : 'warning'">
            {{ row.submitted ? '已提交' : '待提交' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button type="primary" link @click="goSubmit(row.id)">
            {{ row.submitted ? '查看报告' : '提交报告' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'

const router = useRouter()
const trainingList = ref([
  { id: 1, title: '岩心渗透率测定实训', courseName: '油田化学应用', teacher: '陈教授', deadline: '2026-07-10 23:59', submitted: false },
  { id: 2, title: '钻井液性能测试实训', courseName: '钻井工程技术', teacher: '李教授', deadline: '2026-07-15 23:59', submitted: true },
  { id: 3, title: '抽油机工作原理仿真', courseName: '采油工程原理', teacher: '王教授', deadline: '2026-07-20 23:59', submitted: false }
])

const goSubmit = (id) => {
  router.push(`/training/submit/${id}`)
}
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
</style>