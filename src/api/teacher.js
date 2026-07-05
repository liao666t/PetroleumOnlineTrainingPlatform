import request from '../utils/request'

// 课程管理
export function getTeacherCourseListApi(params) {
    // return request({ url: '/teacher/course/list', method: 'get', params })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = [
                { id: 1, name: '石油工程基础', teacher: '张教授', studentCount: 256, status: 1, intro: '石油工程入门课程' },
                { id: 2, name: '钻井工程技术', teacher: '李教授', studentCount: 189, status: 1, intro: '钻井核心技术讲解' },
                { id: 3, name: '采油工程原理', teacher: '王教授', studentCount: 312, status: 1, intro: '采油工程系统讲解' },
                { id: 4, name: '油藏工程导论', teacher: '赵教授', studentCount: 145, status: 0, intro: '油藏工程基础' }
            ]
            resolve({ code: 200, message: 'success', data: { list, total: list.length } })
        }, 300)
    })
}

export function addCourseApi(data) {
    // return request({ url: '/teacher/course', method: 'post', data })
    return new Promise(resolve => {
        setTimeout(() => resolve({ code: 200, message: '新增成功', data: null }), 400)
    })
}

export function updateCourseApi(data) {
    // return request({ url: '/teacher/course', method: 'put', data })
    return new Promise(resolve => {
        setTimeout(() => resolve({ code: 200, message: '修改成功', data: null }), 400)
    })
}

export function deleteCourseApi(id) {
    // return request({ url: `/teacher/course/${id}`, method: 'delete' })
    return new Promise(resolve => {
        setTimeout(() => resolve({ code: 200, message: '删除成功', data: null }), 400)
    })
}

// 实训管理
export function getTeacherTrainingApi(params) {
    // return request({ url: '/teacher/training/list', method: 'get', params })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = [
                { id: 1, title: '岩心渗透率测定实训', courseName: '油田化学应用', deadline: '2026-07-10 23:59', submitCount: '32/45', status: 1 },
                { id: 2, title: '钻井液性能测试实训', courseName: '钻井工程技术', deadline: '2026-07-15 23:59', submitCount: '28/45', status: 1 },
                { id: 3, title: '抽油机工作原理仿真', courseName: '采油工程原理', deadline: '2026-06-30 23:59', submitCount: '40/45', status: 0 }
            ]
            resolve({ code: 200, message: 'success', data: { list, total: list.length } })
        }, 300)
    })
}

export function publishTrainingApi(data) {
    // return request({ url: '/teacher/training', method: 'post', data })
    return new Promise(resolve => {
        setTimeout(() => resolve({ code: 200, message: '发布成功', data: null }), 400)
    })
}

// 批阅
export function getSubmitListApi(trainingId) {
    // return request({ url: `/teacher/submit/list/${trainingId}`, method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = [
                { id: 1, studentName: '张三', className: '石工22-1班', submitTime: '2026-07-03 14:20', status: 0, score: null, content: '本次实训完成了岩心渗透率的测定实验，记录了三组数据，计算得到平均渗透率为120mD...' },
                { id: 2, studentName: '李四', className: '石工22-1班', submitTime: '2026-07-03 16:45', status: 1, score: 88, content: '完成了全部实验步骤，数据处理正确，误差分析合理...' },
                { id: 3, studentName: '王五', className: '石工22-2班', submitTime: '2026-07-04 09:10', status: 0, score: null, content: '实验操作完整，数据记录详细，总结部分有待加强...' }
            ]
            resolve({ code: 200, message: 'success', data: { list } })
        }, 300)
    })
}

export function reviewSubmitApi(data) {
    // return request({ url: '/teacher/review', method: 'post', data })
    return new Promise(resolve => {
        setTimeout(() => resolve({ code: 200, message: '批阅完成', data: null }), 400)
    })
}

// 学生管理
export function getStudentListApi(params) {
    // return request({ url: '/teacher/student/list', method: 'get', params })

    return new Promise(resolve => {
        setTimeout(() => {
            const list = [
                { studentNo: '20220101', name: '张三', className: '石工22-1班', courseCount: 3, totalScore: 580, avgScore: 86.5 },
                { studentNo: '20220102', name: '李四', className: '石工22-1班', courseCount: 2, totalScore: 520, avgScore: 82.0 },
                { studentNo: '20220201', name: '王五', className: '石工22-2班', courseCount: 3, totalScore: 490, avgScore: 79.5 },
                { studentNo: '20220202', name: '赵六', className: '石工22-2班', courseCount: 2, totalScore: 420, avgScore: 88.0 },
                { studentNo: '20220301', name: '钱七', className: '石工22-3班', courseCount: 1, totalScore: 310, avgScore: 75.0 }
            ]
            resolve({ code: 200, message: 'success', data: { list, total: list.length } })
        }, 300)
    })
}

// 统计数据
export function getStatisticsApi() {
    // return request({ url: '/teacher/statistics', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: 'success',
                data: {
                    totalStudent: 135,
                    totalSubmit: 328,
                    avgScore: 82.5,
                    passRate: 94.2,
                    scoreDistribution: [
                        { range: '90-100', count: 28 },
                        { range: '80-89', count: 56 },
                        { range: '70-79', count: 32 },
                        { range: '60-69', count: 11 },
                        { range: '不及格', count: 8 }
                    ],
                    classRank: [
                        { name: '石工22-1班', avg: 85.6 },
                        { name: '石工22-2班', avg: 82.3 },
                        { name: '石工22-3班', avg: 79.8 }
                    ]
                }
            })
        }, 300)
    })
}