package com.accenture.jive.mastodonbackend.persistence.entities;

import jakarta.persistence.*;

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
    @Column(name = "icon_url")
    private String icon;
    @Column(name = "description")
    private String postText;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
