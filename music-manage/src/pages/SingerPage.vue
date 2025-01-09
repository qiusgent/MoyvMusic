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
          placeholder="请输入歌手名"
          class="handle-input"
          style="margin-left: 5px"
        ></el-input>

        <el-button
          style="margin-left: 5px"
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌手</el-button
        >
      </div>
    </div>

    <el-table
      size="mini"
      style="width: 100%"
      border
      height="500px"
      :data="data"
      @selection-change="handleSclectionChange"
    >
      <el-table-column type="selection" width="40"></el-table-column>

      <el-table-column label="歌手图片" width="110" align="center">
        <!--（位置）slot-scope="scope" 当前的单元格 -->
        <template slot-scope="scope">
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)" style="width: 100%" />
          </div>
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeAvatorUpload"
            :on-success="handleAvatorSuccess"
          >
            <el-button size="mini">更新头像</el-button>
          </el-upload>
        </template>
      </el-table-column>

      <el-table-column
        prop="name"
        label="歌手"
        width="120"
        align="center"
      ></el-table-column>

      <el-table-column label="类别" align="center" width="50">
        <template slot-scope="scope">
          {{ changeSex(scope.row.sex) }}
        </template>
      </el-table-column>

      <el-table-column label="生日" align="center" width="120">
        <template slot-scope="scope">
          {{ attachBirth(scope.row.birth) }}
        </template>
      </el-table-column>

      <el-table-column
        prop="location"
        label="地区"
        width="100"
        align="center"
      ></el-table-column>

      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height: 100px; overflow-y: scroll; scrollbar-width: none">
            {{ scope.row.introduction }}
          </p>
        </template>
      </el-table-column>

      <el-table-column label="歌曲管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="songEdit(scope.row.id, scope.row.name)"
            >歌曲管理</el-button
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

    <!-- 添加歌手 -->
    <el-dialog
      title="添加歌手"
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
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.name"
              placeholder="歌手名"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="类别" size="mini" prop="sex">
          <el-col :span="20">
            <el-radio-group v-model="registeForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">组合</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>

        <el-form-item prop="birth" label="生日" size="mini">
          <el-col :span="20">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="registeForm.birth"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item prop="location" label="地区" size="mini">
          <el-col :span="20">
            <el-select
              v-model="registeForm.location"
              placeholder="地区"
              style="width: 100%"
            >
              <!-- option（选项）label显示的内容 -->
              <el-option
                v-for="item in cities"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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
      </el-form>
      <!-- slot(位置) -->
      <span slot="footer">
        <el-button
          size="mini"
          @click="centerDialogVisible = false"
          class="editbutf"
          >取消</el-button
        >
        <el-button size="mini" @click="addSinger" class="editbutr"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑歌手 -->
    <el-dialog
      title="编辑歌手"
      :visible.sync="editVisible"
      width="380px"
      center
      style="margin-top: 2.5%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form :model="form" ref="form" label-width="80px" :rules="rules1">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-col :span="20">
            <el-input v-model="form.name" placeholder="歌手名"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="类别" size="mini" prop="sex">
          <el-col :span="20">
            <el-radio-group v-model="form.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">组合</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>

        <el-form-item prop="birth" label="生日" size="mini">
          <el-col :span="20">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.birth"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item prop="location" label="地区" size="mini">
          <el-col :span="20">
            <el-select
              v-model="form.location"
              placeholder="地区"
              style="width: 100%"
            >
              <!-- option（选项）label显示的内容 -->
              <el-option
                v-for="item in cities"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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

    <!-- 删除歌手 -->
    <el-dialog
      title="删除歌手"
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
import { getAllSinger, setSinger, updateSinger, delSinger } from "../api/index";
import { mixin } from "../mixins/index";
import { cities } from "../assets/js/form";
export default {
  mixins: [mixin],
  data() {
    return {
      centerDialogVisible: false, //添加
      editVisible: false, //编辑
      delVisible: false, //删除
      registeForm: {
        name: "",
        sex: "",
        birth: "",
        location: "",
        introduction: "",
        cities: [], //地区
      },
      form: {
        id: "",
        name: "",
        sex: "",
        birth: "",
        location: "",
        introduction: "",
      },
      tableData: [],
      tempDate: [],
      select_word: "",
      pageSize: 5,
      currentPage: 1,
      idx: -1, //当前选择项
      multipleSelection: [], //多选
      rules: {
        name: [{ required: true, message: "请输入歌手名", trigger: "blur" }],
        sex: [{ required: true, message: "请选择类别", trigger: "change" }],
        birth: [{ required: true, message: "请选择出道日期", trigger: "blur" }],
        location: [
          { required: true, message: "请输入出生地区", trigger: "blur" },
        ],
      },
      rules1: {
        name: [{ required: true, message: "请输入歌手名", trigger: "blur" }],
        sex: [{ required: true, message: "请选择类别", trigger: "change" }],
        birth: [{ required: true, message: "请选择出道日期", trigger: "blur" }],
        location: [
          { required: true, message: "请输入出生地区", trigger: "blur" },
        ],
      },
    };
  },

  //页面创建的时候加载
  created() {
    this.cities = cities;
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

  // 初始化钩子函数
  // created() {
  //   this.getData();
  // },
  mounted() {
    this.getData(); // 初始化时加载数据
  },

  methods: {
    // 查询所有歌手
    getData() {
      this.tempDate = [];
      this.tableData = [];
      getAllSinger().then((res) => {
        this.tableData = res;
        this.tempDate = res;
      });
    },

    // 添加歌手
    addSinger() {
      this.$refs.registeForm.validate((valid) => {
        if (valid) {
          let d = new Date(this.registeForm.birth);
          let datetime =
            d.getFullYear() +
            "-" +
            (d.getMonth() + 1) +
            "-" +
            (d.getDate() + 1);
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("name", this.registeForm.name);
          params.append("sex", this.registeForm.sex);
          params.append("pic", "/img/singerPic/touxiang.png");
          params.append("birth", datetime);
          params.append("location", this.registeForm.location);
          params.append("introduction", this.registeForm.introduction);

          setSinger(params)
            .then((res) => {
              if (res.code == 1) {
                this.getData();
                this.$notify({
                  title: "添加成功",
                  message: "",
                  type: "success",
                });
                this.registeForm.name = "";
                this.registeForm.sex = "";
                this.registeForm.birth = "";
                this.registeForm.location = "";
                this.registeForm.introduction = "";
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
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`;
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
        sex: row.sex,
        birth: row.birth,
        location: row.location,
        introduction: row.introduction,
      };
    },
    //编辑框保存
    editSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let d = new Date(this.form.birth);
          let datetime =
            d.getFullYear() +
            "-" +
            (d.getMonth() + 1) +
            "-" +
            (d.getDate() + 1);
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("id", this.form.id);
          params.append("name", this.form.name);
          params.append("sex", this.form.sex);
          params.append("birth", datetime);
          params.append("location", this.form.location);
          params.append("introduction", this.form.introduction);

          updateSinger(params)
            .then((res) => {
              if (res.code == 1) {
                getAllSinger().then((res) => {
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
      delSinger(this.idx)
        .then((res) => {
          if (res) {
            getAllSinger().then((res) => {
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

    songEdit(id, name) {
      this.$router.push({ path: "/Song", query: { id, name } });
    },
  },
};
</script>

<style>
.singer-img {
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
