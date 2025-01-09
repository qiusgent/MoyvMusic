import { likesongOfSongName, getCollectOfUserId } from "../api/index";
import { mapGetters } from "vuex";

export const mixin = {
  computed: {
    //  ...mapGetters([])，可以将指定的 getters 映射到当前组件中。
    ...mapGetters(["toggle", "isplay", "duration", "loginIn", "userId"]),
  },

  methods: {
    //获取图片地址
    attachImageUrl(srcUrl) {
      return (srcUrl = this.$store.state.configure.HOST + srcUrl);
    },

    //格局歌曲名字 模糊查询
    getSong() {
      // console.log("歌曲：" + this.$route.query.keywords);
      if (!this.$route.query.keywords) {
        // $store 是 Vuex 的实例
        // 通过 $store 可以访问应用中的状态 (state)、提交 (commit) mutations、分发 (dispatch) actions 等操作
        this.$store.commit("setlistOfSongs", []);
        this.$notify({
          title: "您输入的内容为空",
          message: "",
          type: "error",
        });
      } else {
        likesongOfSongName(this.$route.query.keywords)
          .then((res) => {
            if (!res.length) {
              this.$store.commit("setlistOfSongs", []);
              this.$notify({
                title: "系统暂无符合条件的歌曲",
                message: "",
                type: "error",
              });
            } else {
              // commit 承诺
              this.$store.commit("setlistOfSongs", res);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },

    // 获取名字前半部分
    replaceLName(str) {
      // 字符串用 split拆分 结果是一个数组
      let arr = str.split("-");
      return arr[0];
    },

    // 获取名字后半部分
    replaceFName(str) {
      let arr = str.split("-");
      return arr[1];
    },

    //解释时间 format(格式)
    formatSeconds(value) {
      // parseInt格式转换
      let theTime = parseInt(value);
      let result = "";
      let hour = parseInt(theTime / 3600);
      let minute = parseInt((theTime / 60) % 60);
      let second = parseInt(theTime % 60);
      if (hour > 0) {
        if (hour < 10) {
          result = "0" + hour + ":";
        } else {
          result = hour + ":";
        }
      }
      if (minute > 0) {
        if (minute < 10) {
          result += "0" + minute + ":";
        } else {
          result += minute + ":";
        }
      } else {
        result += "00:";
      }
      if (second > 0) {
        if (second < 10) {
          result += "0" + second;
        } else {
          result += second;
        }
      } else {
        result += "00";
      }
      return result;
    },

    //播放
    toplay: function (id, url, pic, index, name, lyric) {
      if (this.toggle != id) {
        this.$store.commit("setIsPlay", true);
        this.$store.commit("setToggle", id);
        this.$store.commit("setId", id);
        // console.log(id);
        // this.$store.state:是用来访问 Vuex 状态管理库中存储的状态的
        this.$store.commit("setUrl", this.$store.state.configure.HOST + url);
        this.$store.commit("setPicUrl", this.$store.state.configure.HOST + pic);
        this.$store.commit("setListIndex", index);
        this.$store.commit("setTitle", this.replaceFName(name));
        this.$store.commit("setArtist", this.replaceLName(name));
        this.$store.commit("setLyric", this.parseLyric(lyric));
        // console.log(this.parseLyric(lyric));
        this.$store.commit("setIsActive", false);
        if (this.loginIn) {
          getCollectOfUserId(this.userId).then((res) => {
            for (let item of res) {
              if (item.songId == id) {
                this.$store.commit("setIsActive", true);
                break;
              }
            }
          });
        }
      } else {
        // console.log("else:");
        // console.log(this.toggle);
        // console.log(id);
        this.$store.commit("setIsPlay", false);
        this.$store.commit("setToggle", false);
      }
    },

    //解析歌词
    parseLyric(text) {
      let lines = text.split("\n");
      // return lines;
      // /：正则表达式的开始和结束标记
      // \d 代表数字（0-9），{2} 表示重复两次
      // . 是一个特殊字符，匹配除换行符外的任意单个字符(\.匹配.字符)
      // g：这是一个标志，表示全局搜索，找到所有匹配，而不是仅仅找到第一个
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      // .+：表示匹配任意字符（除换行符之外）1 次或多次。. 表示匹配任意字符，+ 表示匹配前面的字符至少 1 次。
      // test() 是 JavaScript 中用于测试字符串是否符合正则表达式规则的方法
      //eg:text的值为hello 则返回[[0, "Hello"]]，返回了一个数组（0代表不符合这个格式的key）
      if (!/\[.+\]/.test(text)) {
        return [[0, text]];
      }
      //解决数组里前面不是当前格式
      while (!pattern.test(lines[0])) {
        //split（分裂）
        // slice(割) 方法会返回包含从起始索引到结束索引的元素的子数组
        // slice(1)：是数组的一个方法，表示从索引位置 1 开始（即第二个元素），提取出所有元素，然后返回一个新的数组。
        lines = lines.slice(1);
      }
      //遍历[时间，歌词]
      for (let item of lines) {
        // match() 方法是用于在字符串中检索指定的正则表达式模式匹配的内容
        // 如果匹配成功，则返回一个包含匹配内容的数组，否则返回 null
        let time = item.match(pattern);
        let value = item.replace(pattern, " "); //replace替换 歌词
        for (let item1 of time) {
          let t = item1.slice(1, -1).split(":"); //先取[]内 取中间 再用:分割数组
          if (value != "") {
            //push这里想数组末尾添加数组
            result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]); //强制类型转换（10进制）
          }
        }
        //排序 遍历
        // return a[0] - b[0]：这里的逻辑是：
        // 如果 a[0] 小于 b[0]，返回一个负数（a 会排在 b 前面）。
        // 如果 a[0] 大于 b[0]，返回一个正数（b 会排在 a 前面）。
        // 如果 a[0] 等于 b[0]，返回 0（a 和 b 的顺序不变）
        result.sort(function (a, b) {
          return a[0] - b[0];
        });
      }
      return result;
    },
  },
};
