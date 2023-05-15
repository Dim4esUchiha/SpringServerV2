package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.models.User;
import org.dim4es.springserver.services.UserService;
import org.dim4es.springserver.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
