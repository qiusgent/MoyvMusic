<template>
  <div class="setting1">
    <div class="leftCol">
      <div class="settingsMainHeader">设置</div>
      <ul class="setting-aside">
        <li
          style="margin-bottom: 5px"
          v-for="(item, index) in settingList"
          :key="index"
          :class="{ activeColor: activeName == item.name }"
          @click="handleClick(item)"
        >
          {{ item.name }}
        </li>
      </ul>
    </div>
    <div class="contentCol">
      <!--component 组件 is渲染子组件-->
      <component :is="componentSrc"></component>
    </div>
  </div>
</template>

<script>
import Info from "../components/Info.vue";
import Upload from "../components/Upload.vue";
export default {
  name: "setting",
  components: { Info, Upload },
  data() {
    return {
      settingList: [
        { name: "个人信息", path: "Info" },
        { name: "修改头像", path: "Upload" },
      ],
      activeName: "个人信息",
      componentSrc: "Info",
    };
  },

  mounted() {},

  methods: {
    handleClick(item) {
      this.activeName = item.name;
      this.componentSrc = item.path;
    },
  },
};
</script>

<style>
@import "../assets/css/setting.css";
</style>
