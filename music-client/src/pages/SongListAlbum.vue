<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src="attachImageUrl(tempList.pic)" />
      </div>
      <div class="album-info">
        <h3>简介：</h3>
        <span>{{ tempList.introduction }}</span>
      </div>
    </div>
    <div class="song-list">
      <div class="album-title">
        <p>{{ tempList.title }}</p>
      </div>
      <div class="album-score">
        <div>
          <h3>歌单评分</h3>
          <div>
            <!-- Rate(率) 评分  disabled(有残疾的) 失效-->
            <el-rate
              v-model="average"
              disabled
              disabled-void-color="#C0C4CC"
            ></el-rate>
          </div>
        </div>
        <span>{{ average * 2 }}</span>
        <div>
          <h3>评分:</h3>
          <div @click="setRank">
            <!-- Rate(率) 评分  allow-half 是否半选 show-text 是否显示辅助文字-->
            <el-rate v-model="rank" allow-half show-text></el-rate>
          </div>
        </div>
      </div>

      <!-- 歌单 -->
      <div class="songs-body">
        <!-- album页面 里的props获取 -->
        <album-content :songList="listOfSongs"></album-content>
        <comment :playId="songListId" :type="1"></comment>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
import Comment from "../components/Comment.vue";
import {
  ListSongDetail,
  songOfSongId,
  setRank,
  getAverage,
} from "../api/index";
import AlbumContent from "../components/AlbumContent.vue";
export default {
  mixins: [mixin],
  name: "song-list-album",
  // components组成部分
  components: { AlbumContent, Comment },
  data() {
    return {
      songList: [], //歌曲列表
      songListId: "", //歌单id
      average: 0, //平均分
      rank: 0, //提交评价的分数
    };
  },
  computed: {
    ...mapGetters(["listOfSongs", "tempList", "loginIn", "userId"]),
  },

  created() {
    this.songListId = this.$route.params.id;
    // console.log(this.songListId);
    this.getSongId();
    this.getRank(this.songListId);
  },

  methods: {
    //获取当前歌单的歌曲列表
    getSongId() {
      ListSongDetail(this.songListId)
        .then((res) => {
          // for (let item of res) {
          //   this.getSongList(item.songId);
          // }
          // this.$store.commit("setlistOfSongs", this.songList);

          //使用 map 方法来创建一个包含所有异步请求的数组。
          //使用 Promise.all 方法来等待所有异步请求完成
          //解决刷新时数据排序不规定
          const promises = res.map((item) => songOfSongId(item.songId));
          Promise.all(promises).then((results) => {
            this.$store.commit("setlistOfSongs", results);
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //根据歌曲id获取歌曲信息
    // getSongList(id) {
    //   songOfSongId(id)
    //     .then((res) => {
    //       this.songList.push(res);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },

    //获取歌单评分
    getRank(id) {
      getAverage(id)
        .then((res) => {
          // 半颗心为一分
          this.average = res / 2;
        })
        .catch((err) => {
          console.log(err);
        });
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
@import "../assets/css/song-list-album.css";
</style>
