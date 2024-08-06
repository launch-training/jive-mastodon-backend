package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.controllers.mappers.CityMapper;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import com.accenture.jive.mastodonbackend.persistence.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    CityMapper cityMapper;

    @GetMapping("/cities")
    public ResponseEntity<List<CityDtoOutput>> readAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDtoOutput> dtos = cityMapper.toDtosOutput(cities);
        return ResponseEntity.ok(dtos);
    }

}
