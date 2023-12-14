package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Attendee;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    @Query("SELECT participant FROM Lecturer WHERE lecture.id = :idLecture")
    List<Participant> findByLectureId(@Param("idLecture") UUID idLecture);

    @Query("SELECT l FROM Lecturer l WHERE l.lecture.id = :idLecture")
    List<Lecturer> findLecturersByLecture(@Param("idLecture") UUID idLecture);

    @Query("SELECT l FROM Lecturer l WHERE l.participant.id = :idParticipant")
    List<Lecturer> findLecturersByParticipant(@Param("idParticipant") UUID idParticipant);

    @Query("SELECT l FROM Lecturer l WHERE l.lecture.id = :idLecture AND l.participant.id = :idParticipant")
    Optional<Lecturer> findLecturerByLectureAndParticipant(@Param("idLecture") UUID idLecture, @Param("idParticipant") UUID idParticipant);
}
