package com.abhi.controller;

import com.abhi.model.UserEntity;
import com.abhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UsersController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<?> getUsersList() {
        List<UserEntity> usersList = userService.getUsers();
        if(usersList.size()<=0) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(usersList));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            userService.addUser(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PutMapping("/editUser")
    public ResponseEntity<?> updateUser(@ModelAttribute("userAttribute") @Validated UserEntity userEntity) {
    	try {
			userService.addUser(userEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") int userId) {
    	try {
			userService.deleteUser(userId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
}
