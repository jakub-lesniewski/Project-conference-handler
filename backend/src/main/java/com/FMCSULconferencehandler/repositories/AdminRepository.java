package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
