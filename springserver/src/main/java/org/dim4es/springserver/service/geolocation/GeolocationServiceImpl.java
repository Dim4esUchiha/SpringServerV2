package org.dim4es.springserver.service.geolocation;

import org.dim4es.springserver.common.Utils;
import org.dim4es.springserver.dto.LocationUpdateDto;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.repository.jpa.UserRepository;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GeolocationServiceImpl implements GeolocationService {

    private final UserRepository userRepository;

    @Autowired
    public GeolocationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUserLocation(Long userId, LocationUpdateDto dto) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Unable to find entity by id = " + userId));

        String coordinates = Utils.concatenateCoordinates(dto.getLatitude(),
                dto.getLongitude());
        user.setLastLocation(coordinates);
        user.setLastLocationUpdate(Instant.ofEpochMilli(dto.getTimestamp()));
        userRepository.save(user);
    }
}
