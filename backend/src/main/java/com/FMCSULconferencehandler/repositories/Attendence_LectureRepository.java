package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.Attendance_Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface Attendence_LectureRepository extends JpaRepository<Attendance_Lecture, UUID> {
    @Query("SELECT participant FROM Attendance_Lecture WHERE lecture.id = :idLecture")
    List<Participant> findByLectureId(@Param("idLecture") UUID idLecture);
}
