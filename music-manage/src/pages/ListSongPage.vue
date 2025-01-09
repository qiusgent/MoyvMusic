<template>
  <div class="table">
    <div class="container">
      <div class="crumbs">
        <img src="../assets/img/gedan.png" />
        <div class="crumbs-txt">歌单歌曲信息</div>
      </div>
      <div class="handle-box">
        <el-button type="danger" size="mini" @click="delAll"
          >批量删除</el-button
        >

        <el-input
          v-model="select_word"
          size="mini"
          placeholder="请输入歌曲名"
          class="handle-input"
          style="margin-left: 5px"
        ></el-input>

        <el-button
          style="margin-left: 5px"
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌曲</el-button
        >
      </div>
    </div>

    <el-table
      size="mini"
      style="width: 100%"
      border
      height="66vh"
      :data="data"
      @selection-change="handleSclectionChange"
    >
      <el-table-column type="selection" width="40"></el-table-column>

      <el-table-column
        prop="name"
        label="歌手-歌名"
        align="center"
      ></el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            plain
            @click="handleDelete(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="pageination">
      <!--layout(布局)  -->
      <el-pagination
        background
        layout="total,prev,pager,next,jumper"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrent"
      ></el-pagination>
    </div>

    <!-- 添加歌曲 -->
    <el-dialog
      title="添加歌曲"
      :visible.sync="centerDialogVisible"
      width="350px"
      center
      style="margin-top: 1.5%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form
        :model="registeForm"
        ref="registeForm"
        action=""
        id="tf"
        :rules="rules"
      >
        <el-form-item prop="singerName" label="歌手名" size="mini">
          <el-input
            v-model="registeForm.singerName"
            type="text"
            name="singerName"
          ></el-input>
        </el-form-item>

        <el-form-item prop="songName" label="歌曲名" size="mini">
          <el-input
            v-model="registeForm.songName"
            type="text"
            name="songName"
          ></el-input>
        </el-form-item>
      </el-form>
      <!-- slot(位置) -->
      <span slot="footer">
        <el-button
          size="mini"
          @click="centerDialogVisible = false"
          class="editbutf"
          >取消</el-button
        >
        <el-button size="mini" @click="getSongId" class="editbutr"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除歌曲 -->
    <el-dialog
      title="删除歌曲"
      :visible.sync="delVisible"
      width="300px"
      center
      style="margin-top: 8%"
    >
      <div align="center">删除不可恢复，是否确定删除呢？</div>
      <!-- slot(位置) -->
      <span slot="footer">
        <el-button size="mini" @click="delVisible = false" class="editbutf"
          >取消</el-button
        >
        <el-button size="mini" @click="delRow" class="editbutr">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from "../mixins/index";
