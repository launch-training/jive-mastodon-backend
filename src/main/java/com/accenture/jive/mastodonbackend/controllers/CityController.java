package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoActiveInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.controllers.mappers.CityMapper;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import com.accenture.jive.mastodonbackend.persistence.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
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

    @PutMapping("/cities/{guid}")
    public ResponseEntity<CityDtoOutput> updateCity(@PathVariable String guid, @RequestBody CityDtoActiveInput cityDtoActiveInput) {
        Optional<City> optionalCity = cityRepository.findByGuid(guid);
        if (optionalCity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        City city = optionalCity.get();
        city.setActive(cityDtoActiveInput.isActive());
        City updatedCity = cityRepository.save(city);
        CityDtoOutput dtoOutput = cityMapper.toDtoOutput(updatedCity);
        return ResponseEntity.ok(dtoOutput);
    }

}
