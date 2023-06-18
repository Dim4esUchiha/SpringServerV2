package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.City;
import org.dim4es.springserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT user FROM User user WHERE user.city = :city or user.city is null")
    List<User> findByCity(City city);

    @Query("SELECT u FROM User u WHERE u.country.name = :countryName")
    List<User> findByCountry(String countryName);
}
