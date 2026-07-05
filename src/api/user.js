import request from '../utils/request'

/**
 * 登录
 * @param {Object} data { username, password, type }
 */
export function loginApi(data) {
    // ========== 真实接口（对接后端时放开） ==========
    // return request({
    //   url: '/user/login',
    //   method: 'post',
    //   data
    // })

    // ========== 模拟数据（默认开启） ==========
    return new Promise(resolve => {
        setTimeout(() => {
            const isTeacher = data.type === 'teacher'
            resolve({
                code: 200,
                message: '登录成功',
                data: {
                    token: 'mock-token-' + Date.now(),
                    role: isTeacher ? 'teacher' : 'student',
                    userInfo: {
                        id: 1,
                        name: isTeacher ? '张教授' : '测试学生',
                        avatar: '',
                        phone: isTeacher ? '139****6666' : '138****8888',
                        className: isTeacher ? '' : '石工22-1班',
                        major: isTeacher ? '' : '石油工程',
                        department: isTeacher ? '石油工程学院' : '',
                        title: isTeacher ? '教授' : ''
                    }
                }
            })
        }, 500)
    })
}

/**
 * 注册
 * @param {Object} data
 */
export function registerApi(data) {
    // return request({ url: '/user/register', method: 'post', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '注册成功', data: null })
        }, 600)
    })
}

/**
 * 获取用户信息
 */
export function getUserInfoApi() {
    // return request({ url: '/user/info', method: 'get' })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                code: 200,
                message: 'success',
                data: {
                    id: 1,
                    name: '测试学生',
                    avatar: '',
                    phone: '138****8888',
                    className: '石工22-1班',
                    major: '石油工程'
                }
            })
        }, 300)
    })
}

/**
 * 修改个人信息
 * @param {Object} data
 */
export function updateUserInfoApi(data) {
    // return request({ url: '/user/info', method: 'put', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '修改成功', data: null })
        }, 400)
    })
}

/**
 * 修改密码
 * @param {Object} data { oldPassword, newPassword }
 */
export function updatePasswordApi(data) {
    // return request({ url: '/user/password', method: 'put', data })

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({ code: 200, message: '密码修改成功', data: null })
        }, 400)
    })
}