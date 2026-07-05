<template>
  <div class="course-list-page page-container">
    <div class="filter-bar">
      <el-input
          v-model="searchKey"
          placeholder="搜索课程名称"
          style="width: 300px;"
          clearable
          @search="getList"
      >
        <template #prefix><el-icon><Search /></el-icon></template>
      </el-input>
      <el-select v-model="sortType" style="width: 160px;" @change="getList">
        <el-option label="最新发布" value="new" />
        <el-option label="学习人数最多" value="hot" />
        <el-option label="评分最高" value="score" />
      </el-select>
    </div>

    <el-row :gutter="16" class="course-grid" v-loading="loading">
      <el-col :span="6" v-for="course in courseList" :key="course.id">
        <el-card class="course-card" shadow="hover" @click="goDetail(course.id)">
          <img :src="course.cover" class="course-cover" />
          <div class="course-body">
            <h4 class="course-name">{{ course.name }}</h4>
            <p class="course-teacher">讲师：{{ course.teacher }}</p>
            <div class="course-meta">
              <span class="score">★ {{ course.score }}</span>
              <span>{{ course.studyCount }}人学习</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 分页 -->
    <div class="pagination-wrap">
      <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[8, 12, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="getList"
          @current-change="getList"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCourseListApi } from '../../api/course'

const router = useRouter()
const loading = ref(false)
const searchKey = ref('')
const sortType = ref('new')
const courseList = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(8)

// 获取列表
const getList = async () => {
  loading.value = true
  try {
    const res = await getCourseListApi({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      keyword: searchKey.value,
      sort: sortType.value
    })
    courseList.value = res.data.list
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const goDetail = (id) => {
  router.push(`/course/detail/${id}`)
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.filter-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
.course-card {
  cursor: pointer;
  margin-bottom: 16px;
}
.course-cover {
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 4px;
}
.course-body {
  padding: 8px 0;
}
.course-name {
  font-size: 14px;
  margin: 6px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.course-teacher {
  font-size: 12px;
  color: #86909c;
  margin-bottom: 8px;
}
.course-meta {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #86909c;
}
.score {
  color: #ff7d00;
}
.pagination-wrap {
  margin-top: 20px;
  text-align: right;
}
</style>