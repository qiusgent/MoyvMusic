<template>
  <div>
    <div class="info2-data">
      <div class="info2-title">
        <span>编辑个人资料</span>
      </div>
      <hr />
      <div class="personal">
        <el-form
          :model="registerForm"
          ref="registerForm"
          label-width="80px"
          :rules="rules"
        >
          <el-form-item prop="username" label="用户名" size="mini">
            <el-input
              v-model="registerForm.username"
              placeholder="用户名"
            ></el-input>
          </el-form-item>

          <el-form-item prop="password" label="密码" size="mini">
            <el-input
              type="password"
              v-model="registerForm.password"
              placeholder="密码"
            ></el-input>
          </el-form-item>

          <el-form-item label="性别" size="mini" prop="sex">
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item prop="phoneNum" label="手机号" size="mini">
            <el-input
              v-model="registerForm.phoneNum"
              placeholder="手机号"
            ></el-input>
          </el-form-item>

          <el-form-item prop="email" label="电子邮箱" size="mini">
            <el-input
              v-model="registerForm.email"
              placeholder="电子邮箱"
            ></el-input>
          </el-form-item>

          <el-form-item prop="birth" label="生日" size="mini">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="registerForm.birth"
              style="width: 100%"
            ></el-date-picker>
          </el-form-item>

          <el-form-item prop="introduction" label="签名" size="mini">
            <el-input
              v-model="registerForm.introduction"
              placeholder="签名"
              type="textarea"
            ></el-input>
          </el-form-item>

          <el-form-item prop="location" label="地区" size="mini">
            <el-select
              v-model="registerForm.location"
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
          </el-form-item>
        </el-form>
        <div class="info2-btn">
          <el-button @click="saveMsg" type="primary" size="mini"
            >保存</el-button
          >
          <el-button @click="goback(-1)" size="mini">取消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { cities } from "../assets/data/form.js";
import { updateUserMsg, getUserOfId } from "../api/index";
import { mapGetters } from "vuex";
export default {
  name: "info",
  data() {
    return {
      // register登记
      registerForm: {
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
      },
      cities: [], //地区
      previousPath: "", //上一个路径
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
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

  mounted() {
    this.getMsg(this.userId);
    // console.log(this.activeName);
  },

  //计算:根据相关数据的变化而自动更新
  computed: {
    ...mapGetters(["activeName", "userId"]),
  },

  methods: {
    //用户信息
    getMsg(userId) {
      getUserOfId(userId)
        .then((res) => {
          this.registerForm.username = res.username;
          this.registerForm.password = res.password;
          this.registerForm.sex = res.sex;
          this.registerForm.phoneNum = res.phoneNum;
          this.registerForm.email = res.email;
          this.registerForm.birth = res.birth;
          this.registerForm.introduction = res.introduction;
          this.registerForm.location = res.location;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //保存
    saveMsg() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let d = new Date(this.registerForm.birth);
          var datetime =
            d.getFullYear() +
            "-" +
            (d.getMonth() + 1) +
            "-" +
            (d.getDate() + 1);

          console.log(this.registerForm.birth);
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("id", this.userId);
          params.append("username", this.registerForm.username);
          params.append("password", this.registerForm.password);
          params.append("sex", this.registerForm.sex);
          params.append("phoneNum", this.registerForm.phoneNum);
          params.append("email", this.registerForm.email);
          params.append("birth", datetime);
          params.append("introduction", this.registerForm.introduction);
          params.append("location", this.registerForm.location);
          updateUserMsg(params)
            .then((res) => {
              if (res.code == 1) {
                this.$notify({
                  title: "保存成功",
                  message: "",
                  type: "success",
                });
                this.$store.commit("setUsername", this.registerForm.username);
              } else {
                this.$notify({
                  title: "保存失败",
                  message: "",
                  type: "error",
                });
              }
            })
            // 赶上:失败时调用
            .catch((err) => {
              console.log(err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 取消
    goback(index) {
      // go路由历史记录中向前或向后导航；-1(向前)
      this.$router.go(index);
      this.$store.commit("setActiveName", this.activeName);
    },
  },
};
</script>

<style>
@import "../assets/css/info.css";
</style>
