package com.moyv.music.service.impl;

import com.moyv.music.dao.ListSongMapper;
import com.moyv.music.domain.ListSong;
import com.moyv.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong)>0;
    }

    @Override
    public boolean delete(Integer songId,Integer songListId) {
        return listSongMapper.delete(songId,songListId)>0;
    }

    @Override
    public ListSong findBySongAndSongList(Integer songId, Integer songListId) {
        return listSongMapper.findBySongAndSongList(songId,songListId);
    }

    @Override
    public ListSong findById(Integer id) {
        return listSongMapper.findById(id);
    }

    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    @Override
    public List<ListSong> listSongOfSongList(Integer songListId) {
        return listSongMapper.listSongOfSongList(songListId);
    }
}
