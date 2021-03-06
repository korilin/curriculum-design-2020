import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login'
import Main from '../views/main'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: "Home"
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/main/:userType/:userName',
        name: 'Main',
        component: Main,
        props: true
    }
]

const router = new VueRouter({
    routes
})

export default router
