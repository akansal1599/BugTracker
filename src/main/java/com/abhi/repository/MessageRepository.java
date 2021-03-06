package com.abhi.repository;

import com.abhi.model.MessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

    @Override
    <S extends MessageEntity> S save(S entity);

    @Override
    void deleteById(Integer integer);
}
