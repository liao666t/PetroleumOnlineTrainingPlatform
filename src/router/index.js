import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

const routes = [
    // 登录注册
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login/Login.vue'),
        meta: { title: '登录', noAuth: true }
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Login/Register.vue'),
        meta: { title: '注册', noAuth: true }
    },

    // 学生端主布局
    {
        path: '/',
        component: () => import('../components/StudentLayout.vue'),
        redirect: '/home',
        meta: { role: 'student' },
        children: [
            {
                path: 'home',
                name: 'Home',
                component: () => import('../views/home/Home.vue'),
                meta: { title: '平台首页' }
            },
            {
                path: 'course/list',
                name: 'CourseList',
                component: () => import('../views/course/CourseList.vue'),
                meta: { title: '课程列表' }
            },
            {
                path: 'course/detail/:id',
                name: 'CourseDetail',
                component: () => import('../views/course/CourseDetail.vue'),
                meta: { title: '课程详情' }
            },
            {
                path: 'training/list',
                name: 'TrainingList',
                component: () => import('../views/training/TrainingList.vue'),
                meta: { title: '实训作业' }
            },
            {
                path: 'training/submit/:id',
                name: 'TrainingSubmit',
                component: () => import('../views/training/TrainingSubmit.vue'),
                meta: { title: '提交实训报告' }
            },
            {
                path: 'game/level',
                name: 'GameLevel',
                component: () => import('../views/game/GameLevel.vue'),
                meta: { title: '知识点闯关' }
            },
            {
                path: 'game/rank',
                name: 'GameRank',
                component: () => import('../views/game/GameRank.vue'),
                meta: { title: '积分排行榜' }
            },
            {
                path: 'ai/assistant',
                name: 'AiAssistant',
                component: () => import('../views/ai/AiAssistant.vue'),
                meta: { title: 'AI石油助教' }
            },
            {
                path: 'user/profile',
                name: 'UserProfile',
                component: () => import('../views/user/Profile.vue'),
                meta: { title: '个人信息' }
            },
            {
                path: 'user/my-course',
                name: 'MyCourse',
                component: () => import('../views/user/MyCourse.vue'),
                meta: { title: '我的选课' }
            },
            {
                path: 'user/my-training',
                name: 'MyTraining',
                component: () => import('../views/user/MyTraining.vue'),
                meta: { title: '我的实训报告' }
            },
            {
                path: 'user/favorite',
                name: 'MyFavorite',
                component: () => import('../views/user/Favorite.vue'),
                meta: { title: '我的收藏' }
            },
            {
                path: 'user/study-data',
                name: 'StudyData',
                component: () => import('../views/user/StudyData.vue'),
                meta: { title: '学习数据分析' }
            }
        ]
    },

    // 教师端
    {
        path: '/teacher',
        component: () => import('../components/TeacherLayout.vue'),
        redirect: '/teacher/course',
        meta: { role: 'teacher' },
        children: [
            {
                path: 'course',
                name: 'TeacherCourse',
                component: () => import('../views/teacher/CourseManage.vue'),
                meta: { title: '课程管理' }
            },
            {
                path: 'training',
                name: 'TeacherTraining',
                component: () => import('../views/teacher/TrainingManage.vue'),
                meta: { title: '实训作业管理' }
            },
            {
                path: 'review',
                name: 'TeacherReview',
                component: () => import('../views/teacher/TrainingReview.vue'),
                meta: { title: '实训批阅' }
            },
            {
                path: 'student',
                name: 'TeacherStudent',
                component: () => import('../views/teacher/StudentManage.vue'),
                meta: { title: '学生管理' }
            },
            {
                path: 'statistics',
                name: 'TeacherStatistics',
                component: () => import('../views/teacher/ScoreStatistics.vue'),
                meta: { title: '成绩统计' }
            },
            {
                path: 'profile',
                name: 'TeacherProfile',
                component: () => import('../views/teacher/TeacherProfile.vue'),
                meta: { title: '个人信息' }
            }
        ]
    },

    // 404
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('../views/error/NotFound.vue'),
        meta: { title: '页面不存在', noAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 全局路由
router.beforeEach((to, from, next) => {
    document.title = (to.meta.title || '石油智能教学实训平台') + ' - 石油智能教学实训平台'
    const userStore = useUserStore()
    const hasToken = !!userStore.token

    // 免登录页面直接放行
    if (to.meta.noAuth) {
        next()
        return
    }

    // 未登录跳登录页
    if (!hasToken) {
        next('/login')
        return
    }

    // 角色权限校验
    const needRole = to.matched[1]?.meta?.role
    if (needRole && userStore.role !== needRole) {
        // 角色不匹配，跳转到对应身份的首页
        if (userStore.role === 'teacher') {
            next('/teacher/course')
        } else {
            next('/home')
        }
        return
    }

    next()
})

export default router