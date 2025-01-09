<template>
  <div class="song-list">
    <content-list :contentList="data"></content-list>
    <!-- Pagination 分页 -->
    <!-- current-change currentPage改变时触发 -->
    <!-- layout布局 显示的内容 -->
    <div class="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        background
        layout="total,prev,pager,next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="SongListDatas.length"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import ContentList from "../components/ContentList.vue";
import { getAllSongList } from "../api/index";
import { mixin } from "../mixins";

export default {
  name: "Song-list",
  mixins: [mixin],
  components: { ContentList },
  data() {
    return {
      SongListDatas: [], //歌单数据
      pageSize: 15, //页面歌单数
      currentPage: 1, //当前页
    };
  },

  computed: {
    //计算表中的数据
    data() {
      return this.SongListDatas.slice(
        (this.currentPage - 1) * this.pageSize,
        this.pageSize * this.currentPage
      );
    },
  },

  //安装 准备
  mounted() {
    this.getSongList();
  },

  methods: {
    getSongList() {
      getAllSongList().then((res) => {
        this.SongListDatas = res;
      });
    },

    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val;
    },
  },
};
</script>

<style>
@import "../assets/css/song-list.css";
</style>
