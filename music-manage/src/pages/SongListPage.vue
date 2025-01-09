<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="danger" size="mini" @click="delAll"
          >批量删除</el-button
        >

        <el-input
          v-model="select_word"
          size="mini"
          placeholder="筛选关键字"
          class="handle-input"
          style="margin-left: 5px"
        ></el-input>

        <el-button
          style="margin-left: 5px"
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌单</el-button
        >
      </div>
    </div>

    <el-table
      size="mini"
      style="width: 100%"
      border
      max-height="500px"
      :data="data"
      @selection-change="handleSclectionChange"
    >
      <el-table-column type="selection" width="40"></el-table-column>

      <el-table-column label="歌单图片" width="110" align="center">
        <!--（位置）slot-scope="scope" 当前的单元格 -->
        <template slot-scope="scope">
          <div class="songList-img">
            <img :src="getUrl(scope.row.pic)" style="width: 100%" />
          </div>
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeAvatorUpload"
            :on-success="handleAvatorSuccess"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>

      <el-table-column
        prop="title"
        label="标题"
        width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height: 100px">
            {{ scope.row.introduction }}
          </p>
        </template>
      </el-table-column>

      <el-table-column
        prop="style"
        label="风格"
        width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="歌单管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="songEdit(scope.row.id)"
            >歌单管理</el-button
          >
        </template>
      </el-table-column>

      <el-table-column label="评论" width="80" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="getComment(scope.row.id)"
            >评论</el-button
          >
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

    <!-- 添加歌单 -->
    <el-dialog
      title="添加歌单"
      :visible.sync="centerDialogVisible"
      width="380px"
      center
      style="margin-top: 2.5%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form
        :model="registeForm"
        ref="registeForm"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item prop="title" label="标题" size="mini">
          <el-col :span="20">
            <el-input v-model="registeForm.title" placeholder="标题"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="introduction" label="简介" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.introduction"
              placeholder="简介"
              type="textarea"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="style" label="风格" size="mini">
          <el-col :span="20">
            <el-input v-model="registeForm.style" placeholder="风格"></el-input>
          </el-col>
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
        <el-button size="mini" @click="addSongList" class="editbutr"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑歌单 -->
    <el-dialog
      title="编辑歌单"
      :visible.sync="editVisible"
      width="380px"
      center
      style="margin-top: 2.5%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form :model="form" ref="form" label-width="80px" :rules="rules1">
        <el-form-item prop="title" label="标题" size="mini">
          <el-col :span="20">
            <el-input v-model="form.title" placeholder="标题"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="introduction" label="简介" size="mini">
          <el-col :span="20">
            <el-input
              v-model="form.introduction"
              placeholder="简介"
              type="textarea"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="style" label="风格" size="mini">
          <el-col :span="20">
            <el-input v-model="form.style" placeholder="风格"></el-input>
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

    <!-- 删除歌单 -->
    <el-dialog
      title="删除歌单"
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
import {
  getAllSongList,
  setSongList,
  updateSongList,
  delSongList,
} from "../api/index";
import { mixin } from "../mixins/index";
export default {
  mixins: [mixin],
  data() {
    return {
      centerDialogVisible: false, //添加
      editVisible: false, //编辑
      delVisible: false, //删除
      registeForm: {
        id: "",
        title: "",
        introduction: "",
        style: "",
      },
      form: {
        id: "",
        title: "",
        introduction: "",
        style: "",
      },
      tableData: [],
      tempDate: [],
      select_word: "",
      pageSize: 5,
      currentPage: 1,
      idx: -1, //当前选择项
      multipleSelection: [], //多选
      rules: {
        title: [{ required: true, message: "请输入标题名", trigger: "blur" }],
        style: [{ required: true, message: "请输入风格", trigger: "blur" }],
      },
      rules1: {
        title: [{ required: true, message: "请输入标题名", trigger: "blur" }],
        style: [{ required: true, message: "请输入风格", trigger: "blur" }],
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
          if (item.title.includes(this.select_word)) {
            this.tableData.push(item);
          }
        }
      }
    },
  },

  // 初始化钩子函数
  // created() {
  //   this.getData();
  // },
  mounted() {
    this.getData(); // 初始化时加载数据
  },

  methods: {
    // 查询所有歌单
    getData() {
      this.tempDate = [];
      this.tableData = [];
      getAllSongList().then((res) => {
        this.tableData = res;
        this.tempDate = res;
      });
    },

    // 添加歌单
    addSongList() {
      this.$refs.registeForm.validate((valid) => {
        if (valid) {
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("title", this.registeForm.title);
          params.append("pic", "/img/songListPic/gedan.png");
          params.append("introduction", this.registeForm.introduction);
          params.append("style", this.registeForm.style);
          setSongList(params)
            .then((res) => {
              if (res.code == 1) {
                this.getData();
                this.$notify({
                  title: "添加成功",
                  message: "",
                  type: "success",
                });
                this.registeForm.title = "";
                this.registeForm.introduction = "";
                this.registeForm.style = "";
                // this.registeForm = {}; 不建议这样写
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
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 更新图片
    uploadUrl(id) {
      // 传俩参数 前面是文件 ，‘？’后面是id
      return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`;
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
        title: row.title,
        introduction: row.introduction,
        style: row.style,
      };
    },
    //编辑框保存
    editSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("id", this.form.id);
          params.append("title", this.form.title);
          params.append("introduction", this.form.introduction);
          params.append("style", this.form.style);
          updateSongList(params)
            .then((res) => {
              if (res.code == 1) {
                getAllSongList().then((res) => {
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
      delSongList(this.idx)
        .then((res) => {
          if (res) {
            getAllSongList().then((res) => {
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

    songEdit(id) {
      this.$router.push({ path: "/ListSong", query: { id } });
    },

    //评论
    getComment(id) {
      this.$router.push({ path: "/Comment", query: { id } });
      // console.log(id);
    },
  },
};
</script>

<style>
.songList-img {
  width: 100%;
  border-radius: 5px;
  overflow: hidden;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.pageination {
  display: flex;
  /* 水平居中 */
  justify-content: center;
  /* 垂直居中 */
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
</style>
