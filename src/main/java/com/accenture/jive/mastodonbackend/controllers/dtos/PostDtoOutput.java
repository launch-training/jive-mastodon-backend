package com.accenture.jive.mastodonbackend.controllers.dtos;

import java.time.LocalDateTime;

public class PostDtoOutput {

    private String uuid;
    private String name;
    private LocalDateTime mastodonDate;
    private String mastodonUrl;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getMastodonDate() {
        return mastodonDate;
    }

    public void setMastodonDate(LocalDateTime mastodonDate) {
        this.mastodonDate = mastodonDate;
    }

    public String getMastodonUrl() {
        return mastodonUrl;
    }

    public void setMastodonUrl(String mastodonUrl) {
        this.mastodonUrl = mastodonUrl;
    }
}
