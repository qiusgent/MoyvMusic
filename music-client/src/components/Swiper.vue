<template>
  <div class="swiper1">
    <div class="swiper">
      <!-- Carousel 走马灯(轮播图) -->
      <!-- interval间隔	自动切换的时间间隔，单位为毫秒 -->
      <el-carousel :interval="4000" type="card" height="238.9px">
        <!--:key指索引 -->
        <el-carousel-item v-for="(item, index) in swiperList1" :key="index">
          <img :src="item.picImg" />
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import { swiperList } from "../assets/data/swiper.js";
export default {
  name: "swiper",
  data() {
    return {
      swiperList1: [],
    };
  },

  created() {
    this.swiperList1 = swiperList;
  },
};
</script>

<style scoped>
@import "../assets/css/swiper.css";
</style>
