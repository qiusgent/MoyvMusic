<template>
  <div class="header">
    <div class="collapse-btn" @click="CollapseChange">
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">摸鱼音乐后台</div>
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullScreen">
        <!--tooltip 文字提示 placement（属性决定展示效果）  -->
        <el-tooltip
          :content="fullscreen ? '取消全屏' : '全屏'"
          placement="bottom"
        >
          <i class="el-icon-rank"></i>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="../assets/img/huai.jpg" alt="" />
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ userName }}
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import bus from "../assets/js/bus";
export default {
  data() {
    return {
      collapse: false,
      fullscreen: false,
    };
  },
  methods: {
    // 侧边栏折叠
    CollapseChange() {
      this.collapse = !this.collapse;
      // 发送
      bus.$emit("collapse", this.collapse);
    },

    // 全屏
    handleFullScreen() {
      if (this.fullscreen) {
        //现代浏览器（文件）推出全屏
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        }
      } else {
        //访问和操作整个文档的属性和内容
        let element = document.documentElement;
        //实现元素全屏显示
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },

    handleCommand(command) {
      if (command == "logout") {
        localStorage.removeItem("userName");
        this.$router.push("/");
      }
    },
  },

  computed: {
    userName() {
      return localStorage.getItem("userName");
    },
  },
};
</script>

<style>
.header {
  position: relative;
  background-color: #215177;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}

.header .logo {
  float: left;
  line-height: 71px;
  font-size: 30px;
  font-weight: 600;
  font-family: STKaiti;
}

.header-right {
  float: right;
  padding-right: 50px;
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-fullscreen {
  margin-right: 5px;
  font-size: 24px;
  transform: rotate(45deg);
}
.user-avator {
  margin-left: 20px;
}

.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.user-name {
  margin-left: 10px;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}

.el-dropdown-menu__item:not(.is-disabled):hover {
  background-color: #fff;
}
</style>
