package com.calendar.core.domain.entity.repository;

import com.calendar.core.domain.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
}