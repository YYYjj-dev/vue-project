import {
    createRouter,
    createWebHashHistory,
    createWebHistory,
} from 'vue-router'

import pinia from '../store'
import { defineUser } from '../store/userStore'

let userStore = defineUser(pinia)
const router = createRouter({
    history: createWebHistory(),
    routes: [
        // 在这里添加你的路由配置
        {
            // path:路由路径，这个路径是程序员自己确定的
            path: '/',
            // redirect:重定向
            redirect: { path: '/Home' },
        },
        {
            path: '/Home',
            name: 'Home',
            component: () => import('../views/Home.vue')
        },
        {
            path: '/regulation',
            name: 'regulation',
            component: () => import('../views/user/regulation/regulation.vue')
        },
        {
            path: '/additive_type',
            name: 'additive_type',
            component: () => import('../views/user/additive/additive_type.vue')
        },
        {
            path: '/additive_zhuanqu',
            name: 'additive_zhuanqu',
            component: () => import('../views/user/additive/additive_zhuanqu.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/additive_zhuanqu_info1',
            name: 'additive_zhuanqu_info1',
            component: () => import('../views/user/additive/additive_zhuanqu_info1.vue')
        },
        {
            path: '/additive_zhuanqu_info2',
            name: 'additive_zhuanqu_info2',
            component: () => import('../views/user/additive/additive_zhuanqu_info2.vue')
        },
        {
            path: '/additive_type_info/:category/:imageId',
            name: 'additive_type_info',
            component: () => import('../views/user/additive/additive_type_info.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/cases',
            name: 'cases',
            component: () => import('../views/user/cases/cases.vue')
        },
        {
            path: '/cases_more',
            name: 'cases_more',
            component: () => import('../views/user/cases/cases_more.vue')
        },
        {
            path: '/cases_info/',
            name: 'cases_info',
            component: () => import('../views/user/cases/cases_info.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/news',
            name: 'news',
            component: () => import('../views/user/news/news.vue')
        },
        {
            path: '/news_info/:id',
            name: 'news_info',
            component: () => import('../views/user/news/news_info.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/shop_rec',
            name: 'shop_rec',
            component: () => import('../views/user/shop/shop_rec.vue')
        },
        {
            path: '/shop_rec_info/:id',
            name: 'shop_rec_info',
            component: () => import('../views/user/shop/shop_rec_info.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/shop_sel_info1/:id',
            name: 'shop_sel_info1',
            component: () => import('../views/user/shop/shop_sel_info1.vue'),
            props: true  // 启用 props 传递路由参数
        },
        {
            path: '/shop_sel_info2',
            name: 'shop_sel_info2',
            component: () => import('../views/user/shop/shop_sel_info2.vue')
        },
        {
            path: '/shop_sel_info3',
            name: 'shop_sel_info3',
            component: () => import('../views/user/shop/shop_sel_info3.vue')
        },
        {
            path: '/feedback',
            name: 'feedback',
            component: () => import('../views/user/feedback/feedback.vue')
        },
        {
            path: '/regulation_info/:id',
            name: 'regulation_info',
            component: () => import('../views/user/regulation/regulation_info.vue'),
            props: true  // 使用路由参数作为 props 传递到组件
        },
        {
            path: '/shop_sel',
            name: 'shop_sel',
            component: () => import('../views/user/shop/shop_sel.vue')
        },
        {
            path: '/login',
            name: 'Login',
            component: () => import('../views/Login.vue')
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import('../views/Register.vue')
        },
        {
            path: '/cart',
            name: 'Cart',
            component: () => import('../views/user/cart/cart.vue')
        },
        {
            path:'/upload',
            name:'Upload',
            component: () => import('../views/test/upload.vue')
        }, 
        {
        path: '/admin',
        name: 'admin',
        component: () => import('../views/Admin.vue'),
        children: [
            {
                path: 'safety',
                name: 'safety_manage',
                component: () => import('../views/admin/SafetyManage.vue')
            },
            {
                path: 'news',
                name: 'news_manage',
                component: () => import('../views/admin/NewsManage.vue')
            },
            {
                path: 'cases',
                name: 'cases_manage',
                component: () => import('../views/admin/CasesManage.vue')
            },
            {
                path: 'additives',
                name: 'additives_manage',
                component: () => import('../views/admin/AdditiveManage.vue')
            },
            {
                path: 'users',
                name: 'users_manage',
                component: () => import('../views/admin/UsersManage.vue')
            },
            {
                path: 'products',
                name: 'products_manage',
                component: () => import('../views/admin/ProductsManage.vue')
            }
        ]
    }
        // ... 其他路由
    ]
})

export default router