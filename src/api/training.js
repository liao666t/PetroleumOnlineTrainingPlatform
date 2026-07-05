import request from '../utils/request'

const mockTrainingList = [
    { id: 1, title: '岩心渗透率测定实训', courseName: '油田化学应用', teacher: '陈教授', deadline: '2026-07-10 23:59', submitted: false, requirement: '1. 掌握渗透率测定的基本原理与实验方法；2. 独立完成实验操作并记录数据；3. 完成数据处理与误差分析，撰写完整实训报告。' },
    { id: 2, title: '钻井液性能测试实训', courseName: '钻井工程技术', teacher: '李教授', deadline: '2026-07-15 23:59', submitted: true, score: 88, requirement: '1. 熟悉钻井液常用性能指标；2. 掌握粘度、密度、滤失量的测定方法；3. 完成实验报告。' },
    { id: 3, title: '抽油机工作原理仿真', courseName: '采油工程原理', teacher: '王教授', deadline: '2026-07-20 23:59', submitted: false, requirement: '1. 理解抽油机四连杆机构工作原理；2. 完成仿真操作并记录示功图；3. 分析工况特征。' }
]

const mockMyReportList = [
    { id: 2, title: '钻井液性能测试实训', courseName: '钻井工程技术', submitTime: '2026-06-28 15:30', status: 1, score: 88 },
    { id: 1, title: '岩心渗透率测定实训', courseName: '油田化学应用', submitTime: '2026-07-04 17:20', status: 0, score: null }
]

/**
 * 实训作业列表
 * @param {Object} params
 */
export function getTrainingListApi(params) {
    // return request({ url: '/training/list', method: 'get', params })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: 'success',
                data: { list: mockTrainingList, total: mockTrainingList.length }
            })
        }, 300)
    })
}

/**
 * 实训详情
 * @param {Number} id
 */
export function getTrainingDetailApi(id) {
    // return request({ url: `/training/${id}`, method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            const info = mockTrainingList.find(item => item.id === Number(id)) || mockTrainingList[0]
            resolve({ code: 200, message: 'success', data: info })
        }, 300)
    })
}

/**
 * 提交实训报告
 * @param {Object} data
 */
export function submitReportApi(data) {
    // return request({ url: '/training/submit', method: 'post', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '提交成功', data: null })
        }, 600)
    })
}

/**
 * 我的实训报告列表
 */
export function getMyReportApi() {
    // return request({ url: '/training/my', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: 'success', data: { list: mockMyReportList } })
        }, 300)
    })
}