package com.akier.akierpodcasts.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akier.akierpodcasts.entities.Podcast;
import com.akier.akierpodcasts.repositories.PodcastRepository;

@Service
public class PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    public Podcast savePodcast(Podcast podcast) {
        return podcastRepository.save(podcast);
    }

    public Optional<Podcast> findByName(String name) {
        return podcastRepository.findByName(name);
    }

}
