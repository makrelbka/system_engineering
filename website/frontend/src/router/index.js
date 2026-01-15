import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Checkout from '../views/Checkout.vue'
import Admin from '../views/Admin.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/checkout',
      name: 'Checkout',
      component: Checkout
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    }
  ]
})

export default router
