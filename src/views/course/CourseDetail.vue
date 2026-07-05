<template>
  <div class="course-detail-page">
    <div class="course-header page-container">
      <el-row :gutter="20">
        <el-col :span="8">
          <img :src="courseInfo.cover" class="detail-cover" />
        </el-col>
        <el-col :span="16">
          <h2 class="course-title">{{ courseInfo.name }}</h2>
          <p class="course-intro">{{ courseInfo.intro }}</p>
          <div class="course-meta-row">
            <span>讲师：{{ courseInfo.teacher }}</span>
            <span>评分：{{ courseInfo.score }} 分</span>
            <span>{{ courseInfo.studyCount }} 人学习</span>
          </div>
          <div class="btn-row">
            <el-button type="primary" size="large" @click="handleSelect">立即学习</el-button>
            <el-button size="large" @click="isFavorite = !isFavorite">
              <el-icon><Star :filled="isFavorite" /></el-icon>
              {{ isFavorite ? '已收藏' : '收藏课程' }}
            </el-button>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="6">
        <div class="page-container chapter-box">
          <h4>课程目录</h4>
          <el-collapse default-active="1">
            <el-collapse-item v-for="chapter in chapterList" :key="chapter.id" :title="chapter.name" :name="chapter.id">
              <div
                  v-for="res in chapter.resources"
                  :key="res.id"
                  class="res-item"
                  :class="{ active: currentResId === res.id }"
                  @click="currentResId = res.id"
              >
                <el-icon><VideoPlay /></el-icon>
                <span>{{ res.name }}</span>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-col>

      <el-col :span="18">
        <div class="page-container">
          <div class="video-player">
            <div class="video-placeholder">请选择左侧章节开始学习</div>
          </div>

          <el-tabs v-model="activeTab" style="margin-top: 20px;">
            <el-tab-pane label="课程问答" name="question">
              <div class="qa-section">
                <el-button type="primary">我要提问</el-button>
                <div class="question-list">
                  <div v-for="q in questionList" :key="q.id" class="question-item">
                    <div class="q-header">
                      <el-avatar :size="32" />
                      <span class="username">{{ q.username }}</span>
                      <span class="time">{{ q.time }}</span>
                    </div>
                    <p class="q-content">{{ q.content }}</p>
                  </div>
                </div>
              </div>
            </el-tab-pane>

            <el-tab-pane label="课程评价" name="comment">
              <div class="comment-section">
                <el-button type="primary">发表评价</el-button>
                <div class="comment-list">
                  <div v-for="c in commentList" :key="c.id" class="comment-item">
                    <div class="c-header">
                      <el-avatar :size="32" />
                      <div>
                        <p class="username">{{ c.username }}</p>
                        <el-rate v-model="c.score" disabled show-score />
                      </div>
                      <span class="time">{{ c.time }}</span>
                    </div>
                    <p class="c-content">{{ c.content }}</p>
                  </div>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {ElMessage} from 'element-plus'

const courseInfo = ref({
  name: '石油工程基础',
  intro: '本课程系统介绍石油工程的基本概念、核心原理与常用技术，涵盖钻井、采油、油藏等核心模块，适合石油相关专业初学者学习。',
  teacher: '张教授',
  score: 4.8,
  studyCount: 256,
  cover: 'https://picsum.photos/400/220?random=1'
})

const chapterList = ref([
  {
    id: 1, name: '第一章 石油工程概述',
    resources: [
      { id: 11, name: '1.1 石油工业发展历程' },
      { id: 12, name: '1.2 石油工程学科体系' }
    ]
  },
  {
    id: 2, name: '第二章 油气藏基础知识',
    resources: [
      { id: 21, name: '2.1 油气藏的形成与分类' },
      { id: 22, name: '2.2 储层岩石与流体性质' }
    ]
  }
])

const currentResId = ref(null)
const isFavorite = ref(false)
const activeTab = ref('question')

const questionList = ref([
  { id: 1, username: '同学A', time: '2026-06-30', content: '请问孔隙度和渗透率有什么区别？' },
  { id: 2, username: '同学B', time: '2026-07-02', content: '第一章的作业什么时候发布？' }
])

const commentList = ref([
  { id: 1, username: '学生1', score: 5, time: '2026-06-28', content: '老师讲得很清楚，适合入门' },
  { id: 2, username: '学生2', score: 4, time: '2026-07-01', content: '内容很全面，希望多些案例' }
])

const handleSelect = () => {
  ElMessage.success('已加入我的课程')
}
</script>

<style scoped>
.course-title {
  font-size: 24px;
  margin-bottom: 12px;
}
.course-intro {
  color: #4e5969;
  line-height: 1.6;
  margin-bottom: 16px;
}
.course-meta-row {
  display: flex;
  gap: 24px;
  color: #86909c;
  margin-bottom: 24px;
}
.btn-row {
  display: flex;
  gap: 12px;
}
.detail-cover {
  width: 100%;
  border-radius: 8px;
}
.chapter-box {
  min-height: 500px;
}
.chapter-box h4 {
  margin-bottom: 16px;
}
.res-item {
  padding: 8px 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  border-radius: 4px;
  margin-bottom: 4px;
}
.res-item:hover {
  background: #f2f3f5;
}
.res-item.active {
  color: #165dff;
  background: #e8f3ff;
}
.video-player {
  min-height: 400px;
  background: #000;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.video-placeholder {
  color: #86909c;
}
.question-item, .comment-item {
  padding: 16px 0;
  border-bottom: 1px solid #e5e6eb;
}
.q-header, .c-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}
.username {
  font-weight: 500;
}
.time {
  margin-left: auto;
  color: #86909c;
  font-size: 12px;
}
.q-content, .c-content {
  line-height: 1.6;
}
</style>