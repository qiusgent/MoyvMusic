import Vue from "vue";
import App from "./App.vue";

import router from "./router/index";
import VueRouter from "vue-router";
import store from "./store/index";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/main.css";
import "./assets/css/global.css";

// 兼容低版本浏览器
import "babel-polyfill";

// 绘制各种类型的图表
import VCharts from "v-charts";

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(VCharts);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
