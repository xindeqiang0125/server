package com.xcs.server.web.repository.history;

import com.xcs.server.history.domain.IntegerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IntegerHistoryRepository extends JpaRepository<IntegerHistory,Integer>{
    List<IntegerHistory> findByXItem_IdAndTimeBetween(Integer itemId,LocalDateTime start, LocalDateTime end);
    IntegerHistory findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(Integer itemId,LocalDateTime start);
}
