package com.accenture.jive.mastodonbackend.persistence.repositories;

import com.accenture.jive.mastodonbackend.persistence.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {


}