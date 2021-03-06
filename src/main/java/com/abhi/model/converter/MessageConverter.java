package com.abhi.model.converter;

import com.abhi.model.MessageEntity;
import com.abhi.model.requestModel.MessageRequest;
import com.abhi.service.TicketService;
import com.abhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MessageConverter {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Transactional
    public MessageEntity getConvertedMessage(MessageRequest messageRequest) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setCreateDate(messageRequest.getCreateDate());
        messageEntity.setTicket(ticketService.getTicket(messageRequest.getTicketId()));
        messageEntity.setAuthor(userService.getUser(messageRequest.getAuthorId()));
        messageEntity.setRecipient(userService.getUser(messageRequest.getRecipientId()));
        messageEntity.setText(messageRequest.getText());

        return messageEntity;
    }

}
