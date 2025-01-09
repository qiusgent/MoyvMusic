import Vue from "vue";
import Vuex from "vuex";
Vue.use(Vuex);

// 缓存数据
const store = new Vuex.Store({
  state: {
    HOST: "http://127.0.0.1:8888",
    isplay: false, //是否播放中
    url: "", //歌曲地址
    id: "", //歌曲id
    toggle: false,
  },
  // 获取器
  getters: {
    isplay: (state) => state.isplay,
    url: (state) => state.url,
    id: (state) => state.id,
    toggle: (state) => state.toggle,
  },
  // 变更函数
  //用来修改状态的函数，它们接收当前状态作为第一个参数，需要修改的新值作为第二个参数
  mutations: {
    setIsPlay: (state, isplay) => {
      state.isplay = isplay;
    },
    setUrl: (state, url) => {
      state.url = url;
    },
    setId: (state, id) => {
      state.id = id;
    },
    updateToggle: (state, toggle) => {
      state.toggle = toggle;
    },
  },
});

// 导出
export default store;
