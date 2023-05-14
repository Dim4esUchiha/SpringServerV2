package org.dim4es.springserver.repositories;

import org.dim4es.springserver.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City getCityByCityName(String cityName);
}
