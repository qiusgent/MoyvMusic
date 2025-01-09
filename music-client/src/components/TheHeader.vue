<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <div class="icon">
        <i class="el-icon-headset"></i>
      </div>
      <span style="font-family: STKaiti">摸鱼音乐</span>
    </div>

    <ul class="navbar">
      <!--active: (判断后面的布尔值)  -->
      <li
        :class="{ active: item.name == activeName }"
        v-for="item in navMsg"
        :key="item.path"
        @click="goPage(item.path, item.name)"
      >
        {{ item.name }}
      </li>
      <li>
        <div class="header-search">
          <!--keyup.enter 回车 -->
          <input
            type="text"
            placeholder="搜索音乐"
            @keyup.enter="goSeach()"
            v-model="keywords"
          />
          <div class="search-btn" @click="goSeach()">
            <svg
              t="1729561805595"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="4272"
              width="20"
              height="20"
            >
              <path
                d="M766 673l-77 78 230 226 77-79zM423.5 790C210.594 790 38 617.406 38 404.5S210.594 19 423.5 19 809 191.594 809 404.5 636.406 790 423.5 790z m1.5-111c152.43 0 276-123.57 276-276S577.43 127 425 127 149 250.57 149 403s123.57 276 276 276z"
                fill="#fff"
                p-id="4273"
              ></path>
            </svg>
          </div>
        </div>
      </li>
      <!-- 右侧导航栏 -->
      <li
        v-show="!loginIn"
        :class="{ active: item.name == activeName }"
        v-for="item in loginMsg"
        :key="item.path"
        @click="goPage(item.path, item.name)"
      >
        {{ item.name }}
      </li>
    </ul>
    <!-- 头像 -->
    <div class="header-right" v-show="loginIn">
      <div id="user">
        <img :src="attachImageUrl(avator)" />
      </div>
      <ul class="menu">
        <li
          v-for="(item, index) in menuList"
          :key="index"
          @click="goMenuList(item.path)"
        >
          {{ item.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
// import { navMsg } from "../assets/data/header";
//获取其他js的数据 这里时store里的
import { mapGetters } from "vuex";
import { likesongOfSongName, getSongListOfLikeTitle } from "../api/index";
import { menuList } from "../assets/data/header";
export default {
  name: "the-header",
  data() {
    return {
      navMsg: [
        { name: "首页", path: "/" },
        { name: "歌单", path: "/song-list" },
        { name: "歌手", path: "/singer" },
        { name: "我的音乐", path: "/my-music" },
      ],
      keywords: "", //搜索关键字

      // 右侧导航栏
      loginMsg: [
        { name: "登录", path: "/login-in" },
        { name: "注册", path: "/sign-up" },
      ],

      menuList: [], //用户下拉菜单
    };
  },

  created() {
    if (!this.activeName) {
      this.$store.commit("setActiveName", "首页");
    }
    this.menuList = menuList;
  },
  mounted() {
    document.querySelector("#user").addEventListener(
      "click",
      function (e) {
        document.querySelector(".menu").classList.add("show");
        //阻止冒泡 Propagation（传播）
        e.stopPropagation();
      },
      false
    );

    //点击menu内部时不会关闭
    // document.querySelector(".menu").addEventListener(
    //   "click",
    //   function (e) {
    //     //阻止冒泡 Propagation（传播）
    //     e.stopPropagation();
    //   },
    //   false
    // );

    //点击外部时关闭
    document.addEventListener(
      "click",
      function (e) {
        document.querySelector(".menu").classList.remove("show");
      },
      false
    );
  },

  //计算:根据相关数据的变化而自动更新
  computed: {
    ...mapGetters([
      "activeName",
      "loginIn",
      "avator",
      "toggle",
      "albumDatas",
      "listOfSongs",
    ]),
  },

  watch: {
    toggle() {
      // console.log(this.toggle);
      this.SouSuo();
    },
  },

  methods: {
    goHome() {
      if (this.$route.path === "/") {
        return; // 如果当前路由与目标路由相同，则不进行导航
      }
      this.$router.push({ path: "/" });
    },

    goPage(path, name) {
      if (this.$route.path === path) {
        return; // 如果当前路由与目标路由相同，则不进行导航
      }
      if (!this.loginIn && path == "/my-music") {
        alert("请先登录");
      } else {
        this.$router.push({ path: path });
        this.$store.commit("setActiveName", name);
      }
    },

    goSeach() {
      // query查询 ：传参数
      if (this.$route.path !== "/search") {
        this.$router.push({
          path: "/search",
          query: { keywords: this.keywords },
        });
      }
      this.SouSuo();
    },

    SouSuo() {
      // console.log(this.keywords);
      if (this.toggle == "Songs") {
        likesongOfSongName(this.keywords)
          .then((res) => {
            if (!res.length) {
              this.$store.commit("setlistOfSongs", []);
              console.log(this.listOfSongs);
              this.$notify({
                title: "系统暂无符合条件的歌曲",
                message: "",
                type: "error",
              });
            } else {
              // commit 承诺
              this.$store.commit("setlistOfSongs", res);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
      if (this.toggle == "SongLists") {
        getSongListOfLikeTitle(this.keywords).then((res) => {
          if (!res.length) {
            this.$store.commit("setAlbumDatas", []);
            console.log(this.albumDatas);
            this.$notify({
              title: "系统暂无符合条件的歌单",
              message: "",
              type: "error",
            });
          } else {
            this.$store.commit("setAlbumDatas", res);
          }
        });
      }
    },

    //获取图片地址
    attachImageUrl(srcUrl) {
      return (srcUrl = this.$store.state.configure.HOST + srcUrl);
    },

    //下拉菜单
    goMenuList(path) {
      if (path == 0) {
        this.$store.commit("setLoginIn", false);
        this.$store.commit("setIsActive", false);
        if (this.$route.path === "/") {
          //刷新页面
          this.$router.go(0);
          this.$store.commit("setActiveName", "首页");
        } else {
          this.$router.push({ path: "/" });
          this.$store.commit("setActiveName", "首页");
        }
      } else {
        if (this.$route.path === path) {
          return; // 如果当前路由与目标路由相同，则不进行导航
        }
        this.$router.push({ path: path });
      }
    },
  },
};
</script>

<style>
@import "../assets/css/the-header.css";
</style>