import {
  FindBySingerName,
  ListSongDetail,
  findBySongAndSongList,
  songOfSongId,
  songOfSongName,
  ListSongAdd,
  delListSong,
} from "../api/index";
export default {
  mixins: [mixin],
  data() {
    let checksingerName = (rule, value, callback) => {
      FindBySingerName(value)
        .then((res) => {
          //Array.isArray 判断是否为数组
          if (Array.isArray(res) && res.length > 0) {
            callback();
            console.log(FindBySingerName(value));
          } else {
            callback(new Error("歌手不存在"));
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };
    let checksongName = (rule, value, callback) => {
      let singerName = this.registeForm.singerName;
      // console.log(singerName);
      let songName = value; // 当前输入的歌曲名
      var songOfName = singerName + "-" + songName;
      songOfSongName(songOfName)
        .then((res) => {
          if (Array.isArray(res) && res.length > 0) {
            findBySongAndSongList(res[0].id, this.$route.query.id).then(
              (res2) => {
                if (res2) {
                  // console.log(res2 + "真");
                  callback(new Error("该歌手唱的歌曲已存在"));
                } else {
                  // console.log(res2 + "假");
                  callback(); // 验证通过
                }
              }
            );
          } else {
            callback(new Error("歌曲名不存在")); // 验证失败
          }
        })
        .catch((err) => {
          console.log(err);
          callback(new Error("验证过程出现错误")); // 处理错误
        });
    };
    return {
      centerDialogVisible: false, //添加
      delVisible: false, //删除
      registeForm: {
        singerName: "",
        songName: "",
      },
      tableData: [],
      tempDate: [],
      select_word: "",
      pageSize: 13,
      currentPage: 1,
      idx: -1, //当前选择项
      multipleSelection: [], //多选
      songListId: "", //歌单id
      rules: {
        singerName: [
          { required: true, message: "请输入歌手名", trigger: "blur" },
          { validator: checksingerName, trigger: "blur" },
        ],
        songName: [
          { required: true, message: "请输入歌曲名", trigger: "blur" },
          { validator: checksongName, trigger: "blur" },
        ],
      },
    };
  },

  // 计算，页面加载后
  computed: {
    // 计算当前搜索结果里的数据
    data() {
      // slice割[)
      return this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
  },

  // 监控变化的值
  watch: {
    select_word: function () {
      if (this.select_word == "") {
        this.tableData = this.tempDate;
      } else {
        this.tableData = [];
        // for...of 循环 push向数组末尾添加
        for (let item of this.tempDate) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item);
          }
        }
      }
    },
  },

  //初始化钩子函数
  created() {
    this.songListId = this.$route.query.id;
    this.getData();
  },
  //   mounted() {
  //     this.getData(); // 初始化时加载数据
  //   },

  methods: {
    // 查询所有歌曲
    getData() {
      this.tempDate = [];
      this.tableData = [];
      ListSongDetail(this.songListId).then((res) => {
        // for (let item of res) {
        //   // this.getSong(item.songId);
        // }

        //使用 map 方法来创建一个包含所有异步请求的数组。
        //使用 Promise.all 方法来等待所有异步请求完成
        //解决刷新时数据排序不规定
        const promises = res.map((item) => songOfSongId(item.songId));
        Promise.all(promises).then((results) => {
          this.tempDate = results;
          this.tableData = results;
        });
      });
    },

    //根据歌曲id查询歌曲对象，放到tempDate 和 tableData里
    // getSong(id) {
    //   songOfSongId(id)
    //     .then((res) => {
    //       this.tempDate.push(res);
    //       this.tableData.push(res);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },

    // 添加歌曲前的准备，获取到歌曲id
    getSongId() {
      this.$refs.registeForm.validate((valid) => {
        if (valid) {
          let _this = this;
          var songOfName =
            _this.registeForm.singerName + "-" + _this.registeForm.songName;
          songOfSongName(songOfName).then((res) => {
            this.addSong(res[0].id);
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //添加
    addSong(songId) {
      // URLSearchParams 对象，用于处理 URL 查询参数
      let params = new URLSearchParams();
      params.append("songId", songId);
      params.append("songListId", this.songListId);
      ListSongAdd(params)
        .then((res) => {
          if (res.code == 1) {
            this.getData();
            this.registeForm.singerName = "";
            this.registeForm.songName = "";
            this.$notify({
              title: "添加成功",
              message: "",
              type: "success",
            });
          } else {
            this.$notify({
              title: "添加失败",
              message: "",
              type: "error",
            });
          }
        })
        // 赶上:失败时调用
        .catch((err) => {
          console.log(err);
        });
      this.centerDialogVisible = false;
    },

    //获取当前页
    handleCurrent(val) {
      this.currentPage = val;
    },

    //删除
    delRow() {
      delListSong(this.idx, this.songListId)
        .then((res) => {
          if (res) {
            this.getData();
            this.$notify({
              title: "删除成功",
              message: "",
              type: "success",
            });
          } else {
            this.$notify({
              title: "删除失败",
              message: "",
              type: "error",
            });
          }
        })
        // 赶上:失败时调用
        .catch((err) => {
          console.log(err);
        });
      this.delVisible = false;
    },
  },
};
</script>

<style>
.handle-input {
  width: 300px;
  display: inline-block;
}

.pageination {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 55px;
}

.editbutr {
  background: #2faee6;
  color: #fff;
}
.editbutr:hover {
  background: #2e8fbc;
  color: #fff;
}

.editbutf {
  background: #eff3f8;
  color: #35c1ff;
  border: 1px solid #eff3f8;
}
.editbutf:hover {
  background: #eaeef3;
  color: #35c1ff;
  border: 1px solid #eff3f8;
}

.el-dialog__footer {
  padding: 0px 20px 20px;
}

.el-dialog--center .el-dialog__body {
  text-align: initial;
  padding: 25px 25px 25px;
}

.song-img {
  width: 100%;
  height: 90px;
  border-radius: 5px;
  overflow: hidden;
}
.song-img img {
  width: 100%; /* 图片宽度充满容器 */
  height: 100%; /* 图片高度充满容器 */
  object-fit: cover; /* 保持图片比例并填充容器 */
}
.play {
  position: absolute;
  z-index: 1;
  background: rgba(0, 0, 0, 0.4);
  width: 89.2px;
  height: 89.2px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 7px;
  left: 10px;
  border-radius: 5px;
  opacity: 0;
}
.song-img:hover .play {
  opacity: 1;
}

.song-img:hover img {
  transform: scale(1.05);
  transition: all 0.5s ease;
}
.icon {
  overflow: hidden;
  color: #ffffff;
  font-size: 32px;
}

.el-dialog--center .el-dialog__body {
  padding: 5px 25px 0 25px;
}
</style>
