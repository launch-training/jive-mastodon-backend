package com.accenture.jive.mastodonbackend.controllers.dtos;

import java.time.LocalDateTime;

public class PostDtoOutput {

    private String uuid;
    private String name;
    private LocalDateTime mastodonDate;
    private String mastodonUrl;
    private String icon;
    private String postText;

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
