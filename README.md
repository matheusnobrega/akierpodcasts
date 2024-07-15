# Podcast RSS Parser

Esta aplicação é responsável por analisar um feed RSS de podcasts e armazenar os episódios em um banco de dados. Ela é construída usando Java, Spring Boot e JPA.

## Funcionalidades

- Parsing de feeds RSS de podcasts
- Armazenamento de episódios de podcast em um banco de dados relacional
- Consulta de episódios e podcasts

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Postgres
- XML (para parsing do feed RSS)

## Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/akier/akierpodcasts/
│   │       ├── entities/
│   │       │   ├── Episode.java
│   │       │   └── Podcast.java
│   │       ├── repositories/
│   │       │   └── PodcastRepository.java
│   │       ├── service/
│   │       │   └── PodcastService.java
│   │       ├── controller/
│   │       │   └── PodcastController.java
│   │       └── RssParser.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── com/akier/akierpodcasts/
            └── PodcastServiceTests.java
