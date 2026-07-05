import request from '../utils/request'

// 模拟课程数据
const mockCourses = [
    { id: 1, name: '石油工程基础', teacher: '张教授', score: 4.8, studyCount: 256, intro: '本课程系统介绍石油工程的基本概念、核心原理与常用技术，涵盖钻井、采油、油藏等核心模块，适合石油相关专业初学者学习。', cover: 'https://picsum.photos/400/220?random=1', status: 1 },
    { id: 2, name: '钻井工程技术', teacher: '李教授', score: 4.6, studyCount: 189, intro: '全面讲解钻井工程的工艺流程、关键设备、井下工具与常用技术，结合现场案例深化理解。', cover: 'https://picsum.photos/400/220?random=2', status: 1 },
    { id: 3, name: '采油工程原理', teacher: '王教授', score: 4.9, studyCount: 312, intro: '系统讲授采油工程的核心原理、人工举升技术、增产措施与日常管理方法。', cover: 'https://picsum.photos/400/220?random=3', status: 1 },
    { id: 4, name: '油藏工程导论', teacher: '赵教授', score: 4.5, studyCount: 145, intro: '油气藏形成与分类、储层物性、渗流力学基础、开发方案设计入门。', cover: 'https://picsum.photos/400/220?random=4', status: 1 },
    { id: 5, name: '油气储运技术', teacher: '刘教授', score: 4.7, studyCount: 201, intro: '油气集输、长距离管道输送、储存技术与安全管理全流程讲解。', cover: 'https://picsum.photos/400/220?random=5', status: 1 },
    { id: 6, name: '油田化学应用', teacher: '陈教授', score: 4.4, studyCount: 167, intro: '油田化学剂原理、钻井液、完井液、驱油化学与油田水处理技术。', cover: 'https://picsum.photos/400/220?random=6', status: 1 },
    { id: 7, name: '测井方法与解释', teacher: '周教授', score: 4.8, studyCount: 233, intro: '常规测井方法、测井资料解释、储层参数计算与应用场景。', cover: 'https://picsum.photos/400/220?random=7', status: 1 },
    { id: 8, name: '石油地质基础', teacher: '吴教授', score: 4.6, studyCount: 178, intro: '沉积岩与沉积相、构造地质、油气成藏机理与勘探方法基础。', cover: 'https://picsum.photos/400/220?random=8', status: 1 }
]

/**
 * 课程列表（分页）
 * @param {Object} params { pageNum, pageSize, keyword }
 */
export function getCourseListApi(params) {
    // return request({ url: '/course/list', method: 'get', params })

    return new Promise(resolve => {
        setTimeout(() => {
            const { pageNum = 1, pageSize = 8, keyword = '' } = params
            let list = mockCourses.filter(item => item.name.includes(keyword))
            const total = list.length
            const start = (pageNum - 1) * pageSize
            list = list.slice(start, start + pageSize)
            resolve({
                code: 200,
                message: 'success',
                data: { list, total, pageNum, pageSize }
            })
        }, 300)
    })
}

/**
 * 课程详情
 * @param {Number} id 课程id
 */
export function getCourseDetailApi(id) {
    // return request({ url: `/course/${id}`, method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            const course = mockCourses.find(item => item.id === Number(id)) || mockCourses[0]
            // 模拟章节数据
            const chapters = [
                {
                    id: 1, name: '第一章 课程概述',
                    resources: [
                        { id: 11, name: '1.1 学科发展与课程介绍' },
                        { id: 12, name: '1.2 核心知识体系梳理' }
                    ]
                },
                {
                    id: 2, name: '第二章 核心原理',
                    resources: [
                        { id: 21, name: '2.1 基本概念与定义' },
                        { id: 22, name: '2.2 核心公式与推导' }
                    ]
                }
            ]
            resolve({
                code: 200,
                message: 'success',
                data: { ...course, chapters }
            })
        }, 300)
    })
}

/**
 * 我的选课列表
 */
export function getMyCourseApi() {
    // return request({ url: '/course/my', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = mockCourses.slice(0, 3).map((item, idx) => ({
                ...item,
                progress: [35, 60, 20][idx]
            }))
            resolve({ code: 200, message: 'success', data: { list } })
        }, 300)
    })
}

/**
 * 收藏课程
 * @param {Number} courseId
 */
export function favoriteCourseApi(courseId) {
    // return request({ url: `/course/favorite/${courseId}`, method: 'post' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '收藏成功', data: null })
        }, 200)
    })
}

/**
 * 取消收藏
 * @param {Number} courseId
 */
export function cancelFavoriteApi(courseId) {
    // return request({ url: `/course/favorite/${courseId}`, method: 'delete' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '已取消收藏', data: null })
        }, 200)
    })
}

/**
 * 我的收藏列表
 */
export function getFavoriteListApi() {
    // return request({ url: '/course/favorite/list', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = mockCourses.slice(3, 5)
            resolve({ code: 200, message: 'success', data: { list } })
        }, 300)
    })
}