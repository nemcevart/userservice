package com.example.home.service;

import com.example.home.entity.User;
import com.example.home.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User createUser(User user) {
        Optional<User> dbUserOptional = getUserByUsername(user.getUserName());
        if (!dbUserOptional.isPresent()) {
            User newUser = new User(user.getUserName(), user.getFirstName(), user.getMiddleName(), user.getLastName());
            userRepository.save(newUser);

            return newUser;
        } else {
            throw new RuntimeException("User with this username already exists");
        }
    }

    public User updateUser(User user) {
        Optional<User> dbUserOptional = getUserById(user.getId());
        if (dbUserOptional.isPresent()) {
            User dbUser = dbUserOptional.get();

            dbUser.setUserName(user.getUserName());
            dbUser.setFirstName(user.getFirstName());
            dbUser.setMiddleName(user.getMiddleName());
            dbUser.setLastName(user.getLastName());

            userRepository.save(dbUser);

            return dbUser;
        } else {
            throw new RuntimeException("User with this ID doesnt exist");
        }
    }

    public void deleteUser(Long id) {
        Optional<User> dbUser = getUserById(id);
        if (dbUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with this ID doesnt exist");
        }
    }
}
