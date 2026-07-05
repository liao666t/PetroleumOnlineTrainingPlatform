import request from '../utils/request'

const mockLevels = [
    { id: 1, name: '石油基础概念', rewardScore: 20, unlocked: true, passed: true },
    { id: 2, name: '油气藏基础知识', rewardScore: 25, unlocked: true, passed: false },
    { id: 3, name: '钻井工程入门', rewardScore: 30, unlocked: true, passed: false },
    { id: 4, name: '采油技术原理', rewardScore: 30, unlocked: false, passed: false },
    { id: 5, name: '油田化学应用', rewardScore: 35, unlocked: false, passed: false },
    { id: 6, name: '油气储运技术', rewardScore: 35, unlocked: false, passed: false },
    { id: 7, name: '测井与解释', rewardScore: 40, unlocked: false, passed: false },
    { id: 8, name: '综合提升关卡', rewardScore: 50, unlocked: false, passed: false }
]

const mockQuestions = [
    {
        id: 1,
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
        id: 2,
        content: '下列哪项不属于石油工程三大核心方向？',
        options: [
            { key: 'A', value: '油藏工程' },
            { key: 'B', value: '钻井工程' },
            { key: 'C', value: '采油工程' },
            { key: 'D', value: '石油炼制' }
        ],
        answer: 'D'
    }
]

const mockRankList = [
    { id: 1, username: '张三', avatar: '', className: '石工22-1班', totalScore: 580, passLevel: 12 },
    { id: 2, username: '李四', avatar: '', className: '石工22-1班', totalScore: 520, passLevel: 10 },
    { id: 3, username: '王五', avatar: '', className: '石工22-2班', totalScore: 490, passLevel: 9 },
    { id: 4, username: '赵六', avatar: '', className: '石工22-1班', totalScore: 420, passLevel: 8 },
    { id: 5, username: '钱七', avatar: '', className: '石工22-2班', totalScore: 380, passLevel: 7 },
    { id: 6, username: '孙八', avatar: '', className: '石工22-3班', totalScore: 310, passLevel: 6 },
    { id: 7, username: '周九', avatar: '', className: '石工22-1班', totalScore: 260, passLevel: 5 },
    { id: 8, username: '我', avatar: '', className: '石工22-1班', totalScore: 120, passLevel: 2 }
]

/**
 * 关卡列表
 */
export function getLevelListApi() {
    // return request({ url: '/game/levels', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: 'success', data: { list: mockLevels, myScore: 120 } })
        }, 300)
    })
}

/**
 * 获取关卡题目
 * @param {Number} levelId
 */
export function getLevelQuestionsApi(levelId) {
    // return request({ url: `/game/questions/${levelId}`, method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: 'success', data: { list: mockQuestions } })
        }, 300)
    })
}

/**
 * 提交答卷
 * @param {Object} data { levelId, answers }
 */
export function submitAnswerApi(data) {
    // return request({ url: '/game/submit', method: 'post', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: '提交成功',
                data: {
                    correctCount: 2,
                    totalCount: 2,
                    score: 25,
                    passed: true
                }
            })
        }, 500)
    })
}

/**
 * 积分排行榜
 */
export function getRankListApi() {
    // return request({ url: '/game/rank', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: 'success', data: { list: mockRankList } })
        }, 300)
    })
}