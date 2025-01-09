<template>
  <div class="table">
    <div class="container">
      <div class="crumbs">
        <img src="../assets/img/gedan.png" />
        <div class="crumbs-txt">歌单评论信息</div>
      </div>
      <div class="handle-box">
        <el-button type="danger" size="mini" @click="delAll"
          >批量删除</el-button
        >

        <el-input
          v-model="select_word"
          size="mini"
          placeholder="请输入用户名"
          class="handle-input"
          style="margin-left: 5px"
        ></el-input>

        <el-button
          style="margin-left: 5px"
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌曲</el-button
        >
      </div>
    </div>

    <!-- selection-change当选择项发生变化时会触发该事件 -->
    <el-table
      size="mini"
      style="width: 100%"
      border
      height="66vh"
      :data="data"
      @selection-change="handleSclectionChange"
    >
      <el-table-column type="selection" width="40"></el-table-column>

      <el-table-column
        prop="username"
        label="用户名"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="content"
        label="评论内容"
        align="center"
      ></el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            plain
            @click="handleDelete(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="pageination">
      <!--layout(布局)  -->
      <el-pagination
        background
        layout="total,prev,pager,next,jumper"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :total="tableData.length"
        @current-change="handleCurrent"
      ></el-pagination>
    </div>

    <!-- 删除提示框 -->
    <el-dialog
      title="删除评论"
      :visible.sync="delVisible"
      width="300px"
      center
      style="margin-top: 8%"
    >
      <div class="del-dialog-cnt" align="center">
        删除不可恢复，是否确定删除？
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="delVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="delRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from "../mixins";
import {
  getCommentOfSongListId,
  getUserOfId,
  deleteComment,
} from "../api/index";

export default {
  name: "comment-page",
  mixins: [mixin],
  data() {
    return {
      tableData: [],
      tempDate: [],
      multipleSelection: [], //打勾框
      editVisible: false,
      delVisible: false, //弹框
      select_word: "",
      idx: -1, //当前选项

      pageSize: 13,
      currentPage: 1,
    };
  },

  computed: {
    // 计算当前搜索结果里的数据
    data() {
      // slice割[)
      return this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
  },

  watch: {
    select_word: function () {
      if (this.select_word === "") {
        this.tableData = this.tempDate;
      } else {
        this.tableData = [];
        for (let item of this.tempDate) {
          if (item.username.includes(this.select_word)) {
            this.tableData.push(item);
          }
        }
      }
    },
  },
  created() {
    this.getData();
  },
  methods: {
    // 获取评论
    getData() {
      this.tableData = [];
      this.tempDate = [];
      getCommentOfSongListId(this.$route.query.id).then((res) => {
        for (let item of res) {
          this.getUsers(item.userId, item);
        }
      });
    },
    //获取用户名
    getUsers(id, item) {
      // console.log(item);
      getUserOfId(id)
        .then((res) => {
          // console.log(res);
          let o = item;
          o.username = res.username;
          this.tableData.push(o);
          this.tempDate.push(o);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 确定删除
    delRow() {
      console.log(this.idx);
      deleteComment(this.idx)
        .then((res) => {
          if (res) {
            this.getData();
            this.$notify({
              title: "删除成功",
              message: "",
              type: "success",
            });
          } else {
            this.$notify({
              title: "删除失败",
              message: "",
              type: "error",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
      this.delVisible = false;
    },

    //获取当前页
    handleCurrent(val) {
      this.currentPage = val;
    },
  },
};
</script>

<style>
.handle-input {
  width: 300px;
  display: inline-block;
}
.pageination {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 55px;
}
</style>
