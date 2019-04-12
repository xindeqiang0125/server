package com.xcs.server.web.repository.history;

import com.xcs.server.history.domain.StringHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StringHistoryRepository extends JpaRepository<StringHistory,Integer>{
    List<StringHistory> findByXItem_IdAndTimeBetween(Integer itemId,LocalDateTime start, LocalDateTime end);
    StringHistory findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(Integer itemId,LocalDateTime start);
}
