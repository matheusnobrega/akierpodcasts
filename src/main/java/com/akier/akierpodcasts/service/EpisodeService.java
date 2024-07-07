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
import org.w3c.dom.Element;
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
        List<Episode> episodes = parseRss(rssContent, urlRss);

        for (Episode episode : episodes) {
            if (!episodeRepository.findByPodcastIdAndTitle(episode.getPodcast().getId(), episode.getTitle()).isPresent()) {
                episodeRepository.save(episode);
            }
        }
    }
    
    public List<Episode> parseRss(String rssContent, String urlRss) {
        List<Episode> episodes = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(rssContent)));

            NodeList itemNodes = doc.getElementsByTagName("item");

            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemElement = (Element) itemNodes.item(i);

                String title = getElementTextContent(itemElement, "title");
                String description = getElementTextContent(itemElement, "description");
                String audioUrl = getElementTextContent(itemElement, "enclosure", "url");
                String pubDate = getElementTextContent(itemElement, "pubDate");

                // String title = doc.getElementsByTagName("title").item(i).getTextContent();
                // String description = doc.getElementsByTagName("description").item(i).getTextContent();
                // String audioUrl = doc.getElementsByTagName("enclosure").item(i).getAttributes().getNamedItem("url").getTextContent();
                // String pubDate = doc.getElementsByTagName("pubDate").item(i).getTextContent();
                
                LocalDateTime publishedAt = LocalDateTime.parse(pubDate, DateTimeFormatter.RFC_1123_DATE_TIME);
                LocalDateTime createdAt = LocalDateTime.now();

                Episode episode = new Episode();
                episode.setTitle(title);
                episode.setDescription(description);
                episode.setAudioUrl(audioUrl);
                episode.setPublishedAt(publishedAt);
                episode.setCreatedAt(createdAt);

                Podcast podcast = podcastRepository.findByRssFeedUrl(urlRss);

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


    private String getElementTextContent(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }
    
    private String getElementTextContent(Element parent, String tagName, String attributeName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getAttributes().getNamedItem(attributeName).getTextContent();
        }
        return null;
    }
}
