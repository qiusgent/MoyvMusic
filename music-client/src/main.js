import Vue from "vue";
import App from "./App.vue";

import Vuex from "vuex";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import "./assets/css/index.css";

import router from "./router";
import VueRouter from "vue-router";
import store from "./store/index";
// import "./assets/icons/index.js";

Vue.use(Vuex);
Vue.use(VueRouter);
Vue.use(ElementUI);

Vue.config.productionTip = false;

// Vue.js 应用程序的实例化过程
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
