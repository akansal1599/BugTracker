package com.abhi.service;

import com.abhi.model.MessageEntity;
import com.abhi.model.TicketEntity;
import com.abhi.repository.MessageRepository;
import com.abhi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void addTicket(TicketEntity ticketEntity) {
        ticketRepository.save(ticketEntity);
    }

    @Transactional
    @Override
    public List<TicketEntity> getTickets() {
        return ticketRepository.findAll();
    }

    @Transactional
    @Override
    public TicketEntity getTicket(int id) {
        return ticketRepository.findById(id).get();
    }

    @Override
    public void updateTicket(TicketEntity ticketEntity) {
        ticketRepository.save(ticketEntity);
    }

    @Transactional
    @Override
    public void deleteTicket(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public void addMessage(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
        updateTicket(messageEntity.getTicket());
    }

    @Override
    public void updateMessage(MessageEntity messageEntity) {
        messageRepository.save(messageEntity);
        updateTicket(messageEntity.getTicket());
    }

    @Transactional
    @Override
    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<TicketEntity> findUsersTickets(int userId) {

        List<TicketEntity> ticketEntityList = new ArrayList<>();
        for (TicketEntity ticket : getTickets()) {
            if (ticket.getCreator().getUserId() == userId || ticket.getHolder().getUserId() == userId) {
                ticketEntityList.add(ticket);
            }
        }
        return ticketEntityList;
    }
}
