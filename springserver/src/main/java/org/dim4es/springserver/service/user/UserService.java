package org.dim4es.springserver.service.user;

import org.dim4es.springserver.dto.UserNearbyDto;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.service.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User findUserById(Long id) throws EntityNotFoundException;

    List<UserNearbyDto> findUsersByFilterValues(User user, int radius, String country);

    void addUser(User user);

    Optional<User> findByUsername(String username);

    List<User> findByCountry(String countryName);

    void updateUserStatus(User user, UserStatus newStatus);
}
