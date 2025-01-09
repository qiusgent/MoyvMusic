package com.moyv.music.service.impl;

import com.moyv.music.dao.SingerMapper;
import com.moyv.music.domain.Singer;
import com.moyv.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;

    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }

    @Override
    public Singer findById(Integer id) {
        return singerMapper.findById(id);
    }

    @Override
    public List<Singer> findByName(String name) {
        return singerMapper.findByName(name);
    }

    @Override
    public List<Singer> allSinger() {
        return singerMapper.allSinger();
    }

    @Override
    public List<Singer> singerOfName(String name) {
        return singerMapper.singerOfName(name);
    }

    @Override
    public List<Singer> singerOfSex(Integer sex) {
        return singerMapper.singerOfSex(sex);
    }
}
