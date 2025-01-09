<template>
  <div class="table">
    <div class="container">
      <div class="crumbs">
        <img src="../assets/img/gedan.png" />
        <div class="crumbs-txt">歌曲信息</div>
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

      <el-table-column label="歌曲图片" width="110" align="center">
        <!--（位置）slot-scope="scope" 当前的单元格 -->
        <template slot-scope="scope">
          <div class="song-img">
            <img :src="getUrl(scope.row.pic)" />

            <div
              class="play"
              @click="setSongUrl(scope.row.url, scope.row.name)"
            >
              <div v-if="toggle == scope.row.name">
                <i class="el-icon-video-pause icon"></i>
              </div>
              <div v-if="toggle != scope.row.name">
                <i class="el-icon-video-play icon"></i>
              </div>
            </div>
          </div>

          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeAvatorUpload"
            :on-success="handleAvatorSuccess"
          >
            <el-button size="mini" style="margin-top: 6%">更新头像</el-button>
          </el-upload>
        </template>
      </el-table-column>

      <el-table-column
        prop="name"
        label="歌手-歌名"
        width="200"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="introduction"
        label="专辑"
        width="130"
        align="center"
      ></el-table-column>

      <el-table-column label="歌词" align="center">
        <template slot-scope="scope">
          <ul style="overflow-y: scroll; height: 100px">
            <!-- item 为内容 index为下标 -->
            <li
              v-for="(item, index) in parseLyric(scope.row.lyric)"
              :key="index"
            >
              {{ item }}
            </li>
          </ul>
        </template>
      </el-table-column>

      <el-table-column label="资源更新" align="center" width="120">
        <template slot-scope="scope">
          <el-upload
            :action="uploadSongUrl(scope.row.id)"
            :before-upload="beforeSongUpload"
            :on-success="handleSongSuccess"
          >
            <el-button size="mini">更新歌曲</el-button>
          </el-upload>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
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
        <el-form-item prop="name" size="mini">
          <label>歌名</label>
          <el-input
            v-model="registeForm.name"
            type="text"
            name="name"
          ></el-input>
        </el-form-item>

        <el-form-item prop="introduction" size="mini">
          <label>专辑</label>
          <el-input
            type="text"
            name="introduction"
            v-model="registeForm.introduction"
          ></el-input>
        </el-form-item>

        <el-form-item prop="lyric" size="mini">
          <label>歌词</label>
          <el-input
            type="textarea"
            name="lyric"
            v-model="registeForm.lyric"
          ></el-input>
        </el-form-item>

        <el-form-item prop="file">
          <label>歌曲上传</label>
          <el-input type="file" name="file" v-model="registeForm.file" />
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
        <el-button size="mini" @click="addSong" class="editbutr"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑歌曲 -->
    <el-dialog
      title="修改歌曲"
      :visible.sync="editVisible"
      width="380px"
      center
      style="margin-top: 2.5%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form :model="form" ref="form" label-width="80px" :rules="rules1">
        <el-form-item prop="name" label="歌名" size="mini">
          <el-col :span="20">
            <el-input v-model="form.name" placeholder="歌名"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-col :span="20">
            <el-input v-model="form.introduction" placeholder="专辑"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-col :span="20">
            <el-input
              v-model="form.lyric"
              placeholder="歌词"
              type="textarea"
            ></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <!-- slot(位置) -->
      <span slot="footer">
        <el-button size="mini" @click="editVisible = false" class="editbutf"
          >取消</el-button
        >
        <el-button size="mini" @click="editSave" class="editbutr"
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
import { mapGetters } from "vuex";
import { singerOfSingerId, updateSong, deleteSong } from "../api/index";
export default {
  mixins: [mixin],
  data() {
    return {
      singerId: "", //歌手id
      singerName: "",
      centerDialogVisible: false, //添加
      editVisible: false, //编辑
      delVisible: false, //删除
      registeForm: {
        singerId: "",
        name: "",
        singerName: "",
        introduction: "",
        lyric: "",
        file: "",
      },
      form: {
        id: "",
        name: "",
        introduction: "",
        lyric: "",
      },
      tableData: [],
      tempDate: [],
      select_word: "",
      pageSize: 5,
      currentPage: 1,
      idx: -1, //当前选择项
      multipleSelection: [], //多选
      rules: {
        name: [{ required: true, message: "请输入歌曲名", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入歌曲专辑", trigger: "blur" },
        ],
        file: [
          { required: true, message: "请上传歌曲文件", trigger: "change" },
        ],
      },
      rules1: {
        name: [{ required: true, message: "请输入歌曲名", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入歌曲专辑", trigger: "blur" },
        ],
      },
    };
  },

  // 计算，页面加载后
  computed: {
    ...mapGetters(["isplay", "toggle"]),
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
    this.singerId = this.$route.query.id;
    this.singerName = this.$route.query.name;
    this.getData();
  },
  //   mounted() {
  //     this.getData(); // 初始化时加载数据
  //   },

  // (破坏)生命周期
  destroyed() {
    this.$store.commit("setIsPlay", false);
    this.$store.commit("updateToggle", false);
  },

  methods: {
    // 查询所有歌曲
    getData() {
      this.tempDate = [];
      this.tableData = [];
      singerOfSingerId(this.singerId).then((res) => {
        this.tempDate = res;
        this.tableData = res;
      });
    },

    // 添加歌曲
    addSong() {
      this.$refs.registeForm.validate((valid) => {
        if (valid) {
          let _this = this;
          var form = new FormData(document.getElementById("tf"));
          form.append("singerId", this.singerId);
          form.set("name", this.singerName + "-" + form.get("name"));
          if (!form.get("lyric")) {
            form.set("lyric", "[00:00:00]暂无歌词");
          }
          var req = new XMLHttpRequest();
          //   请求状态变化时被调用
          req.onreadystatechange = function () {
            // req.readyState == 4请求完成(ajax)
            // 状态码为200表示成功
            if (req.readyState == 4 && req.status == 200) {
              //解析json字符串 ，json字符串
              let res = JSON.parse(req.response);
              if (res.code) {
                _this.getData();
                _this.registeForm = {
                  name: "",
                  introduction: "",
                  lyric: "",
                  file: "",
                };
                _this.$notify({
                  title: "添加成功",
                  message: "",
                  type: "success",
                });
              } else {
                _this.$notify({
                  title: "保存失败",
                  message: "",
                  type: "error",
                });
              }
            }
          };
          //   false异步，true 同步
          req.open("post", `${_this.$store.state.HOST}/song/add`, false);
          console.log(req);
          req.send(form);
          this.centerDialogVisible = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 更新图片
    uploadUrl(id) {
      // 传俩参数 前面是文件 ，‘？’后面是id
      return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`;
    },

    //获取当前页
    handleCurrent(val) {
      this.currentPage = val;
    },

    //编辑框
    handleEdit(row) {
      this.editVisible = true;
      this.form = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        lyric: row.lyric,
      };
    },
    //编辑框保存
    editSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("id", this.form.id);
          params.append("introduction", this.form.introduction);
          params.append("lyric", this.form.lyric);
          if (this.form.name.includes(this.singerName + "-")) {
            params.append("name", this.form.name);
          } else {
            params.append("name", this.singerName + "-" + this.form.name);
          }
          updateSong(params)
            .then((res) => {
              if (res.code == 1) {
                singerOfSingerId(this.singerId).then((res) => {
                  this.tableData = res;
                });
                this.$notify({
                  title: "修改成功",
                  message: "",
                  type: "success",
                });
              } else {
                this.$notify({
                  title: "修改失败",
                  message: "",
                  type: "error",
                });
              }
            })
            // 赶上:失败时调用
            .catch((err) => {
              console.log(err);
            });
          this.editVisible = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //删除
    delRow() {
      deleteSong(this.idx)
        .then((res) => {
          if (res) {
            singerOfSingerId(this.singerId).then((res) => {
              this.tableData = res;
            });
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

    // 解析歌词
    parseLyric(text) {
      // 指定的分隔符拆分成一个数组
      let lines = text.split("\n");
      // /：正则表达式的开始和结束标记
      // \d 代表数字（0-9），{2} 表示重复两次
      // . 是一个特殊字符，匹配除换行符外的任意单个字符(\.匹配.字符)
      // g：这是一个标志，表示全局搜索，找到所有匹配，而不是仅仅找到第一个
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      for (let item of lines) {
        let value = item.replace(pattern, "");
        result.push(value); //push向数组的末尾添加一个或多个元素
      }
      return result;
    },

    // 上传歌曲的校验
    beforeSongUpload(file) {
      // lastIndexOf('.') 是字符串方法，用于找到指定字符在字符串中最后一次出现的位置
      // 将找到的最后一个点的位置加 1，这样我们就得到了文件扩展名的起始位置
      // substring 方法用于提取字符串中位于两个指定下标之间的字符[) 一个数字时到最后
      var testMsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      if (!testMsg == "mp3" || !testMsg == "ogg") {
        this.$message({
          message: "上传文件只能是MP3或ogg格式",
          type: "error",
        });
        return false;
      }
      return true;
    },

    // 上传歌曲成功后
    handleSongSuccess(res) {
      let _this = this;
      if (res.code == 1) {
        _this.getData();
        _this.$notify({
          title: "上传成功",
          message: "",
          type: "success",
        });
      } else {
        _this.$notify({
          title: "上传失败",
          message: "",
          type: "success",
        });
      }
    },

    // 更新歌曲
    uploadSongUrl(id) {
      return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`;
    },

    // 切换播放歌曲
    setSongUrl(url, name) {
      if (this.toggle != name) {
        this.$store.commit("updateToggle", name);
        // commit 用于提交一个 mutation
        this.$store.commit("setUrl", this.$store.state.HOST + url);
        this.$store.commit("setIsPlay", true);
      } else {
        this.$store.commit("updateToggle", false);
        this.$store.commit("setIsPlay", false);
      }
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
