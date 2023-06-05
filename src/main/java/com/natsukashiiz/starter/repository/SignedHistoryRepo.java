package com.natsukashiiz.starter.repository;

import com.natsukashiiz.starter.entity.SignedHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignedHistoryRepo extends JpaRepository<SignedHistory, Long> {
}
