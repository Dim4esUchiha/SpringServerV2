package org.dim4es.springserver.services;

import org.dim4es.springserver.models.User;
import org.dim4es.springserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(User user){
        Optional<User> optionalUser = userRepository.findById(user.getId());

        if(!optionalUser.isPresent()){
            throw new IllegalStateException("User with this id not found!");
        }

        if(user.getEmail() != null){
            optionalUser.get().setEmail(user.getEmail());
        }

        if(user.getNickname() != null){
            optionalUser.get().setNickname(user.getNickname());
        }

        if(user.getPassword() != null){
            optionalUser.get().setPassword(user.getPassword());
        }

        userRepository.save(optionalUser.get());
    }

    public void deleteUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){
            throw new IllegalStateException("User with this id not found!");
        }

        userRepository.delete(optionalUser.get());

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByNickname(username);
    }
}
