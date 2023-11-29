package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.Lecteurer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LecteurerRepository extends JpaRepository<Lecteurer, UUID> {
    @Query("SELECT participant FROM Lecteurer WHERE lecture.id = :idLecture")
    List<Participant> findByLectureId(@Param("idLecture") UUID idLecture);
}
