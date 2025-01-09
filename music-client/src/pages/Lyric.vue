<template>
  <div class="song-lyric">
    <h1 class="lyric-title">歌词</h1>
    <ul class="has-lyric" v-if="lyr.length" key="index">
      <li v-for="(item, index) in lyr" :key="index">
        {{ item[1] }}
      </li>
    </ul>

    <!-- 无歌词 -->
    <div v-else class="no-lyric" key="no-lyric">
      <span>纯音乐，请欣赏</span>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
export default {
  mixins: [mixin],
  name: "lyric",
  data() {
    return {
      lyr: [], //歌曲的歌词
    };
  },

  //计算属性 有缓存功能
  computed: {
    ...mapGetters(["curTime", "id", "lyric", "listOfSongs", "listIndex"]),
  },

  //实例创建之后被调用
  created() {
    this.lyr = this.lyric;
  },

  watch: {
    id: function () {
      // console.log("123");
      this.lyr = [];
      this.lyr = this.lyric;
      // console.log(this.lyr);
    },

    curTime() {
      if (this.lyr.length > 0) {
        for (let i = 0; i < this.lyr.length; i++) {
          if (this.curTime >= this.lyr[i][0]) {
            for (let j = 0; j < this.lyr.length; j++) {
              //[j] 表示访问第 j 个 <li> 元素
              document.querySelectorAll(".has-lyric li")[j].style.color =
                "#000";
              document.querySelectorAll(".has-lyric li")[j].style.fontSize =
                "15px";
            }
            for (let k = 0; k < this.lyr.length; k++) {
              if (this.lyr[k][0] == this.lyr[i][0]) {
                document.querySelectorAll(".has-lyric li")[k].style.color =
                  "#01e5ff";
                document.querySelectorAll(".has-lyric li")[k].style.fontSize =
                  "25px";
              }
            }
          }
        }
      }
    },
  },
};
</script>

<style>
@import "../assets/css/lyric.css";
</style>
