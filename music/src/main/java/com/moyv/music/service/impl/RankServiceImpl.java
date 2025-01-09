package com.moyv.music.service.impl;

import com.moyv.music.dao.RankMapper;
import com.moyv.music.domain.Rank;
import com.moyv.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankMapper rankMapper;

    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    @Override
    public int selectScoreSum(Integer songListId) {
        return rankMapper.selectScoreSum(songListId);
    }

    @Override
    public int selectRankNum(Integer songListId) {
        return rankMapper.selectRankNum(songListId);
    }

    @Override
    public int rankOfSongListId(Integer songListId) {
        int rankNum = rankMapper.selectRankNum(songListId);
        int rankSum = rankMapper.selectScoreSum(songListId);
        if(rankNum == 0){
            return 5;
        }
        return rankSum/rankNum;
    }
}
