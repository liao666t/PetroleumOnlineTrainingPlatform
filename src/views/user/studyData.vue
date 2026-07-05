<template>
  <div class="study-data-page page-container">
    <h3>学习数据分析</h3>

    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">累计学习时长</p>
          <p class="stat-value">36.5 <span>小时</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">已修课程数</p>
          <p class="stat-value">3 <span>门</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">闯关通关数</p>
          <p class="stat-value">2 <span>关</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">实训完成数</p>
          <p class="stat-value">1 <span>项</span></p>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <div class="page-container">
          <h4>学习进度分布</h4>
          <div class="progress-list">
            <div v-for="item in progressList" :key="item.name" class="progress-item">
              <span class="course-name">{{ item.name }}</span>
              <el-progress :percentage="item.value" :stroke-width="12" />
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="page-container">
          <h4>积分获取记录</h4>
          <el-timeline>
            <el-timeline-item
                v-for="item in scoreRecord"
                :key="item.id"
                :timestamp="item.time"
            >
              {{ item.title }} <span class="score-add">+{{ item.score }}</span>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {ref} from 'vue'

const progressList = ref([
  { name: '石油工程基础', value: 35 },
  { name: '钻井工程技术', value: 60 },
  { name: '采油工程原理', value: 20 }
])

const scoreRecord = ref([
  { id: 1, title: '完成第1关闯关', score: 20, time: '2026-07-01 14:30' },
  { id: 2, title: '完成第2关闯关', score: 25, time: '2026-07-02 16:20' },
  { id: 3, title: '提交钻井液实训报告', score: 30, time: '2026-07-03 10:15' },
  { id: 4, title: '连续学习7天奖励', score: 45, time: '2026-07-04 08:00' }
])
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
.stat-cards .stat-card {
  background: linear-gradient(135deg, #e8f3ff 0%, #f0f7ff 100%);
  padding: 24px;
  border-radius: 12px;
}
.stat-label {
  color: #4e5969;
  margin-bottom: 8px;
}
.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #165dff;
}
.stat-value span {
  font-size: 16px;
  font-weight: normal;
}
.progress-list {
  margin-top: 16px;
}
.progress-item {
  margin-bottom: 20px;
}
.course-name {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
}
.score-add {
  color: #00b42a;
  font-weight: 500;
}
</style>