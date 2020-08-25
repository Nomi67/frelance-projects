import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    barColor: 'rgba(0, 0, 0, .8), rgba(0, 0, 0, .8)',
    barImage: 'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-1.jpg',
    drawer: null,
    role:'',
    // adminRoleLogin:false,
    userLogin:false,
    roleAdmin:false,
    roleManager:false,
    designation:'',
    username:'',
    firstName:'',
    lastName:'',
    ACCESS_LOCATION:false,
    ACCESS_EMPLOYEE:false,
    ACCESS_USER_MANAGE:false,
    ACCESS_ROLE_MANAGEMENT:false,
    ACCESS_PERMISSION_ASSIGNING:false,
    ACCESS_CREATE_COMPANY:false,
    ACCESS_USER_LOGS:false,

  },
  mutations: {
    SET_DESIGNATION(state, payload){
      state.designation= payload;
    },
    SET_USERNAME(state, payload){
      state.username= payload;
    },
    SET_FIRSTNAME(state, payload){
      state.firstName= payload;
    },
    SET_LASTNAME(state, payload){
      state.lastName= payload;
    },
    SET_ACCESS_USER_LOGS (state, payload) {
      state.ACCESS_USER_LOGS = payload
    },
    SET_ACCESS_CREATE_COMPANY (state, payload) {
      state.ACCESS_CREATE_COMPANY = payload
    },
    SET_ACCESS_PERMISSION_ASSIGNING (state, payload) {
      state.ACCESS_PERMISSION_ASSIGNING = payload
    },
    SET_ACCESS_ROLE_MANAGEMENT (state, payload) {
      state.ACCESS_ROLE_MANAGEMENT = payload
    },
    SET_ACCESS_USER_MANAGE (state, payload) {
      state.ACCESS_USER_MANAGE = payload
    },
    SET_ACCESS_LOCATION (state, payload) {
      state.ACCESS_LOCATION = payload
    },
    SET_ACCESS_EMPLOYEE (state, payload) {
      state.ACCESS_EMPLOYEE = payload
    },
    SET_BAR_IMAGE (state, payload) {
      state.barImage = payload
    },
    SET_DRAWER (state, payload) {
      state.drawer = payload
    },
    // SET_ROLE_ADMIN(state, payload){
    //   state.roleAdmin=payload
    // },
    // SET_ADMIN_LOGIN(state, payload){
    //   state.adminRoleLogin= payload
    // },
    SET_USER_LOGIN(state, payload){
      state.userLogin= payload
    },
    SET_ROLE_ADMIN(state, payload){
      state.roleAdmin= payload
    },
    SET_ROLE_MANAGER(state, payload){
      state.roleManager= payload
    },
    SET_ROLE(state, payload)
    {
      state.role=payload
    },
    USER_LOGOUT(state){
      state.username='';
      state.userLogin=false;
      state.firstName='';
      state.lastName='';
      state.ACCESS_LOCATION=false;
      state.ACCESS_EMPLOYEE=false;
      state.ACCESS_USER_MANAGE=false;
      state.ACCESS_ROLE_MANAGEMENT=false;
      state.ACCESS_PERMISSION_ASSIGNING=false;
      state.ACCESS_CREATE_COMPANY=false;
      state.ACCESS_USER_LOGS=false;
      state.roleAdmin=false;
      state.roleManager=false;
    }
    // SET_MAMAGER_ROLE(state, payload)
    // {
    //   state.roleManager=payload;
    // }
    
  },
  actions: {

  },
})
