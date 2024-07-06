package com.akier.akierpodcasts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.akier.akierpodcasts.entities.Podcast;
import com.akier.akierpodcasts.repositories.PodcastRepository;

@Service
public class ScheduledTasks {
    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private PodcastRepository podcastRepository;

    @Scheduled(fixedRate = 3600000) 
    public void updateAllPodcasts() {
        List<Podcast> podcasts = podcastRepository.findAll();
        for (Podcast podcast : podcasts) {
            episodeService.saveEpisodeRss(podcast.getRssFeedUrl());
        }
    }
}
