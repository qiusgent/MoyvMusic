import Vue from "vue";
import Vuex from "vuex";

import configure from "./configure";
import user from "./user";
import song from "./song";
Vue.use(Vuex);

// 缓存数据
const store = new Vuex.Store({
  // modules模块 用于将store分割成更小的模块的选项
  modules: {
    configure,
    user,
    song,
  },
});

// 导出
export default store;
