package com.akier.akierpodcasts.service;


import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.akier.akierpodcasts.entities.Episode;
import com.akier.akierpodcasts.entities.Podcast;
import com.akier.akierpodcasts.repositories.EpisodeRepository;
import com.akier.akierpodcasts.repositories.PodcastRepository;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private PodcastRepository podcastRepository;

    private RestTemplate restTemplate = new RestTemplate();
    
    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public void saveEpisodeRss(String urlRss) {
        String rssContent = restTemplate.getForObject(urlRss, String.class);

        List<Episode> episodes = parseRss(rssContent);

        for (Episode episode : episodes) {
            if (!episodeRepository.findByPodcastIdAndTitle(episode.getPodcast().getId(), episode.getTitle()).isPresent()) {
                episodeRepository.save(episode);
            }
        }
    }
    
    public List<Episode> parseRss(String rssContent) {
        List<Episode> episodes = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(rssContent)));

            NodeList itemNodes = doc.getElementsByTagName("item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                String title = doc.getElementsByTagName("title").item(i).getTextContent();
                String description = doc.getElementsByTagName("description").item(i).getTextContent();
                String audioUrl = doc.getElementsByTagName("enclosure").item(i).getAttributes().getNamedItem("url").getTextContent();
                String pubDate = doc.getElementsByTagName("pubDate").item(i).getTextContent();
                LocalDateTime publishedAt = LocalDateTime.parse(pubDate, DateTimeFormatter.RFC_1123_DATE_TIME);

                Episode episode = new Episode();
                episode.setTitle(title);
                episode.setDescription(description);
                episode.setAudioUrl(audioUrl);
                episode.setPublishedAt(publishedAt);

                Podcast podcast = podcastRepository.findByRssFeedUrl(rssContent);

                if (podcast != null) {
                    episode.setPodcast(podcast);
                    episodes.add(episode);
                }
            }  

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }
}
