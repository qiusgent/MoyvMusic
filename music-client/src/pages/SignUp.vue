<template>
  <div>
    <login-logo></login-logo>
    <div class="sign-up">
      <div class="sign-up-head">
        <span>用户注册</span>
      </div>
      <el-form
        :model="registerForm"
        ref="registerForm"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item prop="username" label="用户名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registerForm.username"
              placeholder="用户名"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="password" label="密码" size="mini">
          <el-col :span="20">
            <el-input
              type="password"
              v-model="registerForm.password"
              placeholder="密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="性别" size="mini" prop="sex">
          <el-col :span="20">
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-col>
        </el-form-item>

        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registerForm.phoneNum"
              placeholder="手机号"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registerForm.email"
              placeholder="电子邮箱"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="birth" label="生日" size="mini">
          <el-col :span="20">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="registerForm.birth"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item prop="introduction" label="签名" size="mini">
          <el-col :span="20">
            <el-input
              v-model="registerForm.introduction"
              placeholder="签名"
              type="textarea"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item prop="location" label="地区" size="mini">
          <el-col :span="20">
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
          </el-col>
        </el-form-item>
        <div class="sign-up-btn">
          <!-- goback(-1)返回上一个页面 -->
          <el-button @click="goback(-1)">取消</el-button>
          <el-button @click="Sigup" type="primary">确定</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import loginLogo from "../components/LoginLogo.vue";
import { cities } from "../assets/data/form.js";
import { setConsumer, getByUsername } from "../api/index";
import { mapGetters } from "vuex";
export default {
  name: "sign-up",
  components: { loginLogo },
  data() {
    let checkConsumer = (rule, value, callback) => {
      getByUsername(value).then((res) => {
        if (res.username == value) {
          callback(new Error("用户名已存在"));
        } else {
          callback();
        }
      });
    };

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
      previousPathName: "", //上一个路径名字
      PathName: "", //对应TheHeader的name
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
    };
  },

  //页面创建的时候加载
  created() {
    this.cities = cities;
  },

  //计算:根据相关数据的变化而自动更新
  computed: {
    ...mapGetters(["activeName"]),
  },

  //获取前一个路径的地址，名字
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      // 通过 `vm` 访问组件实例,将值传入fromPath 相当于this
      vm.previousPath = from.path;
      // console.log(from);
      vm.previousPathName = from.name;
    });
  },

  methods: {
    //注册提交
    Sigup() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let d = this.registerForm.birth;
          let datetime =
            d.getFullYear() +
            "-" +
            (d.getMonth() + 1) +
            "-" +
            (d.getDate() + 1);
          // URLSearchParams 对象，用于处理 URL 查询参数
          let params = new URLSearchParams();
          params.append("username", this.registerForm.username);
          params.append("password", this.registerForm.password);
          params.append("sex", this.registerForm.sex);
          params.append("phoneNum", this.registerForm.phoneNum);
          params.append("email", this.registerForm.email);
          params.append("birth", datetime);
          params.append("introduction", this.registerForm.introduction);
          params.append("location", this.registerForm.location);
          params.append("avator", "/avatorImages/user.png");

          setConsumer(params)
            .then((res) => {
              if (res.code == 1) {
                this.$notify({
                  title: "注册成功",
                  message: "",
                  type: "success",
                });
                //setTimeout 函数延迟
                setTimeout(() => {
                  this.$router.push({ path: "/" });
                  this.$store.commit("setActiveName", "首页");
                }, 500);
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
                  title: "注册失败",
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
      switch (this.previousPathName) {
        case "home":
          this.PathName = "首页";
          break;
        case "song-list":
          this.PathName = "歌单";
          break;
        case "singer":
          this.PathName = "歌手";
          break;
        case "my-music":
          this.PathName = "我的音乐";
          break;
        case "login-in":
          this.PathName = "登录";
          break;
        default:
          console.log("未知上一个路径");
      }
      // console.log(this.PathName);
      this.$store.commit("setActiveName", this.PathName);
    },
  },
};
</script>

<style>
@import "../assets/css/sign-up.css";
</style>
