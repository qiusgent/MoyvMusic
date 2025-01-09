import { get, post } from "./http";

// 判断管理员是否登录成功
// export将模块中的变量、函数、类等 导出 ，以便在其他文件中使用
export const getLoginStatus = (params) => post(`admin/login`, params);

// 歌手-------------------------------------------------------------------------------
// 查询所有歌手
export const getAllSinger = () => post(`singer/allSinger`);

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

//用户查询
export const getUserOfId = (id) => get(`consumer/findById?id=${id}`);

// 收藏-------------------------------------------------------------------------------

//查询某个用户下的收藏列表
export const getCollectOfUserId = (userId) =>
  get(`collect/collectOfuserId?userId=${userId}`);

// 评论-------------------------------------------------------------------------------
//查询某个跟单下的评论列表
export const getCommentOfSongListId = (songListId) =>
  get(`comment/commentOfSongListId?songListId=${songListId}`);

// 删除评论
export const deleteComment = (id) => get(`comment/delete?id=${id}`);

//
