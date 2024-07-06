package com.akier.akierpodcasts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akier.akierpodcasts.entities.Episode;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long>{
    Optional<Episode> findByPodcastIdAndTitle(Long podcastId, String title);

}
