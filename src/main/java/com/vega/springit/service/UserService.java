package com.vega.springit.service;

import com.vega.springit.domain.AppUser;
import com.vega.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final RoleService roleService;

    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        this.encoder = new BCryptPasswordEncoder();
    }

    public AppUser register(AppUser user) {

        // take the password from the form and encode
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);

        // confirm password
        user.setConfirmPassword(secret);

        // assign a role to this user
        user.addRole((roleService.findByName("ROLE_USER")));

        // set an activation code
        user.setActivationCode(UUID.randomUUID().toString());

        // disable the user
        user.setEnabled(false);

        // save user
        save(user);

        // send activation email
        sendActivationEmail(user);

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

    public void sendActivationEmail(AppUser user) {

        mailService.sendActivationEmail(user);

    }

    public void sendWelcomeEmail(AppUser user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<AppUser> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }

}
