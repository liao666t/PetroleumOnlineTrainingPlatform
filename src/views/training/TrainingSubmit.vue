<template>
  <div class="training-submit-page page-container">
    <div v-if="trainingInfo">
      <h3>{{ trainingInfo.title }}</h3>
      <div class="training-info">
        <span>所属课程：{{ trainingInfo.courseName }}</span>
        <span>截止时间：{{ trainingInfo.deadline }}</span>
      </div>

      <div class="requirement-box">
        <h4>实训要求</h4>
        <p>{{ trainingInfo.requirement }}</p>
      </div>

      <div class="report-form">
        <h4>实训报告内容</h4>
        <el-input
            v-model="reportContent"
            type="textarea"
            :rows="12"
            placeholder="请填写实训目的、原理、步骤、结果分析与总结..."
        />

        <div class="upload-section">
          <h4>上传附件</h4>
          <el-upload
              v-model:file-list="fileList"
              action="#"
              :auto-upload="false"
              multiple
          >
            <el-button type="primary">选择文件</el-button>
            <template #tip>
              <div class="el-upload__tip">支持 pdf、word、zip 格式，单个文件不超过 10MB</div>
            </template>
          </el-upload>
        </div>

        <div class="submit-btn">
          <el-button type="primary" size="large" :loading="submitting" @click="handleSubmit">
            提交实训报告
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {ElMessage} from 'element-plus'

const reportContent = ref('')
const fileList = ref([])
const submitting = ref(false)

const trainingInfo = ref({
  title: '岩心渗透率测定实训',
  courseName: '油田化学应用',
  deadline: '2026-07-10 23:59',
  requirement: '1. 掌握渗透率测定的基本原理与实验方法；2. 独立完成实验操作并记录数据；3. 完成数据处理与误差分析，撰写完整实训报告。'
})

const handleSubmit = () => {
  if (!reportContent.value.trim()) {
    ElMessage.warning('请填写报告内容')
    return
  }
  submitting.value = true
  setTimeout(() => {
    submitting.value = false
    ElMessage.success('提交成功')
  }, 1000)
}
</script>

<style scoped>
h3 {
  margin-bottom: 12px;
}
.training-info {
  display: flex;
  gap: 24px;
  color: #86909c;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #e5e6eb;
}
.requirement-box {
  background: #f7f8fa;
  padding: 16px;
  border-radius: 6px;
  margin-bottom: 20px;
}
.requirement-box h4 {
  margin-bottom: 8px;
}
.report-form h4 {
  margin-bottom: 12px;
}
.upload-section {
  margin: 20px 0;
}
.upload-section h4 {
  margin-bottom: 12px;
}
.submit-btn {
  text-align: center;
  margin-top: 30px;
}
</style>