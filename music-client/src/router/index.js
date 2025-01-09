import VueRouter from "vue-router";

const routes = [
  {
    path: "/",
    name: "home",
    component: () => import("../pages/Home.vue"),
  },
  {
    path: "/song-list",
    name: "song-list",
    component: () => import("../pages/SongList.vue"),
  },
  {
    path: "/singer",
    name: "singer",
    component: () => import("../pages/Singer.vue"),
  },
  {
    path: "/my-music",
    name: "my-music",
    component: () => import("../pages/MyMusic.vue"),
  },
  {
    path: "/search",
    name: "search",
    component: () => import("../pages/Search"),
  },
  {
    path: "/lyric",
    name: "lyric",
    component: () => import("../pages/Lyric.vue"),
  },
  {
    path: "/sign-up",
    name: "sign-up",
    component: () => import("../pages/SignUp.vue"),
  },
  {
    path: "/login-in",
    name: "login-in",
    component: () => import("../pages/LoginIn.vue"),
  },
  {
    path: "/setting",
    name: "setting",
    component: () => import("../pages/Setting.vue"),
  },
  {
    path: "/singer-album/:id",
    name: "singer-album",
    component: () => import("../pages/singerAlbum.vue"),
  },
  {
    path: "/song-list-album/:id",
    name: "song-list-album",
    component: () => import("../pages/SongListAlbum.vue"),
  },
];

const router = new VueRouter({
  mode: "history" /*去掉路径中的#号*/,
  routes,
});

export default router;
