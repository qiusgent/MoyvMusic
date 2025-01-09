<template>
  <div>
    <the-header></the-header>
    <the-aside></the-aside>
    <!-- collapse 为true时添加 content-collapse-->
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <!-- 路由 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TheHeader from "./TheHeader.vue";
import TheAside from "./TheAside.vue";
import bus from "../assets/js/bus";
export default {
  // 导出的组件
  components: { TheHeader, TheAside },

  data() {
    return {
      collapse: false,
    };
  },
  created() {
    // bus进行组件间的通信，折叠侧边栏
    // 接收
    bus.$on("collapse", (msg) => {
      this.collapse = msg;
    });
  },
};
</script>

<style></style>
