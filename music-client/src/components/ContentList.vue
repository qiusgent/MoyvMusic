<template>
  <div class="content-list">
    <ul class="section-content">
      <li
        class="content-item"
        v-for="(item, index) in contentList"
        :key="index"
      >
        <div class="kuo">
          <img class="item-img" :src="attachImageUrl(item.pic)" />
        </div>
        <div class="play" @click="goAlbum(item, item.name)">
          <svg
            t="1729503550633"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4162"
            data-spm-anchor-id="a313x.search_index.0.i13.55b53a81xbnsD6"
            width="48"
            height="48"
          >
            <path
              d="M231.253333 929.28a25.173333 25.173333 0 0 1-25.173333-25.173333V119.893333a25.173333 25.173333 0 0 1 39.68-20.48l561.066667 392.106667a25.173333 25.173333 0 0 1 0 42.666667L245.76 924.586667a25.173333 25.173333 0 0 1-14.506667 4.693333z"
              fill="#fff"
              p-id="4163"
              data-spm-anchor-id="a313x.search_index.0.i14.55b53a81xbnsD6"
              class="selected"
            ></path>
          </svg>
        </div>

        <p class="item-name">{{ item.name || item.title }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
export default {
  name: "content-list",
  props: ["contentList"], //props(道具):组件传值
  mixins: [mixin],

  methods: {
    goAlbum(item, type) {
      this.$store.commit("setTempList", item);
      if (type) {
        //歌手
        this.$router.push({ path: `singer-album/${item.id}` });
      } else {
        //歌单
        this.$router.push({ path: `song-list-album/${item.id}` });
      }
    },
  },
};
</script>

<style>
@import "../assets/css/content-list.css";
</style>
