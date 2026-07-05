<template>
  <div class="page-container">
    <h3>成绩数据分析</h3>

    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">班级总人数</p>
          <p class="stat-value">135 <span>人</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">已提交实训</p>
          <p class="stat-value">328 <span>份</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">班级平均分</p>
          <p class="stat-value">82.5 <span>分</span></p>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card">
          <p class="stat-label">及格率</p>
          <p class="stat-value">94.2 <span>%</span></p>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:20px;">
      <el-col :span="12">
        <div class="chart-box">
          <h4>各分数段人数分布</h4>
          <div class="bar-chart">
            <div v-for="item in scoreDistribution" :key="item.range" class="bar-item">
              <div class="bar" :style="{ height: item.count * 3 + 'px' }"></div>
              <span class="label">{{ item.range }}</span>
              <span class="count">{{ item.count }}人</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="chart-box">
          <h4>各班级平均分对比</h4>
          <div class="class-rank">
            <div v-for="(item, idx) in classRank" :key="item.name" class="rank-item">
              <span class="rank-no">{{ idx + 1 }}</span>
              <span class="class-name">{{ item.name }}</span>
              <div class="progress-wrap">
                <div class="progress-bar" :style="{ width: item.avg + '%' }"></div>
              </div>
              <span class="score">{{ item.avg }}分</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const scoreDistribution = ref([
  { range: '90-100', count: 28 },
  { range: '80-89', count: 56 },
  { range: '70-79', count: 32 },
  { range: '60-69', count: 11 },
  { range: '不及格', count: 8 }
])

const classRank = ref([
  { name: '石工22-1班', avg: 85.6 },
  { name: '石工22-2班', avg: 82.3 },
  { name: '石工22-3班', avg: 79.8 }
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
.chart-box {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  min-height: 320px;
}
.chart-box h4 {
  margin-bottom: 20px;
}
.bar-chart {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 220px;
  border-bottom: 1px solid #e5e6eb;
  padding-bottom: 10px;
}
.bar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}
.bar {
  width: 40px;
  background: linear-gradient(180deg, #409eff 0%, #165dff 100%);
  border-radius: 4px 4px 0 0;
}
.label {
  font-size: 12px;
  color: #86909c;
}
.count {
  font-size: 12px;
  font-weight: 500;
}
.class-rank {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.rank-item {
  display: flex;
  align-items: center;
  gap: 12px;
}
.rank-no {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: #165dff;
  color: #fff;
  text-align: center;
  line-height: 24px;
  font-size: 12px;
}
.class-name {
  width: 100px;
}
.progress-wrap {
  flex: 1;
  height: 12px;
  background: #f2f3f5;
  border-radius: 6px;
  overflow: hidden;
}
.progress-bar {
  height: 100%;
  background: linear-gradient(90deg, #409eff, #165dff);
  border-radius: 6px;
}
.score {
  width: 60px;
  text-align: right;
  font-weight: 500;
}
</style>