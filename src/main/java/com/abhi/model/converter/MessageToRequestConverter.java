package com.abhi.model.converter;

import com.abhi.model.MessageEntity;
import com.abhi.model.requestModel.MessageRequest;
import com.abhi.service.TicketService;
import com.abhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageToRequestConverter {
    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    public MessageRequest getConvertedMessage(MessageEntity messageEntity) {
        MessageRequest messageRequest = new MessageRequest();

        messageRequest.setMessageId(messageEntity.getMessageId());
        messageRequest.setTicketId(messageEntity.getTicket().getTicketId());
        messageRequest.setAuthorId(messageEntity.getAuthor().getUserId());
        messageRequest.setRecipientId(messageEntity.getRecipient().getUserId());
        messageRequest.setText(messageEntity.getText());
        messageRequest.setCreateDate(messageEntity.getCreateDate());
        return messageRequest;
    }
}
