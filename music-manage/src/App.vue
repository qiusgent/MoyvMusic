<template>
  <div id="app">
    <SongAudio />
    <!-- <img alt="Vue logo" src="./assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/> -->
    <!-- 路由组件 显示路由页面 -->
    <router-view></router-view>
  </div>
</template>

<script>
import SongAudio from "./components/SongAudio.vue";
export default {
  name: "App",
  components: {
    SongAudio,
  },
};
</script>

<style>
/* #app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
} */
</style>
