package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.LocationUpdateDto;
import org.dim4es.springserver.security.UserInfoDetails;
import org.dim4es.springserver.services.exception.EntityNotFoundException;
import org.dim4es.springserver.services.geolocation.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geolocation")
public class GeoLocationController {
    
    private final GeolocationService geolocationService;

    @Autowired
    public GeoLocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @PostMapping
    public ResponseEntity<Void> updateLocation(@AuthenticationPrincipal UserInfoDetails userDetails,
                                               @RequestBody LocationUpdateDto locationUpdate) throws EntityNotFoundException {
        geolocationService.updateUserLocation(userDetails.getUser().getId(), locationUpdate);
        return ResponseEntity.ok().build();
    }
}
