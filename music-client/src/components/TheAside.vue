<template>
  <!-- transition(过度) element组件里的-->
  <transition name="slide-fade">
    <div class="the-aside" v-if="showAside">
      <h2 class="title">播放队列</h2>
      <ul class="menus">
        <li
          v-for="(item, index) in listOfSongs"
          :key="index"
          :class="{ 'is-play': id == item.id }"
          @click="
            toplay(
              item.id,
              item.url,
              item.pic,
              item.index,
              item.name,
              item.lyric
            )
          "
        >
          <span class="item-index">
            {{ index + 1 }}
          </span>
          <span class="item-title">{{ replaceFName(item.name) }}</span>
          <span class="item-name">{{ replaceLName(item.name) }}</span>
          <span class="item-intro">{{ item.introduction }}</span>
        </li>
      </ul>
    </div>
  </transition>
</template>

<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
export default {
  mixins: [mixin],
  name: "the-aside",
  computed: {
    ...mapGetters(["showAside", "listOfSongs", "id"]),
  },

  //加载页面后
  mounted() {
    let _this = this;
    //document里不能直接写this
    // 第三个参数：false 是从该对象往上传
    // true 是从外面往该对象传
    // document.addEventListener(
    //   "click",
    //   function () {
    //     console.log("歌单列表页面：" + _this.showAside);
    //     if (_this.showAside == true) {
    //       _this.$store.commit("setShowAside", false);
    //     }
    //   },
    //   true
    // );
  },

  methods: {
    // 获取名字前半部分
    replaceLName(str) {
      // 字符串用 split拆分 结果是一个数组
      let arr = str.split("-");
      return arr[0];
    },

    // 获取名字后半部分
    replaceFName(str) {
      let arr = str.split("-");
      return arr[1];
    },

    //播放
    toplay: function (id, url, pic, index, name, lyric) {
      if (this.toggle != id) {
        this.$store.commit("setIsPlay", true);
        this.$store.commit("setToggle", id);
        this.$store.commit("setId", id);
        // this.$store.state:是用来访问 Vuex 状态管理库中存储的状态的
        this.$store.commit("setUrl", this.$store.state.configure.HOST + url);
        this.$store.commit("setPicUrl", this.$store.state.configure.HOST + pic);
        this.$store.commit("setListIndex", index);
        this.$store.commit("setTitle", this.replaceFName(name));
        this.$store.commit("setArtist", this.replaceLName(name));
        this.$store.commit("setLyric", this.parseLyric(lyric));
      } else {
        this.$store.commit("setIsPlay", false);
        this.$store.commit("setToggle", false);
      }
    },
  },
};
</script>

<style>
@import "../assets/css/the-aside.css";
</style>
