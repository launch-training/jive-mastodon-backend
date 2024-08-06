package com.accenture.jive.mastodonbackend.persistence.entities;

import jakarta.persistence.*;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_history")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String guid;
    private LocalDateTime timestampWeatherRequest;
    private LocalDateTime timestampMastodonPosted;
    private String postLink;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime getTimestampWeatherRequest() {
        return timestampWeatherRequest;
    }

    public void setTimestampWeatherRequest(LocalDateTime timestampWeatherRequest) {
        this.timestampWeatherRequest = timestampWeatherRequest;
    }

    public LocalDateTime getTimestampMastodonPosted() {
        return timestampMastodonPosted;
    }

    public void setTimestampMastodonPosted(LocalDateTime timestampMastodonPosted) {
        this.timestampMastodonPosted = timestampMastodonPosted;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
