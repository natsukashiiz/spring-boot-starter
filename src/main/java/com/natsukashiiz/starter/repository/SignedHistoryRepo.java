package com.natsukashiiz.starter.repository;

import com.natsukashiiz.starter.entity.SignedHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignedHistoryRepo extends JpaRepository<SignedHistory, Long> {
    Page<SignedHistory> findByUid(long uid, Pageable pageable);
}
