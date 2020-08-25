import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store/index'
import axios from 'axios';
// import setData from './Permissions';
// import CryptoJS from 'vue-cryptojs'
// import Home from '../views/Home.vue'

Vue.use(VueRouter)

  const routes = [
    {
      name: 'Login',
      path: '/login',
      component: () => import('@/views/pages/Login'),
    },
    {
      name: 'Forget Password',
      path: '/forget',
      component: () => import('@/views/pages/forgetPassword'),
    },
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/dashboard/Index'),
    meta:{
      requireAuth:true,
    },
    children:[
      {
        name: 'Dashboard',
        path: '',
        component: () => import('@/views/dashboard/Dashboard'),
        meta:{
          requireAuth:true,
        },
      },
      // Pages
      // {
      //   name: 'User Profile',
      //   path: 'user',
      //   component: () => import('@/views/dashboard/pages/UserProfile'),
      // },

      {
        name: 'User Management',
        path: 'manage',
        component: () => import('@/views/pages/manage'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name: 'Create Company',
        path: 'createcompany',
        component: () => import('@/views/pages/CreateCompany'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name:'Roles Management',
        path:'role-management',
        component: () => import('@/views/pages/RoleManagement'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name:'Roles Assigning',
        path:'role-assigning',
        component: () => import('@/views/pages/PermissionAssigning'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name: 'Create Company',
        path: 'create-company',
        component: () => import('@/views/pages/CreateCompany'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name: 'Location/Department Manage',
        path: 'location',
        component: () => import('@/views/pages/LocationDep'),
        meta:{
          requireAuth:true,
        },
      },
      {
        name: 'Employee Management',
        path: 'emp-manage',
        component: () => import('@/views/pages/employeeManage'),
        meta:{
          requireAuth:true,
        },
      },
        {
          name: 'User Logs',
          path: 'user-logs',
          component: () => import('@/views/pages/userLogs'),
          meta:{
            requireAuth:true,
          },
      },
      
    ]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')

  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.matched.some(record => record.meta.requireAuth)){
    console.log('inside route check ');
    if(store.state.userLogin){
      // this.$router.push('/superAdmin')
      next();
    }
    // else if(store.state.instituteAdmin){
    //   this.$router.push('/i-admin')
    // }
    // else if(store.state.marketing){
    //   this.$router.push('/users/dashboard')
    // }
    else
    {
      // let userLogin=this.CryptoJS.AES.decrypt(localStorage.getItem('allowed'),process.env.VUE_APP_CRYPTO_KEY).toString(this.CryptoJS.enc.Utf8);
      console.log('outside of token present');
      if(localStorage.getItem('token') != null)
      {
        console.log('token is  present');
        // setData();
        axios.get(process.env.VUE_APP_SERVER_ADDRESS+"/auth/permissions?jwt="+localStorage.getItem('token'),
        { headers: { authentication: 'nomi '+localStorage.getItem('token') } }
        )
        .then(response =>{
          if(response.data.code == "0000")
          {
            let list=response.data.data;
            for (let i = 0; i < list.length; i++) {
              if (list[i].name == "ACCESS_LOCATION") {
               store.commit("SET_ACCESS_LOCATION",true);
              }
              else if(list[i].name == "ACCESS_EMPLOYEE")
              {
                store.commit("SET_ACCESS_EMPLOYEE",true);
              }
              else if(list[i].name == "ACCESS_USER_MANAGE")
              {
                store.commit("SET_ACCESS_USER_MANAGE",true);
              }
              else if(list[i].name == "ACCESS_ROLE_MANAGEMENT")
              {
                store.commit("SET_ACCESS_ROLE_MANAGEMENT",true);
              }
              else if(list[i].name == "ACCESS_PERMISSION_ASSIGNING")
              {
                store.commit("SET_ACCESS_PERMISSION_ASSIGNING",true);
              }
               else if(list[i].name == "ACCESS_CREATE_COMPANY")
              {
                store.commit("SET_ACCESS_CREATE_COMPANY",true);
              }
               else if(list[i].name == "ACCESS_USER_LOGS")
              {
                store.commit("SET_ACCESS_USER_LOGS",true);
              }
            }
            store.commit('SET_USER_LOGIN',true);
          }
          else if(response.data.code == "1001")
          {
            next({
              path: '/login',
              query: { redirect: to.fullPath }
            })
          }
        }).catch( ()=>{
          next({
            path: '/login',
            query: { redirect: to.fullPath }
          })
        })
       
        next();
      }
      else
      {
      // next();
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    }
    }
  }
  else{
    next();
  }
});

export default router
