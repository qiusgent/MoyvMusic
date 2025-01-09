package com.moyv.music.service;

import com.moyv.music.domain.Rank;

import java.util.List;

public interface RankService {
    /*增加*/
    public boolean insert(Rank rank);

    /*删除*/
//    public int delete(Integer id);

    /*查询总分*/
    public int selectScoreSum(Integer songListId);

    /*查总评分人数*/
    public int selectRankNum(Integer songListId);

    //计算评价分
    public int rankOfSongListId(Integer songListId);
}
