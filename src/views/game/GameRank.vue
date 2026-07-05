<template>
  <div class="rank-page page-container">
    <div class="rank-header">
      <h3>积分排行榜</h3>
      <el-radio-group v-model="rankType">
        <el-radio-button label="class">班级榜</el-radio-button>
        <el-radio-button label="school">全校榜</el-radio-button>
      </el-radio-group>
    </div>

    <div class="top-three">
      <div v-for="(item, index) in topThree" :key="item.id" :class="['top-card', `rank-${index+1}`]">
        <div class="rank-badge">{{ index + 1 }}</div>
        <el-avatar :size="64" :src="item.avatar" />
        <p class="username">{{ item.username }}</p>
        <p class="score">{{ item.totalScore }} 分</p>
      </div>
    </div>

    <el-table :data="rankList" border style="margin-top: 20px;">
      <el-table-column label="排名" width="80" align="center">
        <template #default="{ $index }">
          <span v-if="$index < 3" class="top-rank">{{ $index + 1 }}</span>
          <span v-else>{{ $index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户">
        <template #default="{ row }">
          <div style="display: flex; align-items: center; gap: 10px;">
            <el-avatar :size="32" :src="row.avatar" />
            <span>{{ row.username }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="className" label="班级" width="180" />
      <el-table-column prop="totalScore" label="总积分" width="120" />
      <el-table-column prop="passLevel" label="通关数" width="120" />
    </el-table>

    <div class="my-rank">
      <span>我的排名：第 {{ myRank }} 名</span>
      <span>总积分：{{ myScore }} 分</span>
    </div>
  </div>
</template>

<script setup>
import {computed, ref} from 'vue'

const rankType = ref('class')
const myRank = ref(8)
const myScore = ref(120)

const rankList = ref([
  { id: 1, username: '张三', avatar: '', className: '石工22-1班', totalScore: 580, passLevel: 12 },
  { id: 2, username: '李四', avatar: '', className: '石工22-1班', totalScore: 520, passLevel: 10 },
  { id: 3, username: '王五', avatar: '', className: '石工22-2班', totalScore: 490, passLevel: 9 },
  { id: 4, username: '赵六', avatar: '', className: '石工22-1班', totalScore: 420, passLevel: 8 },
  { id: 5, username: '钱七', avatar: '', className: '石工22-2班', totalScore: 380, passLevel: 7 },
  { id: 6, username: '孙八', avatar: '', className: '石工22-3班', totalScore: 310, passLevel: 6 },
  { id: 7, username: '周九', avatar: '', className: '石工22-1班', totalScore: 260, passLevel: 5 },
  { id: 8, username: '我', avatar: '', className: '石工22-1班', totalScore: 120, passLevel: 2 }
])

const topThree = computed(() => rankList.value.slice(0, 3))
</script>

<style scoped>
.rank-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
.top-three {
  display: flex;
  justify-content: center;
  gap: 40px;
  align-items: flex-end;
}
.top-card {
  text-align: center;
  padding: 20px;
  border-radius: 12px;
  width: 180px;
  position: relative;
}
.rank-1 {
  background: linear-gradient(135deg, #ffd700 0%, #ffb800 100%);
  color: #fff;
  transform: translateY(-20px);
}
.rank-2 {
  background: linear-gradient(135deg, #c0c0c0 0%, #a0a0a0 100%);
  color: #fff;
}
.rank-3 {
  background: linear-gradient(135deg, #cd7f32 0%, #b87333 100%);
  color: #fff;
}
.rank-badge {
  position: absolute;
  top: -10px;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 30px;
  background: #fff;
  border-radius: 50%;
  color: #333;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
}
.username {
  margin: 10px 0 4px;
  font-weight: 500;
}
.score {
  font-size: 18px;
  font-weight: bold;
}
.top-rank {
  color: #ff7d00;
  font-weight: bold;
}
.my-rank {
  margin-top: 20px;
  text-align: right;
  font-size: 16px;
  display: flex;
  gap: 24px;
  justify-content: flex-end;
}
</style>