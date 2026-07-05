<template>
  <el-container class="student-layout">
    <el-aside width="220px" class="aside">
      <div class="logo">石油实训平台</div>
      <el-menu
          :default-active="activeMenu"
          router
          background-color="#001529"
          text-color="#fff"
          active-text-color="#409eff"
      >
        <el-menu-item index="/home">
          <el-icon><HomeFilled /></el-icon>
          <span>平台首页</span>
        </el-menu-item>
        <el-menu-item index="/course/list">
          <el-icon><Reading /></el-icon>
          <span>课程中心</span>
        </el-menu-item>
        <el-menu-item index="/training/list">
          <el-icon><Document /></el-icon>
          <span>实训作业</span>
        </el-menu-item>
        <el-menu-item index="/game/level">
          <el-icon><Trophy /></el-icon>
          <span>闯关刷题</span>
        </el-menu-item>
        <el-menu-item index="/ai/assistant">
          <el-icon><ChatDotRound /></el-icon>
          <span>AI助教</span>
        </el-menu-item>
        <el-sub-menu index="user-center">
          <template #title>
            <el-icon><User /></el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/profile">个人信息</el-menu-item>
          <el-menu-item index="/user/my-course">我的选课</el-menu-item>
          <el-menu-item index="/user/my-training">我的实训</el-menu-item>
          <el-menu-item index="/user/favorite">我的收藏</el-menu-item>
          <el-menu-item index="/user/study-data">学习数据</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">石油智能教学实训平台 · 学生端</div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" />
              <span style="margin-left:8px;">{{ userStore.userInfo.name || '同学' }}</span>
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
// 手动导入所有用到的图标，彻底消除IDE警告
import {
  HomeFilled,
  Reading,
  Document,
  Trophy,
  ChatDotRound,
  User
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const activeMenu = computed(() => route.path)

const handleCommand = (cmd) => {
  if (cmd === 'profile') {
    router.push('/user/profile')
  } else if (cmd === 'logout') {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.student-layout {
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