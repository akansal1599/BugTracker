package com.abhi.controller;

import com.abhi.model.MessageEntity;
import com.abhi.model.TicketEntity;
import com.abhi.model.converter.MessageConverter;
import com.abhi.model.requestModel.MessageRequest;
import com.abhi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tickets")
public class MessageController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MessageConverter messageConverter;

   	@PostMapping("/addMessage")
    public ResponseEntity<?> addMessage(@ModelAttribute("messageAttribute") @Validated MessageRequest messageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            MessageEntity messageEntity = messageConverter.getConvertedMessage(messageRequest);

            TicketEntity ticketEntity = ticketService.getTicket(messageEntity.getTicket().getTicketId());
            ticketEntity.setHolder(messageEntity.getRecipient());

            ticketService.addMessage(messageEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(messageEntity);
        }
    }

    @PutMapping("/editMessage")
    public ResponseEntity<?> editMessage(@ModelAttribute("messageAttribute") @Validated MessageRequest messageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            MessageEntity messageEntity = messageConverter.getConvertedMessage(messageRequest);
            messageEntity.setMessageId(messageRequest.getMessageId());
            ticketService.addMessage(messageEntity);
            return ResponseEntity.status(HttpStatus.OK).body(messageEntity);
        }
    }

}
