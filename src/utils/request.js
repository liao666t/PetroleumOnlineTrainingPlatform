import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
    baseURL: '/api', // 后端接口前缀，对接时改成真实地址
    timeout: 10000
})

// 请求拦截器：自动携带token
request.interceptors.request.use(config => {
    const token = localStorage.getItem('petroleum_token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, error => {
    return Promise.reject(error)
})

// 响应拦截器：统一处理错误
request.interceptors.response.use(
    response => {
        const res = response.data
        // 后端约定code=200为成功
        if (res.code !== 200) {
            ElMessage.error(res.message || '请求失败')
            // 401未登录，跳登录页
            if (res.code === 401) {
                localStorage.removeItem('petroleum_token')
                router.push('/login')
            }
            return Promise.reject(new Error(res.message || '请求失败'))
        }
        return res
    },
    error => {
        ElMessage.error(error.message || '网络异常')
        return Promise.reject(error)
    }
)

export default request