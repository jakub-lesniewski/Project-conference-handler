package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID> {

    @Query("SELECT e FROM Lecture e WHERE e.event.id = :event_fk")
    Lecture findByEvent_fk(@Param("event_fk") UUID event_fk);

    Optional<Lecture> findByEventId(UUID id);

    @Modifying
    Long deleteByEventId(UUID idEvent);

    boolean existsByEventId(UUID id);
}
