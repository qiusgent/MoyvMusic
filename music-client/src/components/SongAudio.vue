<template>
  <div class="song-audio">
    <!-- controls 显示音频控件（播放、暂停、音量等） -->
    <!-- 
          preload(预载)提示浏览器在页面加载时如何处理音频文件
          auto：浏览器选择最佳方式预加载音频文件。 
      -->

    <!--  ref:this.$refs.player(调用这个组件) 带:一般作为数据 -->
    <audio
      ref="player"
      :src="url"
      controls
      preload="true"
      @canplay="startPlay"
      @ended="end"
      @timeupdate="timeupdate"
    ></audio>
  </div>
</template>

<script>
import { mapGetters } from "vuex"; //监控
export default {
  name: "SongAudio",
  data() {
    return {};
  },

  //   可以将 Vuex Store 中的 getters 的值作为计算属性在当前组件中使用
  computed: {
    //  ...mapGetters([])，可以将指定的 getters 映射到当前组件中。
    ...mapGetters([
      "id",
      "url",
      "isplay",
      "listOfSongs",
      "curTime",
      "changeTime",
      "autoNext",
      "volume",
    ]),
  },

  watch: {
    //监听是播放还是暂停
    isplay: function () {
      this.togglePlay();
    },

    //跳转到指定的播放时刻
    changeTime() {
      this.$refs.player.currentTime = this.changeTime;
    },

    //改变音量
    volume() {
      // volume属性的值为（0.0 - 1.0）
      this.$refs.player.volume = this.volume / 100;
      // console.log(this.volume);
    },
  },

  methods: {
    // 获取链接后准备播放
    // 表示当音频可以播放时，调用
    startPlay() {
      let player = this.$refs.player;
      this.$store.commit("setDuration1", player.duration);
      this.$refs.player.volume = this.volume / 100;
      // addEventListener(监听器)
      //  canplaythrough 事件表示音频文件已经完全加载完成，可以播放整个音频文件
      setTimeout(() => {
        player.play().catch((error) => {
          console.error("播放错误:", error);
          alert("无法播放音频，可能是格式不受支持或音频未加载。");
        });
      }, 300); // 延迟 1 秒
      this.$store.commit("setIsPlay", true);
    },
    // 播放完成后触发
    end() {
      // let player = document.querySelector("#player");
      // player.pause();
      this.$store.commit("setIsPlay", false);
      // console.log(this.isplay);
      //当前时间
      this.$store.commit("setCurTime", 0);
      //让这个值变化在play-bar里监控
      this.$store.commit("setAutoNext", !this.autoNext);
    },

    //音乐播放时记录音乐的播放位置（例如播放、暂停、拖动进度条等操作）
    timeupdate() {
      this.$store.commit("setCurTime", this.$refs.player.currentTime);
    },

    //开始，暂停
    togglePlay() {
      let player = this.$refs.player;
      if (this.isplay) {
        //延迟加载 以防资源加载不出来报没有资源的错误
        setTimeout(() => {
          player.play().catch((error) => {
            console.error("播放错误:", error);
            alert("无法播放音频，可能是格式不受支持或音频未加载。");
          });
        }, 300); // 延迟 1 秒
      } else {
        player.pause();
      }
    },
  },
};
</script>

<style>
.song-audio {
  /* 设置元素的显示类型 */
  /* display: inline; */
  display: none;
}
</style>
