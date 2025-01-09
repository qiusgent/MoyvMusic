// 缓存数据
const song = {
  state: {
    listOfSongs: [], //当前歌曲列表
    isplay: false, //是否播放中
    url: "", //歌曲地址
    url1: "",
    id: "", //歌曲id
    playButtonUrl: "", //播放状态图标
    toggle: "",

    duration1: 0, //音乐时长
    curTime: 0, //当前播放位置
    changeTime: 0, //指定播放时间
    title: "", //歌名
    artist: "", //歌手名
    picUrl: "", //歌曲图片
    autoNext: true, //自动触发下一首
    lyric: [], //歌词
    tempList: {}, //单个歌单信息/歌手信息
    listIndex: null, //当前歌曲在歌单中的位置
    volume: 50, //音量volume（体积）
  },
  // 获取器
  getters: {
    listOfSongs: (state) => {
      let listOfSongs = state.listOfSongs;
      if (!listOfSongs.length) {
        //parse（分析） json分析
        listOfSongs = JSON.parse(
          window.sessionStorage.getItem("listOfSongs") || null
        );
      }
      return listOfSongs;
    },

    isplay: (state) => {
      let isplay = state.isplay;
      if (!isplay) {
        isplay = JSON.parse(window.sessionStorage.getItem("isplay") || null);
      }
      return isplay;
    },
    url: (state) => {
      let url = state.url;
      if (!url) {
        url = JSON.parse(window.sessionStorage.getItem("url") || null);
      }
      return url;
    },

    url1: (state) => {
      let url1 = state.url1;
      if (!url1) {
        url1 = JSON.parse(window.sessionStorage.getItem(" url1") || null);
      }
      return url1;
    },
    id: (state) => {
      let id = state.id;
      if (!id) {
        id = JSON.parse(window.sessionStorage.getItem("id") || null);
      }
      return id;
    },
    playButtonUrl: (state) => {
      let playButtonUrl = state.playButtonUrl;
      if (!playButtonUrl) {
        playButtonUrl = JSON.parse(
          window.sessionStorage.getItem("playButtonUrl") || null
        );
      }
      return playButtonUrl;
    },

    toggle: (state) => {
      let toggle = state.toggle;
      if (!toggle) {
        toggle = JSON.parse(window.sessionStorage.getItem("toggle") || null);
      }
      return toggle;
    },

    duration1: (state) => {
      let duration1 = state.duration1;
      if (!duration1) {
        duration1 = JSON.parse(
          window.sessionStorage.getItem("duration1") || null
        );
      }
      return duration1;
    },

    curTime: (state) => {
      let curTime = state.curTime;
      if (!curTime) {
        curTime = JSON.parse(window.sessionStorage.getItem("curTime") || null);
      }
      return curTime;
    },

    changeTime: (state) => {
      let changeTime = state.changeTime;
      if (!changeTime) {
        changeTime = JSON.parse(
          window.sessionStorage.getItem("changeTime") || null
        );
      }
      return changeTime;
    },

    title: (state) => {
      let title = state.title;
      if (!title) {
        title = JSON.parse(window.sessionStorage.getItem("title") || null);
      }
      return title;
    },

    artist: (state) => {
      let artist = state.artist;
      if (!artist) {
        artist = JSON.parse(window.sessionStorage.getItem("artist") || null);
      }
      return artist;
    },

    picUrl: (state) => {
      let picUrl = state.picUrl;
      if (!picUrl) {
        picUrl = JSON.parse(window.sessionStorage.getItem("picUrl") || null);
      }
      return picUrl;
    },

    autoNext: (state) => {
      let autoNext = state.autoNext;
      if (!autoNext) {
        autoNext = JSON.parse(
          window.sessionStorage.getItem("autoNext") || null
        );
      }
      return autoNext;
    },

    lyric: (state) => {
      let lyric = state.lyric;
      if (!lyric) {
        lyric = JSON.parse(window.sessionStorage.getItem("lyric") || null);
      }
      return lyric;
    },

    tempList: (state) => {
      let tempList = state.tempList;
      if (!tempList) {
        tempList = JSON.parse(
          window.sessionStorage.getItem("tempList") || null
        );
      }
      return tempList;
    },

    listIndex: (state) => {
      let listIndex = state.listIndex;
      if (!listIndex) {
        listIndex = JSON.parse(
          window.sessionStorage.getItem("listIndex") || null
        );
      }
      return listIndex;
    },

    volume: (state) => {
      let volume = state.volume;
      if (!volume) {
        volume = JSON.parse(window.sessionStorage.getItem("volume") || null);
      }
      return volume;
    },
  },
  // mutations变更函数
  //用来修改状态的函数，它们接收当前状态作为第一个参数，需要修改的新值作为第二个参数
  mutations: {
    setlistOfSongs: (state, listOfSongs) => {
      state.listOfSongs = listOfSongs;
      //   sessionStorage(会话 存储)  转换为json格式 stringify（串化）
      window.sessionStorage.setItem("listOfSongs", JSON.stringify(listOfSongs));
    },

    setIsPlay: (state, isplay) => {
      state.isplay = isplay;
      window.sessionStorage.setItem("isplay", JSON.stringify(isplay));
    },
    setUrl: (state, url) => {
      state.url = url;
      window.sessionStorage.setItem("url", JSON.stringify(url));
    },
    setUrl1: (state, url1) => {
      state.url1 = url1;
      window.sessionStorage.setItem("url1", JSON.stringify(url1));
    },
    setId: (state, id) => {
      state.id = id;
      window.sessionStorage.setItem("id", JSON.stringify(id));
    },
    setplayButtonUrl: (state, playButtonUrl) => {
      state.playButtonUrl = playButtonUrl;
      window.sessionStorage.setItem(
        "playButtonUrl",
        JSON.stringify(playButtonUrl)
      );
    },

    setToggle: (state, toggle) => {
      state.toggle = toggle;
      window.sessionStorage.setItem("toggle", JSON.stringify(toggle));
    },

    setDuration1: (state, duration1) => {
      state.duration1 = duration1;
      window.sessionStorage.setItem("duration1", JSON.stringify(duration1));
    },

    setCurTime: (state, curTime) => {
      state.curTime = curTime;
      window.sessionStorage.setItem("curTime", JSON.stringify(curTime));
    },

    setChangeTime: (state, changeTime) => {
      state.changeTime = changeTime;
      window.sessionStorage.setItem("changeTime", JSON.stringify(changeTime));
    },

    setTitle: (state, title) => {
      state.title = title;
      window.sessionStorage.setItem("title", JSON.stringify(title));
    },

    setArtist: (state, artist) => {
      state.artist = artist;
      window.sessionStorage.setItem("artist", JSON.stringify(artist));
    },

    setPicUrl: (state, picUrl) => {
      state.picUrl = picUrl;
      window.sessionStorage.setItem("picUrl", JSON.stringify(picUrl));
    },

    setAutoNext: (state, autoNext) => {
      state.autoNext = autoNext;
      window.sessionStorage.setItem("autoNext", JSON.stringify(autoNext));
    },

    setLyric: (state, lyric) => {
      state.lyric = lyric;
      window.sessionStorage.setItem("lyric", JSON.stringify(lyric));
    },

    setTempList: (state, tempList) => {
      state.tempList = tempList;
      window.sessionStorage.setItem("tempList", JSON.stringify(tempList));
    },

    setListIndex: (state, listIndex) => {
      state.listIndex = listIndex;
      window.sessionStorage.setItem("listIndex", JSON.stringify(listIndex));
    },

    setVolume: (state, volume) => {
      state.volume = volume;
      window.sessionStorage.setItem("volume", JSON.stringify(volume));
    },
  },
};

// 导出
export default song;
