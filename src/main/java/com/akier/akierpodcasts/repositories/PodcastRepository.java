package com.akier.akierpodcasts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akier.akierpodcasts.entities.Podcast;

public interface PodcastRepository extends JpaRepository<Podcast, Long>{
    Optional<Podcast> findByName(String name);

}
