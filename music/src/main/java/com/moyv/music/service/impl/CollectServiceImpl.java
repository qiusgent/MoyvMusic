package com.moyv.music.service.impl;

import com.moyv.music.dao.CollectMapper;
import com.moyv.music.domain.Collect;
import com.moyv.music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    @Override
    public boolean delete(Integer userId,Integer songId) {
        return collectMapper.delete(userId, songId)>0;
    }

    @Override
    public List<Collect> allCollect() {
        return collectMapper.allCollect();
    }

    @Override
    public List<Collect> collectOfuserId(Integer userId) {
        return collectMapper.collectOfuserId(userId);
    }

    @Override
    public boolean exsistSongId(Integer userId, Integer songId) {
        return collectMapper.exsistSongId(userId,songId)>0;
    }
}
