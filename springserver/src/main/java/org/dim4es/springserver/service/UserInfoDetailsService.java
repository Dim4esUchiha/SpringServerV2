package org.dim4es.springserver.service;

import org.dim4es.springserver.model.User;
import org.dim4es.springserver.repository.jpa.UserRepository;
import org.dim4es.springserver.web.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserInfoDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(nickname);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserInfoDetails(user.get());
    }
}
