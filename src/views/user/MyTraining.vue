<template>
  <div class="my-training-page page-container">
    <h3>我的实训报告</h3>
    <el-table :data="reportList" border stripe>
      <el-table-column prop="title" label="实训标题" min-width="200" />
      <el-table-column prop="courseName" label="所属课程" width="180" />
      <el-table-column prop="submitTime" label="提交时间" width="180" />
      <el-table-column label="批阅状态" width="120">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">
            {{ row.status === 1 ? '已批阅' : '待批阅' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="得分" width="100">
        <template #default="{ row }">
          <span v-if="row.status === 1" class="score">{{ row.score }} 分</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="primary" link>查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import {ref} from 'vue'

const reportList = ref([
  { id: 1, title: '钻井液性能测试实训', courseName: '钻井工程技术', submitTime: '2026-06-28 15:30', status: 1, score: 88 },
  { id: 2, title: '岩心渗透率测定实训', courseName: '油田化学应用', submitTime: '2026-07-04 17:20', status: 0, score: null }
])
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
.score {
  color: #ff7d00;
  font-weight: bold;
}
</style>