package com.moyv.music.service.impl;

import com.moyv.music.dao.SongListMapper;
import com.moyv.music.domain.SongList;
import com.moyv.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList)>0;
    }

    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    @Override
    public SongList findById(Integer id) {
        return songListMapper.findById(id);
    }

    @Override
    public List<SongList> allSongList() {
        return songListMapper.allSongList();
    }

    @Override
    public List<SongList> songListOfTitle(String title) {
        return songListMapper.songListOfTitle(title);
    }

    @Override
    public List<SongList> likeTitle(String title) {
        return songListMapper.likeTitle(title);
    }

    @Override
    public List<SongList> likeStyle(String style) {
        return songListMapper.likeStyle(style);
    }
}
