<template>
  <div class="search-songs">
    <!-- album页面 里的props获取 -->
    <album-content :songList="listOfSongs"></album-content>
  </div>
</template>

<script>
import AlbumContent from "../AlbumContent.vue";
import { mixin } from "../../mixins";
import { mapGetters } from "vuex";
export default {
  components: { AlbumContent },
  mixins: [mixin],
  name: "search-song",
  //计算
  computed: {
    ...mapGetters(["listOfSongs"]),
  },

  //mounted 安装：页面加载时
  mounted() {
    // this.getSong();
    this.$store.commit("setToggle", "Songs");
  },
};
</script>

<style>
@import "../../assets/css/search-songs.css";
</style>
