<template>
  <div class="search">
    <!-- nav 导航部分       ref调用：直接访问该元素或组件-->
    <nav class="searchList-nav" ref="change">
      <span
        @click="handleChangeView('Songs')"
        :class="{ isActive: toggle1 == 'Songs' }"
        >歌曲</span
      >
      <span
        @click="handleChangeView('SongLists')"
        :class="{ isActive: toggle1 == 'SongLists' }"
        >歌单</span
      >
    </nav>
    <!--component组成部分 is：切换页面  -->
    <component :is="currentView"> </component>
  </div>
</template>

<script>
import SearchSongs from "../components/search/SearchSong.vue";
import SearchSongLists from "../components/search/SearchSongList.vue";
import { mapGetters } from "vuex";
export default {
  name: "search",
  components: {
    SearchSongs,
    SearchSongLists,
  },
  data() {
    return {
      toggle1: "Songs",
      currentView: "SearchSongs",
    };
  },

  created() {
    this.$store.commit("setToggle", this.toggle1);
    // console.log(this.toggle1);
  },

  computed: {
    //  ...mapGetters([])，可以将指定的 getters 映射到当前组件中。
    ...mapGetters(["toggle"]),
  },
  methods: {
    // 切换组件
    handleChangeView(component) {
      this.currentView = "Search" + component;
      this.toggle1 = component;
      this.$store.commit("setToggle", component);
    },
  },
};
</script>

<style>
@import "../assets/css/search.css";
</style>
