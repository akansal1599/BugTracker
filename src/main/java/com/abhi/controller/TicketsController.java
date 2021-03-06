package com.abhi.controller;

import com.abhi.model.TicketEntity;
import com.abhi.model.UserEntity;
import com.abhi.model.converter.TicketConverter;
import com.abhi.model.requestModel.TicketRequest;
import com.abhi.service.TicketService;
import com.abhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketConverter ticketConverter;


    @GetMapping("/list")
    public ResponseEntity<?> getTicketsList(@RequestParam("userId") String userId) {
        if (userId == "") {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            int userNameId = userService.findByUsername(userName).getUserId();
            userId = String.valueOf(userNameId);
        }
        int id = Integer.parseInt(userId);

        List<TicketEntity> userTickets = ticketService.findUsersTickets(id);

        return ResponseEntity.of(Optional.of(userTickets));
    }


    @GetMapping("/{ticketId}")
    public ResponseEntity<?> showTicket(@PathVariable String ticketId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        int userId = userService.findByUsername(userName).getUserId();

        TicketEntity ticketEntity = ticketService.getTicket(Integer.parseInt(ticketId));

        if (ticketEntity.getCreator().getUserId() == userId
                || ticketEntity.getHolder().getUserId() == userId
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

        	return ResponseEntity.of(Optional.of(ticketEntity));

        } else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            modelAndView.setStatus(HttpStatus.FORBIDDEN);
        }

    }
    
    @PostMapping("/addTicket")
    public ResponseEntity<?> addTicket(Model model, @ModelAttribute("ticketAttribute") @Validated TicketRequest ticketRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<UserEntity> userEntities = userService.getUsers();
            model.addAttribute("users", userEntities);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            TicketEntity ticketEntity = ticketConverter.getConvertedTicket(ticketRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketEntity);
        }
    }


}
