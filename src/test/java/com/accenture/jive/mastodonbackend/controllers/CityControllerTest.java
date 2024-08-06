package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import com.accenture.jive.mastodonbackend.persistence.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CityControllerTest {

    @Autowired
    CityController cityController;
    @Autowired
    CityRepository cityRepository;

    @BeforeEach
    void cleanUpDB() {
        cityRepository.deleteAll();
    }

    @Test
    void readAllCities() {
        {
            ResponseEntity<List<CityDtoOutput>> result = cityController.readAllCities();
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            List<CityDtoOutput> resultBody = result.getBody();
            assertNotNull(resultBody);
            assertEquals(0, resultBody.size());
        }
        {
            City city1 = createCity("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));
            City city2 = createCity("Augsburg", BigDecimal.valueOf(48.37), BigDecimal.valueOf(10.88));

            ResponseEntity<List<CityDtoOutput>> result = cityController.readAllCities();
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            List<CityDtoOutput> resultBody = result.getBody();
            assertNotNull(resultBody);
            assertEquals(2, resultBody.size());
            //todo: auch den Inhalt testen
        }

    }

    private City createCity(String name, BigDecimal latitude, BigDecimal longitude) {
        City city = new City();
        city.setName(name);
        city.setLatitude(latitude);
        city.setLongitude(longitude);
        city.setActive(true);
        city.setSaveDate(LocalDateTime.now());
        city.setGuid(UUID.randomUUID().toString());
        cityRepository.save(city);
        return city;
    }


}