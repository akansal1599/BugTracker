package com.abhi.model.converter;

import com.abhi.model.TicketEntity;
import com.abhi.model.requestModel.TicketRequest;
import com.abhi.service.TicketService;
import com.abhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    public TicketEntity getConvertedTicket(TicketRequest ticketRequest) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setTitle(ticketRequest.getTitle());
        ticketEntity.setCreator(userService.getUser(ticketRequest.getCreatorId()));
        ticketEntity.setHolder(userService.getUser(ticketRequest.getHolderId()));
        ticketService.addTicket(ticketEntity);
        return ticketEntity;
    }
}
