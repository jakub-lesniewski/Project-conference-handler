package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConferenceRepository extends JpaRepository<Conference,UUID> {
    @Query("select c from Conference c order by c.date_start asc")
    List<Conference> findAllByOrderByDate_start();
}
