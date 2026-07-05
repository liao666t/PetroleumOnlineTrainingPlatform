<template>
  <div class="game-level-page page-container">
    <div class="level-header">
      <h3>知识点闯关</h3>
      <div class="my-score">
        <span>我的积分：</span>
        <span class="score-num">{{ myScore }}</span>
        <el-button type="primary" link @click="$router.push('/game/rank')">查看排行榜</el-button>
      </div>
    </div>

    <div class="level-grid">
      <div
          v-for="(level, index) in levelList"
          :key="level.id"
          :class="['level-card', level.unlocked ? 'unlocked' : 'locked']"
          @click="level.unlocked && openLevel(level)"
      >
        <div class="level-num">第 {{ index + 1 }} 关</div>
        <div class="level-name">{{ level.name }}</div>
        <div class="level-info">
          <span>奖励积分：{{ level.rewardScore }}</span>
          <span v-if="level.passed" class="passed">已通关</span>
        </div>
        <el-icon v-if="!level.unlocked" class="lock-icon"><Lock /></el-icon>
      </div>
    </div>

    <el-dialog v-model="answerDialog" title="闯关答题" width="700px" :close-on-click-modal="false">
      <div v-if="currentQuestion">
        <div class="question-header">
          <span>第 {{ currentIndex + 1 }} 题 / 共 {{ questionList.length }} 题</span>
          <span>剩余时间：{{ countdown }}s</span>
        </div>
        <div class="question-content">
          <h4>{{ currentQuestion.content }}</h4>
          <el-radio-group v-model="userAnswer">
            <el-radio v-for="opt in currentQuestion.options" :key="opt.key" :label="opt.key">
              {{ opt.key }}. {{ opt.value }}
            </el-radio>
          </el-radio-group>
        </div>
        <div class="question-footer">
          <el-button :disabled="currentIndex === 0" @click="prevQuestion">上一题</el-button>
          <el-button v-if="currentIndex < questionList.length - 1" type="primary" @click="nextQuestion">
            下一题
          </el-button>
          <el-button v-else type="success" @click="submitAnswer">提交答卷</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {computed, onUnmounted, ref} from 'vue'
import {ElMessage} from 'element-plus'

const myScore = ref(120)
const answerDialog = ref(false)
const levelList = ref([
  { id: 1, name: '石油基础概念', rewardScore: 20, unlocked: true, passed: true },
  { id: 2, name: '油气藏基础知识', rewardScore: 25, unlocked: true, passed: false },
  { id: 3, name: '钻井工程入门', rewardScore: 30, unlocked: true, passed: false },
  { id: 4, name: '采油技术原理', rewardScore: 30, unlocked: false, passed: false },
  { id: 5, name: '油田化学应用', rewardScore: 35, unlocked: false, passed: false },
  { id: 6, name: '油气储运技术', rewardScore: 35, unlocked: false, passed: false },
  { id: 7, name: '测井与解释', rewardScore: 40, unlocked: false, passed: false },
  { id: 8, name: '综合提升关卡', rewardScore: 50, unlocked: false, passed: false }
])

const questionList = ref([
  {
    content: '石油的主要成分是？',
    options: [
      { key: 'A', value: '碳氢化合物' },
      { key: 'B', value: '碳水化合物' },
      { key: 'C', value: '无机物' },
      { key: 'D', value: '氧化物' }
    ],
    answer: 'A'
  },
  {
    content: '下列哪项不属于石油工程三大核心方向？',
    options: [
      { key: 'A', value: '油藏工程' },
      { key: 'B', value: '钻井工程' },
      { key: 'C', value: '采油工程' },
      { key: 'D', value: '石油炼制' }
    ],
    answer: 'D'
  }
])

const currentIndex = ref(0)
const userAnswer = ref('')
const userAnswers = ref({})
const countdown = ref(60)
let timer = null

const currentQuestion = computed(() => questionList.value[currentIndex.value])

const openLevel = () => {
  currentIndex.value = 0
  userAnswers.value = {}
  userAnswer.value = ''
  countdown.value = 60
  answerDialog.value = true
  startCountdown()
}

const startCountdown = () => {
  clearInterval(timer)
  timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
      ElMessage.warning('时间到，自动提交')
      submitAnswer()
    }
  }, 1000)
}

const prevQuestion = () => {
  userAnswers.value[currentIndex.value] = userAnswer.value
  currentIndex.value--
  userAnswer.value = userAnswers.value[currentIndex.value] || ''
}

const nextQuestion = () => {
  userAnswers.value[currentIndex.value] = userAnswer.value
  currentIndex.value++
  userAnswer.value = userAnswers.value[currentIndex.value] || ''
}

const submitAnswer = () => {
  clearInterval(timer)
  userAnswers.value[currentIndex.value] = userAnswer.value
  let correct = 0
  questionList.value.forEach((q, i) => {
    if (userAnswers.value[i] === q.answer) correct++
  })
  const rate = Math.round((correct / questionList.value.length) * 100)
  if (rate >= 60) {
    ElMessage.success(`闯关成功！正确率${rate}%，获得25积分`)
    myScore.value += 25
  } else {
    ElMessage.error(`闯关失败，正确率${rate}%，再接再厉`)
  }
  answerDialog.value = false
}

onUnmounted(() => {
  clearInterval(timer)
})
</script>

<style scoped>
.level-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}
.my-score {
  display: flex;
  align-items: center;
  gap: 8px;
}
.score-num {
  color: #ff7d00;
  font-size: 20px;
  font-weight: bold;
}
.level-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}
.level-card {
  padding: 24px;
  border-radius: 12px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
  border: 2px solid transparent;
}
.level-card.unlocked {
  background: linear-gradient(135deg, #e8f3ff 0%, #fff 100%);
  border-color: #165dff;
}
.level-card.unlocked:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(22, 93, 255, 0.2);
}
.level-card.locked {
  background: #f2f3f5;
  color: #c9cdd4;
  cursor: not-allowed;
}
.level-num {
  font-size: 14px;
  color: #86909c;
  margin-bottom: 8px;
}
.level-name {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 12px;
}
.level-info {
  font-size: 13px;
  display: flex;
  justify-content: space-between;
}
.passed {
  color: #00b42a;
}
.lock-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 40px;
  opacity: 0.3;
}
.question-header {
  display: flex;
  justify-content: space-between;
  padding-bottom: 12px;
  border-bottom: 1px solid #e5e6eb;
  margin-bottom: 20px;
}
.question-content h4 {
  margin-bottom: 20px;
  line-height: 1.6;
}
.question-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
  padding-top: 16px;
  border-top: 1px solid #e5e6eb;
}
</style>