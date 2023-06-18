package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City getCityByCityName(String cityName);
}
