<template>
  <div class="upload">
    <p class="upload-title">修改头像</p>
    <hr />
    <div class="section">
      <!-- drag拖：是否启用拖拽上传 -->
      <!-- action:上传地址 -->
      <!-- show-file-list是否显示已上传文件列表 -->
      <!--  on-success文件上传成功时的钩子-->
      <!-- before-upload上传文件之前的钩子，参数为上传的文件 -->
      <el-upload
        drag
        :action="uploadUrl()"
        :show-file-list="false"
        :on-success="handleAvatorSucess"
        :before-upload="beforeAvatorUpload"
      >
        <i class="el-icon-upload"></i>
        <div>将文件拖到此处，修改<span style="color: blue">头像</span></div>
        <div slot="tip">只能上传jpg/png文件，且不能超过10MB</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import { mapGetters } from "vuex";
export default {
  mixins: [mixin],
  name: "upload",
  data() {
    return {};
  },

  computed: {
    ...mapGetters(["userId"]),
  },

  methods: {
    uploadUrl() {
      return `${this.$store.state.configure.HOST}/consumer/updateConsumerPic?id=${this.userId}`;
    },

    //文件上传成功时/后
    handleAvatorSucess(res) {
      //   console.log(res);
      if (res.code == 1) {
        this.$store.commit("setAvator", res.avator);
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
    },

    //文件上传之前
    beforeAvatorUpload(file) {
      //   console.log(file);
      const isImg = file.type === "image/jpeg" || file.type === "image/png";
      const iSize = file.size / 1024 / 1024 < 10;
      if (!isImg) {
        this.$notify({
          title: "上传图片格式应为jpg或png",
          message: "",
          type: "error",
        });
        return false;
      }
      if (!iSize) {
        this.$notify({
          title: "上传图片应小于10MB",
          message: "",
          type: "error",
        });
        return false;
      }
      return true;
    },
  },
};
</script>

<style>
@import "../assets/css/upload.css";
</style>
