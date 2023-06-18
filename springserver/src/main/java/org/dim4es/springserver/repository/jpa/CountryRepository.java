package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(value = " select c from Country as c where LOWER(c.name) like LOWER(:searchToken)")
    List<Country> searchByName(String searchToken, Pageable pageable);
}
