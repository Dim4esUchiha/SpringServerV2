package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.UserNearbyDto;
import org.dim4es.springserver.service.user.UserService;
import org.dim4es.springserver.web.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getUsersByLocation")
    public ResponseEntity<List<UserNearbyDto>> getUsersByFilterValues(@AuthenticationPrincipal UserInfoDetails details,
                                                                      @RequestParam int radius,
                                                                      @RequestParam(required = false) String country) {
        return ResponseEntity.ok(userService.findUsersByFilterValues(details.getUser(), radius, country));
    }
}
