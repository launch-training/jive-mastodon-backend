package com.accenture.jive.mastodonbackend.controllers.mappers;

import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoInput;
import com.accenture.jive.mastodonbackend.controllers.dtos.CityDtoOutput;
import com.accenture.jive.mastodonbackend.persistence.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "guid", target = "uuid")
    CityDtoOutput toDtoOutput(City city);
    List<CityDtoOutput> toDtosOutput(List<City> cities);

    @Mapping(target = "guid", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "saveDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "active", expression = "java(true)")
    City toEntity(CityDtoInput dto);

}
