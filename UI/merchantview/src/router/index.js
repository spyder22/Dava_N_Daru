import { createRouter, createWebHistory } from 'vue-router'

import AddCategory from '../views/Category/AddCategory'
import Category from '../views/Category/Category'
import Product from '../views/Product/Product'
import Admin from "../views/Admin";
import AddProduct from "../views/Product/AddProduct";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../components/Home.vue')
  },
  {
    path: '/login',
    name: 'LOGIN',
    component: () => import('../components/Login.vue')
  },
  {
    path: '/merchant',
    name: 'merchant',
    component: () => import('../components/MerchantLogin.vue')
  },
  {
    path: '/merchantsignup',
    name: 'MERCHANT',
    component: () => import('../components/MerchantSignup.vue')
  },
  {
    path: '/merchant-forgot-password',
    name: 'forgot-password',
    component: () => import('../components/MerchantForgotPassword.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('../components/Profile.vue')
  },{
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory
  },
  {
    path: '/admin/category',
    name: 'Category',
    component: Category
  },
    // admin home pagecd 
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  },
  {
    path: '/admin/product',
    name: 'AdminProduct',
    component: Product
  },
    // add product
  {
    path: '/admin/product/new',
    name: 'AddProduct',
    component: AddProduct
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
