// 缓存数据 configure配置
const configure = {
  state: {
    HOST: "http://127.0.0.1:8888",
    activeName: "", //当前选择的菜单
    showAside: false, //歌曲播放列表

    loginIn: false, //用户是否已经登录
    albumDatas: [], //歌单
    isActive: false, //歌曲收藏
  },
  // 获取器
  getters: {
    activeName: (state) => {
      let activeName = state.activeName;
      if (!activeName) {
        //parse（分析） json分析
        activeName = JSON.parse(window.sessionStorage.getItem("activeName"));
      }
      return activeName;
    },
    showAside: (state) => {
      let showAside = state.showAside;
      if (!showAside) {
        //parse（分析） json分析
        showAside = JSON.parse(window.sessionStorage.getItem("showAside"));
      }
      return showAside;
    },

    loginIn: (state) => {
      let loginIn = state.loginIn;
      if (!loginIn) {
        //parse（分析） json分析
        loginIn = JSON.parse(window.sessionStorage.getItem("loginIn"));
      }
      return loginIn;
    },

    albumDatas: (state) => {
      let albumDatas = state.albumDatas;
      if (!albumDatas) {
        //parse（分析） json分析
        albumDatas = JSON.parse(window.sessionStorage.getItem("albumDatas"));
      }
      return albumDatas;
    },

    isActive: (state) => {
      let isActive = state.isActive;
      if (!isActive) {
        //parse（分析） json分析
        isActive = JSON.parse(window.sessionStorage.getItem("isActive"));
      }
      return isActive;
    },
  },
  // 变更函数
  //用来修改状态的函数，它们接收当前状态作为第一个参数，需要修改的新值作为第二个参数
  mutations: {
    setActiveName: (state, activeName) => {
      state.activeName = activeName;
      //   sessionStorage(会话 存储)  转换为json格式 stringify（串化）
      window.sessionStorage.setItem("activeName", JSON.stringify(activeName));
    },

    setShowAside: (state, showAside) => {
      state.showAside = showAside;
      window.sessionStorage.setItem("showAside", JSON.stringify(showAside));
    },

    setLoginIn: (state, loginIn) => {
      state.loginIn = loginIn;
      window.sessionStorage.setItem("loginIn", JSON.stringify(loginIn));
    },

    setAlbumDatas: (state, albumDatas) => {
      state.albumDatas = albumDatas;
      window.sessionStorage.setItem("albumDatas", JSON.stringify(albumDatas));
    },

    setIsActive: (state, isActive) => {
      state.isActive = isActive;
      window.sessionStorage.setItem("isActive", JSON.stringify(isActive));
    },
  },
};

// 导出
export default configure;
