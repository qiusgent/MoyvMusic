<template>
  <div>
    <login-logo></login-logo>
    <div class="sign-up" style="margin-top: 105px">
      <div class="sign-up-head">
        <span>账号登录</span>
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
              @keyup.enter.native="handleLoginIn"
            ></el-input>
          </el-col>
        </el-form-item>

        <div class="sign-up-btn">
          <el-button @click="goSignUp">注册</el-button>
          <el-button @click="handleLoginIn" type="primary">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import loginLogo from "../components/LoginLogo.vue";
import { loginConsumer, getByUsername } from "../api/index";
import { mapGetters } from "vuex";

export default {
  name: "login-in",
  components: { loginLogo },
  data() {
    return {
      // register登记
      registerForm: {
        username: "",
        password: "",
      },
      // blur 失去焦点时触发
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
        ],
      },
    };
  },

  //页面创建的时候加载
  created() {},

  //计算:根据相关数据的变化而自动更新
  computed: {
    ...mapGetters(["activeName"]),
  },

  methods: {
    //登录
    handleLoginIn() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let params = new URLSearchParams();
          params.append("username", this.registerForm.username);
          params.append("password", this.registerForm.password);
          loginConsumer(params).then((res) => {
            if (res.code == 1) {
              // notify(通知) element
              this.$notify({
                title: "登录成功",
                message: "",
                type: "success",
              });
              this.$store.commit("setLoginIn", true);
              this.$store.commit("setUserId", res.userMsg.id);
              this.$store.commit("setUsername", res.userMsg.username);
              this.$store.commit("setAvator", res.userMsg.avator);
              //setTimeout 函数延迟
              setTimeout(() => {
                this.$router.push({ path: "/" });
                this.$store.commit("setActiveName", "首页");
              }, 500);
            } else {
              this.$notify({
                title: "用户或密码不正确",
                message: "",
                type: "error",
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    // 注册
    goSignUp() {
      this.$router.push({ path: "/sign-up" });
      this.$store.commit("setActiveName", "注册");
    },
  },
};
</script>

<style>
@import "../assets/css/sign-up.css";
</style>
