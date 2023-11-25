package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID> {

    @Query("SELECT e FROM Lecture e WHERE e.event.id = :event_fk")
    List<Lecture> findByEvent_fk(@Param("event_fk") UUID event_fk);
}
