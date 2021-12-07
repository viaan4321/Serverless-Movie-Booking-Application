package com.upgrad.user.controller;

import com.upgrad.user.dto.UserDTO;
import com.upgrad.user.entities.User;
import com.upgrad.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        User savedUser = userRepo.save(user);

        UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);

        return new ResponseEntity(savedUserDTO, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserByID(@PathVariable(name = "id") String id) {
        Optional<User> user = userRepo.findById(id);

        if(user.isPresent()) {
            UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path="/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers() {
        Iterable<User> allUsers = userRepo.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();

        allUsers.forEach(usr -> userDTOList.add(modelMapper.map(usr, UserDTO.class)));

        return new ResponseEntity(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(path="/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserByID(@PathVariable(name = "id") String id) {
        userRepo.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
