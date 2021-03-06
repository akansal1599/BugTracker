package com.abhi.service;

import com.abhi.model.UserEntity;
import com.abhi.repository.RoleRepository;
import com.abhi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void addUser(UserEntity userEntity) {
        

        boolean userExists = userRepository.findByEmail(userEntity.getEmail())!=null;
        if (userExists) {
			throw new IllegalStateException("email already taken");
	    }
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserEntity getUser(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity findByUsername(String email) {
        return userRepository.findByEmail(email);
    }


}
