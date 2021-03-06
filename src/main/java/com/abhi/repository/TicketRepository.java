package com.abhi.repository;

import com.abhi.model.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends CrudRepository<TicketEntity, Integer> {
    @Override
    <S extends TicketEntity> S save(S entity);

    @Override
    Optional<TicketEntity> findById(Integer integer);

    @Override
    List<TicketEntity> findAll();

    @Override
    void deleteById(Integer integer);

}
