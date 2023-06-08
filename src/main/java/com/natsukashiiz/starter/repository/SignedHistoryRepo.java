package com.natsukashiiz.starter.repository;

import com.natsukashiiz.starter.entity.SignedHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignedHistoryRepo extends JpaRepository<SignedHistory, Long> {
    List<SignedHistory> findAllByUid(long uid, Pageable pageable);
}
