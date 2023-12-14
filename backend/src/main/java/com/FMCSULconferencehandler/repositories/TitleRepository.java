package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TitleRepository extends JpaRepository<Title, UUID> {
}
