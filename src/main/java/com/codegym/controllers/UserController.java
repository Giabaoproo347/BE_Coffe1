package com.codegym.controllers;

import com.codegym.models.user.User;
import com.codegym.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> listAllUser() {
        List<User> users = (List<User>) userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Optional<User>>(user, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> currentUser = userService.findById(id);

        if (!currentUser.isPresent()) {
            return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
        }

        currentUser.get().setAddress(user.getAddress());
        currentUser.get().setEmail(user.getEmail());
        currentUser.get().setId(user.getId());
        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setPhone(user.getPhone());
        currentUser.get().setRoles(user.getRoles());
        currentUser.get().setUsername(user.getUsername());
        userService.save(user);
        return new ResponseEntity<Optional<User>>(currentUser, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
