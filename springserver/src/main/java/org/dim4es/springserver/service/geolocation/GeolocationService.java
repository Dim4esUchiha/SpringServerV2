package org.dim4es.springserver.service.geolocation;

import org.dim4es.springserver.dto.LocationUpdateDto;
import org.dim4es.springserver.service.exception.EntityNotFoundException;

public interface GeolocationService {

    void updateUserLocation(Long userId, LocationUpdateDto locationUpdateDto) throws EntityNotFoundException;
}
