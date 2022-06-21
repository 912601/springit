package com.vega.springit.service;

import com.vega.springit.domain.AppUser;
import com.vega.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser register(AppUser user) {
        return user;
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUsers(AppUser... users) {
        for(AppUser user: users) {
            logger.info("Saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }

}
