<template>
  <div class="singer-album">
    <div class="album-slide">
      <div class="singer-img">
        <img :src="attachImageUrl(singer.pic)" />
      </div>
      <ul class="info">
        <li>{{ attach(singer.sex) }}</li>
        <li>生日：{{ attachBirth(singer.birth) }}</li>
        <li>国籍：{{ singer.location }}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="intro">
        <p class="singerName">{{ singer.name }}</p>

        <p class="Jianjie">{{ singer.introduction }}</p>
      </div>

      <div class="content">
        <!-- album页面 里的props获取 -->
        <album-content :songList="listOfSongs"></album-content>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
import { setRank, singerOfSingerId } from "../api/index";
import AlbumContent from "../components/AlbumContent.vue";
export default {
  mixins: [mixin],
  name: "singer-album",
  components: { AlbumContent },
  data() {
    return {
      singerId: "", //歌手id
      singer: {}, //当前歌手信息
    };
  },
  computed: {
    ...mapGetters(["listOfSongs", "tempList", "loginIn", "userId"]),
  },

  created() {
    this.singerId = this.$route.params.id;
    this.singer = this.tempList;
    this.getsingerOfSingerId();
  },

  methods: {
    //获取当前歌手 查询歌曲列表
    getsingerOfSingerId() {
      singerOfSingerId(this.singerId)
        .then((res) => {
          this.$store.commit("setlistOfSongs", res);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //获取性别
    attach(val) {
      if (val == 0) {
        return "女";
      } else if (val == 1) {
        return "男";
      } else {
        return "组合";
      }
    },

    //获取生日
    attachBirth(val) {
      //substr 提取子字符串
      return val.substr(0, 10);
    },

    //提交评分
    setRank() {
      if (this.loginIn) {
        let params = new URLSearchParams();
        params.append("songListId", this.songListId);
        params.append("consumerId", this.userId);
        params.append("score", this.rank * 2);
        setRank(params)
          .then((res) => {
            if (res.code == 1) {
              this.$notify({
                title: "评分成功",
                message: "",
                type: "success",
              });
              this.getRank(this.songListId);
            } else {
              this.$notify({
                title: "评分失败",
                message: "",
                type: "error",
              });
            }
          })
          .catch((err) => {
            this.$notify({
              title: "您已经评价过啦",
              message: "",
              type: "error",
            });
          });
      } else {
        this.rank = null;
        alert("请先登录");
      }
    },
  },
};
</script>

<style>
@import "../assets/css/singer-album.css";
</style>
