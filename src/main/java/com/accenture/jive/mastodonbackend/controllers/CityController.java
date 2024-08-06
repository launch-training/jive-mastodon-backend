package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.controllers.mappers.CityMapper;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import com.accenture.jive.mastodonbackend.persistence.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
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

    @PostMapping("/cities")
    public ResponseEntity<CityDtoOutput> createCity(@RequestBody CityDtoInput cityDtoInput) {
        City city = cityMapper.toEntity(cityDtoInput);
        city.setLatitude(city.getLatitude().setScale(2, RoundingMode.HALF_UP));
        city.setLongitude(city.getLongitude().setScale(2, RoundingMode.HALF_UP));
        List<City> existingCities = cityRepository.findAllByName(city.getName());
        for (City existingCity : existingCities) {
            if (city.equals(existingCity)) {
                CityDtoOutput dtoOutput = cityMapper.toDtoOutput(existingCity);
                return ResponseEntity.ok(dtoOutput);
            }
        }
        City savedCity = cityRepository.save(city);
        CityDtoOutput dtoOutput = cityMapper.toDtoOutput(savedCity);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoOutput);
    }

}
