<template>
  <el-container class="teacher-layout">
    <el-aside width="220px" class="aside">
      <div class="logo">教师管理端</div>
      <el-menu
          :default-active="activeMenu"
          router
          background-color="#001529"
          text-color="#fff"
          active-text-color="#409eff"
      >
        <el-menu-item index="/teacher/course">
          <el-icon><Reading /></el-icon>
          <span>课程管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/training">
          <el-icon><Document /></el-icon>
          <span>实训作业管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/review">
          <el-icon><EditPen /></el-icon>
          <span>实训批阅</span>
        </el-menu-item>
        <el-menu-item index="/teacher/student">
          <el-icon><User /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/statistics">
          <el-icon><DataAnalysis /></el-icon>
          <span>成绩统计</span>
        </el-menu-item>
        <el-sub-menu index="user-center">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/teacher/profile">个人信息</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">石油智能教学实训平台 · 教师端</div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" />
              <span style="margin-left:8px;">{{ userStore.userInfo.name || '老师' }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
// 手动导入所有用到的图标
import {
  Reading,
  Document,
  EditPen,
  User,
  DataAnalysis,
  Setting
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const activeMenu = computed(() => route.path)

const handleCommand = (cmd) => {
  if (cmd === 'profile') {
    router.push('/teacher/profile')
  } else if (cmd === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.teacher-layout {
  height: 100vh;
}
.aside {
  background: #001529;
  color: #fff;
  overflow: hidden;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  border-bottom: 1px solid #1f3a58;
}
.header {
  background: #fff;
  border-bottom: 1px solid #e5e6eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
}
.header-left {
  font-size: 16px;
  font-weight: 500;
}
.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.main {
  background: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}
</style>