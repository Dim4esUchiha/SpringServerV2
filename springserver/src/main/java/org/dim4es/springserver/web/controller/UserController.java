package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.common.Utils;
import org.dim4es.springserver.dto.UserDto;
import org.dim4es.springserver.models.User;
import org.dim4es.springserver.security.UserInfoDetails;
import org.dim4es.springserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getUsersByLocation")
    public ResponseEntity<List<UserDto>> getTopTenUsersByLocation(@AuthenticationPrincipal UserInfoDetails details, @RequestParam int radius) {
        String locationToCompare = details.getUser().getLastLocation();
        String[] coordinates = Utils.parseCoordinates(locationToCompare);
        double latitudeToCompare = Double.parseDouble(coordinates[0]);
        double longitudeToCompare = Double.parseDouble(coordinates[1]);
        List<User> users;
        if (details.getUser().getCity() != null) {
            users = userService.findUsersByCity(details.getUser().getCity());
            if (users.isEmpty()) {
                users = userService.getAllUsers();
            }
        } else {
            users = userService.getAllUsers();
        }
        List<UserDto> nearFoundedUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.getId().equals(details.getUser().getId())) {
                String location = user.getLastLocation();
                String[] coordinatesOfLocation = location.split(" ");
                double latitude = Double.parseDouble(coordinatesOfLocation[0]);
                double longitude = Double.parseDouble(coordinatesOfLocation[1]);
                long distance = (long) haversine(latitudeToCompare, longitudeToCompare, latitude, longitude);
                if (distance <= radius) {
                    nearFoundedUsers.add(new UserDto(user.getId(), user.getNickname(), distance));
//                if(nearFoundedUsers.size() >= 10) {
//                    break;
//                }
                }
            }
        }
        return ResponseEntity.ok(nearFoundedUsers);
    }

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double earthRadius = 6371000.0;
        return c * earthRadius;
    }

}
