package com.accenture.jive.mastodonbackend.controllers.dtos;

import java.util.List;

public class PageCityDto {

    private Integer pageNumber;
    private Integer totalPages;
    private List<CityDtoOutput> data;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<CityDtoOutput> getData() {
        return data;
    }

    public void setData(List<CityDtoOutput> data) {
        this.data = data;
    }
}
