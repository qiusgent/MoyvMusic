import Axios from "axios";
import { get, post } from "./http";
import song from "@/store/song";

// 判断管理员是否登录成功
// export将模块中的变量、函数、类等 导出 ，以便在其他文件中使用
export const getLoginStatus = (params) => post(`admin/login`, params);

// 歌手-------------------------------------------------------------------------------
// 查询所有歌手
export const getAllSinger = () => post(`singer/allSinger`);

//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/singerOfSex?sex=${sex}`);

// 添加歌手
export const setSinger = (params) => post(`singer/add`, params);

// 编辑歌手
export const updateSinger = (params) => post(`singer/update`, params);

// 删除歌手
export const delSinger = (id) => get(`singer/delete?id=${id}`);

//根据歌手名精确查找
export const FindBySingerName = (singerName) =>
  get(`singer/findByName?singerName=${singerName}`);

// 歌曲-------------------------------------------------------------------------------
// 根据歌手id查询歌曲
export const singerOfSingerId = (id) => get(`song/detail?singerId=${id}`);

// 编辑歌曲
export const updateSong = (params) => post(`song/update`, params);

// 删除歌曲
export const deleteSong = (id) => get(`song/delete?id=${id}`);

//歌曲id查询
export const songOfSongId = (id) => get(`song/songDetail?songId=${id}`);

// 根据歌曲名 获取歌曲对象
export const songOfSongName = (songName) =>
  get(`song/songOfSongName?songName=${songName}`);

// 根据歌曲名 模糊查询
export const likesongOfSongName = (keywords) =>
  get(`song/likesongOfSongName?songName=${keywords}`);

// 查询所有歌曲
export const getAllSong = () => post(`song/allSong`);

// 歌单-------------------------------------------------------------------------------
// 查询所有歌单
export const getAllSongList = () => post(`songList/allSongList`);

// 添加歌单
export const setSongList = (params) => post(`songList/add`, params);

// 编辑歌单
export const updateSongList = (params) => post(`songList/update`, params);

// 删除歌单
export const delSongList = (id) => get(`songList/delete?id=${id}`);

//模糊查询(根据标题)
export const getSongListOfLikeTitle = (keywords) =>
  get(`songList/likeTitle?title=${keywords}`);

// 歌单里的歌曲-------------------------------------------------------------------------------
// 根据歌单id查询歌曲
export const ListSongDetail = (id) => get(`listSong/detail?songListId=${id}`);

// 给歌单增加歌曲
export const ListSongAdd = (params) => post(`listSong/add`, params);

// 删除歌单歌曲
export const delListSong = (songId, songListId) =>
  get(`listSong/delete?songId=${songId}&songListId=${songListId}`);

// 用歌曲和歌单id 精确查询
export const findBySongAndSongList = (songId, songListId) =>
  get(
    `listSong/findBySongAndSongList?songId=${songId}&songListId=${songListId}`
  );

// 用户相关-------------------------------------------------------------------------------
// 查询所有用户
export const getAllConsumer = () => post(`consumer/allConsumer`);

// 添加用户
export const setConsumer = (params) => post(`consumer/add`, params);

// 编辑用户
export const updateConsumer = (params) => post(`consumer/update`, params);

// 删除用户
export const delConsumer = (id) => get(`consumer/delete?id=${id}`);

// 精确查询用户
export const getByUsername = (username) =>
  get(`consumer/getByUsername?username=${username}`);

//用户登录
export const loginConsumer = (params) => post(`consumer/login`, params);

//用户查询
export const getUserOfId = (id) => get(`consumer/findById?id=${id}`);

//更新用户信息
export const updateUserMsg = (params) => post(`consumer/update`, params);

//下载音乐
// Axios 是一个流行的 HTTP 客户端库，用于在浏览器和 Node.js 中发送请求
// responseType(响应类型)
// 部分设置了响应的数据类型为 blob（弄脏，一点/一滴）。Blob 是一种用于表示二进制数据的对象，通常用于处理文件下载或图像等二进制内容。
export const download = (url) =>
  Axios({
    method: "get",
    url: url,
    responseType: "blob",
  });

// 评价-------------------------------------------------------------------------------
// 提交评分
export const setRank = (params) => post(`rank/add`, params);

// 获取平均分
export const getAverage = (songListId) =>
  get(`rank/average?songListId=${songListId}`);

// 评论-------------------------------------------------------------------------------
//提交评论
export const setComment = (params) => post(`comment/add`, params);

//点赞
export const setLike = (params) => post(`comment/like`, params);

//返回当前歌单或歌曲的评论列表
export const getAllComment = (type, id) => {
  if (type == 0) {
    return get(`comment/commentOfSongId?songId=${id}`);
  } else {
    return get(`comment/commentOfSongListId?songListId=${id}`);
  }
};

// 收藏-------------------------------------------------------------------------------
//添加收藏
export const setCollect = (params) => post(`collect/add`, params);

//查询某个用户下的收藏列表
export const getCollectOfUserId = (userId) =>
  get(`collect/collectOfuserId?userId=${userId}`);

//删除收藏
export const deleteCollect = (userId, songId) =>
  get(`collect/delete?userId=${userId}&songId=${songId}`);
