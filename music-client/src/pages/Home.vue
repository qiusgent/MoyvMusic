<template>
  <div class="home">
    <swiper />

    <div class="section" v-for="(item, index) in songList" :key="index">
      <div class="section-title">
        {{ item.name }}
      </div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper.vue";
import contentList from "../components/ContentList.vue";
import { getAllSinger, getAllSongList } from "../api/index";

export default {
  name: "home",
  //   导出的组件
  components: { Swiper, contentList },
  data() {
    return {
      songList: [
        { name: "歌单", list: [] },
        { name: "歌手", list: [] },
      ],
    };
  },

  created() {
    this.getSongList();
    this.getSinger();
  },

  methods: {
    // 获取歌单
    getSongList() {
      getAllSongList()
        .then((res) => {
          // slice部分 (] : 0-9的数据
          this.songList[0].list = res.slice(0, 10);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 获取歌手
    getSinger() {
      getAllSinger()
        .then((res) => {
          // slice部分 (] : 0-9的数据
          this.songList[1].list = res.slice(0, 10);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<!-- lang="css": 这表示该样式表使用 CSS 语言 -->
<!-- scoped(仔细看) 这个属性表示样式是“局部的”，即只应用于当前组件的元素，而不会影响全局或其他组件中的元素-->
<style lang="css" scoped>
@import "../assets/css/home.css";
</style>
