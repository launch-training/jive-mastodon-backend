package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoActiveInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoInput;
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
            ResponseEntity<List<CityDtoOutput>> result = cityController.readAllCities(0);
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

            ResponseEntity<List<CityDtoOutput>> result = cityController.readAllCities(0);
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            List<CityDtoOutput> resultBody = result.getBody();
            assertNotNull(resultBody);
            assertEquals(2, resultBody.size());
            //todo: auch den Inhalt testen
        }
    }

    @Test
    void testCreateCityNew() {
        CityDtoInput dto1 = createDtoInput("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));
        ResponseEntity<CityDtoOutput> result = cityController.createCity(dto1);
        HttpStatusCode actualStatusCode = result.getStatusCode();
        HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(201);
        assertEquals(expectedStatusCode, actualStatusCode);

        CityDtoOutput createdCity = result.getBody();
        assertEquals(dto1.getName(), createdCity.getName());
    }

    @Test
    void testCreateCityExisting() {
        CityDtoInput dto1 = createDtoInput("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));
        CityDtoInput dto2 = createDtoInput("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10.00));
        {
            ResponseEntity<CityDtoOutput> result = cityController.createCity(dto1);
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(201);
            assertEquals(expectedStatusCode, actualStatusCode);
            CityDtoOutput createdCity = result.getBody();
            assertEquals(dto1.getName(), createdCity.getName());
        }
        {
            ResponseEntity<CityDtoOutput> result = cityController.createCity(dto2);
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            CityDtoOutput createdCity = result.getBody();
            assertEquals(dto2.getName(), createdCity.getName());
        }
    }

    @Test
    void updateCity() {
        City city1 = createCity("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));

        {
            ResponseEntity<CityDtoOutput> result = cityController.updateCity(city1.getGuid(), createDtoActiveInput(false));
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            assertFalse(result.getBody().isActive());
        }
        {
            ResponseEntity<CityDtoOutput> result = cityController.updateCity(city1.getGuid(), createDtoActiveInput(true));
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            assertTrue(result.getBody().isActive());
        }
        {
            ResponseEntity<CityDtoOutput> result = cityController.updateCity("9c54d78c-9740-48c7-bf39-e89443e9d316", createDtoActiveInput(false));
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(404);
            assertEquals(expectedStatusCode, actualStatusCode);
        }

    }

    private CityDtoActiveInput createDtoActiveInput(boolean active) {
        CityDtoActiveInput cityDtoActiveInput = new CityDtoActiveInput();
        cityDtoActiveInput.setActive(active);
        return cityDtoActiveInput;
    }

    private CityDtoInput createDtoInput(String name, BigDecimal latitude, BigDecimal longitude) {
        CityDtoInput dto = new CityDtoInput();
        dto.setName(name);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);
        return dto;
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