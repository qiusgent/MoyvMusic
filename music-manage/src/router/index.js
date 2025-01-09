// import Vue from "vue";
import VueRouter from "vue-router";

// Vue.use(Router);

const routes = [
  {
    path: "/",
    name: "login",
    component: () => import("../pages/Login"),
  },
  {
    path: "/Home",
    name: "Home",
    component: () => import("../components/Home.vue"),
    children: [
      {
        path: "/Info",
        name: "Info",
        meta: {
          title: "后台主页",
        },
        component: () => import("../pages/InfoPage.vue"),
      },
      {
        path: "/Consumer",
        name: "Consumer",
        meta: {
          title: "用户管理",
        },
        component: () => import("../pages/ConsumerPage.vue"),
      },
      {
        path: "/Singer",
        name: "Singer",
        meta: {
          title: "歌手管理",
        },
        component: () => import("../pages/SingerPage.vue"),
      },
      {
        path: "/SongList",
        name: "SongList",
        meta: {
          title: "歌单管理",
        },
        component: () => import("../pages/SongListPage.vue"),
      },

      {
        path: "/Song",
        name: "Song",
        meta: {
          title: "歌曲管理",
        },
        component: () => import("../pages/SongPage.vue"),
      },

      {
        path: "/ListSong",
        name: "ListSong",
        meta: {
          title: "歌单管理",
        },
        component: () => import("../pages/ListSongPage.vue"),
      },
      {
        path: "/Comment",
        name: "Comment",
        meta: {
          title: "评论",
        },
        component: () => import("../pages/CommentPage.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history" /*去掉路径中的#号*/,
  routes,
});

export default router;
