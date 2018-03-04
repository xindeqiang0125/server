package com.xcs.server.repository;

import com.xcs.server.domain.DoubleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DoubleHistoryRepository extends JpaRepository<DoubleHistory,Integer>{
    List<DoubleHistory> findByXItem_IdAndTimeBetween(Integer itemId,LocalDateTime start, LocalDateTime end);
    DoubleHistory findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(Integer itemId,LocalDateTime start);
}
