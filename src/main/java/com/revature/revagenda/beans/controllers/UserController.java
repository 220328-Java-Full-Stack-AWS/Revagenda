package com.revature.revagenda.beans.controllers;

import com.revature.revagenda.beans.services.UserService;
import com.revature.revagenda.dtos.AuthDto;
import com.revature.revagenda.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get all users
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //get user by username
    @GetMapping("/{usernameOrId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable String usernameOrId, @RequestHeader("mode") String mode) throws Exception {
        switch(mode) {
            case "username":
                return userService.getUserByUsername(usernameOrId);
            case "id":
                return userService.getUserById(Integer.parseInt(usernameOrId));
            default:
                throw new Exception("That's not a valid mode");
                //TODO: Make this better
        }
    }

    //post a new user - auto generate the ID
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public User persistNewUser(@RequestBody User newUser) {
        return userService.save(newUser);
    }

    @GetMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public User authorizeUSer(@RequestBody AuthDto authDto) throws Exception {
        return userService.authenticateUser(authDto);
        //TODO: ResponseEntity<User> use this object to send back a different response for unauthorized
    }

    //put (update) an existing user (based on id)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {
        //TODO: THIS IS BAD! Doesn't work. Cause: updating an already persistent object.
        //Need to refactor either save() repo method or create a new one to handle updating a persistent object.
        return userService.save(user);
    }



    //delete user by id
    //TODO: add delete method.
}
