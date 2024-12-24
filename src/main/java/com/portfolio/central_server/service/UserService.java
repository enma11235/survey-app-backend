package com.portfolio.central_server.service;

import com.portfolio.central_server.model.User;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.portfolio.central_server.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //SERVICE OPERATIONS
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
