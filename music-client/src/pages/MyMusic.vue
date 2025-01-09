<template>
  <div class="my-music">
    <div class="album-slide1">
      <div class="album-img1">
        <img :src="attachImageUrl(avator)" />
      </div>
      <ul class="album-info1">
        <li>昵称：{{ userName }}</li>
        <li>性别：{{ userSex }}</li>
        <li>生日：{{ birth }}</li>
        <li>地区：{{ location }}</li>
      </ul>
    </div>
    <div class="album-content1">
      <div class="songs-body1">
        <div class="album-title1">个性签名：{{ introduction }}</div>
        <div class="content">
          <album-content :songList="collectList"></album-content>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
import { getUserOfId, getCollectOfUserId, songOfSongId } from "../api/index";
import AlbumContent from "../components/AlbumContent.vue";
export default {
  name: "my-music",
  mixins: [mixin],
  components: { AlbumContent },
  data() {
    return {
      avator: "", //用户头像
      userName: "", //用户名
      userSex: "", //用户性别
      birth: "", //生日
      location: "", //用户地区
      introduction: "", //用户签名
      collection: [], //用户收藏列表
      collectList: [], //收藏的歌曲列表（带歌曲）
    };
  },
  computed: {
    ...mapGetters(["listOfSongs", "userId", "isActive"]),
  },

  mounted() {
    this.getMsg(this.userId);
    this.getCollection(this.userId);
  },

  watch: {
    isActive() {
      if (this.isActive == false) {
        this.collectList = [];
        this.getCollection(this.userId);
      }
    },
  },

  methods: {
    getMsg(userId) {
      getUserOfId(userId)
        .then((res) => {
          this.avator = res.avator;
          this.userName = res.username;
          if (res.sex == 0) {
            this.userSex = "女";
          } else if (res.sex == 1) {
            this.userSex = "男";
          }
          this.birth = res.birth.substr(0, 10);
          this.location = res.location;
          this.introduction = res.introduction;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //获取收藏列表
    getCollection(userId) {
      getCollectOfUserId(userId)
        .then((res) => {
          this.collection = res;
          //通过歌曲id查询
          for (let item of this.collection) {
            this.getSongId(item.songId);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //通过歌曲id查询歌曲
    getSongId(id) {
      songOfSongId(id)
        .then((res) => {
          this.collectList.push(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
@import "../assets/css/my-music.css";
</style>
