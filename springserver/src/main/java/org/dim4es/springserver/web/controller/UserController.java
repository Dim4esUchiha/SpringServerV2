package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.models.User;
import org.dim4es.springserver.security.UserInfoDetails;
import org.dim4es.springserver.services.UserService;
import org.dim4es.springserver.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PutMapping(path = "/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user){
        user.setId(id);
        userService.updateUser(user);
    }

    @DeleteMapping(path ="/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }

    @GetMapping(path = "/getUsersByLocation")
    public List<String> getTopTenUsersByLocation(@AuthenticationPrincipal UserInfoDetails details, @RequestParam int radius) {
        String locationToCompare = details.getUser().getLastLocation();
        String[] coordinates = locationToCompare.split(" ");
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
        List<String> nearFoundedUsers = new ArrayList<>();
        for (User user : users) {
            String location = user.getLastLocation();
            String[] coordinatesOfLocation = location.split(" ");
            double latitude = Double.parseDouble(coordinatesOfLocation[0]);
            double longitude = Double.parseDouble(coordinatesOfLocation[1]);
            if (haversine(latitudeToCompare, longitudeToCompare, latitude, longitude) <= radius) {
                nearFoundedUsers.add(user.getNickname());
//                if(nearFoundedUsers.size() >= 10) {
//                    break;
//                }
            }
        }
        return nearFoundedUsers;
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
