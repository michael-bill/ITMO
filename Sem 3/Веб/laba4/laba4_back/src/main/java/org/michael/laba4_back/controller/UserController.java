package org.michael.laba4_back.controller;

import org.michael.laba4_back.model.AuthToken;
import org.michael.laba4_back.model.User;
import org.michael.laba4_back.repository.AuthTokenRepository;
import org.michael.laba4_back.repository.UserRepository;
import static org.michael.laba4_back.Utils.getRandomHash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static org.michael.laba4_back.Utils.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @PostMapping(value = "/user-reg", produces = "application/json")
    public String addUser(@RequestParam String login, @RequestParam String pass) throws NoSuchAlgorithmException {
        int LOGIN_LENGTH = 4;
        int PASS_LENGTH = 8;
        if (login.length() < LOGIN_LENGTH) {
            return getMessage("Login needs to be at least " + LOGIN_LENGTH + " characters");
        } else if (pass.length() < PASS_LENGTH) {
            return getMessage("Password needs to be at least " + PASS_LENGTH + " characters");
        } else if (userRepository.findByLogin(login) != null) {
            return getMessage("User with this login already exists");
        }
        String hashEdPass = sha256(pass);
        userRepository.save(new User(login, hashEdPass));
        return getMessage("User created");
    }

    @GetMapping(value = "/user-auth", produces = "application/json")
    public String authUser(@RequestParam String login, @RequestParam String pass) throws NoSuchAlgorithmException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            return getMessage("User not found");
        }
        String hashEdPass = sha256(pass);
        if (!hashEdPass.equals(user.getPass())) {
            return getMessage("Wrong password");
        }
        if (authTokenRepository.findByUser(user) != null) {
            authTokenRepository.deleteByUserId(user.getId());
        }
        String token = getRandomHash();
        authTokenRepository.save(new AuthToken(user, token, LocalDateTime.now()));
        return getMessage("token", token);
    }

    @GetMapping(value = "/user-logout", produces = "application/json")
    public String logoutUser(@RequestParam String authToken) {
        AuthToken token = authTokenRepository.findByToken(authToken);
        if (token == null) {
            return getMessage("Wrong token");
        }
        authTokenRepository.deleteByUserId(token.getUser().getId());
        return getMessage("User logged out");
    }
}
