<template>
  <div class="sidebar">
    <!-- :default-active 活跃菜单(刷新不丢失),collapse(折叠) -->
    <el-menu
      class="sidebar-el-menu"
      :collapse="collapse"
      background-color="#4b6b8d"
      text-color="#fff"
      active-text-color="#20a0ff"
      :default-active="activeIndex"
      router
    >
      <template v-for="item in items">
        <template>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon" style="color: #fff"></i>
            <span class="item-title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import bus from "../assets/js/bus";
export default {
  data() {
    return {
      collapse: false,
      items: [
        {
          icon: "el-icon-s-platform",
          index: "Info",
          title: "系统首页",
        },
        {
          icon: "el-icon-s-custom",
          index: "Consumer",
          title: "用户管理",
        },
        {
          icon: "el-icon-user-solid",
          index: "Singer",
          title: "歌手管理",
        },
        {
          icon: "el-icon-notebook-2",
          index: "SongList",
          title: "歌单管理",
        },
      ],
      // 使用 localStorage 来存储当前激活的菜单项的索引
      // activeIndex: localStorage.getItem("activeIndex") || "Info",
    };
  },
  // 监听路由变化watch,$route:(带$)Vue 实例上的属性或方法
  // watch: {
  //   $route(to) {
  //     localStorage.setItem("activeIndex", to.name);
  //   },
  // },

  // computed计算属性是基于它们的依赖进行缓存的，只有在相关依赖发生变化时才会重新计算
  //.path：表示当前路由的路径部分，即 URL 中的路径部分
  // replace将路径中的斜杠("/")替换为空字符串
  computed: {
    activeIndex() {
      // 获取路径 不跳转
      return this.$route.path.replace("/", "");
    },
  },

  // 在 created() 钩子函数内部，我们可以访问实例的数据、方法，也可以进行一些初始化操作
  created() {
    // bus进行组件间的通信，折叠侧边栏
    // 接收
    bus.$on("collapse", (msg) => {
      this.collapse = msg;
    });
  },
};
</script>

<!-- scoped(彻底检查)下面的样式只用于当前的vue -->
<style scoped>
.sidebar {
  /* height: 100vh; */
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  background-color: #4b6b8d;
}

/* ::-webkit-scrollbar：这是一个伪元素，用于选择WebKit浏览器（如Chrome和Safari）中的滚动条。 */
.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar ul {
  height: 100%;
}

/* .el-menu是表示菜单或侧边栏的基本样式类，而--collapse则表示该菜单或侧边栏处于折叠状态 */
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 150px;
}

.item-title {
  padding: 0 2.5px;
}
</style>
