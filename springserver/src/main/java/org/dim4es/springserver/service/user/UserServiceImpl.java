package org.dim4es.springserver.service.user;

import org.dim4es.springserver.common.Utils;
import org.dim4es.springserver.dto.UserNearbyDto;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.repository.jpa.UserPrivateChatRepository;
import org.dim4es.springserver.repository.jpa.UserRepository;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPrivateChatRepository userPrivateChatRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserPrivateChatRepository userPrivateChatRepository) {
        this.userRepository = userRepository;
        this.userPrivateChatRepository = userPrivateChatRepository;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Unable to find user by id = " + id));
    }

    @Override
    public List<UserNearbyDto> findUsersByFilterValues(User user, int radius, String country) {
        String locationToCompare = user.getLastLocation();
        String[] coordinates = Utils.parseCoordinates(locationToCompare);
        double latitudeToCompare = Double.parseDouble(coordinates[0]);
        double longitudeToCompare = Double.parseDouble(coordinates[1]);
        List<User> users;
        if (country != null) {
            users = findByCountry(country);
        } else {
            users = getAllUsers();
        }
        List<UserPrivateChat> userChats = userPrivateChatRepository.findAllUserChats(user.getId());
        List<UserNearbyDto> nearFoundedUsers = new ArrayList<>();
        for (User u : users) {
            boolean hasChatWithUser = userChats.stream()
                    .anyMatch(uc -> uc.getAnotherUser().getId().equals(u.getId()));

            if (!u.getId().equals(user.getId()) && !hasChatWithUser) {
                String location = u.getLastLocation();
                String[] coordinatesOfLocation = Utils.parseCoordinates(location);
                double latitude = Double.parseDouble(coordinatesOfLocation[0]);
                double longitude = Double.parseDouble(coordinatesOfLocation[1]);
                long distance = (long) haversine(latitudeToCompare, longitudeToCompare, latitude, longitude);
                if (distance <= radius) {
                    nearFoundedUsers.add(new UserNearbyDto(u.getId(), u.getUsername(), distance));
                }
            }
        }
        return nearFoundedUsers;
    }

    @Override
    public void addUser(User user){
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findByCountry(String countryName) {
        return userRepository.findByCountry(countryName);
    }

    @Override
    public void updateUserStatus(User user, UserStatus newStatus) {
        Instant timeUpdate = Instant.now();
        user.setStatus(newStatus);
        user.setLastStatusUpdate(timeUpdate);
        userRepository.save(user);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
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
