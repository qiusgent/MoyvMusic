<template>
  <div class="singer">
    <ul class="singer-header">
      <li
        class="singerli"
        v-for="(item, index) in singerStyle"
        :key="index"
        :class="{ active: item.name === activeName }"
        @click="handleChangeView(item)"
      >
        {{ item.name }}
      </li>
    </ul>
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
import { getAllSinger, getSingerOfSex } from "../api/index";
import { mixin } from "../mixins";
import { singerStyle } from "../assets/data/singer";

export default {
  name: "singer",
  mixins: [mixin],
  components: { ContentList },
  data() {
    return {
      SongListDatas: [], //歌手数据
      pageSize: 15, //页面歌单数
      currentPage: 1, //当前页
      singerStyle: [], //类型
      activeName: "全部歌手",
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
    this.singerStyle = singerStyle;
    this.getSingerList();
  },

  methods: {
    getSingerList() {
      getAllSinger().then((res) => {
        this.SongListDatas = res;
      });
    },

    // 获取当前页
    handleCurrentChange(val) {
      this.currentPage = val;
    },

    //根据Style显示对应的歌手
    handleChangeView(item) {
      this.activeName = item.name;
      this.SongListDatas = [];
      if (item.name === "全部歌手") {
        this.getSingerList();
      } else {
        this.getSingerOfSex(item.type);
        console.log(item.type);
      }
    },

    // 通过性别对歌手分类
    getSingerOfSex(sex) {
      getSingerOfSex(sex)
        .then((res) => {
          this.currentPage = 1;
          this.SongListDatas = res;
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

<style>
@import "../assets/css/singer.css";
</style>
