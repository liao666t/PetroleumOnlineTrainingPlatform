<template>
  <div class="page-container">
    <div class="page-header">
      <h3>实训批阅</h3>
      <el-select v-model="trainingId" placeholder="选择实训" style="width:260px">
        <el-option v-for="t in trainingList" :key="t.id" :label="t.title" :value="t.id" />
      </el-select>
    </div>

    <el-table :data="submitList" border stripe>
      <el-table-column prop="studentName" label="学生姓名" width="120" />
      <el-table-column prop="className" label="班级" width="160" />
      <el-table-column prop="submitTime" label="提交时间" width="180" />
      <el-table-column label="批阅状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">
            {{ row.status === 1 ? '已批阅' : '待批阅' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="score" label="得分" width="100">
        <template #default="{ row }">
          <span v-if="row.status === 1" style="color:#ff7d00;font-weight:bold;">{{ row.score }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="primary" link @click="openReview(row)">批阅</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="reviewDialog" title="批阅实训报告" width="700px">
      <div v-if="currentRow">
        <div class="report-info">
          <p><b>学生：</b>{{ currentRow.studentName }}</p>
          <p><b>提交时间：</b>{{ currentRow.submitTime }}</p>
        </div>
        <div class="report-content">
          <h4>报告内容</h4>
          <p>{{ currentRow.content }}</p>
        </div>
        <el-divider />
        <el-form label-width="100px">
          <el-form-item label="打分">
            <el-input-number v-model="reviewForm.score" :min="0" :max="100" />
            <span style="margin-left:8px;color:#86909c;">满分 100 分</span>
          </el-form-item>
          <el-form-item label="评语">
            <el-input v-model="reviewForm.comment" type="textarea" :rows="4" placeholder="请输入评语..." />
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <el-button @click="reviewDialog = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交批阅</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const trainingId = ref(1)
const reviewDialog = ref(false)
const currentRow = ref(null)

const trainingList = ref([
  { id: 1, title: '岩心渗透率测定实训' },
  { id: 2, title: '钻井液性能测试实训' }
])

const submitList = ref([
  { id: 1, studentName: '张三', className: '石工22-1班', submitTime: '2026-07-03 14:20', status: 0, score: null, content: '本次实训完成了岩心渗透率的测定实验，记录了三组数据，计算得到平均渗透率为120mD...' },
  { id: 2, studentName: '李四', className: '石工22-1班', submitTime: '2026-07-03 16:45', status: 1, score: 88, content: '完成了全部实验步骤，数据处理正确，误差分析合理...' },
  { id: 3, studentName: '王五', className: '石工22-2班', submitTime: '2026-07-04 09:10', status: 0, score: null, content: '实验操作完整，数据记录详细，总结部分有待加强...' }
])

const reviewForm = reactive({
  score: 0,
  comment: ''
})

const openReview = (row) => {
  currentRow.value = row
  reviewForm.score = row.score || 0
  reviewForm.comment = row.comment || ''
  reviewDialog.value = true
}

const submitReview = () => {
  currentRow.value.status = 1
  currentRow.value.score = reviewForm.score
  currentRow.value.comment = reviewForm.comment
  ElMessage.success('批阅完成')
  reviewDialog.value = false
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.report-info p {
  margin-bottom: 8px;
}
.report-content h4 {
  margin-bottom: 10px;
}
.report-content p {
  line-height: 1.8;
  color: #4e5969;
  background: #f7f8fa;
  padding: 12px;
  border-radius: 6px;
}
</style>