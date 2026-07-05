import request from '../utils/request'

/**
 * 发送AI提问
 * @param {Object} data { question }
 */
export function sendAiQuestionApi(data) {
    // return request({ url: '/ai/chat', method: 'post', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: 'success',
                data: {
                    answer: `针对你的问题「${data.question}」：石油工程相关知识点可以分为油藏、钻井、采油三大板块，你可以细化提问获取更专业解答。`
                }
            })
        }, 1200)
    })
}

/**
 * 获取历史对话
 */
export function getChatHistoryApi() {
    // return request({ url: '/ai/history', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: 'success',
                data: {
                    list: [
                        { type: 'ai', content: '你好！我是石油专业AI助教，有任何钻井、采油、油藏相关问题都可以问我~' }
                    ]
                }
            })
        }, 200)
    })
}