<template>
  <div class="home-page">
    <!-- 欢迎栏 -->
    <div class="welcome-card page-container">
      <div>
        <h2>你好，{{ userStore.userInfo.name || '同学' }} 👋</h2>
        <p class="welcome-desc">欢迎回到石油智能教学实训平台，今天也要加油学习哦~</p>
      </div>
      <div class="date-info">
        <p class="date">{{ currentDate }}</p>
        <p class="week">{{ currentWeek }}</p>
      </div>
    </div>

    <!-- 数据统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6">
        <div class="stat-card blue">
          <div class="stat-icon"><el-icon><Reading /></el-icon></div>
          <div class="stat-info">
            <p class="stat-num">3</p>
            <p class="stat-label">在学课程</p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card green">
          <div class="stat-icon"><el-icon><Document /></el-icon></div>
          <div class="stat-info">
            <p class="stat-num">2</p>
            <p class="stat-label">待提交实训</p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card orange">
          <div class="stat-icon"><el-icon><Trophy /></el-icon></div>
          <div class="stat-info">
            <p class="stat-num">120</p>
            <p class="stat-label">累计积分</p>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card purple">
          <div class="stat-icon"><el-icon><Clock /></el-icon></div>
          <div class="stat-info">
            <p class="stat-num">36.5</p>
            <p class="stat-label">学习时长(h)</p>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:20px;">
      <!-- 推荐课程 -->
      <el-col :span="16">
        <div class="page-container">
          <div class="section-header">
            <h3>推荐课程</h3>
            <el-button type="primary" link @click="$router.push('/course/list')">查看全部</el-button>
          </div>
          <el-row :gutter="16">
            <el-col :span="8" v-for="course in recommendCourses" :key="course.id">
              <el-card shadow="hover" class="course-card" @click="$router.push(`/course/detail/${course.id}`)">
                <img :src="course.cover" class="course-cover" />
                <div class="course-body">
                  <h4>{{ course.name }}</h4>
                  <p class="teacher">{{ course.teacher }}</p>
                  <div class="meta">
                    <span class="score">★ {{ course.score }}</span>
                    <span>{{ course.studyCount }}人学习</span>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-col>

      <!-- 公告 + 快捷入口 -->
      <el-col :span="8">
        <div class="page-container notice-box">
          <h3>平台公告</h3>
          <ul class="notice-list">
            <li v-for="item in noticeList" :key="item.id">
              <span class="dot"></span>
              <span class="notice-title">{{ item.title }}</span>
              <span class="notice-time">{{ item.time }}</span>
            </li>
          </ul>
        </div>

        <div class="page-container" style="margin-top:20px;">
          <h3>快捷入口</h3>
          <div class="quick-entry">
            <div class="entry-item" @click="$router.push('/training/list')">
              <el-icon><Document /></el-icon>
              <span>实训作业</span>
            </div>
            <div class="entry-item" @click="$router.push('/game/level')">
              <el-icon><Trophy /></el-icon>
              <span>闯关刷题</span>
            </div>
            <div class="entry-item" @click="$router.push('/ai/assistant')">
              <el-icon><ChatDotRound /></el-icon>
              <span>AI助教</span>
            </div>
            <div class="entry-item" @click="$router.push('/user/study-data')">
              <el-icon><DataAnalysis /></el-icon>
              <span>学习数据</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '../../store/user'

const userStore = useUserStore()

// 当前日期
const currentDate = computed(() => {
  const d = new Date()
  return `${d.getFullYear()}年${d.getMonth()+1}月${d.getDate()}日`
})
const currentWeek = computed(() => {
  const weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六']
  return weeks[new Date().getDay()]
})

const recommendCourses = ref([
  { id: 1, name: '石油工程基础', teacher: '张教授', score: 4.8, studyCount: 256, cover: 'https://picsum.photos/400/220?random=1' },
  { id: 2, name: '钻井工程技术', teacher: '李教授', score: 4.6, studyCount: 189, cover: 'https://picsum.photos/400/220?random=2' },
  { id: 3, name: '采油工程原理', teacher: '王教授', score: 4.9, studyCount: 312, cover: 'https://picsum.photos/400/220?random=3' }
])

const noticeList = ref([
  { id: 1, title: '关于期末实训提交的通知', time: '07-04' },
  { id: 2, title: '暑期线上课程安排公布', time: '07-02' },
  { id: 3, title: '知识点闯关活动上线', time: '06-30' },
  { id: 4, title: '平台系统升级维护通知', time: '06-28' }
])

onMounted(() => {
  userStore.getUserInfo()
})
</script>

<style scoped>
.welcome-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #165dff 0%, #4080ff 100%);
  color: #fff;
  margin-bottom: 20px;
}
.welcome-card h2 {
  font-size: 22px;
  margin-bottom: 8px;
}
.welcome-desc {
  opacity: 0.9;
}
.date-info {
  text-align: right;
}
.date {
  font-size: 18px;
  font-weight: 500;
}
.week {
  opacity: 0.9;
  font-size: 14px;
  margin-top: 4px;
}

.stat-row .stat-card {
  padding: 20px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 16px;
  color: #fff;
}
.stat-card.blue { background: linear-gradient(135deg, #409eff, #165dff); }
.stat-card.green { background: linear-gradient(135deg, #36d399, #00b42a); }
.stat-card.orange { background: linear-gradient(135deg, #ffb84d, #ff7d00); }
.stat-card.purple { background: linear-gradient(135deg, #9c7cff, #722ed1); }
.stat-icon {
  font-size: 36px;
  opacity: 0.9;
}
.stat-num {
  font-size: 28px;
  font-weight: bold;
  line-height: 1.2;
}
.stat-label {
  font-size: 13px;
  opacity: 0.9;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.course-card {
  cursor: pointer;
}
.course-cover {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}
.course-body {
  padding: 8px 0;
}
.course-body h4 {
  font-size: 14px;
  margin: 4px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.teacher {
  font-size: 12px;
  color: #86909c;
  margin-bottom: 6px;
}
.meta {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #86909c;
}
.score { color: #ff7d00; }

.notice-list {
  list-style: none;
}
.notice-list li {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px dashed #e5e6eb;
  font-size: 14px;
}
.dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #165dff;
  margin-right: 10px;
}
.notice-title {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.notice-time {
  color: #86909c;
  font-size: 12px;
}

.quick-entry {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
  margin-top: 12px;
}
.entry-item {
  padding: 16px;
  background: #f7f8fa;
  border-radius: 8px;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s;
}
.entry-item:hover {
  background: #e8f3ff;
  color: #165dff;
}
.entry-item .el-icon {
  font-size: 24px;
  margin-bottom: 6px;
}
.entry-item span {
  font-size: 13px;
  display: block;
}
</style>