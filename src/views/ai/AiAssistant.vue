<template>
  <div class="ai-page page-container">
    <div class="chat-header">
      <h3>
        <el-icon color="#165DFF"><ChatDotRound /></el-icon>
        石油专属AI智能助教
      </h3>
      <p>可解答钻井、储运、地质、化工专业问题，分析薄弱知识点</p>
    </div>

    <!-- 对话内容区 -->
    <div class="chat-content" ref="chatContentRef">
      <div v-for="(msg, index) in chatList" :key="index" :class="['msg-item', msg.role]">
        <el-avatar :size="36" :src="msg.role === 'user' ? userStore.userInfo.avatar : '/ai-avatar.png'" />
        <div class="msg-bubble">
          <p v-html="msg.content"></p>
        </div>
      </div>
      <div v-if="loading" class="msg-item ai">
        <el-avatar :size="36" />
        <div class="msg-bubble"><el-icon class="is-loading"><Loading /></el-icon> 思考中...</div>
      </div>
    </div>

    <!-- 输入区 -->
    <div class="chat-input">
      <el-input
          v-model="inputText"
          type="textarea"
          :rows="3"
          placeholder="请输入石油专业问题，例如：钻井液的主要功能有哪些？"
          @keydown.enter.ctrl="sendMessage"
      />
      <div class="input-footer">
        <span>按 Ctrl+Enter 发送</span>
        <el-button type="primary" :loading="loading" @click="sendMessage">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref} from 'vue'
import {useUserStore} from '../../store/user'
import {getChatHistoryApi, sendAiQuestionApi} from '../../api/ai'

const userStore = useUserStore()
const chatList = ref([])
const inputText = ref('')
const loading = ref(false)
const chatContentRef = ref()

onMounted(() => {
  loadHistory()
})

// 加载历史对话
const loadHistory = async () => {
  const res = await getChatHistoryApi()
  chatList.value = res.data
  scrollToBottom()
}

const sendMessage = async () => {
  if (!inputText.value.trim() || loading.value) return
  const question = inputText.value.trim()
  chatList.value.push({ role: 'user', content: question })
  inputText.value = ''
  loading.value = true
  await nextTick()
  scrollToBottom()

  try {
    const res = await sendAiQuestionApi({ question })
    chatList.value.push({ role: 'ai', content: res.data.answer })
  } finally {
    loading.value = false
    await nextTick()
    scrollToBottom()
  }
}

const scrollToBottom = () => {
  nextTick(() => {
    chatContentRef.value.scrollTop = chatContentRef.value.scrollHeight
  })
}
</script>

<style scoped>
.ai-page {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 160px);
}
.chat-header {
  padding-bottom: 16px;
  border-bottom: 1px solid var(--petroleum-border);
  margin-bottom: 16px;
}
.chat-header h3 {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
}
.chat-header p {
  color: #86909C;
  font-size: 14px;
}
.chat-content {
  flex: 1;
  overflow-y: auto;
  padding-right: 10px;
}
.msg-item {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}
.msg-item.user {
  flex-direction: row-reverse;
}
.msg-bubble {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 8px;
  line-height: 1.6;
  background: #F2F3F5;
}
.msg-item.user .msg-bubble {
  background: var(--petroleum-primary);
  color: #fff;
}
.chat-input {
  border-top: 1px solid var(--petroleum-border);
  padding-top: 16px;
}
.input-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
  font-size: 12px;
  color: #86909C;
}
</style>