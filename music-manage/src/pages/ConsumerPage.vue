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
          placeholder="筛选相关用户"
          class="handle-input"
          style="margin-left: 5px"
        ></el-input>

        <el-button
          style="margin-left: 5px"
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加新用户</el-button
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

      <el-table-column label="用户图片" width="110" align="center">
        <!--（位置）slot-scope="scope" 当前的单元格 -->
        <template slot-scope="scope">
          <div class="consumer-img">
            <img :src="getUrl(scope.row.avator)" style="width: 100%" />
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
        prop="username"
        label="用户名"
        width="120"
        align="center"
      ></el-table-column>

      <!-- <el-table-column
        prop="password"
        label="密码"
        width="120"
        align="center"
      ></el-table-column> -->

      <el-table-column label="性别" align="center" width="50">
        <template slot-scope="scope">
          {{ changeSex(scope.row.sex) }}
        </template>
      </el-table-column>

      <el-table-column
        prop="phoneNum"
        label="手机号"
        width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="email"
        label="电子邮箱"
        width="180"
        align="center"
      ></el-table-column>

      <el-table-column label="生日" align="center" width="120">
        <template slot-scope="scope">
          {{ attachBirth(scope.row.birth) }}
        </template>
      </el-table-column>

      <el-table-column
        prop="introduction"
        label="签名"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="location"
        label="地区"
        width="100"
        align="center"
      ></el-table-column>

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

    <!-- 添加用户 -->
    <el-dialog
      title="添加新用户"
      :visible.sync="centerDialogVisible"
      width="380px"
      center
      style="margin-top: -0.7%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form
        :model="registeForm"
        ref="registeForm"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item prop="username" label="用户名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.username"
              placeholder="用户名"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="password" label="密码" size="mini">
          <el-col :span="20">
            <el-input
              type="password"
              v-model="registeForm.password"
              placeholder="密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="性别" size="mini" prop="sex">
          <el-col :span="20">
            <el-radio-group v-model="registeForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>

        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.phoneNum"
              placeholder="手机号"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.email"
              placeholder="电子邮箱"
            ></el-input>
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

        <el-form-item prop="introduction" label="签名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registeForm.introduction"
              placeholder="签名"
              type="textarea"
            ></el-input>
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
      </el-form>
      <!-- slot(位置) -->
      <span slot="footer">
        <el-button
          size="mini"
          @click="centerDialogVisible = false"
          class="editbutf"
          >取消</el-button
        >
        <el-button size="mini" @click="addConsumer" class="editbutr"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 编辑用户 -->
    <el-dialog
      title="编辑"
      :visible.sync="editVisible"
      width="380px"
      center
      style="margin-top: -0.7%"
    >
      <!-- :ref可在下面通过this.$refs.form访问 label(标签) -->
      <el-form :model="form" ref="form" label-width="80px" :rules="rules1">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-col :span="20">
            <el-input v-model="form.username" placeholder="用户名"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="password" label="密码" size="mini">
          <el-col :span="20">
            <el-input
              type="password"
              v-model="form.password"
              placeholder="密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="性别" size="mini" prop="sex">
          <el-col :span="20">
            <el-radio-group v-model="form.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>

        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-col :span="20">
            <el-input v-model="form.phoneNum" placeholder="手机号"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-col :span="20">
            <el-input v-model="form.email" placeholder="电子邮箱"></el-input>
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

        <el-form-item prop="introduction" label="签名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="form.introduction"
              placeholder="签名"
              type="textarea"
            ></el-input>
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

    <!-- 删除用户 -->
    <el-dialog
      title="删除用户"
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
  getAllConsumer,
  setConsumer,
  updateConsumer,
  delConsumer,
  getByUsername,
} from "../api/index";
import { mixin } from "../mixins/index";
import { cities } from "../assets/js/form";
export default {
  mixins: [mixin],
  data() {
    let checkConsumer = (rule, value, callback) => {
      this.userData = [];
      getByUsername(value)
        .then((res) => {
          this.userData = res;

          if (res.username == value) {
            callback(new Error("用户名已存在"));
          } else {
            callback();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };

    return {
      centerDialogVisible: false, //添加
      editVisible: false, //编辑
      delVisible: false, //删除
      registeForm: {
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
      },
      form: {
        id: "",
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
      },
      userData: [], //验证用户是否唯一
      tableData: [],
      tempDate: [],
      select_word: "",
      pageSize: 5,
      currentPage: 1,
      idx: -1, //当前选择项
      multipleSelection: [], //多选
      cities: [], //地区
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { validator: checkConsumer, trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        phoneNum: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            /*这个正则表达式匹配的是一个字符串，它由11个数字组成，符合中国大陆手机号码的格式*/
            //^: 表示字符串的开始。它确保整个字符串从这一部分开始匹配。
            //$: 表示字符串的结束。确保整个字符串在这一部分结束。
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输⼊正确的⼿机号码",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入电子邮箱", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的电子邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        birth: [
          { required: true, message: "请选择出生日期", trigger: "change" },
        ],
        location: [{ required: true, message: "请输入地区", trigger: "blur" }],
      },
      rules1: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { validator: checkConsumer, trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        phoneNum: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            /*这个正则表达式匹配的是一个字符串，它由11个数字组成，符合中国大陆手机号码的格式*/
            //^: 表示字符串的开始。它确保整个字符串从这一部分开始匹配。
            //$: 表示字符串的结束。确保整个字符串在这一部分结束。
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输⼊正确的⼿机号码",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入电子邮箱", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的电子邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        birth: [
          { required: true, message: "请选择出生日期", trigger: "change" },
        ],
        location: [{ required: true, message: "请输入地区", trigger: "blur" }],
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
          if (item.username.includes(this.select_word)) {
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
    // 查询所有用户
    getData() {
      this.tempDate = [];
      this.tableData = [];
      getAllConsumer().then((res) => {
        this.tableData = res;
        this.tempDate = res;
      });
    },

    // 添加用户
    addConsumer() {
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
          params.append("username", this.registeForm.username);
          params.append("password", this.registeForm.password);
          params.append("sex", this.registeForm.sex);
          params.append("phoneNum", this.registeForm.phoneNum);
          params.append("email", this.registeForm.email);
          params.append("birth", datetime);
          params.append("introduction", this.registeForm.introduction);
          params.append("location", this.registeForm.location);
          params.append("avator", "/avatorImages/user.png");

          setConsumer(params)
            .then((res) => {
              if (res.code == 1) {
                this.getData();
                this.$notify({
                  title: "添加成功",
                  message: "",
                  type: "success",
                });
                this.registeForm.username = "";
                this.registeForm.password = "";
                this.registeForm.sex = "";
                this.registeForm.phoneNum = "";
                this.registeForm.email = "";
                this.registeForm.birth = "";
                this.registeForm.introduction = "";
                this.registeForm.location = "";
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
      return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`;
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
        username: row.username,
        password: row.password,
        sex: row.sex,
        phoneNum: row.phoneNum,
        email: row.email,
        birth: row.birth,
        introduction: row.introduction,
        location: row.location,
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
          params.append("username", this.form.username);
          params.append("password", this.form.password);
          params.append("sex", this.form.sex);
          params.append("phoneNum", this.form.phoneNum);
          params.append("email", this.form.email);
          params.append("birth", datetime);
          params.append("introduction", this.form.introduction);
          params.append("location", this.form.location);

          updateConsumer(params)
            .then((res) => {
              if (res.code == 1) {
                getAllConsumer().then((res) => {
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
      delConsumer(this.idx)
        .then((res) => {
          if (res) {
            getAllConsumer().then((res) => {
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
  },
};
</script>

<style>
.el-table--scrollable-y .el-table__body-wrapper {
  overflow-y: scroll;
}

.consumer-img {
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
