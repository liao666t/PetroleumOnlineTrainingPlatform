<template>
  <div class="ai-chat oil-card">
    <div class="chat-title">
      <h3>⛽ 石油专业AI智能助教</h3>
      <span class="tip">钻井/储运/地质/化工专业问题均可提问</span>
    </div>
    <div class="chat-content" ref="chatRef">
      <div v-for="item in chatList" :key="item.chat_id" class="chat-item" :class="item.isSelf?'self':'ai'">
        <div class="msg-box">{{ item.isSelf ? item.user_question : item.ai_answer }}</div>
      </div>
      <div v-if="loading" class="loading-text">AI正在解析专业知识点...</div>
    </div>
    <div class="chat-input">
      <el-input
          v-model="question"
          placeholder="输入石油专业问题，如：钻井液循环流程、油气管道防腐技术"
          type="textarea"
          :rows="2"
      />
      <el-button class="oil-btn-primary mt-2 w-full" @click="sendMsg" :loading="loading">发送提问</el-button>
    </div>
  </div>
</template>
<script setup>
import {nextTick, ref, watch} from 'vue'
import {sendAiMsg} from '@/api/aiChat'

const props = defineProps(['chatList'])
const emit = defineEmits(['addChat'])
const question = ref('')
const loading = ref(false)
const chatRef = ref(null)

watch(()=>props.chatList, ()=>{
  nextTick(()=> chatRef.value.scrollTop = chatRef.value.scrollHeight)
})

const sendMsg = async () => {
  if(!question.value.trim()) return
  loading.value = true
  const q = question.value
  question.value = ''
  emit('addChat', {user_question: q, ai_answer:'', isSelf:true})
  const res = await sendAiMsg(q)
  loading.value = false
  emit('addChat', {user_question: q, ai_answer: res.data, isSelf:false})
}
</script>
<style scoped lang="scss">
.chat-title {
  border-bottom: 1px solid #eee;
  padding-bottom: 12px;
  margin-bottom: 16px;
  h3 { color: #0F4C81; margin-bottom: 4px; }
  .tip { font-size: 12px; color: #8A98A9; }
}
.chat-content {
  height: 400px;
  overflow-y: auto;
  padding: 10px;
  background: #f9fafc;
  border-radius: 8px;
  margin-bottom: 16px;
}
.chat-item {
  margin: 10px 0;
  .msg-box {
    max-width: 70%;
    padding: 10px 14px;
    border-radius: 10px;
    line-height: 1.6;
  }
}
.self {
  text-align: right;
  .msg-box {
    background: #0F4C81;
    color: #fff;
  }
}
.ai {
  text-align: left;
  .msg-box {
    background: #e8f0f9;
    color: #222;
  }
}
.loading-text {
  color: #0F4C81;
  text-align: center;
  padding: 10px;
}
</style>