package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoActiveInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.controllers.dtos.PageCityDto;
import com.accenture.jive.mastodonbackend.controllers.mappers.CityMapper;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import com.accenture.jive.mastodonbackend.persistence.repositories.CityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    @Autowired
    CityController cityController;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CityMapper cityMapper;

    @BeforeEach
    void cleanUpDB() {
        cityRepository.deleteAll();
    }

    @Test
    void readAllCities() {
        {
            ResponseEntity<PageCityDto> result = cityController.readAllCities(0, 500);
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            PageCityDto pageCityDto = result.getBody();
            List<CityDtoOutput> resultBody = pageCityDto.getData();
            assertNotNull(resultBody);
            assertEquals(0, resultBody.size());
        }
        {
            City city1 = createCity("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));
            City city2 = createCity("Augsburg", BigDecimal.valueOf(48.37), BigDecimal.valueOf(10.88));

            ResponseEntity<PageCityDto> result = cityController.readAllCities(0, 500);
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            PageCityDto pageCityDto = result.getBody();
            List<CityDtoOutput> resultBody = pageCityDto.getData();
            assertNotNull(resultBody);
            assertEquals(2, resultBody.size());
            //todo: auch den Inhalt testen
        }
    }

    @Test
    void readAllCitiesPaginated(@Autowired MockMvc mvc) throws Exception {
        String json = mvc.perform(get("/cities").param("page", "0"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals("{\"pageNumber\":0,\"totalPages\":0,\"data\":[]}", json);

        City city1 = createCity("Hamburg", BigDecimal.valueOf(53.55), BigDecimal.valueOf(10));
        City city2 = createCity("Augsburg", BigDecimal.valueOf(48.37), BigDecimal.valueOf(10.88));
        City city3 = createCity("Augsburg", BigDecimal.valueOf(48.37), BigDecimal.valueOf(10.88));
        City city4 = createCity("Augsburg", BigDecimal.valueOf(48.37), BigDecimal.valueOf(10.88));

        String response = mvc.perform(get("/cities").param("page", "0"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        PageCityDto pageCityDto = objectMapper.readValue(response, PageCityDto.class);
        List<CityDtoOutput> data = pageCityDto.getData();
        assertEquals(2, data.size());

        //TODO: extend testing to check the correct objects are returnd
        List<City> expectedCities = new ArrayList<>();
        expectedCities.add(city1);
        expectedCities.add(city2);
        expectedCities.add(city3);
        expectedCities.add(city4);


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