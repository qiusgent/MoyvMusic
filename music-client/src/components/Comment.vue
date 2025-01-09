<template>
  <div>
    <div class="comment">
      <div class="PingLun">
        <h2>评论</h2>
        <span>共{{ commentList.length }}条评论</span>
      </div>
      <div class="comment-msg">
        <el-input
          class="comment-input"
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="textarea"
        ></el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment"
        >发布</el-button
      >
    </div>

    <ul class="popular" v-for="(item, index) in commentList" :key="index">
      <li>
        <div class="popular-img">
          <img :src="attachImageUrl(userPic[index])" />
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{ userName[index] }}</li>
            <li class="time">{{ parseTime(item.createTime) }}</li>
            <li class="content" style="height: 38px">{{ item.content }}</li>
          </ul>
        </div>

        <div class="up" @click="postUp(item.id, item.up, index)">
          <svg
            t="1730884424964"
            class="icon1"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4278"
            width="23"
            height="23"
          >
            <path
              ref="up"
              d="M710.549333 384.810667a12409.045333 12409.045333 0 0 0 47.466667-0.32l8.746667-0.085334c83.989333-0.618667 141.44 67.584 126.72 150.229334L847.296 794.026667c-10.026667 56.448-63.914667 101.546667-121.130667 101.589333L298.624 896a42.730667 42.730667 0 0 1-42.666667-42.410667l-0.810666-383.978666a42.666667 42.666667 0 0 1 42.026666-42.666667l3.157334-0.064c5.226667-0.042667 11.797333-0.042667 19.626666 0 91.946667 0.768 170.88-86.698667 170.709334-170.944-0.149333-86.741333 39.786667-126.762667 106.453333-127.573333 62.250667-0.746667 106.602667 59.605333 107.349333 149.12 0.213333 26.602667-6.293333 73.237333-14.506666 107.434666 6.186667 0 13.077333-0.042667 20.586666-0.085333z m-497.706666 63.232L213.333333 874.624A21.312 21.312 0 0 1 191.786667 896H149.525333A21.333333 21.333333 0 0 1 128 874.624l0.042667-426.581333A21.269333 21.269333 0 0 1 149.44 426.666667h41.984c11.669333 0 21.418667 9.578667 21.418667 21.376z"
              fill="#3D3D3D"
              p-id="4279"
            ></path>
          </svg>
          <span>{{ item.up }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
import { setComment, setLike, getAllComment, getUserOfId } from "../api/index";
export default {
  name: "comment",
  mixins: [mixin],
  //props(道具):组件传值
  props: [
    "playId", //歌曲或歌单id
    "type", //0歌曲 1歌单
  ],

  data() {
    return {
      textarea: "", //输入的评论
      commentList: [], //存放评论列表
      userPic: [], //用户头像
      userName: [], //用户名字
      comment: [],
    };
  },

  computed: {
    ...mapGetters(["id", "avator", "loginIn", "userId"]),
  },

  mounted() {
    this.getComment();
  },

  methods: {
    //提交评论
    postComment() {
      if (this.loginIn) {
        let params = new URLSearchParams();
        if (this.type == 0) {
          //歌单
          params.append("songId", this.playId);
        } else {
          params.append("songListId", this.playId);
        }
        params.append("userId", this.userId);
        params.append("type", this.type);
        params.append("content", this.textarea);
        setComment(params)
          .then((res) => {
            if (res.code == 1) {
              // notify(通知) element
              this.$notify({
                title: "评论成功",
                message: "",
                type: "success",
              });
              this.getComment();
              this.textarea = "";
            } else {
              this.$notify({
                title: "评论失败",
                message: "",
                type: "error",
              });
            }
          })
          .catch((err) => {
            this.$notify({
              title: "评论失败",
              message: "",
              type: "error",
            });
          });
      } else {
        alert("请先登录");
      }
    },

    //评论列表
    getComment() {
      getAllComment(this.type, this.playId)
        .then((res) => {
          this.commentList = res;
          // for (let item of res) {
          //   this.getUsers(item.userId);
          // }

          //map可以请求数组
          //这里的 results是数组 不能直接引用 需要遍历
          const promises = res.map((item) => getUserOfId(item.userId));
          Promise.all(promises).then((results) => {
            this.userPic = results.map((user) => user.avator);
            this.userName = results.map((user) => user.username);
          });
        })
        .catch((err) => {
          console.log(err + "评论列表");
          this.$notify({
            title: "评论加载失败",
            message: "",
            type: "error",
          });
        });
    },

    //获取用户的头像和名称
    // getUsers(id) {
    //   getUserOfId(id)
    //     .then((res) => {
    //       this.userPic.push(res.avator);
    //       this.userName.push(res.username);
    //     })
    //     .catch((err) => {
    //       console.log(err + "获取用户的头像和名称");
    //       this.$notify({
    //         title: "用户信息获取失败",
    //         message: "",
    //         type: "error",
    //       });
    //     });
    // },

    //点赞
    postUp(id, up, index) {
      if (this.loginIn) {
        if (this.$refs.up[index].style.fill != "red") {
          let params = new URLSearchParams();
          params.append("userId", id);
          params.append("up", up + 1);
          setLike(params)
            .then((res) => {
              if (res.code == 1) {
                this.$refs.up[index].style.fill = "red";
                this.getComment();
              }
            })
            .catch((err) => {
              console.log(err);
              this.$notify({
                title: "点赞失败",
                message: "",
                type: "error",
              });
            });
        } else {
          alert("您已经赞过该条评论了");
        }
      } else {
        alert("请先登录");
      }
    },

    //时间
    parseTime(isoString) {
      // 创建一个 Date 对象，并将 ISO 8601 格式的字符串转换为 Date 对象
      const date = new Date(isoString);

      // 将 Date 对象转换为本地时间字符串
      const localTimeString = date.toLocaleString();

      // 使用字符串操作方法提取所需的时间格式
      //join(" ")将数组中的元素按指定的分隔符（在这里是空格）连接
      const formattedTime = localTimeString.split(" ").slice(0, 3).join(" ");

      return formattedTime;
    },
  },
};
</script>

<style>
@import "../assets/css/comment.css";
</style>
