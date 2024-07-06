package com.akier.akierpodcasts.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akier.akierpodcasts.entities.Episode;
import com.akier.akierpodcasts.repositories.EpisodeRepository;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;
    
    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }
}
