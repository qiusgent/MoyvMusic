export const mixin = {
  methods: {
    // 根据相对地址获取觉得地址
    getUrl(url) {
      // `${...}`插入变量或表达式到字符串中
      return `${this.$store.state.HOST}${url}`;
    },

    // 获取性别
    changeSex(value) {
      if (value == 0) {
        return "女";
      }
      if (value == 1) {
        return "男";
      }
      if (value == 2) {
        return "组合";
      }
      return value;
    },

    // 获取生日
    attachBirth(val) {
      // substr提取字符串[0,10)
      return String(val).substr(0, 10);
    },

    // 上传图片前的校验
    beforeAvatorUpload(file) {
      // image 表示文件的类别，即图片
      //‌jpeg和jpg是一样的
      const is = file.type === "image/jpeg" || file.type === "image/png";
      if (!is) {
        this.$message.error("上传图片只能是jpg或png格式");
        return false;
      }
      // const is2M = (file.size/1024/1024) <2;
      // if(!is2M){
      //   this.$message.error("上传图片大小不能超过2MB");
      //   return false;
      // }
      return true;
    },

    // 上传图片成功后
    handleAvatorSuccess(res) {
      let _this = this;
      if (res.code == 1) {
        _this.getData();
        _this.$notify({
          title: "上传成功",
          message: "",
          type: "success",
        });
      } else {
        _this.$notify({
          title: "上传失败",
          message: "",
          type: "success",
        });
      }
    },

    // 弹出删除
    handleDelete(id) {
      this.idx = id;
      this.delVisible = true;
    },

    //把选择的项赋值给multipleSelection
    handleSclectionChange(val) {
      this.multipleSelection = val;
      // console.log(this.multipleSelection);
    },

    //批量删除
    delAll() {
      for (let item of this.multipleSelection) {
        this.handleDelete(item.id);
        this.delRow();
      }
      this.multipleSelection = [];
    },
  },
};
