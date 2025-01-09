<template>
  <div class="search-song-list">
    <content-list :contentList="albumDatas"></content-list>
  </div>
</template>

<script>
import ContentList from "../ContentList.vue";
import { getSongListOfLikeTitle } from "../../api/index";
import { mixin } from "../../mixins";
import { mapGetters } from "vuex";
export default {
  name: "search-song-list",
  mixins: [mixin],
  components: { ContentList },
  data() {
    return {};
  },

  //计算
  computed: {
    ...mapGetters(["albumDatas"]),
  },

  //安装 准备
  mounted() {
    // this.getSearchList();
    this.$store.commit("setToggle", "SongLists");
  },

  methods: {
    getSearchList() {
      // console.log("歌单：" + this.$route.query.keywords);
      if (!this.$route.query.keywords) {
        this.$store.commit("setAlbumDatas", []);
        this.$notify({
          title: "您输入的内容为空",
          message: "",
          type: "error",
        });
      } else {
        getSongListOfLikeTitle(this.$route.query.keywords).then((res) => {
          if (res) {
            this.$store.commit("setAlbumDatas", res);
          } else {
            this.$store.commit("setAlbumDatas", []);
            this.$notify({
              title: "该歌单暂无内容",
              message: "",
              type: "error",
            });
          }
        });
      }
    },
  },
};
</script>

<style>
@import "../../assets/css/search-song-list.css";
</style>
