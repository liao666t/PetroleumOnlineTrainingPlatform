import { defineStore } from 'pinia'
import { getToken, setToken, removeToken } from '../utils/auth'
import { loginApi, getUserInfoApi } from '../api/user'

export const useUserStore = defineStore('user', {
    state: () => ({
        token: getToken() || '',
        userInfo: {},
        role: localStorage.getItem('petroleum_role') || ''
    }),

    actions: {
        // 登录
        async login(loginForm) {
            const res = await loginApi(loginForm)
            this.token = res.data.token
            this.role = res.data.role
            this.userInfo = res.data.userInfo
            setToken(res.data.token)
            localStorage.setItem('petroleum_role', res.data.role)
            return res
        },

        // 获取用户信息
        async getUserInfo() {
            if (this.userInfo.name) return this.userInfo
            const res = await getUserInfoApi()
            this.userInfo = res.data
            return res.data
        },

        // 退出登录
        logout() {
            this.token = ''
            this.userInfo = {}
            this.role = ''
            removeToken()
            localStorage.removeItem('petroleum_role')
        }
    }
})