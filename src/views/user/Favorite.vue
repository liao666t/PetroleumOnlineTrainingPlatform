<template>
  <div class="favorite-page page-container">
    <h3>我的收藏</h3>
    <el-row :gutter="16" v-if="favoriteList.length">
      <el-col :span="6" v-for="item in favoriteList" :key="item.id">
        <el-card shadow="hover" @click="goDetail(item.id)">
          <img :src="item.cover" class="course-cover" />
          <div class="course-info">
            <h4>{{ item.name }}</h4>
            <p class="teacher">{{ item.teacher }}</p>
            <div class="meta">
              <span>★ {{ item.score }}</span>
              <el-button type="danger" link size="small" @click.stop="cancelFavorite(item.id)">
                取消收藏
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-empty v-else description="暂无收藏课程" />
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'

const router = useRouter()
const favoriteList = ref([
  { id: 1, name: '油藏工程导论', teacher: '赵教授', score: 4.5, cover: 'https://picsum.photos/400/220?random=4' },
  { id: 2, name: '油气储运技术', teacher: '刘教授', score: 4.7, cover: 'https://picsum.photos/400/220?random=5' }
])

const goDetail = (id) => {
  router.push(`/course/detail/${id}`)
}

const cancelFavorite = (id) => {
  favoriteList.value = favoriteList.value.filter(item => item.id !== id)
  ElMessage.success('已取消收藏')
}
</script>

<style scoped>
h3 {
  margin-bottom: 20px;
}
.course-cover {
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 4px;
}
.course-info {
  padding: 8px 0;
}
.course-info h4 {
  font-size: 14px;
  margin-bottom: 6px;
}
.teacher {
  font-size: 12px;
  color: #86909c;
  margin-bottom: 8px;
}
.meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: #ff7d00;
}
</style>