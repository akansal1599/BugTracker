package com.abhi.service;

import com.abhi.model.MessageEntity;
import com.abhi.model.TicketEntity;

import java.util.List;

public interface TicketService {

    void addTicket(TicketEntity ticketEntity);

    List<TicketEntity> getTickets();

    TicketEntity getTicket(int id);

    void updateTicket(TicketEntity ticketEntity);

    void deleteTicket(int id);

    void addMessage(MessageEntity messageEntity);

    void updateMessage(MessageEntity messageEntity);

    void deleteMessage(int id);

    List<TicketEntity> findUsersTickets(int userId);

}
