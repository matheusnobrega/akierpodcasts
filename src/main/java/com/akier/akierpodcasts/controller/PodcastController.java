package com.akier.akierpodcasts.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akier.akierpodcasts.entities.Podcast;
import com.akier.akierpodcasts.service.PodcastService;

@RestController
@RequestMapping("/api/podcasts")
public class PodcastController {

    @Autowired
    private PodcastService podcastService;

    @PostMapping
    public ResponseEntity<Podcast> createPodcast(@RequestBody Podcast podcast) {
        Podcast savedPodcast = podcastService.savePodcast(podcast);
        return ResponseEntity.ok(savedPodcast);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Podcast> getPodcastByName(@PathVariable String name) {
        Optional<Podcast> podcast = podcastService.findByName(name);
        return podcast.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Podcast>> getAllPodcasts() {
        List<Podcast> podcasts = podcastService.findAll();
        return ResponseEntity.ok(podcasts);
    }
}
