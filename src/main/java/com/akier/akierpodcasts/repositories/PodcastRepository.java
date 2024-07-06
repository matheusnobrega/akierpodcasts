package com.akier.akierpodcasts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akier.akierpodcasts.entities.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Long>{
    Optional<Podcast> findByName(String name);
    Podcast findByRssFeedUrl(String urlFeedUrl);

}
