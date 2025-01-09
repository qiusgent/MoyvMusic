<template>
  <div class="login-wrap">
    <div class="ms-title">摸鱼音乐 后台管理登录</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <!-- 占位符 -->
          <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ruleForm.password"
            placeholder="密码"
            type="password"
            @keyup.enter.native="submitForm"
          ></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getLoginStatus } from "../api/index";

export default {
  name: "Login",
  data: function () {
    return {
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  // 函数/方法
  methods: {
    submitForm() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let params = new URLSearchParams();
          params.append("name", this.ruleForm.username);
          params.append("password", this.ruleForm.password);
          getLoginStatus(params).then((res) => {
            if (res.code == 1) {
              // 将数据存储在浏览器的本地存储中
              localStorage.setItem("userName", this.ruleForm.username);

              this.$router.push("Info");
              // notify(通知) element
              this.$notify({
                title: "登录成功",
                message: "",
                type: "success",
              });
            } else {
              this.$notify({
                title: "登录失败",
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
  },
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/MoYv2.png");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  background-size: 103%;
  /* background-color: #000; */
  width: 100%;
  height: 100vh;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #2a4d59;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
</style>
