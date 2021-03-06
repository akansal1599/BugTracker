package com.abhi.config;

import com.abhi.model.MessageEntity;
import com.abhi.model.TicketEntity;
import com.abhi.model.UserEntity;
import com.abhi.repository.MessageRepository;
import com.abhi.repository.RoleRepository;
import com.abhi.repository.TicketRepository;
import com.abhi.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class TestUsers implements InitializingBean {

    @Autowired
    private UserService userService;


    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        roleRepository.createRoleIfNotFound("ROLE_ADMIN");
        roleRepository.createRoleIfNotFound("ROLE_USER");


        UserEntity admin = new UserEntity("abhi", "kansal", "ak12345@gmail.com", "12345");


        UserEntity employee = new UserEntity("Usual", "Employee", "employee@email.com", "111111");

        userService.addUser(admin);
        admin.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"), roleRepository.findByName("ROLE_ADMIN"))));

        userService.updateUser(admin);

        userService.addUser(employee);

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setCreator(admin);
        ticketEntity.setHolder(employee);
        ticketEntity.setTitle("Test");

        ticketRepository.save(ticketEntity);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText("bug testing");
        messageEntity.setAuthor(admin);
        messageEntity.setRecipient(employee);
        messageEntity.setTicket(ticketEntity);

        messageRepository.save(messageEntity);

    }
}
