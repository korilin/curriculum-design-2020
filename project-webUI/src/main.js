import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ViewUI from 'view-design'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'view-design/dist/styles/iview.css'

Vue.config.productionTip = false
Vue.use(ViewUI)
Vue.use(VueAxios,axios)

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
