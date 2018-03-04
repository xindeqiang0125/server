package com.xcs.server.repository;

import com.xcs.server.domain.BooleanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BooleanHistoryRepository extends JpaRepository<BooleanHistory,Integer>{
    List<BooleanHistory> findByXItem_IdAndTimeBetween(Integer itemId,LocalDateTime start, LocalDateTime end);
    BooleanHistory findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(Integer itemId,LocalDateTime start);
}
