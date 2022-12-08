package com.ashrafplanet.quicktodolistservice.todos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

	List<TodoEntity> findByUserIdCreatorOrderById(Long userIdCreator);
}
