package com.moyv.music.service.impl;

import com.moyv.music.dao.SongMapper;
import com.moyv.music.domain.Song;
import com.moyv.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    @Override
    public Song findById(Integer id) {
        return songMapper.findById(id);
    }

    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    @Override
    public List<Song> songOfName(String name) {
        return songMapper.songOfName(name);
    }

    @Override
    public List<Song> likesongOfName(String name) {
        return songMapper.likesongOfName("%"+name+"%");
    }

    @Override
    public List<Song> songOfSingerId(Integer singerId) {
        return songMapper.songOfSingerId(singerId);
    }
}
