package com.akier.akierpodcasts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akier.akierpodcasts.entities.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long>{

}
