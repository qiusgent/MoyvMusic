package com.moyv.music.dao;

import com.moyv.music.domain.Rank;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankMapper {
    /*增加*/
    public int insert(Rank rank);

    /*删除*/
//    public int delete(Integer id);

    /*查询总分*/
    public int selectScoreSum(Integer songListId);

    /*查总评分人数*/
    public int selectRankNum(Integer songListId);

}
