<template>
  <div class="content">
    <!-- slot插槽 vue里的占位符 name（此插槽的名字） -->
    <!-- <h1 class="title">
      <slot name="title"></slot>
    </h1> -->
    <ul class="alblum-ul">
      <li style="background-color: #f8f8f8" class="alblum-li">
        <div class="song-item">
          <span class="item-index"></span>
          <span class="item-title1">歌曲名</span>
          <span class="item-name">歌手</span>
          <span class="item-intro">专辑</span>
        </div>
      </li>
      <li v-for="(item, index) in songList" :key="index" class="alblum-li">
        <div
          class="song-item"
          @click="
            toplay(item.id, item.url, item.pic, index, item.name, item.lyric)
          "
        >
          <span class="item-index">
            {{ index + 1 }}
          </span>
          <span class="item-title">{{ replaceFName(item.name) }}</span>
          <span class="item-name">{{ replaceLName(item.name) }}</span>
          <span class="item-intro">{{ item.introduction }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
export default {
  // album(专辑,相册) content(内容)
  name: "album-content",
  mixins: [mixin],
  props: ["songList"],
  computed: {
    ...mapGetters(["toggle"]),
  },
};
</script>

<style>
@import "../assets/css/ablum-content.css";
</style>
