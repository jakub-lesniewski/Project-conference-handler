package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.conference.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeRepository extends JpaRepository<Type, UUID> {

}
