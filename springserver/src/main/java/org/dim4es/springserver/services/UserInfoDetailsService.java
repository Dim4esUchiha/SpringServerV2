package org.dim4es.springserver.services;

import org.dim4es.springserver.models.User;
import org.dim4es.springserver.repositories.UserRepository;
import org.dim4es.springserver.security.UserInfoDetails;
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
        Optional<User> user = userRepository.findByNickname(nickname);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserInfoDetails(user.get());
    }
}
