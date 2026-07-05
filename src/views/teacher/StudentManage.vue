<template>
  <div class="page-container">
    <div class="page-header">
      <h3>学生管理</h3>
      <div class="filter-box">
        <el-input v-model="searchKey" placeholder="搜索姓名/学号" style="width:220px;margin-right:12px;" clearable />
        <el-select v-model="classFilter" placeholder="选择班级" style="width:180px;" clearable>
          <el-option label="石工22-1班" value="石工22-1班" />
          <el-option label="石工22-2班" value="石工22-2班" />
          <el-option label="石工22-3班" value="石工22-3班" />
        </el-select>
      </div>
    </div>

    <el-table :data="filteredList" border stripe>
      <el-table-column prop="studentNo" label="学号" width="140" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="className" label="班级" width="160" />
      <el-table-column prop="courseCount" label="选课数" width="100" />
      <el-table-column prop="totalScore" label="总积分" width="100" />
      <el-table-column prop="avgScore" label="实训平均分" width="120" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="primary" link>学习详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const searchKey = ref('')
const classFilter = ref('')

const studentList = ref([
  { studentNo: '20220101', name: '张三', className: '石工22-1班', courseCount: 3, totalScore: 580, avgScore: 86.5 },
  { studentNo: '20220102', name: '李四', className: '石工22-1班', courseCount: 2, totalScore: 520, avgScore: 82.0 },
  { studentNo: '20220201', name: '王五', className: '石工22-2班', courseCount: 3, totalScore: 490, avgScore: 79.5 },
  { studentNo: '20220202', name: '赵六', className: '石工22-2班', courseCount: 2, totalScore: 420, avgScore: 88.0 },
  { studentNo: '20220301', name: '钱七', className: '石工22-3班', courseCount: 1, totalScore: 310, avgScore: 75.0 }
])

const filteredList = computed(() => {
  return studentList.value.filter(item => {
    const matchKey = !searchKey.value || item.name.includes(searchKey.value) || item.studentNo.includes(searchKey.value)
    const matchClass = !classFilter.value || item.className === classFilter.value
    return matchKey && matchClass
  })
})
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.filter-box {
  display: flex;
}
</style>