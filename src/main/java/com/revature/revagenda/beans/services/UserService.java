package com.revature.revagenda.beans.services;

import com.revature.revagenda.beans.repositories.UserRepository;
import com.revature.revagenda.dtos.AuthDto;
import com.revature.revagenda.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public User getUserById(Integer id ) {
        return userRepository.getById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User authenticateUser(AuthDto authDto) throws Exception {
        User user = userRepository.getByUsername(authDto.getUsername());
        if(user != null && user.getPassword().equals(authDto.getPassword())) {
            return user;
        } else {
            throw new Exception("Unauthorized!");
            //TODO: Make this into a custom exception
        }
    }

    public User update(User user) {
        return userRepository.update(user);
    }


}
