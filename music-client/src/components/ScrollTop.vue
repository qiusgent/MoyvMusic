<template>
  <div class="scroll-top" @click="returnTop">
    <div class="box-in"></div>
  </div>
</template>

<script>
export default {
  name: "scroll-top",
  methods: {
    returnTop() {
      // 网页上 到顶部
      document.documentElement.scrollTop = document.body.scrollTop = 0;
    },
  },
};
</script>

<style>
@import "../assets/css/scroll-top.css";
</style>
