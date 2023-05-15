package org.dim4es.springserver.services.geolocation;

import org.dim4es.springserver.dto.LocationUpdateDto;
import org.dim4es.springserver.services.exception.EntityNotFoundException;

public interface GeolocationService {

    void updateUserLocation(Long userId, LocationUpdateDto locationUpdateDto) throws EntityNotFoundException;
}
