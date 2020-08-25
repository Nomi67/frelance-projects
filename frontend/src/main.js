import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueCryptojs from 'vue-cryptojs'


Vue.config.productionTip = false
Vue.use(VueCryptojs)
Vue.prototype.$server="http://localhost:8090"
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
