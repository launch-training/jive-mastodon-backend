package com.accenture.jive.mastodonbackend.controllers.dtos;

import java.math.BigDecimal;

public class CityDtoInput {

    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
