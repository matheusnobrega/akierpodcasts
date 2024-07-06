package com.akier.akierpodcasts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akier.akierpodcasts.entities.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long>{
    Optional<Episode> findByPodcastIdAndTitle(Long podcastId, String title);

}
