<template>
  <div id="app">
    <the-header />
    <router-view />
    <SongAudio></SongAudio>
    <!-- <the-aside></the-aside> -->
    <play-bar></play-bar>
    <scroll-top></scroll-top>
    <the-footer></the-footer>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import TheHeader from "./components/TheHeader.vue";
import scrollTop from "./components/ScrollTop.vue";
import TheFooter from "./components/TheFooter.vue";
import SongAudio from "./components/SongAudio.vue";
import PlayBar from "./components/PlayBar.vue";
// import TheAside from "./components/TheAside.vue";
export default {
  name: "App",
  components: {
    TheHeader,
    scrollTop,
    TheFooter,
    SongAudio,
    PlayBar,
  },
};
</script>

<style>
/* #app {
  margin: 0;
  padding: 0;
} */
</style>
