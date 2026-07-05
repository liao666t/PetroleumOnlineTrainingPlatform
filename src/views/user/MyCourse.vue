<template>
  <div class="my-course-page page-container">
    <h3>我的选课</h3>
    <el-row :gutter="16">
      <el-col :span="6" v-for="item in myCourseList" :key="item.id">
        <el-card shadow="hover" @click="goDetail(item.id)">
          <img :src="item.cover" class="course-cover" />
          <div class="course-info">
            <h4>{{ item.name }}</h4>
            <p class="teacher">{{ item.teacher }}</p>
            <div class="progress-box">
              <span>学习进度</span>
              <el-progress :percentage="item.progress" :stroke-width="8" />
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'

const router = useRouter()
const myCourseList = ref([
  { id: 1, name: '石油工程基础', teacher: '张教授', progress: 35, cover: 'https://picsum.photos/400/220?random=1' },
  { id: 2, name: '钻井工程技术', teacher: '李教授', progress: 60, cover: 'https://picsum.photos/400/220?random=2' },
  { id: 3, name: '采油工程原理', teacher: '王教授', progress: 20, cover: 'https://picsum.photos/400/220?random=3' }
])

const goDetail = (id) => {
  router.push(`/course/detail/${id}`)
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
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.teacher {
  font-size: 12px;
  color: #86909c;
  margin-bottom: 8px;
}
.progress-box span {
  font-size: 12px;
  color: #86909c;
  display: block;
  margin-bottom: 4px;
}
</style>