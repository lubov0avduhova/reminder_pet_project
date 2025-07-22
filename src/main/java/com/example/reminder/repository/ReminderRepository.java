package com.example.reminder.repository;

import com.example.reminder.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query(value = """
                SELECT * FROM reminder r
                WHERE (CAST(:title AS TEXT) IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')))
                  AND (CAST(:description AS TEXT) IS NULL OR LOWER(r.description) LIKE LOWER(CONCAT('%', :description, '%')))
                  AND (CAST(:start AS TIMESTAMP) IS NULL OR r.remind BETWEEN :start AND :end)
                ORDER BY r.title
            """, nativeQuery = true)
    Reminder findReminder(@Param("title") String title,
                          @Param("description") String description,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);

    Page<Reminder> findAllByRemindBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
