package com.animo.animorise.repository;

import com.animo.animorise.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByScheduleStartBeforeAndNotifiedFalse(LocalDateTime now);
    List<Activity> findByOwnerId(Long ownerId);
}