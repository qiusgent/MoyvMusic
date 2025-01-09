const user = {
  state: {
    userId: "", //用户id
    username: "", //用户账号
    avator: "", //用户头像
  },
  // 获取器
  getters: {
    userId: (state) => {
      let userId = state.userId;
      if (!userId) {
        //parse（分析） json分析
        userId = JSON.parse(window.sessionStorage.getItem("userId"));
      }
      return userId;
    },
    username: (state) => {
      let username = state.username;
      if (!username) {
        //parse（分析） json分析
        username = JSON.parse(window.sessionStorage.getItem("username"));
      }
      return username;
    },

    avator: (state) => {
      let avator = state.avator;
      if (!avator) {
        //parse（分析） json分析
        avator = JSON.parse(window.sessionStorage.getItem("avator"));
      }
      return avator;
    },
  },
  // 变更函数
  //用来修改状态的函数，它们接收当前状态作为第一个参数，需要修改的新值作为第二个参数
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId;
      //   sessionStorage(会话 存储)  转换为json格式 stringify（串化）
      window.sessionStorage.setItem("userId", JSON.stringify(userId));
    },

    setUsername: (state, username) => {
      state.username = username;
      window.sessionStorage.setItem("username", JSON.stringify(username));
    },

    setAvator: (state, avator) => {
      state.avator = avator;
      window.sessionStorage.setItem("avator", JSON.stringify(avator));
    },
  },
};

// 导出
export default user;
