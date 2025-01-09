<template>
  <div class="info">
    <!--gutter(排水沟)间距 span(一共24)：这里占1/4  -->
    <el-row :gutter="20" class="mgb20" style="margin-left: 0; margin-right: 0">
      <el-col :span="6" style="padding-left: 0">
        <!-- card卡片 -->
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ consumerCount }}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <!-- card卡片 -->
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ songCount }}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <!-- card卡片 -->
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ singerCount }}</div>
              <div>歌手数量</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6" style="padding-right: 0">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{ songListCount }}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row class="mgb20" :gutter="20" style="margin-left: 0; margin-right: 0">
      <el-col :span="12" style="padding-left: 0">
        <p class="mgb201">用户性别比例</p>
        <div style="background-color: #fff">
          <ve-pie :data="consumerSex" :theme="options"></ve-pie>
        </div>
      </el-col>

      <el-col :span="12" style="padding-right: 0">
        <p class="mgb201">歌曲类型分布</p>
        <div style="background-color: #fff">
          <!-- histogram直方图 -->
          <ve-histogram :data="songStyle"></ve-histogram>
        </div>
      </el-col>
    </el-row>

    <el-row class="mgb20" :gutter="20" style="margin-left: 0; margin-right: 0">
      <el-col :span="12" style="padding-left: 0">
        <p class="mgb201">歌手类别比例</p>
        <div style="background-color: #fff">
          <ve-pie :data="singerSex"></ve-pie>
        </div>
      </el-col>

      <el-col :span="12" style="padding-right: 0">
        <p class="mgb201">歌手地区分布</p>
        <div style="background-color: #fff">
          <!-- histogram直方图 -->
          <ve-histogram :data="country" :theme="options1"></ve-histogram>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getAllConsumer,
  getAllSong,
  getAllSinger,
  getAllSongList,
} from "../api/index";
export default {
  data() {
    return {
      consumerCount: 0, //用户总数
      songCount: 0, //歌曲总数
      singerCount: 0, //歌手数量
      songListCount: 0, //歌单数量
      consumer: [], //所有用户
      //按性别分类用户
      consumerSex: {
        columns: ["性别", "总数"],
        rows: [
          { 性别: "男", 总数: 0 },
          { 性别: "女", 总数: 0 },
        ],
      },
      options: {
        color: ["#87cefa", "#ffc0cb"],
      },

      //歌单风格统计
      songStyle: {
        columns: ["风格", "总数"],
        rows: [],
      },

      //歌手类别
      singerSex: {
        columns: ["类别", "总数"],
        rows: [
          { 类别: "女", 总数: 0 },
          { 类别: "男", 总数: 0 },
          { 类别: "组合", 总数: 0 },
        ],
      },

      //歌手地区分布
      country: {
        columns: ["地区", "总数"],
        rows: [],
      },
      options1: {
        color: ["#f0e68c"],
      },
    };
  },

  //页面加载 created(创建)
  // created() {},

  //页面加载后 mounted(安装好的)
  mounted() {
    this.getConsumer();
    this.getSong();
    this.getSinger();
    this.getSongList();
  },

  methods: {
    //用户总数
    getConsumer() {
      getAllConsumer().then((res) => {
        this.consumer = res;
        this.consumerCount = res.length;
        this.consumerSex.rows[0]["总数"] = this.setSex(1, this.consumer);
        this.consumerSex.rows[1]["总数"] = this.setSex(0, this.consumer);
      });
    },

    //根据性别获取用户数
    setSex(sex, consumer) {
      let count = 0;
      for (let item of consumer) {
        if (sex == item.sex) {
          count++;
        }
      }
      return count;
    },

    //歌曲总数
    getSong() {
      getAllSong().then((res) => {
        this.songCount = res.length;
      });
    },

    //歌手数量
    getSinger() {
      getAllSinger().then((res) => {
        this.singerCount = res.length;
        this.singerSex.rows[0]["总数"] = this.setSingerSex(0, res);
        this.singerSex.rows[1]["总数"] = this.setSingerSex(1, res);
        this.singerSex.rows[2]["总数"] = this.setSingerSex(2, res);
        for (let item of res) {
          this.getByCountry(item.location);
        }
      });
    },

    setSingerSex(sex, val) {
      let count = 0;
      for (let item of val) {
        if (sex == item.sex) {
          count++;
        }
      }
      return count;
    },

    getByCountry(location) {
      // findIndex它返回数组中第一个满足提供的测试函数的元素的索引 不满足则返回-1
      const index = this.country.rows.findIndex(
        (item) => item.地区 === location
      );
      if (index !== -1) {
        this.country.rows[index].总数++;
      } else {
        this.country.rows.push({ 地区: location, 总数: 1 }); //push添加
      }
    },

    //歌单数量
    getSongList() {
      getAllSongList().then((res) => {
        this.songListCount = res.length;
        for (let item of res) {
          this.getByStyle(item.style);
        }
      });
    },

    //根据歌单风格获取数量
    getByStyle(style) {
      // findIndex它返回数组中第一个满足提供的测试函数的元素的索引 不满足则返回-1
      //===类型和数值相同
      const index = this.songStyle.rows.findIndex(
        (item) => item.风格 === style
      );
      if (index !== -1) {
        this.songStyle.rows[index].总数++;
      } else {
        this.songStyle.rows.push({ 风格: style, 总数: 1 }); //push添加
      }
    },
  },
};
</script>

<style>
.info {
  height: 625.5px;

  overflow-y: scroll;
}

.grid-content {
  display: flex;
  align-content: center;
}

.grid-cont-center {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: darkgray;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}
</style>
