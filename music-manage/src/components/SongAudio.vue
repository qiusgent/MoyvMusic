<template>
  <div class="song-audio">
    <!-- controls 显示音频控件（播放、暂停、音量等） -->
    <!-- 
        reload(预载)提示浏览器在页面加载时如何处理音频文件
        auto：浏览器选择最佳方式预加载音频文件。 
    -->

    <audio
      id="player"
      :src="url"
      controls
      preload="true"
      @canplay="startPlay"
      @ended="end"
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
    ...mapGetters(["id", "url", "isplay", "toggle"]),
  },

  watch: {
    //监听是播放还是暂停
    isplay: function () {
      this.togglePlay();
    },
  },

  methods: {
    // 获取链接后准备播放
    startPlay() {
      let player = document.querySelector("#player");
      // addEventListener(监听器)
      //  canplaythrough 事件表示音频文件已经完全加载完成，可以播放整个音频文件
      player.addEventListener("canplaythrough", () => {
        player.play().catch((error) => {
          console.error("播放错误:", error);
          alert("无法播放音频，可能是格式不受支持或音频未加载。");
        });
      });
    },
    // 播放完成后触发
    end() {
      // let player = document.querySelector("#player");
      // player.pause();
      this.$store.commit("setIsPlay", false);
      this.$store.commit("updateToggle", false);
      // console.log(this.isplay);
    },

    //开始，暂停
    togglePlay() {
      let player = document.querySelector("#player");
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
