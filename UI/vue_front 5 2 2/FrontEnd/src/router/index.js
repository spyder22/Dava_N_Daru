import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import DrinkProducts from '@/components/DrinkProducts'
import CigarProducts from '@/components/CigarProducts'
import FoodProducts from '@/components/FoodProducts'
import MedicineProducts from '@/components/MedicineProducts'
import GamingProducts from '@/components/GamingProducts'
// import AddCategory from '../views/Category/AddCategory'
// import Category from '../views/Category/Category'
// import Product from '../views/Product/Product'
// import Admin from '../views/Admin'
// import AddProduct from '../views/Product/AddProduct'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'LOGIN',
      component: () => import('../components/Login.vue')
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../components/Signin.vue')
    },
    {
      path: '/merchant',
      name: 'merchant',
      component: () => import('../components/MerchantLogin.vue')
    },
    {
      path: '/usersignup',
      name: 'USER',
      component: () => import('../components/UserSignup.vue')
    },
    {
      path: '/merchantsignup',
      name: 'MERCHANT',
      component: () => import('../components/MerchantSignup.vue')
    },
    {
      path: '/forgot-password',
      name: 'forgot-password',
      component: () => import('../components/ForgotPassword.vue')
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
    }, {
      path: '/drink/productslist',
      name: 'DrinkProducts',
      component: DrinkProducts
    },
    {
      path: '/cigar/productslist',
      name: 'CigarProducts',
      component: CigarProducts
    },
    {
      path: '/food/productslist',
      name: 'FoodProducts',
      component: FoodProducts
    },
    {
      path: '/medicine/productslist',
      name: 'MedicineProducts',
      component: MedicineProducts
    },
    {
      path: '/gaming/productslist',
      name: 'GamingProducts',
      component: GamingProducts
    },
    {
      path: '/product/',
      name: 'productslist',
      component: () => import('../components/Productslist.vue')
    },
    {
      path: '/productcart',
      name: 'productcart',
      component: () => import('../components/ProductCart.vue')
    },
    {
      path: '/checkout',
      name: 'checkout',
      component: () => import('../components/Checkout.vue')
    },
    {
      path: '/productdetail/:id',
      name: 'ProductDetail',
      component: () => import('../components/ProductDetail.vue'),
      props: true
    },
    {
      path: '/cart',
      name: 'cart',
      component: () => import('../components/cart.vue')
    }
    // {
    //   path: '/admin/category/add',
    //   name: 'AddCategory',
    //   component: AddCategory
    // },
    // {
    //   path: '/admin/category',
    //   name: 'Category',
    //   component: Category
    // }
    // admin home page
    // {
    //   path: '/admin',
    //   name: 'Admin',
    //   component: Admin
    // },
    // {
    //   path: '/admin/product',
    //   name: 'AdminProduct',
    //   component: Product
    // },
    // // add product
    // {
    //   path: '/admin/product/new',
    //   name: 'AddProduct',
    //   component: AddProduct
    // }
  ]
})
